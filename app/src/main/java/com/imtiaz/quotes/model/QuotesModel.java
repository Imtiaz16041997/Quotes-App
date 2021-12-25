package com.imtiaz.quotes.model;

public class QuotesModel {
    String text;
    String author;
    int id;

    public QuotesModel(String text, String author, int id) {
        this.text = text;
        this.author = author;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
