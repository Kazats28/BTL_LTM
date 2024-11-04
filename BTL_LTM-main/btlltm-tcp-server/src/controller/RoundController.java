package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.DatabaseConnection;

public class RoundController {
    //  SQL
    private final String INSERT_ROUND = "INSERT INTO rounds (gameId, round, winner, product, actual_price, user1_price, user2_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    //  Instance
    private final Connection con;
    
    public RoundController() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public String insertRound(int gameId, int round, String winner, String product, int actual_price, int user1_price, int user2_price) {
    	//  Check user exit
        try {
            PreparedStatement p = con.prepareStatement(INSERT_ROUND);
            p.setInt(1, gameId);
            p.setInt(2, round);
            p.setString(3, winner);
            p.setString(4, product);
            p.setInt(5, actual_price);
            p.setInt(6, user1_price);
            p.setInt(7, user2_price);
            p.executeUpdate();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
