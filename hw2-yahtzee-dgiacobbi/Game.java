/* The purpose of the Game class is to create any methods that can be run as fully completed games. 
 * In this case, Yahtzee is the only game in the class. It also asks the user if it would like to 
 * play again after initialization.
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #5
 * No sources to cite.
 * 
 * @author David Giacobbi
 * @version v2.1 11/11/22
 */

import java.util.ArrayList;
import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;

/*
 * Game CLASS:
 * 
 * The Game class implements the Scorecard and Dice objects as well as adds prompts for the user to play Yahtzee.
 * It is used as the construction area for the Yahtzee game as well as any other additional games that could be
 * added in the future.
 */
public class Game extends JFrame{

    // Internal variable elements
    public int num_sides, num_dice, num_rolls;
    public UserScore player1;

    Game(int num_sides, int num_dice){
        
        // Create a new frame
        new JFrame();

        // Set internal game variables
        this.num_sides = num_sides;
        this.num_dice = num_dice;
        this.num_rolls = 3;

        // Set size, layout, and color of frame
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        getContentPane().setBackground(new Color(227, 125, 125));

        setVisible(true);
    }
  
    /*
    * Runs the complete Yahtzee game, using the Dice and Scorecard objects as the main drivers of the method.
    */
    public void PlayYahtzee(){

        ArrayList<Integer> hand =  new ArrayList<Integer>();

        // Create a new scorecard for upcoming game
        UserScore player1 = new UserScore(num_sides);

        // Start a turn panel
        Dice dice_hand = new Dice(num_sides, num_dice, num_rolls, player1, hand);
        add(dice_hand, BorderLayout.CENTER);      
    }
}