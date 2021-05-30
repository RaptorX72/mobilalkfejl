package com.example.questionare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;*/

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recView;
    private ArrayList<Questionare> questionares;
    private ListQuestionaresAdapter lqa;
    private NotificationHelper notifHelper;
    private FirebaseFirestore store;
    private CollectionReference coll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView = findViewById(R.id.recyclerView);
        store = FirebaseFirestore.getInstance();
        coll = store.collection("Questionares");
        questionares = new ArrayList<>();
        lqa = new ListQuestionaresAdapter(this, questionares);
        recView.setAdapter(lqa);
        recView.setLayoutManager(new LinearLayoutManager(this));

        coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                Questionare q = document.toObject(Questionare.class);
                questionares.add(q);
            }
            lqa.notifyDataSetChanged();
        });

        notifHelper = new NotificationHelper(this);

        findViewById(R.id.buttonAddQuestionare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("QuestionareId", new Questionare("", Questionare.Status.UNKNOWN, 0, "", "", "", new ArrayList<>()));
                //intent.putExtra("QuestionareId", "");
                startActivityForResult(intent, 2);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                questionares.clear();
                coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                        Questionare q = document.toObject(Questionare.class);
                        questionares.add(q);
                    }
                    lqa.notifyDataSetChanged();
                });
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

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
        notifHelper.PushAlert(questionare.getName() + " was deleted!");
    }
}