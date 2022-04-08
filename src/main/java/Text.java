package src.main.java;

import java.awt.Color;

public class Text {
    private int x;
    private int y;
    private String str;
    private Color color;

    // Create a piece of text at a given location with a given string
    public Text(int x, int y, String str) {
        this.x = x;
        this.y = y;
        this.str = str;
        this.color = Color.BLACK;
    }

    // Create a piece of text at a given location with a given string and color
    public Text(int x, int y, String str, Color color) {
        this.x = x;
        this.y = y;
        this.str = str;
        this.color = color;
    }

    // Getters and setters
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getStr() {
        return this.str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
