package Dao;

import Model.News;
import Util.Jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

    private News map(ResultSet rs) throws SQLException {
        News n = new News();
        n.setId(rs.getString("Id"));
        n.setTitle(rs.getString("Title"));
        n.setContent(rs.getString("Content"));
        n.setImage(rs.getString("Image"));
        n.setPostedDate(rs.getDate("PostedDate"));
        n.setAuthor(rs.getString("Author"));
        n.setViewCount(rs.getInt("ViewCount"));
        n.setCategoryId(rs.getString("CategoryId"));
        n.setHome(rs.getBoolean("Home"));
        return n;
    }

    public List<News> getTop5Hot() throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT TOP 5 * FROM News ORDER BY ViewCount DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<News> getTop5Newest() throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT TOP 5 * FROM News ORDER BY PostedDate DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<News> getHomeNews() throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM News WHERE Home = 1 ORDER BY PostedDate DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<News> getByCategory(String categoryId) throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM News WHERE CategoryId = ? ORDER BY PostedDate DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    public List<News> getAll() throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM News ORDER BY PostedDate DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<News> getByAuthor(String author) throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM News WHERE Author = ? ORDER BY PostedDate DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, author);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    public News findById(String id) throws Exception {
        String sql = "SELECT * FROM News WHERE Id = ?";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public void increaseView(String id) throws Exception {
        String sql = "UPDATE News SET ViewCount = ViewCount + 1 WHERE Id = ?";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public void insert(News n) throws Exception {
        String sql = "INSERT INTO News (Id, Title, Content, Image, Author, CategoryId, Home) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, n.getId());
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getContent());
            ps.setString(4, n.getImage());
            ps.setString(5, n.getAuthor());
            ps.setString(6, n.getCategoryId());
            ps.setBoolean(7, n.isHome());
            ps.executeUpdate();
        }
    }

    public void update(News n) throws Exception {
        String sql = "UPDATE News SET Title = ?, Content = ?, Image = ?, CategoryId = ?, Home = ? WHERE Id = ?";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, n.getTitle());
            ps.setString(2, n.getContent());
            ps.setString(3, n.getImage());
            ps.setString(4, n.getCategoryId());
            ps.setBoolean(5, n.isHome());
            ps.setString(6, n.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM News WHERE Id = ?";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public String generateNewsId() throws Exception {
        String sql = "SELECT TOP 1 Id FROM News ORDER BY Id DESC";
        try (Connection c = Jdbc.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String lastId = rs.getString("Id");
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("N%03d", num);
            }
        }
        return "N001";
    }
}