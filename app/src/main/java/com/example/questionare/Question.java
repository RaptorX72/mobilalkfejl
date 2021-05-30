package com.example.questionare;

import java.io.Serializable;

public class Question implements Serializable {
    public enum QuestionType {
        BOOLEAN, DECIMAL, INTEGER, STRING
    }
    private String linkId;
    private QuestionType type;
    private String text;
    private String question;

    public Question() {

    }

    public Question(String linkId, QuestionType type, String text, String question/*, Answer valueSet*/) {
        this.linkId = linkId;
        this.type = type;
        this.text = text;
        this.question = question;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
