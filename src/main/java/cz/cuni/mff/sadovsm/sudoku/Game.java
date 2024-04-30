package cz.cuni.mff.sadovsm.sudoku;

import cz.cuni.mff.sadovsm.ioHandler.*;

import java.util.Objects;
import java.util.Stack;

public class Game {

    private static final int EMPTY_CELL = 0;
    int[][] grid;
    Stack<int[]> moves;
    int toComplete;
    boolean isUndo;
    Reader reader;


    /**
     * Initializes the game
     */
    public Game() {
        restart();
    }


    /**
     * Checks if the game is finished
     * @return boolean
     */
    public boolean isFinished(){
        return toComplete == 0;
    }

    /**
     * Returns the hint for the next move with the coordinates and the value
     * @return an array of three integers representing the hint
     */
    public int[] getHint(){
        return SudokuSolveHinter.posHint(grid);
    }

    /**
     * Returns an array of possible values for a cell in the grid
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return an array of possible values for the cell
     */
    public int[] getPossible(int x, int y){
        return SudokuSolveHinter.possible(grid, x, y);
    }

    /**
     * Fills a cell with a value
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @param value the value to fill the cell with
     * @return a string representing the result of the move
     */
    public String fill(int x, int y, int value){
        if(grid[x][y] == EMPTY_CELL){
            grid[x][y] = value;
            if(SudokuValidator.isValidSudoku(grid)){
                moves.push(new int[]{x, y});

                toComplete--;
                String cell = (char) (y + 'A') + "" + (x + 1);
                return "Cell "+ cell + " filled with " + value;
            } else {
                grid[x][y] = EMPTY_CELL;
                return "Invalid move";
            }
        }
        return "Cell is not empty";
    }

    /**
     * Auto-fills the cell
     * @return a string representing the result of the move
     */
    public String autoFill(){
        int[] hint = SudokuSolveHinter.posHint(grid);
        if(hint[0] == -1){
            return "No hint available";
        }
        return fill(hint[0], hint[1], hint[2]);
    }

    /**
     * Undoes the last move
     * @return boolean if the move was undone
     */
    public boolean undo(){
        if(!moves.empty()){
            int[] lastMove = moves.pop();
            grid[lastMove[0]][lastMove[1]] = EMPTY_CELL;
            toComplete++;
            return true;
        }
        return false;
    }

    /**
     * Solves the Sudoku
     * @return boolean if the Sudoku is solved
     */
    public boolean solve(){
        if(SudokuSolveHinter.solve(grid)){
            toComplete = 0;
            return true;
        }
        return false;
    }

    /**
     * Checks if the Sudoku is solvable
     * @return boolean if the Sudoku is solvable
     */
    public boolean isSolvable(){
        return SudokuGenerator.isSolvable(grid);
    }

    /**
     * Restarts the game
     */
    public void restart(){

        this.isUndo = false;
        this.reader = new Reader();
        this.moves = new Stack<>();


        int difficultyLevel = 0;
        boolean valid = false;

        while (!valid) {
            Writer.writeln("Welcome to Sudoku game");
            Writer.writeln("Choose difficulty level: easy(30), medium(50), hard(65)");
            String difficulty = Reader.readln();

            switch (difficulty != null ? difficulty : "null") {
                case "easy" -> {
                    difficultyLevel = 30;
                    valid = true;
                }
                case "medium" -> {
                    difficultyLevel = 50;
                    valid = true;
                }
                case "hard" -> {
                    difficultyLevel = 65;
                    valid = true;
                }
                default -> {
                    if(Objects.equals(difficulty, "null") || difficulty.equals("")) {
                        Writer.writeln("Non data, medium difficulty level chosen");
                        difficultyLevel = 50;
                        valid = true;
                    }
                    else {
                        Writer.writeln("Invalid difficulty level, choose again");
                    }
                }
            }

        }
        this.grid = SudokuGenerator.generateSudoku(difficultyLevel);
        this.toComplete = emptyCells();
    }

    /**
     * Getter for the grid
     * @return the grid
     */
    public int[][] getGrid(){
        return grid;
    }

    /**
     * Returns the number of empty cells in the grid
     * @return the number of empty cells
     */
    private int emptyCells(){
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if(cell == EMPTY_CELL){
                    count++;
                }
            }
        }
        return count;
    }

}
