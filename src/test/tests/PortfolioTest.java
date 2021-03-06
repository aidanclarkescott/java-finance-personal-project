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
        portfolio = new Portfolio();
    }

    @Test
    public void testHoldingsNewAccount() {
        Assertions.assertEquals(0, portfolio.holdings());
    }

    @Test
    public void testConstructorDuplicateObserver() throws DuplicateInvestmentException {
        InvestmentAccount rrsp = portfolio.getRrsp();
        Portfolio port2 = this.portfolio;
        rrsp.addObserver(port2);
        rrsp.buy("AAPL", 20, 1);
        Assertions.assertEquals(20, portfolio.holdings());
    }

    @Test
    public void testHoldings() throws DuplicateInvestmentException {
        portfolio.buy("1", "TestInvestment", 50, 5);
        portfolio.buy("2", "TestInvestment2", 50, 5);
        portfolio.buy("3", "TestInvestment3", 50, 5);
        Assertions.assertEquals(3 * 250, portfolio.holdings());
    }

    @Test
    public void testBuyNonRegisteredOneInvestment() throws DuplicateInvestmentException {
        portfolio.buy("1", "Test Investment", 50, 2);
        Assertions.assertEquals(50 * 2, portfolio.holdings());
        Assertions.assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyTfsaOneInvestment() throws DuplicateInvestmentException {
        portfolio.buy("2", "Test Investment", 50, 2);
        Assertions.assertEquals(50 * 2, portfolio.holdings());
        Assertions.assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyRrspOneInvestment() throws DuplicateInvestmentException {
        portfolio.buy("3", "Test Investment", 50, 2);
        Assertions.assertEquals(50 * 2, portfolio.holdings());
        Assertions.assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testBuyNonRegisteredTwoInvestments() throws DuplicateInvestmentException {
        portfolio.buy("1", "Test Investment 1", 50, 2);
        portfolio.buy("1", "Test Investment 2", 100, 1);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testBuyTfsaTwoInvestments() throws DuplicateInvestmentException {
        portfolio.buy("2", "Test Investment 1", 50, 2);
        portfolio.buy("2", "Test Investment 2", 100, 1);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(portfolio.getTfsa().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testBuyRrspTwoInvestments() throws DuplicateInvestmentException {
        portfolio.buy("3", "Test Investment 1", 50, 2);
        portfolio.buy("3", "Test Investment 2", 100, 1);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment 1"));
        Assertions.assertTrue(portfolio.getRrsp().getInvestments().containsKey("Test Investment 2"));
    }

    @Test
    public void testSellNonRegistered() throws DuplicateInvestmentException {
        portfolio.buy("1", "Test Investment", 50, 2);
        portfolio.sell("1", "Test Investment", 2);
        Assertions.assertEquals(0, portfolio.holdings());
        Assertions.assertFalse(portfolio.getNonRegistered().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellTfsa() throws DuplicateInvestmentException {
        portfolio.buy("2", "Test Investment", 50, 2);
        portfolio.sell("2", "Test Investment", 2);
        Assertions.assertEquals(0, portfolio.holdings());
        Assertions.assertFalse(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellRrsp() throws DuplicateInvestmentException {
        portfolio.buy("3", "Test Investment", 50, 2);
        portfolio.sell("3", "Test Investment", 2);
        Assertions.assertEquals(0, portfolio.holdings());
        Assertions.assertFalse(portfolio.getTfsa().getInvestments().containsKey("Test Investment"));
    }

    @Test
    public void testSellOptionFour() throws DuplicateInvestmentException {
        portfolio.buy("4", "Test Investment", 50, 2);
        portfolio.sell("4", "Test Investment", 2);
        Assertions.assertEquals(0, portfolio.holdings());
    }

    @Test
    public void testBuyMoreNonRegistered() throws DuplicateInvestmentException {
        portfolio.buy("1", "Test Investment", 50, 2);
        Assertions.assertEquals(100, portfolio.holdings());
        portfolio.buyMore("1", "Test Investment", 2);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertEquals(4, portfolio.getNonRegistered().getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyMoreTfsa() throws DuplicateInvestmentException {
        portfolio.buy("2", "Test Investment", 50, 2);
        Assertions.assertEquals(100, portfolio.holdings());
        portfolio.buyMore("2", "Test Investment", 2);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertEquals(4, portfolio.getTfsa().getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyMoreRrsp() throws DuplicateInvestmentException {
        portfolio.buy("3", "Test Investment", 50, 2);
        Assertions.assertEquals(100, portfolio.holdings());
        portfolio.buyMore("3", "Test Investment", 2);
        Assertions.assertEquals(100 + 100, portfolio.holdings());
        Assertions.assertEquals(4, portfolio.getRrsp().getInvestments().get("Test Investment").getQuantity());
    }

    @Test
    public void testBuyMoreOption4() {
        portfolio.buyMore("4", "Test Investment", 2);
        Assertions.assertEquals(0, portfolio.holdings());
    }

    @Test
    public void testCalculateTaxesWrongInput() throws DuplicateInvestmentException {
        portfolio.buy("1", "Test Investment", 50, 2);
        Assertions.assertEquals(0, portfolio.calculateTaxes("5"));
    }

    @Test
    public void testCalculateTaxesNonRegistered() throws DuplicateInvestmentException {
        portfolio.buy("1", "Test Investment", 50, 2);
        Assertions.assertEquals(portfolio.getNonRegistered().holdings() * 0.15, portfolio.calculateTaxes("1"));
    }

    @Test
    public void testCalculateTaxesTfsa() throws DuplicateInvestmentException {
        portfolio.buy("2", "Test Investment", 50, 2);
        Assertions.assertEquals(0, portfolio.calculateTaxes("2"));
    }

    @Test
    public void testCalculateTaxesRrsp() throws DuplicateInvestmentException {
        portfolio.buy("3", "Test Investment", 50, 2);
        Assertions.assertEquals(portfolio.getRrsp().holdings() * 0.15, portfolio.calculateTaxes("3"));
    }

}
