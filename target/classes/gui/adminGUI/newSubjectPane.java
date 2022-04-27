package gui.adminGUI;

import dao.subjectDAO;
import utils.dateFormatter;
import utils.detailTable;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pojo.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import static java.lang.Math.round;

public class newSubjectPane extends javax.swing.JPanel {
    private gui.generalGUI.mainFrame mainFrame;
    static private Properties dateProperties;
    /**
     * Creates new form ModifySubjectPanel
     */
    public newSubjectPane(gui.generalGUI.mainFrame mainFrame) {
        this.mainFrame=mainFrame;
        initComponents();
        setUpAction();
    }

    private void setUpAction() {
        //        jPanel3.add(startDatePicker);
//        jPanel3.add(endDatePicker);
        dayInWeekCB.setEnabled(false);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new menuPane(mainFrame));
            }
        });

        String[][] data={};
        String[] subjectColNames={"Subject ID","Subject Name","Schedule","Room","Number of weeks","Start day","End day"};
        subjectTM=new detailTable(data,subjectColNames);
        subjectTable.setModel(subjectTM);
        List<Object[]> subjectData= subjectDAO.convertToListObject(subjectDAO.getAllSubjects());
        for (int i=0;i<subjectData.size();i++)
        {
            subjectTM.addRow(subjectData.get(i));
        }
        subjectTable.getTableHeader().setReorderingAllowed(false);

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=subjectTable.getSelectedRow();
                if(selectedRow>=0) {
                    Object[] rowData = subjectTM.getRowData(selectedRow);
                    JTextField subjectID_ETF = new JTextField();
                    subjectID_ETF.setText(String.valueOf(rowData[0]));
                    subjectID_ETF.setEnabled(false);
                    JTextField subjectName_ETF=new JTextField();
                    subjectName_ETF.setText(String.valueOf(rowData[1]));
                    JTextField roomETF=new JTextField();
                    roomETF.setText(String.valueOf(rowData[3]));
                    JDatePickerImpl startEDP=new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(),dateProperties),new dateFormatter());
                    java.util.Date startD= subjectDAO.convertSQLDateToUtilDate((Date)rowData[5]);
                    System.out.println(startD);
                    System.out.println(rowData[4]);
                    startEDP.getModel().setDate(startD.getYear()+1900,startD.getMonth(),startD.getDate());
