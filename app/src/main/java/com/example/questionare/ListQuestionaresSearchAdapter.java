package com.example.questionare;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListQuestionaresSearchAdapter extends RecyclerView.Adapter<ListQuestionaresSearchAdapter.ListQuestionaresSearchViewHolder> {

    ArrayList<Questionare> questionares;
    Context context;

    public ListQuestionaresSearchAdapter(Context context, ArrayList<Questionare> questionares) {
        this.context = context;
        this.questionares = questionares;
    }

    @NonNull
    @Override
    public ListQuestionaresSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_questionares_row_search, parent, false);
        return new ListQuestionaresSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListQuestionaresSearchAdapter.ListQuestionaresSearchViewHolder holder, int position) {
        holder.quesionareName.setText(questionares.get(position).getName());
        holder.questionarePublisher.setText(questionares.get(position).getPublisherName());
        holder.questionareQuestionCount.setText(String.valueOf(questionares.get(position).getQuestions().size()));
    }

    @Override
    public int getItemCount() {
        return questionares.size();
    }

    public class ListQuestionaresSearchViewHolder extends RecyclerView.ViewHolder {
        TextView quesionareName;
        TextView questionarePublisher;
        TextView questionareQuestionCount;
        public ListQuestionaresSearchViewHolder(@NonNull View item) {
            super(item);
            quesionareName = item.findViewById(R.id.textViewSearchQuestionareName);
            questionarePublisher = item.findViewById(R.id.textViewSearchPublisherName);
            questionareQuestionCount = item.findViewById(R.id.textViewSearchQuestionsCount);
        }
    }
}
