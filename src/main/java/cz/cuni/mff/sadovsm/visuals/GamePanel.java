package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private SudokuPanel sudokuPanel;
    private JTextField messageText;


    public GamePanel(int difficulty){
        //TODO: start the game, load sudokuPanel, buttons and wisperer

        messageText = new JTextField();
        sudokuPanel = new SudokuPanel(difficulty, messageText);
        setLayout(new BorderLayout());
        add(sudokuPanel, BorderLayout.CENTER);
        add(messageText, BorderLayout.SOUTH);


    }




}
