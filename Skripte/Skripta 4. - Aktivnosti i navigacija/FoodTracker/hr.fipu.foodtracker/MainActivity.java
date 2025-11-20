package hr.fipu.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FoodTracker foodTracker;
    private TextView textUkupno;
    private TextView textLista;

    private ActivityResultLauncher<Intent> launcher;

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

        String stavke = "";
        for (FoodItem item : foodTracker.getFoodItems()) {
            stavke += item.getFoodName() + " - " +
                    item.getKalorije() + " kalorija\n";
        }
        textLista.setText(stavke);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);

        foodTracker = FoodTracker.getInstance();

        textUkupno = findViewById(R.id.ukupno);
        textLista = findViewById(R.id.lista);

        Button dodaj = findViewById(R.id.btnDodaj);
        Button resetiraj = findViewById(R.id.btnResetiraj);
        Button postavke = findViewById(R.id.btnPostavke);

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
                        textLista.setText("Prazna lista...");

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