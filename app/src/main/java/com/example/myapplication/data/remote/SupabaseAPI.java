package com.example.myapplication.data.remote;

import com.example.myapplication.data.models.Product;
import com.example.myapplication.data.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface SupabaseAPI {

    @GET("rest/v1/products")
    Call<List<Product>> getAllProducts(@Query("select") String select);

    @GET("rest/v1/users")
    Call<List<User>> getUserByEmail(
            @Query("email") String emailFilter,
            @Query("select") String select
    );

    @POST("rest/v1/users")
    @Headers({"Prefer: return=representation", "Content-Type: application/json"})
    Call<List<User>> createUser(@Body User user);

    @Headers({"Content-Type: application/json"})
    @GET("products?select=*")
    Call<List<Product>> getAllProducts();
}
