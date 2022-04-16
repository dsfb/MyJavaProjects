package numbers;

import java.util.*;

class AmazingNumbers {
    static HashMap<String, String> mutualExclusiveProps = new HashMap<>();
    static HashMap<String, String> sameExclusiveProps = new HashMap<>();

    static NumberChecker checker = new NumberChecker();

    static {
        mutualExclusiveProps.put("odd", "even");
        mutualExclusiveProps.put("-odd", "-even");
        mutualExclusiveProps.put("spy", "duck");
        mutualExclusiveProps.put("-spy", "-duck");
        mutualExclusiveProps.put("square", "sunny");
        mutualExclusiveProps.put("-square", "-sunny");
        mutualExclusiveProps.put("happy", "sad");
        mutualExclusiveProps.put("-happy", "-sad");

        String[] tempMutualoptions = {
            "even", "odd", "duck", "spy",
            "sunny", "square", "sad", "happy"
        };

        for (String option : tempMutualoptions) {
            sameExclusiveProps.put("-" + option,  option);
        }
    };

    private static String availablePropertiesStr = "[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]";

    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameters show how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and two properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
        long number = -1;
        int quantity;
        Scanner sc = new Scanner(System.in);
        String input;
        boolean singleCase;
        List<String> properties = Arrays.asList("even", "odd", "buzz",
                "duck", "palindromic",
                "gapful", "spy", "sunny",
                "square", "jumping",
                "sad", "happy");
        do {
            System.out.print("Enter a request: ");
            input = sc.nextLine().toLowerCase();
            singleCase = !input.contains(" ");
            if (singleCase) {
                number = Long.parseLong(input);
                if (number != 0) {
                    checker.processANumber(number);
                }
            } else {
                String[] tokens = input.split(" ");
                if (tokens.length == 2) {
                    number = Long.parseLong(tokens[0]);
                    quantity = Integer.parseInt(tokens[1]);
                    if (quantity > 0 && number > 0) {
                        for (long i = number; i < number + quantity; i++) {
                            System.out.println(checker.processOneOfSeveralNumbers(i));
                        }
                    } else {
                        if (number < 1) {
                            System.out.println("The first parameter should be a natural number or zero.\n");
                        }

                        if (quantity < 1) {
                            System.out.println("The second parameter should be a natural number.\n");
                        }
                    }
                } else if (tokens.length == 3) {
                    String property = tokens[2].toLowerCase();
                    if (properties.contains(property) || (property.charAt(0) == '-'
                            && properties.contains(property.substring(1)))) {
                        number = Long.parseLong(tokens[0]);
                        quantity = Integer.parseInt(tokens[1]);
                        if (quantity > 0 && number > 0) {
                            for (long i = number; quantity > 0; i++) {
                                List<String> processedProperties = new ArrayList<>();
                                processedProperties.add(property);
                                String propertyResult = checker.processPropertiesOrDie(i, processedProperties);
                                if (!propertyResult.isEmpty()) {
                                    System.out.println(propertyResult);
                                    quantity--;
                                }
                            }
                        } else {
                            if (number < 1) {
                                System.out.println("The first parameter should be a natural number or zero.\n");
                            }

                            if (quantity < 1) {
                                System.out.println("The second parameter should be a natural number.\n");
                            }
                        }
                    } else {
                        System.out.printf("\nThe property [%s] is wrong.\n", property.toUpperCase());
                        System.out.println("Available properties:");
                        System.out.println("[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
                        System.out.println();
                    }
                } else if (tokens.length >= 4) {
                    List<String> processedProperties = new ArrayList<>();
                    List<String> invalidProperties = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i++) {
                        String property = tokens[i].toLowerCase();
                        if (!properties.contains(property)) {
                            if (property.startsWith("-")) {
                                property = property.substring(1);
                                if (!properties.contains(property)) {
                                    invalidProperties.add("-" + property);
                                } else {
                                    processedProperties.add("-" + property);
                                }
                            } else {
                                invalidProperties.add(property);
                            }
                        } else if (!processedProperties.contains(property)) {
                            processedProperties.add(property);
                        }
                    }

                    if (invalidProperties.isEmpty()) {
                        boolean checkedMutualExclusiveProps = false;
                        for (Map.Entry<String, String> entry : mutualExclusiveProps.entrySet()) {
                            if (processedProperties.contains(entry.getKey()) &&
                                    processedProperties.contains(entry.getValue())) {
                                System.out.print("The request contains mutually exclusive properties: [");
                                System.out.printf("%s, %s]\n", entry.getKey().toUpperCase(),
                                        entry.getValue().toUpperCase());
                                System.out.println("There are no numbers with these properties.");
                                checkedMutualExclusiveProps = true;
                                break;
                            }
                        }

                        if (checkedMutualExclusiveProps) {
                            continue;
                        }

                        for (Map.Entry<String, String> entry : sameExclusiveProps.entrySet()) {
                            if (processedProperties.contains(entry.getKey()) &&
                                    processedProperties.contains(entry.getValue())) {
                                System.out.print("The request contains mutually exclusive properties: [");
                                System.out.printf("%s, %s]\n", entry.getKey().toUpperCase(),
                                        entry.getValue().toUpperCase());
                                System.out.println("There are no numbers with these properties.");
                                checkedMutualExclusiveProps = true;
                                break;
                            }
                        }

                        if (checkedMutualExclusiveProps) {
                            continue;
                        }

                        number = Long.parseLong(tokens[0]);
                        quantity = Integer.parseInt(tokens[1]);
                        if (quantity > 0 && number > 0) {
                            for (long i = number; quantity > 0; i++) {
                                String propertyResult = checker.processPropertiesOrDie(i, processedProperties);
                                if (!propertyResult.isEmpty()) {
                                    System.out.println(propertyResult);
                                    quantity--;
                                }
                            }
                        } else {
                            if (number < 1) {
                                System.out.println("The first parameter should be a natural number or zero.\n");
                            }

                            if (quantity < 1) {
                                System.out.println("The second parameter should be a natural number.\n");
                            }
                        }
                    } else if (invalidProperties.size() > 1) {
                        StringBuilder sb = new StringBuilder("\nThe properties [");
                        for (String property : invalidProperties) {
                            sb.append(String.format("%s, ", property));
                        }
                        int index = sb.lastIndexOf(", ");
                        sb.delete(index, index + 1);
                        sb.append("] are wrong.\n");
                        sb.append("Available properties:\n");
                        sb.append("[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n");
                        System.out.printf(sb.toString());
                        System.out.printf("%s\n", availablePropertiesStr);
                    } else if (invalidProperties.size() == 1) {
                        System.out.printf("\nThe property [%s] is wrong.\n", invalidProperties.get(0).toUpperCase());
                        System.out.println("Available properties:");
                        System.out.println("[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
                        System.out.printf("%s\n", availablePropertiesStr);
                    }
                }
            }
        } while (number != 0);

        System.out.println();
        System.out.println("Goodbye!");
    }
}

