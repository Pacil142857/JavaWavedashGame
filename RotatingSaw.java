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
                rects[i][1][3 * j] = y;
                rects[i][1][j + 1] = y + 5;
            }
        }
    }

    // Rotate rectangles about a given point
    private static double[][][] rotate(double x, double y, double[][][] rects, boolean goingLeft) {
        int direction = goingLeft ? 1 : -1;
        // DeltaX and deltaY refer to the distance between rectangle 1 and the point it's rotating about
        double deltaX;
        double deltaY;
        // ChangeInX and changeInY refer to the distance between the old location of rectangle 1 and its new location
        double changeInX;
        double changeInY;

        // Rotate the first rectangle
        for (int i = 0; i < 4; i++) {
            deltaX = (rects[0][0][i] - x);
            deltaY = (rects[0][1][i] - y);

            // Rotate the first rectangle
            rects[0][0][i] = x + deltaX * Math.cos(0.1) - deltaY * direction * Math.sin(0.1);
            rects[0][1][i] = y + deltaX * direction * Math.sin(0.1) + deltaY * Math.cos(0.1);
        }

        if (rects.length > 1) {
            // Move the second rectangle (if it exists)
            changeInX = (rects[0][0][2] + rects[0][0][3]) / 2.0 - (rects[1][0][0] + rects[1][0][1]) / 2.0;
            changeInY = (rects[0][1][2] + rects[0][1][3]) / 2.0 - (rects[1][1][0] + rects[1][1][1]) / 2.0;
            for (int i = 0; i < 4; i++) {
                rects[1][0][i] += changeInX;
                rects[1][1][i] += changeInY;

                // Rotate the second rectangle
                deltaX = (rects[1][0][i] - ((rects[0][0][0] + rects[0][0][1]) / 2.0));
                deltaY = (rects[1][1][i] - ((rects[0][1][0] + rects[0][1][1]) / 2.0));
                rects[1][0][i] = x + deltaX * Math.cos(0.15) - deltaY * direction * Math.sin(0.15);
                rects[1][1][i] = y + deltaX * direction * Math.sin(0.15) + deltaY * Math.cos(0.15);
            }
        }

        return rects;
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
        rects = rotate((rects[0][0][0] + rects[0][0][1]) / 2.0, (rects[0][1][0] + rects[0][1][1]) / 2.0, rects, doubleRotater);

        // Move the saw
        int lastRect = rects.length - 1;
        x = (rects[lastRect][0][3] + rects[lastRect][0][2]) / 2.0 - 1 * r;
        y = (rects[lastRect][1][3] + rects[lastRect][1][2]) / 2.0 - 1 * r;
    }

    // Draw the RotatingSaw
    public void draw(Graphics g) {
        move();

        // Draw the rectangles
        g.setColor(Color.BLACK);
        for (double[][] rect : rects) {
            g.fillPolygon(round(rect[0]), round(rect[1]), 4);
        }

        // Draw a circle between the rectangles
        int circleX = (int) ((rects[0][0][3] + rects[0][0][2]) / 2.0 + 0.5);
        int circleY = (int) ((rects[0][1][3] + rects[0][1][2]) / 2.0 + 0.5);
        g.fillArc(circleX - rectLength / 5, circleY - rectLength / 5, 2 * rectLength / 5, 2 * rectLength / 5, 0 , 360);

        // Draw the saw
        g.setColor(fillColor);
        g.fillArc((int) (x + 0.5), (int) (y + 0.5), r * 2, r * 2, 0, 360);
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
