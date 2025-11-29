package hr.fipu.primjer_prvog_kolokvija_z3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout buttonContainer;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(40, 256, 40, 40);

        TextView textView = new TextView(this);
        textView.setText("Self removing blue buttons!");
        textView.setTextSize(24);
        mainLayout.addView(textView);

        Button mainButton = new Button(this);
        mainButton.setText("Click Me");
        mainLayout.addView(mainButton);

        buttonContainer = new LinearLayout(this);
        buttonContainer.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(buttonContainer);

        setContentView(mainLayout);

        mainButton.setOnClickListener(v -> {
            Button btn = new Button(this);
            btn.setText(counter + ". Ukloni me");
            btn.setBackgroundColor(Color.parseColor("#40BAD2"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            btn.setLayoutParams(params);

            int current = counter;
            counter++;

            btn.setOnClickListener(b -> buttonContainer.removeView(btn));

            buttonContainer.addView(btn);
        });
    }
}
