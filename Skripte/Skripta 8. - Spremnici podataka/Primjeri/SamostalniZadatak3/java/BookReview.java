package hr.fipu.room2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_reviews", foreignKeys = @ForeignKey(
        entity = Book.class,
        parentColumns = "id",
        childColumns = "bookId",
        onDelete = ForeignKey.CASCADE
))
public class BookReview {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int bookId;

    public int rating;

    public String reviewText;

    @ColumnInfo(name = "date_reviewed")
    public String dateReviewed;

    public BookReview(int bookId, int rating, String reviewText, String dateReviewed) {
        this.bookId = bookId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.dateReviewed = dateReviewed;
    }
}