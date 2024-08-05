package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.BorderLayout;

public class SudokuFrame extends JFrame {
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;


    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        // Create panels
        menuPanel = new MenuPanel(this);


        // Add panels to the frame
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.CENTER);
    }

    public void gameStart(int difficulty){
        gamePanel = new GamePanel(this, difficulty);
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        menuPanel.setVisible(false);
    }

    public JDialog GameEnd(){
        JDialog endDialog = new JDialog(this, "Game end");

        endDialog.setUndecorated(true);
        endDialog.setSize(400,200);
        endDialog.setLocationRelativeTo(null);

        JLabel text = new JLabel("Congratulation");

        JButton menuButton = new JButton("Go to menu");
        menuButton.addActionListener(e -> {
            menuPanel.setVisible(true);
            gamePanel.setVisible(false);
            endDialog.dispose();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        endDialog.add(text);
        endDialog.add(menuButton);
        endDialog.add(exitButton);

        return endDialog;
    }

}

