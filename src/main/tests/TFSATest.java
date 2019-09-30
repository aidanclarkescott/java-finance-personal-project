package tests;

import Investments.TFSA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(0, tfsa.holdings());
        Assertions.assertEquals(0, tfsa.getInvestments().size());
    }

    @Test
    public void testHoldingsNewAccount() {
        Assertions.assertEquals(0, tfsa.holdings());
    }

    @Test
    public void testHoldingsOneInvestment() {
        tfsa.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() {
        tfsa.buy("Test Investment", 100, 5);
        tfsa.buy("Test Investment 2", 50, 5);
        Assertions.assertEquals(100 * 5 + 50 * 5, tfsa.holdings());
    }

    @Test
    public void testBuyMore() {
        tfsa.buy("Test Investment", 100, 1);
        Assertions.assertEquals(1, tfsa.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(100, tfsa.holdings());
        tfsa.buyMore("Test Investment", 4);
        Assertions.assertEquals(1 + 4, tfsa.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(500, tfsa.holdings());
    }

    @Test
    public void testBuyOneInvestment() {
        tfsa.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment"));
        Assertions.assertEquals(5, tfsa.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertEquals(5, tfsa.getInvestments().get("Test Investment 1").getQuantity());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        Assertions.assertEquals(2, tfsa.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testSellOneInvestment() {
        tfsa.buy("Test Investment 1", 100, 5);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        tfsa.sell("Test Investment 1");
        Assertions.assertEquals(0, tfsa.holdings());
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        tfsa.sell("Test Investment 1");
        tfsa.sell("Test Investment 2");
        Assertions.assertEquals(0, tfsa.holdings());
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        tfsa.sell("Test Investment 2");
        Assertions.assertEquals(100 * 5, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 2"));
    }
}
