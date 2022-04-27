package gui.generalGUI;

import pojo.Account;
import utils.HibernateUtil;

import javax.swing.*;
import java.awt.*;

public class mainFrame extends JFrame {
    private Account account;
    public mainFrame()
    {
        HibernateUtil.config();

        //handle if session error
        if(HibernateUtil.getSessionFactory()==null)
        {
            System.out.println("Error in initializing Session Factory!");
            JOptionPane.showMessageDialog(null,"Error connecting to database","Error",JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        setUpDisplay();

    }

    private void setUpDisplay()
    {
        setTitle("Attendance Management System");
        setSize(new Dimension(800,600));
        setLayout(new BorderLayout());
        setDefaultLookAndFeelDecorated(true);
        add(new loginPane(this));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void changePanel(JPanel panel)
    {
        getContentPane().removeAll();
        add(panel);
        setVisible(true);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
