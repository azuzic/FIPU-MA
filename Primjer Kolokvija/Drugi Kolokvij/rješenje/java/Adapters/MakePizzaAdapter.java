package hr.fipu.primjer_drugog_kolokvija.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import hr.fipu.primjer_drugog_kolokvija.Database.IngredientEntity;
import hr.fipu.primjer_drugog_kolokvija.Model.PizzaIngredientInfo;
import hr.fipu.primjer_drugog_kolokvija.R;

public class MakePizzaAdapter extends RecyclerView.Adapter<MakePizzaAdapter.MakePizzaViewHolder> {
    
    private List<IngredientEntity> ingredients = new ArrayList<>();
    private final Map<Integer, Integer> selectedQuantities = new HashMap<>();
    private final Map<Integer, Boolean> selectedCheckboxes = new HashMap<>();
    private final OnPriceChangeListener priceChangeListener;
    
    public interface OnPriceChangeListener {
        void onPriceChanged();
    }
    
    public MakePizzaAdapter(OnPriceChangeListener priceChangeListener) {
        this.priceChangeListener = priceChangeListener;
    }
    
    @NonNull
    @Override
    public MakePizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_make_pizza_ingredient, parent, false);
        return new MakePizzaViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MakePizzaViewHolder holder, int position) {
        IngredientEntity ingredient = ingredients.get(position);
        
        holder.nameTextView.setText(ingredient.getName());
        holder.availableQuantityTextView.setText(String.format(Locale.US, "Dostupno: %d g", ingredient.getQuantity()));
        holder.pricePerGramTextView.setText(String.format(Locale.US, "€%.3f/g", ingredient.getPricePerGram()));

        boolean isSelected = selectedCheckboxes.getOrDefault(ingredient.getId(), false);
        int quantity = selectedQuantities.getOrDefault(ingredient.getId(), 0);
        
        holder.checkBox.setChecked(isSelected);
        holder.quantityEditText.setText(quantity > 0 ? String.valueOf(quantity) : "");
        holder.quantityEditText.setEnabled(isSelected);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedCheckboxes.put(ingredient.getId(), isChecked);
            holder.quantityEditText.setEnabled(isChecked);
            if (!isChecked) {
                holder.quantityEditText.setText("");
                selectedQuantities.remove(ingredient.getId());
            }
            updatePriceDisplay(holder, ingredient);
            priceChangeListener.onPriceChanged();
        });

        holder.quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    try {
                        int qty = Integer.parseInt(s.toString());
                        selectedQuantities.put(ingredient.getId(), qty);
                    } catch (NumberFormatException e) {
                        selectedQuantities.remove(ingredient.getId());
                    }
                } else {
                    selectedQuantities.remove(ingredient.getId());
                }
                updatePriceDisplay(holder, ingredient);
                priceChangeListener.onPriceChanged();
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        updatePriceDisplay(holder, ingredient);
    }
    
    private void updatePriceDisplay(MakePizzaViewHolder holder, IngredientEntity ingredient) {
        int quantity = selectedQuantities.getOrDefault(ingredient.getId(), 0);
        if (quantity > 0) {
            double price = quantity * ingredient.getPricePerGram();
            holder.ingredientPriceTextView.setText(String.format(Locale.US, "€%.2f", price));
        } else {
            holder.ingredientPriceTextView.setText("€0.00");
        }
    }
    
    @Override
    public int getItemCount() {
        return ingredients.size();
    }
    
    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    public double calculateTotalPrice() {
        double total = 0;
        for (int ingredientId : selectedQuantities.keySet()) {
            int quantity = selectedQuantities.get(ingredientId);
            for (IngredientEntity ingredient : ingredients) {
                if (ingredient.getId() == ingredientId) {
                    total += quantity * ingredient.getPricePerGram();
                    break;
                }
            }
        }
        return total;
    }
    
    public List<PizzaIngredientInfo> getSelectedIngredients() {
        List<PizzaIngredientInfo> selected = new ArrayList<>();
        for (int ingredientId : selectedQuantities.keySet()) {
            int quantity = selectedQuantities.get(ingredientId);
            if (quantity > 0) {
                for (IngredientEntity ingredient : ingredients) {
                    if (ingredient.getId() == ingredientId) {
                        selected.add(new PizzaIngredientInfo(
                            ingredientId,
                            ingredient.getName(),
                            quantity,
                            ingredient.getPricePerGram()
                        ));
                        break;
                    }
                }
            }
        }
        return selected;
    }
    
    public boolean hasSelectedIngredients() {
        for (int quantity : selectedQuantities.values()) {
            if (quantity > 0) {
                return true;
            }
        }
        return false;
    }
    
    public void clearSelection() {
        selectedQuantities.clear();
        selectedCheckboxes.clear();
        notifyDataSetChanged();
    }
    
    public static class MakePizzaViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView nameTextView;
        TextView availableQuantityTextView;
        TextView pricePerGramTextView;
        EditText quantityEditText;
        TextView ingredientPriceTextView;
        
        public MakePizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.ingredient_checkbox);
            nameTextView = itemView.findViewById(R.id.ingredient_name_text_view);
            availableQuantityTextView = itemView.findViewById(R.id.available_quantity_text_view);
            pricePerGramTextView = itemView.findViewById(R.id.price_per_gram_text_view);
            quantityEditText = itemView.findViewById(R.id.quantity_input_edit_text);
            ingredientPriceTextView = itemView.findViewById(R.id.ingredient_price_text_view);
        }
    }
}
