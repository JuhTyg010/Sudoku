package cz.cuni.mff.sadovsm.visuals;


import cz.cuni.mff.sadovsm.sudoku.SudokuGenerator;
import cz.cuni.mff.sadovsm.sudoku.SudokuValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class SudokuPanel extends JPanel {
    private JButton[][] cells;

    private static final int EMPTY_CELL = 0;
    private int[][] grid;
    private Stack<int[]> moves;
    private int toComplete;
    private boolean isUndo;
    private JTextField messageText;


    public SudokuPanel(int difficulty, JTextField messageText_) {
        messageText = messageText_;
        moves = new Stack<int[]>();
        int width = this.getWidth();
        int height = this.getHeight();
        if (width > height) setPreferredSize(new Dimension(height,height));
        else setPreferredSize(new Dimension(width,width));
        setLayout(new GridLayout(9, 9));
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
                if(grid[i][j] != EMPTY_CELL)
                    setCellValue(i, j, grid[i][j]);
            }
        }
        lockPrefilled();
    }

    public int[][] getGrid(){
        return grid;
    }

    public void setCellValue(int row, int col, int value) {
        cells[row][col].setText(value == EMPTY_CELL ? "" : String.valueOf(value));
    }

    public int getCellValue(int row, int col) {
        String text = cells[row][col].getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    public void lockPrefilled(){
        for (JButton[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                if (!cell[j].getText().isEmpty()) {
                    cell[j].setEnabled(false);
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
        private int row;
        private int col;

        public CellButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
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

            // Position the dialog near the clicked button
            numberDialog.setLocation(buttonLocation.x, buttonLocation.y + clickedButton.getHeight());

            // Add a mouse listener to close the dialog when clicking outside of it
            numberDialog.addMouseListener(new MouseAdapter() {
                //TODO: fix this cause it doesn't work
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!numberDialog.getBounds().contains(e.getLocationOnScreen())) {
                        numberDialog.dispose();
                    }
                    System.out.println(e.getLocationOnScreen());
                }
            });

            numberDialog.setVisible(true);
        }
    }


    //TODO: move this to gamePanel where we check possibility and save change
    private class NumberButtonListener implements ActionListener {
        private int row;
        private int col;
        private int number;
        private JDialog dialog;

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
