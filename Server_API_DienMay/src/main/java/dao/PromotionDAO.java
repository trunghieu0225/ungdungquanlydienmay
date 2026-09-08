package dao;

import entity.Promotion;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {

    // Lấy tất cả voucher
    public List<Promotion> getAllPromotions() {

        List<Promotion> list = new ArrayList<>();

        String sql = "SELECT * FROM voucher";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Promotion p = new Promotion();

                p.setCode(rs.getString("code"));
                p.setDiscount(rs.getDouble("discount"));
                p.setMinTotal(rs.getDouble("min_total"));

                list.add(p);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm voucher
    public boolean addPromotion(Promotion promotion) {

        String sql =
                "INSERT INTO voucher(code,discount,min_total) VALUES(?,?,?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, promotion.getCode());
            ps.setDouble(2, promotion.getDiscount());
            ps.setDouble(3, promotion.getMinTotal());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Sửa voucher
    public boolean updatePromotion(Promotion promotion) {

        String sql =
                "UPDATE voucher SET discount=?, min_total=? WHERE code=?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDouble(1, promotion.getDiscount());
            ps.setDouble(2, promotion.getMinTotal());
            ps.setString(3, promotion.getCode());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa voucher
    public boolean deletePromotion(String code) {

        String sql =
                "DELETE FROM voucher WHERE code=?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, code);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lấy voucher phù hợp theo tổng tiền
    public Promotion getPromotionByTotal(double total) {

        String sql =
                "SELECT * FROM voucher " +
                        "WHERE min_total <= ? " +
                        "ORDER BY min_total DESC " +
                        "LIMIT 1";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDouble(1, total);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Promotion p = new Promotion();

                p.setCode(rs.getString("code"));
                p.setDiscount(rs.getDouble("discount"));
                p.setMinTotal(rs.getDouble("min_total"));

                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}