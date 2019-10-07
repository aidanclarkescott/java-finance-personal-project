package tests;

import investments.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class PortfolioTest {
    private Portfolio portfolio;
    private Scanner reader;

    @BeforeEach
    public void setup() {
        reader = new Scanner(System.in);
        portfolio = new Portfolio(reader);
    }

    @Test
    public void testHoldingsNewAccount() {
        Assertions.assertEquals(0, portfolio.holdings());
    }

    @Test
    public void testHoldings() {
        portfolio.buy("1", "TestInvestment", 50, 5);
        portfolio.buy("2", "TestInvestment2", 50, 5);
        portfolio.buy("3", "TestInvestment3", 50, 5);
        Assertions.assertEquals(3 * 250, portfolio.holdings());
    }

    @Test
    public void testBuyNonRegisteredOneInvestment() {
        portfolio.buy("1", "Test Investment", 50, 2);
        Assertions.assertEquals(50 * 2, portfolio.holdings());
        Assertions.assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyTfsaOneInvestment() {
        portfolio.buy("2", "Test Investment", 50, 2);
        Assertions.assertEquals(50 * 2, portfolio.holdings());
        Assertions.assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyRrspOneInvestment() {
        portfolio.buy("3", "Test Investment", 50, 2);
        Assertions.assertEquals(50 * 2, portfolio.holdings());
        Assertions.assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyNonRegisteredTwoInvestments() {
        portfolio.buy("1", "Test Investment 1", 50, 2);
        portfolio.buy("1", "Test Investment 2", 100, 1);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testBuyTfsaTwoInvestments() {
        portfolio.buy("2", "Test Investment 1", 50, 2);
        portfolio.buy("2", "Test Investment 2", 100, 1);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testBuyRrspTwoInvestments() {
        portfolio.buy("3", "Test Investment 1", 50, 2);
        portfolio.buy("3", "Test Investment 2", 100, 1);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellNonRegistered() {
        portfolio.buy("1", "Test Investment", 50, 2);
        portfolio.sell("1", "Test Investment");
        Assertions.assertEquals(0, portfolio.holdings());
        Assertions.assertFalse(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellTfsa() {
        portfolio.buy("2", "Test Investment", 50, 2);
        portfolio.sell("2", "Test Investment");
        Assertions.assertEquals(0, portfolio.holdings());
        Assertions.assertFalse(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellRrsp() {
        portfolio.buy("3", "Test Investment", 50, 2);
        portfolio.sell("3", "Test Investment");
        Assertions.assertEquals(0, portfolio.holdings());
        Assertions.assertFalse(portfolio.getRrsp().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyMoreNonRegistered() {
        portfolio.buy("1", "Test Investment", 50, 2);
        Assertions.assertEquals(100, portfolio.holdings());
        portfolio.buyMore("1", "Test Investment", 2);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertEquals(4, portfolio.getNonRegistered().getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyMoreTfsa() {
        portfolio.buy("2", "Test Investment", 50, 2);
        Assertions.assertEquals(100, portfolio.holdings());
        portfolio.buyMore("2", "Test Investment", 2);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertEquals(4, portfolio.getTfsa().getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyMoreRrsp() {
        portfolio.buy("3", "Test Investment", 50, 2);
        Assertions.assertEquals(100, portfolio.holdings());
        portfolio.buyMore("3", "Test Investment", 2);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertEquals(4, portfolio.getRrsp().getInvestments().get("Test Investment").getQuantity());
    }
}
