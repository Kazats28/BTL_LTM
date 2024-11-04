package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DatabaseConnection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.GameModel;

public class GameController {
    //  SQL
    private final String INSERT_GAME = "INSERT INTO games (startTime, user1, user2, winner, score1, score2, userleavegame) VALUES (?, ?, ?, '', 0, 0, '')";
    
    private final String GET_GAMEID = "SELECT gameId FROM games WHERE startTime = ? AND user1 = ? AND user2 = ?";
    
    private final String GET_INFO = "SELECT startTime, user1, user2 FROM games WHERE gameId=?";
    
    private final String UPDATE_GAME = "UPDATE games SET endTime = ?, winner = ?, score1 = ?, score2 = ?, userleavegame = ? WHERE gameId=?";
    
    private final String HISTORY_GAME = "SELECT startTime, endTime, user1, user2, winner, score1, score2, userleavegame FROM games ORDER BY startTime DESC";

    private final Connection con;
    
    public GameController() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public String insertGame(LocalDateTime startTime, String user1, String user2) {
        try {
            PreparedStatement p = con.prepareStatement(INSERT_GAME);
            p.setTimestamp(1, Timestamp.valueOf(startTime));        
            p.setString(2, user1);
            p.setString(3, user2);
            p.executeUpdate();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public GameModel getGame(int gameId) {
        GameModel game = new GameModel();
        try {
            PreparedStatement p = con.prepareStatement(GET_INFO);
            p.setInt(1, gameId);
            
            ResultSet r = p.executeQuery();
            while(r.next()) {
                game.setStartTime(r.getTimestamp("startTime").toLocalDateTime());
                game.setUser1(r.getString("user1"));
                game.setUser2(r.getString("user2"));
            }
            return game;
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return null;
    }
    
    public int getGameId(LocalDateTime startTime, String user1, String user2) {
        int gameId = 0;
        try {
            PreparedStatement p = con.prepareStatement(GET_GAMEID);
            p.setTimestamp(1, Timestamp.valueOf(startTime));  
            p.setString(2, user1);
            p.setString(3, user2);
            ResultSet r = p.executeQuery();
            while(r.next()) {
                gameId = r.getInt("gameId");
            }
            return gameId;
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return 0;
    }
    
    public boolean updateGame(GameModel game, int gameId) throws SQLException {
        boolean rowUpdated;
        PreparedStatement p = con.prepareStatement(UPDATE_GAME);
        
        p.setTimestamp(1, Timestamp.valueOf(game.getEndTime()));  
        p.setString(2, game.getWinner());
        p.setFloat(3, game.getScore1());
        p.setFloat(4, game.getScore2());
        p.setString(5, game.getUserLeaveGame());
        p.setInt(6, gameId);

        rowUpdated = p.executeUpdate() > 0;
        return rowUpdated;
    }
    
    public List<GameModel> getAllGames() throws SQLException {
        List<GameModel> games = new ArrayList<>();
        try (PreparedStatement p = con.prepareStatement(HISTORY_GAME);
            ResultSet r = p.executeQuery()) {
            while (r.next()) {
                GameModel game = new GameModel();
                game.setStartTime(r.getTimestamp("startTime").toLocalDateTime());
                game.setEndTime(r.getTimestamp("endTime").toLocalDateTime());
                game.setUser1(r.getString("user1"));
                game.setUser2(r.getString("user2"));
                game.setWinner(r.getString("winner"));
                game.setScore1(r.getFloat("score1"));
                game.setScore2(r.getFloat("score2"));
                game.setUserLeaveGame(r.getString("userleavegame"));
                games.add(game);
            }
        }
        return games;
    } 
}
