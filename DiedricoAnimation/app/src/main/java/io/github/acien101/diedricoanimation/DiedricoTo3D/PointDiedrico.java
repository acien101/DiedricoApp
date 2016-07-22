package io.github.acien101.diedricoanimation.DiedricoTo3D;

/**
 * Created by amil101 on 21/02/16.
 */
public class PointDiedrico {
    Point pointY;
    Point pointX;

    public PointDiedrico(Point pointY, Point pointX){
        this.pointY = pointY;
        this.pointX = pointX;
    }

    public Point getPointX() {
        return pointX;
    }

    public void setPointX(Point pointX) {
        this.pointX = pointX;
    }

    public Point getPointY() {
        return pointY;
    }

    public void setPointY(Point pointY) {
        this.pointY = pointY;
    }
}
