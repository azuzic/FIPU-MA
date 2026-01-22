package hr.fipu.primjer_drugog_kolokvija.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hr.fipu.primjer_drugog_kolokvija.Database.IngredientEntity;
import hr.fipu.primjer_drugog_kolokvija.R;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    
    private List<IngredientEntity> ingredients = new ArrayList<>();
    private final OnIngredientListener listener;
    
    public interface OnIngredientListener {
        void onDeleteClick(int ingredientId);
        void onEditClick(IngredientEntity ingredient);
    }
    
    public IngredientAdapter(OnIngredientListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientEntity ingredient = ingredients.get(position);
        
        holder.nameTextView.setText(ingredient.getName());
        holder.quantityTextView.setText(String.format(Locale.US, "%d g", ingredient.getQuantity()));
        holder.priceTextView.setText(String.format(Locale.US, "€%.3f/g", ingredient.getPricePerGram()));
        
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(ingredient.getId()));
        holder.editButton.setOnClickListener(v -> listener.onEditClick(ingredient));
    }
    
    @Override
    public int getItemCount() {
        return ingredients.size();
    }
    
    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView quantityTextView;
        TextView priceTextView;
        Button deleteButton;
        Button editButton;
        
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredient_name);
            quantityTextView = itemView.findViewById(R.id.ingredient_quantity);
            priceTextView = itemView.findViewById(R.id.ingredient_price);
            deleteButton = itemView.findViewById(R.id.ingredient_delete_btn);
            editButton = itemView.findViewById(R.id.ingredient_edit_btn);
        }
    }
}
