package encryptdecrypt;

public abstract class OperatorFactory {

    abstract Operator createOperator(Algorithm algorithm, String msg, int key, Operation operation, String inputFileName, String outputFileName);

    Operator getNewOperator(Algorithm algorithm, String msg, int key, Operation operation, String inputFileName, String outputFileName) {
        Operator operator = createOperator(algorithm, msg, key, operation, inputFileName, outputFileName);
        return operator;
    }
}
