package FrontEnd;

import BackEnd.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/*
 * EndGame
 * Class providing a frame for a finished game's panel.
 */
public class EndGame {

    static int time;                // amount of seconds passed since the beginning of the game
    static int turns;               // amount of turns played since the beginning of the game
    static String name;
    static JFrame endGameFrame;     // frame for a finished game's panel

    /**
     * Initializes frame with a finished game's data. Stops game in the main window.
     * @param time amount of seconds passed since the beginning of the game.
     * @param turns amount of turns played since the beginning of the game
     */
    static public void init(int time, int turns, String name, boolean error) {
        // Finishing game.
        if (GameMenu.mainFrame != null)
            GameMenu.mainFrame.setEnabled(false);
        if (GameMenu.timer != null)
            GameMenu.timer.stop();

        if (error) {
            endGameFrame = new JFrame("There was an error");
            endGameFrame.requestFocus();
            endGameFrame.setAlwaysOnTop(true);
            endGameFrame.setResizable(false);


            JLabel errorLabel = new JLabel("There was an error with connection", JLabel.CENTER);
            endGameFrame.add(errorLabel);
            endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            endGameFrame.pack();
            endGameFrame.setLocationRelativeTo(null);
            endGameFrame.setVisible(true);
            endGameFrame.setResizable(false);
            return;
        }

        EndGame.time = time;
        EndGame.turns = turns;
        EndGame.name = name;

        endGameFrame = new JFrame("Game finished");
        endGameFrame.requestFocus();
        endGameFrame.setAlwaysOnTop(true);
        endGameFrame.setResizable(false);

        endGameFrame.getContentPane().add(new VerticalPanel());
        endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        endGameFrame.pack();
        endGameFrame.setLocationRelativeTo(null);
        endGameFrame.setVisible(true);
        endGameFrame.setResizable(false);
    }

    /**
     * Provides panel with needed buttons and data.
     */
    static class VerticalPanel extends JPanel {

        /**
         * Describes inner content.
         */
        public VerticalPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(300, 150));

            JLabel timePassed = new JLabel(String.format(
                    "Game finished in %02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
                    JLabel.CENTER);

            timePassed.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            timePassed.setPreferredSize(new Dimension(200, 15));
            timePassed.setMaximumSize(timePassed.getPreferredSize());

            JLabel turnsLabel = new JLabel("Amount of turns: " + turns, JLabel.CENTER);
            turnsLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            turnsLabel.setPreferredSize(new Dimension(200, 30));
            turnsLabel.setMaximumSize(turnsLabel.getPreferredSize());

            JLabel winnerLabel = new JLabel(Field.getGameResult(), JLabel.CENTER);
            winnerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            winnerLabel.setPreferredSize(new Dimension(200, 30));
            winnerLabel.setMaximumSize(winnerLabel.getPreferredSize());

//            Button startGameButton = new Button("Play new game");
//            startGameButton.setPreferredSize(new Dimension(100, 30));
//            startGameButton.setMaximumSize(startGameButton.getPreferredSize());
//            startGameButton.setFocusable(false);
//            startGameButton.addActionListener(e -> {
//                GameMenu.mainFrame.setEnabled(true);
//                GameMenu.timer.start();
//                GameMenu.mainFrame.dispose();
//                GameMenu.createAndShowUI();
//                GameMenu.sec = 1;
//                GameMenu.turns = 0;
//                endGameFrame.dispose();
//            });

            Button endGameButton = new Button("End game");
            endGameButton.setPreferredSize(new Dimension(100, 30));
            endGameButton.setMaximumSize(endGameButton.getPreferredSize());
            endGameButton.setFocusable(false);
            endGameButton.addActionListener(
                    e -> endGameFrame.dispatchEvent(new WindowEvent(endGameFrame, WindowEvent.WINDOW_CLOSING))
            );

            add(Box.createVerticalStrut(10));
            add(timePassed);
            add(Box.createVerticalStrut(5));
            add(turnsLabel);
            add(Box.createVerticalStrut(10));
            add(winnerLabel);
            add(Box.createVerticalStrut(15));
            add(endGameButton);
        }
    }
}