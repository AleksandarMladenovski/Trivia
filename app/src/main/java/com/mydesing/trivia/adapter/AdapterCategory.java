package com.mydesing.trivia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mydesing.trivia.R;
import com.mydesing.trivia.listener.StartTriviaTimeListener;
import com.mydesing.trivia.model.Category;
import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryHolder> {
    private List<Category>categories;
    private StartTriviaTimeListener listener;
    public AdapterCategory(List<Category> categories,StartTriviaTimeListener listener){
        this.categories=categories;
        this.listener=listener;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_offline, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        int final_item_position=holder.getAdapterPosition();
        final String category=categories.get(final_item_position).getCategory();
        holder.button.setText(category);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.readyIntent(null,category,null,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void insertCategories(List<Category> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    public void insertCategory(Category category) {
        this.categories.add(category);
        notifyItemInserted(categories.size()-1);
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        Button button;
         CategoryHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.buttonCategory);
        }
    }
}
