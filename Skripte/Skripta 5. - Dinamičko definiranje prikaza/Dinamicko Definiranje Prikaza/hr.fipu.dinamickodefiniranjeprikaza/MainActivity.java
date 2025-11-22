package hr.fipu.dinamickodefiniranjeprikaza;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Stvaramo glavni constraint layout i postavljamo visinu i širinu na MATCH_PARENT
        ConstraintLayout mainLayout = new ConstraintLayout(this);
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );

        // Dodajemo padding za layout
        int mainPaddingDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        mainLayout.setPadding(mainPaddingDp, mainPaddingDp, mainPaddingDp, mainPaddingDp);

        // Stvaramo edit text i postavljamo njegove atribute
        EditText editText = new EditText(this);

        editText.setHint("Unesi text...");
        editText.setId(EditText.generateViewId());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        editText.setLayoutParams(new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));

        mainLayout.addView(editText);

        // Stvaramo add button i postavljamo njegove atribute
        Button buttonAdd = new Button(this);

        buttonAdd.setText("Dodaj");
        buttonAdd.getBackground().setTint(Color.parseColor("#4CAF50"));
        buttonAdd.setId(Button.generateViewId());

        buttonAdd.setLayoutParams(new ViewGroup.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        mainLayout.addView(buttonAdd);

        // Stvaramo reset button i postavljamo njegove atribute
        Button buttonReset = new Button(this);

        buttonReset.setText("Resetiraj");
        buttonReset.getBackground().setTint(Color.parseColor("#FF9800"));
        buttonReset.setId(Button.generateViewId());

        buttonReset.setLayoutParams(new ViewGroup.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        mainLayout.addView(buttonReset);

        // Stvaramo linear layout i postavljamo njegove atribute
        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(mainPaddingDp/2, mainPaddingDp/2, mainPaddingDp/2, mainPaddingDp/2);
        linearLayout.setBackgroundColor(Color.parseColor("#E6E6E6"));
        linearLayout.setId(LinearLayout.generateViewId());

        linearLayout.setLayoutParams( new LinearLayout.LayoutParams(
            0, 0)
        );
        mainLayout.addView(linearLayout);

        // Namještamo layout constraint
        ConstraintSet set = new ConstraintSet(); // Stvaramo constraint set
        set.clone(mainLayout); // Kopiramo trenutne atribute iz constraint layout-a

        // Constraint za EditText
        set.connect(editText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(editText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        set.setVerticalBias(editText.getId(), 0);

        // Constraint za Add Button
        set.connect(buttonAdd.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(buttonAdd.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        // Constraint za Reset Button
        set.connect(buttonReset.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(buttonReset.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        // Constraint za Linear Layout
        set.connect(linearLayout.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(linearLayout.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        // Chain
        set.createVerticalChain(
                ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,
                new int[]{editText.getId(), buttonAdd.getId(), buttonReset.getId(), linearLayout.getId()},
                null,
                ConstraintSet.CHAIN_PACKED);

        set.applyTo(mainLayout);

        // Postavljamo glavni layout kao sadržaj aktivnosti, te dodajemo listener za insets
        setContentView(mainLayout);
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left + mainPaddingDp,
                    systemBars.top + mainPaddingDp,
                    systemBars.right + mainPaddingDp,
                    systemBars.bottom + mainPaddingDp);
            return insets;
        });

        // Listener za add button
        buttonAdd.setOnClickListener(v -> {
            String inputText = editText.getText().toString();
            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setText("- " + inputText);
            linearLayout.addView(textView);
        });

        // Listener za reset button
        buttonReset.setOnClickListener(v -> {
            linearLayout.removeAllViews();
        });
    }
}