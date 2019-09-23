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
    public void testAddExpense() {
        budget.addExpense("Test Item", 100.50);
        assertTrue(budget.getExpenseList().get(0).getName().equals("Test Item"));
        assertEquals(100.50, budget.getExpenseList().get(0).getPrice());
        assertEquals(1, budget.getExpenseList().size());
    }

}
