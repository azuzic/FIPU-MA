package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "pizza_ingredients",
    foreignKeys = {
        @ForeignKey(
            entity = PizzaEntity.class,
            parentColumns = "id",
            childColumns = "pizzaId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = IngredientEntity.class,
            parentColumns = "id",
            childColumns = "ingredientId",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class PizzaIngredientEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int pizzaId;
    private int ingredientId;
    private int quantityUsed;

    public PizzaIngredientEntity(int pizzaId, int ingredientId, int quantityUsed) {
        this.pizzaId = pizzaId;
        this.ingredientId = ingredientId;
        this.quantityUsed = quantityUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(int quantityUsed) {
        this.quantityUsed = quantityUsed;
    }
}
