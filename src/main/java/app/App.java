package app;

import model.DrinkFlavor;
import model.Sauce;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final PricingService pricing = new PricingService();
    private final ReceiptService receipts = new ReceiptService();

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        while (true) {
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            String in = prompt("Choose");
            if ("1".equals(in)) newOrder();
            else if ("0".equals(in)) return;
        }
    }

    private void newOrder() {
        Order order = new Order();
        while (true) {
            System.out.println("1) Add Pizza");
            System.out.println("2) Add Drink");
            System.out.println("3) Add Garlic Knots");
            System.out.println("4) Checkout");
            System.out.println("0) Cancel Order");
            String in = prompt("Choose");
            if ("1".equals(in)) addPizza(order);
            else if ("2".equals(in)) addDrink(order);
            else if ("3".equals(in)) addGarlicknots(order);
            else if ("4".equals(in)) {
                checkout(order);
                return;
            } else if ("0".equals(in)) {
                System.out.println("Order has been cancelled");
                return;
            }
        }
    }

    private void addPizza(Order order) {
        Pizza p = new Pizza();
        p.setSize(selectSize());
        p.setCrust(selectCrust());
        p.setStuffedCrust(askYesNo("Stuffed Crust?"));
        addPremiumMeats(p);
        addPremiumCheeses(p);
        addRegulars(p);
        addSauces(p);
        order.addPizza(p);
        System.out.println("Pizza added:" + p.describe());
    }

    private Size setSize() {
        System.out.println("1) 8\"");
        System.out.println("2) 12\"");
        System.out.println("3) 16\"");
        while (true) {
            String in = prompt("Size");
            if ("1".equals(in)) return Size.PERSONAL_8;
            if ("2".equals(in)) return Size.MEDIUM_12;
            if ("3".equals(in)) return Size.LARGE_16;
        }
    }

    private Crust selectCrust() {
        Sysytem.out.println("1) THIN");
        System.out.println("2) REGULAR");
        System.out.println("3) THICK");
        System.out.println("4) CAULIFLOWER");
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
            System.out.println("Add Meat:");
            int i = 1;
            for (Meat m : Meat.values()) System.out.println(i++ + ")" + m);
            System.out.println("0) Done");
            String.in = prompt("Choose");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, Meat.values().length);
            Meat m = Meat.values()[idx - 1];
            int qty = parsePositive(prompt("Quantity(1 for normal, >1 adds extras)"));
            p.addMeat(m, qty);
        }
    }

    private void addPremiumCheeses(Pizza p) {
        while (true) {
            System.out.println("Add Cheese:");
            int i = 1;
            for (Cheese c : Cheese.values()) System.out.println(i++ + ")" + c);
            System.out.println("0) Done");
            String.in = prompt("Choose");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, Cheese.values().length);
            Cheese c = Cheese.values()[idx - 1];
            int qty = parsePositive(prompt("Quantity(1 for normal, >1 adds extras)"));
            p.addCheese(c, qty);
        }
    }

    private void addRegulars(Pizza p) {
        while (true) {
            System.out.println("Add Regular Topping:");
            int i = 1;
            for (RegularTopping t : RegularTopping.values()) System.out.println(i++ + ")" + t);
            System.out.println("0) Done");
            String.in = prompt("Choose");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, RegularTopping.values().length);
            RegularTopping t = RegularTopping.values()[idx - 1];
            int qty = parsePositive(prompt("Quantity"));
            p.addRegular(t, qty);

        }
    }

    private void addSauces(Pizza P) {
        while (true) {
            System.out.println("Add Sauces:");
            int i = 1;
            for (Sauce s : Sauce.values()) System.out.println(i++ + ")" + s);
            System.out.println("0) Done");
            String.in = prompt("Choose");
            if ("0".equals(in)) break;
            int idx = parseIndex(in, Sauce.values().length);
            Sauce s = Sauce.values()[idx - 1];
            p.addSauce(s);

        }
    }

    private void addDrink(Order order) {
        DrinkSize size = selectDrinkSize();
        DrinkFlavor flavour = selectDrinkFlavour();
        order.addDrink(new Drink(size, flavour));
    }

    private DrinkSize selectDrinkSize() {
        System.out.println("1) SMALL");
        System.out.println("2) MEDIUM");
        System.out.println("3) LARGE");
        while (true) {
            String in = prompt("Size");
            if ("1".equals(in)) return DrinkSize.SMALL;
            if ("2".equals(in)) return DrinkSize.MEDIUM;
            if ("3".equals(in)) return DrinkSize.LARGE;
        }
    }

    private DrinkFlavor selectDrinkFlavour() {
        int i = 1;
        for (DrinkFlavor f : DrinkFlavor.values()) System.out.println(i++ + ")" + f);
        while (true) {
            string in = prompt("flavour");
            int idx = parseIndex(in, DrinkFlavor.values().length);
            return DrinkFlavor.values()[idx - 1];
        }
    }

    private void addGarlicKnots(Order order) {
        int qty = parsePositive(prompt("Garlic Knots quantity"));
        if (order.getGarlicKnots() == null) order.setGarlicKnots(new GarlicKnots(qty));
        else order.setGarlicKnots(new GarlicKnots(order.getGarlicKnots().getQuantity() + qty));
    }

    private void checkout(Order order) {
        if (order.isEmpty()) {
            System.out.println("Order is empty. If pizzas, you must buy garlic knots or a drink.");
            return;
        }
        if (order.getPizzas().isEmpty() && (order.getGarlicKnots() == null || order.getGarlicKnots().getQantity() == 0) && order.getDrinks().isEmpty()) {
            System.out.println("Add garlic knots or a drink to proceed.");
            return;
            System.out.println("Order Details:");
            int i = 1;
            for (Pizza p : order.getPizzas()) {
                System.out.println("Pizza" + (i++));
                System.out.println(p.describe());
                System.out.println("Subtotal:" + pricing.pricePizza(p));
                System.out.println();
            }
            for (Drink d : order.getDrinks()) System.out.println(d.describe() + "-" + pricing.priceDrink(d));
            if (order.getGarlicKnots() != null && order.getGarlicKnots().getQuantity() > 0)
                System.out.println(order.getGarlicKnots().describe() + "-" + pricing.priceGarlicKnots(order.getGarlicKnots()));
            BigDecimal total = pricing.priceOrder(order);
            System.out.println("Total:" + total);
            System.out.println("1) Confirm");
            System.out.println("0) Cancel");
            ;
            String in = prompt("Choose");
            if ("1".equals(in)) {
                try {
                    path p = receipt.save(order);
                    System.out.println("Saved receipt:" + p.toAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Failed to save receipt:" + e.getMessage());
                }
            } else {
                System.out.println("Order Cancelled.");
            }
        }
        private String prompt (String label){
            System.out.print(label + ": ");
            return sc.nextLine().trim();
        }

        private boolean askYesNo (String q){
            while (true) {
                String in = prompt(q + "(y/n)");
                if (in.equalsIgnoreCase("y")) return true;
                if (in.equalsIgnoreCase("n")) return false;
            }
        }
        private int parsePositive (String in,int max){
            try {
                int v = integer.parseInt(in);
                if (v >= 1 && v <= max) return v;
            } catch (Exception ignored) {
            }
            return 1;
        }

        private int parsePositive (String in){
            try {
                int v = integer.parseInt(in);
                if (v >= 1) return v;
            } catch (Exception ignored) {
            }
            return 1;
        }
    }
}