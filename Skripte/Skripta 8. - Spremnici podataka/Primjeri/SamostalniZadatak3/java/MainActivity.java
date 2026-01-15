package hr.fipu.room2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hr.fipu.room2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private AppDatabase database;
    private FloatingActionButton fabAddBook;
    private EditText filterYear;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseProvider.getInstance(this);

        recyclerView = findViewById(R.id.recycler_books);
        fabAddBook = findViewById(R.id.fab_add_book);
        filterYear = findViewById(R.id.filter_year);
        filterButton = findViewById(R.id.filter_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this::onBookClick);
        recyclerView.setAdapter(adapter);

        loadBooks();

        fabAddBook.setOnClickListener(v -> showAddBookDialog());
        filterButton.setOnClickListener(v -> filterBooks());
    }

    private void loadBooks() {
        database.bookDao().getAllBooks().observe(this, books -> {
            adapter.setBooks(books);
            for (Book book : books) {
                database.bookDao().getAverageRating(book.id).observe(this,
                        rating -> adapter.setRating(book.id, rating));
            }
        });
    }

    private void filterBooks() {
        String year = filterYear.getText().toString();
        if (year.isEmpty()) {
            loadBooks();
            return;
        }

        int yearInt = Integer.parseInt(year);
        database.bookDao().getBooksByYear(yearInt).observe(this, books -> {
            adapter.setBooks(books);
            for (Book book : books) {
                database.bookDao().getAverageRating(book.id).observe(this,
                        rating -> adapter.setRating(book.id, rating));
            }
        });
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null);

        EditText titleInput = dialogView.findViewById(R.id.input_title);
        EditText authorInput = dialogView.findViewById(R.id.input_author);
        EditText isbnInput = dialogView.findViewById(R.id.input_isbn);
        EditText pagesInput = dialogView.findViewById(R.id.input_pages);
        EditText yearInput = dialogView.findViewById(R.id.input_year);

        builder.setView(dialogView)
                .setPositiveButton("Dodaj", (dialog, which) -> {
                    String title = titleInput.getText().toString();
                    String author = authorInput.getText().toString();
                    String isbn = isbnInput.getText().toString();
                    int pages = Integer.parseInt(pagesInput.getText().toString());
                    int year = Integer.parseInt(yearInput.getText().toString());

                    Book book = new Book(title, author, isbn, pages, year,
                            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

                    new Thread(() -> {
                        database.bookDao().insertBook(book);
                        Log.d("ROOM", "Knjiga dodana");
                    }).start();
                })
                .setNegativeButton("Odustani", null)
                .show();
    }

    private void onBookClick(Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_book_reviews, null);

        RecyclerView reviewsRecycler = dialogView.findViewById(R.id.reviews_recycler);
        EditText ratingInput = dialogView.findViewById(R.id.input_rating);
        EditText reviewInput = dialogView.findViewById(R.id.input_review_text);
        Button addReviewBtn = dialogView.findViewById(R.id.btn_add_review);
        Button deleteBookBtn = dialogView.findViewById(R.id.btn_delete_book);

        reviewsRecycler.setLayoutManager(new LinearLayoutManager(this));
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        reviewsRecycler.setAdapter(reviewAdapter);

        database.bookDao().getReviewsByBook(book.id).observe(this,
                reviews -> reviewAdapter.setReviews(reviews));

        AlertDialog dialog = builder.setView(dialogView)
                .setNegativeButton("Zatvori", null)
                .show();

        addReviewBtn.setOnClickListener(v -> {
            int rating = Integer.parseInt(ratingInput.getText().toString());
            String text = reviewInput.getText().toString();

            BookReview review = new BookReview(book.id, rating, text,
                    new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

            new Thread(() -> {
                database.bookDao().insertReview(review);
                Log.d("ROOM", "Recenzija dodana");
            }).start();
        });

        deleteBookBtn.setOnClickListener(v -> {
            new Thread(() -> {
                database.bookDao().deleteBook(book);
                runOnUiThread(dialog::dismiss);
            }).start();
            dialog.dismiss();
        });
    }
}