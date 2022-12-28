/* The purpose of the class GameTest is to run Java unit tests on certain
 * methods in the Game object. 
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #4
 * 
 * @author David Giacobbi
 * @version v1.0 10/17/22
 */

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/*
 * GameTest CLASS:
 * 
 * The GameTest Class runs unit tests for the methods written for the Game object.
 */
public class GameTest {

    /*
    * Tests to check if the game class can be instantiated
    */
    @Test
    public void TestGame(){

        Game test_game = new Game(6, 5);
        assertNotNull(test_game);
    }
}
