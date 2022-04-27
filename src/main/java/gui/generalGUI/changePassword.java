package gui.generalGUI;

import dao.accountDAO;
import gui.adminGUI.menuPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class changePassword extends javax.swing.JPanel {

    private gui.generalGUI.mainFrame mainFrame;
    public changePassword(gui.generalGUI.mainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        setUpAction();
    }



    private void jButton1ActionPerformed(ActionEvent evt) {
    }
    private void initComponents() {

        txt_OldPass = new javax.swing.JTextField();
        txt_Pass = new javax.swing.JTextField();
        txt_RePass = new javax.swing.JTextField();
        btn_changePassword = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        title = new javax.swing.JLabel();
        title.setText("Change Password");
        title.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N

        txt_OldPass.setName("Old password"); // NOI18N

        txt_Pass.setName("New Password"); // NOI18N

        txt_RePass.setName("Re-enter Password"); // NOI18N

        btn_changePassword.setText("Change password");
        btn_changePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doiMatKhauActionPerformed(evt);
            }
        });
        btn_Cancel.setText("Cancel");
        btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HuyActionPerformed(evt);
            }
        });

        jLabel1.setText("Old password");

        jLabel2.setText("New password");

        jLabel3.setText("Re-enter password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btn_changePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txt_OldPass)
                                        .addComponent(txt_Pass)
                                        .addComponent(txt_RePass))
                                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_OldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_RePass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_changePassword)
                                        .addComponent(btn_Cancel))
                                .addContainerGap(77, Short.MAX_VALUE))
        );
        add(jPanel1);

    }

    private void btn_doiMatKhauActionPerformed(ActionEvent evt) {
    }

    private void setUpAction() {
        boolean isActive=mainFrame.getAccount().getIsactive();
        btn_Cancel.setEnabled(isActive);
        btn_Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isAdmin = mainFrame.getAccount().getIsadmin();
                if (isAdmin) {
                    mainFrame.changePanel(new menuPane(mainFrame));
                }
                else {
                    mainFrame.changePanel(new gui.studentGUI.MenuPane(mainFrame));
                }
            }
        });
        btn_changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPass = txt_OldPass.getText();
                String newPass = txt_Pass.getText();
                String reNewPass = txt_RePass.getText();
                if(newPass.equals(reNewPass)==false)
                {
                    JOptionPane.showMessageDialog(null, "Your password is not the same","Warning", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //check old password
                if(mainFrame.getAccount().getPassword().equals(accountDAO.hashPassword(oldPass))==false)
                {
                    JOptionPane.showMessageDialog(null,
                            "The old password is not correct!", "Warning", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                accountDAO.updatePass(mainFrame.getAccount(),newPass);
                JOptionPane.showMessageDialog(null,
                        "The password is changed successfully", "Warning", JOptionPane.INFORMATION_MESSAGE);
                txt_Pass.setText("");
                txt_RePass.setText("");
                txt_OldPass.setText("");
                btn_Cancel.setEnabled(true);
            }
        });
    }
    private void btn_HuyActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling cod    private javax.swing.JPanel jPanel1;e here:
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_changePassword;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txt_Pass;
    private javax.swing.JTextField txt_RePass;
    private javax.swing.JTextField txt_OldPass;
    // End of variables declaration

}
