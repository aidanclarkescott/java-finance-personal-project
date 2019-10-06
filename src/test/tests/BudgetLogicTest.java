package tests;

import budget.BudgetLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class BudgetLogicTest {

    private BudgetLogic budget;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        budget = new BudgetLogic();
    }

    @Test
    public void testCreateBudget() {
        budget.createBudget("Test Budget", 500);
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget"));
        Assertions.assertEquals(500.0, budget.getBudgets().get("Test Budget").getBudgetCap());
        Assertions.assertEquals(1, budget.getBudgets().size());
    }

    @Test
    public void testCreateTwoBudgets() {
        budget.createBudget("Test Budget 1", 500);
        budget.createBudget("Test Budget 2", 200);
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget 1"));
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget 2"));
        Assertions.assertEquals(2, budget.getBudgets().size());
        Assertions.assertEquals(500, budget.getBudgets().get("Test Budget 1").getBudgetCap());
    }

    @Test
    public void testAddNoExpense() {
        budget.createBudget("Test Budget", 500);
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getTotalExpenses());
    }

    @Test
    public void testAddExpense() {
        budget.createBudget("Test Budget", 500);
        budget.addExpense("Test Budget", "Test Item", 100.50);
        Assertions.assertTrue(budget.getBudgets().get("Test Budget").getExpenseList()
                .get(0).getName().equals("Test Item"));
        Assertions.assertEquals(100.50, budget.getBudgets().get("Test Budget").getExpenseList()
                .get(0).getPrice());
        Assertions.assertEquals(1, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(500 - 100.50, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenses() {
        budget.createBudget("Test Budget", 500);
        budget.addExpense("Test Budget", "Test Item", 100.50);
        budget.addExpense("Test Budget", "Test Item 2", 50.55);
        Assertions.assertTrue(budget.getBudgets().get("Test Budget").getExpenseList()
                .get(1).getName().equals("Test Item 2"));
        Assertions.assertEquals(50.55, budget.getBudgets().get("Test Budget").getExpenseList()
                .get(1).getPrice());
        Assertions.assertEquals(2, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(500 - 100.50 - 50.55, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddExpenseToSecondBudget() {
        budget.createBudget("Test Budget 1", 500);
        budget.createBudget("Test Budget 2", 200);
        budget.addExpense("Test Budget 2", "Test Item", 150);
        Assertions.assertTrue(budget.getBudgets().get("Test Budget 2").getExpenseList()
                .get(0).getName().equals("Test Item"));
        Assertions.assertEquals(150, budget.getBudgets().get("Test Budget 2").getExpenseList().get(0).getPrice());
        Assertions.assertEquals(1, budget.getBudgets().get("Test Budget 2").getExpenseList().size());
        Assertions.assertEquals(200 - 150, budget.getBudgets().get("Test Budget 2").getCurrentBudget());
    }

    @Test
    public void testAddExpenseOverBudget() {
        budget.createBudget("Test Budget", 500);
        budget.addExpense("Test Budget", "Test Item", 500.01);
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getTotalExpenses());
        Assertions.assertEquals(500, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testLoadOneExpense() throws IOException {
        budget.createBudget("TestBudget", 500);
        budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        budget.getBudgets().get("TestBudget").saveExpenses();
        BudgetLogic newBudgetLogic = new BudgetLogic();
        newBudgetLogic.load();
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
    }
}
