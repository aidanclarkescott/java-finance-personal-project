package ui;

public class BudgetComponentUI {
    private static final String INDENT = "   ";

    public void printIndent(int indent) {
        String indentation = "";

        for (int i  = 0; i < indent; i++) {
            indentation += INDENT;
        }

        System.out.print(indentation);
    }
}
