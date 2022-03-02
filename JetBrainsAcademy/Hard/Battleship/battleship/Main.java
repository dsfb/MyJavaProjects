package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static class ShipData {
        private String name;
        private int size;

        public ShipData(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }

        public String getName() {
            return this.name;
        }
    }

    public enum InputIdentifier {
        AircraftCarrier(new ShipData("Aircraft Carrier", 5)),
        Battleship(new ShipData("Battleship", 4)),
        Submarine(new ShipData("Submarine", 3)),
        Cruiser(new ShipData("Cruiser", 3)),
        Destroyer(new ShipData("Destroyer", 2));

        private ShipData data;

        private InputIdentifier(ShipData data) {
            this.data = data;
        }

        public int getSize() {
            return this.data.getSize();
        }

        public String getName() {
            return this.data.getName();
        }
    }

    public static class InputErrorState {
        private boolean wrongLength;
        private boolean wrongLocation;
        private boolean tooClose;

        public InputErrorState(boolean wrongLength, boolean wrongLocation, boolean tooClose) {
            this.wrongLength = wrongLength;
            this.wrongLocation = wrongLocation;
            this.tooClose = tooClose;
        }

        public boolean isWrongLength() {
            return wrongLength;
        }

        public boolean isWrongLocation() {
            return wrongLocation;
        }

        public boolean isTooClose() {
            return tooClose;
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    public static class InputProcessor {
        private GamePlayer gamePlayer;

        public InputProcessor(GamePlayer gamePlayer) {
            super();
            this.gamePlayer = gamePlayer;
        }

        public String getMsg(String name, int size) {
            return String.format("Enter the coordinates of the %s (%d cells):\n", name, size);
        }

        public void passTurn() {
            System.out.printf("\nPress Enter and pass the move to another player\n");
            scanner.nextLine();
        }

        public String getALocation() {
            String result = scanner.nextLine();
            return result;
        }

        public String[] getShip(String message) {
            System.out.println(message);
            String[] result = scanner.nextLine().split(" ");
            return result;
        }

        public String[] getNewShip(String name, int size) {
            String message = getMsg(name, size);
            String[] tokens = null;
            InputErrorState errorState = null;
            while (true) {
                tokens = this.getShip(message);
                errorState = this.isGoodInput(tokens, name, size);
                if (errorState.isWrongLocation()) {
                    message = "\nError! Wrong ship location! Try again: \n";
                } else if (errorState.isWrongLength()) {
                    message = String.format("\nError! Wrong length of the %s! Try again: \n", name);
                } else if (errorState.isTooClose()) {
                    message = "\nError! You placed it too close to another one. Try again: \n";
                } else {
                    return tokens;
                }
            }
        }

        public InputErrorState isGoodInput(String[] tokens, String name, int size) {
            int diffPos = Integer.parseInt(tokens[0].substring(1)) - Integer.parseInt(tokens[1].substring(1));
            int diffLetter = tokens[0].charAt(0) - tokens[1].charAt(0);
            boolean wrongLocation = false;
            boolean wrongLength = false;
            boolean tooClose = false;
            if (diffLetter != 0 && diffPos != 0) {
                wrongLocation = true;
            }

            if (diffLetter == 0 && Math.abs(diffPos) == size - 1) {
                // nop.
            } else if (Math.abs(diffLetter) == size - 1 && diffPos == 0) {
                // nop.
            } else {
                wrongLength = true;
            }

            if (!wrongLength && !wrongLocation) {
                tooClose = this.gamePlayer.evaluateTooCloseError(tokens);
            }

            return new InputErrorState(wrongLength, wrongLocation, tooClose);
        }
    }

    public static class GamePlayer {
        private String name;
        private static final int HEIGHT = 10;
        private static final int WIDTH = 10;
        private ShipState[][] gameField = this.getNewGameField(HEIGHT, WIDTH);
        private ShipState[][] fogField = this.getNewGameField(HEIGHT, WIDTH);
        private HashMap<InputIdentifier, ArrayList<String>> ships = new HashMap<>();
        private InputProcessor processor = new InputProcessor(this);

        public ShipState[][] getNewGameField(int height, int width) {
            ShipState[][] gameField = new ShipState[height][width];

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    gameField[j][i] = ShipState.EMPTY;
                }
            }

            return gameField;
        }

        public GamePlayer(String name) {
            this.name = name;
        }

        private boolean evaluateTooCloseError(String[] tokens) {
            int firstLetter = getIndexByLetter(tokens[0]);
            int secondLetter = getIndexByLetter(tokens[1]);
            int firstIndex = Integer.parseInt(tokens[0].substring(1));
            int secondIndex = Integer.parseInt(tokens[1].substring(1));
            boolean verticalDirection = firstLetter != secondLetter;
            int startLetter = firstLetter - 1;
            int endLetter = firstLetter + 1;
            int startIndex = Math.min(firstIndex, secondIndex) - 1;
            int endIndex = Math.max(firstIndex, secondIndex) + 1;

            if (startLetter < 0) {
                startLetter = 0;
            }

            if (endLetter >= WIDTH) {
                endLetter = WIDTH - 1;
            }

            if (startIndex < 0) {
                startIndex = 0;
            }

            if (endIndex >= HEIGHT) {
                endIndex = HEIGHT - 1;
            }

            if (verticalDirection) {
                for (int i = startLetter; i <= endLetter; i++) {
                    for (int j = startIndex; j <= endIndex; j = j + 2) {
                        if (this.gameField[i][j] != ShipState.EMPTY) {
                            return true;
                        }
                    }
                }
            } else { // horizontalDirection case!
                for (int i = startLetter; i <= endLetter; i = i + 2) {
                    for (int j = startIndex; j <= endIndex; j++) {
                        if (this.gameField[i][j] != ShipState.EMPTY) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        private int getIndexByLetter(String token) {
            return token.toUpperCase().charAt(0) - 'A';
        }

        public void putShips() {
            System.out.printf("%s, place your ships on the game field\n\n", this.name);
            this.printField();

            InputIdentifier[] ships = new InputIdentifier[] {
                    InputIdentifier.AircraftCarrier,
                    InputIdentifier.Battleship,
                    InputIdentifier.Submarine,
                    InputIdentifier.Cruiser,
                    InputIdentifier.Destroyer
            };

            for (InputIdentifier identifier : ships) {
                System.out.println();
                this.processAShip(identifier);
                System.out.println();
                this.printField();
            }
        }

        public void printAField(boolean printFog) {
            System.out.print("  ");
            for (int i = 1; i < WIDTH; i++) {
                System.out.printf("%d ", i);
            }
            System.out.printf("%d", WIDTH);
            System.out.println();

            char letter = 'A';
            String strFmt = "%c ";
            for (int j = 0; j < WIDTH; j++) {
                System.out.printf("%c ", letter + j);
                strFmt = "%c ";
                for (int i = 0; i < HEIGHT; i++) {
                    if (i == HEIGHT - 1) {
                        strFmt = "%c";
                    }

                    if (printFog) {
                        System.out.printf(strFmt, fogField[j][i].getValue());
                    } else {
                        System.out.printf(strFmt, gameField[j][i].getValue());
                    }
                }
                System.out.println();
            }
        }

        public void printField() {
            this.printAField(false);
        }

        public void printFogField() {
            this.printAField(true);
        }

        public void processAShip(InputIdentifier ship) {
            String[] processedShip = this.processor.getNewShip(ship.getName(), ship.getSize());
            this.processInput(processedShip, ship.getSize(), ship);
        }

        public void processInput(String[] input, int size, InputIdentifier ship) {
            int index = getIndexByLetter(input[0]);
            int startNum = Integer.parseInt(input[0].substring(1));
            int endNum = Integer.parseInt(input[1].substring(1));
            ArrayList<String> values = new ArrayList<>();

            if (isSameLetter(input)) {
                int minNum = Math.min(startNum, endNum);
                for (int i = 0; i < size; i++) {
                    this.gameField[index][minNum - 1 + i] = ShipState.NEW;
                    values.add(Character.toString(input[0].charAt(0)).toUpperCase() + Integer.toString(minNum + i));
                }
            } else if (startNum == endNum) {
                int lastIndex = getIndexByLetter(input[1]);
                int minIndex = Math.min(index, lastIndex);
                int maxIndex = Math.max(index, lastIndex);
                for (int i = minIndex; i <= maxIndex; i++) {
                    this.gameField[i][startNum - 1] = ShipState.NEW;
                    values.add(Character.toString('A' + i).toUpperCase() + Integer.toString(startNum));
                }
            }

            this.ships.put(ship, values);
        }

        private boolean isSameLetter(String[] tokens) {
            return tokens[0].toUpperCase().charAt(0) == tokens[1].toUpperCase().charAt(0);
        }

        public void passTurn() {
            this.processor.passTurn();
        }

        public String processLocation() {
            System.out.println();
            System.out.printf("%s, it's your turn:\n\n", this.name);
            return this.processor.getALocation();
        }

        public String processAttack(String location) {
            String result = "";
            int intLetter = this.getIndexByLetter(location);
            int intPosition = Integer.parseInt(location.substring(1)) - 1;
            if (intLetter >= HEIGHT || intPosition >= WIDTH) {
                result = "You missed!";
            } else if (this.gameField[intLetter][intPosition].equals(ShipState.EMPTY)) {
                this.gameField[intLetter][intPosition] = ShipState.MISS;
                this.fogField[intLetter][intPosition] = ShipState.MISS;
                result = "You missed!";
            } else if (this.gameField[intLetter][intPosition].equals(ShipState.NEW) ||
                    this.gameField[intLetter][intPosition].equals(ShipState.HIT)) {
                this.gameField[intLetter][intPosition] = ShipState.HIT;
                this.fogField[intLetter][intPosition] = ShipState.HIT;
                this.printFogField();
                Set<InputIdentifier> shipIdSet = this.ships.keySet();
                for (InputIdentifier input : shipIdSet) {
                    ArrayList<String> values = this.ships.get(input);
                    if (values.contains(location)) {
                        values.remove(location);
                        if (values.isEmpty()) {
                            this.ships.remove(input);
                            result = "You sank a ship!";
                        } else {
                            this.ships.put(input, values);
                            result = "You hit a ship!";
                        }
                        break;
                    }
                }
            }

            return result;
        }

        public boolean loseGame() {
            return this.ships.isEmpty();
        }
    }

    public static class GameManager {
        private GamePlayer player1 = new GamePlayer("Player 1");
        private GamePlayer player2 = new GamePlayer("Player 2");

        public void startGame() {
            player1.putShips();
            player1.passTurn();
            player2.putShips();
            player2.passTurn();
            System.out.println();
            System.out.println();
            // System.out.println("The game starts!");
        }

        private void printSeparator() {
            System.out.println("---------------------");
        }

        public void processGame() {
            String result = null;
            String location = null;
            while (true) {
                player2.printFogField();
                this.printSeparator();
                player1.printField();
                location = player1.processLocation();
                result = player2.processAttack(location);
                if (player2.loseGame()) {
                    break;
                }
                System.out.print(result);
                player1.passTurn();

                player1.printFogField();
                this.printSeparator();
                player2.printField();
                location = player2.processLocation();
                result = player1.processAttack(location);
                if (player1.loseGame()) {
                    break;
                }
                System.out.print(result);
                player2.passTurn();
            }

            System.out.println("You sank the last ship. You won. Congratulations!");
            System.out.println();
        }
    }

    public enum ShipState {
        EMPTY('~'),
        NEW('O'),
        HIT('X'),
        MISS('M');

        ShipState(Character v) {
            this.value = v;
        }

        private Character value;

        public Character getValue() {
            return this.value;
        }
    }

    public static void main(String[] args) {
        GameManager manager = new GameManager();
        manager.startGame();
        manager.processGame();
    }
}