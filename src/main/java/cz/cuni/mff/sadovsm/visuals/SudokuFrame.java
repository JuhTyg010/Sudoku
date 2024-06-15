package cz.cuni.mff.sadovsm.visuals;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SudokuFrame extends JFrame {
    private SudokuPanel sudokuPanel;

    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(400, 400);
        setLocationRelativeTo(null);

        sudokuPanel = new SudokuPanel();
        add(sudokuPanel, BorderLayout.CENTER);
    }
}

