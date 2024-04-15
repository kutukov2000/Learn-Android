package com.example.firstapp.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.MainActivity;
import com.example.firstapp.R;
import com.example.firstapp.services.ApplicationNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

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
        buttonLogin.setOnClickListener(view -> {
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
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Login failed due to unexpected response from server
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}