package cz.cuni.mff.sadovsm.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private JButton exitButton;
    private JButton docButton;
    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    private ButtonGroup difficultyGroup;

    public MenuPanel() {
        setLayout(new GridLayout(6, 1));

        startButton = new JButton("Start");
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

        add(startButton);
        add(easyButton);
        add(mediumButton);
        add(hardButton);
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
        } else if (mediumButton.isSelected()) {
            System.out.println("Starting game in Medium mode");
        } else if (hardButton.isSelected()) {
            System.out.println("Starting game in Hard mode");
        } else {
            System.out.println("No difficulty selected, defaulting to Easy");
        }
    }

    private void openDocumentation() {
        try {
            Desktop.getDesktop().browse(new URI("http://your-documentation-url.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

