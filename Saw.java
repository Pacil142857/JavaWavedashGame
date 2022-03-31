import java.awt.Color;
import java.awt.Graphics;

public class Saw extends Hazard {
    private int r;

    // Create a saw at a given point with a given radius
    public Saw(int x, int y, int r, Color fillColor) {
        super(x, y, r * 2, r * 2, fillColor, fillColor);
        this.r = r;
    }

    // Draw the saw
    public void draw(Graphics g) {
        g.setColor(fillColor);
        g.fillArc(x, y, 2 * r, 2 * r, 0, 360);
    }

    // Determine if the Saw is touching the Player
    public boolean isTouchingPlayer(Player p) {
        // If the Player is air dodging, stop
        if (p.getIsAirDodging()) {
            return false;
        }
        
        // Find what sides of the Player the Saw is closest to
        int xDistance = x + r - p.getRightX();
        int yDistance = y + r - p.getTopY();

        if (Math.abs(xDistance) > Math.abs(x + r - p.getLeftX())) {
            xDistance = x + r - p.getLeftX();
        }
        if (Math.abs(yDistance) > Math.abs(y + r - p.getBottomY())) {
            yDistance = y + r - p.getBottomY();
        }

        // Use Pythagorean Theorem to determine if there's a collision
        if (Math.pow(xDistance, 2) + Math.pow(yDistance, 2) <= Math.pow(r, 2)) {
            return true;
        }
        return false;
    }
}
