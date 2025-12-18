package hr.fipu.kosaricaprimjer;

import java.util.ArrayList;
import java.util.List;

public class Kosarica {
    private List<Proizvod> proizvodi = new ArrayList<>();

    public List<Proizvod> getProizvodi() { return proizvodi; }

    public void dodajProizvod(Proizvod proizvod){ proizvodi.add(proizvod); }

    public void ukloniProizvod(Proizvod proizvod){ proizvodi.remove(proizvod); }

    public double ukupanIznos() {
        double ukupanIznos = 0;
        for (Proizvod proizvod : proizvodi)
            ukupanIznos += proizvod.izracunajUkupnuCijenu();
        return Math.round(ukupanIznos * 100.0) / 100.0;
    }
}
