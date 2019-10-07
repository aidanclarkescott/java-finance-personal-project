package investments;

import java.util.HashMap;

public interface InvestmentAccountBehaviour {

    void buyMore(String investmentName, int quantity);

    void buy(String name, double value, int quantity);

    void sell(String investmentName);

    HashMap<String, Investment> getInvestments();
}
