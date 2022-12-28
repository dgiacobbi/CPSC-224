/**
* This program is the EndGame Object that displays the ending leaderboard and who won
* CPSC 224-01, Fall 2022
* lil' Yahtzee
* No sources to cite.
* 
* @author George Calvert, Henry Stone, David Giacobbi
* @version v1.0 11/29/22
*/
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class EndGame {

    public Vector<Player> playerVec;

    /*
    Constructor for endGAme
    * @return initializes all variables needed
    */
    public EndGame(Vector<Player> playerVec) {
        this.playerVec = playerVec;
    } 

    /**
    Displays the end game frame
    *
    * @param nothing
    * @return Displays leaderboard and the winner
    */
    public void end() {

        // sort so the winner is at the beginning of the array
        sortPlayerVec();

        // end game music
        addSoundEffect("sound-effects/ApplauseCrowdCheeringsoundeffect.wav");
        addSoundEffect("sound-effects/funkytownlowquality.wav");
        
        // Frames and panels
        JFrame EndFrame = new JFrame("End Game");
        EndFrame.setLayout(new BorderLayout());

        JPanel imageP = new JPanel();
        imageP.setBackground(new Color(184, 184, 184));
        JLabel gameOver = new JLabel();
        gameOver.setIcon(new ImageIcon("images/GameOver.png"));
        imageP.add(gameOver);

        // create leaderboard 
        JPanel leaderboard = new JPanel();
        leaderboard.setBackground(new Color(184, 184, 184));
        leaderboard.setLayout(new BoxLayout(leaderboard, BoxLayout.PAGE_AXIS));

        // label for winner
        JLabel title = new JLabel("Leaderboard");
        title.setFont(new Font("Copperplate", Font.PLAIN, 58));
        leaderboard.add(title);

        JLabel tmp;
        for(int i = 0; i < this.playerVec.size(); i++) {
            tmp = new JLabel((i + 1) + ". " + this.playerVec.get(i).getName() + " Score: " + this.playerVec.get(i).getTotal());
            tmp.setFont(new Font("Copperplate", Font.PLAIN, 40));
            tmp.setForeground(Color.white);
            leaderboard.add(tmp);
        }

        // play again button
        JPanel buttonP = new JPanel();
        buttonP.setBackground(new Color(184, 184, 184));
        JButton newGame = new JButton("Play Again");
        newGame.setFont(new Font("Britannic Bold", Font.BOLD, 28));
        newGame.setPreferredSize(new Dimension(400, 100));

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                addSoundEffect("sound-effects/Button.wav");
                
                // starts new game of yahtzee
                InitialDisplay dp = new InitialDisplay();
                EndFrame.dispose();
                dp.Display();
            }
        });
        buttonP.add(newGame);

        // add panels and button
        EndFrame.add(imageP, BorderLayout.PAGE_START);
        EndFrame.add(leaderboard, BorderLayout.CENTER);
        EndFrame.add(buttonP, BorderLayout.PAGE_END);
        
        // fraem settings
        EndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EndFrame.setSize(1000,800);
        EndFrame.setLocationRelativeTo(null);
        EndFrame.setResizable(false);
        EndFrame.setVisible(true); 

    }
    /**
    Sort Vector in descending order so the winner is at index 0
    *
    * @param nothing
    * @return sorted vector
    */
    private void sortPlayerVec() {
        for(int i = 0; i < this.playerVec.size(); i++) {
            for(int k = i + 1; k < this.playerVec.size(); k++) {
                if(this.playerVec.get(i).getTotal() < this.playerVec.get(k).getTotal()) {
                    Player tmp = this.playerVec.get(i);
                    this.playerVec.set(i, this.playerVec.get(k));
                    this.playerVec.set(k, tmp);
                }
            }
        }
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
