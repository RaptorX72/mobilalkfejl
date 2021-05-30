package com.example.questionare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private FirebaseFirestore store;
    private CollectionReference coll;
    private Questionare questionare;
    private ArrayList<Question> questions;
    private RecyclerView recView;
    private ListQuestionsAdapter lqa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_add_questionare);

        store = FirebaseFirestore.getInstance();
        coll = store.collection("Questionares");
        questionare = (Questionare)(getIntent().getSerializableExtra("QuestionareId"));

        recView = findViewById(R.id.questionsRecycleView);
        TextView nameTB = findViewById(R.id.questionareNameTextBox);
        TextView titleTB = findViewById(R.id.questionareTitleTextBox);
        TextView publisherTB = findViewById(R.id.questionarePublisherNameTextBox);
        RadioButton rbDraft = findViewById(R.id.radioButtonStatusDraft);
        RadioButton rbActive = findViewById(R.id.radioButtonStatusActive);
        RadioButton rbRetired = findViewById(R.id.radioButtonStatusRetired);
        RadioButton rbUnknown = findViewById(R.id.radioButtonStatusUnknown);

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

        findViewById(R.id.buttonAddQuestion).setOnClickListener(v -> {
            Intent intent = new Intent(EditActivity.this, AdQuestionActivity.class);
            startActivityForResult(intent, 1);
        });

        findViewById(R.id.buttonSaveChanges).setOnClickListener(v -> {
            String name = nameTB.getText().toString();
            String title = titleTB.getText().toString();
            String publisher = publisherTB.getText().toString();

            if (!ValidateFields(name, title, publisher)) {
                Toast.makeText(EditActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean wasempty = questionare.getUri().isEmpty();
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
                coll.add(questionare);
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        Questionare q = document.toObject(Questionare.class);
                        if (q.getUri().equals(questionare.getUri())) {
                            coll.document(document.getId()).delete();
                            coll.add(questionare);
                            break;
                        }
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
            }
        });

        findViewById(R.id.buttonDiscardChanges).setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                questions.add((Question) data.getSerializableExtra("result"));
                lqa.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
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
