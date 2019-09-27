package Investments;

import java.util.HashMap;

public class TFSA implements GeneralInvestment, InvestmentAccount {
    private HashMap<String, Investment> investments;

    public TFSA() {
        this.investments = new HashMap<String, Investment>();
    }

    public double holdings() {
        double sum = 0;
        for (Investment investment : this.investments.values()) {
            sum += investment.holdings();
        }
        return sum;
    }

    public void buy(int amount) {

    }

    public void sell() {

    }

    public void transfer(double amount, String destinationAccount) {

    }
}
