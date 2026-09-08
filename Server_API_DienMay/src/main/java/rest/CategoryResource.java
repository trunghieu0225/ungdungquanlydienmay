package rest;

import entity.Category;
import service.CategoryServiceBean;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @EJB
    CategoryServiceBean service;

    // Lấy tất cả loại hàng
    @GET
    public List<Category> getAllCategories() {
        return service.getAllCategories();
    }

    // Lấy loại hàng theo ID
    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") int id) {
        Category category = service.getCategoryById(id);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(category).build();
    }

    // Thêm loại hàng
    @POST
    public Response addCategory(Category category) {
        service.addCategory(category);
        return Response.status(Response.Status.CREATED).build();
    }

    // Cập nhật loại hàng
    @PUT
    @Path("/{id}")
    public Response updateCategory(@PathParam("id") int id, Category category) {
        category.setCategoryId(id);
        service.updateCategory(category);
        return Response.ok().build();
    }

    // Xóa loại hàng
    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") int id) {
        service.deleteCategory(id);
        return Response.ok().build();
    }

    // Tìm kiếm
    @GET
    @Path("/search/{keyword}")
    public List<Category> searchCategory(
            @PathParam("keyword") String keyword) {

        return service.searchCategory(keyword);
    }

}