package io.github.acien101.diedricoanimation;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import io.github.acien101.diedricoanimation.vector.LineVectorEquation;
import io.github.acien101.diedricoanimation.vector.PointVector;
import io.github.acien101.diedricoanimation.vector.SpatialVector;

/**
 * Created by amil101 on 23/04/16.
 */
public class DiscontinuousLine {
    List<PointVector> points = new ArrayList<>();

    List<Line> lines = new ArrayList<>();

    int cuts;

    public DiscontinuousLine(PointVector pointA, PointVector pointB, float[] color, int cuts){
        this.cuts = cuts;

        double lambda;
        LineVectorEquation lineVector = new LineVectorEquation(pointA, new SpatialVector(pointA, pointB));
        for(int i = 0; i < cuts; i++){
            lambda = ((double)i/(int)cuts);

            points.add(new PointVector(lineVector.getX(lambda), lineVector.getY(lambda), lineVector.getZ(lambda)));
        }

        for(int i = 0; i < (cuts/2); i++){
            //lines.add(new Line(points.get(i * 2), points.get((i * 2) + 1), color));
            lines.add(new Line((float) points.get(i * 2).getPointX(), (float) points.get(i * 2).getPointY(), (float) points.get(i * 2).getPointZ(), (float) points.get((i *2) +1).getPointX(), (float) points.get((i * 2) + 1).getPointY(), (float) points.get((i * 2) +1).getPointZ(), color));
        }
    }

    public void draw(float[] mMVPMatrix) {
       for(int i = 0; i < cuts/2; i++){
                   lines.get(i).draw(mMVPMatrix);
       }
    }


}
