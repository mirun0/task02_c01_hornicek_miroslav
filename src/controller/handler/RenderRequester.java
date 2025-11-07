package controller.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import controller.Controller2D;

public class RenderRequester implements KeyListener, MouseListener, MouseMotionListener {

    private Controller2D controller;

    public RenderRequester(Controller2D controller) {
        this.controller = controller;
    }

    @Override public void keyTyped(KeyEvent e) { controller.update(e); }
    @Override public void keyPressed(KeyEvent e) { controller.update(e); }
    @Override public void keyReleased(KeyEvent e) { controller.update(e); }
    @Override public void mouseDragged(MouseEvent e) { controller.update(e); }
    @Override public void mouseMoved(MouseEvent e) { controller.update(e); }
    @Override public void mouseClicked(MouseEvent e) { controller.update(e); }
    @Override public void mousePressed(MouseEvent e) { controller.update(e); }
    @Override public void mouseReleased(MouseEvent e) { controller.update(e); }
    @Override public void mouseEntered(MouseEvent e) { controller.update(e); }
    @Override public void mouseExited(MouseEvent e) { controller.update(e); }
}
