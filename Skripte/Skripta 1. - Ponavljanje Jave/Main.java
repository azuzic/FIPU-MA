public class Zivotinja {
    protected String ime;

    public Zivotinja(String ime) {
        this.ime = ime;
    }

    public String opisZvuka() {
        return "Ova životinja proizvodi neki zvuk.";
    }
}

public class Pas extends Zivotinja {
    public Pas(String ime) {
        super(ime); // poziv konstruktora roditelja
    }

    @Override
    public String opisZvuka() {
        // poziv metode roditelja i nadogradnja
        return super.opisZvuka() + " Pas " + ime + " se glasa: Vau vau!";
    }
}

public class Macka extends Zivotinja {
    public Macka(String ime) {
        super(ime); // poziv konstruktora roditelja
    }

    @Override
    public String opisZvuka() {
        return super.opisZvuka() + " Mačka " + ime + " se glasa: Mjau mjau!";
    }
}

public class Main {
    public static void main(String[] args) {
        Zivotinja z = new Zivotinja("Opća");
        Pas pas = new Pas("Rex");
        Macka macka = new Macka("Mica");

        System.out.println(z.opisZvuka());
        System.out.println(pas.opisZvuka());
        System.out.println(macka.opisZvuka());
    }
}
