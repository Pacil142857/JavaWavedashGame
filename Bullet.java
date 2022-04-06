import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Hazard{
    private double x;
    private double y;
    private double xSpd;
    private double ySpd;

    // Create a Bullet at a given location with a given size and speed
    public Bullet(double x, double y, int width, int height, double xSpd, double ySpd) {
        super((int) (x + 0.5), (int) (y + 0.5), width, height, Color.RED, Color.BLACK);
        this.x = x;
        this.y = y;
        this.xSpd = xSpd;
        this.ySpd = ySpd;
    }

    // Create a Bullet at a given location with a given size, speed, and color
    public Bullet(double x, double y, int width, int height, double xSpd, double ySpd, Color fillColor, Color outlineColor) {
        super((int) (x + 0.5), (int) (y + 0.5), width, height, fillColor, outlineColor);
        this.x = x;
        this.y = y;
        this.xSpd = xSpd;
        this.ySpd = ySpd;
    }

    // Move the Bullet
    public void move() {
        x += xSpd;
        y += ySpd;
        
        // If the Bullet went off-screen, respawn it back to the other side
        // I should be using a variable for width and height here, but I don't want to clog up the constructor with more variables
        if (x + w < 0) {
            x = 1300;
        }
        if (x > 1300) {
            x = -w;
        }
        if (y + h < 0) {
            y = 700;
        }
        if (y > 700) {
            y = -h;
        }
    }

    // Draw the Bullet
    public void draw(Graphics g) {
        move();
        
        // Draw the Bullet
        g.setColor(fillColor);
        g.fillRect((int) (x + 0.5), (int) (y + 0.5), w, h);
        
        // Draw the outline
        g.setColor(outlineColor);
        g.drawRect((int) (x + 0.5), (int) (y + 0.5), w, h);
    }

    // Determine if the Bullet is touching the player
    public boolean isTouchingPlayer(Player p) {
        // If the Player is air dodging, stop
        if (p.getIsAirDodging()) {
            return false;
        }
        
        // Check for a collision
        int x = (int) (this.x + 0.5);
        int y = (int) (this.y + 0.5);
        if (x <= p.getRightX() && x + w >= p.getLeftX() && y <= p.getBottomY() && y + h >= p.getTopY()) {
            return true;
        }
        return false;
    }
}
