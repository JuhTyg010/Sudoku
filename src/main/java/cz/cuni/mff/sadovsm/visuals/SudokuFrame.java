package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class SudokuFrame extends JFrame {
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(300,240));
        setLocationRelativeTo(null);

        menuPanel = new MenuPanel(this);
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
        endDialog.setSize(300,150);
        endDialog.setLocationRelativeTo(null);

        JLabel text = new JLabel("Congratulation");
        text.setFont(new Font("Arial", Font.BOLD, 30));

        JButton menuButton = new JButton("Go to menu");
        menuButton.addActionListener(e -> {
            menuPanel.setVisible(true);
            gamePanel.setVisible(false);
            endDialog.dispose();
        });
        menuButton.setPreferredSize(new Dimension(120,40));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        exitButton.setPreferredSize(new Dimension(120,40));

        endDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        endDialog.add(text, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        endDialog.add(menuButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        endDialog.add(exitButton, gbc);

        return endDialog;
    }

}

