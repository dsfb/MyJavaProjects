package cinema;

import java.util.Scanner;

public class Cinema {

    public static void showSeats(char[][] seats) {
        System.out.println("Cinema:");
        System.out.printf("  ");
        for (int i = 1; i <= seats[0].length; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        for (int i = 1; i <= seats.length; i++) {
            System.out.printf("%d ", i);
            for (int j = 1; j <= seats[i - 1].length; j++) {
                System.out.printf("%c ", seats[i - 1][j - 1]);
            }
            System.out.println();
        }
    }

    public static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("0. Exit");
        System.out.println();
    }

    public static void buyTicket(char[][] charSeats, int rows, int seats) {
        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();

        System.out.printf("Ticket price: $");
        int totalSeats = rows * seats;
        if (totalSeats <= 60 || row <= rows / 2) {
            System.out.printf("%d\n", 10);
        } else {
            System.out.printf("%d\n", 8);
        }

        charSeats[row - 1][seat - 1] = 'B';
    }

    private static Scanner scanner = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
                
        char[][] charSeats = new char[rows][seats];
        for (int i = 0; i < charSeats.length; i++) {
            for (int j = 0; j < charSeats[i].length; j++) {
                charSeats[i][j] = 'S';
            }
        }

        int choice = 0;

        do {
            System.out.println();
            showMenu();
            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    showSeats(charSeats);
                    break;
                case 2:
                    buyTicket(charSeats, rows, seats);
                    break;
                default:
                    break;
            }
        } while (choice > 0);

        /*
        System.out.println("Total income:");
        int totalSeats = rows * seats;
        
        if (totalSeats <= 60) {
            System.out.printf("$%d", totalSeats * 10); // The price of each seat is ten dollars in this case.
        } else {
            // The price of each seat is defined by two parts in this case.
            System.out.printf("$%d", (rows / 2) * seats * 10 + (rows - rows / 2) * seats * 8);
        }*/
    }
}
