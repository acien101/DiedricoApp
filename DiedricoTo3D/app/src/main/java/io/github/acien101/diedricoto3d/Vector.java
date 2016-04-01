package io.github.acien101.diedricoto3d;

/**
 * Created by amil101 on 5/02/16.
 */
public class Vector {

    private double x;
    private double y;

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

    public Point getSecondPoint() {
        return secondPoint;
    }


    /*
    double hip;
    double angle;
    double height;
    double lenght;
    double landLine;
    Map<String, double[]> vector= new HashMap<>();

    public void getAngle(double[] u, double[] v){

        hip = Math.sqrt(Math.pow((v[0]-u[0]),2)+ Math.pow((v[1]-u[1]),2));
        angle = Math.acos(((u[0] * v[0]) + (u[1] * v[1])) / (Math.abs(Math.sqrt(Math.abs(Math.pow(u[0], 2)) + Math.abs(Math.pow(u[1], 2)))) * Math.abs(Math.sqrt(Math.abs(Math.pow(v[0], 2)) + Math.abs(Math.pow(v[1], 2))))));
        landLine = Math.sqrt(Math.pow(u[0], 2) + Math.pow(u[1], 2));
        height = Math.sin(angle)*hip;
        lenght = Math.cos(angle)*hip;



        Log.i("u1", Double.toString(u[0]));
        Log.i("u2", Double.toString(u[1]));
        Log.i("v1", Double.toString(v[0]));
        Log.i("v2", Double.toString(v[1]));

        Log.i("altura", Double.toString(height));
        Log.i("longitud", Double.toString(lenght));
        Log.i("landLine lenght", Double.toString(landLine));
        Log.i("agle", Double.toString(angle));
        Log.i("hip", Double.toString(hip));

    }

    public void createVector(double u1, double u2, double v1, double v2, String vectorName){
        double[] AB = new double[2];
        AB[0] = (v1 - u1);
        AB[1] = (v2 - u2);
        vector.put(vectorName, AB);
    }
    public double[] getVector(String vector){
        return this.vector.get(vector);
    }

    public double getHeight() {
        return height;
    }

    public double getLenght() {
        return lenght;
    }

    public double getLandLine() {
        return landLine;
    }

    */


}
