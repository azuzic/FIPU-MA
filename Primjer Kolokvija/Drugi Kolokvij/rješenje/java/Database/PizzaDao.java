package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PizzaDao {
    
    @Insert
    long insert(PizzaEntity pizza);
    
    @Update
    void update(PizzaEntity pizza);
    
    @Delete
    void delete(PizzaEntity pizza);
    
    @Query("SELECT * FROM pizzas ORDER BY createdDate DESC")
    LiveData<List<PizzaEntity>> getAllPizzas();
    
    @Query("SELECT * FROM pizzas WHERE id = :id LIMIT 1")
    PizzaEntity getPizzaById(int id);
    
    @Query("DELETE FROM pizzas WHERE id = :pizzaId")
    void deleteById(int pizzaId);
    
    @Query("SELECT * FROM pizzas WHERE price BETWEEN :minPrice AND :maxPrice ORDER BY createdDate DESC")
    LiveData<List<PizzaEntity>> getPizzasByPriceRange(double minPrice, double maxPrice);
    
    @Query("SELECT * FROM pizzas WHERE name LIKE '%' || :namePart || '%' ORDER BY createdDate DESC")
    LiveData<List<PizzaEntity>> getPizzasByNameContaining(String namePart);
    
    @Query("SELECT SUM(price) FROM pizzas")
    LiveData<Double> getTotalPriceAllPizzas();
}
