package investments;

public class Investment implements GeneralInvestment {
    private String name;
    private double value;
    private int quantity;

    // EFFECTS: creates a new investment object with a name, value and quantity owned.
    public Investment(String name, double value, int quantity) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
    }

    // EFFECTS: returns the investment's name.
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the individual value of the investment.
    public double getIndividualValue() {
        return this.value;
    }

    // EFFECTS: returns the quantity owned of the investment.
    public int getQuantity() {
        return this.quantity;
    }

    // EFFECTS: returns the total value owned of the stock.
    public double holdings() {
        return this.value * this.quantity;
    }

    // REQUIRES: amount cannot be negative.
    // MODIFIES: this
    // EFFECTS: increases the quantity owned of the stock by given amount.
    public void buy(int amount) {
        this.quantity += amount;
    }

    // MODIFIES: this
    // EFFECTS: sells a given quantity of the investment.
    public void sell(int amount) {
        this.quantity -= amount;
    }

    // EFFECTS: outlines formatting for printing an investment.
    public String toString() {
        return getName() + ", Individual Value: " + getIndividualValue() + ", Your Holdings: "
                + Math.round(((holdings() * 100) / 100));
    }
}
