package hr.fipu.room2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    @NonNull
    public String author;

    @NonNull
    public String isbn;

    public int pages;

    public int publishedYear;

    @ColumnInfo(name = "date_added")
    public String dateAdded;

    public Book(@NonNull String title, @NonNull String author, @NonNull String isbn, int pages, int publishedYear, String dateAdded) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
        this.publishedYear = publishedYear;
        this.dateAdded = dateAdded;
    }
}
