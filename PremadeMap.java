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
        map.addRect(0, 0, 25, h - 50);
        map.addRect(w - 25, 0, 25, h - 50);
    }

    // A basic map where wavedashing is not required
    public void formMap1(Graphics g) {
        map = new Map(7, 1);
        addBorder(map);

        map.addRect(150, h - 120, 50, 70);
        map.addRect(300, h - 160, 100, 110);
        map.addRect(400, h - 90, 30, 40);
        map.addLightFloor(370, h - 230, 280);
        map.addRect(700, h - 160, w - 700, 110);

        rects = map.getRects();
        lightFloors = map.getLightFloors();
    }

    // Another basic map where wavedashing is required
    public void formMap2(Graphics g) {
        map = new Map(6, 2);
        addBorder(map);

        map.addRect(300, 0, 2, h - 65);
        map.addRect(500, h - 250, 50, 200);
        map.addRect(524, 0, 2, h - 265);

        map.addLightFloor(450, h - 110, 50);
        map.addLightFloor(300, h - 190, 50);

        rects = map.getRects();
        lightFloors = map.getLightFloors();
    }

    // A map that teaches the Player to jump cancel wavedashes
    public void formMap3(Graphics g) {
        map = new Map(6, 3);
        addBorder(map);

        map.addLightFloor(100, h - 130, 50);
        map.addRect(150, h - 210, 100, 160);
        map.addLightFloor(250, h - 130, 50);

        map.addRect(500, h - 210, 100, 160);
        map.addLightFloor(600, h - 130, 50);
        map.addRect(850, h - 210, w - 775, 160);

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
