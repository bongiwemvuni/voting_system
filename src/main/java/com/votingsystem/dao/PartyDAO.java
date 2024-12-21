package com.votingsystem.dao;

import com.votingsystem.model.Party;
import com.votingsystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartyDAO {
    
    public List<Party> getAllParties() {
        List<Party> parties = new ArrayList<>();
        String sql = "SELECT DISTINCT id, name, symbol, vote_count FROM parties ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Party party = new Party();
                party.setId(rs.getInt("id"));
                party.setName(rs.getString("name"));
                party.setSymbol(rs.getString("symbol"));
                party.setVoteCount(rs.getInt("vote_count"));
                parties.add(party);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parties;
    }
    
    public boolean incrementVoteCount(int partyId) {
        String sql = "UPDATE parties SET vote_count = vote_count + 1 WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, partyId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Party getWinningParty() {
        String sql = "SELECT * FROM parties ORDER BY vote_count DESC LIMIT 1";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                Party party = new Party();
                party.setId(rs.getInt("id"));
                party.setName(rs.getString("name"));
                party.setSymbol(rs.getString("symbol"));
                party.setVoteCount(rs.getInt("vote_count"));
                return party;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
