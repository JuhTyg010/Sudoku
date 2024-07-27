package cz.cuni.mff.sadovsm;

import cz.cuni.mff.sadovsm.visuals.SudokuFrame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class GraphicsApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            SudokuFrame frame = new SudokuFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
