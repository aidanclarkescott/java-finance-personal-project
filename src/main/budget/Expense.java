package budget;

import org.junit.jupiter.api.Test;

import java.util.Objects;

public class Expense {
    private String name;
    private double price;
    private Budget budget;

    // REQUIRES: price cannot be negative.
    // EFFECTS: creates a new expense with a name and a price.
    public Expense(String name, double price) {
        this.name = name;
        this.price = price;
        this.budget = null;
    }

    // EFFECTS: returns the expenses name.
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the expenses price.
    public double getPrice() {
        return this.price;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
        budget.addExpenseSimple(this);
    }

    // EFFECTS: returns the format for printing expenses.
    public String toString() {
        return getName() + " " + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Expense expense = (Expense) o;

        return this.price == expense.getPrice() && name.equals(expense.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
