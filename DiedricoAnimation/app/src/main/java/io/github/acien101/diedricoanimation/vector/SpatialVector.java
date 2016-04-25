package io.github.acien101.diedricoanimation.vector;

/**
 * Created by amil101 on 25/04/16.
 */
public class SpatialVector {
    private double x;
    private double y;
    private double z;

    PointVector firstPoint;
    PointVector secondPoint;


    public SpatialVector(PointVector firstPoint, PointVector secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;

        this.x = secondPoint.getPointX() - firstPoint.getPointX();
        this.y = secondPoint.getPointY() - firstPoint.getPointY();
        this.z = secondPoint.getPointZ() - firstPoint.getPointZ();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
