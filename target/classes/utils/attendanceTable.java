package utils;

import javax.swing.*;

public class attendanceTable extends JTable {
    private attendanceTableModel tableModel;
    private Class[] classNames;
    private static JComboBox<String> emptyCB;

    public static String ABSENT="ABSENT";
    public static String NOT_CHECKED="NOT CHECKED";
    public static String ATTEND="ATTEND";




    public attendanceTable(Object[][]data, Object[]colNames, Class[] classNames)
    {
        emptyCB=new JComboBox<>();
        emptyCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ABSENT,NOT_CHECKED,ATTEND }));

        attendanceTableModel attendanceTableModel =new attendanceTableModel(data,colNames,classNames);

        tableModel= attendanceTableModel;
        setModel(tableModel);
        this.classNames=classNames;
        //set up cell editor base on column
        for (int i=0;i<classNames.length;i++)
        {
            if(classNames[i]!=Object.class&&getColumnModel().getColumnCount()>0)
            {
                getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(emptyCB));
                getColumnModel().getColumn(i).setCellRenderer(new checkinGenerate());
            }
        }
        System.out.println(tableModel.toString());
    }

    public attendanceTableModel getModel() {
        return tableModel;
    }

    public void setTableModel(attendanceTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public Class[] getClassNames() {
        return classNames;
    }

    public void setClassNames(Class[] classNames) {
        this.classNames = classNames;
        tableModel.setColumnClasses(classNames);

    }

}
