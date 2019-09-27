package Budget;

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

    // EFFECTS: prints out all the expenses in the budget.
    public void printExpenses() {
        for (Expense expense : this.expenses) {
            System.out.println(expense);
        }
        System.out.println("");
        System.out.println("Amount remaining in your budget: " + getCurrentBudget());
        System.out.println("Total spent: " + getTotalExpenses());
        System.out.println("");
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

}
