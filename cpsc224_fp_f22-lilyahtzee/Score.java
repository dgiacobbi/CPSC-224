/**
* This program is the Score object that calculates the score based on the dice rolled
* CPSC 224-01, Fall 2022
* lil' Yahtzee
* No sources to cite.
* 
* @author George Calvert, Henry Stone, David Giacobbi
* @version v1.0 11/29/22
*/
import java.util.ArrayList;
public class Score {
    // variables
    private int threeKind;
    private int fourkind;
    private int fullHouse;
    private int largeStraight;
    private int smallStraight;
    private int chance;
    private int yahtzee;
    private int sideDie;
    private int numDie;
    private int[] lines;
    private int numYahtzee;


    /*
    Constructor for score
    * @return sets score to zero
    */
    public Score(int sideDie, int numDie) {
        this.sideDie = sideDie;
        this.numDie = numDie;
        lines = new int[this.sideDie];
        this.numYahtzee = 0;
    }
    /**
    checks the final dice roll and computes/prints the scores
    *
    * @param Arraylist of the dice rolled
    * @return prints put the scores for that round
    */
    public void checkRolls(ArrayList<Integer> rolls) {

        int twoKind = 0;
        this.threeKind = 0; 
        this.fourkind = 0;
        this.fullHouse = 0;
        this.largeStraight = 0;
        this.smallStraight = 0;
        this.chance = getTotal(rolls);
        this.yahtzee = 0; 

        // getting this.lines score
        int check = 0;
        for(int i = 1; i <= this.sideDie; i++) {
            for(int k = 0; k < rolls.size(); k++) {
                if(i == rolls.get(k)) {
                    this.lines[i - 1] += rolls.get(k); 
                }
            }
        }
        
        for(int j = 0; j < this.sideDie; j++) {
            // scored that number
            if((this.lines[j] % (j + 1)) == 0) {
                check = (this.lines[j] / (j + 1));
            }
            // 2, 3, 4 same
            if(check == 3) {
                this.threeKind = getTotal(rolls);
            }
            else if(check == 4) {
                this.fourkind = getTotal(rolls);
                this.threeKind = getTotal(rolls);
            }
            else if(check == 2) {
                twoKind += 1;
            }
            else {
                // do nothing
            }
        }
        // full house
        if(twoKind >= 1 && this.threeKind > 1) {
            this.fullHouse = 25;
        }  
        if (getMaxStraight(rolls) >= 5) {
            this.largeStraight = 40;
            this.smallStraight = 30;
        }
        if (getMaxStraight(rolls) >= 4) {
            this.smallStraight = 30;
        }
        // checking for this.yahtzee
        for(int k = 0; k < this.sideDie; k++) {
            if(this.lines[k] / (k + 1) == this.numDie) {
                this.fourkind = getTotal(rolls);
                this.threeKind = getTotal(rolls);
                this.yahtzee = 50;
                this.numYahtzee += 1;
            }
        }
    }
    /**
    Computes the score for dice that are in order 
    *
    * @param arraylist of the dice rolled
    * @return an int of the number of dice that are in a row
    */
    public int getMaxStraight(ArrayList<Integer> rolls) {
        //this function returns the length of the longest
        //straight found in a hand
        int maxLength = 1;
        int curLength = 1;
        for(int counter = 0; counter < (this.numDie - 1); counter++){
            if (rolls.get(counter) + 1 == rolls.get(counter + 1) ) //jump of 1
                curLength++;
            else if (rolls.get(counter) + 1 < rolls.get(counter + 1)) //jump of >= 2
                curLength = 1;
            if (curLength > maxLength)
                maxLength = curLength;
        }
        return maxLength;
    }
    /**
    Adds up total of all dice
    *
    * @param array list of the dice rolled
    * @return int of the sum of all the dice rolled 
    */
    public int getTotal(ArrayList<Integer> rolls) {
        int result = 0;
        for(int i = 0; i < rolls.size(); i++) {
            result += rolls.get(i);
        }
        return result;
    }
    /*
    * creates array of integers that holds the lower score cards
    *
    * @param none
    * @return integer array of lower score cards 
    */
    public int[] getLowerScores() {
        int[] scores = {this.threeKind, this.fourkind, this.fullHouse, this.largeStraight, this.smallStraight, this.yahtzee, this.chance};
        return scores;
    }
    /*
    * getter for higher scores
    *
    * @param none
    * @return arrray of intgers with high socore cards
    */
    public int[] getHigherScores() {
        return this.lines;
    }
    /*
    * resetScores
    *
    * @param none
    * @return resets all the scores calcualted for a new round in a yahtzee game
    */
    public void resetScores() {
        this.threeKind = 0;
        this.fourkind = 0;
        this.fullHouse = 0;
        this.largeStraight = 0;
        this.smallStraight = 0;
        this.chance = 0;
        this.yahtzee = 0;
        for(int i = 0; i < this.sideDie; i++) {
            this.lines[i] = 0;
        }   
    }
    /*
    * getNumYahtzee
    *
    * @param none
    * @return returns the number of yahtzees rolled in a game for the bonus calculated at the end game
    */
    public int getNumYahtzee() {
        return this.numYahtzee;
    }
}