class NumberChecker {
    public String processOneOfSeveralNumbers(long number) {
        return processPropertiesOrDie(number, new ArrayList<>());
    }

    public String processPropertiesOrDie(long number, List<String> processedProperties) {
        List<String> properties = Arrays.asList("buzz",
                "duck", "palindromic",
                "gapful", "spy",
                "square", "sunny", "jumping",
                "even", "odd", "sad", "happy");

        for (String property : processedProperties) {
            if (!validatePropertyOfNumber(number, property)) {
                return "";
            }
        }

        StringBuilder sb = new StringBuilder(String.format("%d is ", number));
        for (String property : properties) {
            switch (property) {
                case "buzz":
                    if (isBuzzNumber(number)) {
                        sb.append("buzz, ");
                    }
                    break;
                case "duck":
                    if (isDuckNumber(number)) {
                        sb.append("duck, ");
                    }
                    break;
                case "palindromic":
                    if (isPalindromic(number)) {
                        sb.append("palindromic, ");
                    }
                    break;
                case "gapful":
                    if (isGapfulNumber(number)) {
                        sb.append("gapful, ");
                    }
                    break;
                case "spy":
                    if (isSpyNumber(number)) {
                        sb.append("spy, ");
                    }
                    break;
                case "square":
                    if (isSquareNumber(number)) {
                        sb.append("square, ");
                    }
                    break;
                case "sunny":
                    if (isSquareNumber(number + 1)) {
                        sb.append("sunny, ");
                    }
                    break;
                case "jumping":
                    if (isJumpingNumber(number)) {
                        sb.append("jumping, ");
                    }
                    break;
                case "even":
                    if (isEvenNumber(number)) {
                        sb.append("even, ");
                    }
                    break;
                case "odd":
                    if (isOddNumber(number)) {
                        sb.append("odd, ");
                    }
                    break;
                case "happy":
                    if (isHappyNumber(number)) {
                        sb.append("happy");
                    }
                    break;
                case "sad":
                    if (isSadNumber(number)) {
                        sb.append("sad");
                    }
                    break;
                default:
                    break;
            }
        }

        return sb.toString();
    }

