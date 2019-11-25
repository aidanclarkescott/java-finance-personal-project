package tests;

import budget.DuplicateBudgetException;
import investments.DuplicateInvestmentException;
import investments.NonRegistered;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Scanner;

public class NonRegisteredTest {
    private NonRegistered nonRegistered;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        nonRegistered = new NonRegistered();
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(0, nonRegistered.holdings());
        Assertions.assertEquals(0, nonRegistered.getInvestments().size());
    }

    @Test
    public void testHoldingsNewAccount() {
        Assertions.assertEquals(0, nonRegistered.holdings());
    }

    @Test
    public void testHoldingsOneInvestment() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 5);
        Assertions.assertEquals(100 * 5 + 50 * 5, nonRegistered.holdings());
    }

    @Test
    public void testBuyMore() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment", 100, 1);
        Assertions.assertEquals(1, nonRegistered.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(100, nonRegistered.holdings());
        nonRegistered.buyMore("Test Investment", 4);
        Assertions.assertEquals(1 + 4, nonRegistered.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(500, nonRegistered.holdings());
    }

    @Test
    public void testBuyOneInvestment() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment"));
        Assertions.assertEquals(5, nonRegistered.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertEquals(5, nonRegistered.getInvestments().get("Test Investment 1").getQuantity());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        Assertions.assertEquals(2, nonRegistered.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testBuyDuplicateException() {
        try {
            nonRegistered.buy("Test Investment 1", 100, 5);
        } catch (DuplicateInvestmentException e) {
            fail();
        }
        try {
            nonRegistered.buy("Test Investment 1", 100, 5);
            fail();
        } catch (DuplicateInvestmentException e) {

        }
    }

    @Test
    public void testSellOneInvestment() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 100, 5);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        nonRegistered.sell("Test Investment 1", 5);
        Assertions.assertEquals(0, nonRegistered.holdings());
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        nonRegistered.sell("Test Investment 1", 5);
        nonRegistered.sell("Test Investment 2", 2);
        Assertions.assertEquals(0, nonRegistered.holdings());
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellOneOfTwoInvestments() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        nonRegistered.sell("Test Investment 2", 2);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellSpecificQuantity() {
        try {
            nonRegistered.buy("Test Investment 1", 100, 5);
        } catch (DuplicateInvestmentException e) {
            fail();
        }
        nonRegistered.sell("Test Investment 1", 3);
        Assertions.assertEquals(2, nonRegistered.getInvestments().get("Test Investment 1").getQuantity());
        nonRegistered.sell("Test Investment 1", 2);
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellGreaterQuantityThanAvailable() {
        try {
            nonRegistered.buy("Test Investment 1", 100, 5);
        } catch (DuplicateInvestmentException e) {
            fail();
        }
        nonRegistered.sell("Test Investment 1", 3);
        Assertions.assertEquals(2, nonRegistered.getInvestments().get("Test Investment 1").getQuantity());
        nonRegistered.sell("Test Investment 1", 4);
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testCalculateTaxesNegativeHoldings() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", -1, 5);
        Assertions.assertEquals(0, nonRegistered.calculateTaxes());

    }
    @Test
    public void testCalculateTaxesUnderOne() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 1000, 5);
        Assertions.assertEquals(5000 * 0.15, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesUnderTwo() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 10000, 7);
        Assertions.assertEquals(11786.724999999999, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesUnderThree() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 10000, 10);
        Assertions.assertEquals(18310.284999999996, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesUnderFour() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 10000, 15);
        Assertions.assertEquals(31475.345, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesOverFour() throws DuplicateInvestmentException {
        nonRegistered.buy("Test Investment 1", 10000, 30);
        Assertions.assertEquals(81355.705, nonRegistered.calculateTaxes());
    }

}