//                    startEDP.getModel().setDay(startD.getDate());
//                    startEDP.getModel().setMonth(startD.getMonth()-1);
//                    startEDP.getModel().setYear(startD.getYear()+1900);
                    System.out.println(String.format("%d-%d-%d",startD.getDate(),startD.getMonth(),startD.getYear()));
                    String schedule=String.valueOf(rowData[2]);
                    System.out.println(String.format("%d-%d-%d", subjectDAO.convertScheduleToObject(schedule,0),
                            subjectDAO.convertScheduleToObject(schedule,1),
                            subjectDAO.convertScheduleToObject(schedule,2)
                    ));
                    JComboBox<String> diwECB=new JComboBox<>(new DefaultComboBoxModel<String>(
                            new String[]{"Mon","Tue","Wed","Thu","Fri","Sat","Sun"}
                    ));
                    diwECB.setSelectedIndex(subjectDAO.convertScheduleToObject(schedule,0));
                    JComboBox<String> numberOfWeekECB=new JComboBox<>(
                            new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5",
                                    "6", "7", "8", "9", "10", "11",
                                    "12", "13", "14", "15"}
                            ));
                    numberOfWeekECB.setSelectedIndex((int)rowData[4]-1);
                    JComboBox<String> startSesECB=new JComboBox<>(
                            new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5",
                                    "6", "7", "8", "9", "10", "11",
                                    "12", "13", "14", "15"}
                            ));
                    int start= subjectDAO.convertScheduleToObject(schedule,1)-1;
                    if(start>=0)
                        startSesECB.setSelectedIndex(start);

                    JComboBox<String> endSesECB=new JComboBox<>(
                            new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5",
                                    "6", "7", "8", "9", "10", "11",
                                    "12", "13", "14", "15"}
                            ));
                    int end= subjectDAO.convertScheduleToObject(schedule,2)-1;
                    if(end>=0)
                        endSesECB.setSelectedIndex(end);
                    startEDP.getModel().setSelected(true);
                    JDatePickerImpl endEDP=new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(),dateProperties),new dateFormatter());
                    java.util.Date endD= subjectDAO.convertSQLDateToUtilDate((Date)rowData[6]);
                    endEDP.getModel().setDate(endD.getYear()+1900,endD.getMonth(),endD.getDate());

                    endEDP.getModel().setSelected(true);
                    Object[] objects = {
                            "Subject ID:",subjectID_ETF,
                            "Subject Name:",subjectName_ETF,
                            "Day in week:",diwECB,
                            "Start Session",startSesECB,
                            "End Session", endSesECB,
                            "Room Name:",roomETF,
                            "Number of weeks:",numberOfWeekECB,
                            "Start day:",startEDP,
                            "End day:",endEDP
                    };
                    int option = JOptionPane.showOptionDialog(null, objects, "Modify Subject",
                            JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, new Object[]{"Confirm", "Cancel"}, 1);
                    System.out.println(option);
                    if(option==0)
                    {
                        System.out.println("Check data in database and update");
                        Subject subject=new Subject();
                        subject.setIdsubject(subjectID_ETF.getText());
                        subject.setSubjectName(subjectName_ETF.getText());
                        subject.setStartday((java.util.Date) startEDP.getModel().getValue());
                        subject.setEndday((java.util.Date) endEDP.getModel().getValue());
                        subject.setNumberweeks(numberOfWeekECB.getSelectedIndex()+1);
                        subject.setSchedule(Subject.convertDataToSchedule(new String[]{
                                String.valueOf(diwECB.getSelectedItem()),
                                String.valueOf(startSesECB.getSelectedItem()),
                                String.valueOf(endSesECB.getSelectedItem()),
                        }));
                        subject.setRoomname(roomETF.getText());
                        System.out.println(subject);
                        subjectDAO.saveOrUpdateSubject(subject);
                        //update row data
                        subjectTM.updateRowData(selectedRow, subjectDAO.convertSubjectToRowData(subject));
                    }
//                    isFinishEditting = false;
//                    //enable row
//                    selectingRow = selectedRow;
//                    subjectTM.setRowEditable(selectedRow);
//                    subjectTM.setDataEdited(false);
//                    System.out.println(selectedRow);
                }
            }
        });

