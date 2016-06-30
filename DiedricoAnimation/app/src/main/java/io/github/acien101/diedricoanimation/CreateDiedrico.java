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

    Canvas mCanvas;
    Paint paintMax;

    ImageView imageView;

    Bitmap bm;
    public CreateDiedrico(ImageView imageView){

        this.imageView = imageView;

        bm = Bitmap.createBitmap(200, 300, Bitmap.Config.RGB_565);

        mCanvas = new Canvas(bm);
        mCanvas.drawColor(Color.WHITE);
        

        paintMax = new Paint();
        paintMax.setColor(Color.BLACK);
        paintMax.setStyle(Paint.Style.FILL);

        this.imageView.setImageBitmap(bm);
    }

    public void addDiedricoPoints(List<PointVector> pointVectors){

        for(int i = 0; i < pointVectors.size(); i++){
            mCanvas.drawCircle(((float) pointVectors.get(i).getPointZ()), (150.0f - (float) pointVectors.get(i).getPointY()), 1.5f, paintMax);
            mCanvas.drawCircle((float) pointVectors.get(i).getPointZ(), (150.0f + (float) pointVectors.get(i).getPointX()), 1.5f, paintMax);
            mCanvas.drawLine(20.0f, 150.0f, 180.0f, 150.0f, paintMax);
        }

        this.imageView.setImageBitmap(bm);
    }

    public void addDiedricoLines(List<LineVector> lineVectors, ImageView imageView){

    }
}
