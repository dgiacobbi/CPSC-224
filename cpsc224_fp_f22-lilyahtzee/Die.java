import java.util.Random;
public class Die {
    private int sideDie;
    /*
    Constructor for Die
    * @return sets roll to zero
    */
    public Die(int sideDie) {
        this.sideDie = sideDie;
    }
    /**
    Rolls die
    * @return random int between 1-side die
    */
    public int rollDie() {
        Random r = new Random();

        return r.nextInt(this.sideDie) + 1;
    }
    /**
    returns the number of sides the die object has
    *
    * @param none
    * @return returns number of sides on die  
    */
    public int getNumSides() {
        return this.sideDie;
    }
}