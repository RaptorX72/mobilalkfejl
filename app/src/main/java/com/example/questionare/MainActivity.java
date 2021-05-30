package com.example.questionare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore store;
    private CollectionReference coll;
    private NotificationHelper notifHelper;
    private ArrayList<Questionare> questionares;
    private RecyclerView recView;
    private ListQuestionaresAdapter lqa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        store = FirebaseFirestore.getInstance();
        coll = store.collection("Questionares");
        notifHelper = new NotificationHelper(this);
        questionares = new ArrayList<>();
        recView = findViewById(R.id.recyclerView);
        lqa = new ListQuestionaresAdapter(this, questionares);
        recView.setAdapter(lqa);
        recView.setLayoutManager(new LinearLayoutManager(this));

        coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots) questionares.add(document.toObject(Questionare.class));
            lqa.notifyDataSetChanged();
        });

        findViewById(R.id.buttonAddQuestionare).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("QuestionareId", new Questionare("", Questionare.Status.UNKNOWN, 0, "", "", "", new ArrayList<>()));
            startActivityForResult(intent, 2);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        findViewById(R.id.buttonSearch).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                questionares.clear();
                coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for(QueryDocumentSnapshot document: queryDocumentSnapshots) questionares.add(document.toObject(Questionare.class));
                    lqa.notifyDataSetChanged();
                });
            }
        }
    }

    public void DeleteQuestionare(Questionare questionare) {
        coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                Questionare q = document.toObject(Questionare.class);
                if (q.getUri().equals(questionare.getUri())) {
                    coll.document(document.getId()).delete();
                    break;
                }
                for (int i = 0; i < questionares.size(); i++) {
                    if (questionares.get(i).getUri().equals(questionare.getUri())) {
                        questionares.remove(i);
                        break;
                    }
                }
                lqa.notifyDataSetChanged();
            }
        });
        lqa.notifyDataSetChanged();
        notifHelper.PushAlert(questionare.getName() + " was deleted!");
    }
}