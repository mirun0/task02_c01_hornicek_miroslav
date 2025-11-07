package model;

import java.util.ArrayList;
import java.util.Optional;

public class Polygon {
    
    private ArrayList<Point> points;
    private Optional<Integer> fillColor;

    public Polygon(ArrayList<Point> points, Optional<Integer> fillColor) {
        this.points = points;
        this.fillColor = fillColor;
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

    public Optional<Integer> getFillColor() {
        return fillColor;
    }

    public void setFillColor(Optional<Integer> fillColor) {
        this.fillColor = fillColor;
    }

    public void clear() {
        points.clear();
    }

}
