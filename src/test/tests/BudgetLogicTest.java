package tests;

import budget.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;


public class BudgetLogicTest {

    private BudgetLogic budget;

    @BeforeEach
    public void setup() {
        budget = new BudgetLogic();
    }

    @Test
    public void testCreateBudget() {
        try {
            budget.createBudget("Test Budget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget"));
        Assertions.assertEquals(500.0, budget.getBudgets().get("Test Budget").getBudgetCap());
        Assertions.assertEquals(1, budget.getBudgets().size());
    }

    @Test
    public void testCreateDuplicateBudget() {
        try {
            budget.createBudget("Test Budget", 500);
            budget.createBudget("Test Budget", 500);
            fail();
        } catch (DuplicateBudgetException e) {

        }
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget"));
        Assertions.assertEquals(1, budget.getBudgets().size());
    }

    @Test
    public void testCreateTwoBudgets() {
        try {
            budget.createBudget("Test Budget 1", 500);
            budget.createBudget("Test Budget 2", 200);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget 1"));
        Assertions.assertTrue(budget.getBudgets().containsKey("Test Budget 2"));
        Assertions.assertEquals(2, budget.getBudgets().size());
        Assertions.assertEquals(500, budget.getBudgets().get("Test Budget 1").getBudgetCap());
    }

    @Test
    public void testAddNestedBudget() throws DuplicateBudgetException {
        budget.createBudget("Main", 500);
        budget.createBudget("Extra", 50);
        budget.addNestedBudget("Extra", "Main");
        Assertions.assertEquals(1, budget.getBudgets().get("Main").getBudgetComponents().size());
    }

    @Test
    public void testAddNoExpense() {
        try {
            budget.createBudget("Test Budget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getTotalExpenses());
    }

    @Test
    public void testAddExpenseExpectNoExceptions() {
        try {
            budget.createBudget("Test Budget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.addExpense("Test Budget", "Test Item", 100.50);
        } catch (NoBudgetException | TooExpensiveException | DuplicateItemException e){
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
        try {
            budget.createBudget("Test Budget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.addExpense("Test Budget", "Test Item", 100.50);
            budget.addExpense("Test Budget", "Test Item 2", 50.55);
        } catch (NoBudgetException | TooExpensiveException | DuplicateItemException e) {
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
        try {
            budget.createBudget("Test Budget 1", 500);
            budget.createBudget("Test Budget 2", 200);
        } catch (DuplicateBudgetException e) {
            fail();
        }

        try {
            budget.addExpense("Test Budget 2", "Test Item", 150);
        } catch (NoBudgetException | TooExpensiveException | DuplicateItemException e) {
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
        try {
            budget.createBudget("Test Budget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.addExpense("Test Budget", "Test Item", 500.01);
            fail();
        } catch (NoBudgetException e) {
            fail();
        } catch (TooExpensiveException e) {

        } catch (DuplicateItemException e) {
            fail();
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
        } catch (DuplicateItemException e) {
            fail();
        }
    }

    @Test
    public void testRemoveExpense() {
        try {
            budget.createBudget("Test Budget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.addExpense("Test Budget", "Test Item", 50);
        } catch (NoBudgetException | TooExpensiveException | DuplicateItemException e) {
            fail();
        }
        Assertions.assertEquals(1, budget.getBudgets().get("Test Budget").getExpenseList().size());
        try {
            budget.removeExpense("Test Budget", "Test Item", 50);
        } catch (NoBudgetException e) {
            fail();
        } catch (NonexistentItemException e) {
            fail();
        }
        Assertions.assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
    }

    @Test
    public void testSaveBudgetsOneExpense() throws TooExpensiveException {
        try {
            budget.createBudget("TestBudget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        } catch (DuplicateItemException e) {
            fail();
        }
        try {
            budget.saveBudgets();
        } catch (IOException e) {
            fail();
        }
        BudgetLogic newBudgetLogic = new BudgetLogic();
        try {
            Assertions.assertEquals("File found.", newBudgetLogic.load("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt"));
        } catch (IOException | DuplicateItemException e) {
            fail();
        }
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
    }

    @Test
    public void testSaveBudgetsTwoExpenses() throws TooExpensiveException {
        try {
            budget.createBudget("TestBudget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
            budget.getBudgets().get("TestBudget").addExpense("TestExpense2", 100);
        } catch (DuplicateItemException e) {
            fail();
        }
        try {
            budget.saveBudgets();
        } catch (IOException e) {
            fail();
        }
        BudgetLogic newBudgetLogic = new BudgetLogic();
        try {
            Assertions.assertEquals("File found.", newBudgetLogic.load("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt"));
        } catch (IOException | DuplicateItemException e) {
            fail();
        }
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
        Assertions.assertEquals("TestExpense2", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(1).getName());
    }

    @Test
    public void testLoadOneExpenseExpectFail() throws TooExpensiveException {
        try {
            budget.createBudget("TestBudget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        try {
            budget.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        } catch (TooExpensiveException | DuplicateItemException e) {
            fail();
        }
        try {
            budget.getBudgets().get("TestBudget").saveExpenses();
        } catch (IOException e) {
            fail();
        }
        BudgetLogic newBudgetLogic = new BudgetLogic();
        try {
            newBudgetLogic.load("/Users/aidan/IdeaProjects/Personal_Project/data/missingfile.txt");
        } catch (IOException e) {

        } catch (DuplicateItemException e) {
            fail();
        }
        Assertions.assertEquals(0, newBudgetLogic.getBudgets().size());
    }
}

