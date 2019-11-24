package investments;

import java.util.Scanner;

public class Tfsa extends InvestmentAccount {

    // EFFECTS: creates a new NonRegistered object.
    public Tfsa() {
    }

    // EFFECTS: returns zero as TFSA accounts don't pay taxes.
    @Override
    public double calculateTaxes() {
        return 0;
    }


}
