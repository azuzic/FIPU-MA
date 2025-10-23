class Kutija<T extends Number> { // T može biti samo Number ili njegove podklase
    private T broj;

    public void set(T broj) { this.broj = broj; }
    public T get() { return broj; }
}

public class Main {
    public static void main(String[] args) {
        Kutija<Integer> k1 = new Kutija<>();
        k1.set(10);

        Kutija<Double> k2 = new Kutija<>();
        k2.set(3.14);

        Kutija<String> k3 = new Kutija<>(); // ❌ Greška: String ne nasljeđuje Number
    }
}