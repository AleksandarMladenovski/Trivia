package com.mydesing.trivia.DataRepository.IDataBaseImplementations;

import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.model.Question;

import java.util.List;

public interface IDataSource {
    void getData(String amount,String category,String difficulty,String type,final CustomListenerRep listenerRep);

    void insertData(List<Question> questionList);

    void getListOfCategories(CategoryListener listener);
}
