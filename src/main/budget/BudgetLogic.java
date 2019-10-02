package budget;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class BudgetLogic implements BudgetLogicBehaviour {
    private Scanner reader;
    private HashMap<String, Budget> budgets;

    // EFFECTS: Creates a new ui object with a budget list and starts the program.
    public BudgetLogic(Scanner reader) {
        this.reader = reader;
        this.budgets = new HashMap<String, Budget>();
    }

    // MODIFIES: this
    // EFFECTS: loads a budget and all of its expenses from a save file
    @Override
    public void load() {
        try {
            File file = new File("savefile.txt");
            Scanner fileReader = new Scanner(file);
            String budgetName = fileReader.nextLine();
            double budgetCap = Double.parseDouble(fileReader.nextLine());
            this.budgets.put(budgetName, new Budget(budgetName, budgetCap));
            while (fileReader.hasNextLine()) {
                String name = fileReader.nextLine();
                double price = Double.parseDouble(fileReader.nextLine());
                this.budgets.get(budgetName).addExpense(name, price);
            }
        } catch (Exception e) {
            System.out.println("");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a budget with budgetName and budgetCap.
    @Override
    public void createBudget(String budgetName, double budgetCap) {
        this.budgets.put(budgetName, new Budget(budgetName, budgetCap));
    }

    // REQUIRES: must have created a budget already.
    // MODIFIES: this
    // EFFECTS: adds an expense to an existing budget.
    @Override
    public void addExpense(String budgetName, String name, double price) {
        try {
            this.budgets.get(budgetName).addExpense(name, price);
        } catch (Exception e) {
            throw new NullPointerException("You haven't created a budget yet.");
        }
    }

    // REQUIRES: must have created a budget already.
    // EFFECTS: prints out all expenses and total spent for a particular budget.
    @Override
    public void printExpenses(String budgetName) {
        try {
            this.budgets.get(budgetName).printExpenses();
        } catch (Exception e) {
            throw new NullPointerException("You haven't created a budget yet.");
        }

    }

    // EFFECTS: prints out all budgets and their total expenses.
    public void printBudgets() {
        int i = 0;
        for (String budgetName : this.budgets.keySet()) {
            i++;
            System.out.println(i + ". " + budgetName + ", Total Spent: "
                    + this.budgets.get(budgetName).getTotalExpenses() + ", Total Remaining: "
                    + this.budgets.get(budgetName).getCurrentBudget());
        }
        System.out.println("");
    }

    // EFFECTS: returns the budgets HashMap.
    @Override
    public HashMap<String, Budget> getBudgets() {
        return this.budgets;
    }

    public void saveBudgets() throws IOException {
        for (Budget budget : budgets.values()) {
            budget.saveExpenses();
        }
    }
}
