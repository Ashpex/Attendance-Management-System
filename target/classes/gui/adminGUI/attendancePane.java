/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui.adminGUI;
import dao.attendanceDAO;
import dao.subjectDAO;
import pojo.Attendance;
import pojo.AttendanceID;
import pojo.Subject;
import utils.attendanceTable;
import utils.attendanceTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class attendancePane extends javax.swing.JPanel {

    private gui.generalGUI.mainFrame mainFrame;
    public attendancePane(gui.generalGUI.mainFrame mainFrame) {
        this.mainFrame=mainFrame;
        initComponents();
        setUpAction();

    }

    private void setUpAction()
    {

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new menuPane(mainFrame));
            }
        });
        //set up subject cb
        subjectCBM=new DefaultComboBoxModel<>();
        subjectCB.setModel(subjectCBM);
        subjectList= subjectDAO.getAllSubjects();

        subjects = subjectDAO.getAllSubjectNames(subjectList);
        subjectCBM.addAll(subjects);

        Object[] colnames={"Student ID","Week 1","Week 2","Week 3",
                "Week 4","Week 5","Week 6","Week 7",
                "Week 8","Week 9","Week 10","Week 11","Week 12"
                ,"Week 13","Week 14","Week 15"};
        Class[] classNames={Object.class, String.class, String.class, String.class
                , String.class, String.class, String.class, String.class
                , String.class, String.class, String.class, String.class
                , String.class, String.class, String.class, String.class};
        Object[][]data={

        };
        classTable=new attendanceTable(data,colnames,classNames);
        classTable.getTableHeader().setReorderingAllowed(false);
        classTM=classTable.getModel();



        jScrollPane1.setViewportView(classTable);
        subjectCB.setSelectedIndex(0);

        showClassBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected=subjectCB.getSelectedIndex();
                if(selected>=0)
                {
                    List<Attendance> attendanceTable= attendanceDAO.queryAttend(subjectList.get(selected).getIdsubject());
                    classTM.removeAllRow();
                    for (int i=0;i<attendanceTable.size();i++)
                    {
                        classTM.addRow(attendanceDAO.generateToRow(attendanceTable.get(i),false));
                    }
                }
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected=classTable.getSelectedRow();
                if(selected>=0)
                {
                    classTM.addEditableRow(selected);
                }
            }
        });
        showClassBtn.doClick();

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList editRows=classTM.getEditableRows();
                Subject subject=subjectList.get(subjectCB.getSelectedIndex());
                while (editRows.size()>0)
                {
                    int row=(int) editRows.get(0);
                    Object[] rowData=classTM.getRowData(row);
                    //convert to attend string
                    char[] attend="111111111111111".toCharArray();
                    for(int i=1;i<rowData.length;i++)
                    {
                        attend[i-1]=(char) ((int)rowData[i]+'0');
                    }
                    //update in database
                    attendanceDAO.updateAttend(String.valueOf(rowData[0]),subject.getIdsubject(),new String(attend));

                    //pop out element
                    editRows.remove(0);
                }

                //update editrow in TableModel
                classTM.setEditableRows(editRows);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String newStudentID=JOptionPane.showInputDialog(null,"Please enter student ID");
                Attendance attendance=new Attendance();
                AttendanceID attendanceID=new AttendanceID();
                attendanceID.setIdstudent(newStudentID);
                System.out.println(String.valueOf(subjects.get(subjectCB.getSelectedIndex())));
                attendanceID.setIdsubject(String.valueOf(subjectList.get(subjectCB.getSelectedIndex()).getIdsubject()));
                attendance.setIdattendance(attendanceID);
                attendance.setAttend("111111111111111");
                JOptionPane.showMessageDialog(null,"Added new student "+newStudentID+" to subject "+subjectList.get(subjectCB.getSelectedIndex()).getIdsubject());
                attendanceDAO.addAttend(attendanceID.getIdstudent(),attendanceID.getIdsubject(),"111111111111111");
                classTM.addRow(attendanceDAO.generateToRow(attendance,false));

            }
        });

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=classTable.getSelectedRow();
                if(selectedRow>=0)
                {
                    int option=JOptionPane.showConfirmDialog(null,"Do you want to delete this student?",
                            "Delete student",JOptionPane.YES_NO_OPTION);
                    if(option==JOptionPane.YES_OPTION)
                    {
                        while (selectedRow>=0)
                        {
                            Object[] rowData=classTM.getRowData(selectedRow);
                            //remove in database
                            attendanceDAO.removeStudentFromClassNew(String.valueOf(subjectList.get(subjectCB.getSelectedIndex()).getIdsubject()),
                                    String.valueOf(rowData[0]));

                            //remove in tablemodel
                            classTM.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null,"Removed student "+rowData[0]+" from subject "+subjectList.get(subjectCB.getSelectedIndex()).getIdsubject());
                            selectedRow=classTable.getSelectedRow();
                        }
                    }
                }

            }
        });

        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc=new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("*.csv","csv"));
                int val=fc.showOpenDialog(null);
                if(val==JFileChooser.APPROVE_OPTION)
                {
                    File studentFile=fc.getSelectedFile();
                    String extensionFile=studentFile.getName().split("\\.")[1];
                    if(extensionFile.equals("csv")==false)
                    {
                        JOptionPane.showMessageDialog(null,new Object[]{
                                "File extension is not .csv"},"Error",JOptionPane.ERROR_MESSAGE);

                        return;
                    }

                    ArrayList<String> studentList= readStudentFile(studentFile);
                    for(int i=0;i<studentList.size();i++)
                    {
                        Attendance attendance=new Attendance();
                        AttendanceID attendanceID=new AttendanceID();
                        attendanceID.setIdstudent(studentList.get(i));
                        attendanceID.setIdsubject(String.valueOf(subjectList.get(subjectCB.getSelectedIndex()).getIdsubject()));
                        attendance.setIdattendance(attendanceID);
                        attendance.setAttend("111111111111111");
                        attendanceDAO.addAttend(attendanceID.getIdstudent(),attendanceID.getIdsubject(),"111111111111111");
                        JOptionPane.showMessageDialog(null,new Object[]{
                                "Import student success","Student: "+studentList.get(i)},"Success",JOptionPane.INFORMATION_MESSAGE);
                        classTM.addRow(attendanceDAO.generateToRow(attendance,false));
                    }

                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        centerPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        jPanel6 = new javax.swing.JPanel();
        editBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        applyBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        importBtn = new javax.swing.JButton();
        topPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        subjectCB = new javax.swing.JComboBox<>();
        emptyCB = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        showClassBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        centerPanel.setMaximumSize(new java.awt.Dimension(2147483647, 400));
        centerPanel.setPreferredSize(new java.awt.Dimension(577, 300));
        centerPanel.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());


        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        centerPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel6.setMaximumSize(new java.awt.Dimension(150, 32767));
        jPanel6.setMinimumSize(new java.awt.Dimension(125, 100));
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 300));

        editBtn.setText("Edit");
        editBtn.setPreferredSize(new java.awt.Dimension(100, 30));

        removeBtn.setText("Remove");
        removeBtn.setPreferredSize(new java.awt.Dimension(100, 30));

        applyBtn.setText("Apply");
        applyBtn.setPreferredSize(new java.awt.Dimension(100, 30));

        addBtn.setText("Add");
        addBtn.setPreferredSize(new java.awt.Dimension(100, 30));

        importBtn.setText("Import...");
        importBtn.setPreferredSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap(25, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(applyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(importBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(importBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addComponent(applyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        centerPanel.add(jPanel6, java.awt.BorderLayout.WEST);

        add(centerPanel, java.awt.BorderLayout.CENTER);

        topPanel.setPreferredSize(new java.awt.Dimension(684, 100));
        topPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(580, 200));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel4.setPreferredSize(new java.awt.Dimension(460, 100));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("Subject:");
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 25));
        jPanel4.add(jLabel2);

        subjectCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Subject 1" }));
        subjectCB.setPreferredSize(new java.awt.Dimension(80, 25));
        jPanel4.add(subjectCB);

        emptyCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ABSENT", "NOT CHECKED", "ATTEND" }));
//        jPanel4.add(emptyCB);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        topPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel2.setMaximumSize(new java.awt.Dimension(100, 32767));
        jPanel2.setPreferredSize(new java.awt.Dimension(125, 93));

        showClassBtn.setText("Show");
        showClassBtn.setAlignmentX(0.5F);
        showClassBtn.setPreferredSize(new java.awt.Dimension(85, 30));
        //jPanel2.add(showClassBtn);

        topPanel.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setPreferredSize(new java.awt.Dimension(787, 35));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        backBtn.setText("Back");
        backBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        backBtn.setPreferredSize(new java.awt.Dimension(85, 30));
        jPanel3.add(backBtn);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("ATTENDANCE MANAGEMENT");
        jLabel1.setMinimumSize(new java.awt.Dimension(252, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel3.add(jLabel1);

        topPanel.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        add(topPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton applyBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel centerPanel;
    private attendanceTable classTable;
    private javax.swing.JButton editBtn;
    private javax.swing.JComboBox<String> emptyCB;
    private javax.swing.JButton importBtn;
    private javax.swing.JComboBox<String> subjectCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton showClassBtn;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    private attendanceTableModel classTM;
    private DefaultComboBoxModel<String> subjectCBM;
    private List<Subject> subjectList;
    ArrayList<String>subjects;

    //another function
    public ArrayList<String> readStudentFile(File file)
    {
        ArrayList<String> ret=new ArrayList<>();
        try{
            BufferedReader br=new BufferedReader(new FileReader(file));
            //skip first line
            br.readLine();
            String line=br.readLine();

            while (line!=null)
            {
                ret.add(line);
                System.out.println(line);
                System.out.println(line);
                line=br.readLine();
            }
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null,new Object[]{
                    "Error file or csv is not compatible with template.",
                    "Template example: ",
                    "Student ID\n" +
                            "id1\n" +
                            "id2\n" +
                            "...\n" +
                            "idn\n"
            },"Error",JOptionPane.ERROR_MESSAGE);
        }


        return ret;

    }
}


