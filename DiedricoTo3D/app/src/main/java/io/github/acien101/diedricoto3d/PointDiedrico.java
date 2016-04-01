package io.github.acien101.diedricoto3d;

/**
 * Created by amil101 on 21/02/16.
 */
public class PointDiedrico {
    Point cota;
    Point alejamiento;

    public PointDiedrico(Point cota, Point alejamiento){
        this.cota = cota;
        this.alejamiento = alejamiento;
    }

    public Point getAlejamiento() {
        return alejamiento;
    }

    public void setAlejamiento(Point alejamiento) {
        this.alejamiento = alejamiento;
    }

    public Point getCota() {
        return cota;
    }

    public void setCota(Point cota) {
        this.cota = cota;
    }
}
