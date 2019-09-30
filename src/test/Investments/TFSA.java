package Investments;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.HashMap;
import java.util.Scanner;

public class TFSA implements GeneralInvestment, InvestmentAccount {
    private HashMap<String, Investment> investments;
    private Scanner reader;

    public TFSA(Scanner reader) {
        this.investments = new HashMap<String, Investment>();
        this.reader = reader;
    }

    public double holdings() {
        double sum = 0;
        for (Investment investment : this.investments.values()) {
            sum += investment.holdings();
        }
        return sum;
    }

    public void buyMoreInput() {
        printInvestments();
        System.out.print("Which investment would you like to buy more of: ");
        String investmentName = reader.nextLine();
        System.out.print("How many more shares would you like to buy: ");
        int quantity = Integer.parseInt(reader.nextLine());
        buyMore(investmentName, quantity);
    }

    public void buyMore(String investmentName, int quantity) {
        this.investments.get(investmentName).buy(quantity);
    }

    public void buy(String name, double value, int quantity) {
        this.investments.put(name, new Investment(name, value, quantity));
    }

    public void sell() {

    }

    public void printInvestments() {
        System.out.println("TFSA: \n");
        for (Investment investment : this.investments.values()) {
            System.out.println(investment);
        }
        System.out.println("");
    }
}
