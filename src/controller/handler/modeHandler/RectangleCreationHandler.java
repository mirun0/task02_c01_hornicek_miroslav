package controller.handler.modeHandler;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import model.Point;
import model.Rectangle;
import utils.GeometryUtils;
import world.Scene2D;

public class RectangleCreationHandler implements ModeHandler {

    private Scene2D scene;
    private Rectangle activeRectangle;

    private boolean pointBmoving;
    private boolean lineCreated;

    public RectangleCreationHandler(Scene2D scene) {
        this.scene = scene;
        activeRectangle = null;
        lineCreated = false;
        pointBmoving = false;
    }
    

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (activeRectangle == null) { // 1. klik
                ArrayList<Point> points = new ArrayList<>();
                points.add(new Point(e.getX(), e.getY(), 0xffff00)); // a
                points.add(new Point(e.getX(), e.getY(), 0xff0000)); // b
                activeRectangle = new Rectangle(points, false);
                scene.getPolygons().add(activeRectangle);
                pointBmoving = true;
                if (Action.GRADIENT.isOn()) {
                    //activeRectangle.getPoints().get(0).setColor(RandomColor.create());
                    //activeRectangle.getPoints().get(1).setColor(RandomColor.create());
                }
            } else if(pointBmoving) { // 2. klik
                if ((activeRectangle.getPointA().getX() != activeRectangle.getPointB().getX() ||
                activeRectangle.getPointA().getY() != activeRectangle.getPointB().getY())) {
                    activeRectangle.getPoints().add(new Point(activeRectangle.getPointB().getX(), activeRectangle.getPointB().getY(), 0x00ff00)); // c
                    activeRectangle.getPoints().add(new Point(activeRectangle.getPointA().getX(), activeRectangle.getPointA().getY(), 0x0000ff)); // d

                    pointBmoving = false;
                    lineCreated = true;
                }
            } else if(lineCreated) { // 3. klik
                activeRectangle = null;
                lineCreated = false;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            activeRectangle = null;
            scene.getPolygons().remove(scene.getPolygons().size() - 1);
            lineCreated = false;
            pointBmoving = false;
        }

        if(activeRectangle != null && activeRectangle.getPoints().size() < 3) {
            if(Action.FILLING.isOn()) {
                activeRectangle.setFill(true);
            } else {
                activeRectangle.setFill(false);
            }
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        if(activeRectangle == null) {
            return;
        }

        if(pointBmoving) {
            setPointB(e.getX(), e.getY());
        }

        if(lineCreated) {
            GeometryUtils.moveToHeight(activeRectangle, e.getX(), e.getY());
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if(activeRectangle == null) {
            return;
        }

        if(pointBmoving) {
            setPointB(e.getX(), e.getY());
        }

        if(lineCreated) {
            GeometryUtils.moveToHeight(activeRectangle, e.getX(), e.getY());
        }
    }

    @Override
    public void clear() {
        activeRectangle = null;
    }

    private void setPointB(int x, int y) {
        if (Action.SNAP.isOn()) {
                Point snapPoint = GeometryUtils.snap(
                        activeRectangle.getPointA().getX(),
                        activeRectangle.getPointA().getY(),
                        x, y);
                activeRectangle.getPointB().setX(snapPoint.getX());
                activeRectangle.getPointB().setY(snapPoint.getY());
        } else {
            activeRectangle.getPointB().setX(x);
            activeRectangle.getPointB().setY(y);
        }
    }

    public void setActiveRectangleNull() {
        scene.getPolygons().remove(activeRectangle);
        activeRectangle = null;
        pointBmoving = false;
        lineCreated = false;
    }
}
