package hr.fipu.zadatak_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BooksFragment extends Fragment {

    private BookViewModel viewModel;
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        recyclerView = view.findViewById(R.id.booksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        EditText titleInput = view.findViewById(R.id.bookTitleInput);
        EditText authorInput = view.findViewById(R.id.bookAuthorInput);
        Button addBtn = view.findViewById(R.id.addBookBtn);

        addBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String author = authorInput.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            Book book = new Book(title, author);
            viewModel.addBook(book);

            titleInput.setText("");
            authorInput.setText("");
        });

        // Observer
        viewModel.getBooks().observe(getViewLifecycleOwner(), books -> adapter.setBooks(books));

        return view;
    }
}
