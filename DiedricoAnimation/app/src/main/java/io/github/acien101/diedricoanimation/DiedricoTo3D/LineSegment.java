package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac;
import boofcv.android.ConvertBitmap;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.factory.feature.detect.line.FactoryDetectLineAlgs;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageSingleBand;
import georegression.struct.line.LineSegment2D_F32;

/**
 * Created by amil101 on 31/01/16.
 */
public class LineSegment extends AsyncTask<Bitmap, Integer, Bitmap>{
    Context context;
    ImageView pic;
    Bitmap bmPic;
    private static Bitmap myBM;

    int nPoints;
    int nLines;
    int nPlanes;

    List<Point> points = new ArrayList<>();
    List<Line> lines = new ArrayList<>();

    Spinner numeroPuntos;

    public AsyncResponse delegate = null;

    @Override
    protected Bitmap doInBackground(Bitmap... params) {

        myBM = detectLineSegments(detect(params[0], ImageFloat32.class, nPoints *2), ImageFloat32.class, ImageFloat32.class);

        return myBM;


    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        bmPic = bitmap;
        pic.setImageBitmap(bitmap);

        delegate.processFinish(points, lines, null);
    }

    public LineSegment(Context context, ImageView pic, int nPoints, Spinner numeroPuntos, AsyncResponse delegate){
        super();
        this.delegate = delegate;
        this.context = context;
        this.pic = pic;
        this.nPoints = nPoints;
        this.numeroPuntos = numeroPuntos;
    }

    public <T extends ImageSingleBand, D extends ImageSingleBand>
    Bitmap detectLineSegments( Bitmap image ,
                             Class<T> imageType ,
                             Class<D> derivType )
    {
        // convert the line into a single band image
        T input = ConvertBitmap.bitmapToGray(image, null, imageType, null);

        // Comment/uncomment to try a different type of line detector
        DetectLineSegmentsGridRansac<T,D> detector = FactoryDetectLineAlgs.lineRansac(40, 10, 1, true, imageType, derivType);

        List<LineSegment2D_F32> found = detector.detect(input);

        Paint paintMax;
        paintMax = new Paint();
        paintMax.setColor(Color.RED);
        paintMax.setStyle(Paint.Style.FILL);

        Canvas canvas = new Canvas(image);

        if(found.size() > 0){
            List<Double> modules = new ArrayList<>();               //For preveting find very short lines, we get the top module and then we discard the short ones
            for (int i = 0; i < found.size(); i++) {
                modules.add(new Vector(new Point(found.get(i).a.x, found.get(i).a.y), new Point(found.get(i).b.x, found.get(i).b.y)).getModule());
            }
            Collections.sort(modules);
            Collections.reverse(modules);

            double topModule = modules.get(0);          //this is the module of the largest line

            for (int i = 0; i < found.size(); i++) {
                if (new Vector(new Point(found.get(i).a.x, found.get(i).a.y), new Point(found.get(i).b.x, found.get(i).b.y)).getModule() > (topModule / 6)) {
                    canvas.drawLine(found.get(i).a.x, found.get(i).a.y, found.get(i).b.x, found.get(i).b.y, paintMax);
                    if (found.get(i).a.x < found.get(i).b.x) {               ////problem with lines
                        lines.add(new Line(found.get(i).a.x, found.get(i).a.y, found.get(i).b.x, found.get(i).b.y));
                    } else {
                        lines.add(new Line(found.get(i).b.x, found.get(i).b.y, found.get(i).a.x, found.get(i).a.y));
                    }
                }
            }
        }
        return image;
    }

    public <T extends ImageFloat32>
    Bitmap detect( Bitmap image, Class<T> imageType , int nPuntos) {

        if(nPuntos != 0){           //There is a problem with BoofCV if we put 0 points
            T input = ConvertBitmap.bitmapToGray(image, null, imageType, null);

            // Create a Fast Hessian detector from the SURF paper.
            // Other detectors can be used in this example too.

            InterestPointDetector<T> detector = FactoryInterestPoint.fastHessian(
                    new ConfigFastHessian(30, 2, nPuntos, 2, 9, 3, 4));

            // find interest points in the image
            detector.detect(input);

            Paint paintMax;
            paintMax = new Paint();
            paintMax.setColor(Color.RED);
            paintMax.setStyle(Paint.Style.FILL);

            Canvas canvas = new Canvas(image);


            for(int i = 0; i<detector.getNumberOfFeatures();i++){
                canvas.drawCircle((float) detector.getLocation(i).getX(), (float) detector.getLocation(i).getY(), 3, paintMax);
                points.add(new Point(detector.getLocation(i).getX(), detector.getLocation(i).getY()));
            }
        }
        return image;

    }

    public Bitmap getPic(){
        return bmPic;
    }

    public interface AsyncResponse{
        void processFinish(List<Point> points, List<Line> lines, List<Double> planos);
    }

}
