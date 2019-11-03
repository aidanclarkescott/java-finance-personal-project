package ui;

import budget.*;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BudgetUserInterface {
    private Scanner reader;
    private BudgetLogicBehaviour budget;

    public BudgetUserInterface(Scanner reader) {
        this.reader = reader;
        this.budget = new BudgetLogic();
    }

    public void load() {
        try {
            budget.load("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt");
        } catch (IOException e) {
            System.out.println("File not found!");
        } catch (TooExpensiveException e) {
            System.out.println("Item too expensive for budget!");
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for new budget name and creates a budget with that name.
    public void createBudgetInput() {
        String budgetName = whichBudgetScanner();
        System.out.print("Total budget maximum per month: ");
        double budgetCap = Double.parseDouble(reader.nextLine());
        try {
            budget.createBudget(budgetName, budgetCap);
        } catch (DuplicateBudgetException e) {
            System.out.println("You have already created that budget!");
        }
        System.out.println("");
    }

    // EFFECTS: returns user given item name.
    public String nameScanner() {
        System.out.print("Name of item: ");
        String name = reader.nextLine();
        return name;
    }

    // EFFECTS: returns user given item price.
    public double priceScanner() {
        System.out.print("Price: ");
        return Double.parseDouble(reader.nextLine());
    }

    // EFFECTS: prints and returns a user specified budget name.
    public String whichBudgetScanner() {
        System.out.print("Budget name: ");
        String budgetName = reader.nextLine();
        return budgetName;
    }

    // REQUIRES: must have created a budget already.
    // MODIFIES: budget
    // EFFECTS: adds an expense to an existing budget.
    public void addExpense(String budgetName, String name, double price) {
        try {
            budget.addExpense(budgetName, name, price);
        } catch (NoBudgetException e) {
            System.out.println("You haven't created a budget yet.");
        } catch (TooExpensiveException e) {
            System.out.println("You cannot afford that item. \n");
        }
    }

    // MODIFIES: budget
    // EFFECTS: removes an expense from a budget
    public void removeExpense(String budgetName, String expenseName, double price) {
        budget.removeExpense(budgetName, expenseName, price);
    }

    // EFFECTS: prints out all expenses and total spent for a particular budget.
    public void printExpenses(String budgetName) throws NoBudgetException {
        if (this.budget.getBudgets().get(budgetName) == null) {
            throw new NoBudgetException();
        }

        for (Expense expense : this.budget.getBudgets().get(budgetName).getExpenseList()) {
            System.out.println(expense);
        }
        System.out.println("");
        System.out.println("Amount remaining in your budget: " + this.budget.getBudgets().get(budgetName)
                .getCurrentBudget());
        System.out.println("Total spent: " + this.budget.getBudgets().get(budgetName)
                .getTotalExpenses());

    }

    // EFFECTS: prints out all budgets and their total expenses.
    public void printBudgets() {
        int i = 0;
        for (String budgetName : this.budget.getBudgets().keySet()) {
            i++;
            System.out.println(i + ". " + budgetName + ", Total Spent: "
                    + this.budget.getBudgets().get(budgetName).getTotalExpenses() + ", Total Remaining: "
                    + this.budget.getBudgets().get(budgetName).getCurrentBudget());
        }
        System.out.println("");
    }

    // EFFECTS: saves the budgets and their expenses.
    public void saveBudgets() {
        try {
            budget.saveBudgets();
        } catch (IOException e) {
            System.out.println("No file was found to save to!");
        }
    }

}
