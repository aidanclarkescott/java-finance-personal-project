package Budget;

import java.util.ArrayList;
import java.util.Scanner;

public class Budget {
    private ArrayList<Expense> expenses;
    private int totalExpenses;

    public Budget() {
        this.expenses = new ArrayList<Expense>();
    }

    public void addExpense(Scanner reader) {
        System.out.print("Name of item: ");
        String name = reader.nextLine();
        System.out.print("Price: ");
        Double price = Double.parseDouble(reader.nextLine());
        this.expenses.add(new Expense(name, price));
        this.totalExpenses += price;
    }

    public void printExpenses() {
        for (Expense expense : this.expenses) {
            System.out.println(expense);
        }
        System.out.println("");
    }

    public int getTotalExpenses() {
        return this.totalExpenses;
    }

}
