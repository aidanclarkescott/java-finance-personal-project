package budget;

import java.io.IOException;
import java.util.HashMap;

public interface BudgetLogicBehaviour {
    String load(String fileName) throws IOException, TooExpensiveException;

    void createBudget(String budgetName, double budgetCap) throws DuplicateBudgetException;

    void addExpense(String budgetName, String name, double price) throws NoBudgetException, TooExpensiveException;

    void removeExpense(String budgetName, String name, double price);

    void saveBudgets() throws IOException;

    HashMap<String, Budget> getBudgets();
}
