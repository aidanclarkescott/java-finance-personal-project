package ui;

import budget.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterfaceLogic implements ActionListener {
    private Scanner reader;
    private BudgetUserInterfaceLogic budget;
    // InvestmentUserInterfaceLogic is for old implementation of program
    private InvestmentUserInterfaceLogic portfolio;
    private InvestmentUserInterface investmentUserInterface;

    private JTextField budgetNameOneInput;
    private JTextField maxPerMonthInput;
    private JButton createBudgetBtn;

    private JTextField budgetNameTwoInput;
    private JTextField itemNameInput;
    private JTextField priceInput;
    private JButton addExpenseBtn;
    private JButton removeExpenseBtn;

    private JTextField budgetNameThreeInput;
    private JButton printExpensesBtn;

    private JTextArea printArea;

    private JButton printBudgetListBtn;
    private JButton printBudgetHierarchyBtn;
    private JButton investmentMenuBtn;
    private JButton quitBtn;

    private JFrame userInterfaceJFrame;

    // EFFECTS: Creates a new ui object with a budget list and starts the program.
    public UserInterfaceLogic(Scanner reader) {
        this.reader = reader;
        this.budget = new BudgetUserInterfaceLogic();
        this.portfolio = new InvestmentUserInterfaceLogic();
        budget.load();
    }

    public UserInterfaceLogic(JTextField budgetNameOneInput, JTextField maxPerMonthInput, JButton createBudgetBtn,
                              JTextField budgetNameTwoInput, JTextField itemNameInput, JTextField priceInput,
                              JButton addExpenseBtn, JButton removeExpenseBtn, JTextArea printArea,
                              JTextField budgetNameThreeInput, JButton printExpensesBtn, JButton printBudgetListBtn,
                              JButton printBudgetHierarchyBtn, JButton investmentMenuBtn, JButton quitBtn,
                              JFrame frame) {
        this.budget = new BudgetUserInterfaceLogic();
        budget.load();
        this.budgetNameOneInput = budgetNameOneInput;
        this.maxPerMonthInput = maxPerMonthInput;
        this.createBudgetBtn = createBudgetBtn;
        this.budgetNameTwoInput = budgetNameTwoInput;
        this.itemNameInput = itemNameInput;
        this.priceInput = priceInput;
        this.addExpenseBtn = addExpenseBtn;
        this.removeExpenseBtn = removeExpenseBtn;
        this.budgetNameThreeInput = budgetNameThreeInput;
        this.printExpensesBtn = printExpensesBtn;
        this.printArea = printArea;
        this.printBudgetListBtn = printBudgetListBtn;
        this.printBudgetHierarchyBtn = printBudgetHierarchyBtn;
        this.investmentMenuBtn = investmentMenuBtn;
        this.quitBtn = quitBtn;
        this.userInterfaceJFrame = frame;
        investmentUserInterface = new InvestmentUserInterface();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == quitBtn) {
                //budget.saveBudgets();
                System.exit(0);
            } else if (e.getSource() == investmentMenuBtn) {
                userInterfaceJFrame.setVisible(false);
                investmentUserInterface.run(userInterfaceJFrame);
            } else if (e.getSource() == createBudgetBtn) {
                createBudgetUI();
            } else if (e.getSource() == addExpenseBtn) {
                addExpenseUI();
            } else if (e.getSource() == removeExpenseBtn) {
                removeExpenseUI();
            } else {
                actionPerformedContinued(e);
            }
        } catch (NumberFormatException ex) {
            printArea.setText("Those fields cannot be empty.");
        }
    }

    public void actionPerformedContinued(ActionEvent e) {
        if (e.getSource() == printExpensesBtn) {
            printExpensesUI();
        } else if (e.getSource() == printBudgetListBtn) {
            printBudgetListUI();
        } else if (e.getSource() == printBudgetHierarchyBtn) {
            System.out.println("Not implemented yet.");
        }
    }

    public void createBudgetUI() {
        String name = budgetNameOneInput.getText();
        String monthlyMax = maxPerMonthInput.getText();
        try {
            budget.createBudgetUIInput(name, monthlyMax);
            printArea.setText("Budget " + name + " created, Monthly maximum: $" + monthlyMax);
        } catch (DuplicateBudgetException e) {
            printArea.setText("You have already created that budget!");
        }
    }

    public void addExpenseUI() {
        String budgetName = budgetNameTwoInput.getText();
        String itemName = itemNameInput.getText();
        double itemPrice = Double.parseDouble(priceInput.getText());
        try {
            budget.addExpenseUI(budgetName, itemName, itemPrice);
            printArea.setText(itemName + ", $" + itemPrice + " was added to the budget " + budgetName);
        } catch (NoBudgetException e) {
            printArea.setText("You haven't created that budget yet.");
        } catch (TooExpensiveException e) {
            printArea.setText("You cannot afford that item.");
        } catch (DuplicateItemException e) {
            printArea.setText("That item is already in that budget.");
        }
    }

    public void removeExpenseUI() {
        String budgetName = budgetNameTwoInput.getText();
        String itemName = itemNameInput.getText();
        double itemPrice = Double.parseDouble(priceInput.getText());
        try {
            budget.removeExpenseUI(budgetName, itemName, itemPrice);
            printArea.setText(itemName + ", $" + itemPrice + " was removed from the budget " + budgetName);
        } catch (NoBudgetException e) {
            printArea.setText("That budget doesn't exist.");
        } catch (NonexistentItemException e) {
            printArea.setText("That item isn't in that budget.");
        }
    }

    public void printExpensesUI() {
        try {
            printArea.setText("");
            for (String expense : budget.printExpensesUI(budgetNameThreeInput.getText())) {
                printArea.append(expense + "\n");
            }
        } catch (NoBudgetException exc) {
            printArea.setText("You haven't created that budget yet.");
        }
    }

    public void printBudgetListUI() {
        ArrayList<String> budgetList = budget.formatBudgetPrinting();
        printArea.setText("");
        for (String budget : budgetList) {
            printArea.append(budget + "\n");
        }
    }


    // ----NON-UI-METHODS OR PREVIOUS IMPLEMENTATION METHODS------------------------------------------------------------

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
            } else {
                menuOptionsContinued(command);
            }
        }
    }

    private void menuOptionsContinued(String command) {
        if (command.equals("5")) {
            printExpenses();
        } else if (command.equals("6")) {
            budget.printBudgets();
        } else if (command.equals("7")) {
            budget.displayInput();
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
        }
    }

}
