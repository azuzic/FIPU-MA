package hr.fipu.room2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, BookReview.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}