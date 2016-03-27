package io.github.acien101.diedricoto3d;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 27/03/16.
 */
public class PlanoVector implements Parcelable{
    float desplazamientoPlanoOrigen;
    float cota;
    float alejamiento;
    float desplazamiento;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(new float[]{this.desplazamientoPlanoOrigen,
                                        this.cota,
                                        this.alejamiento,
                                        this.desplazamiento});
    }

    public static final Parcelable.Creator<PlanoVector> CREATOR = new Creator<PlanoVector>() {
        @Override
        public PlanoVector createFromParcel(Parcel in) {
            return new PlanoVector(in);
        }

        @Override
        public PlanoVector[] newArray(int size) {
            return new PlanoVector[size];
        }
    };

    public PlanoVector(Parcel in){
        float[] data = new float[4];

        in.readFloatArray(data);
        this.desplazamientoPlanoOrigen = data[0];
        this.cota = data[1];
        this.alejamiento = data[2];
        this.desplazamiento = data[3];

    }

    public PlanoVector(float desplazamientoPlanoOrigen, float cota, float alejamiento, float desplazamiento){

        this.desplazamientoPlanoOrigen = desplazamientoPlanoOrigen;
        this.cota = cota;
        this.alejamiento = alejamiento;
        this.desplazamiento = desplazamiento;
    }

    public float getAlejamiento() {
        return alejamiento;
    }

    public void setAlejamiento(float alejamiento) {
        this.alejamiento = alejamiento;
    }

    public float getCota() {
        return cota;
    }

    public void setCota(float cota) {
        this.cota = cota;
    }

    public float getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(float desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public float getDesplazamientoPlanoOrigen() {
        return desplazamientoPlanoOrigen;
    }

    public void setDesplazamientoPlanoOrigen(float desplazamientoPlanoOrigen) {
        this.desplazamientoPlanoOrigen = desplazamientoPlanoOrigen;
    }
}


