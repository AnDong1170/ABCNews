// DAO quản lý newsletter - đăng ký + lấy email gửi mail
package Dao;

import Util.Jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsletterDAO {

    public void insert(String email) throws Exception {
        String sql = "IF NOT EXISTS (SELECT * FROM Newsletters WHERE Email = ?) INSERT INTO Newsletters (Email, Enabled) VALUES (?, 1)";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    public List<String> getAllEnabledEmails() throws Exception {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Email FROM Newsletters WHERE Enabled = 1";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Email"));
            }
        }
        return list;
    }

    // CRUD cho admin sẽ thêm sau
}