    public void processANumber(long number) {
        if (number < 0) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else if (number > 0) {
            System.out.printf("Properties of %d\n", number);
            System.out.printf("        buzz: %b\n", isBuzzNumber(number));
            System.out.printf("        duck: %b\n", isDuckNumber(number));
            System.out.printf(" palindromic: %b\n", isPalindromic(number));
            System.out.printf("      gapful: %b\n", isGapfulNumber(number));
            System.out.printf("         spy: %b\n", isSpyNumber(number));
            System.out.printf("      square: %b\n", isSquareNumber(number));
            System.out.printf("       sunny: %b\n", isSquareNumber(number + 1));
            System.out.printf("     jumping: %b\n", isJumpingNumber(number));
            System.out.printf("        even: %b\n", isEvenNumber(number));
            System.out.printf("         odd: %b\n", isOddNumber(number));
            System.out.printf("       happy: %b\n", isHappyNumber(number));
            System.out.printf("         sad: %b\n", isSadNumber(number));
        }
    }

    public boolean validatePropertyOfNumber(long number, String property) {
        boolean validProperty = false;

        switch (property) {
            case "even":
                validProperty = isEvenNumber(number);
                break;
            case "-even":
                validProperty = !isEvenNumber(number);
                break;
            case "-odd":
                validProperty = !isOddNumber(number);
                break;
            case "odd":
                validProperty = isOddNumber(number);
                break;
            case "buzz":
                validProperty = isBuzzNumber(number);
                break;
            case "-buzz":
                validProperty = !isBuzzNumber(number);
                break;
            case "duck":
                validProperty = isDuckNumber(number);
                break;
            case "-duck":
                validProperty = !isDuckNumber(number);
                break;
            case "palindromic":
                validProperty = isPalindromic(number);
                break;
            case "-palindromic":
                validProperty = !isPalindromic(number);
                break;
            case "gapful":
                validProperty = isGapfulNumber(number);
                break;
            case "-gapful":
                validProperty = !isGapfulNumber(number);
                break;
            case "spy":
                validProperty = isSpyNumber(number);
                break;
            case "-spy":
                validProperty = !isSpyNumber(number);
                break;
            case "square":
                validProperty = isSquareNumber(number);
                break;
            case "-square":
                validProperty = !isSquareNumber(number);
                break;
            case "sunny":
                validProperty = isSquareNumber(number + 1);
                break;
            case "-sunny":
                validProperty = !isSquareNumber(number + 1);
                break;
            case "jumping":
                validProperty = isJumpingNumber(number);
                break;
            case "-jumping":
                validProperty = !isJumpingNumber(number);
                break;
            case "sad":
                validProperty = isSadNumber(number);
                break;
            case "-sad":
                validProperty = !isSadNumber(number);
                break;
            case "happy":
                validProperty = isHappyNumber(number);
                break;
            case "-happy":
                validProperty = !isHappyNumber(number);
                break;
            default:
                break;
        }

        return validProperty;
    }

    public int length(long number) {
        return (int) Math.log10(number) + 1;
    }

    public boolean isDuckNumber(long number) {
        while (number > 0) {
            if (number % 10 == 0) {
                return true;
            }
            number /= 10;
        }

        return false;
    }

    public boolean isBuzzNumber(long number) {
        return number % 7 == 0 || number % 10 == 7;
    }

    public boolean isPalindromic(long number) {
        if (number % 10 == 0) {
            return false;
        }

        long endNum = 0;
        while (endNum < number) {
            endNum = endNum * 10 + number % 10;
            if (endNum < number) {
                number /= 10;
            }
        }

        return endNum == number;
    }

    public boolean isEvenNumber(long number) {
        return number % 2 == 0;
    }

    public boolean isOddNumber(long number) {
        return number % 2 == 1;
    }

    public boolean isHappyNumber(long number) {
        String strNum;
        long finalNum = number;
        long newNum;
        do {
            strNum = String.valueOf(finalNum);
            newNum = 0;
            for (char c : strNum.toCharArray()) {
                int n = c - '0';
                newNum += n * n;
            }
            finalNum = newNum;
        } while (finalNum != number && finalNum != 1 && finalNum != 4);

        return finalNum == 1;
    }

    public boolean isSadNumber(long number) {
        return !isHappyNumber(number);
    }

    public boolean isGapfulNumber(long number) {
        if (number < 100) {
            return false;
        }

        long workNumber = number % 10 + (int) (number / Math.pow(10, length(number) - 1)) * 10;

        return number % workNumber == 0;
    }

    public boolean isSquareNumber(long number) {
        return Math.sqrt(number) == Math.floor(Math.sqrt(number));
    }

    public boolean isSpyNumber(long number) {
        long summer = 0;
        long multiplier = 1;
        int value;
        while (number > 0) {
            value = (int) number % 10;
            if (value == 0) {
                return false;
            }
            number /= 10;
            summer += value;
            if (value == 1) {
                continue;
            }
            multiplier *= value;
        }

        return multiplier == summer;
    }

    public boolean isJumpingNumber(long number) {
        long last = number % 10;
        number /= 10;
        long checker;
        while (number > 0) {
            checker = number % 10;
            if (checker != last - 1 && checker != last + 1) {
                return false;
            }
            last = checker;
            number /= 10;
        }

        return true;
    }
}
