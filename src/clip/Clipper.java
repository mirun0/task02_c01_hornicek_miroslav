package clip;

import java.util.ArrayList;
import java.util.Optional;

import model.Line;
import model.Point;
import model.Polygon;
import utils.Vec2;

public class Clipper {

    public Clipper() {

    }

    public Polygon clip(Polygon clippingPolygon, Polygon subjectPolygon) {
        Polygon outputPolygon = subjectPolygon;

        for (int i = 0; i < clippingPolygon.getPoints().size(); i++) {
            Polygon inputPolygon = outputPolygon;
            outputPolygon = new Polygon(new ArrayList<>(), false);

            Point A = clippingPolygon.getPoints().get(i);
            Point B = clippingPolygon.getPoints().get((i + 1) % clippingPolygon.getPoints().size());
            Line clipEdge = new Line(A, B);

            if (inputPolygon.getPoints().isEmpty()) {
                break;
            }

            Point p1 = inputPolygon.getPoints().get(inputPolygon.getPoints().size() - 1);

            for (Point p2 : inputPolygon.getPoints()) {
                boolean p1Inside = isInsideEdge(p1, clipEdge);
                if (isInsideEdge(p2, clipEdge)) {
                    System.out.println("P2 inside clip edge");
                    if (!p1Inside) {
                        Point intersect = intersection(p1, p2, clipEdge);
                        if (intersect != null) {
                            System.out.println("P1 not inside clip edge, calculating intersection P1 -> P2");
                            outputPolygon.getPoints().add(intersect);
                        }
                    }
                    outputPolygon.getPoints().add(p2);
                } else if (p1Inside) {
                    Point intersect = intersection(p1, p2, clipEdge);
                    if (intersect != null) {
                        System.out.println("P1 inside clip edge, calculating intersection P1 -> P2");
                        outputPolygon.getPoints().add(intersect);
                    }
                }
                p1 = p2;
            }

            if (outputPolygon.getPoints().isEmpty()) {
                break;
            }
        }

        return outputPolygon;
    }

    private boolean isInsideEdge(Point p, Line line) {
        Vec2 t = new Vec2(line.getPointB().getX() - line.getPointA().getX(), line.getPointB().getY() - line.getPointA().getY());
        Vec2 ap = new Vec2(p.getX() - line.getPointA().getX(), p.getY() - line.getPointA().getY());
        return ap.cross(t) <= 0;
    }

    private Point intersection(Point p1, Point p2, Line line) {
        Vec2 d1 = new Vec2(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        Vec2 d2 = new Vec2(line.getPointB().getX() - line.getPointA().getX(), line.getPointB().getY() - line.getPointA().getY());
        Vec2 ap1 = new Vec2(line.getPointA().getX() - p1.getX(), line.getPointA().getY() - p1.getY());

        float cross = d1.cross(d2); // tady pokud budou skoro rovnobezny, tak cross bude blizky 0, takze to chce podminku
        float t = ap1.cross(d2) / cross;

        double ix = p1.getX() + t * d1.getX();
        double iy = p1.getY() + t * d1.getY();

        return new Point(ix, iy, 0xff0000);
    }
}
