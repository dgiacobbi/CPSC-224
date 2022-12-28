/* The purpose of the class Scorecard is to encapsulate any methods pertaining to keeping score after 
 * the dice have been rolled. It is also in charge of sorting the user's hand.
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #5
 * No sources to cite.
 * 
 * @author David Giacobbi
 * @version v3.1 11/11/22
 */

import java.util.ArrayList;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

/*
 * Scorecard CLASS:
 * 
 * The Scorecard class has the bulk of the methods for the Yahtzee game. The main purpose, however,
 * is to sort the array of the user's final dice and display the score of the current round.
 */
public class Scorecard extends JFrame{

    // Game-modifying variables, internal
    public int num_sides, num_dice;
    public UserScore user;

    // GUI components, visual elements
    public JButton line_3K, line_4K, line_FH, line_SS, line_LS, line_Y, line_side;
    public JPanel score_panel;
    public JLabel instructions, sorted_hand;

    /*
    * Scorecard constructor used to pass in different dice attributes for Lizard Spock Yahtzee.
    *
    * @param sides: int number of sides on each die 
    *        dice: int number of total amount of dice in play
    *        user: UserScore tracks all player points
    *        hand: ArrayList<Integer> holds the current hand being scored
    */
    Scorecard(int sides, int dice, UserScore user, ArrayList<Integer> hand) {

        // Set internal variables
        this.num_sides = sides;
        this.num_dice = dice;
        this.user = user;

        // Create a new panel hold all scoring buttons and sorted hand
        score_panel = new JPanel();
        score_panel.setBackground(Color.LIGHT_GRAY);

        // Set frame size, operations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 575);

        // Add instructions on to guide user through game
        instructions = new JLabel("Select One of the Available Scoring Lines:");
        score_panel.add(instructions);

        // Calculate the scores and display available button scoring lines
        calculateScore(hand);

        // Add to frame and show
        add(score_panel);
        setVisible(true);
    }

    /*
    * Calculates each possibility that a user can score points from set of dice.
    * Use of the sorted array is required to check for possible dice scores.
    *
    * @param hand: ArrayList<Integer> hold the values of user's final hand of dice for scoring
    */
    public void calculateScore(ArrayList<Integer> hand){

        //Sort and display hand
        displaySortedHand(hand);

        //Upper scorecard available buttons printed  
        printUpperScorecard(hand);   
        
        //Lower scorecard available buttons printed
        printLowerScorecard(hand);   
    }
    
    /*
    * Finds the total amount of matching dice in the user's final set.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    * @return int: amount of matching dice in the set
    */
    public int maxOfAKindFound(ArrayList<Integer> hand){

        int max_count = 0;
        int curr_count;
        for (int die_val = 1; die_val <= num_sides; die_val++)
        {
            curr_count = 0;
            for (int die_pos = 0; die_pos < num_dice; die_pos++)
            {
                if (hand.get(die_pos) == die_val)
                    curr_count++;
            }
            if (curr_count > max_count)
                max_count = curr_count;
        }
        return max_count;
    }

    /*
    * Checks the sorted ArrayList to see if there are any straights. If so, how long the
    * straight is which varies the amount of points distributed.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    * @return int: length of the longest straight in the dice set
    */
    public int maxStraightFound(ArrayList<Integer> hand){

        hand = sortArray(hand, num_dice);

        int max_length = 1;
        int cur_length = 1;
        for (int counter = 0; counter < (num_dice - 1); counter++){
            
            if (hand.get(counter) + 1 == hand.get(counter + 1) ) //jump of 1
                cur_length++;
            else if (hand.get(counter) + 1 < hand.get(counter + 1)) //jump of >= 2
                cur_length = 1;
            if (cur_length > max_length)
                max_length = cur_length;
        }
        return max_length;
    }

