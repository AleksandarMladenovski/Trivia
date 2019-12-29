package com.mydesing.trivia.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydesing.trivia.DataRepository.RepositoryInstance;
import com.mydesing.trivia.R;
import com.mydesing.trivia.adapter.AdapterCategory;
import com.mydesing.trivia.listener.AtaskTOAtaskCategoryListener;
import com.mydesing.trivia.listener.CategoryListener;
import com.mydesing.trivia.listener.CustomListenerRep;
import com.mydesing.trivia.listener.StartTriviaTimeListener;
import com.mydesing.trivia.listener.getCategoryListener;
import com.mydesing.trivia.model.Category;
import com.mydesing.trivia.model.Question;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlayOffline extends Fragment {
    private AdapterCategory adapterCategory;
    private StartTriviaTimeListener listener;
    private TextView tvSync;

    public FragmentPlayOffline() {
        // Required empty public constructor
    }

    public FragmentPlayOffline(StartTriviaTimeListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View itemView = inflater.inflate(R.layout.fragment_play_offline, container, false);
        tvSync = itemView.findViewById(R.id.tvSync);
        RecyclerView recyclerView = itemView.findViewById(R.id.recyclerViewOfflinePlay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCategory = new AdapterCategory(new LinkedList<Category>(), listener);
        recyclerView.setAdapter(adapterCategory);
        setAdapterQuestions();

        return itemView;
    }

    private void setAdapterQuestions() {
        String[] categories = getResources().getStringArray(R.array.values_category);
        ATaskGetCategories getCategories = new ATaskGetCategories(getContext(), categories, new getCategoryListener() {
            @Override
            public void getAllCategories(List<Category> categories) {
                adapterCategory.insertCategories(categories);
            }

            @Override
            public void getCategory(Category category) {
                adapterCategory.insertCategory(category);
            }

            @Override
            public void setVisibilityFalse() {
                tvSync.setVisibility(View.GONE);
            }
        });
        getCategories.execute();
    }

    private static class ATaskGetCategories extends AsyncTask<Void, Void, Void> {

        private WeakReference<Context> context;
        private getCategoryListener listener;
        private String[] nameOfCategories;

        public ATaskGetCategories(Context context, String[] nameOfCategories, getCategoryListener listener) {
            this.context = new WeakReference<>(context);
            this.listener = listener;
            this.nameOfCategories = nameOfCategories;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            RepositoryInstance.getRepositoryQuestion(context.get()).getListOfCategories(new CategoryListener() {
                @Override
                public void getAllCategories(List<Category> categories) {
                    if(categories==null){
                        return;
                    }
                    if (categories.size() == nameOfCategories.length-1) {
                        listener.getAllCategories(categories);
                        listener.setVisibilityFalse();
                    }
                    else {
                        AtaskTOAtaskCategoryListener listenerCategory = new AtaskTOAtaskCategoryListener() {
                            @Override
                            public void returnCategory(Category category) {
                                listener.getCategory(category);
                            }

                            @Override
                            public void setVisibilityFalse() {
                                listener.setVisibilityFalse();
                            }
                        };
                        ATaskInsertCategories insertCategories = new ATaskInsertCategories(context.get(), nameOfCategories, listenerCategory);
                        insertCategories.execute();
                    }
                }
            });

            return null;
        }
    }

    private static class ATaskInsertCategories extends AsyncTask<Void, Void, Void> {
        private WeakReference<Context> context;
        private AtaskTOAtaskCategoryListener listener;
        private String[] nameOfCategories;
        int count = 1;

        public ATaskInsertCategories(Context context, String[] nameOfCategories, AtaskTOAtaskCategoryListener listener) {
            this.context = new WeakReference<>(context);
            this.nameOfCategories = nameOfCategories;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CustomListenerRep listenerRep = new CustomListenerRep() {
                @Override
                public void RepReturn(final List<Question> questions) {
                            count++;
                    if (questions != null && questions.size() > 0) {
                        listener.returnCategory(new Category(questions.get(0).getCategory()));
                        final Executor executor = Executors.newFixedThreadPool(1);
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                RepositoryInstance.getRepositoryQuestion(context.get()).insertDataToDatabase(questions);
                                if(count==nameOfCategories.length){
                                    listener.setVisibilityFalse();
                                }
                            }
                        });
                    }
                }
            };

            for (int i = 1; i < nameOfCategories.length; i++) {
                RepositoryInstance.getRepositoryQuestion(context.get())
                        .getDataFromAPI("30", getCategoryID(nameOfCategories[i]), null, null, listenerRep);
            }
            return null;
        }

    }

    private static String getCategoryID(String category) {
        switch (category) {
            case "Sports":
                return "21";
            case "Animals":
                return "27";
            case "Vehicles":
                return "28";
            case "Geography":
                return "22";
            case "History":
                return "23";
            case "General Knowledge":
                return "9";
            default:
                return null;
        }
    }
}

