package cz.cuni.mff.sadovsm.visuals;

import cz.cuni.mff.sadovsm.sudoku.SudokuSolveHinter;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private SudokuPanel sudokuPanel;
    private final JTextField messageText;
    private final SudokuFrame controller;
    private final int difficulty;


    /**
     * Instantiates a new Game panel.
     *
     * @param controller_ the frame of the game
     * @param difficulty_ the difficulty level of the game
     */
    public GamePanel(SudokuFrame  controller_, int difficulty_){

        messageText = new JTextField();
        sudokuPanel = new SudokuPanel(controller_, difficulty_, messageText);
        controller = controller_;
        difficulty = difficulty_;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 4;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        add(sudokuPanel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        add(messageText, gbc);

        JPanel buttons = ButtonPanel();
        gbc.gridy = 0;
        add(buttons, gbc);

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
            sudokuPanel.setVisible(false);  //without this it doesn't remove it in time, but after the click
            remove(sudokuPanel);
            sudokuPanel = new SudokuPanel(controller, difficulty, messageText);
            add(sudokuPanel, BorderLayout.CENTER);

        });

        JButton autoFillButton = new JButton("Auto fill");
        autoFillButton.addActionListener(e -> messageText.setText(sudokuPanel.autofill()));

        JButton undoButton = new JButton("←");
        undoButton.addActionListener(e -> messageText.setText(sudokuPanel.undo()));

        panel.add(undoButton);
        panel.add(hintButton);
        panel.add(autoFillButton);
        panel.add(restartButton);

        return panel;
    }
    
}
