package hr.fipu.adaptersandviewholderpattern;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> items;
    private final OnItemClickListener listener;

    public MyAdapter(List<String> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String item, int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.itemText);
        }
        void bind(String item, OnItemClickListener listener, int position) {
            text.setText(item);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(item, position);
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String item =items.get(position);
        holder.bind(item, listener, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<String> newItems) {
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new MyDiffCallback(this.items, newItems));
        this.items = newItems;
        diffResult.dispatchUpdatesTo(this);
    }
}
