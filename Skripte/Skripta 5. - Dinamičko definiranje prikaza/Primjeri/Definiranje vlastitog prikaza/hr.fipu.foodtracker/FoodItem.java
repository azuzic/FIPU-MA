package hr.fipu.foodtracker;

public class FoodItem {

    private String FoodName;
    private float Kalorije;

    FoodItem(String foodName, float kalorije) {
        this.FoodName = foodName;
        this.Kalorije = kalorije;
    }

    public String getFoodName() {
        return this.FoodName;
    }

    public float getKalorije() {
        return this.Kalorije;
    }

}
