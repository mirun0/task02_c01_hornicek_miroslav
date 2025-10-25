package fill;

import java.util.ArrayList;
import java.util.Comparator;

import model.Polygon;
import model.Point;
import rasterize.RasterBufferedImage;

public class ScanlineFiller implements Filler {

    private class EdgeTableEntry {
        final float yMin;
        final float yMax;
        float currentX;
        final float inverseSlope;

        public EdgeTableEntry(Point a, Point b) {
            Point pMin = a.getY() <= b.getY() ? a : b;
            Point pMax = a.getY() <= b.getY() ? b : a;

            float dy = b.getY() - a.getY();
            float dx = b.getX() - a.getX();

            if(dy == 0) {
                this.yMin = this.yMax = this.currentX = this.inverseSlope = 0;
                return;
            }

            this.yMin = pMin.getY();
            this.yMax = pMax.getY();
            this.currentX = pMin.getX();
            this.inverseSlope = dx / dy;
        }

        boolean isValid() { 
            return yMin != yMax; 
        }

        @Override
        public String toString() {
            return "yMin=" + yMin + " yMax=" + yMax + " currentX=" + currentX + " inverseSlope=" + inverseSlope;

        }

    }

    private RasterBufferedImage raster;
    private int color;
    private Polygon polygon;

    public ScanlineFiller(RasterBufferedImage raster, int color) {
        this.raster = raster;
        this.color = color;
    }

    @Override
    public void fill() {
        ArrayList<EdgeTableEntry> et = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            Point a = polygon.getPoints().get(i);
            Point b = polygon.getPoints().get((i + 1) % polygon.size());
            EdgeTableEntry e = new EdgeTableEntry(a, b);
            if (e.isValid()) et.add(e);
        }

        et.sort(Comparator.comparingDouble(e -> e.yMin));
        
        /*
        System.out.println("---------- START ----------");
        System.out.println("Initial Edge Table: ");
        for (EdgeTableEntry edgeTableEntry : et) {
            System.out.println(edgeTableEntry.toString());
        }
        */

        ArrayList<EdgeTableEntry> aet = new ArrayList<>();
        float yPointer = et.get(0).yMin;

        while(!et.isEmpty() || !aet.isEmpty()) {

            for (int i = 0; i < et.size(); ) {
                EdgeTableEntry e = et.get(i);
                if (Math.round(e.yMin) == Math.round(yPointer)) {
                    aet.add(e);
                    et.remove(i);
                } else {
                    i++;
                }
            }

            for (int i = 0; i < aet.size(); ) {
                if (yPointer >= aet.get(i).yMax) {
                    aet.remove(i);
                } else {
                    i++;
                }
            }

            if (aet.isEmpty() && et.isEmpty()) break;

            aet.sort(Comparator.comparingDouble(e -> e.currentX));

            for (int i = 0; i + 1 < aet.size(); i += 2) {
                int xStart = Math.round(aet.get(i).currentX);
                int xEnd = Math.round(aet.get(i + 1).currentX);

                if (xStart > xEnd) { 
                    int t = xStart;
                    xStart = xEnd;
                    xEnd = t;
                }

                for (int x = xStart; x <= xEnd; x++) {
                    raster.setPixel(x, (int) yPointer, 0x00ff00);
                }
            }

            yPointer++;

            for (EdgeTableEntry e : aet) {
                e.currentX += e.inverseSlope;
            }

        }

        //System.exit(0);
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void setColor(int color) {
        this.color = color;
    }
}