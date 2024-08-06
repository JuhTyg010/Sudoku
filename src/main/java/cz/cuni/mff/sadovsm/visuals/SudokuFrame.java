package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {
    private GamePanel gamePanel;
    private final MenuPanel menuPanel;
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
        final int width = 300;
        final int height = 150;

        endDialog.setUndecorated(true);
        endDialog.setSize(width,height);
        endDialog.setLocation(getLocation().x + getWidth()/2 - width / 2,
                getLocation().y + getHeight() / 2 - height / 2);

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
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setPreferredSize(new Dimension(120,40));

        endDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 2;
        gbc.weighty = 2;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        endDialog.add(text, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        endDialog.add(menuButton, gbc);

        gbc.gridx = 1;
        endDialog.add(exitButton, gbc);

        return endDialog;
    }

}

