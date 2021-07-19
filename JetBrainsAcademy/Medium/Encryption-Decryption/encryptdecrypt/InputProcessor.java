package encryptdecrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputProcessor {
    private Algorithm algorithm;
    private String msg;
    private int key;
    private Operation operation;
    private String inputFileName;
    private String outputFileName;

    public InputProcessor() {
        this.algorithm = Algorithm.SHIFT;
        this.msg = "";
        this.inputFileName = "";
        this.outputFileName = "";
        this.operation = Operation.ENCRYPT;
    }

    public void process(String[] args) {
        String argKey = "";
        String argValue = "";

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                argKey = args[i];
            } else {
                argValue = args[i];
                switch (argKey) {
                    case "-mode":
                        if (argValue.equals("enc")) {
                            // Nothing to do! ;)
                        } else if (argValue.equals("dec")) {
                            operation = Operation.DECRYPT;
                        }
                        break;
                    case "-key":
                        try {
                            key = Integer.parseInt(argValue);
                        } catch (NumberFormatException e) {
                        }
                        break;
                    case "-data":
                        msg = argValue;
                        break;
                    case "-in":
                        inputFileName = argValue;
                        break;
                    case "-out":
                        outputFileName = argValue;
                        break;
                    case "-alg":
                        if (argValue.equals("unicode")) {
                            algorithm = Algorithm.UNICODE;
                        } else if (argValue.equals("shift")) {
                            algorithm = Algorithm.SHIFT;
                        }
                    default:
                        break;
                }
            }
        }

        if (msg.isEmpty()) {
            if (!inputFileName.isEmpty()) {
                try (FileReader fr = new FileReader(inputFileName);
                     BufferedReader br = new BufferedReader(fr); ) {
                    msg = br.readLine();
                } catch (IOException e) {
                }
            }
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public String getMsg() {
        return msg;
    }

    public int getKey() {
        return key;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
