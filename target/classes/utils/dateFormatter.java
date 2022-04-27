package utils;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dateFormatter extends JFormattedTextField.AbstractFormatter {
    @Override
    public Object stringToValue(String text) throws ParseException {

        return null;
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if(value!=null)
        {
            Calendar cal=(Calendar) value;
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String strdate=format.format(cal.getTime());
            return strdate;
        }

        return "";
    }
}
