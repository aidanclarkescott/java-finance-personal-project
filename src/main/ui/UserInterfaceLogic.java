package ui;

import budget.NoBudgetException;

import java.util.Scanner;

public class UserInterfaceLogic {
    private Scanner reader;
    private BudgetUserInterface budget;
    private InvestmentUserInterface portfolio;

    // EFFECTS: Creates a new ui object with a budget list and starts the program.
    public UserInterfaceLogic(Scanner reader) {
        this.reader = reader;
        this.budget = BudgetUserInterface.getInstance();
        this.portfolio = InvestmentUserInterface.getInstance();
        budget.load();
    }

    // EFFECTS: Prints out the main menu and option functionality.
    public void menu() {
        System.out.println("Personal Finance Application \n");
        menuOptions();
        System.out.println("Thank you for using the application.");
        budget.saveBudgets();
    }

    // EFFECTS: calls appropriate methods based on user input.
    private void menuOptions() {
        while (true) {
            String command = printOptions();
            if (command.equals("8")) {
                break;
            } else if (command.equals("1")) {
                budget.createBudgetInput();
            } else if (command.equals("2")) {
                budget.addExpense(budget.whichBudgetScanner(), budget.nameScanner(), budget.priceScanner());
            } else if (command.equals("3")) {
                budget.removeExpense(budget.whichBudgetScanner(), budget.nameScanner(), budget.priceScanner());
            } else if (command.equals("4")) {
                investmentMenu();
            } else if (command.equals("5")) {
                printExpenses();
            } else if (command.equals("6")) {
                budget.printBudgets();
            } else if (command.equals("7")) {
                budget.displayInput();
            }
        }
    }

    // EFFECTS: Prints out the investment menu and investment options/functionality.
    private void investmentMenu() {
        System.out.println("Investment Portfolio: ");
        while (true) {
            String command = printInvestmentOptions();
            if (command.equals("6")) {
                break;
            } else if (command.equals("1")) {
                System.out.println(portfolio.holdings() + "\n");
            } else if (command.equals("2")) {
                portfolio.printInvestmentSummary();
            } else if (command.equals("3")) {
                portfolio.buyMenuInput();
            } else if (command.equals("4")) {
                portfolio.sellInput();
            } else if (command.equals("5")) {
                System.out.println(portfolio.calculateTaxes());
            }
        }
    }

    // EFFECTS: prints out all the main menu options and takes in user input.
    private String printOptions() {
        System.out.println("Menu: \n 1. Create a new budget " + "\n 2. Add expense to existing budget "
                + "\n 3. Remove expense from existing budget "
                + "\n 4. View investment portfolio " + "\n 5. Print budget expenses"
                + "\n 6. Print budget list" + "\n 7. Print a budget hierarchy"
                + "\n 8. Quit \n");
        String command = reader.nextLine();
        return command;
    }

    // EFFECTS: prints out all the investment menu options and takes in user input.
    private String printInvestmentOptions() {
        System.out.println(" 1. View total holdings \n" + " 2. Print out investment summary \n"
                + " 3. Buy an investment \n" + " 4. Sell an investment \n"
                + " 5. Calculate taxes \n" + " 6. Return to main menu \n");
        String command = reader.nextLine();
        return command;
    }

    private void printExpenses() {
        String budgetName = budget.whichBudgetScanner();
        try {
            budget.printExpenses(budgetName);
        } catch (NoBudgetException e) {
            System.out.println("You haven't created a budget yet.");
        } finally {
            System.out.println("");
        }
    }

}
