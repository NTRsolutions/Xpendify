package com.samsoft.xpendify.model;

/**
 * Created by Fred on 09-Sep-15.
 */
public class CategoryData {

    int id;
    String category, category_type, category_color;

    public CategoryData() {
    }

    public CategoryData(int id, String category, String category_type, String category_color) {
        this.id = id;
        this.category = category;
        this.category_type = category_type;
        this.category_color = category_color;
    }

    public CategoryData(String category, String category_type, String category_color) {
        this.category = category;
        this.category_type = category_type;
        this.category_color = category_color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public String getCategory_color() {
        return category_color;
    }

    public void setCategory_color(String category_color) {
        this.category_color = category_color;
    }

    @Override
    public String toString() {
        return "CategoryData{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", category_type='" + category_type + '\'' +
                ", category_color='" + category_color + '\'' +
                '}';
    }
}
