package flashcards;

import java.io.*;
import java.util.*;


class Card {
    public String term;
    public String definition;

    public int mistakes = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return mistakes == card.mistakes && Objects.equals(term, card.term) && Objects.equals(definition, card.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, definition, mistakes);
    }
}


public class Main {
    private static HashSet<String> termSet = new HashSet<>();
    private static HashSet<String> definitionSet = new HashSet<>();
    private static HashMap<String, String> definitionTermMap = new HashMap<>();

    private static ArrayList<Card> cardList = new ArrayList<>();

    private static ArrayList<Card> hardestCardList = new ArrayList<>();

    private static ArrayList<String> logLineList = new ArrayList<>();

    private static Set<String> hardestTermSet = new HashSet<>();
    private static Scanner scanner = new Scanner(System.in);

    private static String currentLine = null;

    private static String getAction() {
        String action = null;
        do {
            currentLine = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
            System.out.println(currentLine);
            logLineList.add(currentLine);
            action = scanner.nextLine();
            switch (action) {
                case "add":
                case "remove":
                case "import":
                case "export":
                case "ask":
                case "exit":
                case "log":
                case "hardest card":
                case "reset stats":
                    logLineList.add(action);
                    break;
                default:
                    action = null;
                    break;
            }
        } while (action == null);

        return action;
    }

    private static void addCard() {
        Card card = new Card();
        currentLine = "The card";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        String input = scanner.nextLine();
        logLineList.add(input);
        if (termSet.contains(input))  {
            currentLine = String.format("The card \"%s\" already exists.\n", input);
            System.out.println(currentLine);
            logLineList.add(currentLine);
            return;
        }
        card.term = input;

        currentLine = "The definition of the card:";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        input = scanner.nextLine();
        logLineList.add(input);
        if (definitionSet.contains(input)) {
            currentLine = String.format("The definition \"%s\" already exists.\n", input);
            System.out.println(currentLine);
            logLineList.add(currentLine);
            return;
        }
        card.definition = input;

        cardList.add(card);
        termSet.add(card.term);
        definitionSet.add(card.definition);
        definitionTermMap.put(card.definition, card.term);

        currentLine = String.format("The pair (\"%s\":\"%s\") has been added.\n",
                card.term, card.definition);
        System.out.println(currentLine);
        logLineList.add(currentLine);
    }

    private static Card getCardByTermFromList(String term) {
        for (Card c : cardList) {
            if (c.term.equals(term)) {
                return c;
            }
        }

        throw new RuntimeException();
    }

    private static void removeCard() {
        currentLine = "Which card?";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        String term = scanner.nextLine();
        logLineList.add(term);
        if (termSet.contains(term)) {
            Card card = getCardByTermFromList(term);
            cardList.remove(card);
            termSet.remove(card.term);
            definitionSet.remove(card.definition);
            definitionTermMap.remove(card.definition);
            currentLine = "The card has been removed.";
            System.out.println(currentLine);
            logLineList.add(currentLine);
        } else {
            currentLine = String.format("Can't remove \"%s\": there is no such card.\n", term);
            System.out.println(currentLine);
            logLineList.add(currentLine);
        }
    }

    private static void importFile() {
        currentLine = "File name:";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        String fileName = scanner.nextLine();
        logLineList.add(fileName);
        importFileCoreRoutine(fileName);
    }

