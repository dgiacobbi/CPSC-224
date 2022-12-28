/* The purpose of the class Scorecard is to run Java unit tests on certain
 * methods in the Scorecard object. 
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #3
 * 
 * @author David Giacobbi
 * @version v1.0 10/2/22
 */

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import org.junit.Test;

/*
 * DiceTest CLASS:
 * 
 * The DiceTest Class runs unit tests for the methods written for the Dice object.
 */
public class DiceTest {
    
    /*
    * Tests to check that the die roll is always within the side bounds declared by dice object
    */
    @Test
    public void TestRollDie(){

        // Arrange: Create a new dice object with 6 sides
        UserScore user = new UserScore(6);
        ArrayList<Integer> hand = new ArrayList<Integer>();
        Dice test_die = new Dice(6, 6, 3, user, hand);

        // Action: Roll the die using rollDie() method and store in test variable
        int test_roll = test_die.rollDie();

        // Assert: Check to see if roll was within side bounds
        assertTrue(test_roll > 0 && test_roll <= test_die.num_sides);
    }

    /*
    * Tests to check if a value outside the side bounds declared by dice object is false
    */
    @Test
    public void TestRollDieFalse(){

        // Arrange: Create a new dice object with 6 sides
        UserScore user = new UserScore(6);
        ArrayList<Integer> hand = new ArrayList<Integer>();
        Dice test_die = new Dice(6, 6, 3, user, hand);

        // Action: Roll the die using rollDie() method and store in test variable
        int test_roll = test_die.rollDie();

        // Assert: Check to see if roll was within side bounds
        assertFalse(test_roll < 0 || test_roll > test_die.num_sides);
    }
}