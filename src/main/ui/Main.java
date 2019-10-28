package ui;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        UserInterfaceLogic ui = new UserInterfaceLogic(reader);
        ui.menu();

    }
}

