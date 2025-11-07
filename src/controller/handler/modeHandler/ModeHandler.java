package controller.handler.modeHandler;

import java.awt.event.MouseEvent;

public interface ModeHandler {
    void onMousePressed(MouseEvent e);
    void onMouseReleased(MouseEvent e);
    void onMouseMoved(MouseEvent e);
    void onMouseDragged(MouseEvent e);
    void clear();
}
