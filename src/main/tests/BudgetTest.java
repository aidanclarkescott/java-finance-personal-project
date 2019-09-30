package tests;

import budget.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

public class BudgetTest {
    private Budget budget;
    private BudgetLogic budgetLogic;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        budget = new Budget("Test Name", 500);
        budgetLogic = new BudgetLogic(reader);
    }

    @Test
    public void testAddNoExpense() {
        Assertions.assertEquals(0, budget.getExpenseList().size());
        Assertions.assertEquals(0, budget.getTotalExpenses());
    }

    @Test
    public void testAddExpense() {
        budget.addExpense("Test Item", 100.50);
        Assertions.assertTrue(budget.getExpenseList().get(0).getName().equals("Test Item"));
        Assertions.assertEquals(100.50, budget.getExpenseList().get(0).getPrice());
        Assertions.assertEquals(1, budget.getExpenseList().size());
        Assertions.assertEquals(100.50, budget.getTotalExpenses());
        Assertions.assertEquals(500 - 100.50, budget.getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenses() {
        budget.addExpense("Test Item 1", 50.55);
        budget.addExpense("Test Item 2", 60.22);
        Assertions.assertTrue(budget.getExpenseList().get(1).getName().equals("Test Item 2"));
        Assertions.assertEquals(2, budget.getExpenseList().size());
        Assertions.assertEquals(50.55 + 60.22, budget.getTotalExpenses());
        Assertions.assertEquals(500 - 50.55 - 60.22, budget.getCurrentBudget());
    }

    @Test
    public void testAddExpenseOverBudget() {
        budget.addExpense("Test Overpriced Item", 1000.00);
        Assertions.assertEquals(0, budget.getExpenseList().size());
        Assertions.assertEquals(0, budget.getTotalExpenses());
        Assertions.assertEquals(500, budget.getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenseOverBudget() {
        budget.addExpense("Test Item 1", 500.01);
        budget.addExpense("Test Item 1", 10000);
        Assertions.assertEquals(0, budget.getExpenseList().size());
        Assertions.assertEquals(0, budget.getTotalExpenses());
        Assertions.assertEquals(500, budget.getCurrentBudget());
    }

    @Test
    public void testSaveExpensesOneExpense() throws IOException {
        budgetLogic.createBudget("TestBudget", 500);
        budgetLogic.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        budgetLogic.getBudgets().get("TestBudget").saveExpenses();
        BudgetLogic newBudgetLogic = new BudgetLogic(reader);
        newBudgetLogic.load();
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
    }

    @Test
    public void testSaveExpensesTwoExpenses() throws IOException {
        budgetLogic.createBudget("TestBudget", 500);
        budgetLogic.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        budgetLogic.getBudgets().get("TestBudget").addExpense("TestExpense2", 100);
        budgetLogic.getBudgets().get("TestBudget").saveExpenses();
        BudgetLogic newBudgetLogic = new BudgetLogic(reader);
        newBudgetLogic.load();
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
        Assertions.assertEquals("TestExpense2", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(1).getName());
    }
}
