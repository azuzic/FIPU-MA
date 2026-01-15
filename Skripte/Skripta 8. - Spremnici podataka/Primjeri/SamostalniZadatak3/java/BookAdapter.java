package hr.fipu.room2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books = new ArrayList<>();
    private final Map<Integer, Double> ratings = new HashMap<>();
    private final OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public BookAdapter(OnBookClickListener listener) {
        this.listener = listener;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public void setRating(int bookId, Double rating) {
        ratings.put(bookId, rating);
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book, ratings.getOrDefault(book.id, 0.0));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, authorView, yearView, ratingView;

        BookViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.book_title);
            authorView = itemView.findViewById(R.id.book_author);
            yearView = itemView.findViewById(R.id.book_year);
            ratingView = itemView.findViewById(R.id.book_rating);

            itemView.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (listener != null && pos >= 0) {
                    listener.onBookClick(books.get(pos));
                }
            });
        }

        void bind(Book book, Double rating) {
            titleView.setText(book.title);
            authorView.setText(book.author);
            yearView.setText("Izdano: " + book.publishedYear);
            ratingView.setText("Ocjena:" + rating + " /5.0");
        }
    }
}
