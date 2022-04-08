import java.awt.Color;
import java.awt.Graphics;

public class Goal {
    private int x;
    private int y;
    private int h;
    private int w;
    
    // Create a Goal at a certain location with a given size
    public Goal(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }

    // Draw the goal
    public void draw(Graphics g) {
        // Draw the flagpole
        g.setColor(Color.GRAY);
        g.fillRect(x, y, 2, h);

        // Draw the flag
        g.setColor(Color.RED);
        int[] xPoints = new int[]{x + 2, x + w, x + 2};
        int[] yPoints = new int[]{y, y + (int) (h / 6 + 0.5), y + 2 * (int) (h / 6 + 0.5)};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    // Set the location of the goal
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Set the size of the goal
    public void setSize(int width, int height) {
        this.w = width;
        this.h = height;
    }

    // Check if the goal is touching a Player
    public boolean isTouchingPlayer(Player p) {
        // Check if the Player is too high or low to reach the goal
        if (p.getBottomY() < getY() || p.getTopY() > getY() + getH()) {
            return false;
        }

        // Check if the Player is too left or right to reach the goal
        if (p.getRightX() < getX() || p.getLeftX() > getX() + getW()) {
            return false;
        }
        return true;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
