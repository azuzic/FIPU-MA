package hr.fipu.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FoodTracker foodTracker;
    private TextView textUkupno;
    private LinearLayout listaLayout;
    private ConstraintLayout progressLayout;
    private ProgressView progressView;


    private ActivityResultLauncher<Intent> addItemLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();

                    String naziv = data.getStringExtra("naziv"); // Dohvaćamo podatke
                    float kalorije = data.getFloatExtra("kalorije", 0);

                    FoodItem noviItem = new FoodItem(naziv, kalorije);
                    foodTracker.addFoodItem(noviItem,this);

                    updateUI();
                }
            }
    );

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        int ukupnoKalorija = foodTracker.getTotalCalories();
        textUkupno.setText("Ukupno kalorija: " + ukupnoKalorija + "/" + foodTracker.getCaloriesGoal());

        listaLayout.removeAllViews();
        for (FoodItem item : foodTracker.getFoodItems()) {
            String foodName = item.getFoodName();
            float kalorije = item.getKalorije();

            StavkaView stavkaView = new StavkaView(this, foodName, kalorije);
            listaLayout.addView(stavkaView);
        }

        progressView.setCurrentProgress(ukupnoKalorija);
        progressView.setMaxProgress(foodTracker.getCaloriesGoal());
    }

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

        foodTracker = FoodTracker.getInstance();

        textUkupno = findViewById(R.id.ukupno);
        listaLayout = findViewById(R.id.lista);
        progressLayout = findViewById(R.id.progressBar);

        Button dodaj = findViewById(R.id.btnDodaj);
        Button resetiraj = findViewById(R.id.btnResetiraj);
        Button postavke = findViewById(R.id.btnPostavke);

        progressView = new ProgressView(this);
        progressLayout.addView(progressView);

        updateUI();

        postavke.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        dodaj.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            addItemLauncher.launch(intent);
        });
        resetiraj.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Potvrda resetiranja")
                    .setMessage("Jeste li sigurni da želite resetirati podatke?")
                    .setPositiveButton("Da", (dialog, which) -> {
                        // Spremi stare podatke za undo
                        List<FoodItem> oldItems = new ArrayList<>(foodTracker.getFoodItems());
                        float oldGoal = foodTracker.getCaloriesGoal();

                        foodTracker.clearAll();
                        updateUI();
                        //textLista.setText("Prazna lista...");

                        Snackbar.make(findViewById(R.id.main), "Podaci su uspješno resetirani.", Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> {
                                    foodTracker.setCaloriesGoal(oldGoal);
                                    for (FoodItem item : oldItems) {
                                        foodTracker.addFoodItem(item, this);
                                    }
                                    updateUI();
                                })
                                .show();
                    })
                    .setNegativeButton("Ne", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });
    }
}