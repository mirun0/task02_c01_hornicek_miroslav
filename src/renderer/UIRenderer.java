package renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import controller.handler.modeHandler.Action;
import controller.handler.modeHandler.Mode;

public class UIRenderer {

    private final Graphics g;

    public UIRenderer(Graphics g) {
        this.g = g;
    }

    public void renderUI(Mode mode, int w, int h) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, h - 40, w, 40);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g.drawString("Point Select - Esc", 5, h - 25);
        g.drawString("Line - V", 170, h - 25);
        g.drawString("Polygon - B", 257, h - 25);
        g.drawString("Gradient - G", 370, h - 25);
        g.drawString("Clear - C", 490, h - 25);
        g.drawString("Snap - Hold Shift", 590, h - 25);
        g.drawString("New Point - N", 5, h - 5);
        g.drawString("Delete Point - D", 140, h - 5);
        g.setColor(Color.GRAY);
        g.drawString("(Confirm polygon creation with RMB)", 450, h - 5);

        g.setFont(new Font("Monospaced", Font.BOLD, 13));
        g.setColor(Color.GREEN);
        if (mode == Mode.LINE_CREATION) g.drawString("Line - V", 170, h - 25);
        else if (mode == Mode.POLYGON_CREATION) g.drawString("Polygon - B", 257, h - 25);
        else if (mode == Mode.POINT_CREATION) g.drawString("New Point - N", 5, h - 5);
        else if (mode == Mode.POINT_DELETION) g.drawString("Delete Point - D", 140, h - 5);
        else if (mode == Mode.POINT_MOVING) g.drawString("Point Select - Esc", 5, h - 25);

        g.setColor(Color.YELLOW);
        if (Action.GRADIENT.isOn()) g.drawString("Gradient - G", 370, h - 25);
        if (Action.SNAP.isOn()) g.drawString("Snap - Hold Shift", 590, h - 25);
    }
}
