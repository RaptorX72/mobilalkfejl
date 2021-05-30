package com.example.questionare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private FirebaseFirestore store;
    private CollectionReference coll;
    private ArrayList<Questionare> questionares = new ArrayList<>();
    private SharedPreferences preferences;
    private String searchText = "";
    boolean includeReitred = false;
    boolean includeUnknown = false;
    private TextView searchTB;
    private CheckBox cbUnknown;
    private CheckBox cbRetired;
    private RecyclerView recView;
    private ListQuestionaresSearchAdapter lqsa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        store = FirebaseFirestore.getInstance();
        coll = store.collection("Questionares");
        recView = findViewById(R.id.recyclerViewSearch);
        searchTB = findViewById(R.id.textBoxSearch);
        cbUnknown = findViewById(R.id.checkBoxIncUnknown);
        cbRetired = findViewById(R.id.checkBoxIncRetired);
        lqsa = new ListQuestionaresSearchAdapter(this, questionares);
        recView.setAdapter(lqsa);
        recView.setLayoutManager(new LinearLayoutManager(this));

        coll.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots) questionares.add(document.toObject(Questionare.class));
            lqsa.notifyDataSetChanged();
        });

        findViewById(R.id.buttonSearchQuestionares).setOnClickListener(v -> {
            questionares.clear();
            searchText = searchTB.getText().toString();
            includeUnknown = cbUnknown.isChecked();
            includeReitred = cbRetired.isChecked();
            coll.orderBy("name").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
                for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                    Questionare q = document.toObject(Questionare.class);
                    if (!includeUnknown && q.getStatus() == Questionare.Status.UNKNOWN) continue;
                    if (!includeReitred && q.getStatus() == Questionare.Status.RETIRED) continue;
                    if (q.getName().contains(searchText)) questionares.add(q);
                }
                lqsa.notifyDataSetChanged();
            });
            lqsa = new ListQuestionaresSearchAdapter(SearchActivity.this, questionares);
            recView.setAdapter(lqsa);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences != null) {
            searchTB.setText(preferences.getString("searchterm", ""));
            cbUnknown.setChecked(preferences.getBoolean("includeUnknown", false));
            cbRetired.setChecked(preferences.getBoolean("includeRetired", false));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("searchterm", searchTB.getText().toString());
            editor.putBoolean("includeUnknown", cbUnknown.isChecked());
            editor.putBoolean("includeRetired", cbRetired.isChecked());
            editor.apply();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}