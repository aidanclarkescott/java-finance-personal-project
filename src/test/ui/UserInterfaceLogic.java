package ui;

import Budget.Budget;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterfaceLogic {
    private Scanner reader;
    private HashMap<String, Budget> budgets;

    // EFFECTS: Creates a new ui object with a budget list and starts the program.
    public UserInterfaceLogic(Scanner reader) {
        this.reader = reader;
        this.budgets = new HashMap<String, Budget>();
    }

    // EFFECTS: Prints out the main menu and option functionality.
    public void menu() {
        System.out.println("Personal Finance Application \n");
        while (true) {
            System.out.println("Menu: \n 1. Create a new budget " + "\n 2. Add expense to existing budget "
                    + "\n 3. View stock portfolio " + "\n 4. Print budget expenses"
                    + "\n 5. Print budget list" + "\n 6. Quit \n");
            String command = reader.nextLine();
            if (command.equals("6")) {
                break;
            } else if (command.equals("1")) {
                createBudgetInput();
            } else if (command.equals("2")) {
                String budgetName = whichBudgetScanner();
                String name = nameScanner();
                double price = priceScanner();
                addExpense(budgetName, name, price);
            } else if (command.equals("3")) {
                System.out.println("Stocks: \n AAPL: $150 \n GOOG: $1000 \n etc. \n");
            } else if (command.equals("4")) {
                String budgetName = whichBudgetScanner();
                printExpenses(budgetName);
            } else if (command.equals("5")) {
                printBudgets();
            }
        }
        System.out.println("Thank you for using the application.");
    }

    // MODIFIES: this
    // EFFECTS: asks for new budget name and creates a budget with that name.
    public void createBudgetInput() {
        System.out.print("Budget name: ");
        String budgetName = reader.nextLine();
        System.out.print("Total budget maximum per month: ");
        double budgetCap = Double.parseDouble(reader.nextLine());
        createBudget(budgetName, budgetCap);
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: creates a budget with budgetName and budgetCap.
    public void createBudget(String budgetName, double budgetCap) {
        this.budgets.put(budgetName, new Budget(budgetName, budgetCap));
    }

    // REQUIRES: must have created a budget already.
    // MODIFIES: this
    // EFFECTS: adds an expense to an existing budget.
    public void addExpense(String budgetName, String name, double price) {
        try {
            this.budgets.get(budgetName).addExpense(name, price);
        } catch (Exception e) {
            throw new NullPointerException("You haven't created a budget yet.");
        }
    }

    // EFFECTS: returns user given item name.
    private String nameScanner() {
        System.out.print("Name of item: ");
        String name = reader.nextLine();
        return name;
    }

    // EFFECTS: returns user given item price.
    private double priceScanner() {
        System.out.print("Price: ");
        Double price = Double.parseDouble(reader.nextLine());
        return price;
    }

    // REQUIRES: must have created a budget already.
    // EFFECTS: prints out all expenses and total spent for a particular budget.
    public void printExpenses(String budgetName) {
        try {
            this.budgets.get(budgetName).printExpenses();
        } catch (Exception e) {
            throw new NullPointerException("You haven't created a budget yet.");
        }

    }

    // EFFECTS: prints and returns a user specified budget name.
    private String whichBudgetScanner() {
        System.out.print("Which budget: ");
        String budgetName = reader.nextLine();
        System.out.println("You picked " + budgetName);
        return budgetName;

    }

    // EFFECTS: prints out all budgets and their total expenses.
    public void printBudgets() {
        int i = 0;
        for (String budgetName : this.budgets.keySet()) {
            i++;
            System.out.println(i + ". " + budgetName + ", Total Spent: " +
                    this.budgets.get(budgetName).getTotalExpenses() + ", Total Remaining: " +
                    this.budgets.get(budgetName).getCurrentBudget());
        }
        System.out.println("");
    }

    // EFFECTS: returns the budgets HashMap.
    public HashMap<String, Budget> getBudgets() {
        return this.budgets;

    }

}
