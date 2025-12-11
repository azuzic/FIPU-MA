package hr.fipu.foodtracker;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodTracker implements Serializable {
    private static FoodTracker instance;
    private FoodTracker() {};
    public static FoodTracker getInstance() {
        if (instance == null) {
            instance = new FoodTracker();
        }
        return instance;
    }

    private List<FoodItem> foodItems = new ArrayList<>(
            List.of(
                    new FoodItem("Jabuka", 62),
                    new FoodItem("Lubenica", 256),
                    new FoodItem("Kruh", 600),
                    new FoodItem("Maslac", 250)
            )
    );
    private float CaloriesGoal = 2000;

    public float getCaloriesGoal() {
        return CaloriesGoal;
    }

    public void setCaloriesGoal(float caloriesGoal) {
        if (caloriesGoal < 0){
            throw new IllegalArgumentException("Calories goal cannot be less than 0");
        }
        CaloriesGoal = caloriesGoal;
    }

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
