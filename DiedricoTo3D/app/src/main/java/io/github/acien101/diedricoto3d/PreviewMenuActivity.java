package io.github.acien101.diedricoto3d;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import io.github.acien101.diedricoto3d.openGL.OpenGlActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amil101 on 12/02/16.
 */
public class PreviewMenuActivity extends Activity{
    ImageView pic;
    String file;
    Bitmap bmImg;
    Thresholding obj;
    int radious;
    TextView text;
    LineSegment asdf;

    SeekBar seekBar;

    EditText nPuntos;
    EditText nLineas;
    EditText nPlanos;


    Spinner menuTipo;
    Spinner menuNumero;
    Spinner menuColor;

    List<Punto> puntosObj = new ArrayList<>();
    List<Linea> lineasObj = new ArrayList<>();


    Bitmap originalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_menu);

        Intent intent = getIntent();
        String originalFile = intent.getStringExtra("file");
        file = "/storage/emulated/0/Android/data/io.github.acien101.diedricoto3d/files/pic2.jpg";

        copyFile(originalFile, file);

        pic = (ImageView) findViewById(R.id.imagePreview);
        bmImg = BitmapFactory.decodeFile(file);


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        nPuntos = (EditText) findViewById(R.id.nPuntos);
        nLineas = (EditText) findViewById(R.id.nLineas);
        nPlanos = (EditText) findViewById(R.id.nPlanos);

        // array of colors
        String colors[] = {"Linea de tierra", "punto 1"};

        //Set menuTipo to the view and then put an array
        menuTipo = (Spinner) findViewById(R.id.menu_tipo);
        ArrayAdapter<String> menuTipoArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        menuTipoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        menuTipo.setAdapter(menuTipoArrayAdapter);


        //Set menuNumero to the view and then put an array
        menuNumero = (Spinner) findViewById(R.id.menu_numero);
        ArrayAdapter<String> menuNumeroArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        menuNumeroArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuNumero.setAdapter(menuNumeroArrayAdapter);

        //Set menuNumero to the view and then put an array
        menuColor = (Spinner) findViewById(R.id.menu_color);
        ArrayAdapter<String> menuColorArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        menuColorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuColor.setAdapter(menuColorArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preview_menu_topmenu, menu);

        MenuItem analizar = menu.findItem(R.id.analizar);
        analizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                funcionCualquiera("analizar");

                Bitmap picBM = obj.getPic();
                asdf = new LineSegment(getApplicationContext(), pic, Integer.parseInt(nPuntos.getText().toString()), Integer.parseInt(nPlanos.getText().toString()), Integer.parseInt(nPlanos.getText().toString()), menuTipo, new LineSegment.AsyncResponse() {
                    @Override
                    public void processFinish(List<Punto> puntos, List<Linea> lineas, List<Double> planos) {

                        puntosObj = puntos;
                        lineasObj = lineas;

                        List<String> pointsForSpinner = new ArrayList<String>();
                        for(int i = 0; i< puntos.size(); i ++){

                            pointsForSpinner.add("Punto " + Integer.toString(i) + " X:" + Float.toString((float) puntosObj.get(i).getX()) + " Y:" + Float.toString((float) puntosObj.get(i).getY()));



                            Log.i("puntos", "Punto " + Integer.toString(i) + " X:" + Double.toString(puntosObj.get(i).getX()) + " Y:" + Double.toString(puntosObj.get(i).getY()));
                        }


                        List<String> tipos = new ArrayList<String>();
                        tipos.add("Linea de tierra");
                        for(int i =0; i< Integer.parseInt(nPuntos.getText().toString()); i++){
                            tipos.add("Punto nº " + i);
                        }
                        for(int i =0; i< Integer.parseInt(nLineas.getText().toString()); i++){
                            tipos.add("Linea nº " + i);
                        }
                        for(int i =0; i< Integer.parseInt(nPlanos.getText().toString()); i++){
                            tipos.add("Plano nº " + i);
                        }

                        //Is needed for ListenPoint
                        final Bitmap transformationBM;

                        ArrayAdapter<String> menuTipoArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tipos);
                        menuTipoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        menuTipo.setAdapter(menuTipoArrayAdapter);


                        ArrayAdapter<String> menuNumeroArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, pointsForSpinner);
                        menuNumeroArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        menuNumero.setAdapter(menuNumeroArrayAdapter);

                        menuNumero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                new ListenPoint(pic, Bitmap.createBitmap(asdf.getPic()),puntosObj.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                });
                asdf.execute(picBM);

                return true;
            }
        });

        MenuItem info = menu.findItem(R.id.info);
        info.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                funcionCualquiera("info");

                return true;
            }
        });

        MenuItem procesar = menu.findItem(R.id.procesar);
        procesar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                funcionCualquiera("procesar");

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

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    void funcionCualquiera(String mensaje){
        Log.i("menuPreview", mensaje);
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


