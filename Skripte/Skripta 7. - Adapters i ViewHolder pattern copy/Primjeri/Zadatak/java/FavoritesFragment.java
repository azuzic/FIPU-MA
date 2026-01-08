package hr.fipu.zadatak_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesFragment extends Fragment {

    private BookViewModel viewModel;
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        recyclerView = view.findViewById(R.id.favRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        // Observer za favorite knjige
        viewModel.getFavorites().observe(getViewLifecycleOwner(), books -> adapter.setBooks(books));

        return view;
    }
}