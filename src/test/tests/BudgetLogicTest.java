package tests;

import Budget.BudgetLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class BudgetLogicTest {

    private BudgetLogic budget;
    private Scanner reader;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        reader = new Scanner(System.in);
        budget = new BudgetLogic(reader);
    }

    @Test
    public void testCreateBudget() {
        budget.createBudget("Test Budget", 500);
        assertTrue(budget.getBudgets().containsKey("Test Budget"));
        assertEquals(500.0, budget.getBudgets().get("Test Budget").getBudgetCap());
        assertEquals(1, budget.getBudgets().size());
    }

    @Test
    public void testCreateTwoBudgets() {
        budget.createBudget("Test Budget 1", 500);
        budget.createBudget("Test Budget 2", 200);
        assertTrue(budget.getBudgets().containsKey("Test Budget 1"));
        assertTrue(budget.getBudgets().containsKey("Test Budget 2"));
        assertEquals(2, budget.getBudgets().size());
        assertEquals(500, budget.getBudgets().get("Test Budget 1").getBudgetCap());
    }

    @Test
    public void testAddNoExpense() {
        budget.createBudget("Test Budget", 500);
        assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
        assertEquals(0, budget.getBudgets().get("Test Budget").getTotalExpenses());
    }

    @Test
    public void testAddExpense() {
        budget.createBudget("Test Budget", 500);
        budget.addExpense("Test Budget", "Test Item", 100.50);
        assertTrue(budget.getBudgets().get("Test Budget").getExpenseList().
                get(0).getName().equals("Test Item"));
        assertEquals(100.50, budget.getBudgets().get("Test Budget").getExpenseList().
                get(0).getPrice());
        assertEquals(1, budget.getBudgets().get("Test Budget").getExpenseList().size());
        assertEquals(500 - 100.50, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenses() {
        budget.createBudget("Test Budget", 500);
        budget.addExpense("Test Budget", "Test Item", 100.50);
        budget.addExpense("Test Budget", "Test Item 2", 50.55);
        assertTrue(budget.getBudgets().get("Test Budget").getExpenseList().
                get(1).getName().equals("Test Item 2"));
        assertEquals(50.55, budget.getBudgets().get("Test Budget").getExpenseList().
                get(1).getPrice());
        assertEquals(2, budget.getBudgets().get("Test Budget").getExpenseList().size());
        assertEquals(500 - 100.50 - 50.55, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }

    @Test
    public void testAddExpenseToSecondBudget() {
        budget.createBudget("Test Budget 1", 500);
        budget.createBudget("Test Budget 2", 200);
        budget.addExpense("Test Budget 2", "Test Item", 150);
        assertTrue(budget.getBudgets().get("Test Budget 2").getExpenseList().
                get(0).getName().equals("Test Item"));
        assertEquals(150, budget.getBudgets().get("Test Budget 2").getExpenseList().get(0).getPrice());
        assertEquals(1, budget.getBudgets().get("Test Budget 2").getExpenseList().size());
        assertEquals(200 - 150, budget.getBudgets().get("Test Budget 2").getCurrentBudget());
    }

    @Test
    public void testAddExpenseOverBudget() {
        budget.createBudget("Test Budget", 500);
        budget.addExpense("Test Budget", "Test Item", 500.01);
        assertEquals(0, budget.getBudgets().get("Test Budget").getExpenseList().size());
        assertEquals(0, budget.getBudgets().get("Test Budget").getTotalExpenses());
        assertEquals(500, budget.getBudgets().get("Test Budget").getCurrentBudget());
    }
}
