package tests;

import Budget.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    private Expense expense;

    @BeforeEach
    public void setup() {
        expense = new Expense("Test Name", 100.50);
    }

    @Test
    public void testGetName() {
        assertEquals("Test Name", expense.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(100.50, expense.getPrice());
    }

    @Test
    public void testToString() {
        assertEquals("Test Name 100.5", expense.toString());
    }
}
