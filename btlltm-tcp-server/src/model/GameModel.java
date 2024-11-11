package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GameModel implements Serializable {
    private static final long serialVersionUID = 2L;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String user1;
    private String user2;
    private String winner;
    private float score1;
    private float score2;
    private String userLeaveGame;

    public String getUserLeaveGame() {
        return userLeaveGame;
    }

    public void setUserLeaveGame(String userLeaveGame) {
        this.userLeaveGame = userLeaveGame;
    }
    public GameModel() { }
    
    public float getScore1() {
        return score1;
    }

    public void setScore1(float score1) {
        this.score1 = score1;
    }

    public float getScore2() {
        return score2;
    }

    public void setScore2(float score2) {
        this.score2 = score2;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
