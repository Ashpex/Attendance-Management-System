package gui.studentGUI;

import gui.generalGUI.loginPane;
import gui.generalGUI.changePassword;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MenuPane extends javax.swing.JPanel {

    private gui.generalGUI.mainFrame mainFrame;

    public MenuPane(gui.generalGUI.mainFrame mainFrame) {
        this.mainFrame=mainFrame;
        initComponents();
        setUpAction();
    }

    private void initComponents() {
        JLabel label = new JLabel("Hello, " + mainFrame.getAccount().getUsername());
        label.setForeground(Color.BLACK);
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();

        setLayout(new java.awt.BorderLayout());
        jPanel2.setBackground(Color.WHITE);
        jPanel2.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(336, 125));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(label, java.awt.BorderLayout.CENTER);
        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        checkInBtn = new javax.swing.JButton();
        attendResultBtn = new javax.swing.JButton();
        changePassBtn = new javax.swing.JButton();
        signOutBtn = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        jPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

/*
        setBackground(new java.awt.Color(255, 255, 255));
*/

        ImageIcon checkInIcon = new ImageIcon(getClass().getResource("/images/checkin.png"));
        checkInBtn.setIcon(checkInIcon);
        checkInBtn.setText("  Check In");
        checkInBtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        ImageIcon attendIcon = new ImageIcon(getClass().getResource("/images/history.png"));
        attendResultBtn.setIcon(attendIcon);
        attendResultBtn.setText("  Attendance History");
        attendResultBtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        ImageIcon changePassIcon = new ImageIcon(getClass().getResource("/images/change-pass-icon.png")) ;
        changePassBtn.setIcon(changePassIcon);
        changePassBtn.setText("  Change Password");
        changePassBtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        changePassBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new changePassword(mainFrame));
            }
        });

        signOutBtn.setText("  Log Out");
        signOutBtn.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        ImageIcon logOutIcon = new ImageIcon(getClass().getResource("/images/logout.png")) ;
        signOutBtn.setIcon(logOutIcon);
        signOutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signOutBtnActionPerformed(e);
            }
        });
        jPanel1.setLayout(new GridLayout(3,3,20,20));
        jPanel1.add(checkInBtn);
        jPanel1.add(attendResultBtn);
        jPanel1.add(changePassBtn);
        jPanel1.add(signOutBtn);

        add(jPanel1, java.awt.BorderLayout.CENTER);


    }

    private void signOutBtnActionPerformed(ActionEvent e) {
    }

    private void setUpAction()
    {
        attendResultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new HistoryPane(mainFrame));
            }
        });
        checkInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new CheckInPanel(mainFrame));
            }
        });
        signOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new loginPane(mainFrame));
                mainFrame.setAccount(null);
            }
        });
        changePassBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new changePassword(mainFrame));
            }
        });
    }


    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton attendResultBtn;
    private javax.swing.JButton changePassBtn;
    private javax.swing.JButton checkInBtn;
    private javax.swing.JButton signOutBtn;
    // End of variables declaration//GEN-END:variables
}