    private static void importFileCoreRoutine(String fileName) {
        int quantity = 0;
        File file = new File(fileName);
        try (FileReader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String term = null;
            String definition = null;
            int mistakes = 0;
            while (bufferedReader.ready()) {
                term = bufferedReader.readLine();
                definition = bufferedReader.readLine();
                mistakes = Integer.parseInt(bufferedReader.readLine());
                quantity++;
                Card c = null;
                if (termSet.contains(term)) {
                    c = getCardByTermFromList(term);
                    definitionTermMap.remove(c.definition);
                    definitionSet.remove(c.definition);
                    int index = cardList.indexOf(c);
                    c.definition = definition;
                    c.mistakes = mistakes;
                    cardList.set(index, c);
                    definitionTermMap.put(definition, c.term);
                    definitionSet.add(definition);
                } else {
                    c = new Card();
                    c.term = term;
                    c.definition = definition;
                    c.mistakes = mistakes;
                    definitionTermMap.put(definition, term);
                    definitionSet.add(definition);
                    termSet.add(term);
                    cardList.add(c);
                }

                handleHardestCards(c);
            }
        } catch (FileNotFoundException e) {
            currentLine = "File not found.";
            System.out.println(currentLine);
            logLineList.add(currentLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            currentLine = String.format("%d cards have been loaded.\n", quantity);
            System.out.println(currentLine);
            logLineList.add(currentLine);
        }
    }

    private static void handleHardestCards(Card card) {
        if (hardestCardList.isEmpty()) {
            hardestCardList.add(card);
            hardestTermSet.add(card.term);
        } else if (!hardestTermSet.contains(card.term)) {
            if (card.mistakes == hardestCardList.get(0).mistakes) {
                hardestCardList.add(card);
            } else if (card.mistakes > hardestCardList.get(0).mistakes) {
                hardestCardList.clear();
                hardestCardList.add(card);
            }

            hardestTermSet.add(card.term);
        }
    }

    private static void exportFile() {
        currentLine = "File name:";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        String fileName = scanner.nextLine();
        exportFileCoreRoutine(fileName);
    }

    private static void exportFileCoreRoutine(String fileName) {
        int quantity = 0;
        try (FileWriter fw = new FileWriter(fileName);
             BufferedWriter bw = new BufferedWriter(fw);) {
            for (Card c : cardList) {
                bw.write(c.term);
                bw.write("\n");
                bw.write(c.definition);
                bw.write("\n");
                bw.write(Integer.toString(c.mistakes));
                bw.write("\n");
                quantity++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            currentLine = String.format("%d cards have been saved.\n", quantity);
            System.out.println(currentLine);
            logLineList.add(currentLine);
        }
    }

    private static void ask() {
        String answer = null;
        int manyTimes;
        currentLine = "How many times to ask?";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        currentLine = scanner.nextLine();
        manyTimes = Integer.parseInt(currentLine);
        logLineList.add(currentLine);
        for (int i = 0; i < manyTimes; i++) {
            currentLine = String.format("Print the definition of \"%s\":\n", cardList.get(i).term);
            System.out.println(currentLine);
            logLineList.add(currentLine);
            answer = scanner.nextLine();
            logLineList.add(answer);
            if (answer.equals(cardList.get(i).definition)) {
                currentLine = "Correct!";
                System.out.println(currentLine);
                logLineList.add(currentLine);
            } else if (definitionSet.contains(answer)) {
                currentLine = String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n",
                        cardList.get(i).definition, definitionTermMap.get(answer));
                System.out.println(currentLine);
                logLineList.add(currentLine);
                cardList.get(i).mistakes++;
                handleHardestCards(cardList.get(i));
            } else {
                currentLine = String.format("Wrong. The right answer is \"%s\".\n", cardList.get(i).definition);
                System.out.println(currentLine);
                logLineList.add(currentLine);
                cardList.get(i).mistakes++;
                handleHardestCards(cardList.get(i));
            }
        }
    }

    private static void saveLogs() {
        currentLine = "File name:";
        System.out.println(currentLine);
        String fileName = scanner.nextLine();
        try (FileWriter fw = new FileWriter(fileName);
             BufferedWriter bw = new BufferedWriter((fw))) {
            for (String line : logLineList) {
                bw.write(line);
                bw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            currentLine = "The log has been saved.";
            System.out.println(currentLine);
        }
    }

    private static void showHardestCard() {
        if (hardestCardList.isEmpty()) {
            currentLine = "There are no cards with errors.";
            System.out.println(currentLine);
        } else if (hardestCardList.size() == 1) {
            Card c = hardestCardList.get(0);
            currentLine = String.format("The hardest card is \"%s\". You have %d errors answering it\n",
                    c.term, c.mistakes);
            System.out.println(currentLine);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("The hardest cards are ");
            for (Card c : hardestCardList) {
                sb.append(String.format("\"%s\", ", c.term));
            }
            sb.setLength(sb.length() - 2);
            sb.append(String.format(". You have %d errors answering them",
                    hardestCardList.get(0).mistakes));
            System.out.println(sb);
            currentLine = sb.toString();
        }

        logLineList.add(currentLine);
    }

    private static void resetStats() {
        currentLine = "Card statistics have been reset.";
        System.out.println(currentLine);
        logLineList.add(currentLine);
        hardestCardList.clear();
        for (Card c : cardList) {
            c.mistakes = 0;
        }
    }

    public static void main(String[] args) {
        if (args.length == 2 || args.length == 4) {
           if (args[0].equals("-import")) {
               String fileName = args[1];
               importFileCoreRoutine(fileName);
           } else if (args.length == 4 && args[2].equals("-import")) {
               String fileName = args[3];
               importFileCoreRoutine(fileName);
           }
        }

        String action = null;

        do {
            action = getAction();
            switch (action) {
                case "add":
                    addCard();
                    break;
                case "ask":
                    ask();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    importFile();
                    break;
                case "export":
                    exportFile();
                    break;
                case "exit":
                    scanner.close();
                    System.out.println("Bye bye!");
                    break;
                case "log":
                    saveLogs();
                    break;
                case "hardest card":
                    showHardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                default:
                    break;
            }
        } while (!action.equals("exit"));

        if (args.length == 2 || args.length == 4) {
            if (args[0].equals("-export")) {
                String fileName = args[1];
                exportFileCoreRoutine(fileName);
            }  else if (args.length == 4 && args[2].equals("-export")) {
                String fileName = args[3];
                exportFileCoreRoutine(fileName);
            }
        }
    }
}