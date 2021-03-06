package tests;

import investments.DuplicateInvestmentException;
import investments.Tfsa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

public class TfsaTest {
    private Tfsa tfsa;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        tfsa = new Tfsa();
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
    public void testHoldingsOneInvestment() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment", 100, 5);
        tfsa.buy("Test Investment 2", 50, 5);
        Assertions.assertEquals(100 * 5 + 50 * 5, tfsa.holdings());
    }

    @Test
    public void testBuyMore() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment", 100, 1);
        Assertions.assertEquals(1, tfsa.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(100, tfsa.holdings());
        tfsa.buyMore("Test Investment", 4);
        Assertions.assertEquals(1 + 4, tfsa.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(500, tfsa.holdings());
    }

    @Test
    public void testBuyOneInvestment() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment"));
        Assertions.assertEquals(5, tfsa.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertEquals(5, tfsa.getInvestments().get("Test Investment 1").getQuantity());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        Assertions.assertEquals(2, tfsa.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testBuyDuplicateException() {
        try {
            tfsa.buy("Test Investment 1", 100, 5);
        } catch (DuplicateInvestmentException e) {
            fail();
        }
        try {
            tfsa.buy("Test Investment 1", 100, 5);
            fail();
        } catch (DuplicateInvestmentException e) {

        }
    }

    @Test
    public void testSellOneInvestment() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment 1", 100, 5);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        tfsa.sell("Test Investment 1", 5);
        Assertions.assertEquals(0, tfsa.holdings());
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        tfsa.sell("Test Investment 1", 5);
        tfsa.sell("Test Investment 2", 2);
        Assertions.assertEquals(0, tfsa.holdings());
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment 1", 100, 5);
        tfsa.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 2"));
        tfsa.sell("Test Investment 2", 2);
        Assertions.assertEquals(100 * 5, tfsa.holdings());
        Assertions.assertTrue(tfsa.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(tfsa.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testCalculateTaxes() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment 1", 100, 5);
        Assertions.assertEquals(0, tfsa.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesLargeSum() throws DuplicateInvestmentException {
        tfsa.buy("Test Investment 1", 50000, 5);
        Assertions.assertEquals(0, tfsa.calculateTaxes());
    }
}
