package ui;

import budget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BudgetUserInterfaceLogic {
    private Scanner reader;
    private BudgetLogicBehaviour budget;

    public BudgetUserInterfaceLogic() {
        this.reader = new Scanner(System.in);
        this.budget = new BudgetLogic();
    }

    // MODIFIES: budget
    // EFFECTS: loads one budget and all its expenses into the system.
    public void load() {
        try {
            budget.load("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt");
        } catch (IOException e) {
            System.out.println("File not found!");
        } catch (NoSuchElementException e) {
            System.out.println("");
        } catch (TooExpensiveException e) {
            System.out.println("Item too expensive for budget!");
        } catch (DuplicateItemException e) {
            System.out.println("That item is already in that budget!");
        }
    }

    // MODIFIES: budget
    // EFFECTS: creates a budget based on given inputs.
    public void createBudgetUIInput(String budgetNameInput, String budgetCapInput) throws DuplicateBudgetException {
        String budgetName = budgetNameInput;
        double budgetCap = Double.parseDouble(budgetCapInput);
        budget.createBudget(budgetName, budgetCap);
    }

    // MODIFIES: budget
    // EFFECTS: adds an expense to a given budget.
    public void addExpenseUI(String budgetName, String name, double price) throws NoBudgetException,
            TooExpensiveException, DuplicateItemException {
        budget.addExpense(budgetName, name, price);
    }

    // MODIFIES: budget
    // EFFECTS: removes an expense from a given budget.
    public void removeExpenseUI(String budgetName, String expenseName, double price) throws NoBudgetException,
            NonexistentItemException {
        budget.removeExpense(budgetName, expenseName, price);
    }

    // EFFECTS: formats all budgets into specific strings and adds them to a list
    public ArrayList<String> formatBudgetPrinting() {
        ArrayList<String> budgetList = new ArrayList<>();
        int i = 0;
        for (String budgetName : this.budget.getBudgets().keySet()) {
            i++;
            budgetList.add(i + ". " + budgetName + ", Total Spent: "
                    + this.budget.getBudgets().get(budgetName).getTotalExpenses() + ", Total Remaining: "
                    + this.budget.getBudgets().get(budgetName).getCurrentBudget());
        }
        return budgetList;
    }

    // EFFECTS: formats all expenses in a given budget and adds them to a list.
    public ArrayList<String> printExpensesUI(String budgetName) throws NoBudgetException {
        ArrayList<String> expenseList = new ArrayList<>();
        if (this.budget.getBudgets().get(budgetName) == null) {
            throw new NoBudgetException();
        }

        for (Expense expense : this.budget.getBudgets().get(budgetName).getExpenseList()) {
            expenseList.add(expense.toString());
        }

        expenseList.add("\nAmount remaining in your budget: " + this.budget.getBudgets().get(budgetName)
                .getCurrentBudget());
        expenseList.add("Total spent: " + this.budget.getBudgets().get(budgetName)
                .getTotalExpenses());

        return expenseList;
    }

    // -----PRE-UI METHODS--------------------------------------------------------------------------------------------

    // MODIFIES: this
    // EFFECTS: asks for new budget name and creates a budget with that name.
    public void createBudgetInput() {
        String budgetName = whichBudgetScanner();
        System.out.print("Total budget maximum per month: ");
        double budgetCap = Double.parseDouble(reader.nextLine());
        Boolean isNested = nestedBudgetInput();
        try {
            budget.createBudget(budgetName, budgetCap);
            if (isNested) {
                String budgetNestedWithin = whichBudgetScanner();
                budget.addNestedBudget(budgetName, budgetNestedWithin);
            }
        } catch (DuplicateBudgetException e) {
            System.out.println("You have already created that budget!");
        }
        System.out.println("");
    }

    // EFFECTS: takes in user input to decide weather a budget will be nested or not
    public Boolean nestedBudgetInput() {
        System.out.println("Do you want to put this budget in an existing budget? (Yes/No)");
        String isNested = reader.nextLine();
        if (isNested.equals("Yes")) {
            return true;
        }
        return false;
    }

    // EFFECTS: displays the budget heirarchy of the given budget.
    public void displayInput() {
        String tempBudget = whichBudgetScanner();
        budget.display(tempBudget);
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
            System.out.println("You cannot afford that item.");
        } catch (DuplicateItemException e) {
            System.out.println("That item is already in that budget.");
        }
    }

    // MODIFIES: budget
    // EFFECTS: removes an expense from a budget
    public void removeExpense(String budgetName, String expenseName, double price) {
        try {
            budget.removeExpense(budgetName, expenseName, price);
        } catch (NoBudgetException e) {
            System.out.println("That budget doesn't exist.");
        } catch (NonexistentItemException e) {
            System.out.println("That item isn't in that budget");
        }
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
