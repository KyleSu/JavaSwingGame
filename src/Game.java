/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes inmutability
        // even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("SNAKE");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        final JLabel points = new JLabel ("Points: 0");
        status_panel.add(points);

        // Main playing area
        final GameCourt court = new GameCourt(status, points);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is
        // an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        
        
        
        
        final String introText = "Welcome to Snake!" + "\n" + "Snake has some simple"
        + " rules which will be explained below." + "\n" + "1. You control a"
        + " snake, which you will see represented as a series of black squares."
        + "\n" + "2. The goal of this game is to manuever the snake to capture" 
        + " the poisonous mushrooms." + "\n" + "3. You control the head of the"
        + " snake by using the arrow keys. The snake's head will continue in"
        + " the last direction key pressed by yourself." + "\n" + "4. Use the"
        + " arrow keys to guide your snakes over the mushroom. Everytime you"
        + " swallow a mushroom a new one will appear, and your snake will grow"
        + " larger." + "\n" + "5. If you touch any of the walls or any other "
        + "segment of the snake with the head, you just lost the game." + "\n"
        + "Yes there are explosions.";

        
        
        
        
        
        
        

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        final JButton introButton = new JButton("Instructions");
        introButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        introText);
            }
        });
        control_panel.add(introButton);
        
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        // Start game
        court.reset();
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
