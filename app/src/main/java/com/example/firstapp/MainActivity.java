package com.example.firstapp;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.category.CategoriesAdapter;
import com.example.firstapp.dto.category.CategoryItemDTO;
import com.example.firstapp.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    RecyclerView rcCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        loadCategories();
    }

    private void loadCategories() {
        setContentView(R.layout.activity_main);
        rcCategories = findViewById(R.id.rcCategories);
        rcCategories.setHasFixedSize(true);
        rcCategories.setLayoutManager(new GridLayoutManager(MainActivity.this, 1, RecyclerView.VERTICAL, false));

        ApplicationNetwork.getInstance().getCategoriesApi().list().enqueue(new Callback<List<CategoryItemDTO>>() {
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