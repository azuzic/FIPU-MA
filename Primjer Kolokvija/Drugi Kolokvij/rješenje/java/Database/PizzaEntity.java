package hr.fipu.primjer_drugog_kolokvija.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pizzas")
public class PizzaEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String name;
    private double price;
    private long createdDate;

    public PizzaEntity(String name, double price, long createdDate) {
        this.name = name;
        this.price = price;
        this.createdDate = createdDate;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
