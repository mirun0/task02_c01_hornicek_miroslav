package controller.handler.modeHandler;

import java.awt.event.MouseEvent;

import utils.GeometryUtils;
import world.Scene2D;

public class AddPointHandler implements ModeHandler {

    private Scene2D scene;

    public AddPointHandler(Scene2D scene) {
        this.scene = scene;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        GeometryUtils.createNewPoint(scene, e.getX(), e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        
    }

    @Override
    public void clear() {
        
    }
    
}
