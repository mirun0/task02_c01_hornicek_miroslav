package world;

import java.util.ArrayList;

import model.Line;
import model.Polygon;
import model.Seed;

public class Scene2D {

    private ArrayList<Polygon> polygons;
    private ArrayList<Line> lines;
    private ArrayList<Seed> seeds;

    public Scene2D() {
        this.polygons = new ArrayList<Polygon>();
        this.lines = new ArrayList<Line>();
        this.seeds = new ArrayList<Seed>();
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public ArrayList<Seed> getSeeds() {
        return seeds;
    }

    public void clear() {
        polygons.clear();
        lines.clear();
        seeds.clear();
    }
}
