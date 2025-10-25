package model;

public class Seed {
    private int x;
    private int y;
    private int color;
    private int backgroundColor;

    public Seed(int x, int y, int color, int backgroundColor) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.backgroundColor = backgroundColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
