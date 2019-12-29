package com.mydesing.trivia.DataRepository;

import android.content.Context;

import com.mydesing.trivia.DataRepository.IDataBaseImplementations.DatabaseDataSource;
import com.mydesing.trivia.DataRepository.IDataBaseImplementations.IDataSource;
import com.mydesing.trivia.DataRepository.IDataBaseImplementations.NetworkDataSource;
import com.mydesing.trivia.DataRepository.RepositoryQuestion.RepositoryQuestionImpl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryInstance {
   private static RepositoryQuestionImpl repositoryQuestion;

    public static RepositoryQuestionImpl getRepositoryQuestion(Context context){
        if(repositoryQuestion!=null){
            return repositoryQuestion;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IDataSource database=new DatabaseDataSource(context);
        IDataSource network = new NetworkDataSource(retrofit);
        repositoryQuestion=new RepositoryQuestionImpl(network, database);

        return repositoryQuestion;
    }
}
