package io.github.acien101.diedricoto3d;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
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

import io.github.acien101.diedricoto3d.openGL.Line;
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

    List<PuntoDiedrico> puntoDiedricos = new ArrayList<>();

    Bitmap originalBitmap;

    int currentType;
    List<Punto> cotas = new ArrayList<>();
    List<Punto> alejamiento = new ArrayList<>();

    List<Linea> currentLine = new ArrayList<>();

    int typeOfPoint = 0;             // 0 means cota, 1 means alejamiento
    int numberOfPoint = 0;

    ArrayAdapter<String> menuPuntoArrayAdapter;
    ArrayAdapter<String> menuLineaArrayAdapter;

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
        ArrayAdapter<CharSequence> menuColorArrayAdapter = ArrayAdapter.createFromResource(this, R.array.colorsSpinner, android.R.layout.simple_spinner_item);
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

                        List<String> linesForSpinner = new ArrayList<>();
                        for(int i = 0; i < lineas.size(); i++){
                            linesForSpinner.add("Linea " + Integer.toString(i) + " Xa: " + Float.toString((float) lineasObj.get(i).getXa()) + " Ya: " + Float.toString((float) lineasObj.get(i).getYa()) + " Xb: "  + Float.toString((float) lineasObj.get(i).getXb()) + " Yb: " + Float.toString((float) lineasObj.get(i).getYb()));
                        }


                        menuPuntoArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, pointsForSpinner);
                        menuPuntoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        menuLineaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, linesForSpinner);
                        menuLineaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                        /*
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

                        */


                        //Creamos la Spinner con los alejamientos y cotas que antes hemos indicado
                        //We create a Spinner with the cotas(?) and alejamientos(?) that we early specificated

                        List<String> puntosSpinner = new ArrayList<String>();
                        for(int i = 0; i < Integer.parseInt(nLineas.getText().toString()); i++){
                            puntosSpinner.add("Linea " + i);
                            if(i == 0){
                                currentLine.add(lineasObj.get(i));          //we need to put at least one, later we specify what line it is, in the second spinner
                            }
                        }
                        for(int i =0; i< Integer.parseInt(nPuntos.getText().toString()); i++){
                            puntosSpinner.add("Cota punto nº " + i);
                            cotas.add(puntosObj.get((i*2)));
                            puntosSpinner.add("Alejamiento punto nº " + i);
                            alejamiento.add(puntosObj.get(((i*2)+1)));

                        }

                        //Is needed for ListenPoint
                        final Bitmap transformationBM;

                        ArrayAdapter<String> menuTipoArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, puntosSpinner);
                        menuTipoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        menuTipo.setAdapter(menuTipoArrayAdapter);
                        menuTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Log.i("info", "Toco " + position);
                                currentType = position;
                                if((position - Integer.parseInt(nLineas.getText().toString())) >= 0 && (position - Integer.parseInt(nLineas.getText().toString()))%2 == 0){                        //if it is a cota(?)
                                    menuNumero.setAdapter(menuPuntoArrayAdapter);
                                    menuNumero.setOnItemSelectedListener(onMenuNumeroPointSelectedListener(cotas.get((position - Integer.parseInt(nLineas.getText().toString())) / 2)));
                                    Log.i("INFO", "COTA");
                                    new ListenPoint(pic, Bitmap.createBitmap(asdf.getPic()), cotas.get((position - Integer.parseInt(nLineas.getText().toString()))/2));

                                    typeOfPoint = 0;                //what type it is, for later with the other spinner specify the point
                                    numberOfPoint = (position - Integer.parseInt(nLineas.getText().toString()))/2;          //what number of point it is
                                }
                                if((position - Integer.parseInt(nLineas.getText().toString())) >= 0 && (position - Integer.parseInt(nLineas.getText().toString())) % 2 != 0) {
                                    menuNumero.setAdapter(menuPuntoArrayAdapter);// if it is a alejamiento(?)
                                    menuNumero.setOnItemSelectedListener(onMenuNumeroPointSelectedListener(alejamiento.get((position - Integer.parseInt(nLineas.getText().toString()))/2)));
                                    Log.i("INFO", "ALEJAMIENTO");
                                    new ListenPoint(pic, Bitmap.createBitmap(asdf.getPic()), alejamiento.get((position - Integer.parseInt(nLineas.getText().toString()))/2));

                                    typeOfPoint = 1;            //what type it is, for later with the other spinner specify the point
                                    numberOfPoint = (position - Integer.parseInt(nLineas.getText().toString()))/2;          //what number of point it is
                                }
                                if(position == 0){
                                    Log.i("INFO", "LINE");

                                    menuNumero.setAdapter(menuLineaArrayAdapter);

                                    menuNumero.setOnItemSelectedListener(onMenuNumeroLineSelectedListener());
                                    new ListenLine(pic, Bitmap.createBitmap(asdf.getPic()), currentLine.get(0));


                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        //Creamos el Spinner con todos los puntos(por ahora) para seleccionar si son cotas o alejamientos
                        //We create a Spinner with all the points (currently) for select later
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

                ArrayList<PuntoVector> puntoVectors = new ArrayList<PuntoVector>();


                for(int i = 0; i < Integer.parseInt(nPuntos.getText().toString()); i++){
                    Vector vector = new Vector();
                    vector.createVector(currentLine.get(0).getXa(), currentLine.get(0).getYa(), currentLine.get(0).getXb(), currentLine.get(0).getYb(), "AB");
                    vector.createVector(currentLine.get(0).getXa(), currentLine.get(0).getYa(), cotas.get(i).getX(), cotas.get(i).getY(), "AC");
                    vector.getAngle(vector.getVector("AB"), vector.getVector("AC"));

                    Vector vector2 = new Vector();
                    vector.createVector(currentLine.get(0).getXa(), currentLine.get(0).getYa(), currentLine.get(0).getXb(), currentLine.get(0).getYb(), "AB");
                    vector2.createVector(currentLine.get(0).getXa(), currentLine.get(0).getYa(), alejamiento.get(i).getX(), alejamiento.get(i).getY(), "AD");
                    vector2.getAngle(vector.getVector("AB"), vector2.getVector("AD"));

                    puntoVectors.add(new PuntoVector(vector.getHeight()/vector.getLandLine(), vector2.getHeight()/vector.getLandLine(),vector.getLenght()/vector.getLandLine()));

                }

                Intent intent = new Intent(getApplicationContext(), OpenGlActivity.class);
                intent.putParcelableArrayListExtra("vector", puntoVectors);

                Log.i("send", Integer.toString(puntoVectors.size()));
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

    AdapterView.OnItemSelectedListener onMenuNumeroPointSelectedListener(Punto selectedPoint) {

        menuNumero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int contador = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {               //if we select a point, it converts in the type of the previous Spinner (Cota o alejamiento), with this way we specify what point is it
                if(contador != 0) {                         //when we create the listener, it activates alone. This don't let him!!
                    Log.i("INFO", "TOCADOO MENUNUMERO");
                    new ListenPoint(pic, Bitmap.createBitmap(asdf.getPic()), puntosObj.get(position));

                    if (typeOfPoint == 0) {           //the point we selected is a cota(?)

                        Punto necessaryPoint = puntosObj.get(position);
                        cotas.remove(numberOfPoint);
                        cotas.add(numberOfPoint, puntosObj.get(position));

                    } else {                               //the point we selected is a alejamiento(?)

                        Punto necessaryPoint = puntosObj.get(position);
                        alejamiento.remove(numberOfPoint);
                        alejamiento.add(numberOfPoint, puntosObj.get(position));

                    }
                }
                contador++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new ListenPoint(pic, Bitmap.createBitmap(asdf.getPic()), selectedPoint);
        return menuNumero.getOnItemSelectedListener();

    }

    AdapterView.OnItemSelectedListener onMenuNumeroLineSelectedListener() {
        menuNumero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int contador = 0;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {               //if we select a point, it converts in the type of the previous Spinner (Cota o alejamiento), with this way we specify what point is it
                if(contador != 0) {
                    new ListenLine(pic, Bitmap.createBitmap(asdf.getPic()), lineasObj.get(position));

                    currentLine.remove(0);
                    currentLine.add(lineasObj.get(position));

                }
                contador++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return menuNumero.getOnItemSelectedListener();
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


