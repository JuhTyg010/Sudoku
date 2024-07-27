package cz.cuni.mff.sadovsm.visuals;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class SudokuFrame extends JFrame {
    private SudokuPanel sudokuPanel;


    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create panels
        sudokuPanel = new SudokuPanel();


        // Add panels to the frame
        setLayout(new BorderLayout());

        add(sudokuPanel, BorderLayout.CENTER);
    }

}

