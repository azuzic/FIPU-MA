package hr.fipu.primjer_drugog_kolokvija.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.fipu.primjer_drugog_kolokvija.Adapters.PizzaAdapter;
import hr.fipu.primjer_drugog_kolokvija.Database.PizzaEntity;
import hr.fipu.primjer_drugog_kolokvija.R;
import hr.fipu.primjer_drugog_kolokvija.ViewModel.SharedViewModel;

public class HomeFragment extends Fragment implements PizzaAdapter.OnPizzaListener {
    
    private SharedViewModel viewModel;
    private PizzaAdapter adapter;
    EditText searchEditText;
    EditText minPriceEditText;
    EditText maxPriceEditText;
    private TextView totalRevenueTextView;
    private List<PizzaEntity> allPizzas = new ArrayList<>();
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.pizza_recycler_view);
        searchEditText = view.findViewById(R.id.search_pizza_edit_text);
        minPriceEditText = view.findViewById(R.id.min_price_edit_text);
        maxPriceEditText = view.findViewById(R.id.max_price_edit_text);
        totalRevenueTextView = view.findViewById(R.id.total_revenue_text_view);
        
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        adapter = new PizzaAdapter((int pizzaId) -> viewModel.getPizzeria().deletePizza(pizzaId));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getAllPizzas().observe(getViewLifecycleOwner(), pizzas -> {
            allPizzas = pizzas;
            adapter.setPizzas(pizzas);
        });

        viewModel.getTotalRevenue().observe(getViewLifecycleOwner(), revenue -> {
            if (revenue != null) totalRevenueTextView.setText("Ukupna zarada: €" + revenue);
            else totalRevenueTextView.setText("Ukupna zarada: €0.00");
        });

        setUpSearchListeners();
    }

    private void setUpSearchListeners() {
        TextWatcher searchWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPizzas();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        
        searchEditText.addTextChangedListener(searchWatcher);
        minPriceEditText.addTextChangedListener(searchWatcher);
        maxPriceEditText.addTextChangedListener(searchWatcher);
    }
    
    private void filterPizzas() {
        String searchText = searchEditText.getText().toString().toLowerCase();
        String minPriceStr = minPriceEditText.getText().toString();
        String maxPriceStr = maxPriceEditText.getText().toString();
        
        List<PizzaEntity> filteredPizzas = new ArrayList<>();
        
        for (PizzaEntity pizza : allPizzas) {
            boolean matchesSearch = searchText.isEmpty() || pizza.getName().toLowerCase().contains(searchText);
            
            boolean matchesPrice = true;
            if (!minPriceStr.isEmpty() || !maxPriceStr.isEmpty()) {
                double minPrice = minPriceStr.isEmpty() ? 0 : Double.parseDouble(minPriceStr);
                double maxPrice = maxPriceStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceStr);
                matchesPrice = pizza.getPrice() >= minPrice && pizza.getPrice() <= maxPrice;
            }
            
            if (matchesSearch && matchesPrice) {
                filteredPizzas.add(pizza);
            }
        }
        
        adapter.setPizzas(filteredPizzas);
    }

    @Override
    public void onDeleteClick(int pizzaId) {
        viewModel.getPizzeria().deletePizza(pizzaId);
    }
}
