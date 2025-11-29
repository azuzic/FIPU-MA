package hr.fipu.primjer_prvog_kolokvija_z2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Knjiznica implements Serializable {
    List<Knjiga> knjige = new ArrayList<>();

    private static Knjiznica instance;
    private Knjiznica() {
    }
    public static synchronized Knjiznica getInstance() {
        if (instance == null) {
            instance = new Knjiznica();
        }
        return instance;
    }

    void dodajKnjigu(Knjiga knjiga) {
        knjige.add(knjiga);
    }

    boolean ukloniKnjigu(String naslov) {
        if (nadjiKnjigu(naslov) == null) return false;
        knjige.removeIf(knjiga -> knjiga.getNaslov().equals(naslov));
        return true;
    }

    Knjiga nadjiKnjigu(String naslov) {
        for (Knjiga knjiga : knjige) {
            if (knjiga.getNaslov().equals(naslov)) {
                return knjiga;
            }
        }
        return null;
    }

    List<Knjiga> dohvatiSveKnjige() {
        return knjige;
    }
}
