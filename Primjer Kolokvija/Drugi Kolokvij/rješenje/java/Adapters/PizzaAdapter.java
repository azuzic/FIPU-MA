package hr.fipu.primjer_drugog_kolokvija.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hr.fipu.primjer_drugog_kolokvija.Database.PizzaEntity;
import hr.fipu.primjer_drugog_kolokvija.R;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    
    private List<PizzaEntity> pizzas = new ArrayList<>();
    private OnPizzaListener listener;
    
    public interface OnPizzaListener {
        void onDeleteClick(int pizzaId);
    }
    
    public PizzaAdapter(OnPizzaListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        PizzaEntity pizza = pizzas.get(position);
        
        holder.nameTextView.setText(pizza.getName());
        holder.priceTextView.setText(String.format(Locale.US, "€%.2f", pizza.getPrice()));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        holder.dateTextView.setText(sdf.format(new Date(pizza.getCreatedDate())));
        
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(pizza.getId()));
    }
    
    @Override
    public int getItemCount() {
        return pizzas.size();
    }
    
    public void setPizzas(List<PizzaEntity> pizzas) {
        this.pizzas = pizzas != null ? pizzas : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView dateTextView;
        Button deleteButton;
        
        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pizza_name);
            priceTextView = itemView.findViewById(R.id.pizza_price);
            dateTextView = itemView.findViewById(R.id.pizza_date);
            deleteButton = itemView.findViewById(R.id.pizza_delete_btn);
        }
    }
}
