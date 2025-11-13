package app;

import model.*;
import service.*;
import java.util.*;
import java.math.BigDecimal;
import java.nio.file.Path;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final PricingService pricing = new PricingService();
    private final ReceiptService receipts = new ReceiptService();

    public static void main(String[] args) { new App().run(); }

    private void run() {
        while (true) {
            System.out.println("\n                 HOME                ");
            System.out.println("----------------------------------------------------------");
            System.out.println("🍕🍕🍕🍕🍕Welcome to PIZZA-licious Restaurant 🍕🍕🍕🍕🍕");
            System.out.println("----------------------------------------------------------");
            String Q = prompt("Are you Ready to place your Order(yes/no)");
            if ("no".equals(Q)) return;
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            System.out.println("----------------------------------------------------------");
            String in = prompt("Choose");
            if ("1".equals(in)) newOrder();
            else if ("0".equals(in)) return;
        }
    }

    private void newOrder() {
        Order order = new Order();
        while (true) {
            System.out.println("\n              MENU       ");
            System.out.println("-----------------------------------");
            System.out.println("1) Add Pizza 🍕");
            System.out.println("2) Add Drink 🍹");
            System.out.println("3) Add Garlic Knots 🧄");
            System.out.println("4) Checkout 💰");
            System.out.println("0) Cancel Order ❌");
            System.out.println("-----------------------------------");
            String in = prompt("Choose");
            if ("1".equals(in)) addPizza(order);
            else if ("2".equals(in)) addDrink(order);
            else if ("3".equals(in)) addGarlicKnots(order);
            else if ("4".equals(in)) { checkout(order); return; }
            else if ("0".equals(in)) { System.out.println("Order cancelled."); return; }
        }
    }

    private void addPizza(Order order) {
        Pizza p = new Pizza();
        p.setSize(selectSize());
        p.setCrust(selectCrust());
        p.setStuffedCrust(askYesNo("Stuffed crust?"));
        addPremiumMeats(p);
        addPremiumCheeses(p);
        addRegulars(p);
        addSauces(p);
        order.addPizza(p);
        System.out.println("Added pizza:\n"+p.describe());
    }

    private Size selectSize() {
        System.out.println("\n            SELECT SIZE            ");
        System.out.println("-----------------------------------");
        System.out.println("1) PERSONAL 8\"");
        System.out.println("2) MEDIUM 12\"");
        System.out.println("3) LARGE 16\"");
        System.out.println("-----------------------------------");
        while (true) {
            String in = prompt("Size");
            if ("1".equals(in)) return Size.PERSONAL_8;
            if ("2".equals(in)) return Size.MEDIUM_12;
            if ("3".equals(in)) return Size.LARGE_16;
        }
    }

    private Crust selectCrust() {
        System.out.println("\n            SELECT CRUST           ");
        System.out.println("-----------------------------------");
        System.out.println("1) THIN");
        System.out.println("2) REGULAR");
        System.out.println("3) THICK");
        System.out.println("4) CAULIFLOWER");
        System.out.println("-----------------------------------");
        while (true) {
            String in = prompt("Crust");
            if ("1".equals(in)) return Crust.THIN;
            if ("2".equals(in)) return Crust.REGULAR;
            if ("3".equals(in)) return Crust.THICK;
            if ("4".equals(in)) return Crust.CAULIFLOWER;
        }
    }

    private void addPremiumMeats(Pizza p) {
        while (true) {
            System.out.println("\n      SELECT MEAT 🥩      ");
            System.out.println("-----------------------------------");
            int i=1; for (Meat m : Meat.values()) System.out.println(i++ + ") " + m);
            System.out.println("0) Done");
            System.out.println("-----------------------------------");
            String in = prompt("Add Meat");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, Meat.values().length);
            Meat m = Meat.values()[idx-1];
            int qty = parsePositive(prompt("Quantity (1 for normal, >1 adds extras)"));
            p.addMeat(m, qty);
        }
    }

    private void addPremiumCheeses(Pizza p) {
        while (true) {
            System.out.println("\n        SELECT CHEESE 🧀     ");
            System.out.println("----------------------------------");
            int i=1; for (Cheese c : Cheese.values()) System.out.println(i++ + ") " + c);
            System.out.println("0) Done");
            System.out.println("-----------------------------------");
            String in = prompt("Add Cheese");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, Cheese.values().length);
            Cheese c = Cheese.values()[idx-1];
            int qty = parsePositive(prompt("Quantity (1 for normal, >1 adds extras)"));
            p.addCheese(c, qty);
        }
    }

    private void addRegulars(Pizza p) {
        while (true) {
            System.out.println("\n      SELECT REGULAR TOPPING   ");
            System.out.println("-----------------------------------");
            int i=1; for (RegularTopping t : RegularTopping.values()) System.out.println(i++ + ") " + t);
            System.out.println("0) Done");
            System.out.println("-----------------------------------");
            String in = prompt("Add Regular Topping");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, RegularTopping.values().length);
            RegularTopping t = RegularTopping.values()[idx-1];
            int qty = parsePositive(prompt("Quantity"));
            p.addRegular(t, qty);
        }
    }

    private void addSauces(Pizza p) {
        while (true) {
            System.out.println("\n        SELECT SAUCE 🍝     ");
            System.out.println("-----------------------------------");
            int i=1; for (Sauce s : Sauce.values()) System.out.println(i++ + ") " + s);
            System.out.println("0) Done");
            System.out.println("-----------------------------------");
            String in = prompt("Choose");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, Sauce.values().length);
            Sauce s = Sauce.values()[idx-1];
            p.addSauce(s);
        }
    }

    private void addDrink(Order order) {
        DrinkSize size = selectDrinkSize();
        DrinkFlavor flavor = selectDrinkFlavor();
        order.addDrink(new Drink(size, flavor));
    }

    private DrinkSize selectDrinkSize() {
        System.out.println("\n      SELECT SIZE OF DRINK   ");
        System.out.println("-----------------------------------");
        System.out.println("1) SMALL");
        System.out.println("2) MEDIUM");
        System.out.println("3) LARGE");
        System.out.println("-----------------------------------");
        while (true) {
            String in = prompt("Size");
            if ("1".equals(in)) return DrinkSize.SMALL;
            if ("2".equals(in)) return DrinkSize.MEDIUM;
            if ("3".equals(in)) return DrinkSize.LARGE;
        }
    }

    private DrinkFlavor selectDrinkFlavor() {
        System.out.println("\n      SELECT FLAVOR OF DRINK   ");
        System.out.println("-----------------------------------");
        int i=1; for (DrinkFlavor f : DrinkFlavor.values()) System.out.println(i++ + ") " + f);
        System.out.println("-----------------------------------");
        while (true) {
            String in = prompt("Flavor");
            int idx = parseIndex(in, DrinkFlavor.values().length);
            return DrinkFlavor.values()[idx-1];
        }
    }

    private void addGarlicKnots(Order order) {
        System.out.println("\n       GARLICKNOTS       ");
        System.out.println("-----------------------------------");
        int qty = parsePositive(prompt("Garlic Knots quantity"));
        if (order.getGarlicKnots()==null) order.setGarlicKnots(new GarlicKnots(qty));
        else order.setGarlicKnots(new GarlicKnots(order.getGarlicKnots().getQuantity()+qty));
    }

    private void checkout(Order order) {
        if (order.isEmpty()) {
            System.out.println("Order is empty. If 0 pizzas, you must buy garlic knots or a drink.");
            return;
        }
        if (order.getPizzas().isEmpty() && (order.getGarlicKnots()==null || order.getGarlicKnots().getQuantity()==0) && order.getDrinks().isEmpty()) {
            System.out.println("Add garlic knots or a drink to proceed.");
            return;
        }

        System.out.println("\nOrder Details");
        System.out.println("-----------------------------------");
        int i=1;
        for (Pizza p : order.getPizzas()) {
            System.out.println("Pizza "+(i++));
            System.out.println(p.describe());
            System.out.println("\nSubtotal: " + pricing.pricePizza(p));
            System.out.println();
        }
        for (Drink d : order.getDrinks()) System.out.println(d.describe() + " - " + pricing.priceDrink(d));
        if (order.getGarlicKnots()!=null && order.getGarlicKnots().getQuantity()>0) {

            System.out.println(order.getGarlicKnots().describe() + " - " + pricing.priceGarlicKnots(order.getGarlicKnots()));
        }
        BigDecimal total = pricing.priceOrder(order);
        System.out.println("___________________________________");
        System.out.println("Total: " + total);
        System.out.println("___________________________________");
        System.out.println("1) Confirm✅");
        System.out.println("0) Cancel❌");
        String in = prompt("Choose");
        if ("1".equals(in)) {
            try {
                Path p = receipts.save(order);
                System.out.println("Saved receipt: " + p.toAbsolutePath());
            } catch (Exception e) {
                System.out.println("Failed to save receipt: " + e.getMessage());
            }
        } else {
            System.out.println("Order cancelled.");
        }
    }

    private String prompt(String label) {
        System.out.print(label + ": ");
        return sc.nextLine().trim();
    }

    private boolean askYesNo(String q) {
        while (true) {
            String in = prompt(q + " (y/n)");
            if (in.equalsIgnoreCase("y")) return true;
            if (in.equalsIgnoreCase("n")) return false;
        }
    }

    private int parseIndex(String in, int max) {
        try {
            int v = Integer.parseInt(in);
            if (v>=1 && v<=max) return v;
        } catch (Exception ignored) {}
        return 1;
    }

    private int parsePositive(String in) {
        try {
            int v = Integer.parseInt(in);
            if (v>=1) return v;
        } catch (Exception ignored) {}
        return 1;
    }
}
