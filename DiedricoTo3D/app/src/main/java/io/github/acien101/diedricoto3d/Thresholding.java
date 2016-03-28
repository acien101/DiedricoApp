package io.github.acien101.diedricoto3d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.android.ConvertBitmap;
import boofcv.android.VisualizeImageData;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageUInt8;

/**
 * Created by amil101 on 24/01/16.
 */
public class Thresholding extends AsyncTask<String,Integer,Bitmap>{
    ImageView pic;
    int progress;
    Bitmap bmPic;
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        bmPic = bitmap;
        pic.setImageBitmap(bitmap);

    }

    @Override
    protected Bitmap doInBackground(String... params) {

        Bitmap image = BitmapFactory.decodeFile(params[0]);

        Bitmap image2 = Bitmap.createScaledBitmap(image, 500, 500, false);

        // convert into firstPoint usable format
        ImageFloat32 input = ConvertBitmap.bitmapToGray(image2,(ImageFloat32)null,null);                    //(image, null, ImageFloat32.class);

        ImageUInt8 binary = new ImageUInt8(input.width,input.height);

        GThresholdImageOps.localSauvola(input, binary, progress, 0.3f, false);
        VisualizeImageData.binaryToBitmap(binary, false, image2, null);

        image = image2;
        return image;
    }

    public Thresholding(ImageView pic, int progress) {
        super();
        this.pic = pic;
        this.progress = progress;
    }

    public Bitmap getPic(){
        return bmPic;
    }

}
