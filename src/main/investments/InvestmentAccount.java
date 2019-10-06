package investments;

import java.util.HashMap;
import java.util.Scanner;

public abstract class InvestmentAccount implements GeneralInvestment, InvestmentAccountBehaviour {
    protected HashMap<String, Investment> investments;
    protected Scanner reader;

    public InvestmentAccount(Scanner reader) {
        this.investments = new HashMap<>();
        this.reader = reader;
    }

    // EFFECTS: returns the total value of all the investments in the account.
    public double holdings() {
        double sum = 0;
        for (Investment investment : this.investments.values()) {
            sum += investment.holdings();
        }
        return sum;
    }

    // REQUIRES: quantity cannot be negative, investmentName must already be an existing investment
    // MODIFIES: this
    // EFFECTS: increases the number of shares of a given investment by a given quantity.
    public void buyMore(String investmentName, int quantity) {
        this.investments.get(investmentName).buy(quantity);
    }

    // REQUIRES: quantity cannot be negative
    // MODIFIES: this
    // EFFECTS: creates a new investment and places it in the account.
    public void buy(String name, double value, int quantity) {
        this.investments.put(name, new Investment(name, value, quantity));
    }

    // REQUIRES: investmentName must already be an existing investment
    // MODIFIES: this
    // EFFECTS: removes/sells an investment from the account.
    public void sell(String investmentName) {
        this.investments.remove(investmentName);
    }

    // EFFECTS: prints out all the investments in the account
    public void printInvestments() {
        for (Investment investment : this.investments.values()) {
            System.out.println(investment);
        }
        System.out.println("");
    }

    // EFFECTS: returns the list of investments.
    public HashMap<String, Investment> getInvestments() {
        return this.investments;
    }
}
