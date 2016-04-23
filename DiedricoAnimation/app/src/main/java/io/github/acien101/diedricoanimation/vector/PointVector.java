package io.github.acien101.diedricoanimation.vector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 13/03/16.
 */
public class PointVector implements Parcelable{

    private double pointY;
    private double pointX;
    private double pointZ;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeDoubleArray(new double[]{this.pointY,
                                          this.pointX,
                                          this.pointZ});
    }

    public static final Creator<PointVector> CREATOR
            = new Creator<PointVector>() {
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
        this.pointY = data[0];
        this.pointX = data[1];
        this.pointZ = data[2];
    }

    public PointVector(double pointX, double pointY, double pointZ) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.pointZ = pointZ;
    }

    public double getPointX() {

        return pointX;
    }

    public void setPointX(double pointX) {
        this.pointX = pointX;
    }

    public double getPointY() {
        return pointY;
    }

    public void setPointY(double pointY) {
        this.pointY = pointY;
    }

    public double getPointZ() {
        return pointZ;
    }

    public void setPointZ(double pointZ) {
        this.pointZ = pointZ;
    }
}
