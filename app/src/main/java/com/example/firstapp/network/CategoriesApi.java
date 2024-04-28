package com.example.firstapp.network;

import com.example.firstapp.dto.category.CategoryCreateDTO;
import com.example.firstapp.dto.category.CategoryItemDTO;
import com.example.firstapp.authentication.login.LoginRequest;
import com.example.firstapp.authentication.login.LoginResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CategoriesApi {
    @GET("/api/categories")
    public Call<List<CategoryItemDTO>> list();

    @Multipart
    @POST("/api/categories")
    Call<CategoryItemDTO> create(
            @Part("name") String name,
            @Part("description") String description,
            @Part MultipartBody.Part image // Use MultipartBody.Part for image
    );
    @POST("/api/account/login")
    public Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Multipart
    @POST("/api/account/register")
    public Call<LoginResponse> register(
            @Part("email") RequestBody email, //set request body type here
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("confirmPassword") RequestBody confirmPassword,
            @Part MultipartBody.Part image // Use MultipartBody.Part for image
    );
}

