/* The purpose of the YahtzeeGUI is to configure the dice attributes, set
 * their values, and start the game.
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #5
 * No sources to cite.
 * 
 * @author David Giacobbi
 * @version v2.1 11/11/22
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;

/*
 * YahtzeeGUI CLASS:
 * 
 * The YahtzeeGUI class starts the game by configuring the dice attributes and setting the
 * values that will be passed into the main game objects.
 */
public class YahtzeeGUI{

    // Internal game elements
    public int num_sides, num_dice;

    // GUI components used to configure the game
    public JButton start_game;
    public JFrame frame;
    public JComboBox<String> side_choices, num_choices;
    public JLabel side_label, num_label, config_label;
    public JPanel config;

    YahtzeeGUI(){

        // Create a new frame for the configuration part
        frame = new JFrame();

        // Strings for the combo boxes (options for side and dice numbers)
        String[] dice_sides = {"6", "8", "12"};
        String[] dice_num = {"5", "6", "7"};

        // Set frame size, color, and layout
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1200, 600);
        frame.getContentPane().setBackground(new Color(227, 125, 125));

        // config panel Setup
        config = new JPanel();
        config.setBackground(Color.lightGray);
        config.setBounds(300, 200, 600, 200);

        config_label = new JLabel();
        config_label.setText("Welcome to Yahtzee!");
        config_label.setFont(new Font("Baskerville Old Face", Font.BOLD, 48));

        config.add(config_label);

        // Dice Side Selection
        side_choices = new JComboBox<>(dice_sides);

        side_label = new JLabel("", SwingConstants.LEFT);
        side_label.setText("Select the number of sides for each die: ");
        side_label.setFont(new Font("Baskerville Old Face", Font.PLAIN, 26));
        side_label.setSize(600, 50);

        config.add(side_label);
        config.add(side_choices);

        // Dice Number Selection
        num_choices = new JComboBox<>(dice_num);

        num_label = new JLabel("", SwingConstants.LEFT);
        num_label.setText("Select the number of dice to roll: ");
        num_label.setFont(new Font("Baskerville Old Face", Font.PLAIN, 26));
        num_label.setSize(600, 50);

        config.add(num_label);
        config.add(num_choices);

        // Set Game button to continue to gameplay screen
        start_game = new JButton("Start Game");
        start_game.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the Strings from the combo boxes
                String side_str = side_choices.getItemAt(side_choices.getSelectedIndex());
                String num_str = num_choices.getItemAt(num_choices.getSelectedIndex());

                // Convert strings to integers to set the internal game variables
                num_sides = Integer.parseInt(side_str);
                num_dice = Integer.parseInt(num_str);

                frame.setVisible(false);

                // Create a new game and play Yahtzee
                Game new_game = new Game(num_sides, num_dice);
                new_game.PlayYahtzee();
            }
        });       
        config.add(start_game);

        // Show frame to user
        frame.add(config);
        frame.setVisible(true);    
    }
}
