package dao;

import util.DBConnection;

import java.sql.*;

public class OrderDAO {

    public boolean createOrder(String username,
                               String voucherCode,
                               double totalAmount) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DBConnection.getConnection();

            // Tìm user
            String sqlUser =
                    "SELECT user_id FROM user WHERE username=?";

            ps = conn.prepareStatement(sqlUser);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Không tìm thấy user: " + username);
                return false;
            }

            int userId = rs.getInt("user_id");

            rs.close();
            ps.close();

            // Thêm đơn hàng
            String sql =
                    "INSERT INTO ordertable(user_id,voucher_code,total_amount,status) " +
                            "VALUES(?,?,?,?)";

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, userId);

            if (voucherCode == null || voucherCode.trim().isEmpty()) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, voucherCode);
            }

            ps.setDouble(3, totalAmount);
            ps.setString(4, "Pending");

            int row = ps.executeUpdate();

            if (row > 0) {

                ResultSet key = ps.getGeneratedKeys();

                if (key.next()) {

                    int orderId = key.getInt(1);

                    System.out.println("OrderId = " + orderId);
                }

                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }

}