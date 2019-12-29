package com.mydesing.trivia.DataRepository.RepositoryQuestion;

import com.mydesing.trivia.DataRepository.IDataBaseImplementations.IDataSource;
import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.model.Question;

import java.util.List;

public class RepositoryQuestionImpl implements RepositoryQuestion {
    private IDataSource networkDataSource;
    private IDataSource databaseDataSource;

    public RepositoryQuestionImpl(IDataSource networkDataSource, IDataSource databaseDataSource) {
        this.networkDataSource = networkDataSource;
        this.databaseDataSource = databaseDataSource;
    }

    @Override
    public void getDataFromAPI(String amount, String category, String difficulty, String type, CustomListenerRep listenerRep) {
        networkDataSource.getData(amount,category,difficulty,type,listenerRep);
    }
    public void getData(){

    }
    @Override
    public void getDataFromDatabase(String category, CustomListenerRep listenerRep) {
        databaseDataSource.getData(null,category,null,null,listenerRep);
    }

    @Override
    public void insertDataToDatabase(List<Question> questionList) {
        databaseDataSource.insertData(questionList);
    }

    @Override
    public void getListOfCategories(CategoryListener listener) {
        databaseDataSource.getListOfCategories(listener);
    }
}