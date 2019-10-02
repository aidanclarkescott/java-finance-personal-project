package ui;

import budget.*;

import java.io.IOException;
import java.util.Scanner;

public class BudgetUserInterface {
    private Scanner reader;
    private BudgetLogicBehaviour budget;

    public BudgetUserInterface(Scanner reader) {
        this.reader = reader;
        this.budget = new BudgetLogic();
    }

    public void load() {
        budget.load();
    }

    // MODIFIES: this
    // EFFECTS: asks for new budget name and creates a budget with that name.
    public void createBudgetInput() {
        System.out.print("Budget name: ");
        String budgetName = reader.nextLine();
        System.out.print("Total budget maximum per month: ");
        double budgetCap = Double.parseDouble(reader.nextLine());
        budget.createBudget(budgetName, budgetCap);
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
        Double price = Double.parseDouble(reader.nextLine());
        return price;
    }

    // EFFECTS: prints and returns a user specified budget name.
    public String whichBudgetScanner() {
        System.out.print("Which budget: ");
        String budgetName = reader.nextLine();
        System.out.println("You picked " + budgetName);
        return budgetName;
    }

    // REQUIRES: must have created a budget already.
    // MODIFIES: budget
    // EFFECTS: adds an expense to an existing budget.
    public void addExpense(String budgetName, String name, double price) {
        budget.addExpense(budgetName, name, price);
    }

    // REQUIRES: must have created a budget already.
    // EFFECTS: prints out all expenses and total spent for a particular budget.
    public void printExpenses(String budgetName) {
        budget.printExpenses(budgetName);
    }

    // EFFECTS: prints out all budgets and their total expenses.
    public void printBudgets() {
        budget.printBudgets();
    }

    // EFFECTS: saves the budgets and their expenses.
    public void saveBudgets() throws IOException {
        budget.saveBudgets();
    }

}
