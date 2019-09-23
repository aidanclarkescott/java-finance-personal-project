package tests;

import Budget.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {
    private Budget budget;

    @BeforeEach
    public void setup() {
        budget = new Budget("Test Name", 500);
    }

    @Test
    public void testAddNoExpense() {
        assertEquals(0, budget.getExpenseList().size());
        assertEquals(0, budget.getTotalExpenses());
    }

    @Test
    public void testAddExpense() {
        budget.addExpense("Test Item", 100.50);
        assertTrue(budget.getExpenseList().get(0).getName().equals("Test Item"));
        assertEquals(100.50, budget.getExpenseList().get(0).getPrice());
        assertEquals(1, budget.getExpenseList().size());
        assertEquals(100.50, budget.getTotalExpenses());
        assertEquals(500 - 100.50, budget.getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenses() {
        budget.addExpense("Test Item 1", 50.55);
        budget.addExpense("Test Item 2", 60.22);
        assertTrue(budget.getExpenseList().get(1).getName().equals("Test Item 2"));
        assertEquals(2, budget.getExpenseList().size());
        assertEquals(50.55 + 60.22, budget.getTotalExpenses());
        assertEquals(500 - 50.55 - 60.22, budget.getCurrentBudget());
    }

    @Test
    public void testAddExpenseOverBudget() {
        budget.addExpense("Test Overpriced Item", 1000.00);
        assertEquals(0, budget.getExpenseList().size());
        assertEquals(0, budget.getTotalExpenses());
        assertEquals(500, budget.getCurrentBudget());
    }

    @Test
    public void testAddTwoExpenseOverBudget() {
        budget.addExpense("Test Item 1", 500.01);
        budget.addExpense("Test Item 1", 10000);
        assertEquals(0, budget.getExpenseList().size());
        assertEquals(0, budget.getTotalExpenses());
        assertEquals(500, budget.getCurrentBudget());
    }

}
