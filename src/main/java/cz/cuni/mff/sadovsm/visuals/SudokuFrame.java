package cz.cuni.mff.sadovsm.visuals;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class SudokuFrame extends JFrame {
    private GamePanel gamePanel;
    private MenuPanel menuPanel;


    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create panels
        menuPanel = new MenuPanel(this);


        // Add panels to the frame
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.CENTER);
    }

    public void gameStart(int difficulty){
        gamePanel = new GamePanel(difficulty);
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        menuPanel.setVisible(false);
    }

}

