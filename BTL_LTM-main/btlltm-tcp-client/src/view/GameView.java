/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.concurrent.Callable;

import run.ClientRun;
import helper.*;
import java.util.Enumeration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;


/**
 *
 * @author admin
 */
public class GameView extends javax.swing.JFrame {
    String competitor = "";
    CountDownTimer matchTimer;
    CountDownTimer matchTimerRound;
    int currentRound = 0;
    int maxRounds = 10;
    float playerScore = 0;
    int playerGuess = 0;
    float competitorScore = 0;
    int competitorGuess = 0;
    String currentProduct = "";
    int currentPrice = 0;
    
    boolean answer = false;
    boolean isSubmit = false;
    
    public GameView() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/icon.png"));
        setIconImage(icon.getImage());
        btnSubmit.setVisible(false);
        pbgTimer.setVisible(false);
        lbRound.setVisible(false);
        lbProduct.setVisible(false);
        lbScore.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        //jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        tfGuess.setVisible(false);
        // close window event
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(GameView.this, "Bạn sẽ thua nếu rời trận đấu. Có chắc chắn muốn rời không?", "RỜI TRẬN ĐẤU", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                    ClientRun.getSocketHandler().leaveGame(competitor);
                    ClientRun.getSocketHandler().setRoomIdPresent(null);
                    dispose();
                } 
            }
        });      
    }
    
    public void setInfoPlayer (String username) {
        competitor = username;
        infoPLayer.setText("Chơi với: " + username);
    }
    
    public void setStateHostRoom () {
        answer = false;
        btnStart.setVisible(true);
        lbWaiting.setVisible(false);
    }
    
    public void setStateUserInvited () {
        answer = false;
        btnStart.setVisible(false);
        lbWaiting.setVisible(true);
    }
    
    public void afterSubmit() {
        btnSubmit.setVisible(false);
        tfGuess.setVisible(false);
        lbWaiting.setText("Đợi đối thủ...");
        lbWaiting.setVisible(true);
    }
    
    public void setStartGame () {
        answer = false;
        
        btnStart.setVisible(false);
        lbWaiting.setVisible(false);
        btnSubmit.setVisible(true);
        pbgTimer.setVisible(true);
        lbProduct.setVisible(true);
        lbScore.setVisible(true);
        tfGuess.setVisible(true);
    }
    
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
    
    public void setCurrentProduct(String product) {
        this.currentProduct = product;
        lbProduct.setText("Tên sản phẩm: " + product);
    }
    
    public void setCurrentPrice(int price) {
        this.currentPrice = price;
    }
    
    public void startNewRound(int matchTimeLimit) {
        currentRound++;
        lbRound.setText("Vòng: " + currentRound + "/" + maxRounds);
        lbRound.setVisible(true);
        tfGuess.setVisible(true);
        btnSubmit.setVisible(true);
        lbWaiting.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        tfGuess.setText("");
        isSubmit = false;
        matchTimer = new CountDownTimer(matchTimeLimit);
        matchTimer.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pbgTimer.setValue(100 * matchTimer.getCurrentTick() / matchTimer.getTimeLimit());
                    pbgTimer.setString("" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick()));
                    if (pbgTimer.getString().equals("00:00")) {
                        if (!isSubmit) {
                            afterSubmit();                            
                        }
                        matchTimer.pause();
                    }
                    return null;
                },
                // tick interval
                1
        );
    }

    public void showRoundResult(String winner, int actualPrice, int playerGuess, int competitorGuess, float playerScore, float competitorScore) {
        this.playerScore = playerScore;
        this.competitorScore = competitorScore;
        lbScore.setText("Điểm số: Bạn " + playerScore + " - " + competitorScore + " Đối thủ");
        
        jLabel1.setText("Giá của sản phẩm: " + actualPrice);
        jLabel2.setText("Giá đoán của bạn: " + playerGuess);
        jLabel3.setText("Giá đoán của đối thủ: " + competitorGuess);
        if (winner.equals("DRAW")) {
            jLabel4.setText("Hòa");
        }
        else {
            jLabel4.setText("Người chiến thắng: " + winner);
        }
        lbWaiting.setVisible(false);
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(true);
        matchTimerRound = new CountDownTimer(5);
        matchTimerRound.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pbgTimer.setValue(100 * matchTimerRound.getCurrentTick() / matchTimerRound.getTimeLimit());
                    pbgTimer.setString("" + CustumDateTimeFormatter.secondsToMinutes(matchTimerRound.getCurrentTick()));
                    if (pbgTimer.getString().equals("00:00")) {
                        matchTimerRound.pause();
                    }
                    return null;
                },
                // tick interval
                1
        );
    }

    public void showGameEnd(String winner, float finalScore) {
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        lbProduct.setVisible(false);
        if (winner.equals(ClientRun.getSocketHandler().getLoginUser())) {
            jLabel5.setVisible(true);
        } else {
            jLabel6.setVisible(true);
        }    
        matchTimer = new CountDownTimer(10);
        matchTimer.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pbgTimer.setValue(100 * matchTimer.getCurrentTick() / matchTimer.getTimeLimit());
                    pbgTimer.setString("" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick()));
                    if (pbgTimer.getString().equals("00:00")) {
                        matchTimer.cancel();
                        ClientRun.getSocketHandler().getInfo();
                        ClientRun.closeScene(ClientRun.SceneName.GAMEVIEW);
                    }
                    return null;
                },
                // tick interval
                1
        );       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        infoPLayer = new javax.swing.JLabel();
        btnLeaveGame = new javax.swing.JButton();
        pbgTimer = new javax.swing.JProgressBar();
        btnStart = new javax.swing.JButton();
        lbWaiting = new javax.swing.JLabel();
        lbScore = new javax.swing.JLabel();
        lbRound = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        lbProduct = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        tfGuess = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Game Đoán Giá");

        jLayeredPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        infoPLayer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        infoPLayer.setText("Chơi với:");
        jLayeredPane2.add(infoPLayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 24, 263, 34));

        btnLeaveGame.setBackground(new java.awt.Color(255, 51, 51));
        btnLeaveGame.setForeground(new java.awt.Color(255, 255, 255));
        btnLeaveGame.setText("Rời trận đấu");
        btnLeaveGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveGameActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnLeaveGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(589, 27, 120, 34));

        pbgTimer.setStringPainted(true);
        jLayeredPane2.add(pbgTimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 79, 687, 27));
        pbgTimer.getAccessibleContext().setAccessibleName("");

        btnStart.setText("Bắt đầu");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        jLayeredPane2.add(btnStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 120, 98, -1));

        lbWaiting.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbWaiting.setText("Đợi chủ phòng bắt đầu....");
        jLayeredPane2.add(lbWaiting, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 118, 336, -1));

        lbScore.setText("Điểm số: Bạn 0 - 0 Đối thủ");
        jLayeredPane2.add(lbScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 477, 435, -1));

        lbRound.setText("Vòng:");
        jLayeredPane2.add(lbRound, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 35, 131, -1));

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbProduct.setText("Product:");
        jLayeredPane1.setLayer(lbProduct, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(lbProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 6, 172, -1));

        jLabel1.setText("Price :");
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 28, 172, -1));

        jLabel2.setText("P1");
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 172, -1));

        jLabel3.setText("P2");
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 72, 172, -1));

        jLabel4.setText("Winner");
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 186, 172, -1));

        btnSubmit.setText("Gửi đáp án");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        jLayeredPane1.setLayer(btnSubmit, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 136, 106, 29));

        tfGuess.setText("Guess:");
        tfGuess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfGuessActionPerformed(evt);
            }
        });
        jLayeredPane1.setLayer(tfGuess, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(tfGuess, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 96, 172, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/win.gif"))); // NOI18N
        jLayeredPane1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 480, 319));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/lose.gif"))); // NOI18N
        jLayeredPane1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, 480, 270));

        jLayeredPane2.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 146, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLeaveGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveGameActionPerformed
        if (JOptionPane.showConfirmDialog(GameView.this, "Bạn sẽ thua nếu rời trận đấu. Có chắc chắn muốn rời không?", "RỜI TRẬN ĐẤU", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            ClientRun.getSocketHandler().leaveGame(competitor);
            ClientRun.getSocketHandler().setRoomIdPresent(null);
            dispose();
        } 
    }//GEN-LAST:event_btnLeaveGameActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        ClientRun.getSocketHandler().startGame(competitor);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int guess = Integer.parseInt(tfGuess.getText());
            ClientRun.getSocketHandler().submitGuess(guess);
            afterSubmit();
            isSubmit = true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tfGuessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfGuessActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfGuessActionPerformed

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
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameView().setVisible(true);
            }
        });
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLeaveGame;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnSubmit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JLabel infoPLayer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLabel lbProduct;
    private javax.swing.JLabel lbRound;
    private javax.swing.JLabel lbScore;
    private javax.swing.JLabel lbWaiting;
    public static javax.swing.JProgressBar pbgTimer;
    private javax.swing.JTextField tfGuess;
    // End of variables declaration//GEN-END:variables
}
