package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MenuPanel extends JPanel {
    private final JRadioButton easyButton;
    private final JRadioButton mediumButton;
    private final JRadioButton hardButton;

    private final SudokuFrame controller;

    /**
     * Instantiates a new Menu panel.
     *
     * @param controller_ the frame of the game
     */
    public MenuPanel(SudokuFrame controller_) {

        controller = controller_;
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(180,50));

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(180,50));

        JButton docButton = new JButton("Open Documentation");
        docButton.setPreferredSize(new Dimension(180,50));

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

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 4;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(startButton, gbc);

        gbc.gridy = 1;
        add(ratioPanel, gbc);

        gbc.gridy = 2;
        add(docButton, gbc);

        gbc.gridy = 3;
        add(exitButton, gbc);

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
            //TODO: this should be like working
            Desktop.getDesktop().browse(new URI("http://todo_this_link.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

