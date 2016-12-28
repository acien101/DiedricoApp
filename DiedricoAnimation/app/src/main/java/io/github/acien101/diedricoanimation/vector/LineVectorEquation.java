package io.github.acien101.diedricoanimation.vector;

/**
 * Created by amil101 on 25/04/16.
 */
public class LineVectorEquation {
    PointVector point;
    SpatialVector vector;

    private float lambda;

    public LineVectorEquation(PointVector point, SpatialVector vector) {
        this.point = point;
        this.vector = vector;
    }

    public float getX(float lambda) {
        return (point.getPointX() + (vector.getX() * lambda));
    }

    public float getY(float lambda) {
        return (point.getPointY() + (vector.getY() * lambda));
    }

    public float getZ(float lambda) {
        return (point.getPointZ() + (vector.getZ() * lambda));
    }
}
