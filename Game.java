import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Game extends JPanel{
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 700;

    private BufferedImage image;
    private Graphics g;
    private Timer timer;
    private final int dt = 16;
    private Player player;
    private Map map;

    // When certain buttons are pressed, have the player take actions
    private class TakeAction extends AbstractAction {
        private String action;
        private TakeAction(String action) {
            this.action = action;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case "moveRight":
                    player.moveRight();
                    break;
                case "moveLeft":
                    player.moveLeft();
                    break;
                case "jump":
                    player.jump(6);
                    break;
                case "airDodge":
                    player.airDodge(5);
                    break;
            }
        }
    }

    // Sets up everything, such as the player and the map
    public Game() {
        // set up Buffered Image and Graphics objects
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        // Create a Player and a Map
        player = new Player(50, HEIGHT - 100);
        map = new Map(3, 2, Color.BLACK, Color.BLACK);

        // In the future, I'll probably put this in a different method or class
        // Form the map
        map.addRect(0, HEIGHT - 50, WIDTH, 50);
        map.addRect(600, 100, 100, HEIGHT - 150);
        map.addRect(100, 400, 200, 50);

        map.addLightFloor(300, 550, 300);
        map.addLightFloor(500, 400, 100);

        map.drawMap(g);

        // Create the timer
        timer = new Timer(dt, new TimerListener());
        timer.start();

        // Set certain buttons to perform certain actions
        setFocusable(true);
        getInputMap().put(KeyStroke.getKeyStroke('s'), "moveRight");
        getInputMap().put(KeyStroke.getKeyStroke('a'), "moveLeft");
        getInputMap().put(KeyStroke.getKeyStroke(' '), "jump");
        getInputMap().put(KeyStroke.getKeyStroke('j'), "airDodge");
        getActionMap().put("moveRight", new TakeAction("moveRight"));
        getActionMap().put("moveLeft", new TakeAction("moveLeft"));
        getActionMap().put("jump", new TakeAction("jump"));
        getActionMap().put("airDodge", new TakeAction("airDodge"));
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Draw the background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // Draw the map & player, and make the player move
            map.drawMap(g);
            player.move(map.getRects(), map.getLightFloors(), dt);
            player.draw(g);

            repaint();
        }
    }

    // Draw the image
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    // Runs the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("WaveDash");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Game());
        frame.setVisible(true);
    }
}
