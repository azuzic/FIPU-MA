package hr.fipu.zadatak_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

public class HomeFragment extends Fragment {

    private BookViewModel viewModel;
    private TextView randomBookText;

    private Book currentBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        randomBookText = view.findViewById(R.id.randomBookText);
        Button nextBtn = view.findViewById(R.id.nextBookBtn);
        Button addFavBtn = view.findViewById(R.id.addFavoriteBtn);

        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        nextBtn.setOnClickListener(v -> showRandomBook());

        addFavBtn.setOnClickListener(v -> {
            if (currentBook != null) {
                viewModel.addFavorite(currentBook);
            }
        });

        viewModel.getBooks().observe(getViewLifecycleOwner(), books -> showRandomBook());

        return view;
    }

    private void showRandomBook() {
        List<Book> books = viewModel.getBooks().getValue();
        if (books == null || books.isEmpty()) {
            randomBookText.setText("No books yet!");
            currentBook = null;
            return;
        }

        int index = (int) (Math.random() * books.size());
        currentBook = books.get(index);

        randomBookText.setText(currentBook.getTitle() + "\nby " + currentBook.getAuthor());
    }

}
