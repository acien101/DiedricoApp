package com.example.android.camera2basic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.camera2basic.openGL.OpenGlActivity;

import java.io.*;

import java.io.FileNotFoundException;

public class ImageToBoofCV extends Activity {

    ImageView pic;
    String file;
    Bitmap bmImg;
    Thresholding obj;
    int radious;
    TextView text;
    LineSegment asdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_boof_cv);

        Intent intent = getIntent();
        String originalFile = intent.getStringExtra("file");
        file = "/storage/emulated/0/Android/data/com.example.android.camera2basic/files/pic2.jpg";

        copyFile(originalFile, file);

        pic = (ImageView)findViewById(R.id.imageView);
        bmImg = BitmapFactory.decodeFile(file);
        text = (TextView) findViewById(R.id.picInfo);


        /*
        ImageView pic = (ImageView)findViewById(R.id.imageView);
        Bitmap bmImg = BitmapFactory.decodeFile(file);
        pic.setImageBitmap(bmImg);
           */

        SeekBar seek = (SeekBar) findViewById(R.id.seekBar);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radious = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                obj = new Thresholding(pic, radious);
                obj.execute(file);

                Log.i("seek", String.valueOf(radious));

            }
        });

        Button interestButton = (Button) findViewById(R.id.botonInteresante);
        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap picBM = obj.getPic();
                asdf = new LineSegment(pic);
                asdf.execute(picBM);
            }
        });

        Button readyButton = (Button)findViewById(R.id.ready);
        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text.setText("altura: " + (asdf.getVector().getHeight()/asdf.getVector().getLandLine()) + "\r\n"
                            + "longitud: " + (asdf.getVector().getLenght()/asdf.getVector().getLandLine()) + "\r\n"
                            + "land line length " + "1");

            }
        });

        Button openGL = (Button) findViewById(R.id.openGL);
        openGL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double altura1;
                double altura2;
                double longitud;

                longitud = (asdf.getVector().getLenght()/asdf.getVector().getLandLine());
                Log.i("longituddd", Double.toString(longitud));
                altura1 = (asdf.getVector().getHeight()/asdf.getVector().getLandLine());
                Log.i("alturaaa", Double.toString(altura1));
                altura2 = (asdf.getVector2().getHeight()/asdf.getVector2().getLandLine());
                Log.i("alturaaa", Double.toString(altura1));


                Intent intent = new Intent(getApplicationContext(), OpenGlActivity.class);

                Log.i("asdff", Double.toString(altura1));
                intent.putExtra("altura1", altura1);
                intent.putExtra("altura2", altura2);
                intent.putExtra("longitud", longitud);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(intent);



            }
        });
    }

        private void copyFile(String inputFile, String outputPath) {

            InputStream in = null;
            OutputStream out = null;
            try {
                /*
                //create output directory if it doesn't exist
                File dir = new File (outputPath);
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                */

                in = new FileInputStream(inputFile);
                out = new FileOutputStream(outputPath);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                in = null;

                // write the output file (You have now copied the file)
                out.flush();
                out.close();
                out = null;

            }  catch (FileNotFoundException fnfe1) {
                Log.e("tag", fnfe1.getMessage());
            }
            catch (Exception e) {
                Log.e("tag", e.getMessage());
            }

        }

}
