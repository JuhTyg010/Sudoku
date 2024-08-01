package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private SudokuPanel sudokuPanel;

    public GamePanel(int difficulty){
        //TODO: start the game, load sudokuPanel, buttons and wisperer
        sudokuPanel = new SudokuPanel();
        setLayout(new BorderLayout());
        add(sudokuPanel, BorderLayout.CENTER);
    }
}
