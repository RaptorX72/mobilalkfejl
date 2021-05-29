package com.example.questionare;

import android.util.Log;

import java.util.ArrayList;

public class IQuestionareDAO implements QuestionareDAO {
    private static IQuestionareDAO instance;
    private ArrayList<Questionare> questionares;

    public static IQuestionareDAO getInstance() {
        if (instance == null) instance = new IQuestionareDAO();
        return instance;
    }

    public IQuestionareDAO() {
        questionares = new ArrayList<>();
        Log.d("Activity", "lefutott!");
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Question> questions2 = new ArrayList<>();

        questions.add( new Question(
                "linkid1",
                Question.QuestionType.INTEGER,
                "desc",
                "How old are you?"
        ));

        questions.add( new Question(
                "linkid2",
                Question.QuestionType.STRING,
                "desc",
                "What is your name?"
        ));

        questions.add( new Question(
                "linkid3",
                Question.QuestionType.BOOLEAN,
                "desc",
                "Are you studying in a university?"
        ));


        questions2.add( new Question(
                "linkid1",
                Question.QuestionType.INTEGER,
                "desc",
                "How old are you?"
        ));

        questions2.add( new Question(
                "linkid2",
                Question.QuestionType.STRING,
                "desc",
                "What is your name?"
        ));

        questions2.add( new Question(
                "linkid3",
                Question.QuestionType.BOOLEAN,
                "desc",
                "Are you studying in a university?"
        ));

        questionares.add( new Questionare(
                "uid1",
                Questionare.Status.ACTIVE,
                0,
                "First questionare",
                "the first",
                "me",
                questions
        ));

        questionares.add( new Questionare(
                "uid2",
                Questionare.Status.RETIRED,
                0,
                "Second questionare",
                "the first",
                "me",
                questions2
        ));
    }

    @Override
    public ArrayList<Questionare> getAllQuestionares() {
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
        for (int i = 0; i < questionares.size(); i++) {
            Questionare q = questionares.get(i);
            if (q.getUri().equals(questionare.getUri())) {
                questionares.set(i, questionare);
                break;
            }
        }
    }

    @Override
    public void DeleteQuestionareById(String id) {

    }

    @Override
    public void DeleteQuestionare(Questionare questionare) {
        for (int i = 0; i < questionares.size(); i++) {
            Questionare q = questionares.get(i);
            if (q.getUri().equals(questionare.getUri())) {
                questionares.remove(i);
                break;
            }
        }
    }

    @Override
    public void AddQuestionare(Questionare questionare) {
        Log.d("DAO", "added questionare " + questionare.getName());
        questionares.add(questionare);
    }
}
