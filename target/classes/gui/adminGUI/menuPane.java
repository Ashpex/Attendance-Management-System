/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui.adminGUI;

import gui.generalGUI.loginPane;
import gui.generalGUI.changePassword;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuPane extends javax.swing.JPanel {

    private gui.generalGUI.mainFrame mainFrame;
    public menuPane(gui.generalGUI.mainFrame mainFrame) {
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        jLabel1 = new javax.swing.JLabel();

        subjectManageBtn = new javax.swing.JButton();
        classManageBtn = new javax.swing.JButton();
        changePassBtn = new javax.swing.JButton();
        signOutBtn = new javax.swing.JButton();
        adminManageBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(Color.WHITE);
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(336, 125));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(label, java.awt.BorderLayout.CENTER);
        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        jPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        ImageIcon subjectIcon = new ImageIcon(getClass().getResource("/images/subject.png"));
        subjectManageBtn.setText("  Subject Mangement");
        subjectManageBtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        subjectManageBtn.setIcon(subjectIcon);


        ImageIcon classManageIcon = new ImageIcon(getClass().getResource("/images/attendance.png"));
        classManageBtn.setText("  Attendance Management");
        classManageBtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        classManageBtn.setIcon(classManageIcon);

        ImageIcon changePassIcon = new ImageIcon(getClass().getResource("/images/change-pass-icon.png")) ;
        changePassBtn.setIcon(changePassIcon);
        changePassBtn.setFont(new java.awt.Font("Tahoma", 1, 20));
        changePassBtn.setText("  Change Password");
        changePassBtn.setVerticalAlignment(SwingConstants.CENTER);  // of text and icon
        changePassBtn.setHorizontalAlignment(SwingConstants.CENTER); // of text and icon

        signOutBtn.setText("  Log out");
        signOutBtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        ImageIcon logOutIcon = new ImageIcon(getClass().getResource("/images/logout.png")) ;
        signOutBtn.setIcon(logOutIcon);
        signOutBtn.setVerticalAlignment(SwingConstants.CENTER);  // of text and icon
        signOutBtn.setHorizontalAlignment(SwingConstants.CENTER);

        signOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signOutBtnActionPerformed(evt);
            }
        });

        adminManageBtn.setText("Admin Management");
        adminManageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminManageBtnActionPerformed(evt);
            }
        });


        jPanel1.setLayout(new GridLayout(3,3,20,20));
        jPanel1.add(subjectManageBtn);
        jPanel1.add(classManageBtn);
        jPanel1.add(changePassBtn);
        jPanel1.add(signOutBtn);
        //jPanel1.add(adminManageBtn);
        jPanel1.add(jLabel1);
        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void setUpAction()
    {
        subjectManageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new newSubjectPane(mainFrame));
            }
        });
        classManageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.changePanel(new attendancePane(mainFrame));
            }
        });
//        studentAttendBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mainFrame.changePanel(new StudentAttendancePanel(mainFrame));
//            }
//        });
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
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    private void signOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signOutBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_signOutBtnActionPerformed

    private void adminManageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminManageBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminManageBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminManageBtn;
    private javax.swing.JButton changePassBtn;
    private javax.swing.JButton classManageBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton signOutBtn;
    private javax.swing.JButton studentAttendBtn;
    private javax.swing.JButton subjectManageBtn;
    // End of variables declaration//GEN-END:variables
}
