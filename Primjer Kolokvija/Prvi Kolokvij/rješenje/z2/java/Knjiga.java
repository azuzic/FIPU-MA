package hr.fipu.primjer_prvog_kolokvija_z2;

public class Knjiga {
    private String naslov;
    private String autor;
    private int godinaIzdavanja;
    private String opis;

    public Knjiga(String naslov, String autor, int godinaIzdavanja, String opis) {
        this.naslov = naslov;
        this.autor = autor;
        this.godinaIzdavanja = godinaIzdavanja;
        this.opis = opis;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getAutor() {
        return autor;
    }

    public int getGodinaIzdavanja() {
        return godinaIzdavanja;
    }

    public String getOpis() {
        return opis;
    }
}
