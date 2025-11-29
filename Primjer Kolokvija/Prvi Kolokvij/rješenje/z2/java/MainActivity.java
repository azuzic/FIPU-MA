package hr.fipu.primjer_prvog_kolokvija_z2;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Knjiznica knjiznica = Knjiznica.getInstance();
    private TextView listaKnjiga = null;
    private TextView naslovKnjige = null;

    private ActivityResultLauncher<Intent> addKnjigaLauncher =
        registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String naslov = data.getStringExtra("naslov");
                        String autor = data.getStringExtra("autor");
                        int godina = data.getIntExtra("godina", 0);
                        String opis = data.getStringExtra("opis");

                        knjiznica.dodajKnjigu(new Knjiga(naslov, autor, godina, opis));
                        UpdateUI();
                        Toast.makeText(this, "Knjiga uspješno dodana!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    private void UpdateUI() {
        String text = "";
        for (Knjiga knjiga : knjiznica.dohvatiSveKnjige()) {
            text += knjiga.getNaslov() + " ▪ " + knjiga.getAutor() + "\n";
        }
        listaKnjiga.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int mainPaddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
            v.setPadding(systemBars.left + mainPaddingDp,
                    systemBars.top + mainPaddingDp,
                    systemBars.right + mainPaddingDp,
                    systemBars.bottom + mainPaddingDp);
            return insets;
        });


        knjiznica.dodajKnjigu(new Knjiga("Hobit", "J.R.R. Tolkien", 1937,
                "Fantastični roman koji prati avanture hobita Bilba Bagginsa dok putuje Međuzemljem " +
                "kako bi povratio blago koje je zmaj Smaug oteo patuljcima. Knjiga je uvod u " +
                "bogati svijet 'Gospodara prstenova' i predstavlja čitateljima mnoge rase i mjesta " +
                "koja će kasnije postati ključna."));
        knjiznica.dodajKnjigu(new Knjiga("1984", "George Orwell", 1949,
                "Distopijski roman koji opisuje život u totalitarnoj državi Oceaniji, gdje Veliki Brat " +
                "sve nadzire, a misao je zločin. Glavni lik, Winston Smith, radi za Ministarstvo istine " +
                "i potajno se buni protiv režima, što ga dovodi u veliku opasnost."));
        knjiznica.dodajKnjigu(new Knjiga("Ponos i predrasude", "Jane Austen", 1813,
                "Ljubavni roman koji istražuje složene društvene odnose i pitanja braka, morala i odgoja " +
                "u Engleskoj početkom 19. stoljeća kroz priču o Elizabeth Bennet i gospodinu Darcyju. " +
                "Kroz duhovite dijaloge i složene likove, Austen kritizira društvene konvencije svog vremena."));
        knjiznica.dodajKnjigu(new Knjiga("Ubiti pticu rugalicu", "Harper Lee", 1960,
                "Roman smješten na američkom Jugu tijekom Velike depresije, koji se bavi ozbiljnim temama " +
                "rasne nepravde i gubitka nevinosti, promatranim očima djevojčice Scout Finch. Njezin otac, " +
                "Atticus Finch, odvjetnik je koji brani crnca lažno optuženog za silovanje bjelkinje, " +
                "što izaziva bijes u njihovoj zajednici."));
        knjiznica.dodajKnjigu(new Knjiga("Alkemičar", "Paulo Coelho", 1988,
                "Filozofski roman koji prati mladog andaluzijskog pastira Santiaga u njegovoj potrazi " +
                "za blagom skrivenim u egipatskim piramidama. Putovanje ga uči o važnosti slušanja " +
                "vlastitog srca, prepoznavanju znakova i ostvarivanju svoje 'Osobne legende'."));


        listaKnjiga = findViewById(R.id.listaKnjiga);
        naslovKnjige = findViewById(R.id.inputNaziv);
        UpdateUI();

        Button btnDodaj = findViewById(R.id.btnDodaj);
        Button btnUkloni = findViewById(R.id.btnUkloni);
        Button btnDetalji = findViewById(R.id.btnDetalji);

        btnDodaj.setOnClickListener(v -> {
            Intent intent = new Intent(this, DodajKnjiguActivity.class);
            addKnjigaLauncher.launch(intent);
        });

        btnUkloni.setOnClickListener(v -> {
            String naslov = naslovKnjige.getText().toString();
            boolean uklonjeno = knjiznica.ukloniKnjigu(naslov);
            if (uklonjeno) {
                UpdateUI();
                Toast.makeText(this, "Knjiga uspješno uklonjena!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Knjiga nije pronađena!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDetalji.setOnClickListener(v -> {
            String naslov = naslovKnjige.getText().toString();
            Knjiga knjiga = knjiznica.nadjiKnjigu(naslov);
            if (knjiga == null) {
                Toast.makeText(this, "Knjiga nije pronađena!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, DetaljiKnjigeActivity.class);
            intent.putExtra("naslov", knjiga.getNaslov());
            intent.putExtra("autor", knjiga.getAutor());
            intent.putExtra("godina", knjiga.getGodinaIzdavanja());
            intent.putExtra("opis", knjiga.getOpis());
            startActivity(intent);
        });

    }
}