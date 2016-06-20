package io.github.acien101.diedricoanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import io.github.acien101.diedricoanimation.vector.PointVector;

/**
 * Created by amil101 on 19/06/16.
 */
public class CreateDiedrico {
    Canvas mCanvas;
    public CreateDiedrico(PointVector point, ImageView imageView){

        Bitmap bm = Bitmap.createBitmap(200, 300, Bitmap.Config.RGB_565);

        mCanvas = new Canvas(bm);
        mCanvas.drawColor(Color.WHITE);
        
        Paint paintMax;
        paintMax = new Paint();
        paintMax.setColor(Color.BLACK);
        paintMax.setStyle(Paint.Style.FILL);

        mCanvas.drawCircle(((float) point.getPointX()), (150.0f - (float) point.getPointY()), 1.5f, paintMax);
        mCanvas.drawCircle((float) point.getPointX(), (150.0f + (float) point.getPointZ()), 1.5f, paintMax);
        mCanvas.drawLine(20.0f, 150.0f, 180.0f, 150.0f, paintMax);


        imageView.setImageBitmap(bm);
    }
}
