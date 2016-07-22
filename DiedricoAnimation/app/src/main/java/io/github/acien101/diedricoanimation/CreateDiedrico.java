package io.github.acien101.diedricoanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import java.util.List;

import io.github.acien101.diedricoanimation.vector.LineVector;
import io.github.acien101.diedricoanimation.vector.PointVector;

/**
 * Created by amil101 on 19/06/16.
 */
public class CreateDiedrico {
    Paint paintMax;

    ImageView imageView;
    public CreateDiedrico(ImageView imageView){

        this.imageView = imageView;

        paintMax = new Paint();
        paintMax.setColor(Color.BLACK);
        paintMax.setStyle(Paint.Style.FILL);
    }

    public void addDiedricoPoints(List<PointVector> pointVectors){
        Bitmap bm = Bitmap.createBitmap(200, 300, Bitmap.Config.RGB_565);

        Canvas mCanvas = new Canvas(bm);
        mCanvas.drawColor(Color.WHITE);

        mCanvas.drawLine(20.0f, 150.0f, 180.0f, 150.0f, paintMax);

        for(int i = 0; i < pointVectors.size(); i++){
            mCanvas.drawCircle(((float) pointVectors.get(i).getPointZ()), (150.0f - (float) pointVectors.get(i).getPointY()), 1.5f, paintMax);
            mCanvas.drawCircle((float) pointVectors.get(i).getPointZ(), (150.0f + (float) pointVectors.get(i).getPointX()), 1.5f, paintMax);
        }

        this.imageView.setImageBitmap(bm);
    }

    public void addDiedricoLines(List<LineVector> lineVectors){
        Bitmap bm = Bitmap.createBitmap(200, 300, Bitmap.Config.RGB_565);

        Canvas mCanvas = new Canvas(bm);
        mCanvas.drawColor(Color.WHITE);

        mCanvas.drawLine(20.0f, 150.0f, 180.0f, 150.0f, paintMax);

        for(int i = 0; i < lineVectors.size(); i++){
            mCanvas.drawLine((100.0f + lineVectors.get(i).getLineZB() * 180.0f), (150.0f - (lineVectors.get(i).getLineYA()) * 150.0f), (100.0f + lineVectors.get(i).getLineZA() * 180.0f), (150.0f - lineVectors.get(i).getLineYB() * 150.f), paintMax);
            mCanvas.drawLine((100.0f + lineVectors.get(i).getLineZB() * 180.0f), (150.0f + (lineVectors.get(i).getLineXA()) * 150.0f), (100.0f + lineVectors.get(i).getLineZA() * 180.0f), (150.0f + lineVectors.get(i).getLineXB() * 150.0f), paintMax);
        }

        this.imageView.setImageBitmap(bm);
    }

    public void addDiedricoPoint(PointVector pointVector){
        Bitmap bm = Bitmap.createBitmap(200, 300, Bitmap.Config.RGB_565);

        Canvas mCanvas = new Canvas(bm);
        mCanvas.drawColor(Color.WHITE);

        mCanvas.drawLine(20.0f, 150.0f, 180.0f, 150.0f, paintMax);

        mCanvas.drawCircle((100.0f + (float) pointVector.getPointZ() * 120), (150.0f - ((float) pointVector.getPointY() * 120.0f)), 1.5f, paintMax);
        mCanvas.drawCircle((100.0f + (float) pointVector.getPointZ() * 120), (150.0f + ((float) pointVector.getPointX() * 120.0f)), 1.5f, paintMax);

        this.imageView.setImageBitmap(bm);
    }

    public void addLine(LineVector lineVector){
        Bitmap bm = Bitmap.createBitmap(200, 300, Bitmap.Config.RGB_565);

        Canvas mCanvas = new Canvas(bm);
        mCanvas.drawColor(Color.WHITE);

        mCanvas.drawLine(20.0f, 150.0f, 180.0f, 150.0f, paintMax);

        mCanvas.drawLine((100.0f + lineVector.getLineZB() * 180.0f), (150.0f - (lineVector.getLineYA()) * 150.0f), (100.0f + lineVector.getLineZA() * 180.0f), (150.0f - lineVector.getLineYB() * 150.f), paintMax);
        mCanvas.drawLine((100.0f + lineVector.getLineZB() * 180.0f), (150.0f + (lineVector.getLineXA()) * 150.0f), (100.0f + lineVector.getLineZA() * 180.0f), (150.0f + lineVector.getLineXB() * 150.0f), paintMax);

        this.imageView.setImageBitmap(bm);
    }
}
