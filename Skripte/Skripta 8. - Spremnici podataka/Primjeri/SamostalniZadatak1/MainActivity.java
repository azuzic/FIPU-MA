package hr.fipu.internalstorage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        // Samostalni zadatak za vježbu #1
        // ==========================================================================================================

        List<FoodItem> foodList = new ArrayList<>();
        foodList.add(new FoodItem("Jabuka", 95));
        foodList.add(new FoodItem("Banana", 105));
        foodList.add(new FoodItem("Breskva", 400));

        saveFoodList(foodList);
        foodList = loadFoodList();
        for (FoodItem item : foodList) {
            Log.d("Z1", "Food item: " + item.getName() + ", " + item.getCalories());
        }

    }
    public void saveFoodList(List<FoodItem> foodList) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(foodList);
        try (FileOutputStream fos = openFileOutput("food_list.json", Context.MODE_PRIVATE)) {
            fos.write(jsonString.getBytes());
            Log.d("Z1", "Food list saved");
        } catch (IOException e) {
            Log.e("Z1", "Error saving food list", e);
        }
    }
    public List<FoodItem> loadFoodList() {
        List<FoodItem> foodList = new ArrayList<>();

        try (FileInputStream fis = openFileInput("food_list.json")) {
            byte[] buffer = new byte[4096];
            int bytesRead = fis.read(buffer);
            String jsonString = new String(buffer, 0, bytesRead);

            Gson gson = new Gson();
            FoodItem[] items = gson.fromJson(jsonString, FoodItem[].class);
            foodList = Arrays.asList(items);

            Log.d("Z1", "Food list loaded: " + foodList.size() + " items");
        } catch (IOException e) {
            Log.e("Z1", "Error loading food list", e);
        }

        return foodList;
    }
}