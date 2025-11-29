package hr.fipu.primjer_prvog_kolokvija_z2;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DodajKnjiguActivity extends AppCompatActivity {

    private Knjiznica knjiznica = Knjiznica.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dodaj_knjigu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int mainPaddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
            v.setPadding(systemBars.left + mainPaddingDp,
                    systemBars.top + mainPaddingDp,
                    systemBars.right + mainPaddingDp,
                    systemBars.bottom + mainPaddingDp);
            return insets;
        });

        Button btnDodajKnjigu = findViewById(R.id.btnDodajKnjigu);
        Button btnOdustani = findViewById(R.id.btnOdustani);

        EditText inputNaslov = findViewById(R.id.inputNaslov);
        EditText inputAutor = findViewById(R.id.inputAutor);
        EditText inputGodinaIzdanja = findViewById(R.id.inputGodinaIzdanja);
        EditText inputOpis = findViewById(R.id.inputOpis);

        btnDodajKnjigu.setOnClickListener(v -> {

            String naslov = inputNaslov.getText().toString();
            String autor = inputAutor.getText().toString();
            int godina = Integer.parseInt(inputGodinaIzdanja.getText().toString());
            String opis = inputOpis.getText().toString();

            if (knjiznica.nadjiKnjigu(naslov) != null) {
                Toast.makeText(this, "Knjiga sa istim naslovom već postoji!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("naslov", naslov);
                intent.putExtra("autor", autor);
                intent.putExtra("godina", godina);
                intent.putExtra("opis", opis);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnOdustani.setOnClickListener(v -> {
            finish();
        });

    }
}