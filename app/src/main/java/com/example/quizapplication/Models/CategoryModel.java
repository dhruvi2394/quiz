package com.example.quizapplication.Models;

public class CategoryModel {

    private String CategoryName,CategoryImage,key;
    int setNum;

    public CategoryModel(String CategoryName,String CategoryImage,String key,int setNum) {
        this.CategoryName = CategoryName;
        this.CategoryImage = CategoryImage;
        this.key=key;
        this.setNum=setNum;
    }

    public CategoryModel() {
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public int getSetNum() {
        return setNum;
    }

    public void setSetNum(int setNum) {
        this.setNum = setNum;
    }
}