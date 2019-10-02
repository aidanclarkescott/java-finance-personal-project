package ui;

import investments.Portfolio;

import java.util.Scanner;

public class InvestmentsUserInterface {
    private Scanner reader;
    private Portfolio portfolio;

    public InvestmentsUserInterface(Scanner reader) {
        this.reader = reader;
        this.portfolio = new Portfolio(reader);
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
        portfolio.buyMore(account);
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
        portfolio.buy(account, name, value, quantity);
        System.out.println("");
    }

    // EFFECTS: takes in user input for selling an existing investment.
    public void sellInput() {
        System.out.println("Which investment account is the investment in: 1. Non-Registered, 2. TFSA, 3. RRSP: ");
        String account = reader.nextLine();
        System.out.print("Which investment would you like to sell: ");
        String investmentName = reader.nextLine();
        portfolio.sell(account, investmentName);
    }

    // EFFECTS: returns the total value of all the investments.
    public double holdings() {
        return portfolio.holdings();
    }

    // EFFECTS: prints out all the investments in all accounts.
    public void printInvestmentSummary() {
        portfolio.printInvestmentSummary();
    }

}
