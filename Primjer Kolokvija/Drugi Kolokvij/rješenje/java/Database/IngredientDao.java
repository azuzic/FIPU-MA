package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {
    
    @Insert
    void insert(IngredientEntity ingredient);
    
    @Update
    void update(IngredientEntity ingredient);
    
    @Delete
    void delete(IngredientEntity ingredient);
    
    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    LiveData<List<IngredientEntity>> getAllIngredients();
    
    @Query("SELECT * FROM ingredients WHERE name = :name LIMIT 1")
    IngredientEntity getIngredientByName(String name);
    
    @Query("SELECT * FROM ingredients WHERE id = :id LIMIT 1")
    IngredientEntity getIngredientById(int id);
    
    @Query("DELETE FROM ingredients WHERE name = :name")
    void deleteByName(String name);
}
