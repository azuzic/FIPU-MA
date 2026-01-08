package hr.fipu.zadatak_fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {

    private final MutableLiveData<List<Book>> books = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Book>> favorites = new MutableLiveData<>(new ArrayList<>());

    public BookViewModel() {
        List<Book> initialBooks = new ArrayList<>();
        initialBooks.add(new Book("1984", "George Orwell", "Fiction"));
        initialBooks.add(new Book("Sapiens", "Yuval Noah Harari", "Non-Fiction"));
        initialBooks.add(new Book("Dune", "Frank Herbert", "Sci-Fi"));
        initialBooks.add(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        initialBooks.add(new Book("A Brief History of Time", "Stephen Hawking", "Science"));
        books.setValue(initialBooks);
    }

    public LiveData<List<Book>> getBooks() { return books; }
    public LiveData<List<Book>> getFavorites() { return favorites; }

    public void addBook(Book book) {
        if (book == null || book.getTitle().isEmpty()) return;
        List<Book> current = new ArrayList<>(books.getValue());
        current.add(book);
        books.setValue(current);
    }

    public void addFavorite(Book book) {
        List<Book> current = new ArrayList<>(favorites.getValue());
        if (book != null && !current.contains(book)) {
            current.add(book);
            favorites.setValue(current);
        }
    }
}
