package io.github.acien101.diedricoanimation.DiedricoTo3D;

/**
 * Created by amil101 on 16/02/16.
 */
public class Line {

    float xa;
    float ya;

    float xb;
    float yb;

    public Line(float xa, float ya, float xb, float yb){
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
    }

    public float getXa() {
        return xa;
    }

    public void setXa(float xa) {
        this.xa = xa;
    }

    public float getXb() {
        return xb;
    }

    public void setXb(float xb) {
        this.xb = xb;
    }

    public float getYa() {
        return ya;
    }

    public void setYa(float ya) {
        this.ya = ya;
    }

    public float getYb() {
        return yb;
    }

    public void setYb(float yb) {
        this.yb = yb;
    }
}
