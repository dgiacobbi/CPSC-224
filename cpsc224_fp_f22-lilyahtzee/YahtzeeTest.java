/**
* This runs the unit tests on system internal objects.
* CPSC 224-01, Fall 2022
* lil' Yahtzee
* No sources to cite.
* 
* @author George Calvert, Henry Stone, David Giacobbi
* @version v1.0 11/29/22
*/

import static org.junit.Assert.assertTrue;
import java.beans.Transient;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class YahtzeeTest {

    // Die Object
    @Test
    public void testGetNumSides(){
        //create a die with 6 sides
        Die testDie = new Die(6);
        //create a variable set to 6 to see if the die is created with the correct number of sides
        int realSides = 6;
        //assert
        assert(testDie.getNumSides() == realSides);
    }

    @Test
    public void testRollDie(){
        //create a die with 6 sides
        Die testDie = new Die(6);
        //max number it could return is 6
        int maxNum = 6;
        //assert
        assert(testDie.rollDie() <= maxNum);
    }

    //Player Object
    @Test
    public void testPlayerRollDieArrayLength(){
        //create a new player
        Player testPlayer = new Player("Test", 5, 6);
        //the length of the rolls array should be 5;
        int checkLength = 5;
        //call rollDice to set up the array 
        testPlayer.rollDice();

        assert(testPlayer.getHand().size() == checkLength);
    }

    @Test
    public void testGetSideDie(){
        //create a new player
        Player testPlayer = new Player("Test", 5, 8);
        //the number of sides should return 6
        int numSide = 8;

        assert(testPlayer.getSideDie() == numSide);
    }

    @Test
    public void testGetGrandTotal(){
        //create a new player
        Player testPlayer = new Player("Test", 5, 12);
        //the starting grand total should be 0
        int testTotal = 0;

        assert(testPlayer.getTotal() == testTotal);
    }
    
    //Score Object
    @Test
    public void testCheckRoll(){
        //create a new score object
        Score testScore = new Score(6, 5);
        //create an array of rolls to give a yahtzee
        ArrayList<Integer> testHand = new ArrayList<Integer>(Arrays.asList(5,5,5,5,5));
        //run check score
        testScore.checkRolls(testHand);
        //check and see if a yahtzee occured
        assert(testScore.getNumYahtzee() == 1);
    }

    @Test
    public void testGetTotal(){
        //create a new score object
        Score testScore = new Score(6, 5);
        //create an array of a hand
        ArrayList<Integer> testHand = new ArrayList<Integer>(Arrays.asList(1,1,2,2,4));
        //the total of this hand should be 10
        int totalScore = 10;

        assert(testScore.getTotal(testHand) == totalScore);
    }

    @Test
    public void testGetMaxStraight(){
        //create a new score object
        Score testScore = new Score(6, 5);
        //create an array of a hand, a yahtzee will occur
        ArrayList<Integer> testHand = new ArrayList<Integer>(Arrays.asList(1,2,3,1,1));
        //should return 3
        int maxStraight = 3;

        assert(testScore.getMaxStraight(testHand) == maxStraight);
    }
}
