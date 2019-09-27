package Investments;

public class Investment implements GeneralInvestment {
    private String name;
    private double value;
    private int quantity;

    public Investment(String name, double value, int quantity) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public double getIndividualValue() {
        return this.value;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double holdings() {
        return this.value * this.quantity;
    }

    public void buy(int amount) {
        this.quantity += amount;
    }

    public void sell() {

    }
}
