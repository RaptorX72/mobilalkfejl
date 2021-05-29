package com.example.questionare;

public class Answer {
    private Question.QuestionType type; //BOOLEAN, DECIMAL, INTEGER, STRING
    private boolean answerBool;
    private double answerDecimal;
    private int answerInteger;
    private String answerString;

    public Answer(boolean answerBool) {
        this.answerBool = answerBool;
        this.type = Question.QuestionType.BOOLEAN;
    }

    public Answer(double answerDecimal) {
        this.answerDecimal = answerDecimal;
        this.type = Question.QuestionType.DECIMAL;
    }

    public Answer(int answerInteger) {
        this.answerInteger = answerInteger;
        this.type = Question.QuestionType.INTEGER;
    }

    public Answer(String answerString) {
        this.answerString = answerString;
        this.type = Question.QuestionType.STRING;
    }

    public Question.QuestionType getType() {
        return type;
    }

    public boolean getAnswerBool() {
        return type == Question.QuestionType.BOOLEAN ? answerBool : null;
    }

    public double getAnswerDecimal() {
        return type == Question.QuestionType.DECIMAL ? answerDecimal : null;
    }

    public int getAnswerInteger() {
        return type == Question.QuestionType.INTEGER ? answerInteger : null;
    }

    public String getAnswerString() {
        return type == Question.QuestionType.STRING ? answerString : null;
    }
}
