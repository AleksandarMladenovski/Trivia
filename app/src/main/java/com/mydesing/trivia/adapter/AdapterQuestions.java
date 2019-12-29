package com.mydesing.trivia.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mydesing.trivia.R;
import com.mydesing.trivia.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterQuestions extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Question>questions;
    private  String []user_input;

    public AdapterQuestions(List<Question> questions) {
        this.questions=questions;
        user_input=new String[questions.size()];
    }
    @Override
    public int getItemViewType(int position) {
        if (questions.get(position).get_Answer_Count()==2) {
            return 1;
        } else{
            return 2;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1){
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_two_question, parent, false);

            return new QuestionTwoHolder(itemView);
        }
        else{
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_four_question, parent, false);

            return new QuestionFourHolder(itemView);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position){
        int position_holder=holder.getAdapterPosition();
        if(getItemViewType(position_holder)==1) {
            set_Up_QuestionTwoHolder(holder);
        }
        else{
            set_Up_QuestionFourHolder(holder);
        }
    }
    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void set_Up_QuestionTwoHolder(RecyclerView.ViewHolder holder){
        final int position_holder=holder.getAdapterPosition();
        String question=questions.get(position_holder).getQuestion();
        String category=questions.get(position_holder).getCategory();
        String difficulty=questions.get(position_holder).getDifficulty();
        String correct_Answer=questions.get(position_holder).getCorrect_answer();
        ArrayList<String>wrong_Answers=questions.get(position_holder).getIncorrect_answers();

        QuestionTwoHolder questionTwoHolder=(QuestionTwoHolder)holder;

        questionTwoHolder.getQuestion().setText(question);
        questionTwoHolder.getCategory().setText(category);
        questionTwoHolder.getDifficulty().setText(difficulty);
        questionTwoHolder.getQuestion_number().setText(String.valueOf(position_holder+1));
        final RadioButton radioButton_one= questionTwoHolder.getRadioButton_ONE();
        final RadioButton radioButton_two= questionTwoHolder.getRadioButton_TWO();

        ArrayList<String>allAnswers=new ArrayList<>();
        allAnswers.add(wrong_Answers.get(0));
        allAnswers.add(correct_Answer);
        Collections.shuffle(allAnswers);
        radioButton_one.setText(allAnswers.get(1));
        radioButton_two.setText(allAnswers.get(0));

        radioButton_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input[position_holder]=radioButton_one.getText().toString();
                radioButton_two.setChecked(false);
            }
        });

        radioButton_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input[position_holder]=radioButton_two.getText().toString();
                radioButton_one.setChecked(false);

            }
        });
    }
    public void set_Up_QuestionFourHolder(RecyclerView.ViewHolder holder){
        final int position_holder=holder.getAdapterPosition();
        String question=questions.get(position_holder).getQuestion();
        String category=questions.get(position_holder).getCategory();
        String difficulty=questions.get(position_holder).getDifficulty();
        String correct_Answer=questions.get(position_holder).getCorrect_answer();
        ArrayList<String>wrong_Answers=questions.get(position_holder).getIncorrect_answers();

        QuestionFourHolder questionFourHolder=(QuestionFourHolder) holder;

        questionFourHolder.getQuestion().setText(question);
        questionFourHolder.getCategory().setText(category);
        questionFourHolder.getDifficulty().setText(difficulty);
        questionFourHolder.getQuestion_number().setText(String.valueOf(position_holder+1));

        final RadioButton radioButton_one= questionFourHolder.getRadioButton_ONE();
        final RadioButton radioButton_two= questionFourHolder.getRadioButton_TWO();
        final RadioButton radioButton_three= questionFourHolder.getRadioButton_THREE();
        final RadioButton radioButton_four= questionFourHolder.getRadioButton_FOUR();

        ArrayList<String>allAnswers=new ArrayList<>();
        allAnswers.add(wrong_Answers.get(0));
        allAnswers.add(wrong_Answers.get(1));
        allAnswers.add(wrong_Answers.get(2));
        allAnswers.add(correct_Answer);
        Collections.shuffle(allAnswers);

        radioButton_one.setText(allAnswers.get(0));
        radioButton_two.setText(allAnswers.get(1));
        radioButton_three.setText(allAnswers.get(2));
        radioButton_four.setText(allAnswers.get(3));

        radioButton_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input[position_holder]=radioButton_one.getText().toString();
                radioButton_two.setChecked(false);
                radioButton_three.setChecked(false);
                radioButton_four.setChecked(false);
            }
        });

        radioButton_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input[position_holder]=radioButton_two.getText().toString();
                radioButton_one.setChecked(false);
                radioButton_three.setChecked(false);
                radioButton_four.setChecked(false);
            }
        });

        radioButton_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input[position_holder]=radioButton_three.getText().toString();
                radioButton_one.setChecked(false);
                radioButton_two.setChecked(false);
                radioButton_four.setChecked(false);
            }
        });
        radioButton_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input[position_holder]=radioButton_four.getText().toString();
                radioButton_one.setChecked(false);
                radioButton_two.setChecked(false);
                radioButton_three.setChecked(false);
            }
        });
    }

    public  String [] getUser_input() {
        return user_input;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    class QuestionTwoHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private RadioButton radioButton_ONE;
        private RadioButton radioButton_TWO;
        private TextView category;
        private TextView difficulty;
        private TextView question_number;
        public QuestionTwoHolder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.TV_QUESTION);
            radioButton_ONE=itemView.findViewById(R.id.RB_TWO_ANSWER_ONE);
            radioButton_TWO=itemView.findViewById(R.id.RB_TWO_ANSWER_TWO);
            category=itemView.findViewById(R.id.TV_CATEGORY);
            difficulty=itemView.findViewById(R.id.TV_DIFFICULTY);
            question_number=itemView.findViewById(R.id.TV_QUESTION_NUMBER);
        }

        public TextView getQuestion_number() {
            return question_number;
        }

        public void setQuestion_number(TextView question_number) {
            this.question_number = question_number;
        }

        public TextView getQuestion() {
            return question;
        }

        public void setQuestion(TextView question) {
            this.question = question;
        }

        public RadioButton getRadioButton_ONE() {
            return radioButton_ONE;
        }

        public void setRadioButton_ONE(RadioButton radioButton_ONE) {
            this.radioButton_ONE = radioButton_ONE;
        }

        public RadioButton getRadioButton_TWO() {
            return radioButton_TWO;
        }

        public void setRadioButton_TWO(RadioButton radioButton_TWO) {
            this.radioButton_TWO = radioButton_TWO;
        }

        public TextView getCategory() {
            return category;
        }

        public void setCategory(TextView category) {
            this.category = category;
        }

        public TextView getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(TextView difficulty) {
            this.difficulty = difficulty;
        }

    }
    class QuestionFourHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private RadioButton radioButton_ONE;
        private RadioButton radioButton_TWO;
        private RadioButton radioButton_THREE;
        private RadioButton radioButton_FOUR;
        private TextView category;
        private TextView difficulty;
        private TextView question_number;
        public QuestionFourHolder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.TV_QUESTION);
            radioButton_ONE=itemView.findViewById(R.id.RB_FOUR_ANSWER_ONE);
            radioButton_TWO=itemView.findViewById(R.id.RB_FOUR_ANSWER_TWO);
            radioButton_THREE=itemView.findViewById(R.id.RB_FOUR_ANSWER_THREE);
            radioButton_FOUR=itemView.findViewById(R.id.RB_FOUR_ANSWER_FOUR);
            category=itemView.findViewById(R.id.TV_CATEGORY);
            difficulty=itemView.findViewById(R.id.TV_DIFFICULTY);
            question_number=itemView.findViewById(R.id.TV_QUESTION_NUMBER);
        }
        public TextView getQuestion() {
            return question;
        }

        public void setQuestion(TextView question) {
            this.question = question;
        }

        public RadioButton getRadioButton_ONE() {
            return radioButton_ONE;
        }

        public void setRadioButton_ONE(RadioButton radioButton_ONE) {
            this.radioButton_ONE = radioButton_ONE;
        }

        public RadioButton getRadioButton_TWO() {
            return radioButton_TWO;
        }

        public void setRadioButton_TWO(RadioButton radioButton_TWO) {
            this.radioButton_TWO = radioButton_TWO;
        }

        public RadioButton getRadioButton_THREE() {
            return radioButton_THREE;
        }

        public void setRadioButton_THREE(RadioButton radioButton_THREE) {
            this.radioButton_THREE = radioButton_THREE;
        }

        public RadioButton getRadioButton_FOUR() {
            return radioButton_FOUR;
        }

        public void setRadioButton_FOUR(RadioButton radioButton_FOUR) {
            this.radioButton_FOUR = radioButton_FOUR;
        }

        public TextView getCategory() {
            return category;
        }

        public void setCategory(TextView category) {
            this.category = category;
        }

        public TextView getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(TextView difficulty) {
            this.difficulty = difficulty;
        }

        public TextView getQuestion_number() {
            return question_number;
        }

        public void setQuestion_number(TextView question_number) {
            this.question_number = question_number;
        }
    }
String getdifficultyWorth(String difficulty){
    Log.e("dif",difficulty);
        switch (difficulty){
            case "easy":
                return "1";
            case "medium":
                return "2";
            case "hard":
                return "3";
            default:
                return "0";
        }
    }

}