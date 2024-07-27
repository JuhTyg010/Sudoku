package cz.cuni.mff.sadovsm;

import cz.cuni.mff.sadovsm.visuals.MenuFrame;
import cz.cuni.mff.sadovsm.visuals.SudokuFrame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class GraphicsApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            SudokuFrame frame = new SudokuFrame();
            MenuFrame menu = new MenuFrame(frame);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(false);
            menu.setVisible(true);
        });
    }
}
