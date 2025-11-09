package controller.handler.modeHandler;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import clip.Clipper;
import model.Point;
import model.Polygon;
import utils.GeometryUtils;
import world.Scene2D;

public class PolygonClippingHandler implements ModeHandler {

    Scene2D scene;
    Polygon clippingPolygon;
    Polygon subjectPolygon;

    Clipper cliper;
    Polygon clipped;

    Point closestPoint;

    public PolygonClippingHandler(Scene2D scene) {
        this.scene = scene;
        createScene();
    }

    private void createScene() {

        /*
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(100, 100, 0x00ffff));
        points.add(new Point(500, 300, 0x00ffff));
        points.add(new Point(200, 300, 0x00ffff));
        clippingPolygon = new Polygon(points, false);
        scene.getPolygons().add(clippingPolygon);

        points = new ArrayList<>();
        points.add(new Point(300, 100, 0xff00ff));
        points.add(new Point(500, 200, 0xff00ff));
        points.add(new Point(200, 400, 0xff00ff));
        subjectPolygon = new Polygon(points, false);
        scene.getPolygons().add(subjectPolygon);
        */
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(161, 61, 0x00ffff));
        points.add(new Point(319, 111, 0x00ffff));
        points.add(new Point(379, 316, 0x00ffff));
        points.add(new Point(214, 487, 0x00ffff));
        points.add(new Point(43, 394, 0x00ffff));
        points.add(new Point(40, 169, 0x00ffff));
        clippingPolygon = new Polygon(points, false);
        scene.getPolygons().add(clippingPolygon);

        points = new ArrayList<>();
        points.add(new Point(317, 58, 0xff00ff));
        points.add(new Point(595, 117, 0xff00ff));
        points.add(new Point(599, 410, 0xff00ff));
        points.add(new Point(421, 455, 0xff00ff));
        points.add(new Point(278, 356, 0xff00ff));
        points.add(new Point(220, 194, 0xff00ff));
        subjectPolygon = new Polygon(points, false);
        scene.getPolygons().add(subjectPolygon);

        cliper = new Clipper();
        clipped = cliper.clip(clippingPolygon, subjectPolygon);
        clipped.setFill(true);
        scene.getPolygons().add(clipped);

    }

    @Override
    public void onMousePressed(MouseEvent e) {
        closestPoint = GeometryUtils.findPoint(scene, e.getX(), e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    @Override
    public void onMouseMoved(MouseEvent e) {

    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if(closestPoint == null || cliper == null) {
            return;
        }

        closestPoint.setX(e.getX());
        closestPoint.setY(e.getY());
        scene.getPolygons().remove(clipped);
        clipped = cliper.clip(clippingPolygon, subjectPolygon);
        clipped.setFill(true);
        scene.getPolygons().add(clipped);
    }

    @Override
    public void clear() {
        clippingPolygon = null;
        subjectPolygon = null;
        cliper = null;
        clipped = null;
        closestPoint = null;
    }
    
}
