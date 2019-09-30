package investments;

import java.util.Scanner;

public class Portfolio implements GeneralInvestment {
    private NonRegistered nonRegistered;
    private Tfsa tfsa;
    private Rrsp rrsp;
    private Scanner reader;

    // EFFECTS: creates a new portfolio object with an instance of each investment account
    public Portfolio(Scanner reader) {
        this.reader = reader;
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

    // EFFECTS: takes in user input for the buy investment menu.
    public void buyMenuInput() {
        System.out.println("1. Buy a new investment \n" + "2. Buy more of an existing investment");
        String command = reader.nextLine();
        buyMenu(command);
    }

    // EFFECTS: calls the appropriate buy method based on user input from buyMenuInput
    public void buyMenu(String command) {
        if (command.equals("1")) {
            buyNewInput();
        } else if (command.equals("2")) {
            buyMoreInput();
        }
    }

    // EFFECTS: takes in user input for buying more of an existing stock.
    public void buyMoreInput() {
        System.out.println("Which investment account: 1. Non-Registered, 2. TFSA, 3. RRSP: ");
        String account = reader.nextLine();
        buyMore(account);
    }

    // EFFECTS: calls the appropriate buy more method on different accounts based on user input.
    public void buyMore(String account) {
        if (account.equals("1")) {
            nonRegistered.buyMoreInput();
        } else if (account.equals("2")) {
            tfsa.buyMoreInput();
        } else if (account.equals("3")) {
            rrsp.buyMoreInput();
        }
    }

    // EFFECTS: takes in user input for buying a new investment.
    public void buyNewInput() {
        System.out.println("Which investment account: 1. Non-Registered, 2. TFSA, 3. RRSP: ");
        String account = reader.nextLine();
        System.out.print("Name of investment: ");
        String name = reader.nextLine();
        System.out.print("Value of investment: ");
        double value = Double.parseDouble(reader.nextLine());
        System.out.print("Quantity to purchase: ");
        int quantity = Integer.parseInt(reader.nextLine());
        buy(account, name, value, quantity);
        System.out.println("");
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

    // EFFECTS: takes in user input for selling an existing investment.
    public void sellInput() {
        System.out.println("Which investment account is the investment in: 1. Non-Registered, 2. TFSA, 3. RRSP: ");
        String account = reader.nextLine();
        System.out.print("Which investment would you like to sell: ");
        String investmentName = reader.nextLine();
        sell(account, investmentName);
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

    // EFFECTS: prints out all the investments in all accounts.
    public void printInvestmentSummary() {
        nonRegistered.printInvestments();
        tfsa.printInvestments();
        rrsp.printInvestments();
    }

    // EFFECTS: returns non registered account.
    public NonRegistered getNonRegistered() {
        return this.nonRegistered;
    }

    // EFFECTS: return tfsa account.
    public Tfsa getTfsa() {
        return this.tfsa;
    }

    // EFFECTS: return rrsp account.
    public Rrsp getRrsp() {
        return this.rrsp;
    }
}
