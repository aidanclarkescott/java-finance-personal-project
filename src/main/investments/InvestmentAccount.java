package investments;

import observer.Subject;

import java.util.HashMap;
import java.util.Scanner;

public abstract class InvestmentAccount extends Subject implements GeneralInvestment, InvestmentAccountBehaviour {
    protected HashMap<String, Investment> investments;

    public InvestmentAccount() {
        this.investments = new HashMap<>();
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
        notifyObservers(investments.get(investmentName).getIndividualValue() * quantity);
    }

    // REQUIRES: quantity cannot be negative
    // MODIFIES: this
    // EFFECTS: creates a new investment and places it in the account.
    public void buy(String name, double value, int quantity) {
        if (!investments.containsKey(name)) {
            this.investments.put(name, new Investment(name, value, quantity));
            notifyObservers(value * quantity);
        }
    }

    // REQUIRES: investmentName must already be an existing investment
    // MODIFIES: this
    // EFFECTS: removes/sells an investment from the account.
    public void sell(String investmentName, int quantity) {
        Investment tempInvestment = this.investments.get(investmentName);
        if ((tempInvestment.getQuantity() - quantity) > 0) {
            notifyObservers(-(investments.get(investmentName).getIndividualValue() * quantity));
            tempInvestment.sell(quantity);
        } else if ((tempInvestment.getQuantity() - quantity) <= 0) {
            notifyObservers(-(investments.get(investmentName).holdings()));
            this.investments.remove(investmentName);
        }
    }

    // EFFECTS: calculates taxes based on holdings
    public double calculateTaxes() {
        double holdings = holdings();
        if (holdings > 0 && holdings < 46605) {
            return holdings * 0.15;
        } else if (holdings > 46605 && holdings < 93208) {
            double aboveFirst = holdings - 46605;
            return (46605 * 0.15) + (aboveFirst * 0.205);
        } else if (holdings > 93208 && holdings < 144498) {
            double aboveSecond = holdings - 93208;
            return (46605 * 0.15) + (46603 * 0.205) + (aboveSecond * 0.26);
        } else if (holdings > 144498 && holdings < 205842) {
            double aboveThird = holdings - 144498;
            return (46605 * 0.15) + (46603 * 0.205) + (51290 * 0.26) + (aboveThird * 0.29);
        } else if (holdings > 205842) {
            double aboveFourth = holdings - 205842;
            return (46605 * 0.15) + (46603 * 0.205) + (61344 * 0.26) + (61344 * 0.29) + (aboveFourth * 0.33);
        }
        return 0;
    }

    // EFFECTS: returns the list of investments.
    public HashMap<String, Investment> getInvestments() {
        return this.investments;
    }
}
