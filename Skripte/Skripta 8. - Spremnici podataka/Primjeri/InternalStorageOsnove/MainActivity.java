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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        // Internal Storage
        // ==========================================================================================================

        // Spremanje datoteke
        try (FileOutputStream fos = openFileOutput("data.txt", Context.MODE_PRIVATE)) {
            fos.write("Hello World".getBytes());
        } catch (IOException e) {
            Log.e("STORAGE", "Error writing file", e);
        }

        // Čitanje datoteke
        try (FileInputStream fis = openFileInput("data.txt")) {
            byte[] buffer = new byte[1024];
            int bytesRead = fis.read(buffer);
            String content = new String(buffer, 0, bytesRead);
            Log.d("STORAGE", "Content: " + content);
        } catch (IOException e) {
            Log.e("STORAGE", "Error reading file", e);
        }

        // Brisanje datoteke
        boolean deleted = deleteFile("data.txt");
        if (deleted) {
            Log.d("STORAGE", "File deleted successfully");
        }

        // ==========================================================================================================
        // Rad s JSON datotekama (GSON)
        // ==========================================================================================================

        // Spremanje JSON datoteke
        FoodItem food = new FoodItem("Jabuka", 95);
        Gson gson = new Gson();
        String jsonString = gson.toJson(food);

        try (FileOutputStream fos = openFileOutput("food.json", Context.MODE_PRIVATE)) {
            fos.write(jsonString.getBytes());
            Log.d("GSON", "JSON saved: " + jsonString);
        } catch (IOException e) {
            Log.e("GSON", "Error saving JSON", e);
        }

        // Čitanje JSON datoteke
        try (FileInputStream fis = openFileInput("food.json")) {
            byte[] buffer = new byte[1024];
            int bytesRead = fis.read(buffer);
            jsonString = new String(buffer, 0, bytesRead);

            // Pretvaramo JSON string u Java objekt
            food = gson.fromJson(jsonString, FoodItem.class);

            Log.d("GSON", "Food loaded: " + food.getName() + ", " + food.getCalories());
        } catch (IOException e) {
            Log.e("GSON", "Error reading JSON", e);
        }

    }
}