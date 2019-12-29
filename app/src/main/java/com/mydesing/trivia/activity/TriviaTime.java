package com.mydesing.trivia.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.mydesing.trivia.DataRepository.RepositoryInstance;
import com.mydesing.trivia.R;
import com.mydesing.trivia.adapter.AdapterQuestions;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.model.Question;

import java.lang.ref.WeakReference;
import java.util.List;


public class TriviaTime extends AppCompatActivity {
    RecyclerView recyclerViewQuestions;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_time);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btn_check_trivia_answers).setVisibility(View.GONE);

        String numberOfQuestions=getIntent().getStringExtra(getString(R.string.KEY_AMOUNT));
        String realCategory=getIntent().getStringExtra(getString(R.string.KEY_CATEGORY));
        String realDifficulty=getIntent().getStringExtra(getString(R.string.KEY_DIFFICULTY));
        String realType=getIntent().getStringExtra(getString(R.string.KEY_TYPE));

        if(numberOfQuestions!=null) {
            setViaRetrofitClient(numberOfQuestions, realCategory, realDifficulty, realType);
        }
        else{
            setViaDatabase(realCategory);
        }
    }
    public void setViaDatabase(String category){

        CustomListenerRep listener=new CustomListenerRep() {
            @Override
            public void RepReturn(List<Question> questions) {
                generateDataList(questions);
            }
        };
        AsyncTaskGetQuestionsFromDatabase asyncTaskGetQuestionsFromDatabase=new AsyncTaskGetQuestionsFromDatabase(this,listener);
        asyncTaskGetQuestionsFromDatabase.execute(category);
    }
    private static class AsyncTaskGetQuestionsFromDatabase extends AsyncTask<String,Void,Void>{
        private WeakReference<Context>context;
        CustomListenerRep listenerToActivity;
        public AsyncTaskGetQuestionsFromDatabase(Context context,CustomListenerRep listenerToActivity){
            this.context=new WeakReference<>(context);
            this.listenerToActivity=listenerToActivity;
        }

        @Override
        protected Void doInBackground(String... strings) {
            CustomListenerRep listenerRep=new CustomListenerRep() {
                @Override
                public void RepReturn(List<Question> questions) {
                    listenerToActivity.RepReturn(questions);
                }
            };
            RepositoryInstance.getRepositoryQuestion(context.get()).getDataFromDatabase(strings[0],listenerRep);
            return null;
        }
    }
    public void setViaRetrofitClient(String numberOfQuestions,String category,String difficulty,String type){
        CustomListenerRep listenerRep=new CustomListenerRep() {
            @Override
            public void RepReturn(List<Question> questions) {
                generateDataList(questions);
            }
        };
        RepositoryInstance.getRepositoryQuestion(this).getDataFromAPI(numberOfQuestions, category, difficulty, type,listenerRep);
    }
    public void generateDataList(final List<Question>questions){
        Button checkResults=findViewById(R.id.btn_check_trivia_answers);
        checkResults.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                findViewById(R.id.btn_check_trivia_answers).setVisibility(View.VISIBLE);
            }
        });
        final AdapterQuestions adapterQuestions = new AdapterQuestions(questions);
        recyclerViewQuestions=findViewById(R.id.recyclerView);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(TriviaTime.this));
        recyclerViewQuestions.setAdapter(adapterQuestions);
        checkResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counter=0;
                String [] user_input=adapterQuestions.getUser_input();
                for(int i=0;i<questions.size();i++){
                    if(questions.get(i).getCorrect_answer().equals(user_input[i])){
                        counter++;
                    }
                }
                showDialog(counter,questions.size());

            }
        });
    }
    protected void showDialog(int correctAnswers,int totalAnswers){

        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("SCORE");
        alertBuilder.setMessage(correctAnswers+ " / " + totalAnswers);
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               finish();
            }
        });
        alertBuilder.show();
    }

}
