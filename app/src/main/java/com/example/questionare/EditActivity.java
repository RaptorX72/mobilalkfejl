package com.example.questionare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    Questionare questionare;
    ArrayList<Question> questions;
    IQuestionareDAO questionareDAO = IQuestionareDAO.getInstance();
    RecyclerView recView;
    ListQuestionsAdapter lqa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_add_questionare);

        String uid = getIntent().getStringExtra("QuestionareId");
        if (uid.isEmpty()) questionare = new Questionare("", Questionare.Status.UNKNOWN, 0, "", "", "", new ArrayList<>());
        else questionare = questionareDAO.getQuestionareById(uid);

        recView = findViewById(R.id.questionsRecycleView);

        TextView nameTB = findViewById(R.id.questionareNameTextBox);
        TextView titleTB = findViewById(R.id.questionareTitleTextBox);
        TextView publisherTB = findViewById(R.id.questionarePublisherNameTextBox);

        RadioButton rbDraft = findViewById(R.id.radioButtonStatusDraft);
        RadioButton rbActive = findViewById(R.id.radioButtonStatusActive);
        RadioButton rbRetired = findViewById(R.id.radioButtonStatusRetired);
        RadioButton rbUnknown = findViewById(R.id.radioButtonStatusUnknown);

        Button addQuestion = findViewById(R.id.buttonAddQuestion);
        Button saveQuestionare = findViewById(R.id.buttonSaveChanges);
        Button discardQuestionare = findViewById(R.id.buttonDiscardChanges);


        switch (questionare.getStatus()) {
            case DRAFT:
                rbDraft.setChecked(true);
                break;
            case ACTIVE:
                rbActive.setChecked(true);
                break;
            case RETIRED:
                rbRetired.setChecked(true);
                break;
            case UNKNOWN:
                rbUnknown.setChecked(true);
                break;
        }
        nameTB.setText(questionare.getName());
        titleTB.setText(questionare.getTitle());
        publisherTB.setText(questionare.getPublisherName());

        questions = new ArrayList<>(questionare.getQuestions());

        lqa = new ListQuestionsAdapter(this, questions);
        recView.setAdapter(lqa);
        recView.setLayoutManager(new LinearLayoutManager(this));

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, AdQuestionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        saveQuestionare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTB.getText().toString();
                String title = titleTB.getText().toString();
                String publisher = publisherTB.getText().toString();

                if (!ValidateFields(name, title, publisher)) {
                    Toast.makeText(EditActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean wasempty = uid.isEmpty();
                questionare.setName(name);
                questionare.setTitle(title);
                questionare.setPublisherName(publisher);
                int ver = questionare.getVersion();
                questionare.setVersion(++ver);
                questionare.setQuestions(questions);
                Questionare.Status status = null;
                if (rbDraft.isChecked()) status = Questionare.Status.DRAFT;
                else if (rbActive.isChecked()) status = Questionare.Status.ACTIVE;
                else if (rbRetired.isChecked()) status = Questionare.Status.RETIRED;
                else if (rbUnknown.isChecked()) status = Questionare.Status.UNKNOWN;
                questionare.setStatus(status);
                if(wasempty) {
                    questionare.setUri(Common.GenerateUIID());
                    questionareDAO.AddQuestionare(questionare);
                } else questionareDAO.UpdateQuestionare(questionare);
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        discardQuestionare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Question q = (Question) data.getSerializableExtra("result");
                questions.add(q);
                lqa.notifyDataSetChanged();
            }
        }
    }

    public void RemoveQuestionById(String id) {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            if (q.getLinkId().equals(id)) {
                questions.remove(i);
                break;
            }
        }
        lqa.notifyDataSetChanged();
    }

    private boolean ValidateFields(String name, String title, String publisher) {
        return !name.isEmpty() && !title.isEmpty() && !publisher.isEmpty();
    }
}
