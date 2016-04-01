package io.github.acien101.diedricoto3d;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 23/03/16.
 */
public class LineVector implements Parcelable{
    float cotaA;
    float alejamientoA;
    float distanciaA;

    float cotaB;
    float alejamientoB;
    float distanciaB;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(new float[]{this.cotaA,
                this.alejamientoA,
                this.distanciaA,
                this.cotaB,
                this.alejamientoB,
                this.distanciaB});
    }

    public static final Parcelable.Creator<LineVector> CREATOR = new Creator<LineVector>() {
        @Override
        public LineVector createFromParcel(Parcel in) {
            return new LineVector(in);
        }

        @Override
        public LineVector[] newArray(int size) {
            return new LineVector[size];
        }
    };

    public LineVector(Parcel in){
        float[] data = new float[6];

        in.readFloatArray(data);
        this.cotaA = data[0];
        this.alejamientoA = data[1];
        this.distanciaA = data[2];

        this.cotaB = data[3];
        this.alejamientoB = data[4];
        this.distanciaB = data[5];

    }

    public LineVector(float cotaA, float alejamientoA, float distanciaA, float cotaB, float alejamientoB, float distanciaB){

        this.cotaA = cotaA;
        this.alejamientoA = alejamientoA;
        this.distanciaA = distanciaA;

        this.cotaB = cotaB;
        this.alejamientoB = alejamientoB;
        this.distanciaB = distanciaB;
    }

    public float getDistanciaB() {
        return distanciaB;
    }

    public void setDistanciaB(float distanciaB) {
        this.distanciaB = distanciaB;
    }

    public float getDistanciaA() {
        return distanciaA;
    }

    public void setDistanciaA(float distanciaA) {
        this.distanciaA = distanciaA;
    }

    public float getCotaB() {
        return cotaB;
    }

    public void setCotaB(float cotaB) {
        this.cotaB = cotaB;
    }

    public float getCotaA() {
        return cotaA;
    }

    public void setCotaA(float cotaA) {
        this.cotaA = cotaA;
    }

    public float getAlejamientoB() {
        return alejamientoB;
    }

    public void setAlejamientoB(float alejamientoB) {
        this.alejamientoB = alejamientoB;
    }

    public float getAlejamientoA() {
        return alejamientoA;
    }

    public void setAlejamientoA(float alejamientoA) {
        this.alejamientoA = alejamientoA;
    }
}
