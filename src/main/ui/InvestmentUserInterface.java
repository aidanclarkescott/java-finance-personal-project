package ui;

import javax.swing.*;
import java.awt.*;

public class InvestmentUserInterface {
    private JFrame frame;
    private InvestmentUserInterfaceLogic ui;

    private JLabel header;

    private JRadioButton buyNewInvestmentBtn;
    private JRadioButton buyExistingInvestmentBtn;
    private JComboBox<String> whichAccountBox;
    private JLabel investmentCodeLabel;
    private JLabel quantityLabel;
    private JTextField investmentCodeInput;
    private JTextField quantityInput;
    private JButton buyBtn;
    private JButton sellBtn;

    private JTextArea printArea;

    private JButton holdingsBtn;
    private JButton investmentSummaryBtn;
    private JButton returnBtn;

    public InvestmentUserInterface() {

    }

    public void run(JFrame userInterfaceJFrame) {
        frame = new JFrame("Personal Finance Application");
        frame.setPreferredSize(new Dimension(720, 420));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        this.ui = new InvestmentUserInterfaceLogic(buyNewInvestmentBtn, buyExistingInvestmentBtn, whichAccountBox,
                investmentCodeInput, quantityInput, buyBtn, sellBtn, printArea, holdingsBtn, investmentSummaryBtn,
                returnBtn, frame, userInterfaceJFrame);

        setActionListeners();
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        container.setLayout(new BorderLayout());
        header = new JLabel("Investment Portfolio", SwingConstants.CENTER);
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
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(createTopLeftInputs());
        panel.add(createBottomLeftInputs());
        return panel;
    }

    public JPanel createBottomLeftInputs() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        investmentCodeLabel = new JLabel("Investment Code: ");
        investmentCodeInput = new JTextField();
        quantityLabel = new JLabel("Quantity: ");
        quantityInput = new JTextField();
        buyBtn = new JButton("Buy");
        sellBtn = new JButton("Sell");
        panel.add(investmentCodeLabel);
        panel.add(investmentCodeInput);
        panel.add(quantityLabel);
        panel.add(quantityInput);
        panel.add(buyBtn);
        panel.add(sellBtn);
        return panel;
    }

    public JPanel createTopLeftInputs() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        ButtonGroup buyGroup = new ButtonGroup();
        buyNewInvestmentBtn = new JRadioButton("Buy New Investment");
        buyExistingInvestmentBtn = new JRadioButton("Buy Existing Investment");
        String[] whichAccount = {"Non-Registered", "TFSA", "RRSP"};
        whichAccountBox = new JComboBox<>(whichAccount);
        buyGroup.add(buyNewInvestmentBtn);
        buyGroup.add(buyExistingInvestmentBtn);
        buyNewInvestmentBtn.setSelected(true);
        panel.add(buyNewInvestmentBtn);
        panel.add(buyExistingInvestmentBtn);
        panel.add(whichAccountBox);
        return panel;
    }

    public JPanel createBottomButtons() {
        JPanel panel = new JPanel(new GridLayout());
        holdingsBtn = new JButton("Total Holdings");
        investmentSummaryBtn = new JButton("Print Investment Summary");
        returnBtn = new JButton("Return");
        panel.add(holdingsBtn);
        panel.add(investmentSummaryBtn);
        panel.add(returnBtn);
        return panel;
    }

    public void setActionListeners() {
        buyNewInvestmentBtn.addActionListener(ui);
        buyExistingInvestmentBtn.addActionListener(ui);
        whichAccountBox.addActionListener(ui);
        buyBtn.addActionListener(ui);
        sellBtn.addActionListener(ui);
        holdingsBtn.addActionListener(ui);
        investmentSummaryBtn.addActionListener(ui);
        returnBtn.addActionListener(ui);
    }
}
