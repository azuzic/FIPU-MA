package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class IngredientEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String name;
    private int quantity;
    private double pricePerGram;

    public IngredientEntity(String name, int quantity, double pricePerGram) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerGram = pricePerGram;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerGram() {
        return pricePerGram;
    }

    public void setPricePerGram(double pricePerGram) {
        this.pricePerGram = pricePerGram;
    }
}
