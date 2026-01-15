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
        // Samostalni zadatak za vježbu #2
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
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (dir != null && dir.exists()) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(foodList);
            File file = new File(dir, "food_list.json");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(jsonString.getBytes());
                Log.d("JSON", "JSON saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                Log.e("JSON", "Error saving JSON", e);
            }
        }
    }
    public List<FoodItem> loadFoodList() {
        List<FoodItem> foodList = new ArrayList<>();

        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (dir != null && dir.exists()) {
            File file = new File(dir, "food_list.json");
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[(int) file.length()];
                    int bytesRead = fis.read(buffer);
                    String jsonString = new String(buffer, 0, bytesRead);
                    Gson gson = new Gson();
                    foodList = Arrays.asList(gson.fromJson(jsonString, FoodItem[].class));
                } catch (IOException e) {
                    Log.e("JSON", "Error reading JSON", e);
                }
            }
        }

        return foodList;
    }
}