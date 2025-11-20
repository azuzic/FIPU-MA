package hr.fipu.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        EditText inputNaziv = findViewById(R.id.inputNaziv);
        EditText inputKalorije = findViewById(R.id.inputKalorije);

        Button btnSave = findViewById(R.id.btnDodajStavku);
        Button btnCancel = findViewById(R.id.btnOdustani);

        btnSave.setOnClickListener(v -> {
            Intent resultIntent = new Intent();

            String naziv = inputNaziv.getText().toString();
            Float kalorije = Float.parseFloat(inputKalorije.getText().toString());

            resultIntent.putExtra("naziv", naziv);
            resultIntent.putExtra("kalorije", kalorije);

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}