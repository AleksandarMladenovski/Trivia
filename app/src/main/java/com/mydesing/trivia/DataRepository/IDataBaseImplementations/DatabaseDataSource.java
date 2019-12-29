package com.mydesing.trivia.DataRepository.IDataBaseImplementations;

import android.content.Context;

import com.mydesing.trivia.DataRepository.RoomDatabase.QuestionDatabase;
import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.model.Category;
import com.mydesing.trivia.model.Question;

import java.util.Collections;
import java.util.List;

public class DatabaseDataSource implements IDataSource {
    private Context context;

    public DatabaseDataSource() {
    }
    public DatabaseDataSource(Context context) {
        this.context=context;
    }

    @Override
    public void getData(String amount, String category, String difficulty, String type, CustomListenerRep listenerRep) {
        List<Question> questions= QuestionDatabase.getQuestionDatabase(context).noteDao().getAllQuestionsByCategory(category);
        Collections.shuffle(questions);
        listenerRep.RepReturn(questions.subList(0,10));
    }
    @Override
    public void insertData(List<Question> questionList) {
        QuestionDatabase.getQuestionDatabase(context).noteDao().insertNotes(questionList);
        Category category=new Category(questionList.get(0).getCategory(),questionList.size());
        QuestionDatabase.getQuestionDatabase(context).noteDao().insertCategory(category);

    }

    @Override
    public void getListOfCategories(CategoryListener listener) {
        listener.getAllCategories(QuestionDatabase.getQuestionDatabase(context).noteDao().getAllCategories());
    }
//
//    @Override
//    public void getData(ListenerRepositoryDataSource listenerRep) {
//     DataBaseHelper db= AppSingleton.getINSTANCE(context).getDbHelper();
//     List<Question>questions=db.getAllQuestions();
//        Log.e("DATABASE_QUESTION_COUNT",String.valueOf(questions.size()));
//     listenerRep.sendDataToRepository(questions);
//        db.close();
//    }
//
//    @Override
//    public void insertData(List<Question> questionList) {
//        DataBaseHelper db= AppSingleton.getINSTANCE(context).getDbHelper();
//            for(Question question:questionList){
//                db.insertQuestion(question);
//            }
//            db.close();
//    }
}
