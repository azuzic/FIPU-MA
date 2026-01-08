package hr.fipu.kosaricaprimjer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private Kosarica kosarica;
    TextView textSveukupno;
    Button dugmeDodaj;
    EditText textProizvod;
    EditText textCijena;
    RecyclerView recycler;
    KosaricaAdapter adapter;

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

        kosarica = new Kosarica();
        Proizvod jabuka = new Proizvod("Jabuka", 0.28f, 2);
        Proizvod banana = new Proizvod("Banana", 0.16f, 5);
        Proizvod breskva = new Proizvod("Breskva", 0.42f, 1);

        kosarica.dodajProizvod(jabuka);
        kosarica.dodajProizvod(banana);
        kosarica.dodajProizvod(breskva);

        textSveukupno = findViewById(R.id.textSveukupno);
        dugmeDodaj = findViewById(R.id.btnDodaj);
        textProizvod = findViewById(R.id.inputNaziv);
        textCijena = findViewById(R.id.inputCijena);

        updateTotalAmount();
        dugmeDodaj.setOnClickListener(v -> dodajProizvod());

        recycler = findViewById(R.id.myRecyclerView);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new KosaricaAdapter(kosarica);
        adapter.setOnItemsChangedListener(this::updateTotalAmount);

        recycler.setAdapter(adapter);
    }

    private void updateTotalAmount() {
        String totalText = String.valueOf(kosarica.ukupanIznos());
        textSveukupno.setText(totalText);
    }
    private void dodajProizvod() {
        String nazivProizvoda = textProizvod.getText().toString();
        double cijenaProizvoda = Double.parseDouble(textCijena.getText().toString());
        Proizvod noviProizvod = new Proizvod(nazivProizvoda, cijenaProizvoda, 1);
        kosarica.dodajProizvod(noviProizvod);
        updateTotalAmount();
        adapter.notifyDataSetChanged();
    }
}