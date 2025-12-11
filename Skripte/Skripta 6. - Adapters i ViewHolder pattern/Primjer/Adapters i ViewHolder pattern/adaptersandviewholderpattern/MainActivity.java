package hr.fipu.adaptersandviewholderpattern;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<String> items;

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

        items = new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4"));

        RecyclerView recycler = findViewById(R.id.myRecyclerView);
        MyAdapter adapter = new MyAdapter(new ArrayList<>(items),
            (item, pos) -> {
                Toast.makeText(this, "[" + pos + " ] Clicked: " + item, Toast.LENGTH_SHORT).show();
            }
        );
        recycler.setAdapter(adapter);

        Button btnDodajRandom = findViewById(R.id.btnDodaj);
        btnDodajRandom.setOnClickListener(v -> {
            items.add("Random item " + new Random().nextInt(100));
            List<String> newItems = new ArrayList<>(items);
            adapter.setItems(newItems);
        });
        Button btnRemoveRandom = findViewById(R.id.btnUkloni);
        btnRemoveRandom.setOnClickListener(v -> {
            if (items.size() > 0) {
                items.remove(new Random().nextInt(items.size()));
                List<String> newItems = new ArrayList<>(items);
                adapter.setItems(newItems);
            }
        });

    }

}