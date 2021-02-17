package banking;

import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static long selectedCreditNumber = -1;
    private static int customerAccountNumber = 493800000 + new Random().nextInt(99999);

    public static void showMainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static long processCardNumber(long cardNumber) {
        long newCard = cardNumber / 10;
        ArrayList<Long> digits = new ArrayList<>();
        while (newCard > 0) {
            digits.add(newCard % 10);
            newCard /= 10;
        }

        long sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            if (i % 2 == 0) {
                digits.set(i, digits.get(i) * 2);
                if (digits.get(i) > 9) {
                    digits.set(i, digits.get(i) - 9);
                }
            }

            sum += digits.get(i);
        }

        int identifier = 0;

        while ((sum + identifier) % 10 > 0) {
            identifier++;
        }

        return (cardNumber / 10) * 10 + identifier;
    }

    public static void createCard() {
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        long cardNumber = 5 + (customerAccountNumber++ * 10) + 400000 * 10000000000L;
        cardNumber = processCardNumber(cardNumber);
        System.out.println(cardNumber);
        Random rn = new Random();
        int cardPin = rn.nextInt(9999);
        System.out.println("Your card PIN:");
        System.out.printf("%04d\n", cardPin);
        String pin = String.format("%04d", cardPin);

        try (Connection con = dataSource.getConnection()) {
            String createCard = "INSERT INTO card(number, pin) VALUES(? , ?);";
            try (PreparedStatement preparedStatement = con.prepareStatement(createCard)) {
                preparedStatement.setString(2, pin);
                preparedStatement.setLong(1, cardNumber);
                int i = preparedStatement.executeUpdate();
                if (i != 1) {
                    throw new SQLException("An error has occurred!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getOption() {
        return scanner.nextInt();
    }

    public static long getLongOption() {
        return scanner.nextLong();
    }

    public static void logIn() {
        System.out.println("Enter your card number:");
        long cardNumber = getLongOption();
        System.out.println("Enter your PIN:");
        int cardPin = getOption();

        try (Connection con = dataSource.getConnection()) {
            String selectBalance = "SELECT COUNT(*) FROM card WHERE number = ? AND pin = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(selectBalance)) {
                preparedStatement.setLong(1, cardNumber);
                preparedStatement.setString(2, String.valueOf(cardPin));
                try (ResultSet balanceRs = preparedStatement.executeQuery()) {
                    while (balanceRs.next()) {
                        // Retrieve column values
                        int hasCard = balanceRs.getInt(1);
                        if (hasCard == 1) {
                            println();
                            System.out.println("You have successfully logged in!");
                            println();
                            selectedCreditNumber = cardNumber;
                        } else if (hasCard == 0) {
                            System.out.println("Wrong card number or PIN!");
                            println();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showLoggedMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    public static void logOut() {
        selectedCreditNumber = -1;
        println();
        System.out.println("You have successfully logged out!");
        println();
    }

    public static void println() {
        System.out.println();
    }

    public static void exit() {
        println();
        System.out.println("Bye!");
    }

    public static void addIncome() {
        System.out.println("Enter income:");
        int income = scanner.nextInt();

        try (Connection con = dataSource.getConnection()) {
            String addIncome = "UPDATE card " +
                    "SET balance = balance + ? " +
                    "WHERE number = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(addIncome)) {
                preparedStatement.setInt(1, income);
                preparedStatement.setLong(2, selectedCreditNumber);
                int u = preparedStatement.executeUpdate();
                if (u == 1) {
                    System.out.println("Income was added!");
                    println();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void doTransfer() {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        long cardNumber = scanner.nextLong();

        if (cardNumber != processCardNumber(cardNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            println();
            return;
        }

        try (Connection con = dataSource.getConnection()) {
            String selectBalance = "SELECT COUNT(*) FROM card WHERE number = ?;";
            String selectBalanceRs = "SELECT balance FROM card WHERE number = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(selectBalance)) {
                preparedStatement.setLong(1, cardNumber);
                try (ResultSet balanceRs = preparedStatement.executeQuery()) {
                    while (balanceRs.next()) {
                        // Retrieve column values
                        int hasCard = balanceRs.getInt(1);
                        System.out.println("hasCard: " + hasCard);
                        if (hasCard == 0) {
                            System.out.println("Such a card does not exist.");
                            println();
                            return;
                        } else {
                            System.out.println("Enter how much money you want to transfer:");
                            int money = scanner.nextInt();
                            int balance = 0;
                            try (PreparedStatement preparedStatementRs = con.prepareStatement(selectBalanceRs)) {
                                preparedStatementRs.setLong(1, selectedCreditNumber);
                                try (ResultSet balanceRs2 = preparedStatementRs.executeQuery()) {
                                    while (balanceRs2.next()) {
                                        balance = balanceRs2.getInt("balance");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            if (balance < money) {
                                System.out.println("Not enough money!");
                                println();
                            } else {
                                String sqlTransfer = "UPDATE card " +
                                        "SET balance = balance + ? " +
                                        "WHERE number = ?;";
                                try (PreparedStatement preparedStatement2 = con.prepareStatement(sqlTransfer)) {
                                    preparedStatement2.setLong(2, selectedCreditNumber);
                                    preparedStatement2.setInt(1, -money);
                                    int result = preparedStatement2.executeUpdate();
                                    if (result != 1) {
                                        return;
                                    }

                                    preparedStatement2.clearParameters();
                                    preparedStatement2.setLong(2, cardNumber);
                                    preparedStatement2.setInt(1, money);
                                    result = preparedStatement2.executeUpdate();
                                    if (result != 1) {
                                        return;
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("Success!");
                                println();
                            }
                        }

                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAccount() {
        println();
        try (Connection con = dataSource.getConnection()) {
            String deleteIncome = "DELETE FROM card " +
                    "WHERE number = ?;";

            try (PreparedStatement preparedStatement = con.prepareStatement(deleteIncome)) {
                preparedStatement.setLong(1, selectedCreditNumber);
                int u = preparedStatement.executeUpdate();
                if (u == 1) {
                    System.out.println("The account has been closed!");
                    println();
                    selectedCreditNumber = -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showBalance() {
        println();
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                String selectBalance = "SELECT balance FROM card WHERE number = ?;";

                try (PreparedStatement preparedStatement = con.prepareStatement(selectBalance)) {
                    preparedStatement.setLong(1, selectedCreditNumber);
                    try (ResultSet balanceRs = preparedStatement.executeQuery()) {
                        while (balanceRs.next()) {
                            // Retrieve column values
                            int balance = balanceRs.getInt("balance");
                            System.out.printf("Balance: %d\n", balance);
                            println();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String url = null;
    private static SQLiteDataSource dataSource = null;

    public static void connectDB() {
        String url = "jdbc:sqlite:path-to-database";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.println("Connection is valid.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args[0].equals("-fileName")) {
            url = "jdbc:sqlite:" + args[1];
            /*if (!(new File(url)).exists()) {
                new File(url).createNewFile();
            }*/
            dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);

            try (Connection con = dataSource.getConnection()) {
                // Statement creation
                try (Statement statement = con.createStatement()) {
                    // Statement execution
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS card (" +
                            "id INTEGER PRIMARY KEY," +
                            "number TEXT NOT NULL," +
                            "pin TEXT NOT NULL," +
                            "balance INTEGER DEFAULT 0)");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        int option = 1;
        while (option > 0) {
            if (selectedCreditNumber < 0) {
                println();
                showMainMenu();
                option = getOption();
                switch (option) {
                    case 1:
                        println();
                        createCard();
                        break;
                    case 2:
                        println();
                        logIn();
                        break;
                    case 0:
                        exit();
                        break;
                    default:
                        continue;
                }
            } else {
                showLoggedMenu();
                option = getOption();
                switch(option) {
                    case 1:
                        showBalance();
                        break;
                    case 2:
                        println();
                        addIncome();
                        break;
                    case 3:
                        println();
                        doTransfer();
                        break;
                    case 4:
                        println();
                        closeAccount();
                        break;
                    case 5:
                        logOut();
                        break;
                    case 0:
                        exit();
                        break;
                    default:
                        continue;
                }
            }
        }
    }
}