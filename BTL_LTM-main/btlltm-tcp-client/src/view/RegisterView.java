/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import run.ClientRun;
import javax.swing.border.*;
/**
 *
 * @author admin
 */
public class RegisterView extends javax.swing.JFrame {
    /**
     * Creates new form RegisterView
     */
    public RegisterView() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/icon.png"));
        setIconImage(icon.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        tfPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        btnChangeLogin = new javax.swing.JButton();
        tfUsername = new javax.swing.JTextField();
        btnRegister = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfConfirmPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game Đoán Giá");

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPasswordActionPerformed(evt);
            }
        });
        jLayeredPane1.add(tfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 336, 30));

        jLabel3.setText("Xác nhận mật khẩu");
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, 30));

        btnChangeLogin.setBackground(new java.awt.Color(204, 204, 204));
        btnChangeLogin.setText("Đăng nhập");
        btnChangeLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeLoginActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnChangeLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 390, 110, -1));
        jLayeredPane1.add(tfUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 336, 31));

        btnRegister.setBackground(new java.awt.Color(255, 102, 51));
        btnRegister.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnRegister.setText("Đăng ký");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 145, 35));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setText("Đăng ký");
        jLayeredPane1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, 71));

        jLabel2.setText("Tên người dùng");
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, 30));

        jLabel4.setText("Mật khẩu");
        jLayeredPane1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 74, 30));

        tfConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfConfirmPasswordActionPerformed(evt);
            }
        });
        jLayeredPane1.add(tfConfirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 336, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel7.setText("Game Đoán Giá");
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 300, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/register.png"))); // NOI18N
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/BG.png"))); // NOI18N
        jLayeredPane1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPasswordActionPerformed

    private void btnChangeLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeLoginActionPerformed
        this.dispose();
        ClientRun.openScene(ClientRun.SceneName.LOGIN);
    }//GEN-LAST:event_btnChangeLoginActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        String userName = tfUsername.getText();
        String password = tfPassword.getText();
        String confirmPassword = tfConfirmPassword.getText();

        if (userName.equals("")) {
                tfUsername.grabFocus();
        } else if (password.equals("")) {
                tfPassword.grabFocus();
        } else if (confirmPassword.equals("")) {
                tfConfirmPassword.grabFocus();
        } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(btnRegister, "Mật khẩu xác nhận không khớp!");
                tfConfirmPassword.grabFocus();
        } else {
           ClientRun.getSocketHandler().register(userName, password);
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void tfConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfConfirmPasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeLogin;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPasswordField tfConfirmPassword;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
