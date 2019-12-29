package com.mydesing.trivia.listener;

import com.mydesing.trivia.model.Category;

import java.util.List;

public interface getCategoryListener {
    void getAllCategories(List<Category> categories);
    void getCategory(Category category);
    void setVisibilityFalse();
}
