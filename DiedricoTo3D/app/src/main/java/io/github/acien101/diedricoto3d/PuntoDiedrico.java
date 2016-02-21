package io.github.acien101.diedricoto3d;

/**
 * Created by amil101 on 21/02/16.
 */
public class PuntoDiedrico {
    Punto cota;
    Punto alejamiento;

    public PuntoDiedrico(Punto cota, Punto alejamiento){
        this.cota = cota;
        this.alejamiento = alejamiento;
    }

    public Punto getAlejamiento() {
        return alejamiento;
    }

    public void setAlejamiento(Punto alejamiento) {
        this.alejamiento = alejamiento;
    }

    public Punto getCota() {
        return cota;
    }

    public void setCota(Punto cota) {
        this.cota = cota;
    }
}
