/* The purpose of the UserScore class is to keep a running score tally
 * of each of the hands that user has already played. As the user picks
 * scores, those lines are removed from the options and the scores are 
 * added to the table that can the be displayed to the screen.
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #5
 * No sources to cite.
 * 
 * @author David Giacobbi
 * @version v2.1 11/11/22
 */

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;

/*
 * UserScore CLASS:
 * 
 * The UserScore's main purpose is to keep track of the Yahtzee player's score throughout multiple
 * rounds of rolling. It holds the values of each scoring line, and it keeps track of whether the
 * score has been used or not. Lastly, it displays a scorecard for the player to track in between
 * rolls and rounds.
 */
public class UserScore {

    //Total scores for player
    public int upper_total, lower_total, sub_total, bonus, grand_total = 0;

    //Lower scoring for player
    public int score_3K, score_4K, score_FH, score_SS, score_LS, score_Y = 0;
    public boolean used_3K, used_4K, used_FH, used_SS, used_LS, used_Y = false;

    //Dice attributes for upper scoring
    public int num_sides;
    public ArrayList<Integer> upper_scores;
    public ArrayList<Integer> upper_used;

    // GUI components to display scorecard
    private JFrame frame;
    private JTextArea score_area;
    private JPanel score_panel;
    private JButton score_button;
    private String scorecard;
    
    /*
    * UserScore constructor used to set up the upper scoring ArrayLists.
    * Upper scoring is based on number of sides on the dice, so it is subject
    * to change from game to game.
    *
    * @param sides: int of number of sides on this game's dice
    */
    UserScore(int sides){

        this.num_sides = sides;
        this.upper_scores = new ArrayList<Integer>();
        this.upper_used = new ArrayList<Integer>();

        //Load the score array with zeroes until changed
        //Load the used array with -1 until line is used for checking
        for (int i = 0; i < num_sides; i++){
            this.upper_scores.add(i, 0);
            this.upper_used.add(i, -1);
        }
    }

    /*
    * Dynamically creates a current user scoreboard frame. This is done using
    * the variables that in this object that hold all the integer values, printing
    * them in a JTextArea().
    */
    public void displayUserScore(){

        // Create a new frame and panel to put the JTextArea and JButton on
        frame = new JFrame();
        score_panel = new JPanel();

        // Start appending scorecard string with scorecard that originally printed to terminal
        scorecard = ("------------------------------\n" +
                     "USER SCORECARD                \n" + 
                     "------------------------------\n" +
                     "Line                                          Score  \n" +
                     "------------------------------\n");

        //Upper Scorecard Counts
        sub_total = 0;

        //Print out each possible scoring line, depending on number of sides
        for (int die_val = 1; die_val <= num_sides; die_val++){
            
            scorecard += die_val + "                                                  " + upper_scores.get(die_val - 1) + "\n";
            sub_total += upper_scores.get(die_val - 1);
        }

        //Check for bonus in upper scoring and set upper total
        if (sub_total >= 63)
            bonus = 35;
        
        upper_total = sub_total + bonus;

        scorecard += "------------------------------\n" +
                     "Sub Total                                     " + sub_total + "\n" +
                     "Bonus                                          " + bonus + "\n" +
                     "------------------------------\n" +
                     "Upper Total                                  " + upper_total + "\n";

        // Lower Scorecard Counts
        //Set totals based on changes to certain lines in the lower scorecard
        lower_total = score_3K + score_4K + score_FH + score_SS + score_LS + score_Y;
        grand_total = lower_total + upper_total;

        scorecard += "------------------------------\n" +
                     "3K                                                " + score_3K + "\n" +
                     "4K                                                " + score_4K + "\n" +
                     "FH                                               " + score_FH + "\n" +
                     "SS                                                " + score_SS + "\n" +
                     "LS                                                " + score_LS + "\n" +
                     "Y                                                 " + score_Y + "\n" +
                     "------------------------------\n" +
                     "Lower Total                                 " + lower_total + " \n" +
                     "------------------------------\n" +
                     "Grand Total                                 " + grand_total + "\n" +
                     "------------------------------\n";

        // Create a JTextArea to hold the scorecard string, make it uneditable to user
        score_area = new JTextArea(scorecard);
        score_area.setEditable(false);
        score_panel.add(score_area);

        // Create a JButton that closes the window of the scorecard but not the whole program
        score_button = new JButton("Close Scorecard");
        score_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Close window
                frame.dispose();
            }
        });  
        score_panel.add(score_button);

        // Add scoreboard and button to frame and set frame colors/size
        frame.add(score_panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 575);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setVisible(true);
    }

    /*
    * Sets the 3 of a Kind scoring line to a certain score. It also
    * alters its used boolean so that it can't be changed again during this game.
    *
    * @param value: int of new score to set for line
    */
    public void set3K(int value){

        this.score_3K = value;
        this.used_3K = true;
    }

    /*
    * Sets the 4 of a Kind scoring line to a certain score. It also
    * alters its used boolean so that it can't be changed again during this game.
    *
    * @param value: int of new score to set for line
    */
    public void set4K(int value){
        
        this.score_4K = value;
        this.used_4K = true;
    }

    /*
    * Sets the Full House scoring line to a certain score. It also
    * alters its used boolean so that it can't be changed again during this game.
    *
    * @param value: int of new score to set for line
    */
    public void setFH(int value){
        
        this.score_FH = value;
        this.used_FH = true;
    }

    /*
    * Sets the Small Straight scoring line to a certain score. It also
    * alters its used boolean so that it can't be changed again during this game.
    *
    * @param value: int of new score to set for line
    */
    public void setSS(int value){
        
        this.score_SS = value;
        this.used_SS = true;
    }

    /*
    * Sets the Large Straight scoring line to a certain score. It also
    * alters its used boolean so that it can't be changed again during this game.
    *
    * @param value: int of new score to set for line
    */
    public void setLS(int value){
        
        this.score_LS = value;
        this.used_LS = true;
    }

    /*
    * Sets the Yahtzee scoring line to a certain score. It also
    * alters its used boolean so that it can't be changed again during this game.
    *
    * @param value: int of new score to set for line
    */
    public void setY(int value){
        
        this.score_Y = value;
        this.used_Y = true;
    }

    /*
    * Checks if the scorecard card is completely full. If that is the case, the
    * game needs to move to the end game frame and close the roll turn frame.
    *
    * @return: boolean of whether every score line is filled or not
    */
    public boolean checkFullScorecard(){

        // Check lower scorecard values
        if (used_3K && used_4K && used_FH && used_SS && used_LS && used_Y){

            // Check upper scorecard values, depending on number of sides
            int count = 0;
            for (int i = 0; i < num_sides; i++){
                
                if (upper_used.get(i) == 0)
                    count++;
            }
            if (count == num_sides)
                return true;
        }
        return false;
    }
}
