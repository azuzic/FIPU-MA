package hr.fipu.primjer_drugog_kolokvija.Model;

public class PizzaIngredientInfo {
    private int ingredientId;
    private String ingredientName;
    private int quantityUsed;
    private double pricePerGram;

    public PizzaIngredientInfo(int ingredientId, String ingredientName, int quantityUsed, double pricePerGram) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.quantityUsed = quantityUsed;
        this.pricePerGram = pricePerGram;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(int quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public double getPricePerGram() {
        return pricePerGram;
    }

    public void setPricePerGram(double pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    public double getTotalPrice() {
        return quantityUsed * pricePerGram;
    }
}
