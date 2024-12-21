package com.votingsystem.dao;

import com.votingsystem.model.Voter;
import com.votingsystem.util.DBConnection;

import java.sql.*;

public class VoterDAO {
    
    public boolean registerVoter(Voter voter) {
        // First check if username or email already exists
        String checkSql = "SELECT COUNT(*) as count FROM voters WHERE username = ? OR email = ?";
        String insertSql = "INSERT INTO voters (username, password, email, has_voted) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection()) {
            // Check for existing user
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, voter.getUsername());
                checkStmt.setString(2, voter.getEmail());
                
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt("count") > 0) {
                    System.out.println("Username or email already exists!");
                    return false;
                }
            }
            
            // If no existing user found, proceed with registration
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, voter.getUsername());
                insertStmt.setString(2, voter.getPassword());
                insertStmt.setString(3, voter.getEmail());
                insertStmt.setInt(4, 0); // SQLite doesn't have boolean, using 0 for false
                
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Voter authenticateVoter(String username, String password) {
        String sql = "SELECT * FROM voters WHERE username = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Voter voter = new Voter();
                    voter.setId(rs.getInt("id"));
                    voter.setUsername(rs.getString("username"));
                    voter.setEmail(rs.getString("email"));
                    voter.setHasVoted(rs.getInt("has_voted") == 1); // Convert SQLite integer to boolean
                    return voter;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean markAsVoted(int voterId) {
        String sql = "UPDATE voters SET has_voted = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, 1); // SQLite doesn't have boolean, using 1 for true
            pstmt.setInt(2, voterId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
