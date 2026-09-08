package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDetailDAO {

    // Thêm 1 sản phẩm vào chi tiết đơn hàng
    public boolean addOrderDetail(int orderId,
                                  int productId,
                                  int quantity,
                                  double price) {

        String sql =
                "INSERT INTO orderdetail(order_id,product_id,quantity,price) " +
                        "VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setDouble(4, price);

                int row = ps.executeUpdate();
                return row > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}