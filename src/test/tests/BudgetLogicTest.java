package tests;

import budget.BudgetLogic;
import budget.NoBudgetException;
import budget.TooExpensiveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;


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
    public void testAddExpenseExpectNoExceptions() {
        budget.createBudget("Test Budget", 500);
        try {
            budget.addExpense("Test Budget", "Test Item", 100.50);
        } catch (NoBudgetException e) {
            fail();
        } catch (TooExpensiveException e) {
            fail();
        }
        Assertions.assertTrue(budget.getBudgets().get("Test Budget").getExpenseList()
                .get(0).getName().equals("Test Item"));
        Assertions.assertEquals(100.50, budget.getBudgets().get("Test Budget").getExpenseList()
                .get(0).getPrice());
        Assertions.assertEquals(1, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(500 - 100.50, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddTwoExpensesExpectNoExceptions() {
        budget.createBudget("Test Budget", 500);
        try {
            budget.addExpense("Test Budget", "Test Item", 100.50);
            budget.addExpense("Test Budget", "Test Item 2", 50.55);
        } catch (NoBudgetException e) {
            fail();
        } catch (TooExpensiveException e) {
            fail();
        }
        Assertions.assertTrue(budget.getBudgets().get("Test Budget").getExpenseList()
                .get(1).getName().equals("Test Item 2"));
        Assertions.assertEquals(50.55, budget.getBudgets().get("Test Budget").getExpenseList()
                .get(1).getPrice());
        Assertions.assertEquals(2, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(500 - 100.50 - 50.55, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddExpenseToSecondBudgetExpectNoExceptions() {
        budget.createBudget("Test Budget 1", 500);
        budget.createBudget("Test Budget 2", 200);
        try {
            budget.addExpense("Test Budget 2", "Test Item", 150);
        } catch (NoBudgetException e) {
            fail();
        } catch (TooExpensiveException e) {
            fail();
        }
        Assertions.assertTrue(budget.getBudgets().get("Test Budget 2").getExpenseList()
                .get(0).getName().equals("Test Item"));
        Assertions.assertEquals(150, budget.getBudgets().get("Test Budget 2").getExpenseList().get(0).getPrice());
        Assertions.assertEquals(1, budget.getBudgets().get("Test Budget 2").getExpenseList().size());
        Assertions.assertEquals(200 - 150, budget.getBudgets().get("Test Budget 2").getCurrentBudget());
    }

    @Test
    public void testAddExpenseOverBudgetExpectTooExpensiveException() {
        budget.createBudget("Test Budget", 500);
        try {
            budget.addExpense("Test Budget", "Test Item", 500.01);
            fail();
        } catch (NoBudgetException e) {
            fail();
        } catch (TooExpensiveException e) {

        }
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getTotalExpenses());
        Assertions.assertEquals(500, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddExpenseExpectNoBudgetException() {
        try {
            budget.addExpense("Test Budget", "Test Item", 50);
            fail();
        } catch (NoBudgetException e) {

        } catch (TooExpensiveException e) {
            fail();
        }
    }

    @Test
    public void testSaveBudgetsOneExpense() throws TooExpensiveException {
        budget.createBudget("TestBudget", 500);
        budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        try {
            budget.saveBudgets();
        } catch (IOException e) {
            fail();
        }
        BudgetLogic newBudgetLogic = new BudgetLogic();
        try {
            newBudgetLogic.load();
        } catch (IOException e) {
            fail();
        }
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
    }

    @Test
    public void testSaveBudgetsTwoExpenses() throws TooExpensiveException {
        budget.createBudget("TestBudget", 500);
        budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        budget.getBudgets().get("TestBudget").addExpense("TestExpense2", 100);
        try {
            budget.saveBudgets();
        } catch (IOException e) {
            fail();
        }
        BudgetLogic newBudgetLogic = new BudgetLogic();
        try {
            newBudgetLogic.load();
        } catch (IOException e) {
            fail();
        }
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
        Assertions.assertEquals("TestExpense2", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(1).getName());
    }

    @Test
    public void testLoadOneExpense() throws TooExpensiveException {
        budget.createBudget("TestBudget", 500);
        budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        try {
            budget.getBudgets().get("TestBudget").saveExpenses();
        } catch (IOException e) {
            fail();
        }
        BudgetLogic newBudgetLogic = new BudgetLogic();
        try {
            newBudgetLogic.load();
        } catch (IOException e) {
            fail();
        }
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
    }
}
