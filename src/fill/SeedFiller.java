package fill;

import java.util.Stack;

import model.Point;
import rasterize.RasterBufferedImage;

public class SeedFiller implements Filler {

    private RasterBufferedImage raster;
    private int x;
    private int y;
    private int color;
    private int backgroundColor;

    private Stack<Point> stack;

    public SeedFiller(RasterBufferedImage raster, int color) {
        this.raster = raster;
        this.color = color;
        this.backgroundColor = -1;
        this.stack = new Stack<>();
    }


    @Override
    public void fill() {
        if(!raster.getPixel(x, y).isEmpty()) {
            backgroundColor = raster.getPixel(x, y).get();
        }
        
        if(color != backgroundColor) {
            System.out.println(x + " " + y);
            seedFill(x, y);
            //seedFillRecursive(x, y);
        }
    }

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

    public void setStart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
}
