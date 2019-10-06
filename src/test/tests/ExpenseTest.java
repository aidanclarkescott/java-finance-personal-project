package tests;

import budget.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTest {
    private Expense expense;

    @BeforeEach
    public void setup() {
        expense = new Expense("Test Name", 100.50);
    }

    @Test
    public void testGetName() {
        Assertions.assertEquals("Test Name", expense.getName());
    }

    @Test
    public void testGetPrice() {
        Assertions.assertEquals(100.50, expense.getPrice());
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("Test Name 100.5", expense.toString());
    }
}
