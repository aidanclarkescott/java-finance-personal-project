package tests;

import investments.DuplicateInvestmentException;
import investments.Rrsp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

public class RrspTest {
    private Rrsp rrsp;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        rrsp = new Rrsp();
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(0, rrsp.holdings());
        Assertions.assertEquals(0, rrsp.getInvestments().size());
    }

    @Test
    public void testHoldingsNewAccount() {
        Assertions.assertEquals(0, rrsp.holdings());
    }

    @Test
    public void testHoldingsOneInvestment() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, rrsp.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment", 100, 5);
        rrsp.buy("Test Investment 2", 50, 5);
        Assertions.assertEquals(100 * 5 + 50 * 5, rrsp.holdings());
    }

    @Test
    public void testBuyMore() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment", 100, 1);
        Assertions.assertEquals(1, rrsp.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(100, rrsp.holdings());
        rrsp.buyMore("Test Investment", 4);
        Assertions.assertEquals(1 + 4, rrsp.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(500, rrsp.holdings());
    }

    @Test
    public void testBuyOneInvestment() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, rrsp.holdings());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment"));
        Assertions.assertEquals(5, rrsp.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment 1", 100, 5);
        rrsp.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, rrsp.holdings());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertEquals(5, rrsp.getInvestments().get("Test Investment 1").getQuantity());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 2"));
        Assertions.assertEquals(2, rrsp.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testBuyDuplicateException() {
        try {
            rrsp.buy("Test Investment 1", 100, 5);
        } catch (DuplicateInvestmentException e) {
            fail();
        }
        try {
            rrsp.buy("Test Investment 1", 100, 5);
            fail();
        } catch (DuplicateInvestmentException e) {

        }
    }

    @Test
    public void testSellOneInvestment() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment 1", 100, 5);
        Assertions.assertEquals(100 * 5, rrsp.holdings());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        rrsp.sell("Test Investment 1", 5);
        Assertions.assertEquals(0, rrsp.holdings());
        Assertions.assertFalse(rrsp.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment 1", 100, 5);
        rrsp.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, rrsp.holdings());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 2"));
        rrsp.sell("Test Investment 1", 5);
        rrsp.sell("Test Investment 2", 2);
        Assertions.assertEquals(0, rrsp.holdings());
        Assertions.assertFalse(rrsp.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(rrsp.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() throws DuplicateInvestmentException {
        rrsp.buy("Test Investment 1", 100, 5);
        rrsp.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, rrsp.holdings());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 2"));
        rrsp.sell("Test Investment 2", 2);
        Assertions.assertEquals(100 * 5, rrsp.holdings());
        Assertions.assertTrue(rrsp.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(rrsp.getInvestments().containsKey("Test Investment 2"));
    }

}