package com.example.firstapp.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.firstapp.BaseActivity;
import com.example.firstapp.MainActivity;
import com.example.firstapp.R;
import com.example.firstapp.dto.category.CategoryCreateDTO;
import com.example.firstapp.dto.category.CategoryItemDTO;
import com.example.firstapp.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);
    }

    public void onClickCreateCategory(View view) {
        try {
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();
            CategoryCreateDTO dto = new CategoryCreateDTO();
            dto.setName(name);
            dto.setDescription(description);
            ApplicationNetwork.getInstance()
                    .getCategoriesApi()
                    .create(dto)
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

}