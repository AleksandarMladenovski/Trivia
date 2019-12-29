package com.mydesing.trivia.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Question {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;
    private String category;
    private String difficulty;
    private String question;
    private String correct_answer;
    @TypeConverters(Question.class)
    private ArrayList<String> incorrect_answers;
    @TypeConverter
    public String arrayToString(ArrayList<String> incorrect_answers){
        String answers="";
        for(String string:incorrect_answers){
            answers+=string+"|";
        }
        return answers.substring(0,answers.length()-1);
    }
    @TypeConverter
    public ArrayList<String> stringToArray(String answers){
       return new ArrayList<>(Arrays.asList(answers.split("\\|")));
    }
    public Question(){

    }
    public Question(String category, String difficulty, String question, String correct_answer, ArrayList<String> incorrect_answers) {
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public int get_Answer_Count(){
        return 1+incorrect_answers.size();
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    @Override
    public String toString() {
        return question+correct_answer;
    }
}
