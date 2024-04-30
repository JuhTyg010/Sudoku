package cz.cuni.mff.sadovsm.sudoku;

public class SudokuValidator {

    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int EMPTY_CELL = 0;

    /**
     * Checks if the Sudoku grid is valid
     * @param grid the Sudoku grid
     * @return true if the grid is valid, false otherwise
     */
    public static boolean isValidSudoku(int[][] grid) {
        // Check rows
        for (int i = 0; i < SIZE; i++) {
            if (!isValidGroup(grid[i])) {
                return false;
            }
        }

        // Check columns
        for (int j = 0; j < SIZE; j++) {
            int[] column = new int[SIZE];
            for (int i = 0; i < SIZE; i++) {
                column[i] = grid[i][j];
            }
            if (!isValidGroup(column)) {
                return false;
            }
        }

        // Check 3x3 squares
        for(int startRow = 0; startRow < SIZE; startRow += SUBGRID_SIZE) {
            for(int startCol = 0; startCol < SIZE; startCol += SUBGRID_SIZE) {
                int[] square = new int[SIZE];
                int squareIndex = 0;
                for(int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
                    for(int j = startCol; j < startCol + SUBGRID_SIZE; j++) {
                        square[squareIndex++] = grid[i][j];
                    }
                }
                if (!isValidGroup(square)) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Checks if a group of numbers is valid
     * @param group the group of numbers
     * @return true if the group is valid, false otherwise
     */
    public static boolean isValidGroup(int[] group) {
        boolean[] seen = new boolean[10]; // 10 because Sudoku digits are from 1 to 9
        for (int num : group) {
            if (num != EMPTY_CELL) {
                if (seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }
        return true;
    }
}
