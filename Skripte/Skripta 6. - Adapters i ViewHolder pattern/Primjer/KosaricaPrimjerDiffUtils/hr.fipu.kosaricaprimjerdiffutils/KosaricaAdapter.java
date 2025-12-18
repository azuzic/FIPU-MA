package hr.fipu.kosaricaprimjerdiffutils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KosaricaAdapter extends RecyclerView.Adapter<KosaricaAdapter.ProizvodViewHolder> {

    private static Kosarica kosarica;

    public interface OnItemsChangedListener {
        void onItemChanged();
    }
    private OnItemsChangedListener itemsChangedListener;
    public void setOnItemsChangedListener(OnItemsChangedListener listener) {
        this.itemsChangedListener = listener;
    }

    public KosaricaAdapter(Kosarica kosarica) {
        KosaricaAdapter.kosarica = kosarica;
    }

    public void setProizvodi(List<Proizvod> newProizvodi) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new KosaricaDiffCallback(kosarica.getProizvodi(), newProizvodi));
        kosarica.setProizvodi(new ArrayList<>(newProizvodi));
        diffResult.dispatchUpdatesTo(this);
    }

    public class ProizvodViewHolder extends RecyclerView.ViewHolder {

        TextView textNaziv;
        TextView textCijena;
        TextView textKolicina;
        ImageButton buttonAdd;
        ImageButton buttonMinus;
        ImageButton buttonRemove;


        public ProizvodViewHolder(@NonNull View proizvod_item_view) {
            super(proizvod_item_view);
            textNaziv = proizvod_item_view.findViewById(R.id.textNaziv);
            textCijena = proizvod_item_view.findViewById(R.id.textCijena);
            textKolicina = proizvod_item_view.findViewById(R.id.textKolicina);
            buttonAdd = proizvod_item_view.findViewById(R.id.btnAdd);
            buttonMinus = proizvod_item_view.findViewById(R.id.btnMinus);
            buttonRemove = proizvod_item_view.findViewById(R.id.btnRemove);
        }
        void postaviProizvod(Proizvod proizvod) {
            textNaziv.setText(proizvod.getNaziv());
            textCijena.setText(String.valueOf(proizvod.getCijena()));
            textKolicina.setText(String.valueOf(proizvod.getKolicina()));
            buttonAdd.setOnClickListener(view -> {
                proizvod.povecajKolicinu(1);
                textKolicina.setText(String.valueOf(proizvod.getKolicina()));
                itemsChangedListener.onItemChanged();
            });
            buttonMinus.setOnClickListener(view -> {
                proizvod.smanjiKolicinu(1);
                textKolicina.setText(String.valueOf(proizvod.getKolicina()));
                itemsChangedListener.onItemChanged();
            });
            buttonRemove.setOnClickListener(view -> {
                Kosarica newKosarica = new Kosarica();
                newKosarica.setProizvodi(new ArrayList<>(kosarica.getProizvodi()));
                newKosarica.ukloniProizvod(proizvod);
                setProizvodi(newKosarica.getProizvodi());
                itemsChangedListener.onItemChanged();
            });
        }
    }

    @NonNull @Override
    public ProizvodViewHolder onCreateViewHolder(@NonNull ViewGroup kosarica_recycler, int viewType) {
        View proizvod_item_view = LayoutInflater.from(kosarica_recycler.getContext())
                .inflate(R.layout.proizvod_item, kosarica_recycler, false);
        return new ProizvodViewHolder(proizvod_item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProizvodViewHolder proizvodViewHolder, int position) {
        Proizvod proizvod = kosarica.getProizvodi().get(position);
        proizvodViewHolder.postaviProizvod(proizvod);
    }

    @Override
    public int getItemCount() {
        return kosarica.getProizvodi().size();
    }

}