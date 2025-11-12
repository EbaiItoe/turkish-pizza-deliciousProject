package model;

public class Drink {
    private DrinkSize size;
    private DrinkFlavour flavour;

    public Drink(DrinkSize size, DrinkFlavor flavour){this.size = size; this.flavour = flavour;}
    public DrinkSize getSize() { return size;}

    public DrinkFlavour getFlavour() {
        return flavour;
    }
    public String describe(){ return "Drink: " + flavour +" "+ size;
}
