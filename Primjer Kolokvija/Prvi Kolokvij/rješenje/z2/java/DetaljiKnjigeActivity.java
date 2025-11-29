package hr.fipu.primjer_prvog_kolokvija_z2;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetaljiKnjigeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalji_knjige);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int mainPaddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
            v.setPadding(systemBars.left + mainPaddingDp,
                    systemBars.top + mainPaddingDp,
                    systemBars.right + mainPaddingDp,
                    systemBars.bottom + mainPaddingDp);
            return insets;
        });

        String naslov = getIntent().getStringExtra("naslov");
        String autor = getIntent().getStringExtra("autor");
        int godina = getIntent().getIntExtra("godina", 0);
        String opis = getIntent().getStringExtra("opis");

        TextView textNaslov = findViewById(R.id.textNaslov);
        TextView autorText = findViewById(R.id.autorText);
        TextView godinaIzdanjaText = findViewById(R.id.godinaIzdanjaText);
        TextView opisText = findViewById(R.id.opisText);

        textNaslov.setText(naslov);
        autorText.setText(autor);
        godinaIzdanjaText.setText(String.valueOf(godina));
        opisText.setText(opis);

        Button btnPovratak = findViewById(R.id.btnPovratak);
        btnPovratak.setOnClickListener(v -> {
            finish();
        });

    }
}