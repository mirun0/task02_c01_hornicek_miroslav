package fill;

import java.awt.Color;

import rasterize.RasterBufferedImage;

public class SeedFiller implements Filler {

    private RasterBufferedImage raster;
    private int x;
    private int y;
    private int color;
    private int backgroundColor;

    public SeedFiller(RasterBufferedImage raster, int color) {
        this.raster = raster;
        this.color = color;
        this.backgroundColor = -1;
    }


    @Override
    public void fill() {
        if(!raster.getPixel(x, y).isEmpty()) {
            backgroundColor = raster.getPixel(x, y).get();
        }
        System.out.println(backgroundColor);
        seedFill(x, y);
    }

    private void seedFill(int x, int y) {
        
        if(raster.getPixel(x, y).isEmpty() || raster.getPixel(x, y).get() != backgroundColor) {
            return;
        }

        System.out.println("x: " + x + " y: " + y);
        raster.setPixel(x, y, 0xffffff);

        seedFill(x + 1, y);
        seedFill(x - 1, y);
        seedFill(x, y + 1);
        seedFill(x, y - 1);

    }

    public void setStart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
}
