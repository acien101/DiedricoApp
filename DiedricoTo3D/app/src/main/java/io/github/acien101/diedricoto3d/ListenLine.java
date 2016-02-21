package io.github.acien101.diedricoto3d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by amil101 on 21/02/16.
 */
public class ListenLine {
    public ListenLine(ImageView imageView, Bitmap pic, Linea line){
        Bitmap transformation = pic;

        Paint paintMax;
        paintMax = new Paint();
        paintMax.setColor(Color.YELLOW);
        paintMax.setStyle(Paint.Style.FILL);

        Canvas canvas = new Canvas(transformation);

        canvas.drawLine((float)line.getXa(),(float)line.getXb(), (float)line.getYa(),(float)line.getYb(),paintMax);

        imageView.setImageBitmap(transformation);
    }
}
