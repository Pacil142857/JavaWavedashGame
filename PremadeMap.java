import java.awt.Color;
import java.awt.Graphics;

public class PremadeMap {
    private Map map;
    private int w;
    private int h;
    private int[][] rects;
    private int[][] lightFloors;

    // Create a PremadeMap with a given width and height
    public PremadeMap(int width, int height) {
        this.w = width;
        this.h = height;
    }

    public void drawMap(Graphics g) {
        map.drawMap(g);
    }

    public void addBorder(Map map) {
        map.addRect(0, h - 50, w, 50);
        map.addRect(0, 0, 25, h);
        map.addRect(w - 25, 0, 25, h);
    }

    // A basic map where wavedashing is not required
    public void drawMap1(Graphics g) {
        map = new Map(7, 1);
        addBorder(map);
        map.addRect(150, h - 120, 50, 70);
        map.addRect(300, h - 160, 100, 110);
        map.addRect(400, h - 90, 30, 40);
        map.addLightFloor(370, h - 230, 280);
        map.addRect(700, h - 160, w - 700, 110);
        map.drawMap(g);

        rects = map.getRects();
        lightFloors = map.getLightFloors();
    }

    // Getters
    public int[][] getRects() {
        return rects;
    }

    public int[][] getLightFloors() {
        return lightFloors;
    }
}
