package model;

import java.io.Serializable;

public class RoundModel implements Serializable {
    private static final long serialVersionUID = 3L;
    
    public RoundModel() { }
    
    private int gameId;
    private int round;
    private String winner;
    private String product;
    private int actual_price;
    private int user1_price;
    private int user2_price;
    
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getActual_price() {
        return actual_price;
    }

    public void setActual_price(int actual_price) {
        this.actual_price = actual_price;
    }

    public int getUser1_price() {
        return user1_price;
    }

    public void setUser1_price(int user1_price) {
        this.user1_price = user1_price;
    }

    public int getUser2_price() {
        return user2_price;
    }

    public void setUser2_price(int user2_price) {
        this.user2_price = user2_price;
    }
}
