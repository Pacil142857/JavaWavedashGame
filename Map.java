import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Map {
    private int w;
    private int h;
    private int numRects = 0;
    private int numLightFloors = 0;
    private int numHazards = 0;
    private int numTexts = 0;
    private int[][] rects;
    private int[][] lightFloors;
    private Hazard[] hazards;
    private Color fillColor;
    private Color outlineColor;
    private Color backgroundColor;
    private Text[] texts;
    private JPanel game;

    // Create a map with a given number of rectangles, light floors, and texts
    public Map(int width, int height, int numRects, int numLightFloors, int numTexts, JPanel game) {
        this.w = width;
        this.h = height;
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.hazards = new Hazard[0];
        this.texts = new Text[numTexts];
        this.fillColor = Color.BLACK;
        this.outlineColor = Color.BLACK;
        this.backgroundColor = Color.WHITE;
        this.game = game;
    }

    // Create a map with a given number of rectangles, light floors, texts, and hazards
    public Map (int width, int height, int numRects, int numLightFloors, int numTexts, int numHazards, JPanel game) {
        this.w = width;
        this.h = height;
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.hazards = new Hazard[numHazards];
        this.texts = new Text[numTexts];
        this.fillColor = Color.BLACK;
        this.outlineColor = Color.BLACK;
        this.backgroundColor = Color.WHITE;
        this.game = game;
    }

    // Create a map with certain colors and a given number of rectangles, light floors, texts, and hazards
    public Map(int width, int height, int numRects, int numLightFloors, int numTexts, int numHazards, Color fillColor, Color outlineColor, Color backgroundColor, JPanel game) {
        this.w = width;
        this.h = height;
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.hazards = new Hazard[numHazards];
        this.texts = new Text[numTexts];
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.backgroundColor = backgroundColor;
        this.game = game;
    }

    // Add a rectangle to the map
    public void addRect(int x, int y, int w, int h) {
        rects[numRects][0] = x;
        rects[numRects][1] = y;
        rects[numRects][2] = w;
        rects[numRects][3] = h;
        numRects++;
    }

    // Add a light floor to the map
    public void addLightFloor(int x, int y, int w) {
        lightFloors[numLightFloors][0] = x;
        lightFloors[numLightFloors][1] = y;
        lightFloors[numLightFloors][2] = x + w;
        numLightFloors++;
    }

    // Add text to the map
    public void addText(Text text) {
        texts[numTexts] = text;
        numTexts++;
    }

    // Add a hazard to the map
    public void addHazard(Hazard haz) {
        hazards[numHazards] = haz;
        numHazards++;
    }

    // Draw the map
    public void drawMap(Graphics g) {
        // Draw the background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, w, h);

        // Fill the rectangles
        g.setColor(fillColor);
        for (int[] rect : rects) {
            g.fillRect(rect[0], rect[1], rect[2], rect[3]);
        }

        // Outline the rectangles and draw the light floors
        g.setColor(outlineColor);
        for (int[] rect : rects) {
            g.drawRect(rect[0], rect[1], rect[2], rect[3]);
        }
        for (int[] floor : lightFloors) {
            g.drawLine(floor[0], floor[1], floor[2], floor[1]);
        }

        // Draw the hazards
        for (Hazard haz : hazards) {
            haz.draw(g, game);
        }

        // Write out text
        for (Text text : texts) {
            g.setColor(text.getColor());
            g.drawString(text.getStr(), text.getX(), text.getY());
        }
    }

    // Getters and setters
    public int[][] getRects() {
        return this.rects;
    }

    public void setRects(int[][] rects) {
        this.rects = rects;
    }

    public int[][] getLightFloors() {
        return this.lightFloors;
    }

    public void setLightFloors(int[][] lightFloors) {
        this.lightFloors = lightFloors;
    }

    public Hazard[] getHazards() {
        return this.hazards;
    }

    public void setHazards(Hazard[] hazards) {
        this.hazards = hazards;
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

    public Text[] getTexts() {
        return texts;
    }

    public void setTexts(Text[] texts) {
        this.texts = texts;
    }
}
