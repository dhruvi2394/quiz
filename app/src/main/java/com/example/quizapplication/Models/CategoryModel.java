package com.example.quizapplication.Models;

public class CategoryModel {

    private String CategoryName,CategoryImage,key;
    int setNum;

    public CategoryModel(String CategoryName,String categoryImage,String key,int setNum) {
        this.CategoryName = CategoryName;
        this.CategoryImage = categoryImage;
        this.key=key;
        this.setNum=setNum;
    }

    public CategoryModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
