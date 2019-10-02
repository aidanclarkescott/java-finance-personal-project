package investments;

import java.util.Scanner;

public class Tfsa extends InvestmentAccount {

    // EFFECTS: creates a new NonRegistered object.
    public Tfsa(Scanner reader) {
        super(reader);
    }

    // EFFECTS: prints out all the investments in the account
    @Override
    public void printInvestments() {
        System.out.println("TFSA: \n");
        super.printInvestments();
    }

}
