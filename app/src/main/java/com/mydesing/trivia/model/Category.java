package com.mydesing.trivia.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String category;
    private int num_of_Questions;
    private int score=0;
    public Category() {
    }

    public Category(String category) {
        this.category=category;
    }

    public Category(String category, int num_of_Questions) {
        this.category = category;
        this.num_of_Questions = num_of_Questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int getNum_of_Questions() {
        return num_of_Questions;
    }

    public void setNum_of_Questions(int num_of_Questions) {
        this.num_of_Questions = num_of_Questions;
    }
}
