package rest;

import entity.OrderRequest;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.OrderServiceBean;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @EJB
    private OrderServiceBean service;

    @POST
    @Path("/create")
    public Response createOrder(OrderRequest request) {

        boolean result = service.createOrder(
                request.getUsername(),
                request.getVoucherCode(),
                request.getTotalAmount()
        );

        if (result) {

            // Trả về 200 OK, không trả chuỗi để tránh lỗi parse ở Retrofit
            return Response.ok().build();

        } else {

            // Trả về 400 nếu tạo đơn thất bại
            return Response.status(Response.Status.BAD_REQUEST).build();

        }

    }

}