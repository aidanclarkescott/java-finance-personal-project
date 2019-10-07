package budget;

import java.io.IOException;
import java.util.HashMap;

public interface BudgetLogicBehaviour {
    void load();

    void createBudget(String budgetName, double budgetCap);

    void addExpense(String budgetName, String name, double price);

    void saveBudgets() throws IOException;

    HashMap<String, Budget> getBudgets();
}
