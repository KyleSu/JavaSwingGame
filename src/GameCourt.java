/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Snake snake; // the Snake, keyboard control
    private Poison target; // the target

    public boolean playing = false; // whether the game is running
    private JLabel status; // Current status text (i.e. Running...)
    private int pointsnum = 0; // Num of points
    public JLabel points; // Current num of points

    public ArrayList<Explosion> explosions; // Array of explosions
    
    // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 500;
    public static final int SQUARE_VELOCITY = 6;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status, JLabel points) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.explosions = new ArrayList<Explosion>();

        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long
        // as an arrow key is pressed, by changing the square's
        // velocity accordingly. (The tick method below actually
        // moves the square.)
        addKeyListener(new KeyAdapter() {
            // I want the ONLY movement to be in the key direction
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (playing) {
                        snake.getLast().v_y = 0;
                        snake.getLast().v_x = -SQUARE_VELOCITY;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (playing) {
                        snake.getLast().v_y = 0;
                        snake.getLast().v_x = SQUARE_VELOCITY;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (playing) {
                        snake.getLast().v_x = 0;
                        snake.getLast().v_y = SQUARE_VELOCITY;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (playing) {
                        snake.getLast().v_x = 0;
                        snake.getLast().v_y = -SQUARE_VELOCITY;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    playing = !playing; // Pauses game
                }
            }

            public void keyReleased(KeyEvent e) {
                // Does nothing
            }
        });

        this.status = status;
        this.points = points;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {

        snake = new Snake(COURT_WIDTH, COURT_HEIGHT);
        target = new Poison(COURT_WIDTH, COURT_HEIGHT);
        target.changeCoords(); // Change target location

        playing = true;
        status.setText("Running...");
        points.setText("Points: 0");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            
            /*Checks for finished explosions to remove
             */
            ArrayList<Explosion> explosionsToRemove = new 
                    ArrayList<Explosion>();
            for (Explosion x: this.explosions) {
             x.imgSequenceNo++;
             if (x.imgSequenceNo > 17) { // Goes over number of images
              explosionsToRemove.add(x);
             }
            }
            
            //removes finished explosions
            for (Explosion x: explosionsToRemove) {
             this.explosions.remove(x);
            }
            
            
            

            GameObj snakeHead = snake.getLast();

            snake.update(); //updates body segments
            snakeHead.move(); // updates head


            // check for the game end conditions
            if (snakeHead.intersects(target)) {
                snake.addSegment(); //add new segment to Snake
                target.changeCoords(); //redraw target elsewhere
                pointsnum++;
                points.setText("Points: " + pointsnum); // Increment points
                this.explosions.add(new Explosion(COURT_WIDTH, COURT_HEIGHT,
                        snakeHead.pos_x, snakeHead.pos_y)); // EXPLODE                
            } else {
                // if the snake hits a wall end game
                if (snakeHead.willHitWall()) { 
                    playing = false;
                    status.setText("You lose!");
                    pointsnum = 0;
                }
                // if the snakehead hits another section end game
                for (Square s: snake.getBody()){
                    if (!s.equals(snakeHead)) {
                        if (snakeHead.intersects(s)) {
                            playing = false;
                            status.setText("You lose!");
                            pointsnum = 0;
                            break;
                        }
                    }
                }

            }

            // update the display
            repaint();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int count = 1;
        for (Square s: snake.getBody()) { // Draw all segments of snake body
            s.draw(g);
            count++;
        }

        target.draw(g); // Draw target
        
        for (Explosion x: this.explosions) { // Draw explosions
            if (x.imgSequenceNo < 18) {
             x.draw(g);
            }
           }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
