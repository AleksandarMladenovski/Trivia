package com.mydesing.trivia.DataRepository.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mydesing.trivia.model.Category;
import com.mydesing.trivia.model.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(List<Question> questions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category category);

    @Query("SELECT * FROM Question")
    List<Question> getAllQuestions();

    @Query("SELECT * FROM Question WHERE category LIKE:category")
    List<Question> getAllQuestionsByCategory(String category);

    @Query("SELECT * FROM Category WHERE category LIKE:category ")
    Category getObjectForCategory(String category);


    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();



}
