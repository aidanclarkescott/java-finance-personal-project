package ui;

import javax.swing.*;
import java.awt.*;

public class UserInterface implements Runnable {
    private JFrame frame;
    private UserInterfaceLogic ui;

    private JLabel header;

    private JLabel budgetNameOneLabel;
    private JLabel maxPerMonthLabel;
    private JTextField budgetNameOneInput;
    private JTextField maxPerMonthInput;
    private JButton createBudgetBtn;

    private JLabel budgetNameTwoLabel;
    private JLabel itemNameLabel;
    private JLabel priceLabel;
    private JTextField budgetNameTwoInput;
    private JTextField itemNameInput;
    private JTextField priceInput;
    private JButton addExpenseBtn;
    private JButton removeExpenseBtn;

    private JLabel budgetNameThreeLabel;
    private JTextField budgetNameThreeInput;
    private JButton printExpensesBtn;

    private JTextArea printArea;

    private JButton printBudgetListBtn;
    private JButton investmentMenuBtn;
    private JButton quitBtn;

    public UserInterface() {

    }

    @Override
    public void run() {
        frame = new JFrame("Personal Finance Application");
        frame.setPreferredSize(new Dimension(720, 420));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        this.ui = new UserInterfaceLogic(budgetNameOneInput, maxPerMonthInput, createBudgetBtn,
                budgetNameTwoInput, itemNameInput, priceInput, addExpenseBtn, removeExpenseBtn,
                printArea, budgetNameThreeInput, printExpensesBtn, printBudgetListBtn, investmentMenuBtn,
                quitBtn, frame);

        setActionListeners();
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        container.setLayout(new BorderLayout());
        header = new JLabel("Personal Finance Application", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.PLAIN, 20));
        container.add(header, BorderLayout.PAGE_START);
        container.add(createCenterArea(), BorderLayout.CENTER);
        container.add(createBottomButtons(), BorderLayout.PAGE_END);
    }

    public JPanel createCenterArea() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(createLeftInputs());
        printArea = new JTextArea();
        printArea.setEditable(false);
        printArea.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
        panel.add(printArea);
        return panel;
    }

    public JPanel createLeftInputs() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(createLeftTopInputs());
        panel.add(createLeftMiddleInputs());
        panel.add(createLeftBottomInputs());
        return panel;
    }

    public JPanel createLeftTopInputs() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        budgetNameOneLabel = new JLabel("Budget Name: ");
        budgetNameOneInput = new JTextField();
        maxPerMonthLabel = new JLabel("Max Per Month: ");
        maxPerMonthInput = new JTextField();
        createBudgetBtn = new JButton("Create Budget");
        panel.add(budgetNameOneLabel);
        panel.add(budgetNameOneInput);
        panel.add(maxPerMonthLabel);
        panel.add(maxPerMonthInput);
        panel.add(createBudgetBtn);
        panel.add(new JLabel(""));
        return panel;
    }

    public JPanel createLeftMiddleInputs() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        budgetNameTwoLabel = new JLabel("Budget Name: ");
        budgetNameTwoInput = new JTextField();
        itemNameLabel = new JLabel("Item Name: ");
        itemNameInput = new JTextField();
        priceLabel = new JLabel("Price: ");
        priceInput = new JTextField();
        addExpenseBtn = new JButton("Add Expense");
        removeExpenseBtn = new JButton("Remove Expense");
        panel.add(budgetNameTwoLabel);
        panel.add(budgetNameTwoInput);
        panel.add(itemNameLabel);
        panel.add(itemNameInput);
        panel.add(priceLabel);
        panel.add(priceInput);
        panel.add(addExpenseBtn);
        panel.add(removeExpenseBtn);
        return panel;
    }

    public JPanel createLeftBottomInputs() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        budgetNameThreeLabel = new JLabel("Budget Name: ");
        budgetNameThreeInput = new JTextField();
        printExpensesBtn = new JButton("Print Expenses");
        panel.add(budgetNameThreeLabel);
        panel.add(budgetNameThreeInput);
        panel.add(printExpensesBtn);
        return panel;
    }

    public JPanel createBottomButtons() {
        JPanel panel = new JPanel(new GridLayout());
        printBudgetListBtn = new JButton("Print Budget List");
        investmentMenuBtn = new JButton("Investment Menu");
        quitBtn = new JButton("Quit");
        panel.add(printBudgetListBtn);
        panel.add(investmentMenuBtn);
        panel.add(quitBtn);
        return panel;
    }

    public void setActionListeners() {
        createBudgetBtn.addActionListener(ui);
        addExpenseBtn.addActionListener(ui);
        removeExpenseBtn.addActionListener(ui);
        printExpensesBtn.addActionListener(ui);
        printBudgetListBtn.addActionListener(ui);
        investmentMenuBtn.addActionListener(ui);
        quitBtn.addActionListener(ui);
    }

}

// panel.setBackground(new Color(177, 222,255, 216));
