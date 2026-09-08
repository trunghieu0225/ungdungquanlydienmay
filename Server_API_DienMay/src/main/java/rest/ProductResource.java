package rest;

import entity.Product;
import service.ProductServiceBean;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @EJB
    ProductServiceBean service;

    // Lấy tất cả sản phẩm
    @GET
    public List<Product> getProducts() {
        return service.getProducts();
    }

    // Lấy sản phẩm theo ID
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id) {
        Product p = service.getProductById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(p).build();
    }

    // Thêm sản phẩm
    @POST
    public Response addProduct(Product p) {
        service.addProduct(p);
        return Response.status(Response.Status.CREATED).build();
    }

    // Cập nhật sản phẩm
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product p) {
        p.setProductId(id);
        service.updateProduct(p);
        return Response.ok().build();
    }

    // Xóa sản phẩm
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        service.deleteProduct(id);
        return Response.ok().build();
    }
    @GET
    @Path("/search/{keyword}")
    public List<Product> searchProducts(
            @PathParam("keyword") String keyword) {

        return service.searchProducts(keyword);
    }

}
