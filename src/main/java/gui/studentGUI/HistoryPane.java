/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui.studentGUI;

import dao.attendanceDAO;
import pojo.Attendance;
import utils.attendanceTable;
import utils.attendanceTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class HistoryPane extends javax.swing.JPanel {

    private gui.generalGUI.mainFrame mainFrame;

    public HistoryPane(gui.generalGUI.mainFrame mainFrame) {
        this.mainFrame=mainFrame;
        initComponents();
        setUpAction();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(37, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(746, 70));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(746, 40));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));
        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel5.setPreferredSize(new java.awt.Dimension(787, 35));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        backBtn.setText("Back");
        backBtn.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel5.add(backBtn);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("ATTENDANCE HISTORY");
        jLabel1.setMinimumSize(new java.awt.Dimension(252, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel5.add(jLabel1);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 400));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }

    private void semesterCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semesterCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_semesterCBActionPerformed

    private void setUpAction()
    {

        //set up attend table
        Object[] colnames={"Subject ID","Week 1","Week 2","Week 3",
                "Week 4","Week 5","Week 6","Week 7",
                "Week 8","Week 9","Week 10","Week 11","Week 12"
                ,"Week 13","Week 14","Week 15"};
        Class[] classNames={Object.class,  String.class, String.class, String.class
                , String.class, String.class, String.class, String.class
                , String.class, String.class, String.class, String.class
                , String.class, String.class, String.class, String.class};
//        classTable.setModel(null);
        Object[][]data={

        };
        attendTable=new attendanceTable(data,colnames,classNames);
        attendTM=attendTable.getModel();
        jScrollPane1.setViewportView(attendTable);
        attendTable.getTableHeader().setReorderingAllowed(false);

        //load data for attend table
        ArrayList<Attendance> result = attendanceDAO.queryAttendStudent(mainFrame.getAccount().getUsername());
        for (Attendance att:result) {
            attendTM.addRow(attendanceDAO.generateToRow(att,true));
        }


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new MenuPane(mainFrame));
            }
        });
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Semester;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    private attendanceTable attendTable;
    private attendanceTableModel attendTM;
    private javax.swing.JComboBox<String> semesterCB;
    // End of variables declaration//GEN-END:variables
}
