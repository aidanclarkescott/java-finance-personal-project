package ui;

import Budget.Budget;

import java.util.Scanner;
import java.util.HashMap;

public class UserInterface {
    private Scanner reader;
    private HashMap<String, Budget> budgets;

    public UserInterface(Scanner reader) {
        this.reader = reader;
        this.budgets = new HashMap<String, Budget>();
        menu(reader);
    }

    public void menu(Scanner reader) {
        System.out.println("Personal Finance Application \n");
        while (true) {
            System.out.println("Menu: \n 1. Create a new budget " +
                    "\n 2. Add expense to existing budget " +
                    "\n 3. View stock portfolio " +
                    "\n 4. Print budget expenses" +
                    "\n 5. Print budget list" +
                    "\n 6. Quit \n");
            String command = reader.nextLine();

            if (command.equals("6")) {
                break;
            } else if (command.equals("1")) {
                createBudget(reader);
            } else if (command.equals("2")) {
                System.out.print("Which budget: ");
                String budgetName = reader.nextLine();
                try {
                     this.budgets.get(budgetName).addExpense(reader);
                } catch (Exception e) {
                    throw new NullPointerException("You haven't created a budget yet.");
                }
            } else if (command.equals("3")) {
                System.out.println("Stocks: \n AAPL: $150 \n GOOG: $1000 \n etc. \n");
            } else if (command.equals("4")) {
                System.out.print("Which budget: ");
                String budgetName = reader.nextLine();
                try {
                    this.budgets.get(budgetName).printExpenses();
                } catch (Exception e) {
                    throw new NullPointerException("You haven't created a budget yet.");
                }
            } else if (command.equals("5")) {
                printBudgets();
            }
        }
        System.out.println("Thank you for using the application.");
    }

    public void createBudget(Scanner reader) {
        System.out.print("Budget name: ");
        String budgetName = reader.nextLine();
        this.budgets.put(budgetName, new Budget());
    }

    public void printBudgets() {
        int i = 0;
        for (String budgetName : this.budgets.keySet()) {
            i++;
            System.out.println(i + ". " + budgetName + ": $" + this.budgets.get(budgetName).getTotalExpenses());
        }
        System.out.println("");
    }

}
