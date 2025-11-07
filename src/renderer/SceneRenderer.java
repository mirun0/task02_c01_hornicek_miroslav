package renderer;

import controller.handler.modeHandler.Action;
//import fill.SeedFiller;
import fill.scanline.ScanlineFiller;
import model.Line;
import model.Point;
import model.Polygon;
//import model.Seed;
import rasterize.LineRasterizerGradient;
import rasterize.LineRasterizerTrivial;
import rasterize.Raster;
import world.Scene2D;

public class SceneRenderer {

    private final Scene2D scene;
    private final Raster raster;

    private final LineRasterizerTrivial lineRasterizerTrivial;
    private final LineRasterizerGradient lineRasterizerGradient;
    //private final SeedFiller seedFiller;
    private final ScanlineFiller scanlineFiller;

    public SceneRenderer(Raster raster, Scene2D scene) {
        this.scene = scene;
        this.raster = raster;

        this.lineRasterizerTrivial = new LineRasterizerTrivial(raster);
        this.lineRasterizerGradient = new LineRasterizerGradient(raster);
        //this.seedFiller = new SeedFiller(raster, 0x00ff00);
        this.scanlineFiller = new ScanlineFiller(raster, 0x00ff00);
    }

    public void renderScene() {
        if(Action.FILLING.isOn()) {
            renderFill();   
        }
        renderLines();
        renderPolygons();
        renderPoints();
    }

    private void renderLines() {
        for (Line line : scene.getLines()) {
            drawLine(line);
        }
    }

    private void renderPolygons() {
        for (Polygon polygon : scene.getPolygons()) {
            drawPolygon(polygon);
        }
    }

    private void renderFill() {
        /*for (Seed seed : scene.getSeeds()) {
            seedFiller.setStart(seed.getX(), seed.getY());
            seedFiller.setColor(seed.getColor());
            seedFiller.fill();
        }*/
        for(Polygon polygon : scene.getPolygons()) {
            if(polygon.getFillColor() != null) {
                scanlineFiller.setPolygon(polygon);
                scanlineFiller.setColor(polygon.getFillColor().get());
                scanlineFiller.fill();
            }
        }
    }

    private void renderPoints() {
        for (Line line : scene.getLines()) {
            drawPoint(line.getPointA());
            drawPoint(line.getPointB());
        }

        for (Polygon polygon : scene.getPolygons()) {
            for (Point p : polygon.getPoints()) {
                drawPoint(p);
            }
        }
    }

    private void drawLine(Line line) {
        Point a = line.getPointA();
        Point b = line.getPointB();

        if (a.getColor() == b.getColor()) {
            lineRasterizerTrivial.setColor(a.getColor());
            lineRasterizerTrivial.rasterize(a.getX(), a.getY(), b.getX(), b.getY());
        } else {
            lineRasterizerGradient.setColor(a.getColor());
            lineRasterizerGradient.setGradient(b.getColor());
            lineRasterizerGradient.rasterize(a.getX(), a.getY(), b.getX(), b.getY());
        }
    }

    private void drawPolygon(Polygon polygon) {
        for (int i = 0; i < polygon.size() - 1; i++) {
            drawLine(new Line(polygon.getPoints().get(i), polygon.getPoints().get(i + 1)));
        }
        drawLine(new Line(polygon.getPoints().get(polygon.size() - 1), polygon.getPoints().get(0)));
    }

    private void drawPoint(Point p) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                raster.setPixel(p.getX() + dx, p.getY() + dy, p.getColor());
            }
        }
    }
}
