package dao;

import entity.Category;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Lấy tất cả loại
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Category c = new Category();
                    c.setCategoryId(rs.getInt("category_id"));
                    c.setCategoryName(rs.getString("category_name"));
                    c.setDescription(rs.getString("description"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy theo id
    public Category getCategoryById(int id) {
        Category c = null;
        String sql = "SELECT * FROM Category WHERE category_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        c = new Category();
                        c.setCategoryId(rs.getInt("category_id"));
                        c.setCategoryName(rs.getString("category_name"));
                        c.setDescription(rs.getString("description"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    // Thêm
    public void addCategory(Category c) {
        String sql = "INSERT INTO Category(category_name,description) VALUES(?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, c.getCategoryName());
                ps.setString(2, c.getDescription());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sửa
    public void updateCategory(Category c) {
        String sql = "UPDATE Category SET category_name=?,description=? WHERE category_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, c.getCategoryName());
                ps.setString(2, c.getDescription());
                ps.setInt(3, c.getCategoryId());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa
    public void deleteCategory(int id) {
        String sql = "DELETE FROM Category WHERE category_id=?";

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

    // Tìm kiếm
    public List<Category> searchCategory(String keyword) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE category_name LIKE ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + keyword + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Category c = new Category();
                        c.setCategoryId(rs.getInt("category_id"));
                        c.setCategoryName(rs.getString("category_name"));
                        c.setDescription(rs.getString("description"));
                        list.add(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}