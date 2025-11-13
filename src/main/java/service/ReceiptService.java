package service;

import model.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class ReceiptService {
    private final PricingService pricing = new PricingService();
    private final Path root = Paths.get("receipts");

    public Path save(Order order) throws IOException {
        if (!Files.exists(root)) Files.createDirectories(root);
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path file = root.resolve(ts + ".txt");
        try (BufferedWriter w = Files.newBufferedWriter(file)) {
            w.write(render(order, pricing.priceOrder(order)));
        }
        return file;
    }

    public String render(Order order, BigDecimal total) {
        StringBuilder sb = new StringBuilder();
        sb.append("PIZZA-licious Receipt\n");
        sb.append("---------------------\n");
        int idx=1;
        for (Pizza p : order.getPizzas()) {
            sb.append("Pizza ").append(idx++).append(": ").append("\n").append(p.describe()).append("\n");
            sb.append("Subtotal: ").append(pricing.pricePizza(p)).append("\n\n");
        }
        for (Drink d : order.getDrinks()) {
            sb.append(d.describe()).append(" - ").append(pricing.priceDrink(d)).append("\n");
        }
        if (order.getGarlicKnots()!=null && order.getGarlicKnots().getQuantity()>0) {
            sb.append(order.getGarlicKnots().describe()).append(" - ").append(pricing.priceGarlicKnots(order.getGarlicKnots())).append("\n");
        }
        sb.append("\nTotal: ").append(total).append("\n");
        return sb.toString();
    }
}