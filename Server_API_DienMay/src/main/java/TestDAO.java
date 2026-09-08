import dao.ProductDAO;
import entity.Product;

import java.util.List;

public class TestDAO {

    public static void main(String[] args) {

        ProductDAO dao = new ProductDAO();

        List<Product> list =
                dao.getAllProducts();

        for(Product p : list){

            System.out.println(
                    p.getProductId()
                            + " - "
                            + p.getName()
                            + " - "
                            + p.getPrice()
            );
        }
    }
}