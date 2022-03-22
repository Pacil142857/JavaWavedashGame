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
        this.lightFloors = new int[numLightFloors][4];
        this.fillColor = Color.WHITE;
        this.outlineColor = Color.BLACK;
    }

    // Create a map with certain colors and a given number of rectangles and light floors
    public Map(int numRects, int numLightFloors, Color fillColor, Color outlineColor) {
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][4];
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

    // TODO: Add method to add light floors and edit the drawMap method afterwards

    // Draw all rectangles and light floors
    public void drawMap(Graphics g) {
        // First, fill the rectangles
        g.setColor(fillColor);
        for (int[] rect : rects) {
            g.fillRect(rect[0], rect[1], rect[2], rect[3]);
        }

        // Next, outline the rectangles
        g.setColor(outlineColor);
        for (int[] rect : rects) {
                g.drawRect(rect[0], rect[1], rect[2], rect[3]);
        }
    }
}
