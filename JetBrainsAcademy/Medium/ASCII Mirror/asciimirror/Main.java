package asciimirror;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static String mirrorString(String input) {
        StringBuilder result = new StringBuilder();
        char dc = 0;
        for (char c : input.toCharArray()) {
            switch (c) {
            case '[':
                dc = ']';
                break;
            case ']':
                dc = '[';
                break;
            case '(':
                dc = ')';
                break;
            case ')':
                dc = '(';
                break;
            case '\\':
                dc = '/';
                break;
            case '/':
                dc = '\\';
                break;
            case '<':
                dc = '>';
                break;
            case '>':
                dc = '<';
                break;
            default:
                dc = c;
            }
            result.insert(0, dc);
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        int maxLength = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the file path:");
        String filePath = scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                list.add(line);
                maxLength = line.length() > maxLength ? line.length() : maxLength;
                line = br.readLine();
            }

            for (int i = 0; i < list.size(); i++) {
                while (list.get(i).length() < maxLength) {
                    list.set(i, list.get(i) + " ");
                }
                System.out.printf("%s | %s\n", list.get(i), 
                		mirrorString(list.get(i)));
            }
            
            scanner.close();
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        }
    }
}