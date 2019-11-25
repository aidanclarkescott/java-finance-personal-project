package tests;

import budget.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

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

    @Test
    public void testEqualsDifferentClass() {
        Budget budget = new Budget("Test", 50);
        Assertions.assertFalse(expense.equals(budget));
    }

    @Test
    public void testEqualsNull() {
        Expense expenseOne = null;
        Assertions.assertFalse(expense.equals(expenseOne));
    }

    @Test
    public void testEquals() {
        Expense expenseOne = new Expense("Test Name", 100.50);
        Assertions.assertTrue(expense.equals(expenseOne));
    }

    @Test
    public void testEqualsDifferentPrice() {
        Expense expenseOne = new Expense("Test Name", 50);
        Assertions.assertTrue(expense.equals(expenseOne));
    }

    @Test
    public void testEqualsDifferentNameSamePrice() {
        Expense expenseOne = new Expense("Test", 100.50);
        Assertions.assertFalse(expense.equals(expenseOne));
    }

    @Test
    public void testHashCode() {
        Assertions.assertEquals(Objects.hash("Test Name"), expense.hashCode());
    }

    @Test
    public void testHasBudget() {
        Budget budget = new Budget("Test", 500);
        expense.setBudget(budget);
        Assertions.assertTrue(expense.hasBudget());
    }

    @Test
    public void testHasBudgetNull() {
        Assertions.assertFalse(expense.hasBudget());
    }
}
