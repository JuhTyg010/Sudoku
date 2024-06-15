package cz.cuni.mff.sadovsm.visuals;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SudokuFrame extends JFrame {
    private SudokuPanel sudokuPanel;
    private MenuPanel menuPanel;

    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create panels
        sudokuPanel = new SudokuPanel();
        menuPanel = new MenuPanel();

        // Add panels to the frame
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.WEST);
        add(sudokuPanel, BorderLayout.CENTER);
    }
}

