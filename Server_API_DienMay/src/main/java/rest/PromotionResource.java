package rest;

import entity.Promotion;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.PromotionServiceBean;

import java.util.List;

@Path("/vouchers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionResource {

    @EJB
    private PromotionServiceBean service;

    // ==========================
    // Lấy tất cả voucher
    // ==========================
    @GET
    public List<Promotion> getAllPromotions() {
        return service.getAllPromotions();
    }

    // ==========================
    // Thêm voucher
    // ==========================
    @POST
    public Response addPromotion(Promotion promotion) {
        boolean result = service.addPromotion(promotion);
        if (result) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // ==========================
    // Cập nhật voucher
    // ==========================
    @PUT
    @Path("/{code}")
    public Response updatePromotion(
            @PathParam("code") String code,
            Promotion promotion) {

        promotion.setCode(code);
        boolean result = service.updatePromotion(promotion);

        if (result) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // ==========================
    // Xóa voucher
    // ==========================
    @DELETE
    @Path("/{code}")
    public Response deletePromotion(
            @PathParam("code") String code) {

        boolean result = service.deletePromotion(code);

        if (result) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // ==========================
    // Kiểm tra voucher theo tổng tiền
    // ==========================
    @GET
    @Path("/check/{total}")
    public Promotion checkPromotion(
            @PathParam("total") double total) {

        return service.getPromotionByTotal(total);

    }

}