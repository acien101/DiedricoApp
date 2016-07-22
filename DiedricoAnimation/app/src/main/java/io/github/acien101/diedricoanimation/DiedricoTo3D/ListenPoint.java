package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * Created by amil101 on 21/02/16.
 */
public class ListenPoint {
    public ListenPoint(ImageView imageView, Bitmap pic, Point point){
        Bitmap transformation = pic;

        Paint paintMax;
        paintMax = new Paint();
        paintMax.setColor(Color.YELLOW);
        paintMax.setStyle(Paint.Style.FILL);

        Canvas canvas = new Canvas(transformation);

        canvas.drawCircle((float) point.getX(), (float) point.getY(), 3, paintMax);

        imageView.setImageBitmap(transformation);
    }
}
