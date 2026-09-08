package com.example.dienmayapp.api;

import com.example.dienmayapp.model.CartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // ===========================
    // PRODUCT API
    // ===========================

    @GET("products")
    Call<List<ProductAPI>> getProducts();

    @POST("products")
    Call<Void> addProduct(
            @Body ProductApiRequest product
    );

    @PUT("products/{id}")
    Call<Void> updateProduct(
            @Path("id") int id,
            @Body ProductApiRequest product
    );

    @DELETE("products/{id}")
    Call<Void> deleteProduct(
            @Path("id") int id
    );

    @GET("products/search/{keyword}")
    Call<List<ProductAPI>> searchProducts(
            @Path("keyword") String keyword
    );


    // ===========================
    // CATEGORY API
    // ===========================

    @GET("categories")
    Call<List<CategoryAPI>> getCategories();

    @POST("categories")
    Call<Void> addCategory(
            @Body CategoryRequest category
    );

    @PUT("categories/{id}")
    Call<Void> updateCategory(
            @Path("id") int id,
            @Body CategoryRequest category
    );

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(
            @Path("id") int id
    );

    @GET("categories/search/{keyword}")
    Call<List<CategoryAPI>> searchCategory(
            @Path("keyword") String keyword
    );


    // ===========================
    // USER API
    // ===========================

    @GET("users")
    Call<List<UserAPI>> getUsers();

    @POST("users")
    Call<Void> addUser(
            @Body UserRequest user
    );

    @PUT("users/{id}")
    Call<Void> updateUser(
            @Path("id") int id,
            @Body UserRequest user
    );

    @DELETE("users/{id}")
    Call<Void> deleteUser(
            @Path("id") int id
    );

    @GET("users/search/{keyword}")
    Call<List<UserAPI>> searchUser(
            @Path("keyword") String keyword
    );

    @POST("users/login")
    Call<LoginResponse> login(
            @Body LoginRequest request
    );

    @POST("users")
    Call<Void> register(
            @Body UserRequest request
    );


    // ===========================
// ORDER API
// ===========================

    @POST("order/create")
    Call<Void> createOrder(
            @Body OrderRequest request
    );

    //================ PROMOTION =================

    @GET("vouchers")
    Call<List<PromotionAPI>> getPromotions();

    @POST("vouchers")
    Call<Void> addPromotion(
            @Body PromotionRequest request
    );

    @PUT("vouchers/{code}")
    Call<Void> updatePromotion(
            @Path("code") String code,
            @Body PromotionRequest request
    );

    @DELETE("vouchers/{code}")
    Call<Void> deletePromotion(
            @Path("code") String code
    );

    @GET("vouchers/check/{total}")
    Call<PromotionAPI> checkPromotion(
            @Path("total") double total
    );

}