package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PizzaIngredientDao {
    
    @Insert
    void insert(PizzaIngredientEntity pizzaIngredient);
    
    @Query("SELECT i.* FROM ingredients i " +
           "INNER JOIN pizza_ingredients pi ON i.id = pi.ingredientId " +
           "WHERE pi.pizzaId = :pizzaId")
    List<IngredientEntity> getIngredientsForPizza(int pizzaId);
    
    @Query("SELECT SUM(pi.quantityUsed * i.pricePerGram) " +
           "FROM pizza_ingredients pi " +
           "INNER JOIN ingredients i ON pi.ingredientId = i.id " +
           "WHERE pi.pizzaId = :pizzaId")
    Double calculatePizzaPrice(int pizzaId);
}
