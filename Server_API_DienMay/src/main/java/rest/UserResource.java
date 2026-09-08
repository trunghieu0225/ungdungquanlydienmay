package rest;

import entity.User;
import service.UserServiceBean;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    UserServiceBean service;

    // Lấy tất cả User
    @GET
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // Lấy User theo ID
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        User user = service.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    // Thêm User
    @POST
    public Response addUser(User user) {
        service.addUser(user);
        return Response.status(Response.Status.CREATED).build();
    }

    // Cập nhật User
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        user.setUserId(id);
        service.updateUser(user);
        return Response.ok().build();
    }

    // Xóa User
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        service.deleteUser(id);
        return Response.ok().build();
    }

    // Tìm kiếm User
    @GET
    @Path("/search/{keyword}")
    public List<User> searchUser(@PathParam("keyword") String keyword) {
        return service.searchUser(keyword);
    }

    // Đăng nhập
    @POST
    @Path("/login")
    public Response login(User user) {
        User result = service.login(
                user.getUsername(),
                user.getPassword()
        );
        if (result != null) {
            return Response.ok(result).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}