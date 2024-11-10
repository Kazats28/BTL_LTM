package service;

import controller.GameController;
import controller.RoundController;
import controller.UserController;
import helper.CountDownTimer;
import helper.CustumDateTimeFormatter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import model.UserModel;
import run.ServerRun;
import java.util.Random;
import model.GameModel;
import model.ProductModel;

public class Room {
    String id;
    String time = "00:00";
    Client client1 = null, client2 = null;
    ArrayList<Client> clients = new ArrayList<>();
    
    boolean gameStarted = false;
    CountDownTimer matchTimer;
    CountDownTimer matchTimerRound;
    
    String resultClient1;
    String resultClient2;
    
    int gameId;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    private int currentRound = 0;
    private final int maxRounds = 10;
    private String currentProduct;
    private String currentImage;
    private String winner;
    private int currentPrice;
    private int minPrice;
    private int maxPrice;
    private int player1Guess;
    private int player2Guess;
    private float player1Score = 0;
    private float player2Score = 0;
    List<ProductModel> allProducts = new GameController().getAllProducts();
    public Room(String id) {
        // room id
        this.id = id;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startGame() throws SQLException {
        gameStarted = true;       
        startNewRound();
        startTime = LocalDateTime.now();
        gameId = new GameController().insertGame(startTime, client1.getLoginUser(), client2.getLoginUser());
        
    }
    
    private void startNewRound() throws SQLException {
        currentRound++;
        if (currentRound <= maxRounds) {
            getRandomProduct();
            broadcast("NEW_ROUND;" + currentProduct + ";" + currentPrice + ";" + currentImage + ";" + minPrice + ";" + maxPrice);
            resetRoundData();
            matchTimer = new CountDownTimer(10);
            matchTimer.setTimerCallBack(
                null,
                (Callable) () -> {
                    time = "" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick());
                    System.out.println(time);
                    if (time.equals("00:00")) {
                        endRound();
                    }
                    return null;
                },
                1
            );
        } else {
            endGame();
        }
    }
    
    private void endRound() {
        matchTimer.pause();
        calculateRoundResult();
        matchTimerRound = new CountDownTimer(5);
        matchTimerRound.setTimerCallBack(
            null,
            (Callable) () -> {
                time = "" + CustumDateTimeFormatter.secondsToMinutes(matchTimerRound.getCurrentTick());
                System.out.println(time);
                if (time.equals("00:00")) {
                    startNewRound();
                    matchTimerRound.pause();
                }
                return null;
            },
            1
        );
        new RoundController().insertRound(gameId, currentRound, winner, currentProduct, currentPrice, player1Guess, player2Guess);
    }
    
    private void endGame() throws SQLException {
        gameStarted = false;
        String winnerGame = (player1Score > player2Score) ? client1.getLoginUser() : 
                        (player2Score > player1Score) ? client2.getLoginUser() : "DRAW";
        float finalScore = Math.max(player1Score, player2Score);
        broadcast("GAME_END;" + winnerGame + ";" + finalScore);
        updateUserScores(winnerGame);
        endTime = LocalDateTime.now();
        GameModel game = new GameController().getGame(gameId);
        game.setEndTime(endTime);
        game.setWinner(winnerGame);
        game.setScore1(player1Score);
        game.setScore2(player2Score);
        game.setUserLeaveGame(" ");
        new GameController().updateGame(game, gameId);
        matchTimer = new CountDownTimer(10);
        matchTimer.setTimerCallBack(
            null,
            (Callable) () -> {
                time = "" + CustumDateTimeFormatter.secondsToMinutes(matchTimer.getCurrentTick());
                System.out.println(time);
                if (time.equals("00:00")) {
                    matchTimer.pause();
                    deleteRoom();
                }
                return null;
            },
            1
        );
    }
    
    private void resetRoundData() {
        player1Guess = 0;
        player2Guess = 0;
        resultClient1 = null;
        resultClient2 = null;
    }
    
    public void handleGuess(Client client, int guess) {
        if (client == client1) {
            player1Guess = guess;
            resultClient1 = "SUBMITTED";
        } else {
            player2Guess = guess;
            resultClient2 = "SUBMITTED";
        }

        if (resultClient1 != null && resultClient2 != null) {
            endRound();
        }
    }
    
