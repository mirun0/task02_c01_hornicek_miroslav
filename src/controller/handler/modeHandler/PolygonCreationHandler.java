package controller.handler.modeHandler;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;

import model.Point;
import model.Polygon;
import utils.GeometryUtils;
import utils.RandomColor;
import world.Scene2D;

public class PolygonCreationHandler implements ModeHandler {

    private Polygon activePolygon;
    private Scene2D scene;

    public PolygonCreationHandler(Scene2D scene) {
        this.activePolygon = null;
        this.scene = scene;
    }

    public Polygon getActivePolygon() {
        return activePolygon;
    }

    public void setActivePolygonNull() {
        scene.getPolygons().remove(activePolygon);
        activePolygon = null;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (activePolygon == null) {
                ArrayList<Point> points = new ArrayList<>();
                points.add(new Point(e.getX(), e.getY(), 0xffffff));
                Point b = new Point(e.getX(), e.getY(), 0xffffff);
                if (Action.GRADIENT.isOn()) {
                    b.setColor(RandomColor.create());
                }
                points.add(b);
                activePolygon = new Polygon(points, null);
                scene.getPolygons().add(activePolygon);
            } else {
                Point p = new Point(e.getX(), e.getY(), 0xffffff);
                if (Action.GRADIENT.isOn()) {
                    p.setColor(RandomColor.create());
                }
                activePolygon.addPoint(p);
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if(activePolygon != null) {
                if(activePolygon.getPoints().size() > 3) {
                    activePolygon.getPoints().remove(activePolygon.size() - 1);
                } else {
                    scene.getPolygons().remove(activePolygon);
                }
                activePolygon = null;
            }
        }

        if(activePolygon != null) {
            if(Action.FILLING.isOn()) {
                activePolygon.setFillColor(Optional.of(0xff00f0));
            } else {
                activePolygon.setFillColor(null);
            }
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        if(activePolygon == null) {
            return;
        }
        setLastPoint(e.getX(), e.getY());
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if(activePolygon == null) {
            return;
        }
        setLastPoint(e.getX(), e.getY());
    }

    private void setLastPoint(int x, int y) {
        if (Action.SNAP.isOn() && activePolygon.size() > 1) {
                Point snapPoint = GeometryUtils.snap(
                        activePolygon.getPoints().get(activePolygon.size() - 2).getX(),
                        activePolygon.getPoints().get(activePolygon.size() - 2).getY(),
                        x, y);
                activePolygon.getPoints().get(activePolygon.size() - 1).setX(snapPoint.getX());
                activePolygon.getPoints().get(activePolygon.size() - 1).setY(snapPoint.getY());
        } else {
            activePolygon.getPoints().get(activePolygon.size() - 1).setX(x);
            activePolygon.getPoints().get(activePolygon.size() - 1).setY(y);
        }
    }

    @Override
    public void clear() {
        activePolygon = null;
    }
}
