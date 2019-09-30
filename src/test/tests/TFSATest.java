package tests;

import Investments.TFSA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

public class TFSATest {
    private TFSA tfsa;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        tfsa = new TFSA(reader);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, tfsa.holdings());
        assertEquals(0, tfsa.getInvestments().size());
    }

    @Test
    public void testHoldingsNewAccount() {
        assertEquals(0, tfsa.holdings());
    }

    @Test
    public void testHoldingsOneInvestment() {
        tfsa.buy("Test Investment", 100, 5);
        assertEquals(100 * 5, tfsa.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() {
        tfsa.buy("Test Investment", 100, 5);
        tfsa.buy("Test Investment 2", 50, 5);
        assertEquals(100 * 5 + 50 * 5, tfsa.holdings());
    }

    @Test
    public void testBuyMore() {
        tfsa.buy("Test Investment", 100, 1);
        assertEquals(1, tfsa.getInvestments().get("Test Investment").getQuantity());
        assertEquals(100, tfsa.holdings());
        tfsa.buyMore("Test Investment", 4);
        assertEquals(1 + 4, tfsa.getInvestments().get("Test Investment").getQuantity());
        assertEquals(500, tfsa.holdings());
    }

    @Test
    public void testBuyOneInvestment() {
        tfsa.buy("Test Investment", 100, 5);
        assertEquals(100 * 5, tfsa.holdings());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment"));
        assertEquals(5, tfsa.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        assertEquals(5, tfsa.getInvestments().get("Test Investment 1").getQuantity());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        assertEquals(2, tfsa.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testSellOneInvestment() {
        tfsa.buy("Test Investment 1", 100, 5);
        assertEquals(100 * 5, tfsa.holdings());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        tfsa.sell("Test Investment 1");
        assertEquals(0, tfsa.holdings());
        assertFalse(tfsa.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        tfsa.sell("Test Investment 1");
        tfsa.sell("Test Investment 2");
        assertEquals(0, tfsa.holdings());
        assertFalse(tfsa.getInvestments().containsKey("Test Investment 1"));
        assertFalse(tfsa.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        tfsa.sell("Test Investment 2");
        assertEquals(100 * 5, tfsa.holdings());
        assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        assertFalse(tfsa.getInvestments().containsKey("Test Investment 2"));
    }
}
