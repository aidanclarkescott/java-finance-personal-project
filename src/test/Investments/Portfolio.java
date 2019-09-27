package Investments;

public class Portfolio implements GeneralInvestment {
    private NonRegistered nonRegistered;
    private TFSA tfsa;
    private RRSP rrsp;

    public Portfolio() {
        this.nonRegistered = new NonRegistered();
        this.tfsa = new TFSA();
        this.rrsp = new RRSP();
    }

    public double holdings() {
        double sum = 0;
        sum += this.nonRegistered.holdings();
        sum += this.tfsa.holdings();
        sum += this.rrsp.holdings();
        return sum;
    }

    public void buy(int amount) {

    }

    public void sell() {

    }
}
