package hr.fipu.foodtracker;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodTracker {
    private List<FoodItem> foodItems = new ArrayList<>();

    public void addFoodItem(FoodItem foodItem, Context context) {
        foodItems.add(foodItem);
        Toast.makeText(context, ("Stavka " + foodItem.getFoodName() + " uspješno dodana"), Toast.LENGTH_SHORT).show();
    }

    public int getTotalCalories() {
        int totalCalories = 0;

        for (FoodItem foodItem : foodItems) {
            totalCalories += foodItem.getKalorije();
        }
        return totalCalories;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void clearAll() {
        foodItems.clear();
    }

}
