/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

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
    String time = "00:00";
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
    
    private Timer timer;
    private ImageIcon[] frames;
    private int currentFrame = 0;
    private int frameDelay = 300;
    
    public GameView() {
        initComponents();

        btnSubmit.setVisible(false);
        pbgTimer.setVisible(false);
        lbRound.setVisible(false);
        lbProduct.setVisible(false);
        lbScore.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
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

        // Khởi tạo các khung hình từ ảnh riêng lẻ
        frames = new ImageIcon[] {
            new ImageIcon(getClass().getResource("/image/win_1.png")),
            new ImageIcon(getClass().getResource("/image/win_2.png")),
            new ImageIcon(getClass().getResource("/image/win_3.png")),
            new ImageIcon(getClass().getResource("/image/win_4.png")),
            new ImageIcon(getClass().getResource("/image/win_5.png")),
            new ImageIcon(getClass().getResource("/image/win_6.png")),
            new ImageIcon(getClass().getResource("/image/win_7.png")),
        };

        // Cài đặt Timer để chuyển khung hình
        timer = new Timer(frameDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel5.setIcon(frames[currentFrame]);
                currentFrame = (currentFrame + 1) % frames.length; // Lặp lại từ khung hình đầu
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
        String message;
        if (winner.equals("DRAW")) {
            message = "The game ended in a draw!\nFinal Score: " + finalScore;
        } else if (winner.equals(ClientRun.getSocketHandler().getLoginUser())) {
            message = "Congratulations! You won!\nFinal Score: " + finalScore;
        } else {
            message = "You lost. Better luck next time!\nFinal Score: " + finalScore;
        }
        
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        
        // Reset the game state or close the game view as needed
        ClientRun.closeScene(ClientRun.SceneName.GAMEVIEW);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Game Đoán Giá");

        infoPLayer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        infoPLayer.setText("Play game with:");

        btnLeaveGame.setBackground(new java.awt.Color(255, 51, 51));
        btnLeaveGame.setForeground(new java.awt.Color(255, 255, 255));
        btnLeaveGame.setText("Rời trận đấu");
        btnLeaveGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveGameActionPerformed(evt);
            }
        });

        pbgTimer.setStringPainted(true);

        btnStart.setText("Bắt đầu");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        lbWaiting.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbWaiting.setText("Đợi chủ phòng bắt đầu....");

        lbScore.setText("Điểm số: Bạn 0 - 0 Đối thủ");

        lbRound.setText("Vòng:");

        lbProduct.setText("Product:");

        jLabel1.setText("Price :");

        jLabel2.setText("P1");

        jLabel3.setText("P2");

        jLabel4.setText("Winner");

        btnSubmit.setText("Gửi đáp án");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        tfGuess.setText("Guess:");
        tfGuess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfGuessActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/win_1.png"))); // NOI18N

        jLayeredPane1.setLayer(lbProduct, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(btnSubmit, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(tfGuess, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(lbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(tfGuess, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbProduct))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jLabel4))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel3))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(tfGuess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel2))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(infoPLayer, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbRound, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154)
                .addComponent(btnLeaveGame, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addGap(40, 40, 40))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(pbgTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lbWaiting, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lbScore, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoPLayer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lbRound))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnLeaveGame, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(pbgTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnStart))
                    .addComponent(lbWaiting))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(lbScore)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pbgTimer.getAccessibleContext().setAccessibleName("");

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
        Executors.newCachedThreadPool().submit(new Runnable() {
            public void run() {
                new GameView().setVisible(true);
            }
        });
        // java.awt.EventQueue.invokeLater(new Runnable() {
        //     public void run() {
        //         new GameView().setVisible(true);
        //     }
        // });
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
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lbProduct;
    private javax.swing.JLabel lbRound;
    private javax.swing.JLabel lbScore;
    private javax.swing.JLabel lbWaiting;
    public static javax.swing.JProgressBar pbgTimer;
    private javax.swing.JTextField tfGuess;
    // End of variables declaration//GEN-END:variables
}
