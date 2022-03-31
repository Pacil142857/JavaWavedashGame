import java.awt.Color;
import java.awt.Graphics;

public class Hazard {
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected Color fillColor;
    protected Color outlineColor;

    // Create a hazard at a given point with a given color
    public Hazard(int x, int y, Color fillColor, Color outlineColor) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
    }
    
    // Create a hazard at a given point with a given size and color
    public Hazard(int x, int y, int width, int height, Color fillColor, Color outlineColor) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
    }

    // Increase the death count and respawn the player
    public void killPlayer(Player p) {
        p.setNumDeaths(p.getNumDeaths() + 1);
        p.respawn();
    }

    // "Draw" the hazard
    // This should be implemented in every subclass of Hazard
    public void draw(Graphics g) {}

    // "Check" if a hazard is touching the Player
    // This should be implemented in every subclass of Hazard
    public boolean isTouchingPlayer(Player p) {return false;}

    // Getters and setters
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return this.w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getOutlineColor() {
        return this.outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }    
}
