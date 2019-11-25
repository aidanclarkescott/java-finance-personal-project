package budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Budget extends BudgetComponent {
    private ArrayList<Expense> expenses;
    private List<BudgetComponent> budgetComponents;
    private double budgetCap;
    private double totalExpenses;

    // REQUIRES: budgetCap cannot be negative;
    // EFFECTS: creates a new budget object with a list of expenses.
    public Budget(String name, double budgetCap) {
        super(name);
        this.expenses = new ArrayList<>();
        this.budgetCap = budgetCap;
        this.budgetComponents = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a budget component to the budget component list for composite pattern.
    public void addBudgetComponent(BudgetComponent b) {
        this.budgetComponents.add(b);
    }

    // EFFECTS: prints out budget hierarchy.
    public void display(int indent) {
        printIndent(indent);
        System.out.println("Budget: " + name);
        for (BudgetComponent b : budgetComponents) {
            b.display(indent + 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an expense to the budget with a given name and price.
    public void addExpense(String name, double price) throws TooExpensiveException, DuplicateItemException {
        if ((totalExpenses + price) > budgetCap) {
            throw new TooExpensiveException();
        }

        Expense tempExpense = new Expense(name, price);

        if (!this.expenses.contains(tempExpense)) {
            addBudgetComponent(tempExpense);
            this.expenses.add(tempExpense);
            tempExpense.setBudget(this);
            this.totalExpenses += price;
        } else {
            throw new DuplicateItemException();
        }
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
    public void removeExpense(Expense tempExpense) throws NonexistentItemException {
        if (expenses.contains(tempExpense)) {
            expenses.remove(tempExpense);
            this.totalExpenses -= tempExpense.getPrice();
        } else {
            throw new NonexistentItemException();
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

    // EFFECTS: returns budget components.
    public List<BudgetComponent> getBudgetComponents() {
        return this.budgetComponents;
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
