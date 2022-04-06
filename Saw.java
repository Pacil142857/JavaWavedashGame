import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Saw extends Hazard {
    protected int r;
    protected BufferedImage img;

    // Create a saw at a given point with a given radius
    public Saw(int x, int y, int r, Color fillColor) {
        super(x, y, r * 2, r * 2, fillColor, fillColor);
        this.r = r;
    }

    // Draw the saw
    public void draw(Graphics g, JPanel game) {
        try {
            // Try using the file
            img = ImageIO.read(new File("Animation Project\\resources\\saw.png"));
            BufferedImage scaledImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();
            at.scale((double) w / img.getWidth(), (double) w / img.getHeight());
            scaledImg = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR).filter(img, scaledImg);
            g.drawImage(scaledImg, x, y, game);
        }
        catch (IOException e) {
            // If using the file fails, use a circle
            g.setColor(fillColor);
            g.fillArc(x, y, 2 * r, 2 * r, 0, 360);
        }
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
