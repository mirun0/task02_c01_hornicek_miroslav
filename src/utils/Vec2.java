package utils;

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

    public float cross(Vec2 vec) {
        return this.x * vec.y - this.y * vec.x;
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
}
