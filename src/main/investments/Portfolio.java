package investments;

import java.util.Scanner;

public class Portfolio implements GeneralInvestment {
    private InvestmentAccount nonRegistered;
    private InvestmentAccount tfsa;
    private InvestmentAccount rrsp;

    // EFFECTS: creates a new portfolio object with an instance of each investment account
    public Portfolio(Scanner reader) {
        this.nonRegistered = new NonRegistered(reader);
        this.tfsa = new Tfsa(reader);
        this.rrsp = new Rrsp(reader);
    }

    // EFFECTS: returns the total value of all the investments.
    public double holdings() {
        double sum = 0;
        sum += this.nonRegistered.holdings();
        sum += this.tfsa.holdings();
        sum += this.rrsp.holdings();
        return sum;
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
    public void buy(String account, String name, double value, int quantity) {
        if (account.equals("1")) {
            this.nonRegistered.buy(name, value, quantity);
        } else if (account.equals("2")) {
            this.tfsa.buy(name, value, quantity);
        } else if (account.equals("3")) {
            this.rrsp.buy(name, value, quantity);
        }
    }

    // EFFECTS: calls the appropriate method on different accounts to sell an investment.
    public void sell(String account, String investmentName) {
        if (account.equals("1")) {
            nonRegistered.sell(investmentName);
        } else if (account.equals("2")) {
            tfsa.sell(investmentName);
        } else if (account.equals("3")) {
            rrsp.sell(investmentName);
        }
    }

    // EFFECTS: prints the investments of the given account
    public void printInvestments(String account) {
        if (account.equals("1")) {
            nonRegistered.printInvestments();
        } else if (account.equals("2")) {
            tfsa.printInvestments();
        } else if (account.equals("3")) {
            rrsp.printInvestments();
        }
    }

    // EFFECTS: prints out all the investments in all accounts.
    public void printInvestmentSummary() {
        nonRegistered.printInvestments();
        tfsa.printInvestments();
        rrsp.printInvestments();
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
