import java.awt.Color;
import java.awt.Graphics;

public class Map {
    private int[][] rects;
    private int[][] lightFloors;
    private Color fillColor;
    private Color outlineColor;

    // Create a map with a given number of rectangles and light floors
    public Map(int numRects, int numLightFloors) {
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.fillColor = Color.WHITE;
        this.outlineColor = Color.BLACK;
    }

    // Create a map with certain colors and a given number of rectangles and light floors
    public Map(int numRects, int numLightFloors, Color fillColor, Color outlineColor) {
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
    }

    // Add a rectangle to the map
    public void addRect(int x, int y, int w, int h) {
        // Find the first entry in rects that hasn't been initialized
        for (int i = 0; i < rects.length; i++) {
            if (rects[i][0] == 0 && rects[i][1] == 0 && rects[i][2] == 0 && rects[i][3] == 0) {
                // Update rects
                rects[i][0] = x;
                rects[i][1] = y;
                rects[i][2] = w;
                rects[i][3] = h;
            }
        }
    }

    // Add a light floor to the map
    public void addLightFloor(int x, int y, int w) {
        // Find the first entry in lightFloors that hasn't been initialized
        for (int i = 0; i < lightFloors.length; i++) {
            if (lightFloors[i][0] == 0 && lightFloors[i][1] == 0 && lightFloors[i][2] == 0) {
                // Add the light flooor
                lightFloors[i][0] = x;
                lightFloors[i][1] = y;
                lightFloors[i][2] = x + w;
            }
        }
    }

    // Draw all rectangles and light floors
    public void drawMap(Graphics g) {
        // First, fill the rectangles
        g.setColor(fillColor);
        for (int[] rect : rects) {
            g.fillRect(rect[0], rect[1], rect[2], rect[3]);
        }

        // Next, outline the rectangles and draw the light floors
        g.setColor(outlineColor);
        for (int[] rect : rects) {
                g.drawRect(rect[0], rect[1], rect[2], rect[3]);
        }
        for (int[] floor : lightFloors) {
            g.drawLine(floor[0], floor[1], floor[2], floor[1]);
        }
    }
}
