package com.mydesing.trivia.DataRepository.RepositoryQuestion;

import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.model.Question;

import java.util.List;

public interface RepositoryQuestion {
   void getDataFromAPI(String amount, String category, String difficulty, String type, CustomListenerRep listenerRep);
   void getDataFromDatabase(String category, CustomListenerRep listenerRep);
   void insertDataToDatabase(List<Question> questionList);
   void getListOfCategories(CategoryListener listener);
}
