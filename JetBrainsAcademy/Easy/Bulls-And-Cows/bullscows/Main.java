package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static boolean contains(char character, char[] characters) {
        for (int i = 0; i < characters.length; i++) {
            if (character == characters[i]) {
                return true;
            }
        }

        return false;
    }

    private final static char[] characters = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'x', 'w', 'y', 'z'
    };

    public static boolean process(int turn) {
        System.out.printf("Turn %d:\n", turn);
        String guess = scanner.nextLine();
        while (guess.isEmpty()) {
            guess = scanner.nextLine();
        }
        int cows = 0;
        int bulls = 0;
        char[] secretNumberChars = secretNumber.toCharArray();

        for (int i = length - 1; i >= 0; i--) {
            if (guess.charAt(i) == secretNumberChars[i]) {
                bulls++;
            } else if (contains(guess.charAt(i), secretNumberChars)) {
                cows++;
            }
        }

        String grade = "None";

        if (cows != 0 && bulls != 0) {
            grade = String.format("%d bull(s) and %d cow(s)", bulls, cows);
        } else if (bulls != 0) {
            grade = String.format("%d bull(s)", bulls);
        } else if (cows != 0) {
            grade = String.format("%d cow(s)", cows);
        }

        System.out.printf("Grade: %s\n", grade);
        return bulls < length;
    }

    private static Scanner scanner = new Scanner(System.in);
    private static int length;
    private static int numberPossibleSymbols;

    private static boolean setLength() {
        System.out.println("Input the length of the secret code:");
        try {
            length = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf("Error: it's not possible to read a length.");
            System.exit(-5);
        }

        System.out.println("Input the number of possible symbols in the code:");
        try {
            numberPossibleSymbols = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf("Error: it's not possible to read a number of symbols.");
            System.exit(-6);
        }

        if (length <= 0) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, numberPossibleSymbols);
            System.exit(-1);
        } else if (numberPossibleSymbols <= 0) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, numberPossibleSymbols);
            System.exit(-2);
        } else if (numberPossibleSymbols < length) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, numberPossibleSymbols);
            System.exit(-3);
        } else if (numberPossibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(-4);
        }

        return true;
    }

    private static String secretNumber = "";

    private static void generateSecretNumber(int length) {
        Random rnd = new Random(System.nanoTime());
        int sCounter = 0;

        int value;
        while (sCounter < length) {
            do {
                value = rnd.nextInt(numberPossibleSymbols);
            } while (contains(characters[value], secretNumber.toCharArray()));

            secretNumber += characters[value];
            sCounter++;
        }
    }

    public static String showPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('*');
        }
        return sb.toString();
    }

    public static String showCharacters() {
        StringBuilder sb = new StringBuilder("(0-");
        if (numberPossibleSymbols <= 10) {
            sb.append(String.valueOf(numberPossibleSymbols - 1));
        } else {
            sb.append("9, a-");
            sb.append((char) ((int) 'a' + numberPossibleSymbols - 11));
        }

        sb.append(")");
        return sb.toString();
    }

    public static void main(String[] args) {
        if (setLength()) {
            generateSecretNumber(length);
            System.out.printf("The secret is prepared: %s %s.\n", showPassword(), showCharacters());
            System.out.println("Okay, let's start a game!");
            int turn = 1;

            while (process(turn++)) {
            }

            System.out.println("Congratulations! You guessed the secret code.");
        }
    }
}
