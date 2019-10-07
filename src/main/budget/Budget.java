package budget;

import java.io.*;
import java.util.ArrayList;


public class Budget {
    private ArrayList<Expense> expenses;
    private double budgetCap;
    private double totalExpenses;
    private String name;

    // REQUIRES: budgetCap cannot be negative;
    // EFFECTS: creates a new budget object with a list of expenses.
    public Budget(String name, double budgetCap) {
        this.expenses = new ArrayList<Expense>();
        this.name = name;
        this.budgetCap = budgetCap;
    }

    // MODIFIES: this
    // EFFECTS: adds an expense to the budget with a given name and price.
    public void addExpense(String name, double price) {
        if (budgetCap - (totalExpenses + price) < 0) {
            System.out.println("You cannot afford that item. \n");
        } else {
            this.expenses.add(new Expense(name, price));
            this.totalExpenses += price;
        }
    }

    // EFFECTS: returns the total cost of all the expenses in the budget.
    public double getTotalExpenses() {
        return this.totalExpenses;
    }

    // EFFECTS: returns the amount left in the budget.
    public double getCurrentBudget() {
        return this.budgetCap - this.totalExpenses;
    }

    // EFFECTS: returns the initial maximum budgetCap specified by the user.
    public double getBudgetCap() {
        return this.budgetCap;
    }

    // EFFECTS: returns the name of the budget.
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the expense list.
    public ArrayList<Expense> getExpenseList() {
        return this.expenses;
    }

    // EFFECTS: saves the name and budget cap of a budget to file along with all of its expenses.
    public void saveExpenses() throws IOException {
        FileWriter writer = new FileWriter("savefile.txt");
        writer.write(this.name + "\n");
        writer.write(this.budgetCap + "\n");
        for (Expense expense : this.expenses) {
            writer.write(expense.getName() + "\n");
            String price = "" + expense.getPrice() + "\n";
            writer.write(price);
        }
        writer.close();
    }

}
