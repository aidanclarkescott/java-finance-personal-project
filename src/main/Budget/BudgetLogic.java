package Budget;

import java.io.File;
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

        }
    }

    // MODIFIES: this
    // EFFECTS: asks for new budget name and creates a budget with that name.
    @Override
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

    // EFFECTS: returns user given item name.
    @Override
    public String nameScanner() {
        System.out.print("Name of item: ");
        String name = reader.nextLine();
        return name;
    }

    // EFFECTS: returns user given item price.
    @Override
    public double priceScanner() {
        System.out.print("Price: ");
        Double price = Double.parseDouble(reader.nextLine());
        return price;
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

    // EFFECTS: prints and returns a user specified budget name.
    @Override
    public String whichBudgetScanner() {
        System.out.print("Which budget: ");
        String budgetName = reader.nextLine();
        System.out.println("You picked " + budgetName);
        return budgetName;

    }

    // EFFECTS: prints out all budgets and their total expenses.
    @Override
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
    @Override
    public HashMap<String, Budget> getBudgets() {
        return this.budgets;
    }
}
