package hr.fipu.room2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertBook(Book book);

    @Insert
    long insert(Book book);

    @Insert
    void insertReview(BookReview review);

    @Delete
    void deleteBook(Book book);

    @Query("SELECT * FROM books ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM books WHERE publishedYear >= :year ORDER BY title ASC")
    LiveData<List<Book>> getBooksByYear(int year);

    @Query("SELECT * FROM book_reviews WHERE bookId = :bookId ORDER BY date_reviewed DESC")
    LiveData<List<BookReview>> getReviewsByBook(int bookId);

    @Query("SELECT AVG(rating) FROM book_reviews WHERE bookId = :bookId")
    LiveData<Double> getAverageRating(int bookId);
}
