/* The purpose of the class ScorecardTest is to run Java unit tests on certain
 * methods in the Scorecard object. 
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #3
 * 
 * @author David Giacobbi
 * @version v1.0 10/2/22
 */

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * ScorecardTest CLASS:
 * 
 * The ScorecardTest Class runs unit tests for the methods written for the Scorecard object.
 */
public class ScorecardTest { 

    /*
    * Given a test ArrayList, checks to see if method can add all the elements
    * together from the array.
    */
    @Test
    public void TestTotalAllDice(){
        
        // Arrange: Declare new Scorecard object and fill test array
        // with rolls that can be checked with totalAllDice() method
        UserScore user = new UserScore(6);

        ArrayList<Integer> test_dice = new ArrayList<Integer>();
        for (int i = 1; i < 7; i++)
            test_dice.add(i);

        Scorecard test_card = new Scorecard(6, 6, user, test_dice);

        // Action: total all the dice with method call
        int test_total = test_card.totalAllDice(test_dice);

        // Assert: check to see if computations were correct
        assertTrue(test_total == 21);
    }

    /*
    * Given a test ArrayList, checks to see if method can find the highest of a kind
    */
    @Test
    public void TestMaxOfAKindFound() {

        // Arrange: Declare new Scorecard object and test ArrayList to check.
        // ArrayList holds a 6-of-a-kind of 3s
        UserScore user = new UserScore(6);

        ArrayList<Integer> test_dice = new ArrayList<Integer>();
        for (int i = 1; i < 7; i++)
            test_dice.add(3);

        Scorecard test_card = new Scorecard(6, 6, user, test_dice);

        // Action: find the max of a kind using method call
        int test_max = test_card.maxOfAKindFound(test_dice);

        // Assert: check to see if it found the 6-of-a-kind
        assertTrue(test_max == 6);
    }

    /*
    * Given a test ArrayList, checks to see if method can find the longest straight
    */
    @Test
    public void TestMaxStraightFound(){

        // Arrange: Declare new Scorecard object and test ArrayList to check.
        // ArrayList holds a large straight
        UserScore user = new UserScore(6);

        ArrayList<Integer> test_dice = new ArrayList<Integer>();
        for (int i = 1; i < 7; i++)
            test_dice.add(i);

        Scorecard test_card = new Scorecard(6, 6, user, test_dice);

        // Action: find the longest straight using method call
        int test_straight = test_card.maxStraightFound(test_dice);

        // Assert: check to see if the large straight is found
        assertTrue(test_straight == 6);
    }

    /*
    * Given a test ArrayList, checks to see if method can find a full house within the set
    */
    @Test
    public void TestFullHouseFound(){

        UserScore user = new UserScore(6);

        ArrayList<Integer> test_dice = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 2, 2, 7, 12, 13, 14));
        Scorecard test_card = new Scorecard(6, 6, user, test_dice);

        boolean test_FH = test_card.fullHouseFound(test_dice);

        assertTrue(test_FH);
    }

    /*
    * Given a test ArrayList, checks to see if method can sort an unsorted array
    */
    @Test
    public void TestSortArray(){

        UserScore user = new UserScore(6);

        ArrayList<Integer> unsorted_dice = new ArrayList<Integer>(Arrays.asList(9, 4, 3, 6, 1, 2, 8, 5, 7));
        ArrayList<Integer> sorted_dice = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        Scorecard test_card = new Scorecard(6, 6, user, sorted_dice);

        assertTrue(sorted_dice.equals(test_card.sortArray(unsorted_dice, 9)));
    }
}