//        applyBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//
//            }
//        });
        subjectTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyChar()==KeyEvent.VK_ENTER)
                {
                    isFinishEditting=true;
                    System.out.println("Enter pressed");

                }
            }
        });
        TableCellRenderer cellRenderer=new DefaultTableCellRenderer();
        subjectTable.setDefaultRenderer(Boolean.class,cellRenderer);

        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addSubjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get data
                String subjectID=subjectIDTF.getText();
                String subjectName=subjectNameTF.getText();
                String DIW=String.valueOf(dayInWeekCB.getSelectedItem());
                int startSession=Integer.parseInt(String.valueOf(startSessionCB.getSelectedItem()));
                int endSession=Integer.parseInt(String.valueOf(endSessionCB.getSelectedItem()));
                String roomName=roomNameTF.getText();
                int nw= Integer.parseInt(String.valueOf(numberWeekCB.getSelectedItem()));
                Object startDay=startDatePicker.getModel().getValue();
                Object endDay=endDatePicker.getModel().getValue();
                //check data valid
                if(subjectID.equals("")||subjectName.equals("")||roomName.equals(""))
                {
                    JOptionPane.showMessageDialog(null,
                            "Subject ID or Subject Name or Room Name is empty!\nPlease fill all to continue",
                            "Message",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(startSession>=endSession)
                {
                    JOptionPane.showMessageDialog(null,
                            "End Session must be greater than Start Session",
                            "Message",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(startDay==null||endDay==null)
                {
                    JOptionPane.showMessageDialog(null,
                            "Start day or End day is empty",
                            "Message",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Date startDate=new Date(((java.util.Date)startDay).getTime());
                Date endDate=new Date(((java.util.Date)endDay).getTime());
                if(endDate.before(startDate))
                {
                    JOptionPane.showMessageDialog(null,
                            "End day must be greater than Start day",
                            "Message",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }


                Subject subject=new Subject();
                subject.setIdsubject(subjectID);
                subject.setSubjectName(subjectName);
                subject.setStartday(startDate);
                subject.setEndday(endDate);
                subject.setSchedule(Subject.convertDataToSchedule(new String[]{DIW,String.valueOf(startSession),String.valueOf(endSession)}));
                subject.setRoomname(roomName);
                subject.setNumberweeks(numberWeekCB.getSelectedIndex()+1);

                subjectTM.addRow(subjectDAO.convertSubjectToRowData(subject));

                System.out.println("add subject to database");
                boolean commitRes= subjectDAO.saveOrUpdateSubject(subject);
                if(commitRes==false)
                {
                    JOptionPane.showMessageDialog(null,
                            "Error in add new subject or id of subject is duplicated in database\nPlease check again",
                            "Message",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow=subjectTable.getSelectedRow();
                if(selectedRow>=0)
                {
                    //confirm before delete
                    int option=JOptionPane.showConfirmDialog(null,"Do you want to delete selected row?",
                            "Confirm delete",JOptionPane.YES_NO_OPTION);
                    if(option==JOptionPane.YES_OPTION)

                        while (selectedRow>=0) {




                            //remove in database
                            Object[] rowData=subjectTM.getRowData(selectedRow);
                            Subject subject=new Subject();
                            subject.setIdsubject(String.valueOf(rowData[0]));
                            subjectDAO.removeSubject(subject);


                            //remove in model
                            subjectTM.removeRow(selectedRow);


                            selectedRow=subjectTable.getSelectedRow();
                        }

                }
            }
        });

        startDatePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date pickedDate=(java.util.Date)startDatePicker.getModel().getValue();
                if(pickedDate!=null)
                {
                    dayInWeekCB.setSelectedIndex(pickedDate.getDay()-1  );
                }

            }
        });

    }

    private void initComponents() {
        dateProperties = new Properties();
        dateProperties.setProperty("text.day","Day");
        dateProperties.setProperty("text.month","Month");
        dateProperties.setProperty("text.year","Year");
        startDatePicker=new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(),dateProperties),new dateFormatter());
        endDatePicker=new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(),dateProperties),new dateFormatter());

        jLabel11 = new javax.swing.JLabel();
        topPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        subjectIDTF = new javax.swing.JTextField();
        startDayTF = new javax.swing.JTextField();
        endDayTF = new javax.swing.JTextField();
        subjectNameTF = new javax.swing.JTextField();
        numberWeekCB = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
