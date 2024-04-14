package com.example.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firstapp.application.HomeApplication;
import com.example.firstapp.category.CategoriesAdapter;
import com.example.firstapp.dto.category.CategoryItemDTO;
import com.example.firstapp.models.LoginRequest;
import com.example.firstapp.models.LoginResponse;
import com.example.firstapp.models.Post;
import com.example.firstapp.services.ApplicationNetwork;
import com.example.firstapp.services.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCategories;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                ApplicationNetwork
                        .getInstance()
                        .getCategoriesApi()
                        .login(new LoginRequest(username, password))
                        .enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                                if (response.isSuccessful()) {
                                    // Login successful
                                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    loadCategories();
                                } else {
                                    // Login failed due to unexpected response from server
                                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(MainActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void loadCategories() {
        setContentView(R.layout.activity_main);
        rcCategories = findViewById(R.id.rcCategories);
        rcCategories.setHasFixedSize(true);
        rcCategories.setLayoutManager(new GridLayoutManager(MainActivity.this, 1, RecyclerView.VERTICAL, false));

        ApplicationNetwork
                .getInstance()
                .getCategoriesApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        if (response.isSuccessful()) {
                            List<CategoryItemDTO> items = response.body();
                            CategoriesAdapter ca = new CategoriesAdapter(items);
                            rcCategories.setAdapter(ca);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {
                        Log.e("--problem--", "error server");
                    }
                });
    }
}