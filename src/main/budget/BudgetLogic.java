package budget;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class BudgetLogic implements BudgetLogicBehaviour {
    private HashMap<String, Budget> budgets;

    // EFFECTS: Creates a new ui object with a budget list and starts the program.
    public BudgetLogic() {
        this.budgets = new HashMap<>();
    }

    // TODO: add tests for exception
    // MODIFIES: this
    // EFFECTS: loads a budget and all of its expenses from a save file
    @Override
    public void load() throws IOException, TooExpensiveException {
        File file = new File("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt");
        Scanner fileReader = new Scanner(file);
        String budgetName = fileReader.nextLine();
        double budgetCap = Double.parseDouble(fileReader.nextLine());
        this.budgets.put(budgetName, new Budget(budgetName, budgetCap));
        while (fileReader.hasNextLine()) {
            String name = fileReader.nextLine();
            double price = Double.parseDouble(fileReader.nextLine());
            this.budgets.get(budgetName).addExpense(name, price);
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
    public void addExpense(String budgetName, String name, double price) throws NoBudgetException,
            TooExpensiveException {
        if (this.budgets.get(budgetName) == null) {
            throw new NoBudgetException();
        }
        this.budgets.get(budgetName).addExpense(name, price);
    }

    // EFFECTS: returns the budgets HashMap.
    @Override
    public HashMap<String, Budget> getBudgets() {
        return this.budgets;
    }

    // MODIFIES: savefile.txt
    // EFFECTS: saves the budgets and their expenses to file.
    public void saveBudgets() throws IOException {
        for (Budget budget : budgets.values()) {
            budget.saveExpenses();
        }
    }
}
