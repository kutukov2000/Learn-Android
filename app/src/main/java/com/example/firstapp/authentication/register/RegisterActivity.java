package com.example.firstapp.authentication.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstapp.MainActivity;
import com.example.firstapp.R;
import com.example.firstapp.authentication.login.LoginActivity;
import com.example.firstapp.authentication.login.LoginRequest;
import com.example.firstapp.authentication.login.LoginResponse;
import com.example.firstapp.services.ApplicationNetwork;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextEmail, editTextUsername, editTextPassword,editTextConfirmPassword;
    private Button btnUploadImage,buttonRegister;
    ImageView ivSelectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername=findViewById(R.id.editTextUsername);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextConfirmPassword=findViewById(R.id.editTextConfirmPassword);

        btnUploadImage=findViewById(R.id.btnUploadImage);
        buttonRegister=findViewById(R.id.buttonRegister);

        ivSelectedImage=findViewById(R.id.ivSelectedImage);
        btnUploadImage.setOnClickListener(v -> openGallery());
        // Set a click listener for the login button
        buttonRegister.setOnClickListener(view -> {
            RequestBody email=RequestBody.create(MediaType.parse("text/plain"), editTextEmail.getText().toString());
            RequestBody username=RequestBody.create(MediaType.parse("text/plain"), editTextUsername.getText().toString());
            RequestBody password=RequestBody.create(MediaType.parse("text/plain"), editTextPassword.getText().toString());
            RequestBody confirmPassword=RequestBody.create(MediaType.parse("text/plain"), editTextConfirmPassword.getText().toString());
//            String username=editTextUsername.getText().toString().trim();;
//            String password=editTextPassword.getText().toString().trim();;
//            String confirmPassword=editTextConfirmPassword.getText().toString().trim();;

            File image = convertImageViewToFile(ivSelectedImage);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), image);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", image.getName(), requestFile);

            ApplicationNetwork
                    .getInstance()
                    .getCategoriesApi()
                    .register(email,username,password,confirmPassword,body)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                // Login successful
                                Toast.makeText(RegisterActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Login failed due to unexpected response from server
                                Toast.makeText(RegisterActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ivSelectedImage.setImageURI(selectedImageUri);
        }
    }
    private File convertImageViewToFile(ImageView imageView) {
        // Get the drawable from ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // Create a file to store the bitmap
        File file = new File(getCacheDir(), "image.jpg");
        try {
            file.createNewFile();

            // Convert bitmap to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bitmapData = byteArrayOutputStream.toByteArray();

            // Write the bytes to the file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private RequestBody convertStringToRequestBody(String string) {
        RequestBody body=RequestBody.create(string.getBytes());
        return body;
    }
}