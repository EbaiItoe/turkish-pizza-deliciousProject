package model;

import java.util.*;

public class Pizza {
    private Size size;
    private Crust crust;
    private boolean stuffedCrust;
    private Map<Meat,Integer> meats = new LinkedHashMap<>();
    private Map<Cheese,Integer> cheeses = new LinkedHashMap<>();
    private Map<RegularTopping,Integer> regulars = new LinkedHashMap<>();
    private Set<Sauce> sauces = new LinkedHashSet<>();

    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }
    public Crust getCrust() { return crust; }
    public void setCrust(Crust crust) { this.crust = crust; }
    public boolean isStuffedCrust() { return stuffedCrust; }
    public void setStuffedCrust(boolean stuffedCrust) { this.stuffedCrust = stuffedCrust; }

    public void addMeat(Meat m, int qty) { meats.put(m, meats.getOrDefault(m,0)+qty); }
    public void addCheese(Cheese c, int qty) { cheeses.put(c, cheeses.getOrDefault(c,0)+qty); }
    public void addRegular(RegularTopping t, int qty) { regulars.put(t, regulars.getOrDefault(t,0)+qty); }
    public void addSauce(Sauce s) { sauces.add(s); }

    public Map<Meat,Integer> getMeats() { return meats; }
    public Map<Cheese,Integer> getCheeses() { return cheeses; }
    public Map<RegularTopping,Integer> getRegulars() { return regulars; }
    public Set<Sauce> getSauces() { return sauces; }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append(size).append(" ").append(crust).append(stuffedCrust?" (STUFFED)":"");
        if(!meats.isEmpty()) {
            sb.append("\n  Meats: ");
            meats.forEach((k,v)-> sb.append(k).append(v>1?" x"+v:"").append(", "));
            if(sb.charAt(sb.length()-2)==',') sb.setLength(sb.length()-2);
        }
        if(!cheeses.isEmpty()) {
            sb.append("\n  Cheeses: ");
            cheeses.forEach((k,v)-> sb.append(k).append(v>1?" x"+v:"").append(", "));
            if(sb.charAt(sb.length()-2)==',') sb.setLength(sb.length()-2);
        }
        if(!regulars.isEmpty()) {
            sb.append("\n  Regular: ");
            regulars.forEach((k,v)-> sb.append(k).append(v>1?" x"+v:"").append(", "));
            if(sb.charAt(sb.length()-2)==',') sb.setLength(sb.length()-2);
        }
        if(!sauces.isEmpty()) {
            sb.append("\n  Sauces: ").append(String.join(", ", sauces.stream().map(Enum::name).toList()));
        }
        return sb.toString();
    }
}
