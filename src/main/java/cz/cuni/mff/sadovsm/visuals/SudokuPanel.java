package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class SudokuPanel extends JPanel {
    private JButton[][] cells;


    public SudokuPanel() {
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
                    cells[row][col].setBackground(new Color(220, 220, 220)); // Light gray
                } else {
                    cells[row][col].setBackground(new Color(245, 245, 245)); // Slightly darker gray
                }

                add(cells[row][col]);
            }
        }
    }

    public void setCellValue(int row, int col, int value) {
        cells[row][col].setText(value == 0 ? "" : String.valueOf(value));
    }

    public int getCellValue(int row, int col) {
        String text = cells[row][col].getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    private void lockPrefilled(JButton[][] cells){
        //TODO: implement logic to be able on start block some of the position.
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
            setCellValue(row, col, number);
            dialog.dispose();
        }
    }
}
