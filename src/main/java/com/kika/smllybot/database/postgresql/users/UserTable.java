package com.kika.smllybot.database.postgresql.users;

import com.kika.smllybot.database.postgresql.DatabaseManager;
import java.sql.*;

public class UserTable {

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                discord_id BIGINT UNIQUE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ DB:USERS | Успешно загружена");
        } catch (SQLException e) {
            System.err.println("❌ DB:USERS | Ошибка создания: " + e.getMessage());
        }
    }

    public static User getOrCreateUser(long discordId) {
        String insertSql = "INSERT INTO users (discord_id) VALUES (?) ON CONFLICT (discord_id) DO NOTHING RETURNING id";
        String selectSql = "SELECT id FROM users WHERE discord_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                selectPstmt.setLong(1, discordId);
                ResultSet rs = selectPstmt.executeQuery();
                if (rs.next()) {
                    return new User(rs.getInt("id"), discordId);
                }
            }

            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                insertPstmt.setLong(1, discordId);
                ResultSet rs = insertPstmt.executeQuery();
                if (rs.next()) {
                    return new User(rs.getInt("id"), discordId);
                }
            }

            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                selectPstmt.setLong(1, discordId);
                ResultSet rs = selectPstmt.executeQuery();
                if (rs.next()) {
                    return new User(rs.getInt("id"), discordId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
