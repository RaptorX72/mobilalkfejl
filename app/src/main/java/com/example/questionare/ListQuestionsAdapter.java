package com.example.questionare;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListQuestionsAdapter extends RecyclerView.Adapter<ListQuestionsAdapter.ListQuestionsViewHolder> {

    ArrayList<Question> questions;
    Context context;

    public ListQuestionsAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ListQuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_row, parent, false);
        return new ListQuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListQuestionsAdapter.ListQuestionsViewHolder holder, int position) {
        holder.questionTB.setText(questions.get(position).getQuestion());
        Question.QuestionType type = questions.get(position).getType();
        holder.typeTB.setText(type.toString());
        holder.question = questions.get(position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ListQuestionsViewHolder extends RecyclerView.ViewHolder {
        Question question;
        TextView questionTB;
        TextView typeTB;
        public ListQuestionsViewHolder(@NonNull View item) {
            super(item);
            questionTB = item.findViewById(R.id.textViewQuestion);
            typeTB = item.findViewById(R.id.textViewQuestionType);
            item.findViewById(R.id.buttonRemove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditActivity)context).RemoveQuestionById(question.getLinkId());
                }
            });
        }
    }
}