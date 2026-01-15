package hr.fipu.fragments;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = new BooksFragment();

            if (item.getItemId() == R.id.nav_home)
                fragment = new HomeFragment();
            else if (item.getItemId() == R.id.nav_settings)
                fragment = new SettingsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in, // enter
                            R.anim.fade_out, // exit
                            R.anim.fade_in, // popEnter
                            R.anim.fade_out // popExit
                    )
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();

            return true;
        });


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        bottomNav.setSelectedItemId(R.id.nav_home);
    }
}