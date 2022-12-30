package carsharing.manager;

import java.util.Scanner;

public class KeyboardReader {
    private Scanner sc;

    public KeyboardReader() {
        sc = new Scanner(System.in);
    }

    public String readLine() {
        return this.sc.nextLine();
    }

    public int readInteger() {
        String line = this.readLine();
        return Integer.parseInt(line);
    }

    public double readDouble() {
        String line = this.readLine();
        return Double.parseDouble(line);
    }

    public void close() {
        this.sc.close();
    }
}
