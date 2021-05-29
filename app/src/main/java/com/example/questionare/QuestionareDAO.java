package com.example.questionare;

import java.util.ArrayList;

public interface QuestionareDAO {
    ArrayList<Questionare> getAllQuestionares();
    Questionare getQuestionareById(String id);
    Questionare getQuestionare(Questionare questionare);
    void AddQuestionare(Questionare questionare);
    void UpdateQuestionare(Questionare questionare);
    void DeleteQuestionareById(String id);
    void DeleteQuestionare(Questionare questionare);
}
