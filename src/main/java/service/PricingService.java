package service;

import model.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class PricingService {
    public BigDecimal priceOrder(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (Pizza p : order.getPizzas()) total = total.add(pricePizza(p));
        for (Drink d : order.getDrinks()) total = total.add(priceDrink(d));
        if (order.getGarlicKnots()!=null) total = total.add(priceGarlicKnots(order.getGarlicKnots()));
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal pricePizza(Pizza p) {
        BigDecimal base = switch (p.getSize()) {
            case PERSONAL_8 -> bd(8.50);
            case MEDIUM_12 -> bd(12.00);
            case LARGE_16 -> bd(16.50);
        };
        BigDecimal meats = pricePremium(p.getSize(), p.getMeats());
        BigDecimal cheeses = pricePremiumCheese(p.getSize(), p.getCheeses());
        return base.add(meats).add(cheeses);
    }

    private BigDecimal pricePremium(Size size, Map<Meat,Integer> items) {
        BigDecimal first = switch (size) {
            case PERSONAL_8 -> bd(1.00);
            case MEDIUM_12 -> bd(2.00);
            case LARGE_16 -> bd(3.00);
        };
        BigDecimal extra = switch (size) {
            case PERSONAL_8 -> bd(0.50);
            case MEDIUM_12 -> bd(1.00);
            case LARGE_16 -> bd(1.50);
        };
        BigDecimal sum = BigDecimal.ZERO;
        for (Integer qty : items.values()) {
            if (qty<=0) continue;
            sum = sum.add(first);
            if (qty>1) sum = sum.add(extra.multiply(bd(qty-1)));
        }
        return sum;
    }

    private BigDecimal pricePremiumCheese(Size size, Map<Cheese,Integer> items) {
        BigDecimal first = switch (size) {
            case PERSONAL_8 -> bd(0.75);
            case MEDIUM_12 -> bd(1.50);
            case LARGE_16 -> bd(2.25);
        };
        BigDecimal extra = switch (size) {
            case PERSONAL_8 -> bd(0.30);
            case MEDIUM_12 -> bd(0.60);
            case LARGE_16 -> bd(0.90);
        };
        BigDecimal sum = BigDecimal.ZERO;
        for (Integer qty : items.values()) {
            if (qty<=0) continue;
            sum = sum.add(first);
            if (qty>1) sum = sum.add(extra.multiply(bd(qty-1)));
        }
        return sum;
    }

    public BigDecimal priceDrink(Drink d) {
        return switch (d.getSize()) {
            case SMALL -> bd(2.00);
            case MEDIUM -> bd(2.50);
            case LARGE -> bd(3.00);
        };
    }

    public BigDecimal priceGarlicKnots(GarlicKnots gk) {
        return bd(1.50).multiply(bd(gk.getQuantity()));
    }

    private BigDecimal bd(double v) { return new BigDecimal(String.valueOf(v)); }
    private BigDecimal bd(int v) { return new BigDecimal(v); }
}