import java.awt.Color;
import java.awt.Graphics;

public class PremadeMap {
    private Map map;
    private int w;
    private int h;
    private int[][] rects;
    private int[][] lightFloors;
    private Hazard[] hazards;
    private int mapNum = 1;

    // Create a PremadeMap with a given width and height
    public PremadeMap(int width, int height) {
        this.w = width;
        this.h = height;
    }

    public void toNextMap(Graphics g, Player p, Goal goal) {
        mapNum++;
        switch (mapNum) {
            case 2:
                formMap2(g, p, goal);
                break;
            case 3:
                formMap3(g, p, goal);
                break;
            case 4:
                formMap4(g, p, goal);
                break;
            default:
                formMap1(g, p, goal);
                mapNum = 1;
                break;
        }
        drawMap(g);
    }

    public void drawMap(Graphics g) {
        map.drawMap(g);
    }

    public void addBorder(Map map) {
        map.addRect(0, h - 50, w, 50);
        map.addRect(0, 0, 25, h - 50);
        map.addRect(w - 25, 0, 25, h - 50);
    }

    // Set the variables of this to match a Map
    public void setVariables(Map map) {
        rects = map.getRects();
        lightFloors = map.getLightFloors();
        hazards = map.getHazards();
    }

    // A basic map where wavedashing is not required
    public void formMap1(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 100, h - 260);
        goal.setSize(40, 100);
        map = new Map(7, 1);
        addBorder(map);

        map.addRect(150, h - 120, 50, 70);
        map.addRect(300, h - 160, 100, 110);
        map.addRect(400, h - 90, 30, 40);
        map.addLightFloor(370, h - 230, 280);
        map.addRect(700, h - 160, w - 700, 110);

        setVariables(map);
    }

    // Another basic map where wavedashing is required
    public void formMap2(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 100, h - 150);
        goal.setSize(40, 100);
        map = new Map(6, 2);
        addBorder(map);

        map.addRect(300, 0, 2, h - 65);
        map.addRect(500, h - 250, 50, 200);
        map.addRect(524, 0, 2, h - 265);

        map.addLightFloor(450, h - 110, 50);
        map.addLightFloor(300, h - 190, 50);

        setVariables(map);
    }

    // A map that teaches the Player to jump cancel wavedashes
    public void formMap3(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 100, h - 310);
        goal.setSize(40, 100);
        map = new Map(6, 3);
        addBorder(map);

        map.addLightFloor(100, h - 130, 50);
        map.addRect(150, h - 210, 100, 160);
        map.addLightFloor(250, h - 130, 50);

        map.addRect(500, h - 210, 100, 160);
        map.addLightFloor(600, h - 130, 50);
        map.addRect(850, h - 210, w - 775, 160);

        setVariables(map);
    }

    // A map that uses hazards and teaches the Player to waveland
    public void formMap4(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 300);
        p.respawn();
        goal.setLocation(w - 100, h - 470);
        goal.setSize(40, 100);
        map = new Map(6, 2, 2);
        addBorder(map);

        map.addRect(25, h - 250, 275, 200);
        map.addRect(500, h - 250, 200, 200);

        map.addLightFloor(675, h - 330, 25);
        map.addLightFloor(675, h - 370, 25);
        map.addRect(700, h - 370, w - 725, 320);

        map.addHazard(new Saw(300, h - 250, 100, Color.RED));
        map.addHazard(new Saw(675, h - 455, 15, Color.RED));

        setVariables(map);
    }

    // Getters
    public int[][] getRects() {
        return rects;
    }

    public int[][] getLightFloors() {
        return lightFloors;
    }

    public Hazard[] getHazards() {
        return hazards;
    }
}
