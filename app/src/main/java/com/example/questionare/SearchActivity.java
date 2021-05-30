package com.example.questionare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recView;
    IQuestionareDAO questionareDAO = IQuestionareDAO.getInstance();
    ListQuestionaresSearchAdapter lqsa;
    String searchText = "";
    boolean includeReitred = false;
    boolean includeUnknown = false;
    ArrayList<Questionare> questionares;// = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        recView = findViewById(R.id.recyclerViewSearch);

        questionares = questionareDAO.getAllQuestionares();//new ArrayList<>(questionareDAO.getAllQuestionares());
        TextView searchTB = findViewById(R.id.textBoxSearch);
        CheckBox cbUnknown = findViewById(R.id.checkBoxIncUnknown);
        Log.d("searchactivity", String.valueOf(questionares.size()));
        CheckBox cbRetired = findViewById(R.id.checkBoxIncRetired);
        lqsa = new ListQuestionaresSearchAdapter(this, questionares);
        recView.setAdapter(lqsa);
        recView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.buttonSearchQuestionares).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchTB.getText().toString();
                includeUnknown = cbUnknown.isChecked();
                includeReitred = cbRetired.isChecked();
                questionares = questionareDAO.getQuestionares(searchText, includeUnknown, includeReitred);
                lqsa = new ListQuestionaresSearchAdapter(SearchActivity.this, questionares);
                recView.setAdapter(lqsa);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
