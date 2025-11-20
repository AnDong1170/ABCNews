package Dao;

import Model.User;
import Util.Jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User login(String id, String pass) throws Exception {
        String sql = "SELECT * FROM Users WHERE Id = ? AND Password = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getString("Id"));
                    u.setFullname(rs.getString("Fullname"));
                    u.setEmail(rs.getString("Email"));
                    u.setRole(rs.getBoolean("Role"));
                    u.setMobile(rs.getString("Mobile"));
                    u.setBirthday(rs.getDate("Birthday"));
                    u.setGender(rs.getBoolean("Gender"));
                    return u;
                }
            }
        }
        return null;
    }

    public List<User> getAll() throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                u.setMobile(rs.getString("Mobile"));
                u.setBirthday(rs.getDate("Birthday"));
                u.setGender(rs.getBoolean("Gender"));
                list.add(u);
            }
        }
        return list;
    }
}