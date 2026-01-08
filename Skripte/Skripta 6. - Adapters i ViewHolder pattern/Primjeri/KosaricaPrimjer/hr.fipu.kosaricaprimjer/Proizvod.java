package hr.fipu.kosaricaprimjer;

public class Proizvod {
    private String naziv;
    private double cijena;
    private int kolicina;

    public Proizvod(String naziv, double cijena, int kolicina) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.kolicina = kolicina;
    }

    public String getNaziv() { return naziv; }
    public double getCijena() { return cijena; }
    public int getKolicina() { return kolicina; }

    public void povecajKolicinu(int kolicina) {
        if (this.kolicina + kolicina < 100) this.kolicina += kolicina;
    }
    public void smanjiKolicinu(int kolicina) {
        if (this.kolicina - kolicina > 0) this.kolicina -= kolicina;
    }
    public double izracunajUkupnuCijenu() { return cijena * kolicina; }
}
