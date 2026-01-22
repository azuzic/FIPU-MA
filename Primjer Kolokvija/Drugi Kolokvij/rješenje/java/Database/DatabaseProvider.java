package hr.fipu.primjer_drugog_kolokvija.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseProvider {
    
    private static AppDatabase instance;
    
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseProvider.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "pizzeria_database.db"
                    )
                    .allowMainThreadQueries()
                    .build();
                }
            }
        }
        return instance;
    }
}
