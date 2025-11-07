package controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import controller.handler.RenderRequester;
import controller.handler.inputHandler.KeyHandler;
import controller.handler.modeHandler.Action;
import controller.handler.modeHandler.AddPointHandler;
import controller.handler.modeHandler.DeletePointHandler;
import controller.handler.modeHandler.LineCreationHandler;
import controller.handler.modeHandler.Mode;
import controller.handler.modeHandler.ModeHandler;
import controller.handler.modeHandler.PointSelectionHandler;
import controller.handler.modeHandler.PolygonCreationHandler;
import controller.handler.modeHandler.RectangleCreationHandler;
import model.Seed;
import renderer.Renderer2D;
import view.Panel;
import world.Scene2D;

public class Controller2D {

    private Panel panel;
    private Scene2D scene2d;

    private int w;
    private int h;

    private KeyHandler keyHandler;
    private Map<Mode, ModeHandler> modeHandlers;

    private Renderer2D renderer2d;
    private RenderRequester renderRequester;

    public Controller2D(Panel panel) {
        this.panel = panel;

        this.w = panel.getRaster().getWidth();
        this.h = panel.getRaster().getHeight();

        this.scene2d = new Scene2D();
        this.renderer2d = new Renderer2D(panel.getRaster(), scene2d);
        this.renderRequester = new RenderRequester(this);

        this.keyHandler = new KeyHandler();
        this.modeHandlers = new HashMap<Mode, ModeHandler>();
        initHandlers();

        initListeners();

        renderer2d.renderUI(keyHandler.getActiveMode(), w, h);
        panel.repaint();
    }

    private void initHandlers() {
        modeHandlers.put(Mode.LINE_CREATION, new LineCreationHandler(scene2d));
        modeHandlers.put(Mode.POLYGON_CREATION, new PolygonCreationHandler(scene2d));
        modeHandlers.put(Mode.POINT_MOVING, new PointSelectionHandler(scene2d));
        modeHandlers.put(Mode.POINT_DELETION, new DeletePointHandler(scene2d));
        modeHandlers.put(Mode.POINT_CREATION, new AddPointHandler(scene2d));
        modeHandlers.put(Mode.RECTAGLE_CREATION, new RectangleCreationHandler(scene2d));
    }

    void initListeners() {
        panel.addKeyListener(keyHandler);

        // musi byt pod ostatnimi listenery
        panel.addMouseListener(renderRequester);
        panel.addKeyListener(renderRequester);
        panel.addMouseMotionListener(renderRequester);
    }

    public void update(MouseEvent e) {
        for (Mode mode : modeHandlers.keySet()) {
            if(keyHandler.getActiveMode() == mode) {
                ModeHandler modeHandler = modeHandlers.get(mode);
                if(e.getID() == MouseEvent.MOUSE_PRESSED) {
                    modeHandler.onMousePressed(e);
                } else if(e.getID() == MouseEvent.MOUSE_RELEASED) {
                    modeHandler.onMouseReleased(e);
                } else if(e.getID() == MouseEvent.MOUSE_MOVED) {
                    modeHandler.onMouseMoved(e);
                } else if(e.getID() == MouseEvent.MOUSE_DRAGGED) {
                    modeHandler.onMouseDragged(e);
                }
                break;
            }
        }

        updateActive();

        renderer2d.render(keyHandler.getActiveMode(), w, h);
        panel.repaint();
    }

    public void update(KeyEvent e) {
        if(Action.CLEAR.isOn()) { 
            for (Mode mode : modeHandlers.keySet()) {
                ModeHandler modeHandler = modeHandlers.get(mode);
                modeHandler.clear();
                clear();
                Action.CLEAR.setOff();
            }
        }

        updateActive();

        renderer2d.render(keyHandler.getActiveMode(), w, h);
        panel.repaint();
    }

    private void updateActive() {
        if(keyHandler.isModChanged()) {
            ((LineCreationHandler)modeHandlers.get(Mode.LINE_CREATION)).setActiveLineNull();
            ((PolygonCreationHandler)modeHandlers.get(Mode.POLYGON_CREATION)).setActivePolygonNull();
            ((RectangleCreationHandler)modeHandlers.get(Mode.RECTAGLE_CREATION)).setActiveRectangleNull();
            keyHandler.setModChanged(false);
        }
    }

    public void clear() {
        panel.getRaster().clear(Color.BLACK);
        scene2d.clear();
    }

}
