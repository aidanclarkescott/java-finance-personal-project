package tests;

import Investments.NonRegistered;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class NonRegisteredTest {
    private NonRegistered nonRegistered;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        nonRegistered = new NonRegistered(reader);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, nonRegistered.holdings());
        assertEquals(0, nonRegistered.getInvestments().size());
    }

    @Test
    public void testHoldingsNewAccount() {
        assertEquals(0, nonRegistered.holdings());
    }

    @Test
    public void testHoldingsOneInvestment() {
        nonRegistered.buy("Test Investment", 100, 5);
        assertEquals(100 * 5, nonRegistered.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() {
        nonRegistered.buy("Test Investment", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 5);
        assertEquals(100 * 5 + 50 * 5, nonRegistered.holdings());
    }

    @Test
    public void testBuyMore() {
        nonRegistered.buy("Test Investment", 100, 1);
        assertEquals(1, nonRegistered.getInvestments().get("Test Investment").getQuantity());
        assertEquals(100, nonRegistered.holdings());
        nonRegistered.buyMore("Test Investment", 4);
        assertEquals(1 + 4, nonRegistered.getInvestments().get("Test Investment").getQuantity());
        assertEquals(500, nonRegistered.holdings());
    }

    @Test
    public void testBuyOneInvestment() {
        nonRegistered.buy("Test Investment", 100, 5);
        assertEquals(100 * 5, nonRegistered.holdings());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment"));
        assertEquals(5, nonRegistered.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        assertEquals(5, nonRegistered.getInvestments().get("Test Investment 1").getQuantity());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        assertEquals(2, nonRegistered.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testSellOneInvestment() {
        nonRegistered.buy("Test Investment 1", 100, 5);
        assertEquals(100 * 5, nonRegistered.holdings());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        nonRegistered.sell("Test Investment 1");
        assertEquals(0, nonRegistered.holdings());
        assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        nonRegistered.sell("Test Investment 1");
        nonRegistered.sell("Test Investment 2");
        assertEquals(0, nonRegistered.holdings());
        assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        nonRegistered.sell("Test Investment 2");
        assertEquals(100 * 5, nonRegistered.holdings());
        assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 2"));
    }

}
