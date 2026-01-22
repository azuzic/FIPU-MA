package hr.fipu.primjer_drugog_kolokvija.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import hr.fipu.primjer_drugog_kolokvija.Adapters.MakePizzaAdapter;
import hr.fipu.primjer_drugog_kolokvija.Model.PizzaIngredientInfo;
import hr.fipu.primjer_drugog_kolokvija.R;
import hr.fipu.primjer_drugog_kolokvija.ViewModel.SharedViewModel;

public class MakePizzaFragment extends Fragment {
    
    private SharedViewModel viewModel;
    private MakePizzaAdapter adapter;
    private EditText pizzaNameEditText;
    private Button createPizzaButton;
    private TextView totalPriceTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_make_pizza, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        pizzaNameEditText = view.findViewById(R.id.pizza_name_edit_text);
        totalPriceTextView = view.findViewById(R.id.total_price_text_view);
        createPizzaButton = view.findViewById(R.id.create_pizza_button);
        RecyclerView ingredientsRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
        
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        
        adapter = new MakePizzaAdapter(this::updateTotalPrice);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ingredientsRecyclerView.setAdapter(adapter);

        viewModel.getAllIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            adapter.setIngredients(ingredients);
        });

        pizzaNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCreateButtonState();
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        createPizzaButton.setOnClickListener(v -> createPizza());
    }
    
    private void updateTotalPrice() {
        double totalPrice = adapter.calculateTotalPrice();
        totalPriceTextView.setText(String.format(Locale.US, "Ukupna cijena: €%.2f", totalPrice));
        updateCreateButtonState();
    }
    
    private void updateCreateButtonState() {
        String pizzaName = pizzaNameEditText.getText().toString().trim();
        boolean hasSelectedIngredients = adapter.hasSelectedIngredients();
        createPizzaButton.setEnabled(!pizzaName.isEmpty() && hasSelectedIngredients);
    }
    
    private void createPizza() {
        String pizzaName = pizzaNameEditText.getText().toString().trim();
        List<PizzaIngredientInfo> selectedIngredients = adapter.getSelectedIngredients();
        
        if (pizzaName.isEmpty()) {
            Toast.makeText(requireContext(), "Molim unesite naziv pizze", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (selectedIngredients.isEmpty()) {
            Toast.makeText(requireContext(), "Molim odaberite barem jedan sastojak", Toast.LENGTH_SHORT).show();
            return;
        }

        for (PizzaIngredientInfo ingredient : selectedIngredients) {
            if (!viewModel.getPizzeria().checkIngredientAvailability(ingredient.getIngredientId(), ingredient.getQuantityUsed())) {
                Toast.makeText(requireContext(), "Nema dovoljno " + ingredient.getIngredientName(), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        
        viewModel.getPizzeria().createPizza(pizzaName, selectedIngredients);
        Toast.makeText(requireContext(), "Piza je uspješno kreirana!", Toast.LENGTH_SHORT).show();

        pizzaNameEditText.setText("");
        adapter.clearSelection();
        totalPriceTextView.setText("Ukupna cijena: €0.00");
        updateCreateButtonState();
    }
}
