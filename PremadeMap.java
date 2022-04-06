import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PremadeMap {
    private Map map;
    private int w;
    private int h;
    private int[][] rects;
    private int[][] lightFloors;
    private Hazard[] hazards;
    private Text[] texts;
    private int mapNum = 1;
    private JPanel game;

    // Create a PremadeMap with a given width and height
    public PremadeMap(int width, int height, JPanel game) {
        this.w = width;
        this.h = height;
        this.game = game;
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
            case 8:
                formMap8(g, p, goal);
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
        map = new Map(w, h, 7, 1, 3, game);
        addBorder(map);

        map.addRect(150, h - 120, 50, 70);
        map.addRect(300, h - 160, 100, 110);
        map.addRect(400, h - 90, 30, 40);
        map.addLightFloor(370, h - 230, 280);
        map.addRect(700, h - 160, w - 700, 110);

        map.addText(new Text(30, 20, "A/D - Move left/right"));
        map.addText(new Text(30, 50, "Space - Jump"));
        map.addText(new Text(30, 80, "J - Air dodge (only in the air)"));

        setVariables(map);
    }

    // Another basic map where wavedashing is required
    public void formMap2(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 300, h - 150);
        goal.setSize(40, 100);
        map = new Map(w, h, 6, 2, 2, game);
        addBorder(map);

        map.addRect(370, 0, 2, h - 65);
        map.addRect(570, h - 250, 50, 200);
        map.addRect(594, 0, 2, h - 265);

        map.addLightFloor(520, h - 110, 50);
        map.addLightFloor(370, h - 190, 50);

        map.addText(new Text(30, 20, "Air dodge into the ground to wavedash"));
        map.addText(new Text(140, h - 20, "Wavedashing can get you through small spaces", Color.WHITE));

        setVariables(map);
    }

    // A map that teaches the Player to jump cancel wavedashes
    public void formMap3(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 100);
        p.respawn();
        goal.setLocation(w - 100, h - 310);
        goal.setSize(40, 100);
        map = new Map(w, h, 6, 3, 3, game);
        addBorder(map);

        map.addLightFloor(100, h - 130, 50);
        map.addRect(150, h - 210, 100, 160);
        map.addLightFloor(250, h - 130, 50);

        map.addRect(500, h - 210, 100, 160);
        map.addLightFloor(600, h - 130, 50);
        map.addRect(850, h - 210, w - 775, 160);

        map.addText(new Text(30, 20, "Jump during a wavedash to perform a wavejump, which keeps the extra momentum"));
        map.addText(new Text(30, 50, "Remember: A wavedash is an air dodge into the ground"));
        map.addText(new Text(30, 80, "After landing from a wavejump, you keep your momentum for as long as you hold forward."));

        setVariables(map);
    }

    // A map that uses hazards and teaches the Player to waveland
    public void formMap4(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 300);
        p.respawn();
        goal.setLocation(w - 100, h - 470);
        goal.setSize(40, 100);
        map = new Map(w, h, 6, 2, 1, 3, game);
        addBorder(map);

        map.addRect(25, h - 250, 275, 200);
        map.addRect(500, h - 250, 200, 200);

        map.addLightFloor(675, h - 330, 25);
        map.addLightFloor(675, h - 370, 25);
        map.addRect(700, h - 370, w - 725, 320);

        map.addHazard(new Saw(300, h - 250, 100, Color.RED));
        map.addHazard(new Saw(675, h - 455, 15, Color.RED));
        map.addHazard(new Spikes(650, h - 260, 50, 10, 5));

        map.addText(new Text(705, h - 350, "To waveland, air dodge onto a platform after jumping past it", Color.WHITE));

        setVariables(map);
    }

    // A map that teaches the Player that air dodges and wavedashes are invincible and that momentum is kept after wavedashing off an edge
    public void formMap5(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 471);
        p.respawn();
        goal.setLocation(w - 100, h - 150);
        goal.setSize(40, 100);
        map = new Map(w, h, 5, 0, 2, 4, game);
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
        map.addText(new Text(400, h - 20, "Wavedashing off edges keeps momentum", Color.WHITE));

        setVariables(map);
    }

    // The first non-tutorial map! The Player scales upward, and the goal is on the left
    public void formMap6(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(50, h - 121);
        p.respawn();
        goal.setLocation(50, h - 650);
        goal.setSize(40, 100);
        map = new Map(w, h, 5, 5, 0, 1, game);
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
        map = new Map(w, h, 7, 5, 0, 6, game);
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
        map.addLightFloor(640, h - 170, 50);
        map.addLightFloor(820, h - 250, 50);
        map.addLightFloor(960, h - 330, 50);
        map.addRect(844, h - 409, 2, 145);
        map.addLightFloor(820, h - 410, 50);
        map.addLightFloor(650, h - 491, 30);
        
        // Add the spikes
        map.addHazard(new Spikes(501, h - 60, w - 525, 10, (w - 525) / 10));
        map.addHazard(new Spikes(550, h - 500, 50, 10, 5));
        map.addHazard(new Spikes(375, h - 500, 50, 10, 5));

        setVariables(map);
    }

    // A difficult map with RotatingSaws and Bullets in which the Player must move quickly and carefully
    public void formMap8(Graphics g, Player p, Goal goal) {
        p.setSpawnPoint(30, h - 71);
        p.respawn();
        goal.setLocation(50, h - 570);
        goal.setSize(40, 100);
        map = new Map(w, h, 6, 3, 0, 7, Color.BLACK, Color.WHITE, Color.BLACK, game);
        addBorder(map);

        // Add the rectangles
        map.addRect(25, h - 190, w - 225, 80);
        map.addRect(225, h - 330, w - 225, 80);
        map.addRect(25, h - 470, w - 225, 80);

        // Add the light floors
        map.addLightFloor(w - 125, h - 120, 50);
        map.addLightFloor(125, h - 260, 50);
        map.addLightFloor(w - 125, h - 400, 50);

        // Add the RotatingSaws
        map.addHazard(new RotatingSaw(w - 250, h - 150, 15, 100, Color.RED, false, false));
        map.addHazard(new RotatingSaw(w - 250, h - 430, 15, 100, Color.RED, false, false));
        map.addHazard(new RotatingSaw(250, h - 430, 15, 100, Color.RED, false, false));
        map.addHazard(new RotatingSaw(w / 3, h - 290, 15, 60, Color.RED, true, true));
        map.addHazard(new RotatingSaw(2 * w / 3, h - 290, 15, 60, Color.RED, true, false));

        // Add the Bullets
        map.addHazard(new Bullet(300, 0, 150, 50, 0, 8));
        map.addHazard(new Bullet(700, h - 250, 150, 50, 0, -5));

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

    public int getMapNum() {
        return mapNum;
    }
}
