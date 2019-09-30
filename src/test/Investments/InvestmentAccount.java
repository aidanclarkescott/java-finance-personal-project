package Investments;

import java.util.Scanner;

public interface InvestmentAccount {
    public void printInvestments();
    public void buyMoreInput();
    public void buyMore(String investmentName, int quantity);
    public void buy(String name, double value, int quantity);
}
