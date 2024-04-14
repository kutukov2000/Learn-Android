package com.example.firstapp.network;

import com.example.firstapp.dto.category.CategoryCreateDTO;
import com.example.firstapp.dto.category.CategoryItemDTO;
import com.example.firstapp.models.LoginRequest;
import com.example.firstapp.models.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CategoriesApi {
    @GET("/api/categories")
    public Call<List<CategoryItemDTO>> list();

    @POST("/api/categories")
    public Call<CategoryItemDTO> create(@Body CategoryCreateDTO categoryCreateDTO);

    @POST("/api/account/login")
    public Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

