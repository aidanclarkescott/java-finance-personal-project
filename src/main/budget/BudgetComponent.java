package budget;

import ui.BudgetComponentUI;

public abstract class BudgetComponent {
    protected String name;
    protected BudgetComponentUI budgetComponentUI = new BudgetComponentUI();

    public BudgetComponent(String name) {
        this.name = name;
    }

    public abstract void display(int indent);

    // EFFECTS: prints indent
    protected void printIndent(int indent) {
        budgetComponentUI.printIndent(indent);
    }
}
