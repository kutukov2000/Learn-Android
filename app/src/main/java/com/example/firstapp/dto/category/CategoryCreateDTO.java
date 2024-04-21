package com.example.firstapp.dto.category;

import java.io.File;

import retrofit2.http.Multipart;

public class CategoryCreateDTO {
    private String name;
    private String description;
    private File image = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(File image) {
        this.image = image;
    }
}