package ui;

import java.util.Scanner;
import network.*;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        UserInterfaceLogic uiL = new UserInterfaceLogic(reader);
        //uiL.menu();
        UserInterface ui = new UserInterface();
        SwingUtilities.invokeLater(ui);
    }
}

