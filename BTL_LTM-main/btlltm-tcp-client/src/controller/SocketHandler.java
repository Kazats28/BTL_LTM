package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import run.ClientRun;

public class SocketHandler {
     
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String loginUser = null; // lưu tài khoản đăng nhập hiện tại
    String roomIdPresent = null; // lưu room hiện tại
    float score = 0;
    Thread listener = null;

    public String connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);
            
            // establish the connection with server port 
            s = new Socket();
            
            s.connect(new InetSocketAddress(ip, port), 4000);
            System.out.println("Connected to " + ip + ":" + port + ", localport:" + s.getLocalPort());

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // close old listener
            if (listener != null && listener.isAlive()) {
                listener.interrupt();
            }

            // listen to server
            listener = new Thread(this::listen);
            listener.start();

            // connect success
            return "success";

        } catch (IOException e) {

            // connect failed
            return "failed;" + e.getMessage();
        }
    }

    private void listen() {
        boolean running = true;

        while (running) {
            try {
                // receive the data from server
                String received = dis.readUTF();

                System.out.println("RECEIVED: " + received);

                String type = received.split(";")[0];

                switch (type) {
                    case "LOGIN":
                        onReceiveLogin(received);
                        break;
                    case "REGISTER":
                        onReceiveRegister(received);
                        break;
                    case "GET_LIST_ONLINE":
                        onReceiveGetListOnline(received);
                        break;
                    case "LOGOUT":
                        onReceiveLogout(received);
                        break;
                    case "INVITE_TO_CHAT":
                        onReceiveInviteToChat(received);
                        break;
                    case "GET_INFO_USER":
                        onReceiveGetInfoUser(received);
                        break;
                    case "GET_INFO":
                        onReceiveGetInfo(received);
                        break;
                    case "ACCEPT_MESSAGE":
                        onReceiveAcceptMessage(received);
                        break;
                    case "NOT_ACCEPT_MESSAGE":
                        onReceiveNotAcceptMessage(received);
                        break;
                    case "LEAVE_TO_CHAT":
                        onReceiveLeaveChat(received);
                        break;
                    case "CHAT_MESSAGE":
                        onReceiveChatMessage(received);
                        break;
                    case "INVITE_TO_PLAY":
                        onReceiveInviteToPlay(received);
                        break;
                    case "ACCEPT_PLAY":
                        onReceiveAcceptPlay(received);
                        break;
                    case "NOT_ACCEPT_PLAY":
                        onReceiveNotAcceptPlay(received);
                        break;
                    case "LEAVE_TO_GAME":
                        onReceiveLeaveGame(received);
                        break;
                    case "CHECK_STATUS_USER":
                        onReceiveCheckStatusUser(received);
                        break;
                    case "REQUEST_LEADERBOARD":
                        onReceiveRequestLeaderboard(received);
                        break;
                    case "REQUEST_HISTORY":
                        onReceiveRequestHistory(received);
                        break;
                    case "START_GAME":
                        onReceiveStartGame(received);
                        break;
                    case "NEW_ROUND":
                        onReceiveNewRound(received);
                        break;
                    case "ROUND_RESULT":
                        onReceiveRoundResult(received);
                        break;
                    case "GAME_END":
                        onReceiveGameEnd(received);
                        break;
                    case "EXIT":
                        running = false;
                }
            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
                running = false;
            }
        }

        try {
            // closing resources
            s.close();
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        // alert if connect interup
        JOptionPane.showMessageDialog(null, "Mất kết nối tới server. Hãy thử lại sau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        ClientRun.closeAllScene();
    }
    
    public void login(String email, String password) {
        // prepare data
        String data = "LOGIN" + ";" + email + ";" + password;
        // send data
        sendData(data);
    }
    
    public void register(String email, String password) {
        // prepare data
        String data = "REGISTER" + ";" + email + ";" + password;
        // send data
        sendData(data);
    }
    
    public void logout () {
        this.loginUser = null;
        sendData("LOGOUT");
    }
    
    public void close () {
        sendData("CLOSE");
    }
    
    public void getInfoUser(String username) {
        sendData("GET_INFO_USER;" + username);
    }
    
    public void checkStatusUser(String username) {
        sendData("CHECK_STATUS_USER;" + username);
    }
    
    // Chat
    public void inviteToChat (String userInvited) {
        sendData("INVITE_TO_CHAT;" + loginUser + ";" + userInvited);
    }
    
    public void leaveChat (String userInvited) {
        sendData("LEAVE_TO_CHAT;" + loginUser + ";" + userInvited);
    }
    
    public void sendMessage (String userInvited, String message) {
        ClientRun.messageView.setContentChat(message + "\n", true);
            
        sendData("CHAT_MESSAGE;" + loginUser + ";" + userInvited  + ";" + message);
    }
    
    // Play game
    public void inviteToPlay (String userInvited) {
        sendData("INVITE_TO_PLAY;" + loginUser + ";" + userInvited);
    }
    
    public void leaveGame (String userInvited) { 
        sendData("LEAVE_TO_GAME;" + loginUser + ";" + userInvited + ";" + roomIdPresent);
    }
    
    public void startGame (String userInvited) { 
        sendData("START_GAME;" + loginUser + ";" + userInvited + ";" + roomIdPresent);
    }

    // get leaderboard
    public void requestLeaderboard() {
        sendData("REQUEST_LEADERBOARD");
    }
    
    public void requestHistoryGame(String user) {
        sendData("REQUEST_HISTORY;" + user);
    }
    
    public void submitGuess(int guess) {
        sendData("SUBMIT_RESULT;" + loginUser + ";" + roomIdPresent + ";" + guess);
    }
    
    public void getInfo() {
        sendData("GET_INFO;" + loginUser);
    }
    
    public void sendData(String data) {
        try {
            dos.writeUTF(data);
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class
                .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void onReceiveLogin(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(ClientRun.loginView, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            this.loginUser = splitted[2];
            this.score = Float.parseFloat(splitted[3]);
            
            // chuyển scene
            ClientRun.closeScene(ClientRun.SceneName.LOGIN);
            ClientRun.openScene(ClientRun.SceneName.HOMEVIEW);

            // auto set info user
            ClientRun.homeView.setUsername(loginUser);
            ClientRun.homeView.setUserScore(score);
            ClientRun.homeView.setUserScore1(splitted[4]);
            ClientRun.homeView.setUserScore2(splitted[5]);
            ClientRun.homeView.setUserScore3(splitted[6]);
        }
    }
    
    private void onReceiveGetInfo(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            // auto set info user
            ClientRun.homeView.setUsername(splitted[2]);
            ClientRun.homeView.setUserScore(Float.parseFloat(splitted[3]));
            ClientRun.homeView.setUserScore1(splitted[4]);
            ClientRun.homeView.setUserScore2(splitted[5]);
            ClientRun.homeView.setUserScore3(splitted[6]);
        }
    }
    
    private void onReceiveRegister(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            // hiển thị lỗi
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(ClientRun.registerView, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            JOptionPane.showMessageDialog(ClientRun.registerView, "Đăng ký tài khoản thành công. Hãy thực hiện đăng nhập!");
            // chuyển scene
            ClientRun.closeScene(ClientRun.SceneName.REGISTER);
            ClientRun.openScene(ClientRun.SceneName.LOGIN);
        }
    }
    
    private void onReceiveGetListOnline(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            int userCount = Integer.parseInt(splitted[2]);

            Vector vheader = new Vector();
            vheader.add("User");

            Vector vdata = new Vector();
            if (userCount > 1) {
                for (int i = 3; i < userCount + 3; i++) {
                    String user = splitted[i];
                    if (!user.equals(loginUser) && !user.equals("null")) {
                        Vector vrow = new Vector();
                        vrow.add(user);
                        vdata.add(vrow);
                    }
                }

                ClientRun.homeView.setListUser(vdata, vheader);
            } else {
                ClientRun.homeView.resetTblUser();
            }
            
        } else {
            JOptionPane.showMessageDialog(ClientRun.loginView, "Đã xảy ra lỗi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onReceiveGetInfoUser(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userName = splitted[2];
            String userScore =  splitted[3];
            String userWin =  splitted[4];
            String userDraw =  splitted[5];
            String userLose =  splitted[6];
            String userStatus = splitted[7];
            
            ClientRun.openScene(ClientRun.SceneName.INFOPLAYER);
            ClientRun.infoPlayerView.setInfoUser(userName, userScore, userWin, userDraw, userLose, userStatus);
        }
    }
    
    private void onReceiveLogout(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            ClientRun.closeScene(ClientRun.SceneName.HOMEVIEW);
            ClientRun.openScene(ClientRun.SceneName.LOGIN);
        }
    }
    
    // chat
    private void onReceiveInviteToChat(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            if (JOptionPane.showConfirmDialog(ClientRun.homeView, userHost + " muốn nhắn tin với bạn. Có đồng ý không?", "Mời nhắn tin", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                ClientRun.openScene(ClientRun.SceneName.MESSAGEVIEW);
                ClientRun.messageView.setInfoUserChat(userHost);
                sendData("ACCEPT_MESSAGE;" + userHost + ";" + userInvited);
            } else {
                sendData("NOT_ACCEPT_MESSAGE;" + userHost + ";" + userInvited);
            }
        }
    }
    
    private void onReceiveAcceptMessage(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
                
            ClientRun.openScene(ClientRun.SceneName.MESSAGEVIEW);
            ClientRun.messageView.setInfoUserChat(userInvited);
        }
    }
    
    private void onReceiveNotAcceptMessage(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
                
            JOptionPane.showMessageDialog(ClientRun.homeView, userInvited + " đã từ chối nhắn tin!");
        }
    }
    
    private void onReceiveLeaveChat(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            
            ClientRun.closeScene(ClientRun.SceneName.MESSAGEVIEW);   
            JOptionPane.showMessageDialog(ClientRun.homeView, userHost + " đã rời cuộc trò chuyện!");
        }
    }
    
    private void onReceiveChatMessage(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            String message = splitted[4];
            
            ClientRun.messageView.setContentChat(message + "\n", false);
        }
    }
    
    // play game
    private void onReceiveInviteToPlay(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            String roomId = splitted[4];
            if (JOptionPane.showConfirmDialog(ClientRun.homeView, userHost + " muốn bắt đầu một trận đấu với bạn. Có đồng ý không?", "Mời chơi", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                ClientRun.openScene(ClientRun.SceneName.GAMEVIEW);
                ClientRun.gameView.setInfoPlayer(userHost);
                roomIdPresent = roomId;
                ClientRun.gameView.setStateUserInvited();
                sendData("ACCEPT_PLAY;" + userHost + ";" + userInvited + ";" + roomId);
            } else {
                sendData("NOT_ACCEPT_PLAY;" + userHost + ";" + userInvited + ";" + roomId);
            }
        }
    }
    
    private void onReceiveAcceptPlay(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            roomIdPresent = splitted[4];
            ClientRun.openScene(ClientRun.SceneName.GAMEVIEW);
            ClientRun.gameView.setInfoPlayer(userInvited);
            ClientRun.gameView.setStateHostRoom();
        }
    }
    
    private void onReceiveNotAcceptPlay(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
                
            JOptionPane.showMessageDialog(ClientRun.homeView, userInvited + " đã từ chối lời mời!");
        }
    }
    
    private void onReceiveStartGame(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {           
            ClientRun.gameView.setStartGame();
        }
    }
    
    private void onReceiveLeaveGame(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String user1 = splitted[2];
            String user2 = splitted[3];

            roomIdPresent = null;
            ClientRun.closeScene(ClientRun.SceneName.GAMEVIEW);   
            JOptionPane.showMessageDialog(ClientRun.homeView, user1 + " đã rời trận đấu!");
        }
    }
     
    private void onReceiveCheckStatusUser(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[2];
        ClientRun.homeView.setStatusCompetitor(status);
    }
    
    private void onReceiveRequestHistory(String received) {
        String[] splitted = received.split(";");
        System.out.println();
        int gameCount = Integer.parseInt(splitted[1]);

        Vector<Vector<Object>> historyData = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Thời gian bắt đầu");
        columnNames.add("Thời gian kết thúc");
        columnNames.add("Kết quả");
        columnNames.add("Điểm bạn");
        columnNames.add("Đối thủ");
        columnNames.add("Điểm đối thủ");
        columnNames.add("Người rời trận");
        
        for (int i = 0; i < gameCount; i++) {
            Vector<Object> row = new Vector<>();
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
            LocalDateTime start = LocalDateTime.parse(splitted[2 + i * 8]);
            String startTime = start.format(outputFormatter);
            LocalDateTime end = LocalDateTime.parse(splitted[3 + i * 8]);
            String endTime = end.format(outputFormatter);
            row.add(startTime);
            row.add(endTime);
            if (loginUser.equals(splitted[4 + i * 8])) {
                row.add("Thắng");
            }
            else if (splitted[4 + i * 8].equals("DRAW")) {
                row.add("Hòa");
            }
            else {
                row.add("Thua");
            }
            if (loginUser.equals(splitted[5 + i * 8])) {
                row.add(splitted[6 + i * 8]);
                row.add(splitted[7 + i * 8]);
                row.add(splitted[8 + i * 8]);
            }
            else if (loginUser.equals(splitted[7 + i * 8])) {
                row.add(splitted[8 + i * 8]);
                row.add(splitted[5 + i * 8]);
                row.add(splitted[6 + i * 8]);
            }           
            row.add(splitted[9 + i * 8]);
            historyData.add(row);
        }

        SwingUtilities.invokeLater(() -> {
            ClientRun.historyView.setHistoryData(historyData, columnNames);
            ClientRun.historyView.setVisible(true);
        });
    }
    
    private void onReceiveRequestLeaderboard(String received) {
        String[] splitted = received.split(";");
        int userCount = Integer.parseInt(splitted[1]);

        Vector<Vector<Object>> leaderboardData = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Hạng");
        columnNames.add("Tên người dùng");
        columnNames.add("Điểm số");

        for (int i = 0; i < userCount; i++) {
            Vector<Object> row = new Vector<>();
            row.add(0); // Placeholder for rank
            row.add(splitted[2 + i * 2]);
            row.add(Float.parseFloat(splitted[3 + i * 2]));
            leaderboardData.add(row);
        }
        // Update ranks
        for (int i = 0; i < leaderboardData.size(); i++) {
            leaderboardData.get(i).set(0, i + 1);
        }

        SwingUtilities.invokeLater(() -> {
            ClientRun.leaderboardView.setLeaderboardData(leaderboardData, columnNames);
            ClientRun.leaderboardView.setVisible(true);
        });
    }
    
    private void onReceiveNewRound(String received) {
        String[] splitted = received.split(";");
        String product = splitted[1];
        int price = Integer.parseInt(splitted[2]);
        String urlImage = splitted[3];
        int minPrice = Integer.parseInt(splitted[4]);
        int maxPrice = Integer.parseInt(splitted[5]);
        ClientRun.gameView.setCurrentProduct(product);
        ClientRun.gameView.setCurrentPrice(price);
        ClientRun.gameView.setUrlImage(urlImage);
        ClientRun.gameView.setMinPrice(minPrice);
        ClientRun.gameView.setMaxPrice(maxPrice);
        ClientRun.gameView.startNewRound(10);
    }
    
    private void onReceiveRoundResult(String received) {
        String[] splitted = received.split(";");
        String winner = splitted[1];
        int actualPrice = Integer.parseInt(splitted[2]);
        String player1 = splitted[3];
        String player2 = splitted[4];
        int playerGuess = 0;
        int competitorGuess = 0;
        float playerScore = 0;
        float competitorScore = 0;
        if (player1.equals(loginUser)) {
            playerGuess = Integer.parseInt(splitted[5]);
            competitorGuess = Integer.parseInt(splitted[6]);
            playerScore = Float.parseFloat(splitted[7]);
            competitorScore = Float.parseFloat(splitted[8]);
        }
        else {
            playerGuess = Integer.parseInt(splitted[6]);
            competitorGuess = Integer.parseInt(splitted[5]);
            playerScore = Float.parseFloat(splitted[8]);
            competitorScore = Float.parseFloat(splitted[7]);
        }        
        ClientRun.gameView.showRoundResult(winner, actualPrice, playerGuess, competitorGuess, playerScore, competitorScore);
    }
    
    private void onReceiveGameEnd(String received) {
        String[] splitted = received.split(";");
        String winner = splitted[1];
        float finalScore = Float.parseFloat(splitted[2]);
        ClientRun.gameView.showGameEnd(winner, finalScore);
    }
    
    // get set
    public String getLoginUser() {
        return loginUser;
    }
    
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public String getRoomIdPresent() {
        return roomIdPresent;
    }

    public void setRoomIdPresent(String roomIdPresent) {
        this.roomIdPresent = roomIdPresent;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
    
}
