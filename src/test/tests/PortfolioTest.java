package tests;

import Investments.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(0, portfolio.holdings());
    }

    @Test
    public void testHoldings() {
        portfolio.buy("1", "TestInvestment", 50, 5);
        portfolio.buy("2", "TestInvestment2", 50, 5);
        portfolio.buy("3", "TestInvestment3", 50, 5);
        assertEquals(3 * 250, portfolio.holdings());
    }

    @Test
    public void testBuyNonRegisteredOneInvestment() {
        portfolio.buy("1", "Test Investment", 50, 2);
        assertEquals(50 * 2, portfolio.holdings());
        assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyTFSAOneInvestment() {
        portfolio.buy("2", "Test Investment", 50, 2);
        assertEquals(50 * 2, portfolio.holdings());
        assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyRRSPOneInvestment() {
        portfolio.buy("3", "Test Investment", 50, 2);
        assertEquals(50 * 2, portfolio.holdings());
        assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyNonRegisteredTwoInvestments() {
        portfolio.buy("1", "Test Investment 1", 50, 2);
        portfolio.buy("1", "Test Investment 2", 100, 1);
        assertEquals(100 + 100, portfolio.holdings());
        assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment 1"));
        assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testBuyTFSATwoInvestments() {
        portfolio.buy("2", "Test Investment 1", 50, 2);
        portfolio.buy("2", "Test Investment 2", 100, 1);
        assertEquals(100 + 100, portfolio.holdings());
        assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment 1"));
        assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testBuyRRSPTwoInvestments() {
        portfolio.buy("3", "Test Investment 1", 50, 2);
        portfolio.buy("3", "Test Investment 2", 100, 1);
        assertEquals(100 + 100, portfolio.holdings());
        assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment 1"));
        assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellNonRegistered() {
        portfolio.buy("1", "Test Investment", 50, 2);
        portfolio.sell("1", "Test Investment");
        assertEquals(0, portfolio.holdings());
        assertFalse(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellTFSA() {
        portfolio.buy("2", "Test Investment", 50, 2);
        portfolio.sell("2", "Test Investment");
        assertEquals(0, portfolio.holdings());
        assertFalse(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellRRSP() {
        portfolio.buy("3", "Test Investment", 50, 2);
        portfolio.sell("3", "Test Investment");
        assertEquals(0, portfolio.holdings());
        assertFalse(portfolio.getRrsp().getInvestments().containsKey("Test Investment"));
    }
}
