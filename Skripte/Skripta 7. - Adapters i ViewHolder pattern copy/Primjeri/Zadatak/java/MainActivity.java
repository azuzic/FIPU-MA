package hr.fipu.zadatak_fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        // default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            bottomNav.setSelectedItemId(R.id.nav_home);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int selectedItemId = item.getItemId();

            if (selectedItemId == R.id.nav_home) fragment = new HomeFragment();
            else if (selectedItemId == R.id.nav_categories) fragment = new CategoriesFragment();
            else if (selectedItemId == R.id.nav_favorites) fragment = new FavoritesFragment();

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            return true;
        });
    }
}
