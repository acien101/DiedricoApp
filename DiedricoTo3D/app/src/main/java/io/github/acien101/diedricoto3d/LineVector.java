package io.github.acien101.diedricoto3d;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amil101 on 23/03/16.
 */
public class LineVector implements Parcelable{
    float LineYA;
    float LineXA;
    float LineZA;

    float LineYB;
    float LineXB;
    float LineZB;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloatArray(new float[]{this.LineYA,
                this.LineXA,
                this.LineZA,
                this.LineYB,
                this.LineXB,
                this.LineZB});
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
        this.LineYA = data[0];
        this.LineXA = data[1];
        this.LineZA = data[2];

        this.LineYB = data[3];
        this.LineXB = data[4];
        this.LineZB = data[5];

    }

    public LineVector(float LineYA, float LineXA, float LineZA, float LineYB, float LineXB, float LineZB){

        this.LineYA = LineYA;
        this.LineXA = LineXA;
        this.LineZA = LineZA;

        this.LineYB = LineYB;
        this.LineXB = LineXB;
        this.LineZB = LineZB;
    }

    public float getLineZB() {
        return LineZB;
    }

    public void setLineZB(float lineZB) {
        this.LineZB = lineZB;
    }

    public float getLineZA() {
        return LineZA;
    }

    public void setLineZA(float lineZA) {
        this.LineZA = lineZA;
    }

    public float getLineYB() {
        return LineYB;
    }

    public void setLineYB(float lineYB) {
        this.LineYB = lineYB;
    }

    public float getLineYA() {
        return LineYA;
    }

    public void setLineYA(float lineYA) {
        this.LineYA = lineYA;
    }

    public float getLineXB() {
        return LineXB;
    }

    public void setLineXB(float lineXB) {
        this.LineXB = lineXB;
    }

    public float getLineXA() {
        return LineXA;
    }

    public void setLineXA(float lineXA) {
        this.LineXA = lineXA;
    }
}
