package budget;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public interface BudgetLogicBehaviour {
    String load(String fileName) throws IOException, TooExpensiveException, DuplicateItemException;

    void createBudget(String budgetName, double budgetCap) throws DuplicateBudgetException;

    void addExpense(String budgetName, String name, double price) throws NoBudgetException,
            TooExpensiveException, DuplicateItemException;

    void removeExpense(String budgetName, String name, double price) throws NoBudgetException, NonexistentItemException;

    void saveBudgets() throws IOException;

    HashMap<String, Budget> getBudgets();

    void addNestedBudget(String budgetName, String budgetNestedWithin);

    void display(String budgetName);
}
