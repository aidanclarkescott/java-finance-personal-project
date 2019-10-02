package investments;

import java.util.Scanner;

public class NonRegistered extends InvestmentAccount {

    // EFFECTS: creates a new NonRegistered object.
    public NonRegistered(Scanner reader) {
        super(reader);
    }

    // EFFECTS: prints out all the investments in the account
    @Override
    public void printInvestments() {
        System.out.println("Non-Registered: \n");
        super.printInvestments();
    }

}
