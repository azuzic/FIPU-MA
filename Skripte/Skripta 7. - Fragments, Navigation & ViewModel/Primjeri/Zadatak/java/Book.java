package hr.fipu.zadatak_fragments;

import androidx.annotation.NonNull;

public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    @NonNull
    @Override
    public String toString() {
        return title + " by " + author;
    }
}
