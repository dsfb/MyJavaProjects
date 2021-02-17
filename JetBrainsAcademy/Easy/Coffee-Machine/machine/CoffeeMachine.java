package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void printMachineInfo(int water, int milk, int coffee, int cups, int money) {
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", water);
        System.out.printf("%d of milk\n", milk);
        System.out.printf("%d of coffee beans\n", coffee);
        System.out.printf("%d of disposable cups\n", cups);
        System.out.printf("%d of money\n", money);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int water = 400;
        int milk = 540;
        int coffee = 120;
        int cups = 9;
        int money = 550;
        String option = "";
        String buyOption = "";
        int input = 0;
        int buy = 0;

        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            option = scanner.nextLine();
            
            switch (option) {
                case  "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
                    buyOption = scanner.nextLine();
                    if (buyOption.equals("back")) {
                        buy = 0;
                    } else {
                        buy = Integer.parseInt(buyOption);    
                    }

                    if (buy == 1) {
                        if (water >= 250 && coffee >= 16 && cups > 0) {
                            water -= 250;
                            coffee -= 16;
                            money += 4;
                            cups--;
                            System.out.println("I have enough resources, making you a coffee!");
                        } else if (water < 250) {
                            System.out.println("Sorry, not enough water!");
                        } else if (coffee < 16) {
                            System.out.println("Sorry, not enough coffee!");
                        } else if (cups == 0) {
                            System.out.println("Sorry, not enough cup!");
                        }
                    } else if (buy == 2) {
                        if (water >= 350 && milk >= 75 && coffee >= 20 && cups > 0) {
                            water -= 350;
                            milk -= 75;
                            coffee -= 20;
                            money += 7;
                            cups--;
                            System.out.println("I have enough resources, making you a coffee!");
                        } else if (water < 350) {
                            System.out.println("Sorry, not enough water!");
                        } else if (milk < 75) {
                            System.out.println("Sorry, not enough milk!");
                        } else if (coffee < 20) {
                            System.out.println("Sorry, not enough coffee!");
                        } else if (cups == 0) {
                            System.out.println("Sorry, not enough cup!");
                        }
                    } else if (buy == 3) {
                        if (water >= 200 && milk >= 100 && coffee >= 12 && cups > 0) {
                            water -= 200;
                            milk -= 100;
                            coffee -= 12;
                            money += 6;
                            cups--;
                            System.out.println("I have enough resources, making you a coffee!");
                        } else if (water < 200) {
                            System.out.println("Sorry, not enough water!");
                        } else if (milk < 100) {
                            System.out.println("Sorry, not enough milk!");
                        } else if (coffee < 12) {
                            System.out.println("Sorry, not enough coffee!");
                        } else if (cups == 0) {
                            System.out.println("Sorry, not enough cup!");
                        }
                    }
                    break;
                case "fill":
                    System.out.println("Write how many ml of water do you want to add: ");
                    input = Integer.parseInt(scanner.nextLine());
                    water += input;
                    System.out.println("Write how many ml of milk do you want to add: ");
                    input = Integer.parseInt(scanner.nextLine());
                    milk += input;
                    System.out.println("Write how many grams of coffee beans do you want to add: ");
                    input = Integer.parseInt(scanner.nextLine());
                    coffee += input;
                    System.out.println("Write how many disposable cups of coffee do you want to add: ");
                    input = Integer.parseInt(scanner.nextLine());
                    cups += input;
                    break;
                case "take":
                    System.out.printf("I gave you $%d\n", money);
                    money = 0;
                    break;
                case "remaining":
                    printMachineInfo(water, milk, coffee, cups, money);                
                    break;
                default:
                    break;
            }
        } while (!option.equals("exit"));
    }
}

