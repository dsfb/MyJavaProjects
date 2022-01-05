package encryptdecrypt;

public class ShiftOperator extends Operator {
    private char[] lowerAlphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    private char[] upperAlphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final int ALPHABET_LENGTH = 26;

    public ShiftOperator(String msg, int key, Operation operation, String inputFileName, String outputFileName) {
        super(msg, key, operation, inputFileName, outputFileName);
    }

    @Override
    public String operate() {
        if (this.operation.equals(Operation.DECRYPT)) {
            this.key *= -1;
        }

        char[] msgArr = this.msg.toCharArray();
        StringBuilder sb = new StringBuilder();
        int length = msgArr.length;
        for (int i = 0; i < length; i++) {
            char ch = msgArr[i];
            if (Character.isLetter(ch)) {
                int index = 0;
                if (Character.isUpperCase(ch)) {
                    index = (int) (ch - 'A');
                } else {
                    index = (int) (ch - 'a');
                }

                index += key;
                if (index > ALPHABET_LENGTH) {
                    index %= ALPHABET_LENGTH;
                } else if (index < 0) {
                    index += ALPHABET_LENGTH;
                }

                if (Character.isUpperCase(ch)) {
                    sb.append(this.upperAlphabet[index]);
                } else {
                    sb.append(this.lowerAlphabet[index]);
                }
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
