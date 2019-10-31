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
        this.expenses = new ArrayList<>();
        this.name = name;
        this.budgetCap = budgetCap;
    }

    // MODIFIES: this
    // EFFECTS: adds an expense to the budget with a given name and price.
    public void addExpense(String name, double price) throws TooExpensiveException {
        if ((totalExpenses + price) > budgetCap) {
            throw new TooExpensiveException();
        }

        Expense tempExpense = new Expense(name, price);

        if (!this.expenses.contains(tempExpense)) {
            this.expenses.add(tempExpense);
            tempExpense.setBudget(this);
        }
        this.totalExpenses += price;
    }

    // MODIFIES: this
    // EFFECTS: adds an expense object to the list of expenses
    public void addExpenseSimple(Expense expense) {
        if (!this.expenses.contains(expense)) {
            this.expenses.add(expense);
            expense.setBudget(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an expense from the budget
    public void removeExpense(Expense tempExpense) {
        expenses.remove(tempExpense);
        tempExpense.removeBudget(this);
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
        FileWriter writer = new FileWriter("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt");
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
