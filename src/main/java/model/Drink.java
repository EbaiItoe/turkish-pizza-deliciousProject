package model;

public class Drink {
    private DrinkSize size;
    private DrinkFlavor flavor;

    public Drink(DrinkSize size, DrinkFlavor flavor) { this.size = size; this.flavor = flavor; }
    public DrinkSize getSize() { return size; }
    public DrinkFlavor getFlavor() { return flavor; }

    public String describe() { return "Drink: " + flavor + " " + size; }
}
