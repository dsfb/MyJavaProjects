package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static char[][] field = {{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'}};

    public static List<int[]> validPositions(int i, int j) {
        List<int[]> result = new ArrayList<>();
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x == i && y == j) {
                    continue;
                }

                if (x < 0 || x >= 9) {
                    continue;
                }

                if (y < 0 || y >= 9) {
                    continue;
                }

                result.add(new int[]{x, y});
            }
        }

        return result;
    }

    public static void processValidPositions(List<int[]> validPositions) {
        for (int[] position : validPositions) {
            char situation = field[position[0]][position[1]];
            if (situation == '.') {
                field[position[0]][position[1]] = '1';
            } else if (situation != 'X') {
                field[position[0]][position[1]]++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        int mineQuantity = sc.nextInt();
        Random generator = new Random(System.currentTimeMillis());
        for (int i = 0; i < mineQuantity;) {
            int x = generator.nextInt(9);
            int y = generator.nextInt(9);
            if (field[x][y] == '.') {
                field[x][y] = 'X';
                i++;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field[i][j] == 'X') {
                    List<int[]> validPositions = validPositions(i, j);
                    processValidPositions(validPositions);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char[] chars : field) {
            sb.setLength(0);
            for (char aChar : chars) {
                sb.append(aChar);
            }
            System.out.println(sb);
        }
    }
}
