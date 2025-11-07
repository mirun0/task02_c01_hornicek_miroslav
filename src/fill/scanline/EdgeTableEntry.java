package fill.scanline;

import model.Point;

public class EdgeTableEntry {
    final float yMin;
    final float yMax;
    float currentX;
    final float inverseSlope;

    public EdgeTableEntry(Point a, Point b) {
        Point pMin = a.getY() <= b.getY() ? a : b;
        Point pMax = a.getY() <= b.getY() ? b : a;

        float dy = b.getY() - a.getY();
        float dx = b.getX() - a.getX();

        if(dy == 0) {
            this.yMin = this.yMax = this.currentX = this.inverseSlope = 0;
            return;
        }

        this.yMin = pMin.getY();
        this.yMax = pMax.getY();
        this.currentX = pMin.getX();
        this.inverseSlope = dx / dy;
    }

    boolean isValid() { 
        return yMin != yMax; 
    }

    @Override
    public String toString() {
        return "yMin=" + yMin + " yMax=" + yMax + " currentX=" + currentX + " inverseSlope=" + inverseSlope;

    }

}