package tests;

import investments.NonRegistered;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testHoldingsOneInvestment() {
        nonRegistered.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
    }

    @Test
    public void testHoldingsTwoInvestments() {
        nonRegistered.buy("Test Investment", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 5);
        Assertions.assertEquals(100 * 5 + 50 * 5, nonRegistered.holdings());
    }

    @Test
    public void testBuyMore() {
        nonRegistered.buy("Test Investment", 100, 1);
        Assertions.assertEquals(1, nonRegistered.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(100, nonRegistered.holdings());
        nonRegistered.buyMore("Test Investment", 4);
        Assertions.assertEquals(1 + 4, nonRegistered.getInvestments().get("Test Investment").getQuantity());
        Assertions.assertEquals(500, nonRegistered.holdings());
    }

    @Test
    public void testBuyOneInvestment() {
        nonRegistered.buy("Test Investment", 100, 5);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment"));
        Assertions.assertEquals(5, nonRegistered.getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyTwoInvestments() {
        nonRegistered.buy("Test Investment 1", 100, 5);
        nonRegistered.buy("Test Investment 2", 50, 2);
        Assertions.assertEquals(100 * 5 + 50 * 2, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        Assertions.assertEquals(5, nonRegistered.getInvestments().get("Test Investment 1").getQuantity());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 2"));
        Assertions.assertEquals(2, nonRegistered.getInvestments().get("Test Investment 2").getQuantity());
    }

    @Test
    public void testSellOneInvestment() {
        nonRegistered.buy("Test Investment 1", 100, 5);
        Assertions.assertEquals(100 * 5, nonRegistered.holdings());
        Assertions.assertTrue(nonRegistered.getInvestments().containsKey("Test Investment 1"));
        nonRegistered.sell("Test Investment 1", 5);
        Assertions.assertEquals(0, nonRegistered.holdings());
        Assertions.assertFalse(nonRegistered.getInvestments().containsKey("Test Investment 1"));
    }

    @Test
    public void testSellTwoInvestments() {
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
    public void testSellOneOfTwoInvestments() {
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
    public void testCalculateTaxesNegativeHoldings() {
        nonRegistered.buy("Test Investment 1", -1, 5);
        Assertions.assertEquals(0, nonRegistered.calculateTaxes());

    }
    @Test
    public void testCalculateTaxesUnderOne() {
        nonRegistered.buy("Test Investment 1", 1000, 5);
        Assertions.assertEquals(5000 * 0.15, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesUnderTwo() {
        nonRegistered.buy("Test Investment 1", 10000, 7);
        Assertions.assertEquals(11786.724999999999, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesUnderThree() {
        nonRegistered.buy("Test Investment 1", 10000, 10);
        Assertions.assertEquals(18310.284999999996, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesUnderFour() {
        nonRegistered.buy("Test Investment 1", 10000, 15);
        Assertions.assertEquals(31475.345, nonRegistered.calculateTaxes());
    }

    @Test
    public void testCalculateTaxesOverFour() {
        nonRegistered.buy("Test Investment 1", 10000, 30);
        Assertions.assertEquals(81355.705, nonRegistered.calculateTaxes());
    }

}
