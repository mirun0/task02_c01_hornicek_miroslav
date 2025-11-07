package utils;

import controller.handler.modeHandler.Action;
import model.Line;
import model.Point;
import model.Polygon;
import model.Rectangle;
import world.Scene2D;

public class GeometryUtils {
    private static final int findPointPrecision = 10;
    private static final float createNewPointPrecision = 30;
    private static final double snapTolerance = Math.toRadians(15);
    private static final double[] snapAngles = 
    {0, Math.PI / 4, Math.PI / 2, 3 * Math.PI / 4, - Math.PI / 2, - Math.PI / 4, Math.PI, - 3 * Math.PI / 4};

    private static Polygon closestPolygon = null;

    public static Point findPoint(Scene2D scene, int x, int y) {
        Point closestPoint = null;
        float closestLength = findPointPrecision;
        float l;

        for (Line line : scene.getLines()) {
            l = MathUtils.length(x, y, line.getPointA().getX(), line.getPointA().getY());
            if (l <= findPointPrecision) {
                if(closestLength > l) {
                    closestLength = l;
                    closestPoint = line.getPointA();
                }
            }

            l = MathUtils.length(x, y, line.getPointB().getX(), line.getPointB().getY());
            if (l <= findPointPrecision) {
                if(closestLength > l) {
                    closestLength = l;
                    closestPoint = line.getPointB();
                }
            }
        }

        for (Polygon polygon : scene.getPolygons()) {
            for (Point point : polygon.getPoints()) {
                l = MathUtils.length(x, y, point.getX(), point.getY());
                if (l <= findPointPrecision) {
                    if(closestLength > l) {
                        closestLength = l;
                        closestPoint = point;
                        closestPolygon = polygon;
                    }
                }
            }
        }

        return closestPoint;
    }

    public static void deletePoint(Scene2D scene, int x, int y) {
        Point p = findPoint(scene, x, y);

        if (closestPolygon == null) {
            return;
        }

        if(closestPolygon.size() < 4) {
            scene.getPolygons().remove(closestPolygon);
            closestPolygon = null;
        } else {
            closestPolygon.getPoints().remove(p);
        }
    }

    public static void createNewPoint(Scene2D scene, int mouseX, int mouseY) {
        float closestLength = createNewPointPrecision;
        int closestPointIndex = -1;
        int closestPolygonIndex = -1;

        int x = 0;
        int y = 0;

        for (int i = 0; i < scene.getPolygons().size(); i++) {

            Polygon polygon = scene.getPolygons().get(i);
            polygon.addPoint(polygon.getPoints().get(0));
            for (int j = 0; j < polygon.getPoints().size() - 1; j++) {
                Point pa = polygon.getPoints().get(j);
                Point pb = polygon.getPoints().get(j + 1);

                float a = pb.getY() - pa.getY();
                float b = -(pb.getX() - pa.getX());
                float c = -b * pa.getY() - (float)a * pa.getX();
                float l = Math.abs(a * mouseX + b * mouseY + c) / (float)Math.sqrt(a*a + b*b);

                float dx = pb.getX() - pa.getX();
                float dy = pb.getY() - pa.getY();
                float len = dx*dx + dy*dy;
                float t = ((mouseX - pa.getX()) * dx + (mouseY - pa.getY()) * dy) / len;

                if(t >= 0 && t <= 1) {
                    if(l <= createNewPointPrecision) {
                        if(closestLength > l) {
                            closestLength = l;
                            closestPolygonIndex = i;
                            closestPointIndex = j + 1;
                            x = (int)(pa.getX() + t * (pb.getX() - pa.getX()));
                            y = (int)(pa.getY() + t * (pb.getY() - pa.getY()));
                        }
                    }
                }
            }
            polygon.getPoints().remove(polygon.size() - 1);
        }

        if(closestPolygonIndex != -1) {
            int c = 0xffffff;
            if(Action.GRADIENT.isOn()) {
                c = RandomColor.create();
            }
            scene.getPolygons().get(closestPolygonIndex).getPoints().add(closestPointIndex, new Point(x, y, c));
        }
    }

    public static Point snap(int startX, int startY, int nowX, int nowY) {
        double dx = nowX - startX;
        double dy = nowY - startY;
        double angle = Math.atan2(dy, dx);

        for (double a : snapAngles) {
            if (Math.abs(angle - a) < snapTolerance) {
                angle = a;
                break;
            }
        }

        double length = Math.hypot(dx, dy);
        int snappedX = (int) Math.round(startX + length * Math.cos(angle));
        int snappedY = (int) Math.round(startY + length * Math.sin(angle));

        return new Point(snappedX, snappedY, 1);
    }

    public static Polygon getClosestPolygon() {
        return closestPolygon;
    }

    public static void moveToHeight(Rectangle activeRectangle, int x, int y) {

        Point A = activeRectangle.getPointA();
        Point B = activeRectangle.getPointB();

        double dx = B.getX() - A.getX();
        double dy = B.getY() - A.getY();
        double len = Math.sqrt(dx*dx + dy*dy);
        double ux = dx / len;
        double uy = dy / len;

        double nx = -uy;
        double ny = ux;

        double mx = x - A.getX();
        double my = y - A.getY();
        double height = mx * nx + my * ny;

        activeRectangle.getPointC().setX((int)(B.getX() + nx * height));
        activeRectangle.getPointC().setY((int)(B.getY() + ny * height));

        activeRectangle.getPointD().setX((int)(A.getX() + nx * height));
        activeRectangle.getPointD().setY((int)(A.getY() + ny * height));

    }
}
