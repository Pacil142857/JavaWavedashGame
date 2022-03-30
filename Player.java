import java.awt.Color;
import java.awt.Graphics;

public class Player {
    private double x;
    private double y;
    private int length;
    private int height;
    private Color fillColor;
    private Color outlineColor;
    private double xSpd;
    private double ySpd;
    private double xAcc;
    private double yAcc;
    private double xJrk;
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean isAirDodging = false;
    private boolean isGrounded = false;
    private boolean isWaveDashing = false;
    private int airDodgeTimeCounter = 0;

    // Create a white Player with a black outline at (0, 0) with length 20
    public Player() {
        this.x = 0;
        this.y = 0;
        this.length = 20;
        this.height = 20;
        this.fillColor = Color.WHITE;
        this.outlineColor = Color.BLACK;
        this.xSpd = 0;
        this.ySpd = 0;
        this.xAcc = 0;
        this.yAcc = 45;
    }

    // Create a white Player with a black outline at a given location with length 20
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.length = 20;
        this.height = 20;
        this.fillColor = Color.WHITE;
        this.outlineColor = Color.BLACK;
        this.xSpd = 0;
        this.ySpd = 0;
        this.xAcc = 0;
        this.yAcc = 45;
    }

    // Draw the Player
    public void draw(Graphics g) {
        g.setColor(fillColor);
        g.fillRect((int) (x + 0.5), (int) (y + 0.5), length, height);
        g.setColor(outlineColor);
        g.drawRect((int) (x + 0.5), (int) (y + 0.5), length, height);
    }

    // Move the player
    public void move(int[][] rects, int[][] lightFloors, int dt) {
        // Move the Player without considering walls and floors
        if ((xSpd > 6 && xJrk > 0) || (xSpd < -6 && xJrk < 0)) {
            xAcc = 0;
        }
        else {
            xAcc += xJrk;
        }
        applyFriction(dt);
        x += xSpd;
        y += ySpd;
        xSpd += xAcc * dt / 1000;
        ySpd += yAcc * dt / 1000;

        isGrounded = false;
        boolean canHitWall;
        boolean canHitFloor;
        double centerX;
        double centerY;
        for (int[] rect : rects) {
            // First, check if the player can even hit this wall or floor
            canHitWall = !(getTopY() > rect[1] + rect[3] || getBottomY() < rect[1]);
            canHitFloor = !(getRightX() < rect[0] || getLeftX() > rect[0] + rect[2]);

            centerX = rect[0] + rect[2] / 2.0;
            centerY = rect[1] + rect[3] / 2.0;

            // Check for collisions with walls
            if (canHitWall && Math.abs(centerX - getCenterX()) < (length + rect[2]) / 2.0 + 1) {
                if (getPrevRightX() < rect[0]) {
                    x = rect[0] - length - 1;
                    xSpd = 0;
                    xAcc = 0;
                }
                else if (getPrevLeftX() > rect[0] + rect[2]){
                    x = rect[0] + rect[2] + 1;
                    xSpd = 0;
                    xAcc = 0;
                }
            }
            
            // Check for collisions with floors
            if (canHitFloor && Math.abs(centerY - getCenterY()) < (height + rect[3]) / 2.0 + 1) {
                if (getPrevTopY() > rect[1] + rect[3]) {
                    y = rect[1] + rect[3] + 1;
                    ySpd = 0;
                }
                else if (getPrevBottomY() <= rect[1]) {
                    y = rect[1] - height - 1;
                    // Wavedash
                    if (isAirDodging) {
                        waveDash();
                    }
                    ySpd = 0;
                }
            }

            // Check if the player is grounded
            if (canHitFloor && getBottomY() + 1 == rect[1]) {
                isGrounded = true;
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
                y = floor[1] - height  - 1;
                
                // Wavedash
                if (isAirDodging) {
                    waveDash();
                }
                ySpd = 0;
            }

            // Check if the player is grounded
            if (getBottomY() + 1 == floor[1]) {
                isGrounded = true;
            }
        }

        // End air dodges that are done
        if (isAirDodging) {
            airDodgeTimeCounter++;
        }
        if (airDodgeTimeCounter >= 12) {
            endAirDodge();
            // TODO: Keep momentum after wavedashing in the air
            if (!isWaveDashing || isGrounded) {
                xSpd = 0;
            }
        }

        // Revert back to being a square after wavedashing
        if (isWaveDashing && (!isAirDodging)) {
            endWaveDash();
        }
    }

    // Jump with a certain speed
    public void jump(double speed) {
        if (isGrounded) {
            // When jumping out of an air dodge, cancel the air dodge
            if (isAirDodging) {
                endAirDodge();
            }

            ySpd = -speed;
            xAcc = 0;
        }
    }

    // Air dodge with a certain direction
    public void airDodge() {
        // Don't air dodge if the Player is grounded
        if (isGrounded) {
            return;
        }

        // Change the fill color of the Player
        fillColor = new Color(0xbe, 0xee, 0xef);

        // Set the direction of the airdodge based on which way the user is holding
        // default is down-right, but it can down-left too
        double direction = 3.9;
        if (movingRight || !movingLeft) {
            direction = 5.5;
        }

        xAcc = 0;
        yAcc = 0;
        xSpd = 6 * Math.cos(direction);
        ySpd = -6 * Math.sin(direction);
        isAirDodging = true;
    }

    // End an air dodge
    public void endAirDodge() {
        fillColor = Color.WHITE;
        yAcc = 45;
        ySpd = 0;
        isAirDodging = false;
        airDodgeTimeCounter = 0;
    }

    // Do a wave dash
    public void waveDash() {
        // Add 0.9x the ySpd to the xSpd
        xSpd += xSpd > 0 ? 0.9 * ySpd : -0.9 * ySpd;
        
        // Make the Player a rectangle
        height = (int) (length / 2 + 0.5);
        y += length / 2;

        isWaveDashing = true;
    }

    // End a wave dash
    public void endWaveDash() {
        height = length;
        y -= length / 2;
        isWaveDashing = false;
    }

    // Start moving right
    public void moveRight() {
        if (!movingRight) {
            xJrk += 3;
            movingRight = true;
        }
    }

    // Start moving left
    public void moveLeft() {
        if (!movingLeft) {
            xJrk -= 3;
            movingLeft = true;
        }
    }

    // Stop moving right
    public void stopMovingRight() {
        xJrk -= 3;
        movingRight = false;
    }

    // Stop moving left
    public void stopMovingLeft() {
        xJrk += 3;
        movingLeft = false;
    }

    // Apply friction
    public void applyFriction(int dt) {
        if (isAirDodging) {
            return;
        }

        // Make the Player go left if they're going right
        if (xSpd > 0 && !movingRight) {
            xAcc -= Math.abs(-0.5 * xSpd + 13);

            if (xSpd + xAcc * dt / 1000 <= 0) {
                xSpd = 0;
                xAcc = 0;
            }
        }
        // Make the player go right if they're going left
        else if (xSpd < 0 && !movingLeft) {
            xAcc += Math.abs(-0.5 * xSpd + 13);

            if (xSpd + xAcc * dt / 1000 >= 0) {
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
        return (int) (y + height + 0.5);
    }

    public int getPrevBottomY() {
        return (int) (y + height - ySpd + 0.5);
    }

    public double getCenterX() {
        return x + length / 2.0;
    }

    public double getCenterY() {
        return y + height / 2.0;
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
