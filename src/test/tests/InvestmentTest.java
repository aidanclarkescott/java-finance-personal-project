package tests;

import Investments.Investment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvestmentTest {
    private Investment investment;

    @BeforeEach
    public void setup() {
        investment = new Investment("TestInvestment", 50, 5);
    }

    @Test
    public void testHoldings() {
        assertEquals(50 * 5, investment.holdings());
    }

    @Test
    public void testHoldingsAfterAddingQuantity() {
        investment.buy(5);
        assertEquals(50 * 10, investment.holdings());
    }

    @Test
    public void testBuy() {
        investment.buy(5);
        assertEquals(10, investment.getQuantity());
    }

    @Test
    public void testToString() {
        assertEquals("TestInvestment, Individual Value: 50.0, Your Holdings: 250.0", investment.toString());
    }

}
