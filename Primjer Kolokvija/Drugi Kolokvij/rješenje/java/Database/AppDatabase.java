package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
    entities = {IngredientEntity.class, PizzaEntity.class, PizzaIngredientEntity.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    
    public abstract IngredientDao ingredientDao();
    public abstract PizzaDao pizzaDao();
    public abstract PizzaIngredientDao pizzaIngredientDao();
}
