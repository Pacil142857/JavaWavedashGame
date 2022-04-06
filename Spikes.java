import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Spikes extends Hazard {
    private int numSpikes;

    // Create a given number of Spikes at a given point with a given size and color
    public Spikes(int x, int y, int width, int height, Color fillColor, Color outlineColor, int numSpikes) {
        // Round the width down so that one spike isn't bigger than the rest
        super(x, y, width / numSpikes * numSpikes, height, fillColor, outlineColor);
        this.numSpikes = numSpikes;
    }

    // Create a given number of Spikes at a given point with a given size
    public Spikes(int x, int y, int width, int height, int numSpikes) {
        // Round the width down so that one spike isn't bigger than the rest
        super(x, y, width / numSpikes * numSpikes, height, Color.WHITE, Color.BLACK);
        this.numSpikes = numSpikes;
    }

    public void draw(Graphics g, JPanel game) {
        g.setColor(fillColor);
        int l = w / numSpikes;
        int[] xPoints = new int[]{x, x + l / 2, x + l};
        int[] yPoints = new int[]{y + h, y, y + h};

        // Draw the Spikes
        for (int i = 0; i <= numSpikes - 1; i++) {
            g.fillPolygon(xPoints, yPoints, 3);
            xPoints[0] += l;
            xPoints[1] += l;
            xPoints[2] += l;
        }

        // Outline the Spikes
        g.setColor(outlineColor);
        xPoints[0] = x;
        xPoints[1] = x + l / 2;
        xPoints[2] = x + l;
        for (int i = 0; i <= numSpikes - 1; i++) {
            g.drawPolyline(xPoints, yPoints, 3);
            xPoints[0] += l;
            xPoints[1] += l;
            xPoints[2] += l;
        }
    }

    // Determine if the Spikes are touching the Player
    // For simplicity, Spikes are treated as rectangles in collision detection
    public boolean isTouchingPlayer(Player p) {
        // If the Player is air dodging, stop
        if (p.getIsAirDodging()) {
            return false;
        }
        
        if (x <= p.getRightX() && x + w >= p.getLeftX() && y <= p.getBottomY() && y + h >= p.getTopY()) {
            return true;
        }
        return false;
    }
}