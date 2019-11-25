package investments;

import observer.Observer;

import java.util.Scanner;

public class Portfolio implements GeneralInvestment, Observer {
    private InvestmentAccount nonRegistered;
    private InvestmentAccount tfsa;
    private InvestmentAccount rrsp;
    private double totalHoldings = 0;

    // EFFECTS: creates a new portfolio object with an instance of each investment account
    public Portfolio() {
        this.nonRegistered = new NonRegistered();
        this.tfsa = new Tfsa();
        this.rrsp = new Rrsp();
        this.nonRegistered.addObserver(this);
        this.tfsa.addObserver(this);
        this.rrsp.addObserver(this);
    }

    @Override
    public void update(double value) {
        totalHoldings += value;
    }

    // EFFECTS: returns the total value of all the investments.
    public double holdings() {
        return Math.round((totalHoldings * 100.0) / 100.0);
    }

    // EFFECTS: calls the appropriate buy more method on different accounts based on user input.
    public void buyMore(String account, String investmentName, int quantity) {
        if (account.equals("1")) {
            nonRegistered.buyMore(investmentName, quantity);
        } else if (account.equals("2")) {
            tfsa.buyMore(investmentName, quantity);
        } else if (account.equals("3")) {
            rrsp.buyMore(investmentName, quantity);
        }
    }

    // EFFECTS: calls the appropriate method to buy new stocks in different accounts.
    public void buy(String account, String name, double value, int quantity) throws DuplicateInvestmentException {
        if (account.equals("1")) {
            this.nonRegistered.buy(name, value, quantity);
        } else if (account.equals("2")) {
            this.tfsa.buy(name, value, quantity);
        } else if (account.equals("3")) {
            this.rrsp.buy(name, value, quantity);
        }
    }

    // EFFECTS: calls the appropriate method on different accounts to sell an investment.
    public void sell(String account, String investmentName, int quantity) {
        if (account.equals("1")) {
            nonRegistered.sell(investmentName, quantity);
        } else if (account.equals("2")) {
            tfsa.sell(investmentName, quantity);
        } else if (account.equals("3")) {
            rrsp.sell(investmentName, quantity);
        }
    }

    // EFFECTS: calculates the taxes for a given account.
    public double calculateTaxes(String account) {
        if (account.equals("1")) {
            return nonRegistered.calculateTaxes();
        } else if (account.equals("2")) {
            return tfsa.calculateTaxes();
        } else if (account.equals("3")) {
            return rrsp.calculateTaxes();
        }
        return 0;
    }

    // EFFECTS: returns non registered account.
    public InvestmentAccount getNonRegistered() {
        return this.nonRegistered;
    }

    // EFFECTS: return tfsa account.
    public InvestmentAccount getTfsa() {
        return this.tfsa;
    }

    // EFFECTS: return rrsp account.
    public InvestmentAccount getRrsp() {
        return this.rrsp;
    }

}
