package com.mydesing.trivia.DataRepository.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mydesing.trivia.model.Category;
import com.mydesing.trivia.model.Question;

@Database(entities = {Question.class, Category.class},version = 1)
public abstract class QuestionDatabase extends RoomDatabase {
    private static QuestionDatabase INSTANCE;
    public abstract QuestionDao noteDao();

    public static QuestionDatabase getQuestionDatabase(Context context){
        if(INSTANCE==null){
            synchronized (QuestionDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context, QuestionDatabase.class,"questions_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }

        return INSTANCE;
    }

}
