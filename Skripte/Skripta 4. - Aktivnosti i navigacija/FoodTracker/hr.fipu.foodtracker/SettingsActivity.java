package hr.fipu.foodtracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private FoodTracker foodTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        foodTracker = FoodTracker.getInstance();

        EditText inputCilj = findViewById(R.id.inputCilj);
        inputCilj.setText(String.valueOf(foodTracker.getCaloriesGoal()));

        Button btnSpremi = findViewById(R.id.btnSpremi);
        btnSpremi.setOnClickListener(v -> {
            String caloriesGoalText = inputCilj.getText().toString();
            try {
                float caloriesGoal = Float.parseFloat(caloriesGoalText);
                foodTracker.setCaloriesGoal(caloriesGoal);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finish();
        });

    }
}