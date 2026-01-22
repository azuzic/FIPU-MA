package hr.fipu.primjer_drugog_kolokvija.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hr.fipu.primjer_drugog_kolokvija.Database.IngredientEntity;
import hr.fipu.primjer_drugog_kolokvija.Database.PizzaEntity;
import hr.fipu.primjer_drugog_kolokvija.Model.Pizzeria;

public class SharedViewModel extends AndroidViewModel {
    
    private final Pizzeria pizzeria;
    private LiveData<List<PizzaEntity>> allPizzas;
    private final LiveData<List<IngredientEntity>> allIngredients;
    private final LiveData<Double> totalRevenue;
    
    public SharedViewModel(@NonNull Application application) {
        super(application);
        this.pizzeria = Pizzeria.getInstance(application);
        this.allPizzas = pizzeria.getAllPizzas();
        this.allIngredients = pizzeria.getAvailableIngredients();
        this.totalRevenue = pizzeria.getTotalRevenue();
    }
    
    public Pizzeria getPizzeria() {
        return pizzeria;
    }
    
    public LiveData<List<PizzaEntity>> getAllPizzas() {
        allPizzas = pizzeria.getAllPizzas();
        return allPizzas;
    }
    public LiveData<List<PizzaEntity>> getPizzasByPrice(double minPrice, double maxPrice) {
        allPizzas = pizzeria.getPizzasByPrice(minPrice, maxPrice);
        return allPizzas;
    }
    public LiveData<List<PizzaEntity>> getPizzasByName(String namePart) {
        allPizzas = pizzeria.getPizzasByName(namePart);
        return allPizzas;
    }
    
    public LiveData<List<IngredientEntity>> getAllIngredients() {
        return allIngredients;
    }
    
    public LiveData<Double> getTotalRevenue() {
        return totalRevenue;
    }
}
