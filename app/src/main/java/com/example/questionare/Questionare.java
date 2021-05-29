package com.example.questionare;


import java.io.Serializable;
import java.util.ArrayList;

public class Questionare implements Serializable {
    public enum Status {
        DRAFT, ACTIVE, RETIRED, UNKNOWN
    }
    private String uri;
    private Status status;
    private int version;
    private String name;
    private String title;
    private String publisherName;
    private ArrayList<Question> questions;

    public Questionare(String uri, Status status, int version, String name, String title, String publisherName, ArrayList<Question> questions) {
        this.uri = uri;
        this.status = status;
        this.version = version;
        this.name = name;
        this.title = title;
        this.publisherName = publisherName;
        this.questions = questions;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
