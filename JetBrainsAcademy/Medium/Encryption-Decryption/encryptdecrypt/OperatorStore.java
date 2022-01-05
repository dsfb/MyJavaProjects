package encryptdecrypt;

public class OperatorStore extends OperatorFactory {
    @Override
    Operator createOperator(Algorithm algorithm, String msg, int key, Operation operation, String inputFileName, String outputFileName) {
        switch (algorithm) {
            case SHIFT:
                return new ShiftOperator(msg, key, operation, inputFileName, outputFileName);
            case UNICODE:
                return new UnicodeOperator(msg, key, operation, inputFileName, outputFileName);
            default:
                return null;
        }
    }
}
