package budget;

import java.io.IOException;
import java.util.HashMap;

public interface BudgetLogicBehaviour {
    void load() throws IOException, TooExpensiveException;

    void createBudget(String budgetName, double budgetCap);

    void addExpense(String budgetName, String name, double price) throws NoBudgetException, TooExpensiveException;

    void saveBudgets() throws IOException;

    HashMap<String, Budget> getBudgets();
}
