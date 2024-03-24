package com.example.firstapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firstapp.application.HomeApplication;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        String imageUrl = "https://image.api.playstation.com/cdn/UP1004/NPUB31154_00/GFPKXf9DZUowXp1HxCco8FBSYeR7Pn9y.png?w=440&thumb=false";
        Glide.with(HomeApplication.getAppContext())
                .load(imageUrl)
                .apply(new RequestOptions().override(720))
                .into(imageView);
    }
}