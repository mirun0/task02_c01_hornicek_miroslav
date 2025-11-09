package utils;

import model.Point;

public class Vec2 {
    private float x;
    private float y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 normalize() {
        float len = (float)Math.sqrt(x*x + y*y);
        return new Vec2(x / len, y / len);
    }

    public float dot(Vec2 vec) {
        return this.x * vec.x + this.y * vec.y;
    }

    public float cross(Vec2 vec) {
        return this.x * vec.y - this.y * vec.x;
    }

    public Vec2 direction(Vec2 to) {
        return new Vec2(to.getX() - this.x, to.getY() - this.y); 
    }

    public Vec2 normal() {
        return new Vec2(-y, x);
    }

    public Vec2 scale(float scale) {
        return new Vec2(x * scale, y * scale);
    }

    public Vec2 add(Vec2 vec) {
        return new Vec2(x + vec.x, y + vec.y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 fromPoint(Point p) {
        return new Vec2(p.getX(), p.getY());
    }
}
