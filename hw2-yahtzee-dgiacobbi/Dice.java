/* The purpose of the class Dice is to run any methods related to rolling the die, 
 * and picking the die after every roll for the Yahtzee score
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #5
 * No sources to cite.
 * 
 * @author David Giacobbi
 * @version v3.1 11/11/22
 */

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/*
 * Dice CLASS:
 * 
 * The Dice class encapsulates any methods related to rolling the Dice in Yahtzee.
 * Dice rolls are stored in an ArrayList, and a random number generator is used to
 * roll each of the die.
 */
public class Dice extends JPanel{

    // Internal variables related to the dice and user's tracked score
    public int num_sides, num_dice, num_rolls;
    public UserScore user;

    // Regulators for the turn, holds the final hand of dice
    public int turn;
    public ArrayList<Integer> hand;
    public boolean finish_turn;

    // GUI component elements
    public JCheckBox check1, check2, check3, check4, check5, check6, check7;
    public JButton roll_dice, display_score, end_button;
    public JPanel dice_panel, rolls_panel, end_game, button_panel, checkbox_panel;
    public JLabel dice_label, rolls_label, end_label, instruction_label;

    /*
    * Dice constructor used to alter dice attributes for Lizard Spock Yahtzee.
    *
    * @param sides: int number of sides on each die 
    *        dice: int number of total amount of dice in play
    *        rolls: int number of total amount of rolls per round
    *        user: UserScore that tracks all of the user's scored lines and totals
    *        hand: ArrayList<Integer> tracks the hand of dice that gets changed during turn
    */
    Dice(int sides, int dice, int rolls, UserScore user, ArrayList<Integer> hand){

        // Set all of the internal variables
        this.num_sides = sides;
        this.num_dice = dice;
        this.num_rolls = rolls;
        this.user = user;  
        this.turn = 0;
        this.finish_turn = false;
        this.hand = hand;

        // Prepare panel colors, size, and layout
        setLayout(new BorderLayout());
        setBackground(new Color(227, 125, 125));
        setBounds(0, 50, 1200, 650);

        // Create panels that will be added to the Dice panel
        dice_panel = new JPanel();
        dice_panel.setBackground(getBackground());

        button_panel = new JPanel();
        checkbox_panel = new JPanel();
        rolls_panel = new JPanel();
        rolls_label = new JLabel();

        // Create Roll Dice Button and add its Action Listener
        roll_dice = new JButton("Roll Dice");
        roll_dice.setPreferredSize(new Dimension(300, 100));
        roll_dice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Check if the scorecard is full and game has ended
                if (user.checkFullScorecard()){

                    endGameDisplay();
                }
                else {
                    // Roll the dice and update the dice images and hand array
                    rollTurn(hand);
                    displayRollsLeft();
                    
                    // Check if turn has ended
                    checkEndTurn();
                    if (turn == 3 || finish_turn){
                        
                        // Provide scoring lines for end of turn
                        new Scorecard(num_sides, num_dice, user, hand);
                        updateUI();
                        
                        // Update regulator variables, refresh current turn panel
                        turn = 0;
                        finish_turn = false;
                        remove(dice_panel);
                        resetCheckboxes();
                        displayRollsLeft();
                    }
                }
            }        
        });
        // Add button to panel 
        button_panel.add(roll_dice);

        // Display Score Button
        display_score = new JButton("Display Score");
        display_score.setPreferredSize(new Dimension(300, 100));
        display_score.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Display user scorecard in new frame
                user.displayUserScore();
            }
        });
        button_panel.add(display_score);

        // Set button panel colors and add to the dice panel
        button_panel.setBackground(getBackground());
        add(button_panel, BorderLayout.PAGE_END);

        // Create new checkboxes and lable them after each die
        check1 = new JCheckBox("Dice 1");
        check2 = new JCheckBox("Dice 2");
        check3 = new JCheckBox("Dice 3");
        check4 = new JCheckBox("Dice 4");
        check5 = new JCheckBox("Dice 5");
        check6 = new JCheckBox("Dice 6");
        check7 = new JCheckBox("Dice 7");

        // Instructions for how to keep dice in user hand after every roll
        instruction_label = new JLabel("Check the boxes of the dice you want to keep : ");
        checkbox_panel.add(instruction_label);

        // Add checkboxes based on amount of sides in game
        checkbox_panel.add(check1);
        checkbox_panel.add(check2);
        checkbox_panel.add(check3);
        checkbox_panel.add(check4);
        checkbox_panel.add(check5);

        if (num_dice > 5)
            checkbox_panel.add(check6);

        if (num_dice == 7)
            checkbox_panel.add(check7);

        for (int i = 0; i < num_dice; i++)
            hand.add(rollDie());

        // Set checkbox_panel's color and add to dice panel
        checkbox_panel.setBackground(getBackground());
        add(checkbox_panel, BorderLayout.PAGE_START);
            
        displayRollsLeft();
    }

    /*
    * Rolls the dice and updates their corresponding images to GUI, also updates hand values
    *
    * @param hand: ArrayList<Integer> that holds the values of the current rolled dice, 
    * @return hand: ArrayList<Integer> hand that is updated from last roll
    */
    public ArrayList<Integer> rollTurn(ArrayList<Integer> hand){

        // Clean panel and set size
        dice_panel.removeAll();
        dice_panel.setBounds(0, 100, 1200, 300);

        // Loop to roll and set individual dice positions
        for (int i = 0; i < num_dice; i++){
            
            int die_roll = rollDie();

            // If die needs to be changed, update hand array value and image icon
            if (changeDice(i + 1)){

                hand.set(i, die_roll);

                ImageIcon image_icon = new ImageIcon(die_roll + ".png");
                Image image = image_icon.getImage();
                image = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

                dice_label = new JLabel(new ImageIcon(image));
                dice_panel.add(dice_label);
            }
            // If die needs to remain the same, keep current corresponding die image on panel
            else{

                ImageIcon image_icon = new ImageIcon(hand.get(i) + ".png");
                Image image = image_icon.getImage();
                image = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

                dice_label = new JLabel(new ImageIcon(image));
                dice_panel.add(dice_label);
            }
            
        }
        // Add panel to the turn panel
        add(dice_panel, BorderLayout.CENTER);
        updateUI();
        
        // Increment turn
        turn++;

        // Return the hand that is ready for scoring or next roll
        return hand;
    }

    /*
    * Checks a die in the hand to see if user wants to keep it or change it out
    *
    * @param dice_num: int of the current die being checked 
    * @return boolean: whether the die being checked is kept or removed
    */
    public boolean changeDice(int dice_num){

        // Switch statment checks the checkboxes of every dice in current hand
        switch(dice_num){

            case 1:
                if (check1.isSelected())
                    return false;
                else
                    return true;
            
            case 2:
                if (check2.isSelected())
                    return false;
                else
                    return true;
            
            case 3:
                if (check3.isSelected())
                    return false;
                else
                    return true;
            
            case 4:
                if (check4.isSelected())
                    return false;
                else
                    return true;
            
            case 5:
                if (check5.isSelected())
                    return false;
                else
                    return true;

            case 6:
                if (check6.isSelected())
                    return false;
                else
                    return true;
            
            case 7:               
                if (check7.isSelected())
                    return false;
                else
                    return true;
        }
        return false;
    }
    
    /*
    * Rolls an individual die, using a Random object.
    *
    * @return int of the value (1-n sides) of the die
    */
    public int rollDie(){

        Random rand = new Random();
        int roll = rand.nextInt(num_sides) + 1;

        return roll;
    }

    /*
    * Displays how many rolls are left to GUI
    */
    public void displayRollsLeft(){

        // Clean rolls left panel
        rolls_panel.removeAll();

        // Refresh values with new count and set font
        rolls_label.setText("Rolls Left: " + (3 - turn));
        rolls_label.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        
        // Add to main panel and set colors
        rolls_panel.add(rolls_label);
        rolls_panel.setBackground(getBackground());
        button_panel.add(rolls_panel);

        add(button_panel, BorderLayout.PAGE_END);
        updateUI();
    }

    /*
    * Checks if the turn is over, given the user has not used all their rolls yet, but all checkboxes are filled
    */
    public void checkEndTurn(){

        if (check1.isSelected() && check2.isSelected() && check3.isSelected() 
            && check4.isSelected() && check5.isSelected() && num_dice == 5)
            this.finish_turn = true;
        
        if (check1.isSelected() && check2.isSelected() && check3.isSelected() 
            && check4.isSelected() && check5.isSelected() && check6.isSelected() && num_dice == 6)
            this.finish_turn = true;
        
        if (check1.isSelected() && check2.isSelected() && check3.isSelected() 
            && check4.isSelected() && check5.isSelected() && check6.isSelected() 
            && check7.isSelected() && num_dice == 6)
            this.finish_turn = true; 
    }

    /*
    * Set all checkboxes back to unchecked at the beginning of every turn
    */
    public void resetCheckboxes(){

        check1.setSelected(false);
        check2.setSelected(false);
        check3.setSelected(false);
        check4.setSelected(false);
        check5.setSelected(false);

        if (num_dice > 5)
            check6.setSelected(false);

        if (num_dice == 7)
            check7.setSelected(false);
    }

    /*
    * Display the final end frame of the game and create button to close program
    */
    public void endGameDisplay(){
        
        // Clean current panel 
        removeAll();

        // Create new panel for end information
        end_game = new JPanel();
        end_game.setLayout(null);
        end_game.setBackground(getBackground());

        // Set text that indicates game is over
        end_label = new JLabel("Thanks for playing! Here's your final scorecard!");
        end_label.setFont(new Font("Baskerville Old Face", Font.BOLD, 36));
        end_label.setBackground(getBackground());
        end_label.setBounds(100, 50, 1000, 200);
        end_game.add(end_label);

        // Create button to close program
        end_button = new JButton("Close Game");
        end_button.setBounds(400, 400, 400, 100);
        end_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Closes program
                System.exit(0);
            }
        });
        end_game.add(end_button); 

        add(end_game, BorderLayout.CENTER);
        updateUI();

        // Display one final scorecard
        user.displayUserScore();
    }
}