import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel{
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 700;

    private BufferedImage image;
    private Graphics g;
    private Timer timer;
    private final int dt = 16;
    private Player player;
    private PremadeMap map;
    private Goal goal;
    private Song song;

    // Have the Player do things when certain buttons are pressed
    private class Keyboard implements KeyListener {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 's':
                    player.moveRight();
                    break;
                case 'a':
                    player.moveLeft();
                    break;
                case ' ':
                    player.startJump();
                    break;
                case 'n':
                    player.airDodge();
                    break;
            }
        }
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 's':
                    player.stopMovingRight();
                    break;
                case 'a':
                    player.stopMovingLeft();
                    break;
            }
        }
        public void keyTyped(KeyEvent e) {}
    }

    // Sets up everything, such as the player and the map
    public Game() {
        // set up Buffered Image and Graphics objects
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        // Set the font
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        // Create a Player and a Map
        player = new Player(50, HEIGHT - 100);
        map = new PremadeMap(WIDTH, HEIGHT);
        goal = new Goal(WIDTH - 100, HEIGHT - 260, 40, 100);
        goal.draw(g);

        // Form the maps
        map.formMap1(g, player, goal);
        map.drawMap(g);

        // Create a Song
        song = new Song("Animation Project\\intro1.wav", "Animation Project\\loop1.wav");
        song.playIntro();

        // Create the timer
        timer = new Timer(dt, new TimerListener());
        timer.start();

        // Add key listener
        addKeyListener(new Keyboard());
        setFocusable(true);
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Draw the background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // Draw the map and make the player move
            map.drawMap(g);
            player.move(map.getRects(), map.getLightFloors(), dt);

            // Check if the Player died
            for (Hazard haz : map.getHazards()) {
                if (haz.isTouchingPlayer(player)) {
                    haz.killPlayer(player);
                    break;
                }
            }

            // Draw the Player and the goal
            player.draw(g);
            goal.draw(g);

            // If the Player reached the goal, go to the next map
            if (goal.isTouchingPlayer(player)) {
                map.toNextMap(g, player, goal);
            }

            // Loop the song
            song.loop(544768);

            repaint();
        }
    }

    // Draw the image
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    // Runs the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("WaveJump");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Game());
        frame.setVisible(true);
    }
}
