package budget;

import org.junit.jupiter.api.Test;

import java.util.Objects;

public class Expense extends BudgetComponent {
    private double price;
    private Budget budget;

    // REQUIRES: price cannot be negative.
    // EFFECTS: creates a new expense with a name and a price.
    public Expense(String name, double price) {
        super(name);
        this.price = price;
        this.budget = null;
    }

    // EFFECTS: prints out information in heirarchy for expense.
    public void display(int indent) {
        printIndent(indent);
        System.out.println("Expense: " + name + ",  " + price);
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

    // EFFECTS: returns true if expense has a budget, false otherwise
    public Boolean hasBudget() {
        if (this.budget == null) {
            return false;
        }
        return true;
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

        return name.equals(expense.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
