package service;

import dao.PromotionDAO;
import entity.Promotion;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class PromotionServiceBean {

    private PromotionDAO dao = new PromotionDAO();

    // Lấy tất cả voucher
    public List<Promotion> getAllPromotions() {

        return dao.getAllPromotions();
    }

    // Thêm voucher
    public boolean addPromotion(Promotion promotion) {

        return dao.addPromotion(promotion);
    }

    // Cập nhật voucher
    public boolean updatePromotion(Promotion promotion) {

        return dao.updatePromotion(promotion);
    }

    // Xóa voucher
    public boolean deletePromotion(String code) {

        return dao.deletePromotion(code);
    }

    // Kiểm tra voucher theo tổng tiền
    public Promotion getPromotionByTotal(double total) {

        return dao.getPromotionByTotal(total);
    }

}