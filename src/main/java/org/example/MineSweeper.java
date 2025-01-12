package org.example;

import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private int rowNumber;
    private int colNumber;
    private int size;
    private int[][] map;
    private int[][] board;
    private boolean game = true;

    private final Random rand = new Random();
    private final Scanner scan = new Scanner(System.in);

    public void run() {
        System.out.println("Mayin Tarlasi Oyununa Hoşgeldiniz!");
        System.out.println("Lütfen oynamak istediğiniz boyutlari giriniz:");
        System.out.print("Satir Sayisi: ");
        rowNumber = scan.nextInt();
        System.out.print("Sütun Sayisi: ");
        colNumber = scan.nextInt();

        if (rowNumber < 2 || colNumber < 2) {
            System.out.println("Oyun alani en az 2x2 olmalidir!");
            return;
        }

        size = rowNumber * colNumber;
        map = new int[rowNumber][colNumber];
        board = new int[rowNumber][colNumber];
        prepareGame();

        System.out.println("\nMayinlarin Konumu:");
        print(map);
        System.out.println("\nOyun Başladi!");

        while (game) {
            print(board);
            System.out.println("\nKoordinat giriniz (0-" + (rowNumber - 1) + " arasi)");
            System.out.print("Satir: ");
            int row = scan.nextInt() - 1;
            System.out.print("Sütun: ");
            int col = scan.nextInt() - 1;

            if (row < 0 || row >= rowNumber || col < 0 || col >= colNumber) {
                System.out.println("\nGeçersiz koordinat! Lütfen tekrar deneyin.");
                continue;
            }

            if (map[row][col] != -1) {
                checkMine(row, col);
                if (isWin()) {
                    System.out.println("\nTebrikler! Oyunu Kazandiniz!");
                    break;
                }
            } else {
                game = false;
                System.out.println("\nGame Over!");
            }
        }
    }

    private void checkMine(int row, int col) {
        if (map[row][col] == 0) {
            if ((col < colNumber - 1) && (map[row][col + 1] == -1)) {
                board[row][col]++;
            }
            if ((row < rowNumber - 1) && (map[row + 1][col] == -1)) {
                board[row][col]++;
            }
            if ((row > 0) && (map[row - 1][col] == -1)) {
                board[row][col]++;
            }
            if ((col > 0) && (map[row][col - 1] == -1)) {
                board[row][col]++;
            }
            if (board[row][col] == 0) {
                board[row][col] = -2;
            }
        }
    }

    private void prepareGame() {
        int randRow, randCol;
        int count = 0;
        while (count != (size / 4)) {
            randRow = rand.nextInt(rowNumber);
            randCol = rand.nextInt(colNumber);
            if (map[randRow][randCol] != -1) {
                map[randRow][randCol] = -1;
                count++;
            }
        }
    }

    private void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] >= 0) {
                    System.out.print(" ");
                }
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isWin() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] >= 0 || board[i][j] == -2) {
                    count++;
                }
            }
        }
        return count == (size - (size / 4));
    }
}