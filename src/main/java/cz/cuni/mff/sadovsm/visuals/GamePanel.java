package cz.cuni.mff.sadovsm.visuals;

import cz.cuni.mff.sadovsm.sudoku.SudokuSolveHinter;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private SudokuPanel sudokuPanel;
    private JTextField messageText;
    private SudokuFrame controller;


    public GamePanel(SudokuFrame  controller_, int difficulty){
        //TODO: start the game, load sudokuPanel, buttons and wisperer

        messageText = new JTextField();
        sudokuPanel = new SudokuPanel(controller_, difficulty, messageText);
        controller = controller_;
        setLayout(new BorderLayout());
        add(sudokuPanel, BorderLayout.CENTER);
        add(messageText, BorderLayout.SOUTH);
        JPanel buttons = ButtonPanel();
        add(buttons, BorderLayout.NORTH);

    }

    private JPanel ButtonPanel(){
        JPanel panel = new JPanel();

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> {
            int[] hint = SudokuSolveHinter.posHint(sudokuPanel.getGrid());
            if(hint[0] == -1){
                messageText.setText("Sorry no hint available");
            } else {
                messageText.setText(String.format("In %1d. row and %1d. column should be value %1d.",
                        hint[0] + 1, hint[1] + 1, hint[2]));
            }
        });

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            //TODO: reload whole grid in sudokuPanel probably I'll just reset the sudokuPanel
        });

        JButton autoFillButton = new JButton("Auto fill");
        autoFillButton.addActionListener(e -> {
            messageText.setText(sudokuPanel.autofill());
        });

        JButton undoButton = new JButton("←");
        undoButton.addActionListener(e -> {
            messageText.setText(sudokuPanel.undo());
        });


        panel.add(undoButton);
        panel.add(hintButton);
        panel.add(autoFillButton);
        panel.add(restartButton);

        return panel;
    }







}
