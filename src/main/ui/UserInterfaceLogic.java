package ui;

import budget.*;
import investments.*;

import java.io.*;
import java.util.Scanner;

public class UserInterfaceLogic {
    private Scanner reader;
    private BudgetUserInterface budget;
    private Portfolio portfolio;

    // EFFECTS: Creates a new ui object with a budget list and starts the program.
    public UserInterfaceLogic(Scanner reader) {
        this.reader = reader;
        this.budget = new BudgetUserInterface(reader);
        this.portfolio = new Portfolio(reader);
        budget.load();
    }

    // EFFECTS: Prints out the main menu and option functionality.
    public void menu() throws IOException {
        System.out.println("Personal Finance Application \n");
        menuOptions();
        System.out.println("Thank you for using the application.");
        budget.saveBudgets();
    }

    public void menuOptions() {
        while (true) {
            String command = printOptions();
            if (command.equals("6")) {
                break;
            } else if (command.equals("1")) {
                budget.createBudgetInput();
            } else if (command.equals("2")) {
                budget.addExpense(budget.whichBudgetScanner(), budget.nameScanner(), budget.priceScanner());
            } else if (command.equals("3")) {
                investmentMenu();
            } else if (command.equals("4")) {
                String budgetName = budget.whichBudgetScanner();
                budget.printExpenses(budgetName);
            } else if (command.equals("5")) {
                budget.printBudgets();
            }
        }
    }

    // EFFECTS: Prints out the investment menu and investment options/functionality.
    public void investmentMenu() {
        System.out.println("Investment Portfolio: ");
        while (true) {
            System.out.println(" 1. View total holdings \n" + " 2. Print out investment summary \n"
                    + " 3. Buy an investment \n" + " 4. Sell an investment \n"
                    + " 5. Return to main menu \n");
            String command = reader.nextLine();
            if (command.equals("5")) {
                break;
            } else if (command.equals("1")) {
                System.out.println(portfolio.holdings() + "\n");
            } else if (command.equals("2")) {
                portfolio.printInvestmentSummary();
            } else if (command.equals("3")) {
                portfolio.buyMenuInput();
            } else if (command.equals("4")) {
                portfolio.sellInput();
            }
        }
    }

    public String printOptions() {
        System.out.println("Menu: \n 1. Create a new budget " + "\n 2. Add expense to existing budget "
                + "\n 3. View investment portfolio " + "\n 4. Print budget expenses"
                + "\n 5. Print budget list" + "\n 6. Quit \n");
        String command = reader.nextLine();
        return command;
    }

}
