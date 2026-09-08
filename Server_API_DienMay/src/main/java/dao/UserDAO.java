package dao;

import entity.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Lấy tất cả User
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    u.setFullname(rs.getString("fullname"));
                    list.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy User theo ID
    public User getUserById(int id) {
        User u = null;
        String sql = "SELECT * FROM User WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        u = new User();
                        u.setUserId(rs.getInt("user_id"));
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        u.setFullname(rs.getString("fullname"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    // Thêm User
    public void addUser(User u) {
        String sql = "INSERT INTO User(username,password,role,fullname) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPassword());
                ps.setString(3, u.getRole());
                ps.setString(4, u.getFullname());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật User
    public void updateUser(User u) {
        String sql = "UPDATE User SET username=?,password=?,role=?,fullname=? WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPassword());
                ps.setString(3, u.getRole());
                ps.setString(4, u.getFullname());
                ps.setInt(5, u.getUserId());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa User
    public void deleteUser(int id) {
        String sql = "DELETE FROM User WHERE user_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm User
    public List<User> searchUser(String keyword) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM User WHERE username LIKE ? OR fullname LIKE ? OR role LIKE ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
                ps.setString(3, "%" + keyword + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User u = new User();
                        u.setUserId(rs.getInt("user_id"));
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        u.setFullname(rs.getString("fullname"));
                        list.add(u);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đăng nhập
    public User login(String username, String password) {
        String sql = "SELECT * FROM User WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User u = new User();
                        u.setUserId(rs.getInt("user_id"));
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        u.setFullname(rs.getString("fullname"));
                        return u;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}