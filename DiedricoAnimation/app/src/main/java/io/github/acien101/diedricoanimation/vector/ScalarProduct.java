package io.github.acien101.diedricoanimation.vector;

/**
 * Created by amil101 on 28/03/16.
 */
public class ScalarProduct {
    Vector AB;
    Vector CD;
    public ScalarProduct(Vector AB, Vector CD){
        this.AB = AB;
        this.CD = CD;
    }

    public double angle() {
        double angle = Math.acos(product() / Math.abs(AB.getModule() * CD.getModule()));
        return angle;
    }

    public double product(){
        double product = (AB.getX() * CD.getX()) + (AB.getY() * CD.getY());
        return product;
    }

    public double getHeight(){
        double height = Math.sin(angle()) * CD.getModule();
        return height;
    }

    public double getLength(){
        double length= Math.cos(angle()) * CD.getModule();
        return length;
    }

}
