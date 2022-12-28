/* The purpose of the class PlayYahtzeeTest is to run Java unit tests on certain
 * methods in the PlayYahtzee object. 
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
 * PlayYahtzeeTest CLASS:
 * 
 * The PlayYahtzeeTest Class runs unit tests for the methods written for the PlayYahtzee object.
 */
public class PlayYahtzeeTest {
    
    /*
    * Tests to check that the class can be instantiated
    */
    @Test
    public void TestMain(){

        PlayYahtzee play = new PlayYahtzee();
        assertNotNull(play);
    }
}
