package cz.cuni.mff.sadovsm.sudoku;



import java.util.Random;
import java.util.List;
import java.util.ArrayList;


public class SudokuGenerator {

    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int EMPTY_CELL = 0;

    /**
     * Generates a full grid
     * @return the generated Sudoku grid
     */
    private static int[][] generateSudoku() {
        int[][] board = new int[SIZE][SIZE];
        fillDiagonalSubgrids(board);
        fillRemaining(board, 0, SUBGRID_SIZE);
        return board;
    }
    /**
     * Fills the diagonal subgrids of the Sudoku grid
     * @param board the Sudoku grid
     */
    private static void fillDiagonalSubgrids(int[][] board) {
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            fillSubgrid(board, i, i);
        }
    }

    /**
     * Fills a subgrid  of the Sudoku grid
     * @param board the Sudoku grid
     * @param row the row of the subgrid
     * @param col the column of the subgrid
     */
    private static void fillSubgrid(int[][] board, int row, int col) {
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(values);

        for (int value : values) {
            if (isSafe(board, row, col, value)) {
                board[row][col] = value;
                if (fillRemaining(board, row, col + 1)) {
                    return;
                }
                board[row][col] = EMPTY_CELL;
            }
        }
    }

    /**
     * Fills the remaining cells of the Sudoku grid
     * @param board the Sudoku grid
     * @param row the row of the cell
     * @param col the column of the cell
     * @return true if the grid is filled, false otherwise
     */
    private static boolean fillRemaining(int[][] board, int row, int col) {
        if (row == SIZE - 1 && col == SIZE) {
            return true;
        }
        if (col == SIZE) {
            row++;
            col = 0;
        }
        if (board[row][col] != EMPTY_CELL) {
            return fillRemaining(board, row, col + 1);
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (fillRemaining(board, row, col + 1)) {
                    return true;
                }
                board[row][col] = EMPTY_CELL;
            }
        }
        return false;
    }

    /**
     * Checks if it's possible to place a number in a cell
     * @param board the Sudoku grid
     * @param row the row of the cell
     * @param col the column of the cell
     * @param num the number to be placed
     * @return true if it's safe to place the number, false otherwise
     */
    private static boolean isSafe(int[][] board, int row, int col, int num) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 9);
        }
        return SudokuValidator.isValidSudoku(SudokuSolveHinter.fill(row, col, copy, num));
    }

    /**
     * Shuffles an array
     * @param array the array to be shuffled
     */
    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Generates a Sudoku grid with a given number of empty cells or maximum number of empty cells
     * @param emptyCells the number of empty cells
     * @return the generated Sudoku grid
     */
    public static int[][] generateSudoku(int emptyCells) {
        int[][] board = generateSudoku();
        Random random = new Random();
        List<Integer> possible = new ArrayList<>();
        for(int i = 1; i <= SIZE*SIZE; i++) possible.add(i);
        int empty = 0;
        while (empty < emptyCells && !possible.isEmpty()) {

            int index = random.nextInt(possible.size());
            int position = possible.get(index);
            int row = (position - 1) / SIZE;
            int col = (position - 1) % SIZE;
            possible.remove(index);

            int temp = board[row][col];
            //cause we remove already selected we don't need to check if it's already empty
            board[row][col] = EMPTY_CELL;
            if(isSolvable(board)) {
                empty++;
            }
            else {
                board[row][col] = temp;
            }

        }
        return board;
    }



    /**
     * Checks if the Sudoku grid is solvable
     * @param grid the Sudoku grid
     * @return true if the grid is solvable, false otherwise
     */
    public static boolean isSolvable(int[][] grid) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, 9);
        }
        return SudokuSolveHinter.solve(copy);
    }

}
