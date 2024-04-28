package com.example.firstapp.category;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.firstapp.BaseActivity;
import com.example.firstapp.MainActivity;
import com.example.firstapp.R;
import com.example.firstapp.dto.category.CategoryCreateDTO;
import com.example.firstapp.dto.category.CategoryItemDTO;
import com.example.firstapp.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;
    Button btnUploadImage;
    ImageView ivSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);

        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);

        btnUploadImage.setOnClickListener(v -> openGallery());
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

    public void onClickCreateCategory(View view) {
        try {
            RequestBody name=RequestBody.create(MediaType.parse("text/plain"), tlCategoryName.getEditText().getText().toString());
            RequestBody description=RequestBody.create(MediaType.parse("text/plain"), tlCategoryDescription.getEditText().getText().toString());

            File image = convertImageViewToFile(ivSelectedImage);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), image);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", image.getName(), requestFile);

//            CategoryCreateDTO dto = new CategoryCreateDTO();
//            dto.setName(name);
//            dto.setDescription(description);
//            dto.setImage(image);

            ApplicationNetwork.getInstance()
                    .getCategoriesApi()
                    .create(name, description, body)
                    .enqueue(new Callback<CategoryItemDTO>() {
                        @Override
                        public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                        }
                    });
        } catch (Exception ex) {
            Log.e("--CategoryCreateActivity--", "Problem create " + ex.getMessage());
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
}