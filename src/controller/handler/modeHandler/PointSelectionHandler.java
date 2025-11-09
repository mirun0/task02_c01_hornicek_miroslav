package controller.handler.modeHandler;

import java.awt.event.MouseEvent;

import model.Point;
import model.Polygon;
import utils.GeometryUtils;
import world.Scene2D;

public class PointSelectionHandler implements ModeHandler {

    private Scene2D scene;
    private Point closestPoint;
    private Polygon closestPolygon;

    public PointSelectionHandler(Scene2D scene) {
        this.scene = scene;
        this.closestPoint = null;
    }
    

    @Override
    public void onMousePressed(MouseEvent e) {
        closestPoint = GeometryUtils.findPoint(scene, e.getX(), e.getY());
        closestPolygon = GeometryUtils.getClosestPolygon();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        closestPoint = null;
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if(closestPoint == null) {
            return;
        }

        // snapuje pouze na jednu stranu
        if(Action.SNAP.isOn() && closestPolygon != null && closestPolygon.getPoints().contains(closestPoint)) {
            int index = closestPolygon.getPoints().indexOf(closestPoint);
            int nextIndex = (index + 1) % closestPolygon.getPoints().size();
            Point startPoint = closestPolygon.getPoints().get(nextIndex);

            Point snapPoint = GeometryUtils.snap(
                    startPoint.getX(),
                    startPoint.getY(),
                    e.getX(), e.getY());
            closestPoint.setX(snapPoint.getX());
            closestPoint.setY(snapPoint.getY());
        } else {
            closestPoint.setX(e.getX());
            closestPoint.setY(e.getY());
        }
    }

    @Override
    public void clear() {
        closestPoint = null;
    }

}
