import java.io.*;

/**
 * Created by anchung on 12/26/16.
 */
public class GameMain {
    public static void main(String[] args) {

        long start = System.nanoTime();
        URLSudokuReader sreader = new URLSudokuReader("http://show.websudoku.com");
        try {
            sreader.read();
        } catch (IOException e) {
            System.out.println("Invalid URL.");
            return;
        }

        URLParser parser = new URLParser();
        try {
            parser.inFileName("sudokuEasy1.html");
        } catch (IOException e) {
            System.out.println("Invalid input file");
            return;
        }

        parser.parseIt();

        Sudoku sudoku = new Sudoku();
        try {
            sudoku.boardData(parser.data());
        } catch (SudoException e) {
            System.out.println(e.getMessage());
            return;
        }

        sudoku.play();
        long elapsedTime = (System.nanoTime() - start) / 1000000;
        System.out.println("Screen scraped, parsed, and puzzle solved in " + elapsedTime + " msecs.");

//      sudoku.showCell(8, 0);
    }
}