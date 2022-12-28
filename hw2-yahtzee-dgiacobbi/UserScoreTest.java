/* The purpose of the class UserScoreTest is to run Java unit tests on certain
 * methods in the UserScore object. 
 * 
 * CPSC 224-01, Fall 2022
 * Programming Assignment #4
 * 
 * @author David Giacobbi
 * @version v1.0 10/17/22
 */

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * UserScoreTest CLASS:
 * 
 * The UserScoreTest Class runs unit tests for the methods written for the UserScore object.
 */
public class UserScoreTest {

    /*
    * Tests to see if setting the 3K attribute also alters the right variables in UserScore
    */
    @Test
    public void TestSet3K(){

        UserScore test_user = new UserScore(6);

        test_user.set3K(20);

        assertTrue(test_user.score_3K == 20 && test_user.used_3K == true);
    }

    /*
    * Tests to see if setting the 4K attribute also alters the right variables in UserScore
    */
    @Test
    public void TestSet4K(){

        UserScore test_user = new UserScore(6);

        test_user.set4K(20);

        assertTrue(test_user.score_4K == 20 && test_user.used_4K == true);
    }

    /*
    * Tests to see if setting the FH attribute also alters the right variables in UserScore
    */
    @Test
    public void TestSetFH(){

        UserScore test_user = new UserScore(6);

        test_user.setFH(20);

        assertTrue(test_user.score_FH == 20 && test_user.used_FH == true);
    }

    /*
    * Tests to see if setting the SS attribute also alters the right variables in UserScore
    */
    @Test
    public void TestSetSS(){

        UserScore test_user = new UserScore(6);

        test_user.setSS(20);

        assertTrue(test_user.score_SS == 20 && test_user.used_SS == true);
    }

    /*
    * Tests to see if setting the LS attribute also alters the right variables in UserScore
    */
    @Test
    public void TestSetLS(){

        UserScore test_user = new UserScore(6);

        test_user.setLS(20);

        assertTrue(test_user.score_LS == 20 && test_user.used_LS == true);
    }

    /*
    * Tests to see if setting the Y attribute also alters the right variables in UserScore
    */
    @Test
    public void TestSetY(){

        UserScore test_user = new UserScore(6);

        test_user.setY(20);

        assertTrue(test_user.score_Y == 20 && test_user.used_Y == true);
    }
}
