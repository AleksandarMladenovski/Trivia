package com.mydesing.trivia.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.mydesing.trivia.R;
import com.mydesing.trivia.listener.StartTriviaTimeListener;

public class FragmentPlayOnline extends Fragment {
    private StartTriviaTimeListener listener;

    public FragmentPlayOnline() {
        // Required empty public constructor
    }

    public FragmentPlayOnline(StartTriviaTimeListener listener) {
        // Required empty public constructor
        this.listener = listener;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View itemView = inflater.inflate(R.layout.fragment_play_online, container, false);

        final EditText editText = itemView.findViewById(R.id.et_num_of_questions);
        Button button = itemView.findViewById(R.id.btn_get_trivia_questions);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberOfQuestions = editText.getText().toString();
                if (numberOfQuestions.equals("")) {
                    editText.setError(getResources().getString(R.string.ET_error_msg));
                } else {
                    int num_of_questions = Integer.parseInt(numberOfQuestions);
                    if (num_of_questions < 10 || num_of_questions > 20) {
                        editText.setError(getResources().getString(R.string.ET_error_msg));
                    } else {
                        Spinner spinnerCategory = itemView.findViewById(R.id.spinner_category);
                        String category = spinnerCategory.getSelectedItem().toString();
                        String realCategory = getCategoryID(category);


                        Spinner spinnerDifficulty = itemView.findViewById(R.id.spinner_difficulty);
                        String difficulty = spinnerDifficulty.getSelectedItem().toString();
                        String realDifficulty = getDifficultyID(difficulty);

                        Spinner spinnerType = itemView.findViewById(R.id.spinner_type);
                        String type = spinnerType.getSelectedItem().toString();
                        String realType = getTypeID(type);

                        if (listener != null) {
                            listener.readyIntent(numberOfQuestions, realCategory, realDifficulty, realType);
                        }

                    }
                }
            }
        });

        return itemView;
    }

    private String getDifficultyID(String difficulty) {
        if (difficulty.equals("Any Difficulty")) {
            return null;
        } else {
            return difficulty.toLowerCase();
        }
    }

    private String getTypeID(String type) {
        if (type.equals("Multiple Choice")) {
            return "multiple";

        } else if (type.equals("True / False")) {
            return "boolean";
        } else {
            return null;
        }
    }

    private String getCategoryID(String category) {
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
