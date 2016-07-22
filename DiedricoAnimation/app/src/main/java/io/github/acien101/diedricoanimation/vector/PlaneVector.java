package io.github.acien101.diedricoanimation.vector;

/**
 * Created by amil101 on 22/07/16.
 */
public class PlaneVector {

    private PointVector p1;
    private PointVector p2;
    private PointVector p3;
    private PointVector p4;

    public PlaneVector(PointVector p1, PointVector p2, PointVector p3, PointVector p4){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }

    public PointVector getP1() {
        return p1;
    }

    public PointVector getP2() {
        return p2;
    }

    public PointVector getP3() {
        return p3;
    }

    public PointVector getP4() {
        return p4;
    }
}
