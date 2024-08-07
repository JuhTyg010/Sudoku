package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MenuPanel extends JPanel {
    private final JRadioButton easyButton;
    private final JRadioButton mediumButton;
    private final JRadioButton hardButton;

    private final SudokuFrame controller;

    public MenuPanel(SudokuFrame controller_) {


        controller = controller_;
        JButton startButton = new JButton("Start");
        startButton.setSize(new Dimension(200,50));
        JButton exitButton = new JButton("Exit");
        JButton docButton = new JButton("Open Documentation");

        easyButton = new JRadioButton("Easy");
        mediumButton = new JRadioButton("Medium");
        hardButton = new JRadioButton("Hard");

        mediumButton.setSelected(true);

        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        JPanel ratioPanel = new JPanel();
        ratioPanel.add(easyButton);
        ratioPanel.add(mediumButton);
        ratioPanel.add(hardButton);

        add(startButton);
        add(ratioPanel);
        add(docButton);
        add(exitButton);

        // Add action listeners
        startButton.addActionListener(e -> startApp());
        exitButton.addActionListener(e -> System.exit(0));
        docButton.addActionListener(e -> openDocumentation());
    }

    private void startApp() {
        // Logic to start the app, depending on the selected difficulty level
        if (easyButton.isSelected()) {
            System.out.println("Starting game in Easy mode");
            controller.gameStart(30);
        } else if (mediumButton.isSelected()) {
            System.out.println("Starting game in Medium mode");
            controller.gameStart(50);
        } else if (hardButton.isSelected()) {
            System.out.println("Starting game in Hard mode");
            controller.gameStart(65);
        } else {
            System.out.println("No difficulty selected, defaulting to Easy");
            controller.gameStart(30);
        }


    }

    private void openDocumentation() {
        try {
            Desktop.getDesktop().browse(new URI("http://todo_this_link.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

