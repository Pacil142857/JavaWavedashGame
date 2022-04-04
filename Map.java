import java.awt.Color;
import java.awt.Graphics;

public class Map {
    private int[][] rects;
    private int[][] lightFloors;
    private Hazard[] hazards;
    private Color fillColor;
    private Color outlineColor;
    private Text[] texts;

    // Create a map with a given number of rectangles, light floors, and texts
    public Map(int numRects, int numLightFloors, int numTexts) {
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.hazards = new Hazard[0];
        this.texts = new Text[numTexts];
        this.fillColor = Color.BLACK;
        this.outlineColor = Color.BLACK;
    }

    // Create a map with a given number of rectangles, light floors, texts, and hazards
    public Map (int numRects, int numLightFloors, int numTexts, int numHazards) {
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.hazards = new Hazard[numHazards];
        this.texts = new Text[numTexts];
        this.fillColor = Color.BLACK;
        this.outlineColor = Color.BLACK;
    }

    // Create a map with certain colors and a given number of rectangles, light floors, texts, and hazards
    public Map(int numRects, int numLightFloors, int numTexts, int numHazards, Color fillColor, Color outlineColor) {
        this.rects = new int[numRects][4];
        this.lightFloors = new int[numLightFloors][3];
        this.hazards = new Hazard[numHazards];
        this.texts = new Text[numTexts];
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
                break;
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
                break;
            }
        }
    }

    // Add text to the map
    public void addText(Text text) {
        for (int i = 0; i < texts.length; i++) {
            if (texts[i] == null) {
                texts[i] = text;
                break;
            }
        }
    }

    // Add a hazard to the map
    public void addHazard(Hazard haz) {
        // Find the first hazard that hasn't been initialized and set it to this hazard
        for (int i = 0; i < hazards.length; i++) {
            if (hazards[i] == null) {
                hazards[i] = haz;
                break;
            }
        }
    }

    // Draw the map
    public void drawMap(Graphics g) {
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
            haz.draw(g);
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
