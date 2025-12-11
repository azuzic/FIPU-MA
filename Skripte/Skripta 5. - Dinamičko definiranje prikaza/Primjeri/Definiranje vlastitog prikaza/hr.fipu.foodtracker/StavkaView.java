package hr.fipu.foodtracker;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StavkaView extends LinearLayout {
    private TextView textNaziv;
    private TextView textKalorije;

    public StavkaView(Context context, String naziv, float kalorije) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);

        setPadding(64, 32, 64, 32);

        textNaziv = new TextView(context);
        textNaziv.setText(naziv);
        textNaziv.setTextSize(16f);

        LayoutParams nazivParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        addView(textNaziv, nazivParams);

        textKalorije = new TextView(context);
        textKalorije.setText(kalorije + " kcal");
        textKalorije.setTextSize(16f);
        textKalorije.setTypeface(null, Typeface.BOLD);
        LayoutParams kalorijeParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(textKalorije, kalorijeParams);
    }
}
