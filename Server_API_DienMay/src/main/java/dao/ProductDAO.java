package dao;

import entity.Product;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("product_id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setOldPrice(rs.getDouble("old_price"));
                    p.setImage(rs.getString("image"));
                    p.setDescription(rs.getString("description"));
                    p.setGift(rs.getString("gift"));
                    p.setRating(rs.getString("rating"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(int id) {
        Product p = null;
        String sql = "SELECT * FROM Product WHERE product_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        p = new Product();
                        p.setProductId(rs.getInt("product_id"));
                        p.setName(rs.getString("name"));
                        p.setPrice(rs.getDouble("price"));
                        p.setOldPrice(rs.getDouble("old_price"));
                        p.setImage(rs.getString("image"));
                        p.setDescription(rs.getString("description"));
                        p.setGift(rs.getString("gift"));
                        p.setRating(rs.getString("rating"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    // Thêm sản phẩm
    public void addProduct(Product p) {
        String sql = "INSERT INTO Product(name, price, old_price, image, description, gift, rating) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setDouble(2, p.getPrice());
                ps.setDouble(3, p.getOldPrice());
                ps.setString(4, p.getImage());
                ps.setString(5, p.getDescription());
                ps.setString(6, p.getGift());
                ps.setString(7, p.getRating());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật sản phẩm
    public void updateProduct(Product p) {
        String sql = "UPDATE Product SET name=?, price=?, old_price=?, image=?, description=?, gift=?, rating=? WHERE product_id=?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setDouble(2, p.getPrice());
                ps.setDouble(3, p.getOldPrice());
                ps.setString(4, p.getImage());
                ps.setString(5, p.getDescription());
                ps.setString(6, p.getGift());
                ps.setString(7, p.getRating());
                ps.setInt(8, p.getProductId());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa sản phẩm
    public void deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE product_id=?";

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

    // Tìm kiếm sản phẩm
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE name LIKE ? OR description LIKE ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return list;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product p = new Product();
                        p.setProductId(rs.getInt("product_id"));
                        p.setName(rs.getString("name"));
                        p.setPrice(rs.getDouble("price"));
                        p.setOldPrice(rs.getDouble("old_price"));
                        p.setImage(rs.getString("image"));
                        p.setDescription(rs.getString("description"));
                        p.setGift(rs.getString("gift"));
                        p.setRating(rs.getString("rating"));
                        list.add(p);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}