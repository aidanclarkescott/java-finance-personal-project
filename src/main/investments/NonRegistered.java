package investments;

import java.util.HashMap;
import java.util.Scanner;

public class NonRegistered implements GeneralInvestment, InvestmentAccount {
    private HashMap<String, Investment> investments;
    private Scanner reader;

    // EFFECTS: creates a new NonRegistered object.
    public NonRegistered(Scanner reader) {
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

    // EFFECTS: takes in user input for buying more of an existing investment.
    public void buyMoreInput() {
        printInvestments();
        System.out.print("Which investment would you like to buy more of: ");
        String investmentName = reader.nextLine();
        System.out.print("How many more shares would you like to buy: ");
        int quantity = Integer.parseInt(reader.nextLine());
        buyMore(investmentName, quantity);
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
        System.out.println("Non-Registered: \n");
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
