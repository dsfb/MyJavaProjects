package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        InputProcessor inputProcessor = new InputProcessor();
        inputProcessor.process(args);
        Algorithm algorithm = inputProcessor.getAlgorithm();
        String msg = inputProcessor.getMsg();
        int key = inputProcessor.getKey();
        Operation operation = inputProcessor.getOperation();
        String inputFileName = inputProcessor.getInputFileName();
        String outputFileName = inputProcessor.getOutputFileName();
        OperatorFactory factory = new OperatorStore();
        Operator operator = factory.createOperator(algorithm, msg, key, operation, inputFileName, outputFileName);
        operator.processOutput();
    }
}
