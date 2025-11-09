package fill.seed;

import java.util.Stack;

import fill.Fillable;
import fill.Filler;
import model.Point;
import rasterize.Raster;

public class SeedFiller implements Filler {

    private Raster raster;
    private int x;
    private int y;
    private int color;
    private int backgroundColor;

    private Stack<Point> stack;

    public SeedFiller(Raster raster, int color) {
        this.raster = raster;
        this.color = color;
        this.backgroundColor = -1;
        this.stack = new Stack<>();
    }


    @Override
    public void fill(Fillable fillable) {
        if(!raster.getPixel(x, y).isEmpty()) {
            backgroundColor = raster.getPixel(x, y).get();
        }
        
        if(color != backgroundColor) {
            seedFill(x, y);
            //seedFillRecursive(x, y);
        }
    }

    @SuppressWarnings("unused")
    private void seedFillRecursive(int x, int y) {
        
        if(raster.getPixel(x, y).isEmpty() || raster.getPixel(x, y).get() != backgroundColor) {
            return;
        }

        raster.setPixel(x, y, color);

        seedFillRecursive(x + 1, y);
        seedFillRecursive(x - 1, y);
        seedFillRecursive(x, y + 1);
        seedFillRecursive(x, y - 1);

    }

    // seedfill vyplnujici podle pozadi
    private void seedFill(int x, int y) {
        stack.clear();
        stack.add(new Point(x, y, 0x0));

        while (!stack.empty()) {
            Point n = stack.pop();
            if(!raster.getPixel(n.getX(), n.getY()).isEmpty() && raster.getPixel(n.getX(), n.getY()).get() == backgroundColor) {
                raster.setPixel(n.getX(), n.getY(), color);

                stack.add(new Point(n.getX() + 1, n.getY(), 0x0));
                stack.add(new Point(n.getX() - 1, n.getY(), 0x0));
                stack.add(new Point(n.getX(), n.getY() + 1, 0x0));
                stack.add(new Point(n.getX(), n.getY() - 1, 0x0));
            }
        }
    }

    // seedfill vyplnujici podle hranice
    @SuppressWarnings("unused")
    private void seedFill(int x, int y, int borderColor) {
        stack.clear();
        stack.add(new Point(x, y, 0x0));

        while (!stack.empty()) {
            Point n = stack.pop();
            if(!raster.getPixel(n.getX(), n.getY()).isEmpty()) {
                int currentColor = raster.getPixel(n.getX(), n.getY()).get();
                if (currentColor != color && currentColor != borderColor) {
                    raster.setPixel(n.getX(), n.getY(), color);

                    stack.add(new Point(n.getX() + 1, n.getY(), 0x0));
                    stack.add(new Point(n.getX() - 1, n.getY(), 0x0));
                    stack.add(new Point(n.getX(), n.getY() + 1, 0x0));
                    stack.add(new Point(n.getX(), n.getY() - 1, 0x0));
                }
            }
        }
    }

    public void setStart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
}
