package com.mydesing.trivia.DataRepository.IDataBaseImplementations;

import com.mydesing.trivia.DataRepository.Retrofit.GetRetrofitDataService;
import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.model.OpenTDBResult;
import com.mydesing.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetworkDataSource implements IDataSource {
    private static GetRetrofitDataService dataServicee;

    public NetworkDataSource(Retrofit retrofit) {
        if (dataServicee == null) {
            dataServicee = retrofit.create(GetRetrofitDataService.class);
        }

    }

    @Override
    public void getData(String amount, String category, String difficulty, String type,final CustomListenerRep listenerRep) {


        Call<OpenTDBResult> call = dataServicee.get_Trivia_Questions(amount, category, difficulty, type);
        call.enqueue(new Callback<OpenTDBResult>() {
            @Override
            public void onResponse(Call<OpenTDBResult> call, Response<OpenTDBResult> response) {
                listenerRep.RepReturn(response.body().getQuestions());
            }

            @Override
            public void onFailure(Call<OpenTDBResult> call, Throwable t) {
                listenerRep.RepReturn(new ArrayList<Question>());
            }
        });

    }
    @Override
    public void insertData(List<Question> questionList) {
        //TODO MAYBE POST METHOD
    }

    @Override
    public void getListOfCategories(CategoryListener listener) {

    }
}
