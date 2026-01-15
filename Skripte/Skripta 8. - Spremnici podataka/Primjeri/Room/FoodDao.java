package hr.fipu.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insert(FoodItem foodItem);

    @Update
    void update(FoodItem foodItem);

    @Delete
    void delete(FoodItem foodItem);

    @Query("SELECT * FROM food_items ORDER BY name ASC")
    LiveData<List<FoodItem>> getAllFood();

    @Query("SELECT * FROM food_items WHERE id = :foodId")
    LiveData<FoodItem> getFoodById(int foodId);

    @Query("SELECT * FROM food_items WHERE calories > :minCalories")
    LiveData<List<FoodItem>> getFoodByCalories(int minCalories);

    @Query("DELETE FROM food_items WHERE id = :foodId")
    void deleteFoodById(int foodId);
}
