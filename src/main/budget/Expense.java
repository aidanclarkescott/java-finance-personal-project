package budget;

public class Expense {
    private String name;
    private double price;

    // REQUIRES: price cannot be negative.
    // EFFECTS: creates a new expense with a name and a price.
    public Expense(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // EFFECTS: returns the expenses name.
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the expenses price.
    public double getPrice() {
        return this.price;
    }

    // EFFECTS: returns the format for printing expenses.
    public String toString() {
        return getName() + " " + getPrice();
    }

}
