/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import run.ClientRun;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author admin
 */
public class HomeView extends javax.swing.JFrame {
    String loginUserName = "";
    String statusCompetitor = "";
    Vector vdata;
    Vector vheader;
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // Gọi renderer mặc định
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Kiểm tra nếu là hàng được chọn thì bỏ màu nền
            if (isSelected) {
                cell.setBackground(table.getBackground()); // Đặt màu nền giống màu nền bảng
                cell.setForeground(table.getForeground()); // Đặt màu chữ giống màu chữ bảng
            }
            return cell;
        }
    };
    /**
     * Creates new form HomeView
     */
    public void getUserOnline() {
        
    }
    
    public HomeView() {
        initComponents();
        btnGetInfo.setVisible(false);
        btnPlay.setVisible(false);
        Color backgroundColor = new Color(243, 219, 149, 255);
        tblUser.setBackground(backgroundColor);
        jScrollPane2.setBackground(backgroundColor);
        jScrollPane2.getViewport().setBackground(backgroundColor);
        jScrollPane2.setColumnHeaderView(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/icon.png"));
        setIconImage(icon.getImage());
        setupWindowListener();
        tblUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Kiểm tra xem có hàng nào được chọn
                int selectedRow = tblUser.getSelectedRow();
                if (selectedRow >= 0) {
                    // Tính vị trí của hàng được chọn trong bảng
                    Rectangle rowRect = tblUser.getCellRect(selectedRow, 0, true);
                    int rowHeight = rowRect.height;
                    Point tableLocationOnScreen = SwingUtilities.convertPoint(tblUser, rowRect.getLocation(), getContentPane());

                    // Cập nhật vị trí và kích thước của các nút
                    btnGetInfo.setBounds(tableLocationOnScreen.x + 90, tableLocationOnScreen.y, 50, rowHeight);
                    btnPlay.setBounds(tableLocationOnScreen.x + 145, tableLocationOnScreen.y, 50, rowHeight);
                    btnGetInfo.setVisible(true);
                    btnPlay.setVisible(true);
                } else {
                    // Ẩn nút nếu không có hàng nào được chọn
                    btnGetInfo.setVisible(false);
                    btnPlay.setVisible(false);
                }
            }
        });
    }
    
    public void setStatusCompetitor (String status) {
        statusCompetitor = status;
    }
    
    public class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Vector data, Vector columnNames) {
            super(data, columnNames); // Gọi constructor của lớp cha
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Tắt khả năng chỉnh sửa
        }
    }
    
    public void setListUser(Vector vdata, Vector vheader) {
        this.vdata = vdata;
        this.vheader = vheader;
        updateListUser();
    }
    
    
    public void updateListUser() {
        tblUser.setModel(new NonEditableTableModel(vdata, vheader));
        tblUser.getColumnModel().getColumn(0).setCellRenderer(renderer);
    }
    
    public void resetTblUser () {
        DefaultTableModel dtm = (DefaultTableModel) tblUser.getModel();
        dtm.setRowCount(0);
    }
    
    public void setUsername(String username) {
        infoUsername.setText("Tên người dùng: " + username);
        this.loginUserName = username;
    }
    
    public void setUserScore(float score) {
        infoUserScore.setText("Điểm số: " + score);
    }
    
    public void setUserScore1(String win) {
        infoUserScore1.setText("Số trận thắng: " + win);
    }
    
    public void setUserScore2(String draw) {
        infoUserScore2.setText("Số trận hòa: " + draw);
    }
    
    public void setUserScore3(String lose) {
        infoUserScore3.setText("Số trận thua: " + lose);
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
        btnGetInfo = new javax.swing.JButton();
        btnPlay = new javax.swing.JButton();
        btnMessage = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnLeaderboard = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        infoUsername = new javax.swing.JLabel();
        infoUserScore = new javax.swing.JLabel();
        infoUserScore1 = new javax.swing.JLabel();
        infoUserScore2 = new javax.swing.JLabel();
        infoUserScore3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnLeaderboard1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game Đoán Giá");
        setResizable(false);

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGetInfo.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnGetInfo.setText("Info");
        btnGetInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetInfoActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnGetInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, -1, 30));

        btnPlay.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnPlay.setText("Mời");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 60, 30));

        btnMessage.setText("Nhắn tin");
        btnMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMessageActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 450, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Danh sách người dùng online");
        jLayeredPane1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 170, -1));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblUser.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblUser.setName(""); // NOI18N
        tblUser.setOpaque(false);
        tblUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblUser);

        jLayeredPane1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 90, 200));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/right_board.png"))); // NOI18N
        jLayeredPane1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 260, 280));

        btnLogout.setBackground(new java.awt.Color(255, 51, 0));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(204, 255, 255));
        btnLogout.setText("Đăng xuất");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, -1, 30));

        btnExit.setBackground(new java.awt.Color(255, 51, 0));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(204, 255, 255));
        btnExit.setText("THOÁT ");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 80, 34));

        btnLeaderboard.setText("Bảng xếp hạng");
        btnLeaderboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaderboardActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnLeaderboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, -1, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        infoUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        infoUsername.setText("Tên người dùng:");

        infoUserScore.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        infoUserScore.setText("Điểm số:");

        infoUserScore1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        infoUserScore1.setText("Số trận thắng:");

        infoUserScore2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        infoUserScore2.setText("Số trận hòa:");

        infoUserScore3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        infoUserScore3.setText("Số trận thua:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Thông tin người dùng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(infoUserScore, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoUserScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoUserScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoUserScore3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 48, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoUserScore, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoUserScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoUserScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoUserScore3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 220, 220));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/left_board.png"))); // NOI18N
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 260, -1));

        btnLeaderboard1.setText("Lịch sử đấu");
        btnLeaderboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaderboard1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnLeaderboard1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel7.setText("Game Đoán Giá");
        jLayeredPane1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 300, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/BG.png"))); // NOI18N
        jLayeredPane1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "Bạn chưa chọn ai để gửi lời mời. Hãy thực hiện lại!" , "LỖI", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            
            // check user online/in game
            ClientRun.getSocketHandler().checkStatusUser(userSelected);
            while (statusCompetitor == "") {
                ClientRun.getSocketHandler().checkStatusUser(userSelected);
            }
            switch (statusCompetitor) {
                case "ONLINE" -> ClientRun.getSocketHandler().inviteToPlay(userSelected);
                case "OFFLINE" -> JOptionPane.showMessageDialog(HomeView.this, userSelected + "hiện đang offline." , "LỖI", JOptionPane.ERROR_MESSAGE);
                case "INGAME" -> JOptionPane.showMessageDialog(HomeView.this, userSelected + "hiện đang trong trận đấu." , "LỖI", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateListUser();
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMessageActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "Bạn chưa chọn ai để nhắn tin. Hãy thực hiện lại!" , "LỖI", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            System.out.println(userSelected);
            if (userSelected.equals(ClientRun.getSocketHandler().getLoginUser())) {
                JOptionPane.showMessageDialog(HomeView.this, "You can not chat yourself." , "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
               ClientRun.getSocketHandler().inviteToChat(userSelected);
            }
        }
    }//GEN-LAST:event_btnMessageActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        JFrame frame = new JFrame("Đăng xuất");
        if (JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn đăng xuất không?", "Đăng xuất", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            ClientRun.getSocketHandler().logout();
            
        } 
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnGetInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetInfoActionPerformed
        int row = tblUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(HomeView.this, "Bạn chưa chọn ai để xem thông tin. Hãy thực hiện lại!" , "LỖI", JOptionPane.ERROR_MESSAGE);
        } else {
            String userSelected = String.valueOf(tblUser.getValueAt(row, 0));
            System.out.println(userSelected);
            if (userSelected.equals(ClientRun.getSocketHandler().getLoginUser())) {
                JOptionPane.showMessageDialog(HomeView.this, "You can not see yourself." , "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
               ClientRun.getSocketHandler().getInfoUser(userSelected);
            }
        }
        updateListUser();
    }//GEN-LAST:event_btnGetInfoActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        JFrame frame = new JFrame("THOÁT");
        if (JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn thoát không?", "THOÁT", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            ClientRun.getSocketHandler().close();
            System.exit(0);
        } 
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLeaderboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaderboardActionPerformed
        // TODO add your handling code here:
        ClientRun.getSocketHandler().requestLeaderboard();
    }//GEN-LAST:event_btnLeaderboardActionPerformed

    private void btnLeaderboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaderboard1ActionPerformed
        // TODO add your handling code here:
        ClientRun.getSocketHandler().requestHistoryGame(loginUserName);
    }//GEN-LAST:event_btnLeaderboard1ActionPerformed
    private void setupWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ClientRun.getSocketHandler().close();
            }
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGetInfo;
    private javax.swing.JButton btnLeaderboard;
    private javax.swing.JButton btnLeaderboard1;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMessage;
    private javax.swing.JButton btnPlay;
    private javax.swing.JLabel infoUserScore;
    private javax.swing.JLabel infoUserScore1;
    private javax.swing.JLabel infoUserScore2;
    private javax.swing.JLabel infoUserScore3;
    private javax.swing.JLabel infoUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblUser;
    // End of variables declaration//GEN-END:variables
}

