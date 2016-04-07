package io.github.acien101.diedricoto3d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.ImageView;

import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.android.ConvertBitmap;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.struct.image.ImageFloat32;

/**
 * Created by amil101 on 31/01/16.
 */
public class GetPoints extends AsyncTask<Bitmap, Integer, Bitmap> {

    ImageView pic;
    Bitmap bmPic;

    @Override
    protected Bitmap doInBackground(Bitmap... params) {

        return detect(params[0],ImageFloat32.class);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        bmPic = bitmap;
        pic.setImageBitmap(bitmap);

    }

    public GetPoints(ImageView pic){
        super();
        this.pic = pic;
    }

    public static <T extends ImageFloat32>
    Bitmap detect( Bitmap image, Class<T> imageType ) {

        T input = ConvertBitmap.bitmapToGray(image, null, imageType, null);

        // Create a Fast Hessian detector from the SURF paper.
        // Other detectors can be used in this example too.
        InterestPointDetector<T> detector = FactoryInterestPoint.fastHessian(
                new ConfigFastHessian(10, 2, 100, 2, 9, 3, 4));

        // find interest points in the image
        detector.detect(input);

        Paint paintMax;
        paintMax = new Paint();
        paintMax.setColor(Color.RED);
        paintMax.setStyle(Paint.Style.FILL);

        Canvas canvas = new Canvas(image);


        for(int i = 0; i<detector.getNumberOfFeatures();i++){
            canvas.drawCircle((float)detector.getLocation(i).getX(),(float)detector.getLocation(i).getY(),3,paintMax);
        }

        return image;

    }
}
