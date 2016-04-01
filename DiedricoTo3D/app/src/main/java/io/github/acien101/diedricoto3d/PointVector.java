package io.github.acien101.diedricoto3d;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 13/03/16.
 */
public class PointVector implements Parcelable{

    private double cota;
    private double alejamiento;
    private double distancia;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeDoubleArray(new double[]{this.cota,
                this.alejamiento,
                this.distancia});
    }

    public static final Parcelable.Creator<PointVector> CREATOR
            = new Parcelable.Creator<PointVector>() {
        public PointVector createFromParcel(Parcel in) {
            return new PointVector(in);
        }

        public PointVector[] newArray(int size) {
            return new PointVector[size];
        }
    };

    public PointVector(Parcel in) {
        double[] data = new double[3];

        in.readDoubleArray(data);
        this.cota = data[0];
        this.alejamiento = data[1];
        this.distancia = data[2];
    }

    public PointVector(double alejamiento, double cota, double distancia) {
        this.alejamiento = alejamiento;
        this.cota = cota;
        this.distancia = distancia;
    }

    public double getAlejamiento() {

        return alejamiento;
    }

    public void setAlejamiento(double alejamiento) {
        this.alejamiento = alejamiento;
    }

    public double getCota() {
        return cota;
    }

    public void setCota(double cota) {
        this.cota = cota;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
