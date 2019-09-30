package Investments;

import javafx.scene.input.GestureEvent;
import sun.java2d.loops.FillRect;

import java.util.Scanner;

public class Portfolio implements GeneralInvestment {
    private NonRegistered nonRegistered;
    private TFSA tfsa;
    private RRSP rrsp;
    private Scanner reader;

    public Portfolio(Scanner reader) {
        this.reader = reader;
        this.nonRegistered = new NonRegistered(reader);
        this.tfsa = new TFSA(reader);
        this.rrsp = new RRSP(reader);

    }

    public double holdings() {
        double sum = 0;
        sum += this.nonRegistered.holdings();
        sum += this.tfsa.holdings();
        sum += this.rrsp.holdings();
        return sum;
    }

    public void buyMenuInput() {
        System.out.println("1. Buy a new investment \n" + "2. Buy more of an existing investment");
        String command = reader.nextLine();
        buyMenu(command);
    }

    public void buyMenu(String command) {
        if (command.equals("1")) {
            buyNewInput();
        } else if (command.equals("2")) {
            buyMoreInput();
        }
    }

    public void buyMoreInput() {
        System.out.println("Which investment account: 1. Non-Registered, 2. TFSA, 3. RRSP: ");
        String account = reader.nextLine();
        buyMore(account);
    }

    public void buyMore(String account) {
        if (account.equals("1")) {
            nonRegistered.buyMoreInput();
        } else if (account.equals("2")) {
            tfsa.buyMoreInput();
        } else if (account.equals("3")) {
            rrsp.buyMoreInput();
        }
    }

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

    public void buy(String account, String name, double value, int quantity) {
        if (account.equals("1")) {
            this.nonRegistered.buy(name, value, quantity);
        } else if (account.equals("2")) {
            this.tfsa.buy(name, value, quantity);
        } else if (account.equals("3")) {
            this.rrsp.buy(name, value, quantity);
        }
    }

    public void sell() {

    }

    public void printInvestmentSummary() {
        nonRegistered.printInvestments();
        tfsa.printInvestments();
        rrsp.printInvestments();
    }
}
