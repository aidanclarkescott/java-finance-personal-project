package Budget;

import java.util.HashMap;

public interface BudgetLogicBehaviour {
    void load();
    void createBudgetInput();
    void createBudget(String budgetName, double budgetCap);
    void addExpense(String budgetName, String name, double price);
    String nameScanner();
    double priceScanner();
    void printExpenses(String budgetName);
    String whichBudgetScanner();
    void printBudgets();
    HashMap<String, Budget> getBudgets();
}
