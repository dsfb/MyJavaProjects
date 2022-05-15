package search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static Map<String, List<Integer>> tokenMap = new TreeMap<>();
    private static List<String> lineList = new ArrayList<>();
    private static Set<String> foundPersons = new TreeSet<>();
    private static String strategy;
    private static String dataToSearchPeople;

    public static void populateMapAndList(String fileName) {
        int index = 1;
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            while (reader.ready()) {
                String line = reader.readLine();
                lineList.add(line);
                String[] tokens = line.toLowerCase().split(" ");
                for (String token : tokens) {
                    if (tokenMap.containsKey(token)) {
                        tokenMap.get(token).add(index);
                    } else {
                        List<Integer> indexes = new ArrayList<>();
                        indexes.add(index);
                        tokenMap.put(token, indexes);
                    }
                }
                index++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getStrategyOption(Scanner scanner) {
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        return scanner.nextLine();
    }

    public static void showMenuOptions() {
        System.out.println();
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    public static int getMenuOption(Scanner scanner) {
        int option = Integer.parseInt(scanner.nextLine());
        
        if (option < 0 || option > 2) {
            System.out.println();
            System.out.println("Incorrect option! Try again.");
        }

        return option;
    }

    public static void printResults() {
        if (foundPersons.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.printf("\n%d persons found:\n", foundPersons.size());
            for (String row : foundPersons) {
                System.out.println(row);
            }
        }
    }

    public static void processByAnyMatch() {
        String[] tokens = dataToSearchPeople.split(" ");
        for (String token : tokens) {
            if (tokenMap.containsKey(token)) {
                for (int index : tokenMap.get(token)) {
                    foundPersons.add(lineList.get(index - 1));
                }
            }
        }
    }

    public static void processByAllMatch() {
        String[] tokens = dataToSearchPeople.split(" ");
        Set<Integer> indexes = new TreeSet<>();
        for (String token : tokens) {
            if (tokenMap.containsKey(token)) {
                if (indexes.isEmpty()) {
                    for (int index : tokenMap.get(token)) {
                        indexes.add(index);
                    }
                } else {
                    indexes.retainAll(new TreeSet<Integer>(tokenMap.get(token)));
                }
            } else {
                return;
            }
        }

        for (int index : indexes) {
            foundPersons.add(lineList.get(index - 1));
        }
    }

    public static void processByNoneMatch() {
        String[] tokens = dataToSearchPeople.split(" ");
        Set<Integer> indexes = new TreeSet<>();
        for (String token : tokens) {
            if (tokenMap.containsKey(token)) {
                indexes.addAll(new TreeSet<Integer>(tokenMap.get(token)));
            }
        }

        Set<Integer> wantedIndexes = new TreeSet<Integer>();
        for (int i = 1; i <= lineList.size(); i++) {
            if (indexes.contains(i)) {
                continue;
            }

            wantedIndexes.add(i);
        }

        for (int index : wantedIndexes) {
            foundPersons.add(lineList.get(index - 1));
        }
    }

    public static void main(String[] args) {
        String fileName = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--data")) {
                fileName = args[i + 1];
                break;
            }
        }

        populateMapAndList(fileName);
        int option = -1;
        
        while (option != 0) {
            Scanner scanner = new Scanner(System.in);

            do {
                showMenuOptions();
                option = getMenuOption(scanner);
            } while (option < 0 || option > 2);
            
            if (option == 1) {
                String strategy = getStrategyOption(scanner);
                foundPersons.clear();
                System.out.println("\nEnter a name or email to search all suitable people.");
                dataToSearchPeople = scanner.nextLine().toLowerCase();

                if (strategy.toLowerCase().equals("any")) {
                    processByAnyMatch();
                } else if (strategy.toLowerCase().equals("all")) {
                    processByAllMatch();
                } else if (strategy.toLowerCase().equals("none")) {
                    processByNoneMatch();
                }

                printResults();
            } else if (option == 2) {
                System.out.println();
                System.out.println("=== List of people ===");

                for (String row : lineList) {
                    System.out.println(row);
                }
            }

            scanner.close();
        }
    }
}
