package ui;

import investments.Investment;
import investments.Portfolio;
import network.StockData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class InvestmentUserInterfaceLogic implements ActionListener {
    private Scanner reader;
    private Portfolio portfolio;
    private StockData stockData;

    private JRadioButton buyNewInvestmentBtn;
    private JRadioButton buyExistingInvestmentBtn;
    private JComboBox<String> whichAccountBox;
    private JTextField investmentCodeInput;
    private JTextField quantityInput;
    private JButton buyBtn;
    private JButton sellBtn;

    private JTextArea printArea;

    private JButton holdingsBtn;
    private JButton investmentSummaryBtn;
    private JButton returnBtn;

    private JFrame investmentUserInterfaceFrame;
    private JFrame userInterfaceJFrame;

    public InvestmentUserInterfaceLogic() {
        this.reader = new Scanner(System.in);
        this.portfolio = new Portfolio();
        this.stockData = new StockData();
    }

    public InvestmentUserInterfaceLogic(JRadioButton buyNewInvestmentBtn, JRadioButton buyExistingInvestmentBtn,
                                        JComboBox<String> whichAccountBox, JTextField investmentCodeInput,
                                        JTextField quantityInput, JButton buyBtn, JButton sellBtn, JTextArea printArea,
                                        JButton holdingsBtn, JButton investmentSummaryBtn, JButton returnBtn,
                                        JFrame investmentUserInterfaceFrame, JFrame userInterfaceJFrame) {
        this.buyNewInvestmentBtn = buyNewInvestmentBtn;
        this.buyExistingInvestmentBtn = buyExistingInvestmentBtn;
        this.whichAccountBox = whichAccountBox;
        this.investmentCodeInput = investmentCodeInput;
        this.quantityInput = quantityInput;
        this.buyBtn = buyBtn;
        this.sellBtn = sellBtn;
        this.printArea = printArea;
        this.holdingsBtn = holdingsBtn;
        this.investmentSummaryBtn = investmentSummaryBtn;
        this.returnBtn = returnBtn;

        this.investmentUserInterfaceFrame = investmentUserInterfaceFrame;
        this.userInterfaceJFrame = userInterfaceJFrame;

        this.portfolio = new Portfolio();
        this.stockData = new StockData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnBtn) {
            investmentUserInterfaceFrame.setVisible(false);
            userInterfaceJFrame.setVisible(true);
        } else if (e.getSource() == holdingsBtn) {
            printArea.setText("Total Holdings: " + portfolio.holdings());
        } else if (e.getSource() == investmentSummaryBtn) {
            printInvestmentSummaryUI();
        } else if (e.getSource() == buyExistingInvestmentBtn) {
            printInvestmentSummaryUI();
        } else if (e.getSource() == buyBtn) {
            buyInvestmentUI();
        } else if (e.getSource() == sellBtn) {
            sellInvestmentUI();
        }
    }

    public void printInvestmentSummaryUI() {
        printArea.setText("");
        printArea.append("Non-Registered: \n");
        for (Investment investment : portfolio.getNonRegistered().getInvestments().values()) {
            printArea.append(investment + "\n");
        }
        printArea.append("TFSA: \n");
        for (Investment investment : portfolio.getTfsa().getInvestments().values()) {
            printArea.append(investment + "\n");
        }
        printArea.append("RRSP: \n");
        for (Investment investment : portfolio.getRrsp().getInvestments().values()) {
            printArea.append(investment + "\n");
        }
    }

    public void buyInvestmentUI() {
        String account = accountToNumConverter((String) whichAccountBox.getSelectedItem());
        String investmentCode = investmentCodeInput.getText();
        String quantity = quantityInput.getText();
        if (!investmentCode.isEmpty() && !quantity.isEmpty()) {
            if (buyNewInvestmentBtn.isSelected()) {
                buyNewInputUI(account, investmentCode, quantity);
            } else if (buyExistingInvestmentBtn.isSelected()) {
                buyExistingInputUI(account, investmentCode, quantity);
            }
        }
    }

    public void buyNewInputUI(String accountInput, String stockCode, String quantityInput) {
        try {
            double value = stockData.formatApiQuery(stockCode);
            int quantity = Integer.parseInt(quantityInput);
            portfolio.buy(accountInput, stockCode, value, quantity);
        } catch (NullPointerException e) {
            printArea.setText("That stock code doesn't exist.");
        }
    }

    public void buyExistingInputUI(String accountInput, String stockCode, String quantityInput) {
        try {
            int quantity = Integer.parseInt(quantityInput);
            portfolio.buyMore(accountInput, stockCode, quantity);
        } catch (NullPointerException e) {
            printArea.setText("You don't own that investment.");
        }
    }

    public void sellInvestmentUI() {
        try {
            String account = accountToNumConverter((String) whichAccountBox.getSelectedItem());
            String investmentName = investmentCodeInput.getText();
            int quantity = Integer.parseInt(quantityInput.getText());
            portfolio.sell(account, investmentName, quantity);
        } catch (NullPointerException e) {
            printArea.setText("You don't own that investment.");
        }
    }

    public String accountToNumConverter(String account) {
        if (account.equals("Non-Registered")) {
            return "1";
        } else if (account.equals("TFSA")) {
            return "2";
        } else if (account.equals("RRSP")) {
            return "3";
        }
        return "";
    }

    // ----NON-UI-METHODS----------------------------------------------------------------------------------------------

    // EFFECTS: takes in user input for the buy investment menu.
    public void buyMenuInput() {
        System.out.println("1. Buy a new investment \n" + "2. Buy more of an existing investment");
        String command = reader.nextLine();
        buyMenu(command);
    }

    // EFFECTS: calls the appropriate buy method based on user input from buyMenuInput
    public void buyMenu(String command) {
        if (command.equals("1")) {
            buyNewInput();
        } else if (command.equals("2")) {
            buyMoreInput();
        }
    }

    // EFFECTS: takes in and returns the users choice of investment account.
    public String investmentAccountScanner() {
        System.out.println("Which investment account: 1. Non-Registered, 2. TFSA, 3. RRSP: ");
        String account = reader.nextLine();
        return account;
    }

    // EFFECTS: takes in user input for buying more of an existing stock.
    public void buyMoreInput() {
        String account = investmentAccountScanner();
        printInvestments(account);
        System.out.println("");
        System.out.print("Which investment would you like to buy more of: ");
        String investmentName = reader.nextLine();
        System.out.print("How many more shares would you like to buy: ");
        int quantity = Integer.parseInt(reader.nextLine());
        portfolio.buyMore(account, investmentName, quantity);
        System.out.println("");
    }

    // EFFECTS: takes in user input for buying a new investment.
    public void buyNewInput() {
        String account = investmentAccountScanner();
        System.out.print("Name of investment: ");
        String name = reader.nextLine();
        double value = stockData.formatApiQuery(name);
        System.out.print("Quantity to purchase: ");
        int quantity = Integer.parseInt(reader.nextLine());
        portfolio.buy(account, name, value, quantity);
        System.out.println("");
    }

    // EFFECTS: takes in user input for selling an existing investment.
    public void sellInput() {
        String account = investmentAccountScanner();
        System.out.print("Which investment would you like to sell: ");
        String investmentName = reader.nextLine();
        System.out.print("How much would you like to sell: ");
        int quantity = Integer.parseInt(reader.nextLine());
        portfolio.sell(account, investmentName, quantity);
    }

    // EFFECTS: returns the total value of all the investments.
    public double holdings() {
        return portfolio.holdings();
    }

    // EFFECTS: calculates taxes based on holdings for a given account.
    public double calculateTaxes() {
        String account = investmentAccountScanner();
        return portfolio.calculateTaxes(account);
    }

    // EFFECTS: prints the investments of the given account
    public void printInvestments(String account) {
        if (account.equals("1")) {
            System.out.println("Non-Registered: \n");
            for (Investment investment : portfolio.getNonRegistered().getInvestments().values()) {
                System.out.println(investment);
            }
        } else if (account.equals("2")) {
            System.out.println("TFSA: \n");
            for (Investment investment : portfolio.getTfsa().getInvestments().values()) {
                System.out.println(investment);
            }
        } else if (account.equals("3")) {
            System.out.println("RRSP: \n");
            for (Investment investment : portfolio.getRrsp().getInvestments().values()) {
                System.out.println(investment);
            }
        }
    }

    // EFFECTS: prints out all the investments in all accounts.
    public void printInvestmentSummary() {
        printInvestments("1");
        System.out.println("");
        printInvestments("2");
        System.out.println("");
        printInvestments("3");
        System.out.println("");
    }

}
