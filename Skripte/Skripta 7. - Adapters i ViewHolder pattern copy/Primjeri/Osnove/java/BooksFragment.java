package hr.fipu.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class BooksFragment extends Fragment {


    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            EditText editText = view.findViewById(R.id.inputKnjiga);
            String input = editText.getText().toString();
            viewModel.selectItem(input);
        });

        return view;
    }
}