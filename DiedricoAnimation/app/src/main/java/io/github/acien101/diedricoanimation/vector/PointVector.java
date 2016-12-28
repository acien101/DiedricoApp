package io.github.acien101.diedricoanimation.vector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 13/03/16.
 */
public class PointVector implements Parcelable{

    private float pointY;
    private float pointX;
    private float pointZ;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(new float[]{this.pointY,
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
        float[] data = new float[3];

        in.readFloatArray(data);
        this.pointY = data[0];
        this.pointX = data[1];
        this.pointZ = data[2];
    }

    public PointVector(float pointX, float pointY, float pointZ) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.pointZ = pointZ;
    }

    public float getPointX() {

        return pointX;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    public float getPointZ() {
        return pointZ;
    }

    public void setPointZ(float pointZ) {
        this.pointZ = pointZ;
    }
}
