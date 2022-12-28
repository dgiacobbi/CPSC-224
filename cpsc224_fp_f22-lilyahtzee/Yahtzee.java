/**
* This program is the Yahtzee object and displays and caclulates the main functions of the game
* CPSC 224-01, Fall 2022
* lil' Yahtzee
* No sources to cite.
* 
* @author George Calvert, Henry Stone, David Giacobbi
* @version v1.0 11/29/22
*/
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Yahtzee {
    // runs ands displays game of yahtzee
    private int numDie;
    private int sideDie;
    private int numPlayers;
    private String[] playerNames;
    private Vector<Player> playerVec;

    private ArrayList<Integer> hand;
    private Score score;
    private Die die;
    private int player;

    /*
    Constructor for score
    * @return initializes all variables needed
    */
    public Yahtzee(int numDie, int sideDie, int numPlayers, String[] playerNames) {

        this.numDie = numDie;
        this.sideDie = sideDie;
        this.numPlayers = numPlayers;
        this.playerNames = playerNames;

        this.die = new Die(this.sideDie);
        this.score = new Score(this.sideDie, this.numDie);
        this.playerVec = new Vector<Player>();
        this.player = 0;
        this.createPlayers();
    }
    /**
    Runs the game of Yahtzee
    *
    * @param nothing
    * @return main functions of the game yahtzee and the GUI
    */
    public void playGame() {
        // check if scorecards are full for all players
        if(checkEndGame()) {
            EndGame eg = new EndGame(playerVec);
            eg.end();
        }
        else {
            // loop for each player until they want to end the game or all lines are gone
            // another loop for how many rolls per player

            // Frame settings
            JFrame GameFrame = new JFrame("Yahtzee");
            GameFrame.setLayout(new GridLayout(4,0));

            JPanel PlayerTurn = new JPanel(new FlowLayout());
            PlayerTurn.setBackground(new Color(184, 184, 184));

            JPanel Select = new JPanel();
            Select.setBackground(new Color(184, 184, 184));

            JLabel instL = new JLabel("Select the boxes of the dice you want to keep:");
            instL.setFont(new Font("Brittanic Bold", Font.PLAIN, 16));
            Box s = Box.createHorizontalBox();
            // s.add(instL);
            // Select.add(instL);

            JPanel Options = new JPanel(new FlowLayout());
            Options.setBackground(new Color(184, 184, 184));

            JPanel DiceHand = new JPanel(new FlowLayout());
            DiceHand.setBackground(new Color(184, 184, 184));

            JLabel playerDisplay = new JLabel(playerNames[player] + "'s Turn");
            playerDisplay.setFont(new Font("Copperplate", Font.PLAIN, 48));
            playerDisplay.setForeground(Color.red);
            PlayerTurn.add(playerDisplay);

            // roll player dice
            playerVec.get(player).rollDice();
            this.hand = playerVec.get(player).getHand();

            // lists of images of die and the checkbox for each die
            ArrayList<JCheckBox> images = displayHand(this.hand);
            ArrayList<JCheckBox> selection = makeSelections();

            for(int i = 0; i < images.size(); i++) {
                DiceHand.add(images.get(i));
                Box checks = Box.createVerticalBox();
                JLabel checkL = new JLabel("D" + (i + 1)+ ":");
                checkL.setFont(new Font("Britannic Bold", Font.BOLD, 15));
                checks.add(checkL);
                checks.add(selection.get(i));
                Select.add(checks);
                // Select.add(selection.get(i));
            }
            // s.add(checks);
            // s.setBounds(500, 800, 200, 20);
            Select.add(s);
            // Select.add(checks);

            // add buttons for re roll and display score card
            JButton reRoll = new JButton("Reroll");
            reRoll.setFont(new Font("Britannic Bold", Font.BOLD, 30));
            reRoll.setPreferredSize(new Dimension(300, 100));
            reRoll.addActionListener(new ActionListener() {
                int turn = 0;
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Reroll button clicked");

                    // Check if all boxes are selected and ready to score
                    int count = 0;
                    for (int i = 0; i < numDie; i++){

                        if (selection.get(i).isSelected())
                            count++;

                        if (count == numDie)
                            turn = 3;
                    }

                    if(turn <= 1) {

                        addSoundEffect("sound-effects/RollDice.wav");

                        for(int j = 0; j < numDie; j++) {
                            // check dice is not selected
                            if(!selection.get(j).isSelected()) {
                                // rerolls die and updates image
                                hand.set(j, playerVec.get(player).getDie().rollDie());
                                images.get(j).setIcon(getImage(hand.get(j)));
                            }
                        }
                        turn += 1;
                        // button to go to choose scoring screen
                        if(turn == 2) {
                            reRoll.setText("Go to Scoring");
                            GameFrame.revalidate();
                            GameFrame.repaint();
                        }
                        // update dice hand panel
                        DiceHand.revalidate();
                        DiceHand.repaint();
                    }
                    // turn ended
                    else {
                        
                        addSoundEffect("sound-effects/Button.wav");

                        // sort and check rolls of the hand
                        playerVec.get(player).sortRolls();
                        score.checkRolls(hand);
                        // add radio button to group so you can only select one
                        ButtonGroup bg = new ButtonGroup();
                        ArrayList<JRadioButton> choices = playerVec.get(player).getScoreCard(score);
                        // remove the button at bottom of GUI
                        GameFrame.remove(Options);
                        GameFrame.remove(Select);
                        // add radio button to panel and then frame
                        JPanel p = new JPanel();
                        p.setBackground(new Color(184, 184, 184));
                        for(int k = 0; k < choices.size(); k++) {
                            // check if scoring line for player was already used
                            if(!playerVec.get(player).checkUsed(choices.get(k).getName())) {
                                bg.add(choices.get(k));
                                p.add(choices.get(k));
                            }
                        }
                        // Add score to score card button
                        JPanel addScoreP = new JPanel();
                        addScoreP.setBackground(new Color(184, 184, 184));

                        JButton addScore = new JButton("Add Score");
                        addScore.setFont(new Font("Britannic Bold", Font.BOLD, 28));
                        addScore.setPreferredSize(new Dimension(300, 100));

                        addScore.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {

                                System.out.println("Added Score");

                                addSoundEffect("sound-effects/AddScore.wav");

                                // check which score the user selected
                                for(int j = 0; j < choices.size(); j++) {
                                    if(choices.get(j).isSelected()) {
                                        // add score to score card
                                        playerVec.get(player).addScore(choices.get(j).getName(), score);
                                        GameFrame.dispose();

                                        JFrame frame = new JFrame("End of Turn");
                                        JPanel panel = new JPanel();
                                        panel.setBackground(new Color(184, 184, 184));
            
                                        // display scorecard
                                        panel.add(playerVec.get(player).displayScoreCard());
                                        panel.add(DiceHand);
                                        // add end turn button
                                        JButton endTurn = new JButton("End Turn");
                                        endTurn.setPreferredSize(new Dimension(400, 100));
                                        endTurn.setFont(new Font("Britannic Bold", Font.BOLD, 36));
                                        endTurn.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {

                                                addSoundEffect("sound-effects/Button.wav");

                                                // reset score object
                                                score.resetScores();
                                                System.out.println("end turn");
                                                if(player == numPlayers - 1) {
                                                    player = 0;
                                                }
                                                else {
                                                    player++;
                                                }
                                                // next player
                                                hand.clear();
                                                images.clear();
                                                selection.clear();
                                                frame.dispose();
                                                playGame();   
                                            } 
                                        });
                                        // add button and frame settings
                                        panel.add(endTurn);
                                        frame.add(panel);
                                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        frame.setSize(1000,800);
                                        frame.setLocationRelativeTo(null);
                                        frame.setResizable(false);
                                        frame.setVisible(true);
                                    }
                                    else {
                                        // do nothing since nothing was selected
                                    }
                                }
                            }
                        });
                        // add button and upate full gameframe
                        addScoreP.add(addScore);
                        GameFrame.add(p);
                        GameFrame.add(addScoreP);
                        GameFrame.repaint();
                        GameFrame.revalidate();

                    }
                }
            });
            // button to display scorecard
            JButton dispScoreCard = new JButton("Display Score Card");
            dispScoreCard.setFont(new Font("Britannic Bold", Font.BOLD, 24));
            dispScoreCard.setPreferredSize(new Dimension(300, 100));
            dispScoreCard.addActionListener(new ActionListener() {
                int times = 0;
                public void actionPerformed(ActionEvent e) {
                    times += 1;
                    if(times <= 1) {
                        addSoundEffect("sound-effects/Button.wav");

                        // add scorecard to new fraem and display it to user 
                        System.out.println("Display Score card clicked");
    
                        JPanel card = playerVec.get(player).displayScoreCard();
                        JFrame cardFrame = new JFrame("Score Card");
                        JPanel p = new JPanel();
                        p.setBackground(new Color(184,184,184));
    
                        JButton close = new JButton("Close");
                        close.setFont(new Font("Britannic Bold", Font.BOLD, 18));
                        close.setPreferredSize(new Dimension(100, 40));
                        close.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
    
                                addSoundEffect("sound-effects/Button.wav");
                                cardFrame.dispose();
                                System.out.println("Close ScoreCard Frame");
                            }
                        });
                        p.add(close);
                        card.add(p);
                        cardFrame.add(card);
                        
                        // so it doenst stop full program
                        cardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        cardFrame.setSize(350,(sideDie * 10) + 400);
                        cardFrame.setLocationRelativeTo(GameFrame);
                        cardFrame.setResizable(false);
                        cardFrame.setVisible(true); 
                    }
                }
            });
            // add buttons
            Options.add(reRoll);
            Options.add(dispScoreCard);

            // add panels
            GameFrame.add(PlayerTurn);
            GameFrame.add(DiceHand);
            GameFrame.add(Select);
            GameFrame.add(Options);

            // GAmeFrame settings
            GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GameFrame.setSize(1000,800);
            GameFrame.setLocationRelativeTo(null);
            GameFrame.setResizable(false);
            GameFrame.setVisible(true); 
        } 

    }
    /**
    checks if each players score card is full
    *
    * @param nothing
    * @return boolean of whether or not all cards are full
    */
    public boolean checkEndGame() {
        for(int i = 0; i < this.playerVec.size(); i++ ){
            if(!playerVec.get(i).checkFullScoreCard())
               return false;
        }
        return true;
    }
    /**
    Creates a VEctor of Pplayer object to traverse through
    *
    * @param nothing
    * @return populated vector
    */
    public void createPlayers() {
        for(int i = 0; i < this.numPlayers; i++) {
            playerVec.add(new Player(playerNames[i], this.numDie, this.sideDie));
        }
    }
    /**
    creates arralist of checkboxes of images of certain dice based on the hand
    *
    * @param arraylist of the dice rolled
    * @return arraylist of the images of teh dice rolled
    */
    public ArrayList<JCheckBox> displayHand(ArrayList<Integer> hand) {
        ArrayList<JCheckBox> images = new ArrayList<JCheckBox>();
        for(int i = 0; i < hand.size(); i++) {
            images.add(i, new JCheckBox(getImage(hand.get(i))));
        }
        return images;
    }
    /**
    Creates Arraylist of checkboxes for teh user to select
    *
    * @param nothing
    * @return a populated arraylist
    */
    public ArrayList<JCheckBox> makeSelections() {
        ArrayList<JCheckBox> selections = new ArrayList<JCheckBox>();
        for(int i = 0; i < this.numDie; i++) {
            selections.add(i, new JCheckBox("", false));
        }
        return selections;
    }
    /***
    gets the image of the die that goes with the number rolled 
    *
    * @param integer of the number rolled
    * @return ImageIcon of the number rolled
    */
    public ImageIcon getImage(int i) {
        ImageIcon image = new ImageIcon();            
        switch(i) {
            case 1:
                image = new ImageIcon("images/D6-01.png");
                break;
            case 2:
                image = new ImageIcon("images/D6-02.png");
                break;
            case 3:
                image = new ImageIcon("images/D6-03.png");
                break;
            case 4:
                image = new ImageIcon("images/D6-04.png");
                break;
            case 5:
                image = new ImageIcon("images/D6-05.png");
                break;
            case 6:
                image = new ImageIcon("images/D6-06.png");
                break;
            case 7:
                image = new ImageIcon("images/D6-07.png");
                break;
            case 8:
                image = new ImageIcon("images/D6-08.png");
                break;
            case 9:
                image = new ImageIcon("images/D6-09.png");
                break;
            case 10:
                image = new ImageIcon("images/D6-10.png");
                break;
            case 11:
                image = new ImageIcon("images/D6-11.png");
                break;
            case 12:
                image = new ImageIcon("images/D6-12.png");
                break;

        }  
        // return scaled image
        return new ImageIcon(image.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
    }
    /**
    Play a sound effect for buttons
    *
    * @param String object of the filepath to the sound byte
    * @return nothing
    */
    public void addSoundEffect(String filepath){

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