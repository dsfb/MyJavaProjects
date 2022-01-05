package numbers;

import java.util.*;

public class Main {
    public static int length(long number) {
        return (int) Math.log10(number) + 1;
    }

    public static boolean isDuckNumber(long number) {
        while (number > 0) {
            if (number % 10 == 0) {
                return true;
            }
            number /= 10;
        }

        return false;
    }

    public static boolean isHappyNumber(long number) {
        if (number == 1L) {
            return true;
        }

        long checker = number;
        long summer;
        long digit;
        do {
            summer = 0;
            while (checker > 0) {
                digit = checker % 10;
                summer += digit * digit;
                checker /= 10;
            }
            checker = summer;
        } while (checker != number && checker != 1 && checker != 4);

        return checker == 1;
    }

    public static boolean isSadNumber(long number) {
        return !isHappyNumber(number);
    }

    public static boolean isBuzzNumber(long number) {
        return number % 7 == 0 || number % 10 == 7;
    }

    public static boolean isPalindromic(long number) {
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

    public static boolean isEvenNumber(long number) {
        return number % 2 == 0;
    }

    public static boolean isOddNumber(long number) {
        return number % 2 == 1;
    }

    public static void processANumber(long number) {
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

    public static boolean isGapfulNumber(long number) {
        if (number < 100) {
            return false;
        }

        long workNumber = number % 10 + (int) (number / Math.pow(10, length(number) - 1)) * 10;

        return number % workNumber == 0;
    }

    public static boolean isSquareNumber(long number) {
        return Math.sqrt(number) == Math.floor(Math.sqrt(number));
    }

    public static boolean isSpyNumber(long number) {
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

    public static boolean isJumpingNumber(long number) {
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

    public static String processPropertiesOrDie(long number, List<String> processedProperties) {
        List<String> properties = Arrays.asList("buzz",
                "duck", "palindromic",
                "gapful", "spy",
                "square", "sunny", "jumping",
                "happy", "sad", "even", "odd");

        for (String property : new HashSet<String>(processedProperties)) {
            if (!validatePropertyOfNumber(number, property)) {
                return "";
            }

            if (property.startsWith("-")) {
                properties.remove(property.substring(1));
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
                        sb.append("even");
                    }
                    break;
                case "odd":
                    if (isOddNumber(number)) {
                        sb.append("odd");
                    }
                    break;
                case "happy":
                    if (isHappyNumber(number)) {
                        sb.append("happy, ");
                    }
                    break;
                case "sad":
                    if (isSadNumber(number)) {
                        sb.append("sad, ");
                    }
                    break;
                default:
                    break;
            }
        }

        return sb.toString();
    }

    public static String processOneOfSeveralNumbers(long number) {
        return processPropertiesOrDie(number, new ArrayList<>());
    }

    public static boolean validatePropertyOfNumber(long number, String property) {
        boolean validProperty = false;
        boolean minus = property.startsWith("-");
        if (minus) {
            property = property.substring(1);
        }
        switch (property) {
            case "even":
                validProperty = isEvenNumber(number);
                break;
            case "odd":
                validProperty = isOddNumber(number);
                break;
            case "buzz":
                validProperty = isBuzzNumber(number);
                break;
            case "duck":
                validProperty = isDuckNumber(number);
                break;
            case "palindromic":
                validProperty = isPalindromic(number);
                break;
            case "gapful":
                validProperty = isGapfulNumber(number);
                break;
            case "spy":
                validProperty = isSpyNumber(number);
                break;
            case "square":
                validProperty = isSquareNumber(number);
                break;
            case "sunny":
                validProperty = isSquareNumber(number + 1);
                break;
            case "jumping":
                validProperty = isJumpingNumber(number);
                break;
            case "happy":
                validProperty = isHappyNumber(number);
                break;
            case "sad":
                validProperty = isSadNumber(number);
                break;
            default:
                break;
        }

        if (validProperty && minus) {
            validProperty = false;
        }

        return validProperty;
    }

    private static final String availableProps = "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n";

    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n");
        System.out.println("- enter 0 to exit.\n");
        long number = -1;
        int quantity;
        Scanner sc = new Scanner(System.in);
        String input;
        boolean singleCase;
        List<String> properties = Arrays.asList("even", "odd", "buzz",
                "duck", "palindromic", "gapful", "spy", "jumping",
                "square", "sunny", "happy", "sad", "-even", "-odd", "-buzz",
                "-duck", "-palindromic", "-gapful", "-spy", "-jumping",
                "-square", "-sunny", "-happy", "-sad");
        do {
            System.out.print("Enter a request: ");
            input = sc.nextLine();
            singleCase = !input.contains(" ");
            if (singleCase) {
                number = Long.parseLong(input);
                if (number != 0) {
                    processANumber(number);
                }
            } else {
                String[] tokens = input.split(" ");
                if (tokens.length == 2) {
                    number = Long.parseLong(tokens[0]);
                    quantity = Integer.parseInt(tokens[1]);
                    if (quantity > 0 && number > 0) {
                        for (long i = number; i < number + quantity; i++) {
                            System.out.println(processOneOfSeveralNumbers(i));
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
                    if (properties.contains(property)) {
                        number = Long.parseLong(tokens[0]);
                        quantity = Integer.parseInt(tokens[1]);
                        if (quantity > 0 && number > 0) {
                            for (long i = number; quantity > 0; i++) {
                                List<String> processedProperties = new ArrayList<>();
                                processedProperties.add(property);
                                String propertyResult = processPropertiesOrDie(i, processedProperties);
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
                        System.out.printf("\nThe property [%s] is wrong\n", property.toUpperCase());
                        System.out.println(availableProps);
                    }
                } else if (tokens.length >= 4) {
                    List<String> processedProperties = new ArrayList<>();
                    List<String> invalidProperties = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i++) {
                        String property = tokens[i].toLowerCase();
                        if (!properties.contains(property)) {
                            invalidProperties.add(property);
                        } else {
                            processedProperties.add(property);
                        }
                    }

                    if (invalidProperties.isEmpty()) {
                        boolean oddEven = processedProperties.contains("odd") && processedProperties.contains("even");

                        if (oddEven) {
                            System.out.println("The request contains mutually exclusive properties: [ODD, EVEN]");
                            System.out.println("There are no numbers with these properties.");
                            continue;
                        }

                        boolean squareSunny = processedProperties.contains("square") && processedProperties.contains("sunny");

                        if (squareSunny) {
                            System.out.println("The request contains mutually exclusive properties: [SQUARE, SUNNY]");
                            System.out.println("There are no numbers with these properties.");
                            continue;
                        }

                        boolean spyDuck = processedProperties.contains("spy") && processedProperties.contains("duck");

                        if (spyDuck) {
                            System.out.println("The request contains mutually exclusive properties: [SPY, DUCK]");
                            System.out.println("There are no numbers with these properties.");
                            continue;
                        }

                        number = Long.parseLong(tokens[0]);
                        quantity = Integer.parseInt(tokens[1]);
                        if (quantity > 0 && number > 0) {
                            for (long i = number; quantity > 0; i++) {
                                String propertyResult = processPropertiesOrDie(i, processedProperties);
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
                        System.out.printf(sb.toString());
                        System.out.println(availableProps);
                    } else if (invalidProperties.size() == 1) {
                        System.out.printf("\nThe property [%s] is wrong.\n", invalidProperties.get(0).toUpperCase());
                        System.out.println(availableProps);
                    }
                }
            }
        } while (number != 0);

        System.out.println();
        System.out.println("Goodbye!");
    }
}
