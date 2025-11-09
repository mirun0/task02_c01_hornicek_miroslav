package model;

import java.util.ArrayList;

public class Polygon {
    
    private ArrayList<Point> points;
    private boolean fill;

    public Polygon(ArrayList<Point> points, boolean fill) {
        this.points = points;
        this.fill = fill;
    }

    public int size() {
        return points.size();
    }

    public void addPoint(Point point) {
        points.add(point);
    }
    
    public ArrayList<Point> getPoints() {
        return points;
    }

    public boolean getFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public void clear() {
        points.clear();
    }

}
