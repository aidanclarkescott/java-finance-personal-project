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

    private JLabel budgetNameTwoLabel = new JLabel("Budget Name: ");
    private JLabel itemNameLabel = new JLabel("Item Name: ");
    private JLabel priceLabel = new JLabel("Price: ");
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

    // MODIFIES: this
    // EFFECTS: creates and sets up a new JFrame and initializes components and UserInterfaceLogic action listener
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

    // MODIFIES: this
    // EFFECTS: creates and adds components to frame.
    public void createComponents(Container container) {
        container.setLayout(new BorderLayout());
        header = new JLabel("Personal Finance Application", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.PLAIN, 20));
        container.setBackground(new Color(204, 232,255, 216));
        container.add(header, BorderLayout.PAGE_START);
        container.add(createCenterArea(), BorderLayout.CENTER);
        container.add(createBottomButtons(), BorderLayout.PAGE_END);
        container.add(new JLabel(" "), BorderLayout.LINE_START);
        container.add(new JLabel(" "), BorderLayout.LINE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds center area to panel.
    public JPanel createCenterArea() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(createLeftInputs());
        printArea = new JTextArea();
        printArea.setEditable(false);
        printArea.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
        panel.add(printArea);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds left panel.
    public JPanel createLeftInputs() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(createLeftTopInputs());
        panel.add(createLeftMiddleInputs());
        panel.add(createLeftBottomInputs());
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds left top components to panel.
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
        panel.setBackground(new Color(204, 232,255, 216));
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds middle components to panel.
    public JPanel createLeftMiddleInputs() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        budgetNameTwoInput = new JTextField();
        itemNameInput = new JTextField();
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
        panel.setBackground(new Color(204, 232,255, 216));
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds bottom components to panel.
    public JPanel createLeftBottomInputs() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        budgetNameThreeLabel = new JLabel("Budget Name: ");
        budgetNameThreeInput = new JTextField();
        printExpensesBtn = new JButton("Print Expenses");
        panel.add(budgetNameThreeLabel);
        panel.add(budgetNameThreeInput);
        panel.add(printExpensesBtn);
        panel.setBackground(new Color(204, 232,255, 216));
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds bottom buttons to panel.
    public JPanel createBottomButtons() {
        JPanel panel = new JPanel(new GridLayout());
        printBudgetListBtn = new JButton("Print Budget List");
        investmentMenuBtn = new JButton("Investment Menu");
        quitBtn = new JButton("Quit");
        panel.add(printBudgetListBtn);
        panel.add(investmentMenuBtn);
        panel.add(quitBtn);
        panel.setBackground(new Color(204, 232,255, 216));
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: sets all buttons action listeners to the ui logic class.
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

// previous colour panel.setBackground(new Color(177, 222,255, 216));
// final colour panel.setBackground(new Color(204, 232,255, 216));
