package model;

public class GarlicKnots {
    private int quantity;

    public GarlicKnots(int quantity) { this.quantity = quantity; }
    public int getQuantity() { return quantity; }

    public String describe() { return "Garlic Knots x" + quantity; }
}
