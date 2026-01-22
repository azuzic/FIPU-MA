package hr.fipu.primjer_drugog_kolokvija.Model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import hr.fipu.primjer_drugog_kolokvija.Database.AppDatabase;
import hr.fipu.primjer_drugog_kolokvija.Database.DatabaseProvider;
import hr.fipu.primjer_drugog_kolokvija.Database.IngredientEntity;
import hr.fipu.primjer_drugog_kolokvija.Database.PizzaEntity;
import hr.fipu.primjer_drugog_kolokvija.Database.PizzaIngredientEntity;

public class Pizzeria {
    
    private static Pizzeria instance;
    private final AppDatabase database;
    
    private Pizzeria(Context context) {
        this.database = DatabaseProvider.getInstance(context);
        initializeDefaultIngredients();
    }
    
    public static Pizzeria getInstance(Context context) {
        if (instance == null) {
            synchronized (Pizzeria.class) {
                if (instance == null) {
                    instance = new Pizzeria(context);
                }
            }
        }
        return instance;
    }

    private void initializeDefaultIngredients() {
        new Thread(() -> {
            IngredientEntity ingredient = database.ingredientDao().getIngredientByName("Mozzarella");

            if (ingredient != null) return;

            database.ingredientDao().insert(new IngredientEntity("Mozzarella", 1000, 0.05));
            database.ingredientDao().insert(new IngredientEntity("Paradajz", 500, 0.03));
            database.ingredientDao().insert(new IngredientEntity("Masline", 200, 0.10));
            database.ingredientDao().insert(new IngredientEntity("Paprika", 400, 0.08));
            database.ingredientDao().insert(new IngredientEntity("Gljive", 300, 0.12));
            database.ingredientDao().insert(new IngredientEntity("Peperoni", 200, 0.20));
        }).start();
    }

    public void addIngredientToDatabase(String name, int quantity, double pricePerGram) {
        new Thread(() -> {
            IngredientEntity ingredient = new IngredientEntity(name, quantity, pricePerGram);
            database.ingredientDao().insert(ingredient);
        });
    }

    public void removeIngredientFromDatabase(int ingredientId) {
        new Thread(() -> {
            IngredientEntity ingredient = database.ingredientDao().getIngredientById(ingredientId);
            if (ingredient != null) {
                database.ingredientDao().delete(ingredient);
            }
        }).start();
    }
    
    public LiveData<List<IngredientEntity>> getAvailableIngredients() {
        return database.ingredientDao().getAllIngredients();
    }
    
    public boolean checkIngredientAvailability(int ingredientId, int requiredQuantity) {
        IngredientEntity ingredient = database.ingredientDao().getIngredientById(ingredientId);
        return ingredient != null && ingredient.getQuantity() >= requiredQuantity;
    }

    public void createPizza(String name, List<PizzaIngredientInfo> pizzaIngredients) {
        new Thread(() -> {
            PizzaEntity pizza = new PizzaEntity(name, 0, System.currentTimeMillis());
            int pizzaId = (int) database.pizzaDao().insert(pizza);

            for (PizzaIngredientInfo pizzaIngredient : pizzaIngredients) {
                IngredientEntity ingredient = database.ingredientDao().getIngredientById(pizzaIngredient.getIngredientId());
                ingredient.setQuantity(ingredient.getQuantity() - pizzaIngredient.getQuantityUsed());

                PizzaIngredientEntity newPizzaIngredient = new PizzaIngredientEntity(pizzaId, ingredient.getId(), pizzaIngredient.getQuantityUsed());

                database.pizzaIngredientDao().insert(newPizzaIngredient);
                database.ingredientDao().update(ingredient);
            }

            double totalPrice = database.pizzaIngredientDao().calculatePizzaPrice(pizzaId);
            pizza.setId(pizzaId);
            pizza.setPrice(totalPrice);
            database.pizzaDao().update(pizza);

        }).start();
    }

    public void deletePizza(int pizzaId) {
        new Thread(() -> {
            PizzaEntity pizza = database.pizzaDao().getPizzaById(pizzaId);
            if (pizza != null) database.pizzaDao().delete(pizza);
        }).start();
    }
    
    public LiveData<List<PizzaEntity>> getAllPizzas() {
        return database.pizzaDao().getAllPizzas();
    }
    
    public LiveData<List<PizzaEntity>> getPizzasByPrice(double minPrice, double maxPrice) {
        return database.pizzaDao().getPizzasByPriceRange(minPrice, maxPrice);
    }
    
    public LiveData<List<PizzaEntity>> getPizzasByName(String namePart) {
        return database.pizzaDao().getPizzasByNameContaining(namePart);
    }
    
    public LiveData<Double> getTotalRevenue() {
        return database.pizzaDao().getTotalPriceAllPizzas();
    }
    
    public AppDatabase getDatabase() {
        return database;
    }
}
