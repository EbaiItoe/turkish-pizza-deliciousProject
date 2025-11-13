package model;

import java.util.*;

public class Order {
    private final List<Pizza> pizzas = new ArrayList<>();
    private final List<Drink> drinks = new ArrayList<>();
    private GarlicKnots garlicKnots;

    public List<Pizza> getPizzas() { return pizzas; }
    public List<Drink> getDrinks() { return drinks; }
    public GarlicKnots getGarlicKnots() { return garlicKnots; }
    public void setGarlicKnots(GarlicKnots gk) { this.garlicKnots = gk; }

    public void addPizza(Pizza p) { pizzas.add(0, p); }
    public void addDrink(Drink d) { drinks.add(0, d); }

    public boolean isEmpty() {
        return pizzas.isEmpty() && drinks.isEmpty() && (garlicKnots==null || garlicKnots.getQuantity()==0);
    }
}
