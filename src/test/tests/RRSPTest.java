package tests;

import Investments.RRSP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class RRSPTest {
    private RRSP rrsp;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        rrsp = new RRSP(reader);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, rrsp.holdings());
        assertEquals(0, rrsp.getInvestments().size());
    }

    @Test
    public void testHoldingsNewAccount() {
        assertEquals(0, rrsp.holdings());
    }

    @Test
    public void testHoldingsOneInvestment() {
        rrsp.buy("Test Investment", 100, 5);
        assertEquals(100 * 5, rrsp.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() {
        rrsp.buy("Test Investment", 100, 5);
        rrsp.buy("Test Investment 2", 50, 5);
        assertEquals(100 * 5 + 50 * 5, rrsp.holdings());
    }

    @Test
    public void testBuyMore() {
        rrsp.buy("Test Investment", 100, 1);
        assertEquals(1, rrsp.getInvestments().get("Test Investment").getQuantity());
        assertEquals(100, rrsp.holdings());
        rrsp.buyMore("Test Investment", 4);
        assertEquals(1 + 4, rrsp.getInvestments().get("Test Investment").getQuantity());
        assertEquals(500, rrsp.holdings());
    }

    @Test
    public void testBuyOneInvestment() {
        rrsp.buy("Test Investment", 100, 5);
        assertEquals(100 * 5, rrsp.holdings());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment"));
        assertEquals(5, rrsp.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() {
        rrsp.buy("Test Investment 1", 100, 5);
        rrsp.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, rrsp.holdings());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        assertEquals(5, rrsp.getInvestments().get("Test Investment 1").getQuantity());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 2"));
        assertEquals(2, rrsp.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testSellOneInvestment() {
        rrsp.buy("Test Investment 1", 100, 5);
        assertEquals(100 * 5, rrsp.holdings());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        rrsp.sell("Test Investment 1");
        assertEquals(0, rrsp.holdings());
        assertFalse(rrsp.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() {
        rrsp.buy("Test Investment 1", 100, 5);
        rrsp.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, rrsp.holdings());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 2"));
        rrsp.sell("Test Investment 1");
        rrsp.sell("Test Investment 2");
        assertEquals(0, rrsp.holdings());
        assertFalse(rrsp.getInvestments().containsKey("Test Investment 1"));
        assertFalse(rrsp.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() {
        rrsp.buy("Test Investment 1", 100, 5);
        rrsp.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, rrsp.holdings());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 2"));
        rrsp.sell("Test Investment 2");
        assertEquals(100 * 5, rrsp.holdings());
        assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        assertFalse(rrsp.getInvestments().containsKey("Test Investment 2"));
    }

}