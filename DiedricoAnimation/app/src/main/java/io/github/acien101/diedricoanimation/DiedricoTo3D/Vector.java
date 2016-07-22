package io.github.acien101.diedricoanimation.DiedricoTo3D;

/**
 * Created by amil101 on 5/02/16.
 */
public class Vector {

    private double x;           //the x of the vector
    private double y;           //the y of the vector

    Point firstPoint;
    Point secondPoint;

    public Vector(Point firstPoint, Point secondPoint) {
        this.x = (secondPoint.getX()) - (firstPoint.getX());
        this.y = (secondPoint.getY()) - (firstPoint.getY());

        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }


    public double getModule(){
        double module = Math.sqrt(Math.pow(x,2)+ Math.pow(y,2));

        return module;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
