package renderer.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import controller.handler.modeHandler.Action;
import controller.handler.modeHandler.Mode;

public class UIRenderer {

    private record ToolOption(Toolable toolable, String text, int width) {}

    private final Graphics g;
    private final Font font;
    private final Font boldFont;
    private final FontMetrics fm;

    private final ToolOption[] toolOptions;

    public UIRenderer(Graphics g) {
        this.g = g;

        font = new Font("Monospaced", Font.PLAIN, 13);
        boldFont = new Font("Monospaced", Font.BOLD, 13);

        fm = g.getFontMetrics(font);

        String[] texts = new String[] {
            "Point Select - Esc", "Line - V", "Polygon - B", "Gradient - G", "Clear - C", "Snap - Hold Shift",
            "New Point - N", "Delete Point - D", "Rectangle - R", "Fill - F", "Clipping scene - Q"
        };

        Toolable[] toolables = new Toolable[] {
            Mode.POINT_MOVING, Mode.LINE_CREATION, Mode.POLYGON_CREATION, Action.GRADIENT, Action.CLEAR, Action.SNAP,
            Mode.POINT_CREATION, Mode.POINT_DELETION, Mode.RECTAGLE_CREATION, Action.FILLING, Mode.POLYGON_CLIPPING
        };

        toolOptions = new ToolOption[texts.length];
        for (int i = 0; i < toolOptions.length; i++) {
            toolOptions[i] = new ToolOption(toolables[i], texts[i], fm.stringWidth(texts[i]));
        }
    }

    public void renderUI(Mode activeMode, int w, int h) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, h - 40, w, 40);

        int startX = 5;
        int startY = h - 25;
        int x = startX;
        int y = startY;
        int lineHeight = fm.getHeight() + 1;
        int spacing = 20;

        for (ToolOption opt : toolOptions) {
            if (x + opt.width > w - 10) {
                x = startX;
                y += lineHeight;
            }

            g.setFont(font);
            g.setColor(Color.WHITE);
            if(opt.toolable instanceof Mode mode) {
                if(mode == activeMode) {
                    g.setFont(boldFont);
                    g.setColor(Color.GREEN);
                }
            } else if(opt.toolable instanceof Action action) {
                if(action.isOn()) {
                    g.setFont(boldFont);
                    g.setColor(Color.YELLOW);
                }
            }

            g.drawString(opt.text, x, y);

            x += opt.width + spacing;
        }
    }
}
