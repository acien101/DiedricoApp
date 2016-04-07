package io.github.acien101.diedricoto3d;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 27/03/16.
 */
public class PlaneVector implements Parcelable{
    float planeOriginZ;
    float planeY;
    float planeX;
    float planeZ;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(new float[]{this.planeOriginZ,
                                        this.planeY,
                                        this.planeX,
                                        this.planeZ});
    }

    public static final Parcelable.Creator<PlaneVector> CREATOR = new Creator<PlaneVector>() {
        @Override
        public PlaneVector createFromParcel(Parcel in) {
            return new PlaneVector(in);
        }

        @Override
        public PlaneVector[] newArray(int size) {
            return new PlaneVector[size];
        }
    };

    public PlaneVector(Parcel in){
        float[] data = new float[4];

        in.readFloatArray(data);
        this.planeOriginZ = data[0];
        this.planeY = data[1];
        this.planeX = data[2];
        this.planeZ = data[3];

    }

    public PlaneVector(float planeOriginZ, float planeY, float planeX, float planeZ){

        this.planeOriginZ = planeOriginZ;
        this.planeY = planeY;
        this.planeX = planeX;
        this.planeZ = planeZ;
    }

    public float getPlaneX() {
        return planeX;
    }

    public void setPlaneX(float planeX) {
        this.planeX = planeX;
    }

    public float getPlaneY() {
        return planeY;
    }

    public void setPlaneY(float planeY) {
        this.planeY = planeY;
    }

    public float getPlaneZ() {
        return planeZ;
    }

    public void setPlaneZ(float planeZ) {
        this.planeZ = planeZ;
    }

    public float getPlaneOriginZ() {
        return planeOriginZ;
    }

    public void setPlaneOriginZ(float planeOriginZ) {
        this.planeOriginZ = planeOriginZ;
    }
}


