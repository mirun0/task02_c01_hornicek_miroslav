package controller.handler.modeHandler;

import java.awt.event.MouseEvent;

import model.Line;
import model.Point;
import utils.GeometryUtils;
import utils.RandomColor;
import world.Scene2D;

public class LineCreationHandler implements ModeHandler {

    private Line activeLine;
    private Scene2D scene;

    public LineCreationHandler(Scene2D scene) {
        this.activeLine = null;
        this.scene = scene;
    }

    public Line getActiveLine() {
        return activeLine;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (activeLine == null) {
                activeLine = new Line(new Point(e.getX(), e.getY(), 0xffffff), new Point(e.getX(), e.getY(), 0xffffff));
                scene.getLines().add(activeLine);
                if (Action.GRADIENT.isOn()) {
                    activeLine.getPointA().setColor(RandomColor.create());
                    activeLine.getPointB().setColor(RandomColor.create());
                }
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            activeLine = null;
            scene.getLines().remove(scene.getLines().size() - 1);
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if(activeLine == null) {
            return;
        }

        if ((activeLine.getPointA().getX() != activeLine.getPointB().getX() ||
            activeLine.getPointA().getY() != activeLine.getPointB().getY())) {

            activeLine = null;
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        if(activeLine == null) {
            return;
        }
        updatePointB(e.getX(), e.getY());
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if(activeLine == null) {
            return;
        }
        updatePointB(e.getX(), e.getY());
    }

    private void updatePointB(int x, int y) {
        if (Action.SNAP.isOn()) {
                Point snapPoint = GeometryUtils.snap(
                        activeLine.getPointA().getX(),
                        activeLine.getPointA().getY(),
                        x, y);
                activeLine.getPointB().setX(snapPoint.getX());
                activeLine.getPointB().setY(snapPoint.getY());
        } else {
            activeLine.getPointB().setX(x);
            activeLine.getPointB().setY(y);
        }
    }

    @Override
    public void clear() {
        activeLine = null;
    }

    public void setActiveLineNull() {
        activeLine = null;
    }

}
