package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.UserInterfaceLogic;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UITest {
    private UserInterfaceLogic userInterface;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        userInterface = new UserInterfaceLogic(reader);
    }

    @Test
    public void testCreateBudget() {
        userInterface.createBudget("Test Budget", 500);
        assertTrue(userInterface.getBudgets().containsKey("Test Budget"));
        assertEquals(500.0, userInterface.getBudgets().get("Test Budget").getBudgetCap());
        assertEquals(1, userInterface.getBudgets().size());
    }

    @Test
    public void testAddExpense() {
        userInterface.createBudget("Test Budget", 500);
        userInterface.addExpense("Test Budget", "Test Item", 100.50);
        assertTrue(userInterface.getBudgets().get("Test Budget").getExpenseList().
                get(0).getName().equals("Test Item"));
        assertEquals(100.50, userInterface.getBudgets().get("Test Budget").getExpenseList().
                get(0).getPrice());
        assertEquals(1, userInterface.getBudgets().get("Test Budget").getExpenseList().size());
    }

    @Test
    public void testAddTwoExpenses() {
        userInterface.createBudget("Test Budget", 500);
        userInterface.addExpense("Test Budget", "Test Item", 100.50);
        userInterface.addExpense("Test Budget", "Test Item 2", 50.55);
        assertTrue(userInterface.getBudgets().get("Test Budget").getExpenseList().
                get(1).getName().equals("Test Item 2"));
        assertEquals(50.55, userInterface.getBudgets().get("Test Budget").getExpenseList().
                get(1).getPrice());
        assertEquals(2, userInterface.getBudgets().get("Test Budget").getExpenseList().size());
    }
}