package model;

import java.util.ArrayList;

public class Rectangle extends Polygon {

    public Rectangle(ArrayList<Point> points, boolean fill) {
        super(points, fill);
    }

    public Point getPointA() {
        if(this.getPoints().get(0) != null) {
            return this.getPoints().get(0);
        }
        return null;
    }
    
    public Point getPointB() {
        if(this.getPoints().get(1) != null) {
            return this.getPoints().get(1);
        }
        return null;
    }

    public Point getPointC() {
        if(this.getPoints().get(2) != null) {
            return this.getPoints().get(2);
        }
        return null;
    }
    
    public Point getPointD() {
        if(this.getPoints().get(3) != null) {
            return this.getPoints().get(3);
        }
        return null;
    }
}
