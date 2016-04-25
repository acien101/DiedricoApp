package io.github.acien101.diedricoanimation.vector;

/**
 * Created by amil101 on 25/04/16.
 */
public class LineVectorEquation {
    PointVector point;
    SpatialVector vector;

    private double lambda;

    public LineVectorEquation(PointVector point, SpatialVector vector) {
        this.point = point;
        this.vector = vector;
    }

    public double getX(double lambda) {
        return (point.getPointX() + (vector.getX() * lambda));
    }

    public double getY(double lambda) {
        return (point.getPointY() + (vector.getY() * lambda));
    }

    public double getZ(double lambda) {
        return (point.getPointZ() + (vector.getY() * lambda));
    }
}