    /*
    * Determines if there is a full house of some kind in the dice set. A full house
    * consists of one three of a kind and one pair of dice with different values.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    * @return boolean: whether or not there is a full house present in dice set
    */
    public boolean fullHouseFound(ArrayList<Integer> hand){

        boolean found_FH = false;
        boolean found_3K = false;
        boolean found_2K = false;
        int curr_count;

        for (int die_value = 1; die_value <=6; die_value++)
        {
            curr_count = 0;
            for (int die_pos = 0; die_pos < 5; die_pos++)
            {
                if (hand.get(die_pos) == die_value)
                    curr_count++;
            }
            if (curr_count == 2)
                found_2K = true;
            if (curr_count == 3)
                found_3K = true;
        }
        if (found_2K && found_3K)
            found_FH = true;
        return found_FH;
    }

    /*
    * Displays the upper scoring for Yahtzee. Used to calculate total score for round.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    */
    private void printUpperScorecard(ArrayList<Integer> hand){

        for (int die_val = 1; die_val <= num_sides; die_val++)
        {
            //Check if scoring line is still available
            if (user.upper_used.get(die_val - 1) == -1){

                int die = die_val;
                int curr_count = 0;

                for (int die_pos = 0; die_pos < (num_dice - 1); die_pos++){

                    if (hand.get(die_pos) == die_val)
                        curr_count++;
                }

                int count = curr_count;

                // Scoring Button is created if line is available
                line_side = new JButton("Score " + (die_val * curr_count) + " on the " + die_val + " line\n");
                line_side.setSize(400, 60);
                line_side.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Sets user upper score for button pressed
                        user.upper_scores.set(die - 1, die * count);
                        user.upper_used.set(die - 1, 0);

                        // Once selected, close scoring frame and show scorecard
                        user.displayUserScore();
                        dispose();
                    }
                });
                // Add button to the score panel
                score_panel.add(line_side);
            }
        }
    }

    /*
    * Calculates and displays the lower scoring for Yahtzee. Used to determine how many
    * straights and of a kind scoring lines the user can choose from.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    */
    private void printLowerScorecard(ArrayList<Integer> hand){

        //Max of a Kind Line
        for (int i = 3; i < num_dice; i++){

            if (maxOfAKindFound(hand) >= i){

                //Check if scoring line is still available
                if (i == 3 && user.used_3K == false){

                    // Create score button if available and 3K is present
                    line_3K = new JButton("Score " + totalAllDice(hand) + " on the 3 of a Kind line");
                    line_3K.setSize(400, 60);
                    line_3K.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Set UserScore values and print scorecard, close score frame
                            user.set3K(totalAllDice(hand));
                            user.displayUserScore();
                            dispose();
                        }
                    }); 
                    score_panel.add(line_3K);
                }

                //Check if scoring line is still available
                if (i == 4 && user.used_4K == false){

                    // Create score button if available 4K present
                    line_4K = new JButton("Score " + totalAllDice(hand) + " on the 4 of a Kind line");
                    line_4K.setSize(400, 60);
                    line_4K.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Set UserScore values and print scorecard, close the frame
                            user.set4K(totalAllDice(hand)); 
                            user.displayUserScore();
                            dispose();
                        }
                    });
                    // Add button to panel 
                    score_panel.add(line_4K);
                }
            }
            //Determine if scoring line with 0 is possible
            else if (i == 3 && user.used_3K == false) {

                // Create score button and add action listener
                line_3K = new JButton("Score 0 on the 3 of a Kind line");
                line_3K.setSize(400, 60);
                line_3K.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore values and close frame
                        user.set3K(0);
                        user.displayUserScore();
                        dispose();
                    }
                });
                // Add button to panel 
                score_panel.add(line_3K);
            }

            else if (i == 4 && user.used_4K == false){

                // Create score button and add action listener
                line_4K = new JButton("Score 0 on the 4 of a Kind line");
                line_4K.setSize(400, 60);
                line_4K.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore values and close frame
                        user.set4K(0);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_4K);
            }
        }

        // Full House Line w/ user availability check
        if (user.used_FH == false){

            if (fullHouseFound(hand)){

                // Create score button and add action listener
                line_FH = new JButton("Score 25 on the Full House line");
                line_FH.setSize(400, 60);
                line_FH.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore value, display scorecard, and close frame
                        user.setFH(25);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_FH);
            }   
            else {

                // Create button and add action listener
                line_FH = new JButton("Score 0 on the Full House line");
                line_FH.setSize(400, 60);
                line_FH.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore, display scorecard, and close frame
                        user.setFH(0);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_FH);
            }
        }

        // Small Straight Line w/ user availability check
        if (user.used_SS == false){

            if (maxStraightFound(hand) >= (num_dice - 1)){

                // Create button and add action listener
                line_SS = new JButton("Score " + ((num_dice - 1)*10) + " on the Small Straight line");
                line_SS.setSize(400, 60);
                line_SS.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore, display scorecard, and close frame
                        user.setSS((num_dice - 1)*10);
                        user.displayUserScore();
                        dispose();
                    }
                });
                // Add button to panel 
                score_panel.add(line_SS);
            }
            else {

                // Create button and add action listener
                line_SS = new JButton("Score 0 on the Small Straight line");
                line_SS.setSize(400, 60);
                line_SS.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore value, display scorecard, and close frame
                        user.setSS(0);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_SS);
            }
        }

        // Large Straight Line w/ user availability check
        if (user.used_LS == false){

            if (maxStraightFound(hand) >= num_dice){

                // Create button and add action listener
                line_LS = new JButton("Score " + (num_dice*10) + " on the Large Straight line");
                line_LS.setSize(400, 60);
                line_LS.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore value, display scorecard, and close frame
                        user.setLS(num_dice*10);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_LS);
            }
            else {

                // Create button and add action listener
                line_LS = new JButton("Score 0 on the Large Straight line");
                line_LS.setSize(400, 60);
                line_LS.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore value, display scorecard, and close frame
                        user.setLS(0);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_LS);
            }
        }
        

        // Yahtzee Line w/ user availability check
        if (user.used_Y == false){

            if (maxOfAKindFound(hand) >= num_dice) {

                // Create button and add action listener
                line_Y = new JButton("Score 50 on the Yahtzee line");
                line_Y.setSize(400, 60);
                line_Y.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore value, display scorecard, and close frame
                        user.setY(50);
                        user.displayUserScore();
                        dispose();
                    }
                });
                // Add button to panel 
                score_panel.add(line_Y);
            }
            else {

                // Create button and add action listener
                line_Y= new JButton("Score 0 on the Yahtzee line");
                line_Y.setSize(400, 60);
                line_Y.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Set UserScore value, display scorecard, and close frame
                        user.setY(0);
                        user.displayUserScore();
                        dispose();
                    }
                }); 
                // Add button to panel
                score_panel.add(line_Y);
            }
        }
    }

    /*
    * Sorts the ArrayList in ascending order for scoring analysis.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    *        size: int that represents the size of the array being sorted
    */
    public ArrayList<Integer> sortArray(ArrayList<Integer> array, int size){
        
        boolean swap;
        int temp;

        do{
            swap = false;
            for (int count = 0; count < (size - 1); count++)
            {
                if (array.get(count) > array.get(count + 1))
                {
                    temp = array.get(count);
                    array.set(count, array.get(count + 1));
                    array.set(count + 1, temp);
                    swap = true;
                }
            }
        } while (swap);

        return array;
    }

    /*
    * Uses the sort method in Scorecard and prints out the sorted dice to the user's display
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    */
    private void displaySortedHand(ArrayList<Integer> hand) {

        //Hand need to be sorted to check for straights
        hand = sortArray(hand, num_dice);

        //Display sorted hand in the scoring panel
        sorted_hand = new JLabel();

        String label = "Here is your sorted hand: ";
        for (int die_num = 0; die_num < num_dice; die_num++)
            {
                label += hand.get(die_num) + " ";
            }
        // Set and add text to panel
        sorted_hand.setText(label);
        score_panel.add(sorted_hand);
    }

    /*
    * Adds up the integer values of all the dice in the final set.
    *
    * @param hand: ArrayList<Integer> that holds the values of user's final hand of dice
    * @return int: total sum of all the dice in the set
    */
    public int totalAllDice(ArrayList<Integer> hand){

        int total = 0;
        for (int pos = 0; pos < num_dice; pos++){
            total += hand.get(pos);
        }
        return total;
    }
}