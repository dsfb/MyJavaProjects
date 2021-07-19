package encryptdecrypt;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Operator {
    protected Operation operation = Operation.ENCRYPT;
    protected int key = 0;
    protected String msg = "";
    private String inputFileName = "";
    private String outputFileName = "";

    public Operator(String msg, int key, Operation operation, String inputFileName, String outputFileName) {
        this.msg = msg;
        this.key = key;
        this.operation = operation;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public abstract String operate();

    public void processOutput() {
        String result = this.operate();
        if (outputFileName.isEmpty()) {
            System.out.println(result);
        } else {
            try (FileWriter fw = new FileWriter(outputFileName); ) {
                fw.write(result);
            } catch (IOException e) {
            }
        }
    }
}
