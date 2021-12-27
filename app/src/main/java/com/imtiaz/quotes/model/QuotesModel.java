package com.imtiaz.quotes.model;

public class QuotesModel {
    String text;
    String author = "";


    public QuotesModel(String text, String author) {
        this.text = text;
        this.author = author;

    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }



}
