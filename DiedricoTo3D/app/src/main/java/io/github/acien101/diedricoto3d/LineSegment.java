package io.github.acien101.diedricoto3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
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
    static List<Double> interestPoints = new ArrayList<Double>();
    static List<Float> landLine = new ArrayList<Float>();
    private static Bitmap myBM;
    Vector vector;
    Vector vector2;

    int nPuntos;
    int nLineas;
    int nPlanos;

    List<Punto> puntos = new ArrayList<>();
    List<Linea> lineas = new ArrayList<>();

    Spinner numeroPuntos;

    public AsyncResponse delegate = null;

    public Vector getVector() {
        return vector;
    }
    public Vector getVector2() { return  vector2;}

    public List<Punto> getPuntos() {
        return puntos;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... params) {

        myBM = detectLineSegments(detect(params[0], ImageFloat32.class, nPuntos*2), ImageFloat32.class, ImageFloat32.class);

        /*
        Log.i("land line a x", Double.toString(landLine.get(0)));
        Log.i("land line a y", Double.toString(landLine.get(1)));
        Log.i("land line b x", Double.toString(landLine.get(2)));
        Log.i("land line b y", Double.toString(landLine.get(3)));

        Log.i("punto interesante x", Double.toString(interestPoints.get(0)));
        Log.i("punto interesante y", Double.toString(interestPoints.get(1)));

        vector = new Vector();
        vector.createVector(landLine.get(0), landLine.get(1), landLine.get(2), landLine.get(3), "AB");
        vector.createVector(landLine.get(0), landLine.get(1), interestPoints.get(0), interestPoints.get(1), "AC");
        vector.getAngle(vector.getVector("AB"), vector.getVector("AC"));

        vector2 = new Vector();
        vector2.createVector(landLine.get(0), landLine.get(1), landLine.get(2), landLine.get(3), "AB");
        vector2.createVector(landLine.get(0), landLine.get(1), interestPoints.get(2), interestPoints.get(3), "AD");
        vector2.getAngle(vector2.getVector("AB"), vector2.getVector("AD"));

        */

        Log.i("info", Integer.toString(puntos.size()));

        return myBM;


    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        bmPic = bitmap;
        pic.setImageBitmap(bitmap);

        delegate.processFinish(puntos, lineas, null);
    }

    public LineSegment(Context context, ImageView pic, int nPuntos, int nLineas, int nPlanos, Spinner numeroPuntos, AsyncResponse delegate){
        super();
        this.delegate = delegate;
        this.context = context;
        this.pic = pic;
        this.nPuntos = nPuntos;
        this.nLineas = nLineas;
        this.nPlanos = nPlanos;
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


        for(int i = 0; i<found.size();i++){
            canvas.drawLine(found.get(i).a.x,found.get(i).a.y, found.get(i).b.x,found.get(i).b.y,paintMax);
            lineas.add(new Linea(found.get(i).a.x, found.get(i).a.y, found.get(i).b.x, found.get(i).b.y));
        }



        landLine.add(0, found.get(0).a.x);
        landLine.add(1, found.get(0).a.y);

        landLine.add(2, found.get(0).b.x);
        landLine.add(3, found.get(0).b.y);

        return image;
    }

    public <T extends ImageFloat32>
    Bitmap detect( Bitmap image, Class<T> imageType , int nPuntos) {

        T input = ConvertBitmap.bitmapToGray(image, null, imageType, null);

        // Create a Fast Hessian detector from the SURF paper.
        // Other detectors can be used in this example too.

        Log.i("nPuntos", Integer.toString(nPuntos));

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
            puntos.add(new Punto(detector.getLocation(i).getX(), detector.getLocation(i).getY()));
        }

        interestPoints.add(0, detector.getLocation(0).getX());
        interestPoints.add(1, detector.getLocation(0).getY());
        interestPoints.add(2, detector.getLocation(1).getX());
        interestPoints.add(3, detector.getLocation(1).getY());

        return image;

    }

    public Bitmap getPic(){
        return bmPic;
    }

    public interface AsyncResponse{
        void processFinish(List<Punto> puntos, List<Linea> lineas, List<Double> planos);
    }

}
