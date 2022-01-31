package budget;

import budget.controller.Controller;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        new Controller().processActions();
    }
}
