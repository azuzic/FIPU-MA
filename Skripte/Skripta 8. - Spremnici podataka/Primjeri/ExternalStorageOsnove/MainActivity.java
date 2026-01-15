package hr.fipu.externastorage;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

        // ==========================================================================================================
        // App-specific External Storage
        // ==========================================================================================================

        // Spremanje podataka
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (dir != null && dir.exists()) {
            File file = new File(dir, "test.txt");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write("Test data".getBytes());
                Log.d("STORAGE", "File saved: " + file.getAbsolutePath());
            } catch (IOException e) {
                Log.e("STORAGE", "Error saving file", e);
            }
        }

        // Čitanje podataka
        if (dir != null && dir.exists()) {
            File file = new File(dir, "test.txt");
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead = fis.read(buffer);
                    String content = new String(buffer, 0, bytesRead);
                    Log.d("STORAGE", "Content: " + content);
                } catch (IOException e) {
                    Log.e("STORAGE", "Error reading file", e);
                }
            }
        }

    }
}