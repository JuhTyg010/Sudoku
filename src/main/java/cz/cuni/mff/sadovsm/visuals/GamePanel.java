package cz.cuni.mff.sadovsm.visuals;

import cz.cuni.mff.sadovsm.sudoku.SudokuGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class GamePanel extends JPanel {

    private SudokuPanel sudokuPanel;
    private static final int EMPTY_CELL = 0;
    private int[][] grid;
    private Stack<int[]> moves;
    private int toComplete;
    private boolean isUndo;

    public GamePanel(int difficulty){
        //TODO: start the game, load sudokuPanel, buttons and wisperer
        sudokuPanel = new SudokuPanel();
        setLayout(new BorderLayout());
        add(sudokuPanel, BorderLayout.CENTER);

        doPrefill(difficulty);

    }

    private void doPrefill(int difficulty){
        grid = SudokuGenerator.generateSudoku(difficulty);
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j < 9; j++){
                if(grid[i][j] != EMPTY_CELL)
                    sudokuPanel.setCellValue(i, j, grid[i][j]);
            }
        }
        sudokuPanel.lockPrefilled();
    }
}
