package Budget;

import java.util.ArrayList;
import java.util.Scanner;

public class Budget {
    private ArrayList<Expense> expenses;
    private double totalExpenses;
    private String name;

    public Budget(String name) {
        this.expenses = new ArrayList<Expense>();
        this.name = name;
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

    public double getTotalExpenses() {
        return this.totalExpenses;
    }

    public String getName() {
        return this.name;
    }

}
