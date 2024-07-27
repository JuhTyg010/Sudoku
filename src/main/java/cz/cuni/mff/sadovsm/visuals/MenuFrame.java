package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MenuFrame extends JFrame {
    private JButton startButton;
    private JButton exitButton;
    private JButton docButton;
    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    private ButtonGroup difficultyGroup;
    private JPanel ratioPanel;

    public MenuFrame(JFrame gameFrame) {
        setTitle("Sudoku");
        setSize(600, 400);
        setLocationRelativeTo(null);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        startButton = new JButton("Start");
        startButton.setSize(new Dimension(200,50));
        exitButton = new JButton("Exit");
        docButton = new JButton("Open Documentation");

        easyButton = new JRadioButton("Easy");
        mediumButton = new JRadioButton("Medium");
        hardButton = new JRadioButton("Hard");

        mediumButton.setSelected(true);

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        ratioPanel = new JPanel();
        ratioPanel.add(easyButton);
        ratioPanel.add(mediumButton);
        ratioPanel.add(hardButton);

        add(startButton);
        add(ratioPanel);
        add(docButton);
        add(exitButton);

        // Add action listeners
        startButton.addActionListener(e -> startApp(gameFrame));
        exitButton.addActionListener(e -> System.exit(0));
        docButton.addActionListener(e -> openDocumentation());
    }

    private void startApp(JFrame gameFrame) {
        // Logic to start the app, depending on the selected difficulty level
        if (easyButton.isSelected()) {
            System.out.println("Starting game in Easy mode");
        } else if (mediumButton.isSelected()) {
            System.out.println("Starting game in Medium mode");
        } else if (hardButton.isSelected()) {
            System.out.println("Starting game in Hard mode");
        } else {
            System.out.println("No difficulty selected, defaulting to Easy");
        }
        gameFrame.setVisible(true);

    }

    private void openDocumentation() {
        try {
            Desktop.getDesktop().browse(new URI("http://todo_this_link.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

