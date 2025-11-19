package hr.fipu.foodtracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private FoodTracker foodTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);

        foodTracker = new FoodTracker();

        TextView textUkupno = findViewById(R.id.ukupno);
        TextView textLista = findViewById(R.id.lista);
        EditText inputNaziv = findViewById(R.id.inputNaziv);
        EditText inputKalorije = findViewById(R.id.inputKalorije);
        Button dodaj = findViewById(R.id.btnDodaj);
        Button resetiraj = findViewById(R.id.btnResetiraj);

        dodaj.setOnClickListener(view -> {
            String naziv = inputNaziv.getText().toString();
            int kalorije = Integer.parseInt(inputKalorije.getText().toString());
            FoodItem noviItem = new FoodItem(naziv, kalorije);
            foodTracker.addFoodItem(noviItem,this);

            int ukupnoKalorija = foodTracker.getTotalCalories();
            textUkupno.setText("Ukupno kalorija: " + ukupnoKalorija);

            String stavke = "";
            for (FoodItem item : foodTracker.getFoodItems()) {
                stavke += item.getFoodName() + " - " +
                        item.getKalorije() + " kalorija\n";
            }
            textLista.setText(stavke);

        });

        resetiraj.setOnClickListener(view -> {
            foodTracker.clearAll();
            textUkupno.setText("Ukupno kalorija: 0");
            textLista.setText("");
        });
    }
}