package cz.cuni.mff.sadovsm.visuals;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;

public class SudokuPanel extends JPanel {
    private JTextField[][] cells;

    public SudokuPanel() {
        setLayout(new GridLayout(9, 9));
        cells = new JTextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
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
}
