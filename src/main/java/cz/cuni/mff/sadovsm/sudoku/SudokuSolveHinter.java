package cz.cuni.mff.sadovsm.sudoku;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuSolveHinter {
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;


    /**
     * Returns an array of possible values for a cell in the grid
     * @param grid the Sudoku grid
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return an array of possible values for the cell
     */
    public static int[] possible(int[][] grid, int x, int y) {
        List<Integer> possible = new ArrayList<>();
        int[][] copy = copyGrid(grid);
        if(copy[x][y] != EMPTY_CELL) {
            return new int[0];
        }
        for (int i = 1; i <= SIZE; i++) {
            if (SudokuValidator.isValidSudoku(fill(x, y, copy, i))) {
                possible.add(i);
            }
        }
        return possible.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Returns a hint for the next move
     * @param grid the Sudoku grid
     * @return an array of three integers representing the coordinates of the hint and the value
     */
    public static int[] posHint(int[][] grid) {
        int[] hint = new int[] {-1,-1,-1};
        int[][] copy = copyGrid(grid);

        if(!solve(copy)) {
            return hint;
        }
        copy = copyGrid(grid);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int[] possible = possible(copy, i, j);
                if (possible.length == 1) {
                    hint[0] = i;
                    hint[1] = j;
                    hint[2] = possible[0];
                    return hint;
                }
            }
        }
        return advancedHint(copy);
    }

    /**
     * Returns a hint for the next move
     * @param grid the Sudoku grid
     * @return an array of three integers representing the coordinates of the hint and value
     * */
    private static int[] advancedHint(int[][] grid){
        //this is called from hint so it is solvable

        //to choose value from whole grid
        Random random = new Random();
        int maxBound = 81;   //all positions
        int[] chosenOne = findEmptyLocation(grid, random.nextInt(maxBound--));
        while(chosenOne[0] == -1){
            chosenOne = findEmptyLocation(grid, random.nextInt(maxBound--)); //decrease it for sooner response
        }
        int[] possibleForChosen = possible(grid, chosenOne[0],chosenOne[1]);
        for(int possibility : possibleForChosen){
            if(SudokuGenerator.isSolvable(fill(chosenOne[0],chosenOne[1],grid, possibility))){
                return new int[]{chosenOne[0], chosenOne[1], possibility};
            }
        }
        return new int[] {-1,-1,-1};
    }

    /**
     * Solves the Sudoku grid
     * @param grid the Sudoku grid
     * @return true if the Sudoku is solved, false otherwise
     */
    public static boolean solve(int[][] grid) {
        int[] emptyCell = findEmptyLocation(grid);
        int row = emptyCell[0];
        int col = emptyCell[1];

        if (row == -1 && col == -1) {
            // If no empty cells left, the Sudoku is solved
            return true;
        }
        for (int num = 1; num <= SIZE; num++) {
            if (SudokuValidator.isValidSudoku(fill(row, col, grid, num))) {

                grid[row][col] = num;
                if (solve(grid)) {
                    return true;
                }
                // Backtrack if the current configuration doesn't lead to a solution
                grid[row][col] = EMPTY_CELL;
            }
        }

        // No solution found
        return false;
    }


    /**
     * Finds the first empty cell in the grid
     * @param grid the Sudoku grid
     * @return an array of two integers representing the coordinates of the empty cell
     */
    private static int[] findEmptyLocation(int[][] grid, int k) {
        int[] location = {-1, -1};
        int ord = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == EMPTY_CELL && k == ord) {
                    location[0] = row;
                    location[1] = col;
                    return location;
                } else if(grid[row][col] == EMPTY_CELL) ord++;
            }
        }
        return location;
    }

    private static int[] findEmptyLocation(int[][] grid) {
        return findEmptyLocation(grid, 0);
    }

    /** Fills the cell with a value
     * @param row the row of the cell
     * @param col the column of the cell
     * @param grid the Sudoku grid
     * @param val the value to fill the cell with
     * @return the updated Sudoku grid
     */
    public static int[][] fill(int row, int col, int[][] grid, int val) {
        grid[row][col] = val;
        return grid;
    }


    /**
     * Copies the Sudoku grid
     * @param grid the Sudoku grid
     * @return a copy of the Sudoku grid
     */
    private static int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }
}
