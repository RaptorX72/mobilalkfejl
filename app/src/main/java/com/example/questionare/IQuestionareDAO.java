package com.example.questionare;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IQuestionareDAO implements QuestionareDAO {
    private static IQuestionareDAO instance;
    private static ArrayList<Questionare> questionares;
    private static FirebaseFirestore store;
    private static CollectionReference coll;

    private static String PLACEHOLDER_HEY = "XBAykOTSMbI5GpZ723qf";

    public static IQuestionareDAO getInstance() {
        if (instance == null) {
            instance = new IQuestionareDAO();
            store = FirebaseFirestore.getInstance();
            coll = store.collection("Questionares");
        }
        return instance;
    }

    public IQuestionareDAO() {
        questionares = new ArrayList<>();
        questionares = getAllQuestionares();
    }

    @Override
    public ArrayList<Questionare> getAllQuestionares() {
        questionares.clear();
        if (coll != null) {
            coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
                for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                    if (document.getId().equals(PLACEHOLDER_HEY)) continue;
                    Questionare q = document.toObject(Questionare.class);
                    questionares.add(q);
                }
            });
        }
        return questionares;
    }

    @Override
    public Questionare getQuestionareById(String id) {
        for (Questionare q : questionares) if (q.getUri().equals(id)) return q;
        return null;
    }

    @Override
    public Questionare getQuestionare(Questionare questionare) {
        for (Questionare q : questionares) if (q.getUri().equals(questionare.getUri())) return q;
        return null;
    }

    @Override
    public void UpdateQuestionare(Questionare questionare) {
        coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                if (document.getId().equals(PLACEHOLDER_HEY)) continue;
                Questionare q = document.toObject(Questionare.class);
                if (q.getUri().equals(questionare.getUri())) {
                    coll.document(document.getId()).delete();
                    coll.add(questionare);
                    questionares = getAllQuestionares();
                    return;
                }
            }
        });
    }

    @Override
    public void DeleteQuestionareById(String id) {

    }

    @Override
    public void DeleteQuestionare(Questionare questionare) {
        coll.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document: queryDocumentSnapshots) {
                if (document.getId().equals(PLACEHOLDER_HEY)) continue;
                Questionare q = document.toObject(Questionare.class);
                if (q.getUri().equals(questionare.getUri())) {
                    coll.document(document.getId()).delete();
                    questionares = getAllQuestionares();
                    return;
                }
            }
        });
    }

    @Override
    public void AddQuestionare(Questionare questionare) {
        coll.add(questionare);
        questionares.add(questionare);
    }

    @Override
    public ArrayList<Questionare> getQuestionares(String text, boolean unknown, boolean retired) {
        ArrayList<Questionare> results = new ArrayList<>();
        /*for (Questionare q : questionares) {
            if (text.isEmpty()) {
                results.add(q);
            }
            else if (q.getName().toLowerCase().contains(text.toLowerCase())) {
                results.add(q);
            }
        }*/
        return results;
    }
}
