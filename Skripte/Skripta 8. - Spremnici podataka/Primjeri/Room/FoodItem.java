package hr.fipu.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_items")
public class FoodItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    public int calories;

    @ColumnInfo(name = "date_added")
    public String dateAdded;

    public FoodItem(@NonNull String name, int calories, String dateAdded) {
        this.name = name;
        this.calories = calories;
        this.dateAdded = dateAdded;
    }
}
