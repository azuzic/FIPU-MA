package hr.fipu.kosaricaprimjerdiffutils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class KosaricaDiffCallback extends DiffUtil.Callback {
    private final List<Proizvod> oldProizvodi;
    private final List<Proizvod> newProizvodi;

    public KosaricaDiffCallback(List<Proizvod> oldProizvodi, List<Proizvod> newProizvodi) {
        this.oldProizvodi = oldProizvodi;
        this.newProizvodi = newProizvodi;
    }

    @Override
    public int getOldListSize() { return oldProizvodi.size(); }

    @Override
    public int getNewListSize() { return newProizvodi.size(); }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldProizvodi.get(oldItemPosition).equals(newProizvodi.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldProizvodi.get(oldItemPosition).getNaziv().equals(newProizvodi.get(newItemPosition).getNaziv()) &&
                oldProizvodi.get(oldItemPosition).getCijena()==newProizvodi.get(newItemPosition).getCijena() &&
                oldProizvodi.get(oldItemPosition).getKolicina()==newProizvodi.get(newItemPosition).getKolicina());
    }
}
