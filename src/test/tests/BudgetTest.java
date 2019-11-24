package tests;

import budget.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import budget.TooExpensiveException;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;


public class BudgetTest {
    private Budget budget;
    private BudgetLogic budgetLogic;

    @BeforeEach
    public void setup() {
        budget = new Budget("Test Name", 500);
        budgetLogic = new BudgetLogic();
    }

    @Test
    public void testGetName() {
        Assertions.assertEquals("Test Name", budget.getName());
    }

    @Test
    public void testGetBudgetComponents() throws TooExpensiveException, DuplicateItemException {
        budget.addExpense("Test", 50);
        Assertions.assertEquals(1, budget.getBudgetComponents().size());
    }

    @Test
    public void testAddNoExpense() {
        Assertions.assertEquals(0, budget.getExpenseList().size());
        Assertions.assertEquals(0, budget.getTotalExpenses());
    }

    @Test
    public void testAddExpenseExpectNoExceptions() {
        try {
            budget.addExpense("Test Item", 100.50);
        } catch (TooExpensiveException | DuplicateItemException e) {
            fail();
        }
        Assertions.assertTrue(budget.getExpenseList().get(0).getName().equals("Test Item"));
        Assertions.assertEquals(100.50, budget.getExpenseList().get(0).getPrice());
        Assertions.assertEquals(1, budget.getExpenseList().size());
        Assertions.assertEquals(100.50, budget.getTotalExpenses());
        Assertions.assertEquals(500 - 100.50, budget.getCurrentBudget());
    }

    @Test
    public void testAddTwoExpensesExpectNoExceptions() {
        try {
            budget.addExpense("Test Item 1", 50.55);
            budget.addExpense("Test Item 2", 60.22);
        } catch (TooExpensiveException | DuplicateItemException e) {
            fail();
        }
        Assertions.assertTrue(budget.getExpenseList().get(1).getName().equals("Test Item 2"));
        Assertions.assertEquals(2, budget.getExpenseList().size());
        Assertions.assertEquals(50.55 + 60.22, budget.getTotalExpenses());
        Assertions.assertEquals(500 - 50.55 - 60.22, budget.getCurrentBudget());
    }

    @Test
    public void testAddExpenseOverBudgetExpectTooExpensiveException() {
        try {
            budget.addExpense("Test Overpriced Item", 1000.00);
            fail();
        } catch (TooExpensiveException e) {

        } catch (DuplicateItemException e) {
            fail();
        }
        Assertions.assertEquals(0, budget.getExpenseList().size());
        Assertions.assertEquals(0, budget.getTotalExpenses());
        Assertions.assertEquals(500, budget.getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenseOverBudgetExpectTooExpensiveException() {
        try {
            budget.addExpense("Test Item 1", 200);
            budget.addExpense("Test Item 1", 10000);
            fail();
        } catch (TooExpensiveException e) {

        } catch (DuplicateItemException e) {
            fail();
        }
        Assertions.assertEquals(1, budget.getExpenseList().size());
        Assertions.assertEquals(200, budget.getTotalExpenses());
        Assertions.assertEquals(300, budget.getCurrentBudget());
    }

    @Test
    public void addExpenseDuplicate() {
        try {
            budget.addExpense("Test Item", 50);
        } catch (TooExpensiveException | DuplicateItemException e) {
            fail();
        }
        Assertions.assertEquals(1, budget.getExpenseList().size());
        try {
            budget.addExpense("Test Item", 50);
        } catch (TooExpensiveException e) {
            fail();
        } catch (DuplicateItemException e) {

        }
        Assertions.assertEquals(1, budget.getExpenseList().size());
    }

    @Test
    public void testAddExpenseSimple() {
        Expense expense = new Expense("Test", 50);
        budget.addExpenseSimple(expense);
        Assertions.assertTrue(budget.getExpenseList().contains(expense));
        Assertions.assertEquals(1, budget.getExpenseList().size());
    }

    @Test
    public void testAddExpenseSimpleDuplicateExpense() {
        Expense expense = new Expense("Test", 50);
        budget.addExpenseSimple(expense);
        budget.addExpenseSimple(expense);
        Assertions.assertTrue(budget.getExpenseList().contains(expense));
        Assertions.assertEquals(1, budget.getExpenseList().size());
    }

    @Test
    public void testRemoveExpense() {
        Expense expense = new Expense("Test", 50);
        budget.addExpenseSimple(expense);
        Assertions.assertEquals(1, budget.getExpenseList().size());
        try {
            budget.removeExpense(expense);
        } catch (NonexistentItemException e) {
            fail();
        }
        Assertions.assertEquals(0, budget.getExpenseList().size());
    }

    @Test
    public void testSaveExpensesOneExpense() throws IOException, TooExpensiveException, DuplicateItemException {
        try {
            budgetLogic.createBudget("TestBudget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        budgetLogic.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        budgetLogic.getBudgets().get("TestBudget").saveExpenses();
        BudgetLogic newBudgetLogic = new BudgetLogic();
        newBudgetLogic.load("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt");
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
    }

    @Test
    public void testSaveExpensesTwoExpenses() throws IOException, TooExpensiveException, DuplicateItemException {
        try {
            budgetLogic.createBudget("TestBudget", 500);
        } catch (DuplicateBudgetException e) {
            fail();
        }
        budgetLogic.getBudgets().get("TestBudget").addExpense("TestExpense1", 50);
        budgetLogic.getBudgets().get("TestBudget").addExpense("TestExpense2", 100);
        budgetLogic.getBudgets().get("TestBudget").saveExpenses();
        BudgetLogic newBudgetLogic = new BudgetLogic();
        newBudgetLogic.load("/Users/aidan/IdeaProjects/Personal_Project/data/savefile.txt");
        Assertions.assertTrue(newBudgetLogic.getBudgets().containsKey("TestBudget"));
        Assertions.assertEquals("TestExpense1", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(0).getName());
        Assertions.assertEquals("TestExpense2", newBudgetLogic.getBudgets().get("TestBudget")
                .getExpenseList().get(1).getName());
    }
}
