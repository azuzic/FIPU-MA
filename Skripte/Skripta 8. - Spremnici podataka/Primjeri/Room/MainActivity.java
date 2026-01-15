package hr.fipu.room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ==========================================================================================================
        // Room
        // ==========================================================================================================

        Context context = getApplicationContext();
        AppDatabase db = DatabaseProvider.getInstance(context);

        // Dodavanje podataka
        FoodItem apple = new FoodItem("Apple", 95, "2026-01-14");
        FoodItem banana = new FoodItem("Banana", 105, "2026-01-14");
        FoodItem orange = new FoodItem("Orange", 62, "2026-01-14");
        FoodItem peach = new FoodItem("Peach", 59, "2026-01-14");

        new Thread(() -> {
            db.foodDao().insert(apple);
            db.foodDao().insert(banana);
            db.foodDao().insert(orange);
            db.foodDao().insert(peach);
            Log.d("ROOM", "Food inserted");
        }).start();

        // Ažuriranje podataka
        FoodItem updatedFood = new FoodItem("Updated Apple", 100, "2026-01-14");
        updatedFood.id = 1;
        new Thread(() -> {
            db.foodDao().update(updatedFood);
            Log.d("ROOM", "Food updated");
        }).start();

        // Brisanje podataka
        new Thread(() -> {
            db.foodDao().deleteFoodById(3);
            Log.d("ROOM", "Food deleted");
        }).start();

        // Čitanje podataka
        db.foodDao().getAllFood().observe(this, foodList -> {
            Log.d("ROOM", "Foods updated: " + foodList.size());
            for (FoodItem food : foodList) {
                Log.d("ROOM", "Food: " + food.name + ", Calories: " + food.calories);
            }
        });
    }
}