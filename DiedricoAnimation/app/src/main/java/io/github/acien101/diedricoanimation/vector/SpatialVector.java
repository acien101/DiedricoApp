package io.github.acien101.diedricoanimation.vector;

/**
 * Created by amil101 on 25/04/16.
 */
public class SpatialVector {
    private float x;
    private float y;
    private float z;

    PointVector firstPoint;
    PointVector secondPoint;


    public SpatialVector(PointVector firstPoint, PointVector secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;

        this.x = secondPoint.getPointX() - firstPoint.getPointX();
        this.y = secondPoint.getPointY() - firstPoint.getPointY();
        this.z = secondPoint.getPointZ() - firstPoint.getPointZ();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
