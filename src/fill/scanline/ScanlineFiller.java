package fill.scanline;

import java.util.ArrayList;
import java.util.Comparator;

import fill.Filler;
import model.Polygon;
import model.Point;
import rasterize.Raster;

public class ScanlineFiller implements Filler {

    private Raster raster;
    private int color;
    private Polygon polygon;

    public ScanlineFiller(Raster raster, int color) {
        this.raster = raster;
        this.color = color;
    }

    /*
     * https://how.dev/answers/what-is-scanline-fill-algorithm
     */
    /*
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

        if(et.isEmpty()) {
            return;
        }

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

            if (aet.isEmpty() && et.isEmpty()) {
                break;
            }

            aet.sort(Comparator.comparingDouble(e -> e.currentX));

            for (int i = 0; i + 1 < aet.size(); i += 2) {
                int xStart = Math.round(aet.get(i).currentX);
                int xEnd = Math.round(aet.get(i + 1).currentX);

                if (xStart > xEnd) { 
                    int t = xStart;
                    xStart = xEnd;
                    xEnd = t;
                }

                for (int x = xStart + 1; x < xEnd; x++) {
                    raster.setPixel(x, (int) yPointer, color);
                }
            }

            yPointer++;

            for (EdgeTableEntry e : aet) {
                e.currentX += e.inverseSlope;
            }

        }
    }*/

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

        if(et.isEmpty()) {
            return;
        }

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

            if (aet.isEmpty() && et.isEmpty()) {
                break;
            }

            aet.sort(Comparator.comparingDouble(e -> e.currentX));

            for (int i = 0; i + 1 < aet.size(); i += 2) {
                int xStart = Math.round(aet.get(i).currentX);
                int xEnd = Math.round(aet.get(i + 1).currentX);

                if (xStart > xEnd) {
                    int t = xStart;
                    xStart = xEnd;
                    xEnd = t;
                }

                for (int x = xStart + 1; x < xEnd; x++) {
                    int m = (x / Pattern.rocketPattern.getScale()) % Pattern.rocketPattern.getWidth();
                    int n = ((int)yPointer / Pattern.rocketPattern.getScale()) % Pattern.rocketPattern.getHeight();
                    int c = Pattern.rocketPattern.getPixel(m, n);

                    raster.setPixel(x, (int) yPointer, c);
                }
            }

            yPointer++;

            for (EdgeTableEntry e : aet) {
                e.currentX += e.inverseSlope;
            }

        }
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
}