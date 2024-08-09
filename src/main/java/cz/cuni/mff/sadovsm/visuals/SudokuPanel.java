package cz.cuni.mff.sadovsm.visuals;

import cz.cuni.mff.sadovsm.sudoku.SudokuGenerator;
import cz.cuni.mff.sadovsm.sudoku.SudokuSolveHinter;
import cz.cuni.mff.sadovsm.sudoku.SudokuValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

/**
 * The type Sudoku panel.
 */
public class SudokuPanel extends JPanel {
    private final JButton[][] cells;
    private final JTextField messageText;
    private final SudokuFrame controller;

    private static final int EMPTY_CELL = 0;
    private int[][] grid;
    private final Stack<int[]> moves;
    private int toComplete;


    @Override
    public Dimension getPreferredSize() {
        Dimension size = controller.getSize();
        int newSize = Math.min(size.width - 10, size.height - 100);
        return new Dimension(newSize, newSize);
    }

    /**
     * Instantiates a new Sudoku panel.
     *
     * @param controller_  the frame of the game
     * @param difficulty   the difficulty level of the game
     * @param messageText_ place where to display game related messages
     */
    public SudokuPanel(SudokuFrame controller_, int difficulty, JTextField messageText_) {
        messageText = messageText_;
        controller = controller_;
        moves = new Stack<>();
        toComplete = 9 * 9;

        setLayout(new GridLayout(9, 9));
        UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK)); // To edit color of the disabled buttons(positions)
        cells = new JButton[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JButton();
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[row][col].setFocusPainted(false);
                cells[row][col].setMargin(new Insets(0, 0, 0, 0));
                cells[row][col].addActionListener(new CellButtonListener(row, col));

                // Set borders to distinguish 3x3 sections
                Border border = getCellBorder(row, col);
                cells[row][col].setBorder(border);

                // Set alternating background colors for better visual distinction
                if ((row / 3 + col / 3) % 2 == 0) {
                    cells[row][col].setBackground(new Color(220, 220, 220)); // Dark gray
                } else {
                    cells[row][col].setBackground(new Color(245, 245, 245)); // Slightly Lighter gray
                }

                add(cells[row][col]);
            }
        }

        doPrefill(difficulty);
        messageText_.setText("welcome");

    }

    private void doPrefill(int difficulty){
        grid = SudokuGenerator.generateSudoku(difficulty);
        for(int i = 0; i < 9 ; i++){
            for(int j = 0; j < 9; j++){
                if(grid[i][j] != EMPTY_CELL){
                    toComplete--;
                    setCellValue(i, j, grid[i][j]);
                }
            }
        }
        lockPrefilled();
    }

    /**
     * Getter for the grid representing
     *
     * @return the int [ ][ ] the grid
     */
    public int[][] getGrid(){
        return grid;
    }

    private void setCellValue(int row, int col, int value) {
        cells[row][col].setText(value == EMPTY_CELL ? "" : String.valueOf(value));
        if(toComplete <= 0){
            JDialog end = controller.GameEnd();
            end.setVisible(true);
        }
    }

    /**
     * Autofill one cell of the sudoku.
     *
     * @return the string message, explaining what happened
     */
    public String autofill(){
        int[] hint = SudokuSolveHinter.posHint(grid);
        if(hint[0] == -1){
            return "I wasn't able to fill any of those positions";
        }
        grid[hint[0]][hint[1]] = hint[2];
        moves.push(new int[]{hint[0], hint[1]});
        toComplete--;
        setCellValue(hint[0], hint[1], hint[2]);
        return "One value was successfully filled";
    }

    /**
     * Undo removes last added number in the sudoku
     *
     * @return the string explaining what was removed
     */
    public String undo(){
        if(moves.isEmpty()){
            return "There is no move which can be undone";
        }
        int[] pos = moves.pop();
        toComplete++;
        grid[pos[0]][pos[1]] = EMPTY_CELL;
        setCellValue(pos[0],pos[1], EMPTY_CELL);
        if(SudokuGenerator.isSolvable(grid)){
            return "Moved back. Sudoku is solvable.";
        }
        return "Moved back, but sudoku still isn't solvable";
    }

    private void lockPrefilled(){
        for (JButton[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                if (!cell[j].getText().isEmpty()) {
                    cell[j].setEnabled(false);
                } else{
                    cell[j].setForeground(Color.BLUE);
                }
            }
        }
    }

    private Border getCellBorder(int row, int col) {
        int top = (row % 3 == 0) ? 3 : 1;
        int left = (col % 3 == 0) ? 3 : 1;
        int bottom = ((row + 1) % 3 == 0) ? 3 : 1;
        int right = ((col + 1) % 3 == 0) ? 3 : 1;

        return new MatteBorder(top, left, bottom, right, Color.BLACK);
    }

    private class CellButtonListener implements ActionListener {
        private final int row;
        private final int col;

        /**
         * Instantiates a new Cell button listener.
         *
         * @param row the row of the cell
         * @param col the col of the cell
         */
        public CellButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JButton clickedButton = (JButton) event.getSource();
            Point buttonLocation = clickedButton.getLocationOnScreen();

            JDialog numberDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(SudokuPanel.this), true);
            numberDialog.setUndecorated(true); // Remove the title and borders
            numberDialog.setLayout(new GridLayout(3, 3));
            numberDialog.setSize(200, 200);

            for (int i = 1; i <= 9; i++) {
                JButton numberButton = new JButton(String.valueOf(i));
                numberButton.setFont(new Font("Arial", Font.PLAIN, 20));
                numberButton.addActionListener(new NumberButtonListener(row, col, i, numberDialog));
                numberDialog.add(numberButton);
            }
            numberDialog.setLocation(buttonLocation.x, buttonLocation.y + clickedButton.getHeight());
            numberDialog.setVisible(true);
        }
    }

    private class NumberButtonListener implements ActionListener {
        private final int row;
        private final int col;
        private final int number;
        private final JDialog dialog;

        /**
         * Instantiates a new Number button listener.
         *
         * @param row    the row of the sudoku
         * @param col    the col of the sudoku
         * @param number the number it represents
         * @param dialog the dialog in which it is
         */
        public NumberButtonListener(int row, int col, int number, JDialog dialog) {
            this.row = row;
            this.col = col;
            this.number = number;
            this.dialog = dialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            grid[row][col] = number;
            if(SudokuValidator.isValidSudoku(grid)){
                moves.push(new int[]{row, col});

                toComplete--;
                if(SudokuGenerator.isSolvable(grid)){
                    messageText.setText("Sudoku is solvable");
                } else {
                    messageText.setText("Sudoku is not solvable");
                }
                setCellValue(row, col, number);
            } else {
                grid[row][col] = EMPTY_CELL;
                setCellValue(row, col, EMPTY_CELL);
                messageText.setText("Invalid move");
            }

            dialog.dispose();
        }
    }
}
