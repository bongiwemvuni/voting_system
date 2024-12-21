package com.votingsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {
    private static final String DB_FILE = "voting_system.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;
    private static Connection connection = null;
    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);
                if (!initialized) {
                    initializeDatabase();
                    initialized = true;
                }
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC driver not found", e);
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Enable foreign keys
            stmt.execute("PRAGMA foreign_keys = ON");
            
            // Create voters table
            stmt.execute("CREATE TABLE IF NOT EXISTS voters (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "email TEXT UNIQUE NOT NULL," +
                    "has_voted INTEGER DEFAULT 0" +
                    ")");

            // Create parties table with UNIQUE constraint on name
            stmt.execute("CREATE TABLE IF NOT EXISTS parties (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT UNIQUE NOT NULL," +
                    "symbol TEXT NOT NULL," +
                    "vote_count INTEGER DEFAULT 0" +
                    ")");

            // Check if parties table is empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM parties");
            if (rs.next() && rs.getInt("count") == 0) {
                // Only insert sample parties if the table is empty
                stmt.execute("BEGIN TRANSACTION;");
                try {
                    stmt.execute("INSERT INTO parties (name, symbol) VALUES " +
                            "('Purple Party', 'purple.png')");
                    stmt.execute("INSERT INTO parties (name, symbol) VALUES " +
                            "('Orange Party', 'orange.png')");
                    stmt.execute("INSERT INTO parties (name, symbol) VALUES " +
                            "('Green Party', 'green.png')");
                    stmt.execute("INSERT INTO parties (name, symbol) VALUES " +
                            "('Independent Party', 'independent.png')");
                    stmt.execute("COMMIT;");
                } catch (SQLException e) {
                    stmt.execute("ROLLBACK;");
                    throw e;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
