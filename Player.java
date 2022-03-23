import java.awt.Color;
import java.awt.Graphics;

public class Player {
    private double x;
    private double y;
    private int length;
    private Color fillColor;
    private Color outlineColor;
    private double xSpd;
    private double ySpd;
    private double xAcc;
    private double yAcc;
    private boolean isAirDodging = false;

    // Create a white Player with a black outline at (0, 0) with length 20
    public Player() {
        this.x = 0;
        this.y = 0;
        this.length = 20;
        this.fillColor = Color.WHITE;
        this.outlineColor = Color.BLACK;
        this.xSpd = 0;
        this.ySpd = 0;
        this.xAcc = 0;
        this.yAcc = 10;
    }

    // Create a white Player with a black outline at a given location with length 20
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.length = 20;
        this.fillColor = Color.WHITE;
        this.outlineColor = Color.BLACK;
        this.xSpd = 0;
        this.ySpd = 0;
        this.xAcc = 0;
        this.yAcc = 10;
    }

    // Draw the Player
    public void draw(Graphics g) {
        g.setColor(fillColor);
        g.fillRect((int) (x + 0.5), (int) (y + 0.5), length, length);
        g.setColor(outlineColor);
        g.drawRect((int) (x + 0.5), (int) (y + 0.5), length, length);
    }

    // Move the player
    public void move(int[][] rects, int[][] lightFloors, int  dt) {
        // Move the ball without considering walls and floors
        x += xSpd;
        y += ySpd;
        xSpd += xAcc * dt / 1000;
        ySpd += yAcc * dt / 1000;

        boolean canHitWall = true;
        boolean canHitFloor = true;

        // Prevent the Player from going through walls and hard floors
        for(int[] rect : rects) {
            // First, check if the player can even hit this wall or floor
            canHitWall = !(getTopY() > rect[1] + rect[3] || getBottomY() < rect[1]);
            canHitFloor = !(getRightX() < rect[0] || getLeftX() > rect[0] + rect[2]);

            // Check for collisions with walls
            // TODO: Prevent the edge case of colliding with 2 walls (and 2 floors) at once from doing anything
            for (int i = rect[0]; canHitWall && (i == rect[0] || i == rect[0] + rect[2]); i += rect[2]) {
                if (getRightX() >= i && getPrevRightX() < i) {
                    x = i - length - 1;
                    xSpd = 0;
                    xAcc = 0;
                    break;
                }
                else if (getLeftX() <= i && getPrevLeftX() > i) {
                    x = i + 1;
                    xSpd = 0;
                    xAcc = 0;
                    break;
                }
            }

            // Check for collisions with (hard) floors
            for (int i = rect[1]; canHitFloor && (i == rect[1] || i == rect[1] + rect[3]); i+= rect[3]) {
                if (getBottomY() >= i && getPrevBottomY() < i) {
                    y = i - length - 1;

                    // Add 0.9x times the vertical velocity to the horizontal velocity (wavedash)
                    if (isAirDodging) {
                        xSpd += xSpd > 0 ? 0.9 * ySpd : -0.9 * ySpd;
                    }
                    ySpd = 0;
                }
                else if (getTopY() <= i && getPrevTopY() > i) {
                    y = i + 1;
                    ySpd = 0;
                }

                // Apply friction
                if (getBottomY() == i - 1) {
                    applyFriction(dt);
                }
            }
        }

        // If the Player went through a light  floor, stop them
        for (int[] floor : lightFloors) {
            // First, check if the Player has the proper x-coordinate to collide with a floor
            if (getRightX() < floor[0] || getLeftX() > floor[2]) {
                continue;
            }

            // Next, ensure the Player doesn't go through a light floor
            if (getBottomY() >= floor[1] && getPrevBottomY() < floor[1]) {
                y = floor[1] - length - 1;
                
                // Add 0.9x the vertical velocity to the horizontal velocity (wavedash)
                if (isAirDodging) {
                    xSpd += xSpd > 0.9 ? ySpd : -0.9 * ySpd;
                }
                ySpd = 0;
            }

            // Apply friction
            if (getBottomY() == floor[1] - 1) {
                applyFriction(dt);
            }
        }
    }

    // Jump with a certain speed
    public void jump(double speed) {
       ySpd = -speed;
       xAcc = 0;
    }

    // Air dodge with a certain direction
    public void airDodge(double direction) {
        xAcc = 0;
        yAcc = 0;
        xSpd = 6 * Math.cos(direction);
        ySpd = -6 * Math.sin(direction);
        isAirDodging = true;
    }

    // End an air dodge
    public void endAirDodge() {
        yAcc = 10;
        xSpd = 0;
        ySpd = 0;
        isAirDodging = false;
    }

    // Move right
    public void moveRight() {
        /*if (xSpd <= 5) {
            xAcc += 3;
        }*/
        xAcc += 3;
    }

    // Move left
    public void moveLeft() {
        /*if (xSpd >= -5) {
            xAcc -= 3;
        }*/
        xAcc -= 3;
    }

    // Apply friction
    public void applyFriction(int dt) {
        if (xSpd > 0) {
            xAcc -= 0.2 * Math.pow(xSpd, 2);
            if (xSpd > 3.8 && xSpd < 3.9) {
                System.out.println(xSpd + "\t" + xAcc);
            }
            if (xSpd + xAcc * dt / 1000 <= 0) {
                xSpd = 0;
                xAcc = 0;
            }
        }
        else if (xSpd < 0) {
            xAcc += 0.2 * Math.pow(xSpd, 2);
            if (xSpd + xAcc * 1000 / dt >= 0) {
                xSpd = 0;
                xAcc = 0;
            }
        }
    }

    // Custom getters and setters
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getLeftX() {
        return (int) (x + 0.5);
    }

    public int getPrevLeftX() {
        return (int) (x - xSpd + 0.5);
    }

    public int getRightX() {
        return (int) (x + length + 0.5);
    }

    public int getPrevRightX() {
        return (int) (x + length - xSpd + 0.5);
    }

    public int getTopY() {
        return (int) (y + 0.5);
    }

    public int getPrevTopY() {
        return (int) (y - ySpd + 0.5);
    }

    public int getBottomY() {
        return (int) (y + length + 0.5);
    }

    public int getPrevBottomY() {
        return (int) (y + length - ySpd + 0.5);
    }

    // Auto-generated getters and setters
    // TODO: Re-generate these
    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public double getXSpd() {
        return this.xSpd;
    }

    public void setXSpd(double xSpd) {
        this.xSpd = xSpd;
    }

    public double getYSpd() {
        return this.ySpd;
    }

    public void setYSpd(double ySpd) {
        this.ySpd = ySpd;
    }

    public double getXAcc() {
        return this.xAcc;
    }

    public void setXAcc(double xAcc) {
        this.xAcc = xAcc;
    }

    public double getYAcc() {
        return this.yAcc;
    }

    public void setYAcc(double yAcc) {
        this.yAcc = yAcc;
    }

    public boolean getIsAirDodging() {
        return isAirDodging;
    }

    public void setIsAirDodging(boolean isAirDodging) {
        this.isAirDodging = isAirDodging;
    }
}
