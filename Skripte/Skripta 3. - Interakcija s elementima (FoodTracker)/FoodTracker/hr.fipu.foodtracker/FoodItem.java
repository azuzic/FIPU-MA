package hr.fipu.foodtracker;

public class FoodItem {

    private String FoodName;
    private int Kalorije;

    FoodItem(String foodName, int kalorije) {
        this.FoodName = foodName;
        this.Kalorije = kalorije;
    }

    public String getFoodName() {
        return this.FoodName;
    }

    public int getKalorije() {
        return this.Kalorije;
    }

}
