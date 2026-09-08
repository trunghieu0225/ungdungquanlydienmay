package service;

import dao.OrderDAO;
import jakarta.ejb.Stateless;

@Stateless
public class OrderServiceBean {

    private OrderDAO orderDAO = new OrderDAO();

    public boolean createOrder(String username,
                               String voucherCode,
                               double totalAmount) {

        return orderDAO.createOrder(
                username,
                voucherCode,
                totalAmount
        );
    }

}