    public void calculateRoundResult() {
        float roundScore1 = 0;
        float roundScore2 = 0;
        if (player1Guess > currentPrice && player2Guess > currentPrice) {
            if (player1Guess < player2Guess) roundScore1 = 1;
            else if (player2Guess < player1Guess) roundScore2 = 1;
            else {
                roundScore1 = 0.5f;
                roundScore2 = 0.5f;
            }
        } else if (player1Guess <= currentPrice && player2Guess <= currentPrice) {
            if (player1Guess > player2Guess) roundScore1 = 1;
            else if (player2Guess > player1Guess) roundScore2 = 1;
            else {
                roundScore1 = 0.5f;
                roundScore2 = 0.5f;
            }
        } else if (player1Guess <= currentPrice) {
            roundScore1 = 1;
        } else {
            roundScore2 = 1;
        }

        player1Score += roundScore1;
        player2Score += roundScore2;
        
        if (roundScore1  == 1){
            winner = client1.getLoginUser();
        }
        else if (roundScore2 == 1) {
            winner = client2.getLoginUser();
        }
        else {
            winner = "DRAW";
        }
        broadcast("ROUND_RESULT;" + winner + ";" + currentPrice + ";" + client1.getLoginUser() + ";" + client2.getLoginUser() + ";" + player1Guess + ";" + player2Guess + ";" + player1Score + ";" + player2Score);
    }
    
    
    private void updateUserScores(String winner) {
        try {
            if (winner.equals("DRAW")) {
                draw();
            } else if (winner.equals(client1.getLoginUser())) {
                client1Win(0);
            } else {
                client2Win(0);
            }
        } catch (SQLException e) {
        }
    }
    
    public void deleteRoom () {
        client1.setJoinedRoom(null);
        client1.setcCompetitor(null);
        client2.setJoinedRoom(null);
        client2.setcCompetitor(null);
        ServerRun.roomManager.remove(this);
    }
    
    private void getRandomProduct() {
        if (allProducts != null && !allProducts.isEmpty()) {
            Random random = new Random();
            ProductModel randomProduct = allProducts.get(random.nextInt(allProducts.size()));
            currentProduct = randomProduct.getProductName();
            currentPrice = random.nextInt(randomProduct.getMaxPrice() - randomProduct.getMinPrice() + 1) + randomProduct.getMinPrice();
            currentImage = randomProduct.getImageUrl();
            minPrice = randomProduct.getMinPrice();
            maxPrice = randomProduct.getMaxPrice();
        }
    }

    public void draw () throws SQLException {
        UserModel user1 = new UserController().getUser(client1.getLoginUser());
        UserModel user2 = new UserController().getUser(client2.getLoginUser());
        
        user1.setDraw(user1.getDraw() + 1);
        user2.setDraw(user2.getDraw() + 1);
        
        user1.setScore(user1.getScore()+ 0.5f);
        user2.setScore(user2.getScore()+ 0.5f);
              
        new UserController().updateUser(user1);
        new UserController().updateUser(user2);
    }
    
    public void client1Win(int time) throws SQLException {
        UserModel user1 = new UserController().getUser(client1.getLoginUser());
        UserModel user2 = new UserController().getUser(client2.getLoginUser());
        
        user1.setWin(user1.getWin() + 1);
        user2.setLose(user2.getLose() + 1);
        
        user1.setScore(user1.getScore()+ 1);        
        
        new UserController().updateUser(user1);
        new UserController().updateUser(user2);
    }
    
    public void client2Win(int time) throws SQLException {
        UserModel user1 = new UserController().getUser(client1.getLoginUser());
        UserModel user2 = new UserController().getUser(client2.getLoginUser());
        
        user2.setWin(user2.getWin() + 1);
        user1.setLose(user1.getLose() + 1);
        
        user2.setScore(user2.getScore()+ 1);   
        
        new UserController().updateUser(user1);
        new UserController().updateUser(user2);
    }
    
    public void userLeaveGame (String username) throws SQLException {
        matchTimer.pause();
        matchTimerRound.pause();
        endTime = LocalDateTime.now();
        GameModel game = new GameController().getGame(gameId);
        game.setEndTime(endTime);
        game.setScore1(player1Score);
        game.setScore2(player2Score);
        game.setUserLeaveGame(username);
        if (client1.getLoginUser().equals(username)) {
            game.setWinner(client2.getLoginUser());      
            client2Win(0);
        } else if (client2.getLoginUser().equals(username)) {
            game.setWinner(client1.getLoginUser());
            client1Win(0);
        }
        new GameController().updateGame(game, gameId);
    }
    
    // add/remove client
    public boolean addClient(Client c) {
        if (!clients.contains(c)) {
            clients.add(c);
            if (client1 == null) {
                client1 = c;
            } else if (client2 == null) {
                client2 = c;
            }
            return true;
        }
        return false;
    }

    public boolean removeClient(Client c) {
        if (clients.contains(c)) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    // broadcast messages
    public void broadcast(String msg) {
        clients.forEach((c) -> {
            c.sendData(msg);
        });
    }
    
    public Client find(String username) {
        for (Client c : clients) {
            if (c.getLoginUser()!= null && c.getLoginUser().equals(username)) {
                return c;
            }
        }
        return null;
    }

    // gets sets
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient1() {
        return client1;
    }

    public void setClient1(Client client1) {
        this.client1 = client1;
    }

    public Client getClient2() {
        return client2;
    }

    public void setClient2(Client client2) {
        this.client2 = client2;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
    
    public int getSizeClient() {
        return clients.size();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResultClient1() {
        return resultClient1;
    }

    public void setResultClient1() {
        this.resultClient1 = "SUBMITTED";
    }

    public String getResultClient2() {
        return resultClient2;
    }

    public void setResultClient2() {
        this.resultClient2 = "SUBMITTED";
    } 
}
    
