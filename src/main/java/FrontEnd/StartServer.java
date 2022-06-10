package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * EndGame
 * Class providing a frame for a finished game's panel.
 */
class startServerFrame extends JFrame implements ItemListener {

    static String myName;
    static int gameDuration;
    static boolean singlePlayer = true;
    static JComboBox c1;
    static JTextField nameText;
    static JTextField gameDurationText;
    static JTextField portText;
    static JTextField portOpponentText;
    static JTextField opponentNameText;
    static startServerFrame f;

    public static void main(String[] args) {
        f = new startServerFrame();
        f.setResizable(false);

        f.getContentPane().add(new VerticalPanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setResizable(false);
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
            setPreferredSize(new Dimension(350, 450));

            String s1[] = { "Single player", "Multiplayer" };
            c1 = new JComboBox(s1);
            c1.setPreferredSize(new Dimension(200, 25));
            c1.setMaximumSize(c1.getPreferredSize());
            c1.addItemListener(f);

            JLabel createServerLabel =
                    new JLabel("------------------ Or create your own ------------------", JLabel.CENTER);
            createServerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            createServerLabel.setPreferredSize(new Dimension(300, 30));
            createServerLabel.setMaximumSize(createServerLabel.getPreferredSize());

            JLabel opponentNameLabel = new JLabel("Enter your name", JLabel.CENTER);
            opponentNameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            opponentNameLabel.setPreferredSize(new Dimension(200, 30));
            opponentNameLabel.setMaximumSize(opponentNameLabel.getPreferredSize());

            opponentNameText = new JTextField(16);
            opponentNameText.setPreferredSize(new Dimension(200, 20));
            opponentNameText.setMaximumSize(opponentNameText.getPreferredSize());

            JLabel portOpponentLabel = new JLabel("Enter port number", JLabel.CENTER);
            portOpponentLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            portOpponentLabel.setPreferredSize(new Dimension(200, 30));
            portOpponentLabel.setMaximumSize(portOpponentLabel.getPreferredSize());

            portOpponentText = new JTextField(16);
            portOpponentText.setPreferredSize(new Dimension(200, 20));
            portOpponentText.setMaximumSize(portOpponentText.getPreferredSize());

            Button connectToServerButton = new Button("Connect to an existing server");
            connectToServerButton.setPreferredSize(new Dimension(200, 30));
            connectToServerButton.setMaximumSize(connectToServerButton.getPreferredSize());
            connectToServerButton.setFocusable(false);
            connectToServerButton.addActionListener(e -> {
                System.out.println(portOpponentText.getText());
                GameMenu.init(opponentNameText.getText(), "",
                        false, portOpponentText.getText(), false);
                f.dispose();
            });

            JLabel yourNameLabel = new JLabel("Enter your name:", JLabel.CENTER);
            yourNameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            yourNameLabel.setPreferredSize(new Dimension(200, 30));
            yourNameLabel.setMaximumSize(yourNameLabel.getPreferredSize());

            nameText = new JTextField(16);
            nameText.setPreferredSize(new Dimension(200, 20));
            nameText.setMaximumSize(nameText.getPreferredSize());

            JLabel gameDurationLabel = new JLabel("Enter game duration (sec)", JLabel.CENTER);
            gameDurationLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            gameDurationLabel.setPreferredSize(new Dimension(200, 30));
            gameDurationLabel.setMaximumSize(gameDurationLabel.getPreferredSize());

            gameDurationText = new JTextField(16);
            gameDurationText.setPreferredSize(new Dimension(200, 20));
            gameDurationText.setMaximumSize(gameDurationText.getPreferredSize());

            JLabel portLabel = new JLabel("Enter port number", JLabel.CENTER);
            portLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            portLabel.setPreferredSize(new Dimension(200, 30));
            portLabel.setMaximumSize(portLabel.getPreferredSize());

            portText = new JTextField(16);
            portText.setPreferredSize(new Dimension(200, 20));
            portText.setMaximumSize(portText.getPreferredSize());

            Button createServerButton = new Button("Start server");
            createServerButton.setPreferredSize(new Dimension(200, 30));
            createServerButton.setMaximumSize(createServerButton.getPreferredSize());
            createServerButton.setFocusable(false);
            createServerButton.addActionListener(
                    e -> {
                        GameMenu.init(nameText.getText(), gameDurationText.getText(),
                                singlePlayer, portText.getText(), true);
                        f.dispose();
                    }
            );

            add(Box.createVerticalStrut(10));
            add(opponentNameLabel);
            add(opponentNameText);
            add(Box.createVerticalStrut(5));
            add(portOpponentLabel);
            add(portOpponentText);
            add(connectToServerButton);
            add(Box.createVerticalStrut(15));
            add(createServerLabel);
            add(c1);
            add(Box.createVerticalStrut(5));
            add(yourNameLabel);
            add(nameText);
            add(Box.createVerticalStrut(5));
            add(gameDurationLabel);
            add(gameDurationText);
            add(Box.createVerticalStrut(5));
            add(portLabel);
            add(portText);
            add(Box.createVerticalStrut(10));
            add(createServerButton);
        }

        public void startServer(ActionListener e) {

        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        // if the state combobox is changed
        if (e.getSource() == c1) {
            if (c1.getSelectedItem() == "Single player") {
                singlePlayer = true;
            } else {
                singlePlayer = false;
            }
        }
    }
}