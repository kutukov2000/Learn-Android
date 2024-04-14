package com.example.firstapp.dto.category;

public class CategoryCreateDTO {
    private String name;
    private String description;
    private final String image = null;

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
}