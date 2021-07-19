package encryptdecrypt;

public class UnicodeOperator extends Operator {
    public UnicodeOperator(String msg, int key, Operation operation, String inputFileName, String outputFileName) {
        super(msg, key, operation, inputFileName, outputFileName);
    }

    @Override
    public String operate() {
        if (this.operation.equals(Operation.DECRYPT)) {
            this.key *= -1;
        }

        StringBuilder result = new StringBuilder();
        for (char c : msg.toCharArray()) {
            result.append((char)(c + key));
        }

        return result.toString();
    }
}
