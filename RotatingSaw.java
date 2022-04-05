import java.awt.Color;
import java.awt.Graphics;

public class RotatingSaw extends Saw {
    private double x;
    private double y;
    private int rectLength;
    private boolean doubleRotater;
    private double[][][] rects;

    // Create a RotatingSaw at a given point with a given radius and color
    public RotatingSaw(int x, int y, int r, int rectLength, Color fillColor, boolean doubleRotater) {
        super(x, y, r, fillColor);
        this.doubleRotater = doubleRotater;
        this.rects = new double[doubleRotater ? 2 : 1][2][4];
        this.rectLength = rectLength;
        this.x = x;
        this.y = y;

        // Set the values for the rectangles
        for (int i = 0; i < rects.length; i++) {
            for (int j = 0; j < 2; j++) {
                // Set the x-values for the rectangle
                rects[i][0][j] = x + i * rectLength;
                rects[i][0][j + 2] = x + (i + 1) * rectLength;

                // Set the y-values for the rectangle
                rects[i][1][2 * j] = y;
                rects[i][1][2 * j + 1] = y + 2;
            }
        }
    }

    // Rotate a rectangle about a given point
    private static void rotate(double x, double y, double[][] poly, boolean goingLeft) {
        int direction = goingLeft ? 1 : -1;
        for (int i = 0; i < 4; i++) {
            poly[0][i] = x + (poly[0][i] - x) * Math.cos(16 / 1000) - (poly[1][i] - y) * direction * Math.sin(16 / 1000);
            poly[1][i] = x + (poly[0][i] - x) * Math.sin(16 / 1000) - (poly[1][i] - y) * direction * Math.cos(16 / 1000);
        }
    }

    // Round all double values in a double[]
    private static int[] round(double[] nums) {
        int[] roundedNums = new int[nums.length];
        // Round the numbers
        for (int i = 0; i < nums.length; i++) {
            roundedNums[i] = (int) (nums[i] + 0.5);
        }
        return roundedNums;
    }

    // Move the RotatingSaw
    public void move() {
        // Rotate the rectangle(s)
        for (double[][] rect : rects) {
            rotate((rect[0][0] + rect[0][1]) / 2.0, (rect[1][0] + rect[1][1]) / 2.0, rect, doubleRotater);
        }

        // Move the saw
        // TODO: Make the saw rotate
        int lastRect = rects.length - 1;
        x = (rects[lastRect][0][2] + rects[lastRect][0][3]) / 2.0;
        y = (rects[lastRect][1][2] + rects[lastRect][1][3]) / 2.0;
    }

    // Draw the RotatingSaw
    public void draw(Graphics g) {
        move();

        // Draw the rectangles
        g.setColor(Color.BLACK);
        for (double[][] rect : rects) {
            g.fillPolygon(round(rect[0]), round(rect[1]), 4);
        }

        // Draw the saw
        g.setColor(fillColor);
        g.drawArc((int) (x + 0.5), (int) (y + 0.5), r * 2, r * 2, 0, 360);
    }

    // Determine if the RotatingSaw is touching the Player
    public boolean isTouchingPlayer(Player p) {
        int x = (int) (this.x + 0.5);
        int y = (int) (this.y + 0.5);

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