//        jComboBox2 = new javax.swing.JComboBox<>();
        startSessionCB = new javax.swing.JComboBox<>();
        endSessionCB = new javax.swing.JComboBox<>();
        dayInWeekCB=new JComboBox<>();
        roomNameTF = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        addSubjectBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        subjectTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        editBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();

        jLabel11.setText("jLabel11");

        setLayout(new java.awt.BorderLayout());

        topPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        topPanel.setPreferredSize(new java.awt.Dimension(684, 160));
        topPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(580, 200));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel3.setLayout(new java.awt.GridLayout(2, 5, 20, 0));

        jLabel2.setText("Subject ID:");
        jPanel3.add(jLabel2);

        jLabel4.setText("Start Day:");
        jPanel3.add(jLabel4);

        jLabel5.setText("End day:");
        jPanel3.add(jLabel5);

        jLabel3.setText("Subject Name:");
        jPanel3.add(jLabel3);

        jLabel7.setText("Number of weeks:");
        jPanel3.add(jLabel7);

        subjectIDTF.setMinimumSize(new java.awt.Dimension(80, 24));
        subjectIDTF.setPreferredSize(new java.awt.Dimension(81, 24));
        jPanel3.add(subjectIDTF);

        startDayTF.setToolTipText("The beginning of subject");
        startDayTF.setMinimumSize(new java.awt.Dimension(80, 24));
        startDayTF.setPreferredSize(new java.awt.Dimension(81, 24));
        startDayTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDayTFActionPerformed(evt);
            }
        });
        jPanel3.add(startDatePicker);

        endDayTF.setMinimumSize(new java.awt.Dimension(80, 24));
        endDayTF.setPreferredSize(new java.awt.Dimension(81, 24));
        jPanel3.add(endDatePicker);

        subjectNameTF.setMinimumSize(new java.awt.Dimension(80, 24));
        subjectNameTF.setPreferredSize(new java.awt.Dimension(81, 24));
        subjectNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectNameTFActionPerformed(evt);
            }
        });
        jPanel3.add(subjectNameTF);

        numberWeekCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
        jPanel3.add(numberWeekCB);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jPanel4.setPreferredSize(new java.awt.Dimension(460, 100));
        jPanel4.setLayout(new java.awt.GridLayout(2, 5, 10, 5));

        jLabel6.setText("Day in Week:");
        jPanel4.add(jLabel6);

        jLabel8.setText("Start Session:");
        jPanel4.add(jLabel8);

        jLabel9.setText("End Session:");
        jPanel4.add(jLabel9);

        jLabel10.setText("Room Name:");
        jPanel4.add(jLabel10);

        dayInWeekCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        jPanel4.add(dayInWeekCB);

        startSessionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jPanel4.add(startSessionCB);

        endSessionCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jPanel4.add(endSessionCB);

        roomNameTF.setMinimumSize(new java.awt.Dimension(80, 24));
        roomNameTF.setPreferredSize(new java.awt.Dimension(81, 24));
        jPanel4.add(roomNameTF);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        topPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMaximumSize(new java.awt.Dimension(100, 32767));
        jPanel2.setPreferredSize(new java.awt.Dimension(125, 93));

        addSubjectBtn.setText("Add");
        addSubjectBtn.setToolTipText("Add new subject with data from left side");
        addSubjectBtn.setPreferredSize(new java.awt.Dimension(72, 25));
        jPanel2.add(addSubjectBtn);

        topPanel.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel7.setMinimumSize(new java.awt.Dimension(299, 80));
        jPanel7.setPreferredSize(new java.awt.Dimension(787, 40));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        backBtn.setText("Back");
        backBtn.setToolTipText("Back to main menu");
        backBtn.setPreferredSize(new java.awt.Dimension(70, 30));
        backBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel7.add(backBtn);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("SUBJECT MANAGEMENT");
        jLabel1.setMinimumSize(new java.awt.Dimension(252, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel7.add(jLabel1);

        topPanel.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        add(topPanel, java.awt.BorderLayout.PAGE_START);

        centerPanel.setMaximumSize(new java.awt.Dimension(2147483647, 400));
        centerPanel.setPreferredSize(new java.awt.Dimension(577, 300));
        centerPanel.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        subjectTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(subjectTable);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        centerPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setMaximumSize(new java.awt.Dimension(150, 32767));
        jPanel6.setMinimumSize(new java.awt.Dimension(125, 100));
        jPanel6.setPreferredSize(new java.awt.Dimension(125, 300));

        editBtn.setText("Edit");
        editBtn.setPreferredSize(new java.awt.Dimension(110, 30));

        JButton addBtn = new JButton("Add");
        addBtn.setPreferredSize(new java.awt.Dimension(110, 30));
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField subjectID_ETF = new JTextField();
                subjectID_ETF.setText("");
                subjectID_ETF.setEnabled(true);
                JTextField subjectName_ETF=new JTextField();
                subjectName_ETF.setText("");
                JTextField roomETF=new JTextField();
                roomETF.setText("");
                JDatePickerImpl startEDP=new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(),dateProperties),new dateFormatter());
                java.util.Date startD= subjectDAO.convertSQLDateToUtilDate((Date)startEDP.getModel().getValue());
                startEDP.getModel().setDate(startD.getYear()+1900,startD.getMonth(),startD.getDate());
                System.out.println(String.format("%d-%d-%d",startD.getDate(),startD.getMonth(),startD.getYear()));
                String schedule=String.format("%d-%d-%d",startD.getDate(),startD.getMonth(),startD.getYear());
                System.out.println(String.format("%d-%d-%d", subjectDAO.convertScheduleToObject(schedule,0),
                        subjectDAO.convertScheduleToObject(schedule,1),
                        subjectDAO.convertScheduleToObject(schedule,2)
                ));
                JComboBox<String> diwECB=new JComboBox<>(new DefaultComboBoxModel<String>(
                        new String[]{"Mon","Tue","Wed","Thu","Fri","Sat","Sun"}
                ));
                diwECB.setSelectedIndex(subjectDAO.convertScheduleToObject(schedule,0));
                JComboBox<String> numberOfWeekECB=new JComboBox<>(
                        new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5",
                                "6", "7", "8", "9", "10", "11",
                                "12", "13", "14", "15"}
                        ));
                numberOfWeekECB.setSelectedIndex((int)round(subjectDAO.convertScheduleToObject(schedule,1))-1);
                JComboBox<String> startSesECB=new JComboBox<>(
                        new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5",
                                "6", "7", "8", "9", "10", "11",
                                "12", "13", "14", "15"}
                        ));
                int start= subjectDAO.convertScheduleToObject(schedule,1)-1;
                if(start>=0)
                    startSesECB.setSelectedIndex(start);

                JComboBox<String> endSesECB=new JComboBox<>(
                        new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5",
                                "6", "7", "8", "9", "10", "11",
                                "12", "13", "14", "15"}
                        ));
                int end= subjectDAO.convertScheduleToObject(schedule,2)-1;
                if(end>=0)
                    endSesECB.setSelectedIndex(end);
                startEDP.getModel().setSelected(true);
                JDatePickerImpl endEDP=new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(),dateProperties),new dateFormatter());
                java.util.Date endD= subjectDAO.convertSQLDateToUtilDate((Date)endEDP.getModel().getValue());
                endEDP.getModel().setDate(endD.getYear()+1900,endD.getMonth(),endD.getDate());

                endEDP.getModel().setSelected(true);
                Object[] objects = {
                        "Subject ID:",subjectID_ETF,
                        "Subject Name:",subjectName_ETF,
                        "Day in week:",diwECB,
                        "Start Session",startSesECB,
                        "End Session", endSesECB,
                        "Room Name:",roomETF,
                        "Number of weeks:",numberOfWeekECB,
                        "Start day:",startEDP,
                        "End day:",endEDP
                };
                int option = JOptionPane.showOptionDialog(null, objects, "Modify Subject",
                        JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{"Confirm", "Cancel"}, 1);
                System.out.println(option);
                if(option==0)
                {
                    System.out.println("Check data in database and update");
                    Subject subject=new Subject();
                    subject.setIdsubject(subjectID_ETF.getText());
                    subject.setSubjectName(subjectName_ETF.getText());
                    subject.setStartday((java.util.Date) startEDP.getModel().getValue());
                    subject.setEndday((java.util.Date) endEDP.getModel().getValue());
                    subject.setNumberweeks(numberOfWeekECB.getSelectedIndex()+1);
                    subject.setSchedule(Subject.convertDataToSchedule(new String[]{
                            String.valueOf(diwECB.getSelectedItem()),
                            String.valueOf(startSesECB.getSelectedItem()),
                            String.valueOf(endSesECB.getSelectedItem()),
                    }));
                    subject.setRoomname(roomETF.getText());
                    System.out.println(subject);
                    subjectDAO.saveOrUpdateSubject(subject);
                    subjectTM.addRow(subjectDAO.convertSubjectToRowData(subject));
                    System.out.println("add subject to database");
                    boolean commitRes= subjectDAO.saveOrUpdateSubject(subject);
                    if(commitRes==false)
                    {
                        JOptionPane.showMessageDialog(null,
                                "Error in add new subject or id of subject is duplicated in database\nPlease check again",
                                "Message",JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        removeBtn.setText("Remove");
        removeBtn.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(editBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(222, Short.MAX_VALUE))
        );

        centerPanel.add(jPanel6, java.awt.BorderLayout.EAST);

        add(centerPanel, java.awt.BorderLayout.CENTER);
    }
    private void subjectNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subjectNameTFActionPerformed

    private void startDayTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDayTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startDayTFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSubjectBtn;
    private javax.swing.JButton applyBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField endDayTF;
    private javax.swing.JComboBox<String> endSessionCB;
    private javax.swing.JComboBox<String> dayInWeekCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable subjectTable;
    private javax.swing.JComboBox<String> numberWeekCB;
    private javax.swing.JButton removeBtn;
    private javax.swing.JTextField roomNameTF;
    private javax.swing.JTextField startDayTF;
    private javax.swing.JComboBox<String> startSessionCB;
    private javax.swing.JTextField subjectIDTF;
    private javax.swing.JTextField subjectNameTF;
    private javax.swing.JPanel topPanel;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;


    private detailTable subjectTM;
    private boolean isFinishEditting=false;
    private int selectingRow=-1;
    // End of variables declaration//GEN-END:variables
}
