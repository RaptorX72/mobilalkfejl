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

public class ListQuestionaresAdapter extends RecyclerView.Adapter<ListQuestionaresAdapter.ListQuestionaresViewHolder> {

    ArrayList<Questionare> questionares;
    Context context;

    public ListQuestionaresAdapter(Context context, ArrayList<Questionare> questionares) {
        this.context = context;
        this.questionares = questionares;
    }

    @NonNull
    @Override
    public ListQuestionaresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_questionares_row, parent, false);
        return new ListQuestionaresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListQuestionaresAdapter.ListQuestionaresViewHolder holder, int position) {
        holder.quesionareName.setText(questionares.get(position).getName());
        holder.questionare = questionares.get(position);
        holder.ct = context;
    }

    @Override
    public int getItemCount() {
        return questionares.size();
    }

    public class ListQuestionaresViewHolder extends RecyclerView.ViewHolder {
        Context ct;
        Questionare questionare;
        TextView quesionareName;
        public ListQuestionaresViewHolder(@NonNull View item) {
            super(item);
            quesionareName = item.findViewById(R.id.quesionarename);
            item.findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ct, EditActivity.class);
                    //intent.putExtra("QuestionareId", questionare.getUri());
                    intent.putExtra("QuestionareId", questionare);
                    ((MainActivity)ct).startActivityForResult(intent, 2);
                }
            });
            item.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)ct).DeleteQuestionare(questionare);
                }
            });
        }
    }
}
