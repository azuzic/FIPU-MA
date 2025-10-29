import java.util.*;

public class Main {
  public static void main(String[] args) {
      
      Sportas s1 = new Plivac("Ana", 20);
      Sportas s2 = new Plivac("Ivan", 22);
      Sportas s3 = new Nogometas("Marko", 21);
      Sportas s4 = new Nogometas("Luka", 23);
      
      //System.out.println(s1.opisVjezbe());
      //System.out.println(s2.opisVjezbe());
      //System.out.println(s3.opisVjezbe());
      //System.out.println(s4.opisVjezbe());
      
      Tim t1 = new Tim("Plivacki tim");
      t1.dodajSportasa(s1);
      t1.dodajSportasa(s2);
      //t1.ispisiSveSportase();
      
      Tim t2 = new Tim("Nogometni tim");
      t2.dodajSportasa(s3);
      t2.dodajSportasa(s4);
      //t2.ispisiSveSportase();
      
      SportskiKlub klub = new SportskiKlub("Istra");
      klub.dodajTim(t1);
      klub.dodajTim(t2);
      klub.ispisiSveTimove();
  }
}


abstract class Sportas {
  protected String ime;
  protected int godine;
  
  Sportas(String ime, int godine) {
    this.ime = ime;
    this.godine = godine;
  }
  
  String getIme() {
    return this.ime;
  }
  int getGodine() {
    return this.godine;
  }
  void setIme(String ime) {
    this.ime = ime;
  }
  void setGodine(int godine) {
    this.godine = godine;
  }
  
  String oIgracu() {
    return (this.ime + "(" + this.godine + ")");
  }
  
  abstract String opisVjezbe();
}

class Nogometas extends Sportas {
  Nogometas(String ime, int godine) {
    super(ime, godine);
  }
  @Override
  String opisVjezbe() {
    return(super.ime + " igra nogomet.");
  }
}
class Kosarkas extends Sportas {
  Kosarkas(String ime, int godine) {
    super(ime, godine);
  }
  @Override
  String opisVjezbe() {
    return(super.ime + " igra kosarku.");
  }
}
class Plivac extends Sportas {
  Plivac(String ime, int godine) {
    super(ime, godine);
  }
  @Override
  String opisVjezbe() {
    return(super.ime + " pliva.");
  }
}

class Tim {
  private String naziv;
  private List<Sportas> sportasi = new ArrayList<>();

  Tim(String naziv) {
    this.naziv = naziv;
  }

  void dodajSportasa(Sportas s) {
    this.sportasi.add(s);
  }
  void ispisiSveSportase() {
    System.out.println("Tim: " + this.naziv);
    System.out.println("-----------------");
    for (int i = 0; i < sportasi.size(); i++) {
      System.out.println((i+1) + ". " + "Ime: " + sportasi.get(i).oIgracu());
    }
    System.out.println("");
  }
  
}

class SportskiKlub {
  private String naziv;
  private List<Tim> timovi = new ArrayList<>();

  SportskiKlub(String naziv) {
    this.naziv = naziv;
  }
  void dodajTim(Tim tim) {
    this.timovi.add(tim);
  }
  void ispisiSveTimove() {
    System.out.println("Sportski klub: " + this.naziv);
    System.out.println("");
    for (int i = 0; i < timovi.size(); i++) {
      System.out.print((i+1) + ": ");
      timovi.get(i).ispisiSveSportase();
    }
  }
}











