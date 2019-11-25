package tests;

import investments.Investment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvestmentTest {
    private Investment investment;

    @BeforeEach
    public void setup() {
        investment = new Investment("TestInvestment", 50, 5);
    }

    @Test
    public void testHoldings() {
        Assertions.assertEquals(50 * 5, investment.holdings());
    }

    @Test
    public void testHoldingsAfterAddingQuantity() {
        investment.buy(5);
        Assertions.assertEquals(50 * 10, investment.holdings());
    }

    @Test
    public void testBuy() {
        investment.buy(5);
        Assertions.assertEquals(10, investment.getQuantity());
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("TestInvestment, Individual Value: 50.0, Your Holdings: 250.0", investment.toString());
    }

    @Test
    public void testSell() {
        investment.sell(2);
        Assertions.assertEquals(3, investment.getQuantity());
    }

}
