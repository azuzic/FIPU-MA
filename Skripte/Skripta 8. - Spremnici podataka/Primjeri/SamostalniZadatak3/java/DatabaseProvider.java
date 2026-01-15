package hr.fipu.room2;

import android.content.Context;

import androidx.room.Room;

public class DatabaseProvider {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "library_database.db"
            ).build();
        }
        return instance;
    }
}