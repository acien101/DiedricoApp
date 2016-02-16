package io.github.acien101.diedricoto3d;

/**
 * Created by amil101 on 16/02/16.
 */
public class Linea {

    double xa;
    double ya;

    double xb;
    double yb;

    public Linea(double xa, double ya, double xb, double yb){
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
    }

    public double getXa() {
        return xa;
    }

    public void setXa(double xa) {
        this.xa = xa;
    }

    public double getXb() {
        return xb;
    }

    public void setXb(double xb) {
        this.xb = xb;
    }

    public double getYa() {
        return ya;
    }

    public void setYa(double ya) {
        this.ya = ya;
    }

    public double getYb() {
        return yb;
    }

    public void setYb(double yb) {
        this.yb = yb;
    }
}
