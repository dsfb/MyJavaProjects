package budget.controller;

import budget.service.Manager;

import java.util.Scanner;

public class Controller {
    private Scanner sc = new Scanner(System.in);
    private Manager manager = new Manager();

    private String[] actionMenu = new String[]{
            "Add income",
            "Add purchase",
            "Show list of purchases",
            "Balance",
            "Save",
            "Load",
            "Analyze (Sort)"
    };

    private int chooseAction() {
        System.out.println("Choose your action:");
        for (int i = 0; i < actionMenu.length; i++) {
            System.out.printf("%d) %s\n", i + 1, actionMenu[i]);
        }
        System.out.println("0) Exit");
        int option = Integer.parseInt(sc.nextLine());
        return option;
    }

    public void processActions() {
        int option;
        while (true) {
            option = chooseAction();
            switch (option) {
                case 1:
                    manager.addIncome();
                    break;
                case 2:
                    do {
                        option = manager.getPurchaseType();
                        if (option < 4) {
                            String typeName = manager.getTypeMenu(option);
                            manager.addPurchase(typeName);
                        } else {
                            System.out.println();
                        }
                    } while (option < 4);

                    break;
                case 3:
                    manager.showPurchases();
                    break;
                case 4:
                    manager.showBalance();
                    break;
                case 5:
                    manager.saveData();
                    break;
                case 6:
                    manager.loadData();
                    break;
                case 7:
                    manager.processSortingOption();
                    break;
                case 0:
                    System.out.printf("\nBye!");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}
