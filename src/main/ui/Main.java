package ui;

import java.util.Scanner;
import network.*;

public class Main {


    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        UserInterfaceLogic ui = new UserInterfaceLogic(reader);
        ui.menu();
    }
}

