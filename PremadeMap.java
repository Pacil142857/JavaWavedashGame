import java.awt.Color;
import java.awt.Graphics;

public class PremadeMap {
    private Map map;
    private int w;
    private int h;
    private int[][] rects;
    private int[][] lightFloors;
    private Hazard[] hazards;
    private Text[] texts;
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
            case 5:
                formMap5(g, p, goal);
                break;
            case 6:
                formMap6(g, p, goal);
                break;
            case 7:
                formMap7(g, p, goal);
                break;
            default:
                formMap1(g, p, goal);
                mapNum = 1;
                break;
        }
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
        texts = map.getTexts();
    }

    // A basic map where wavedashing is not required
    public void formMap1(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 100, h - 260);
        goal.setSize(40, 100);
        map = new Map(7, 1, 3);
        addBorder(map);

        map.addRect(150, h - 120, 50, 70);
        map.addRect(300, h - 160, 100, 110);
        map.addRect(400, h - 90, 30, 40);
        map.addLightFloor(370, h - 230, 280);
        map.addRect(700, h - 160, w - 700, 110);

        map.addText(new Text(30, 20, "A/D - Move left/right"));
        map.addText(new Text(30, 50, "Space - Jump"));
        map.addText(new Text(30, 80, "J - Air dodge"));

        setVariables(map);
    }

    // Another basic map where wavedashing is required
    public void formMap2(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 300, h - 150);
        goal.setSize(40, 100);
        map = new Map(6, 2, 2);
        addBorder(map);

        map.addRect(300, 0, 2, h - 65);
        map.addRect(500, h - 250, 50, 200);
        map.addRect(524, 0, 2, h - 265);

        map.addLightFloor(450, h - 110, 50);
        map.addLightFloor(300, h - 190, 50);

        map.addText(new Text(550, 20, "Air dodge into the ground to perform a wavedash"));
        map.addText(new Text(550, 50, "Wavedashing can get you through small places"));

        setVariables(map);
    }

    // A map that teaches the Player to jump cancel wavedashes
    public void formMap3(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 100, h - 310);
        goal.setSize(40, 100);
        map = new Map(6, 3, 1);
        addBorder(map);

        map.addLightFloor(100, h - 130, 50);
        map.addRect(150, h - 210, 100, 160);
        map.addLightFloor(250, h - 130, 50);

        map.addRect(500, h - 210, 100, 160);
        map.addLightFloor(600, h - 130, 50);
        map.addRect(850, h - 210, w - 775, 160);

        map.addText(new Text(30, 20, "Jumping during a wavedash lets you keep the extra momentum"));

        setVariables(map);
    }

    // A map that uses hazards and teaches the Player to waveland
    public void formMap4(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 300);
        p.respawn();
        goal.setLocation(w - 100, h - 470);
        goal.setSize(40, 100);
        map = new Map(6, 2, 1, 3);
        addBorder(map);

        map.addRect(25, h - 250, 275, 200);
        map.addRect(500, h - 250, 200, 200);

        map.addLightFloor(675, h - 330, 25);
        map.addLightFloor(675, h - 370, 25);
        map.addRect(700, h - 370, w - 725, 320);

        map.addHazard(new Saw(300, h - 250, 100, Color.RED));
        map.addHazard(new Saw(675, h - 455, 15, Color.RED));
        map.addHazard(new Spikes(650, h - 260, 50, 10, 5));

        map.addText(new Text(30, 20, "To waveland, air dodge onto a platform after jumping past it"));

        setVariables(map);
    }

    // A map that teaches the Player that air dodges and wavedashes are invincible and that momentum is kept after wavedashing off an edge
    public void formMap5(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 471);
        p.respawn();
        goal.setLocation(w - 100, h - 150);
        goal.setSize(40, 100);
        map = new Map(5, 0, 2, 4);
        addBorder(map);

        // Add the elevated floor, wall coming from the ceiling, and spikes
        map.addRect(25, h - 450, 375, 400);
        map.addRect(99, 0, 2, h - 465);
        map.addHazard(new Spikes(90, h - 455, 20, 5, 4));
        map.addHazard(new Spikes(380, h - 455, 20, 5, 4));

        // Add a saw and the last set of spikes
        map.addHazard(new Saw(340, h - 680, 100, Color.RED));
        map.addHazard(new Spikes(400, h - 60, 300, 10, 30));

        map.addText(new Text(10, h - 430, "While air dodging, you are invincible", Color.WHITE));
        map.addText(new Text(10, h - 400, "Wavedashing off edges keeps momentum", Color.WHITE));

        setVariables(map);
    }

    // The first non-tutorial map! The Player scales upward, and the goal is on the left
    public void formMap6(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 121);
        p.respawn();
        goal.setLocation(50, h - 650);
        goal.setSize(40, 100);
        map = new Map(5, 5, 0, 1);
        addBorder(map);

        // Add the Player's floor, the goal's floor, and the spikes
        map.addRect(25, h - 100, 125, 50);
        map.addRect(25, h - 550, 125, 20);
        map.addHazard(new Spikes(150, h - 60, w - 175, 10, (w - 175) / 10));

        // Add the light floors for the Player to scale
        map.addLightFloor(200, h - 170, 50);
        map.addLightFloor(380, h - 250, 50);
        map.addLightFloor(540, h - 330, 50);
        map.addLightFloor(380, h - 410, 50);
        map.addLightFloor(200, h - 490, 50);

        setVariables(map);
    }

    // Uses the concept from map 6 while adding more sections with difficulty induced by hazards
    public void formMap7(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(26, h - 121);
        p.respawn();
        goal.setLocation(50, h - 590);
        goal.setSize(40, 100);
        map = new Map(7, 4, 0, 6);
        addBorder(map);

        // Add the floors
        map.addRect(25, h - 100, 125, 50);
        map.addRect(25, h - 490, 625, 20);
        map.addRect(275, h - 100, 225, 50);

        // Add the saws and spikes in the beginning
        map.addHazard(new Saw(150, h - 175, 62, Color.RED));
        map.addHazard(new Saw(150, h - 350, 62, Color.RED));
        map.addHazard(new Spikes(275, h - 110, 40, 10, 4));

        // Add the light floor portion of the map
        map.addLightFloor(650, h - 170, 50);
        map.addLightFloor(830, h - 250, 50);
        map.addLightFloor(970, h - 330, 50);
        map.addRect(854, h - 410, 2, 145);
        map.addLightFloor(830, h - 410, 50);
        
        // Add the spikes
        map.addHazard(new Spikes(501, h - 60, w - 525, 10, (w - 525) / 10));
        map.addHazard(new Spikes(550, h - 500, 50, 10, 5));
        map.addHazard(new Spikes(375, h - 500, 50, 10, 5));
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

    public Text[] getTexts() {
        return texts;
    }
}
