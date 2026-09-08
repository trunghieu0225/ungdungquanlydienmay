package service;

import dao.ProductDAO;
import entity.Product;

import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ProductServiceBean {


    private ProductDAO dao = new ProductDAO();

    // Lấy tất cả sản phẩm
    public List<Product> getProducts() {

        return dao.getAllProducts();
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(int id) {

        return dao.getProductById(id);
    }

    // Thêm sản phẩm
    public void addProduct(Product p) {

        dao.addProduct(p);
    }

    // Cập nhật sản phẩm
    public void updateProduct(Product p) {

        dao.updateProduct(p);
    }

    // Xóa sản phẩm
    public void deleteProduct(int id) {

        dao.deleteProduct(id);
    }
    public List<Product> searchProducts(String keyword) {

        return dao.searchProducts(keyword);
    }

}
