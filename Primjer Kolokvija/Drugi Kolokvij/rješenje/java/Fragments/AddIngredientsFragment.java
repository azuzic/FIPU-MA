package hr.fipu.primjer_drugog_kolokvija.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hr.fipu.primjer_drugog_kolokvija.Adapters.IngredientAdapter;
import hr.fipu.primjer_drugog_kolokvija.Database.IngredientEntity;
import hr.fipu.primjer_drugog_kolokvija.R;
import hr.fipu.primjer_drugog_kolokvija.ViewModel.SharedViewModel;

public class AddIngredientsFragment extends Fragment implements IngredientAdapter.OnIngredientListener {
    
    private SharedViewModel viewModel;
    private IngredientAdapter adapter;
    private EditText ingredientNameEditText;
    private EditText quantityEditText;
    private EditText priceEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_ingredients, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        ingredientNameEditText = view.findViewById(R.id.ingredient_name_edit_text);
        quantityEditText = view.findViewById(R.id.quantity_edit_text);
        priceEditText = view.findViewById(R.id.price_edit_text);
        Button addIngredientButton = view.findViewById(R.id.add_ingredient_button);
        RecyclerView ingredientsRecyclerView = view.findViewById(R.id.ingredients_list_recycler_view);
        
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        
        adapter = new IngredientAdapter(this);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ingredientsRecyclerView.setAdapter(adapter);

        viewModel.getAllIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            adapter.setIngredients(ingredients);
        });
        
        addIngredientButton.setOnClickListener(v -> addNewIngredient());
    }
    
    private void addNewIngredient() {
        String name = ingredientNameEditText.getText().toString().trim();
        String quantityStr = quantityEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        
        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Molim unesite naziv sastojka", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (quantityStr.isEmpty()) {
            Toast.makeText(requireContext(), "Molim unesite količinu", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (priceStr.isEmpty()) {
            Toast.makeText(requireContext(), "Molim unesite cijenu po gramu", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);

            IngredientEntity existing = viewModel.getPizzeria().getDatabase().ingredientDao().getIngredientByName(name);
            if (existing != null) {
                Toast.makeText(requireContext(), "Sastojak s tim nazivom već postoji!", Toast.LENGTH_SHORT).show();
                return;
            }
            
            viewModel.getPizzeria().addIngredientToDatabase(name, quantity, price);
            Toast.makeText(requireContext(), "Sastojak je uspješno dodan!", Toast.LENGTH_SHORT).show();

            ingredientNameEditText.setText("");
            quantityEditText.setText("");
            priceEditText.setText("");
            
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Molim unesite validne brojeve", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onDeleteClick(int ingredientId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Obriši sastojak?")
                .setMessage("Jeste li sigurni da želite obrisati ovaj sastojak?")
                .setPositiveButton("Obriši", (dialog, which) -> {
                    viewModel.getPizzeria().removeIngredientFromDatabase(ingredientId);
                    Toast.makeText(requireContext(), "Sastojak je obrisan", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Otkaži", null)
                .show();
    }
    
    @Override
    public void onEditClick(IngredientEntity ingredient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.dialog_edit_ingredient, null);
        
        EditText editQuantityEditText = dialogView.findViewById(R.id.edit_quantity_edit_text);
        EditText editPriceEditText = dialogView.findViewById(R.id.edit_price_edit_text);
        
        editQuantityEditText.setText(String.valueOf(ingredient.getQuantity()));
        editPriceEditText.setText(String.valueOf(ingredient.getPricePerGram()));
        
        builder.setView(dialogView)
                .setTitle("Uredi: " + ingredient.getName())
                .setPositiveButton("Spremi", (dialog, which) -> {
                    try {
                        int newQuantity = Integer.parseInt(editQuantityEditText.getText().toString());
                        double newPrice = Double.parseDouble(editPriceEditText.getText().toString());
                        
                        ingredient.setQuantity(newQuantity);
                        ingredient.setPricePerGram(newPrice);
                        viewModel.getPizzeria().getDatabase().ingredientDao().update(ingredient);
                        
                        Toast.makeText(requireContext(), "Sastojak je ažuriiran", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(requireContext(), "Molim unesite validne brojeve", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Otkaži", null)
                .show();
    }
}
