package investments;

import java.util.Scanner;

public class Rrsp extends InvestmentAccount {

    // EFFECTS: creates a new NonRegistered object.
    public Rrsp(Scanner reader) {
        super(reader);
    }

    // EFFECTS: prints out all the investments in the account
    @Override
    public void printInvestments() {
        System.out.println("RRSP: \n");
        super.printInvestments();
    }

}
