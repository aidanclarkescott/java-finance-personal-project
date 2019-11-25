package investments;

import java.util.HashMap;

public interface InvestmentAccountBehaviour {

    void buyMore(String investmentName, int quantity);

    void buy(String name, double value, int quantity) throws DuplicateInvestmentException;

    void sell(String investmentName, int quantity);

    HashMap<String, Investment> getInvestments();

    double calculateTaxes();
}
