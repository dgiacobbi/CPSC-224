/**
* This program is the MAkePALyers object that creates players for the game
* CPSC 224-01, Fall 2022
* lil' Yahtzee
* No sources to cite.
* 
* @author George Calvert, Henry Stone, David Giacobbi
* @version v1.0 11/29/22
*/
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class MakePlayers {
    // asks for player names and creates each object along with their scorecards
    // starts game of yahtzee
    private int numDie;
    private int numTurns;
    private int sideDie;
    private int numPlayers;
    /*
    Constructor for MakePlayers
    * @param number dice, number turns, side of each die and then the number of players
    * @return initializes all variables needed
    */
    public MakePlayers(int numDie, int numTurns, int sideDie, int numPlayers) {
        this.numDie = numDie;
        this.numTurns = numTurns;
        this.sideDie = sideDie;
        this.numPlayers = numPlayers;
    }
    /**
    Creates players for the game
    *
    * @param nothing
    * @return Creates a GUI fo rthe user to interact and enter player names
    */
    public void getPlayers() {

        JFrame PlayerFrame = new JFrame("Players");
        PlayerFrame.setLayout(new BorderLayout());

        JPanel textP = new JPanel();
        textP.setBackground(new Color(184, 184, 184));
        textP.setLayout(new BoxLayout(textP, BoxLayout.PAGE_AXIS));

        JPanel buttonP = new JPanel();
        buttonP.setBackground(new Color(184, 184, 184));

        // Dynamically create text boxes to enter player names in
        Vector<JTextField> textBoxes = new Vector<JTextField>();
        for(int i = 0; i < this.numPlayers; i++) {

            JPanel newPlayer = new JPanel();
            newPlayer.setBackground(new Color(184, 184, 184));
            
            textBoxes.add(new JTextField());

            textBoxes.get(i).setEditable(true);
            textBoxes.get(i).setFont(new Font("Britannic Bold", Font.PLAIN, 30));
            textBoxes.get(i).setColumns(30);

            JLabel newLabel = new JLabel("Player " + (i + 1));
            newLabel.setFont(new Font("Copperplate", Font.BOLD, 24));
            newLabel.setForeground(Color.white);

            newPlayer.add(newLabel);
            newPlayer.add(textBoxes.get(i));
            textP.add(newPlayer);
        }

        // button to start playing
        JButton play = new JButton("Play Game");

        play.setFont(new Font("Britannic Bold", Font.BOLD, 42));
        play.setPreferredSize(new Dimension(600, 100));

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println("Play Game button Clicked");
                String[] playerNames = new String[numPlayers];

                addSoundEffect("sound-effects/Button.wav");

                // getting names added
                for(int j = 0; j < numPlayers; j++) {

                    if (textBoxes.get(j).getText().equals(""))
                        playerNames[j] = "Player " + (j + 1);

                    else if (textBoxes.get(j).getText().length() > 30)
                        playerNames[j] = textBoxes.get(j).getText().substring(0, 29);
                        
                    else
                        playerNames[j] = textBoxes.get(j).getText();
                }
                // close frame
                PlayerFrame.dispose();
                Yahtzee game = new Yahtzee(numDie, sideDie, numPlayers, playerNames);
                game.playGame();
            }
        });
        // add button
        buttonP.add(play);

        // labels
        JPanel titleP = new JPanel();
        titleP.setBackground(new Color(184, 184, 184));

        JLabel enterPlayerNames = new JLabel("Enter Your Player Names:");
        enterPlayerNames.setFont(new Font("Copperplate", Font.BOLD, 56));
        enterPlayerNames.setForeground(Color.red);
        titleP.add(enterPlayerNames);

        // add panels
        PlayerFrame.add(titleP, BorderLayout.PAGE_START);
        PlayerFrame.add(textP, BorderLayout.CENTER);
        PlayerFrame.add(buttonP, BorderLayout.PAGE_END);

        // frame settings
        PlayerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlayerFrame.setSize(1200,800);
        PlayerFrame.setLocationRelativeTo(null);
        PlayerFrame.setResizable(false);
        PlayerFrame.setVisible(true);  
    }

    public void addSoundEffect (String filepath){

        try{
            String soundName = filepath;
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        catch(Exception exc){
            System.err.println(exc.getMessage());
        }
    }
}
