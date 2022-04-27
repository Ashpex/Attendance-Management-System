package utils;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class detailTable extends DefaultTableModel
{
    private int rowEditable=-1;
    private boolean dataEdited=true;
    public detailTable() {
    }

    public detailTable(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public detailTable(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public detailTable(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public detailTable(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    public detailTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        //prevent subjectid editable
        if(column==0)
            return false;
        return row==rowEditable;
    }
    public void setRowEditable(int row)
    {
        rowEditable=row;
    }

    public boolean isDataEdited() {
        return dataEdited;
    }

    public void setDataEdited(boolean dataEdited) {
        this.dataEdited = dataEdited;
    }

    public Object[] getRowData(int row)
    {
        Object[] ret=new Object[getColumnCount()];
        for(int i=0;i<getColumnCount();i++)
            ret[i]=getValueAt(row,i);
        return ret;
    }

    public void updateRowData(int row,Object[] data)
    {
        for(int i=0;i<getColumnCount();i++)
        {
            setValueAt(data[i],row,i);
        }
    }
}
