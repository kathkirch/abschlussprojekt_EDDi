package com.example.abschlussprojekt_eddi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abschlussprojekt_eddi.ui.main.Startseite_Fragment;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.security.Timestamp;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Eintrag_Stuhl extends AppCompatActivity {

    Intent intent;
    Intent intentStartseite;
    Context context = this;
    ImageButton imageButton_camera;
    ImageView imageView_stuhl;
    EditText editText_currentDate;
    EditText editText_currentTime;
    Button button_speichern;
    FileOutputStream outputStream;

    final static int PERMISSION_CODE = 1;
    final static int REQUEST_CODE = 2;
    final static int GALLERY_REQUEST_CODE = 3;

    Spinner spinner_bristol;
    ArrayList<BristolItem> arrayList_bristol;
    SpinnerAdapterBristol aA_bristol;

    Spinner spinner_farbe;
    String [] array_farbe = new String [] {"braun", "grün", "grau-lehmfarben", "gelb", "rot", "schwarz"};
    ArrayAdapter <String> aA_farbe;

    Spinner spinner_schleim;
    String [] array_schleim = new String [] {"wenig", "mittel", "viel", "nur Schleim"};
    ArrayAdapter <String> aA_schleim;

    Spinner spinner_menge;
    String [] array_menge = new String [] {"wenig", "normal", "viel"};
    ArrayAdapter <String> aA_menge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eintrag__stuhl);

        //Momentanes Datum anzeigen (Werte als int gespeichert)
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        editText_currentDate = findViewById(R.id.editText_currentDate);
        editText_currentDate.setText(currentDay + "." + currentMonth + "." + currentYear);

        //Momentane Zeit anzeigen (Werte als int gespeichert)
        //brauche wir sekunden? Userbility?
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        editText_currentTime = findViewById(R.id.editText_currentTime);
        editText_currentTime.setText(currentHour + ":" + currentMinute);

        //Bristol Spinner ArrayList befüllen
        initListBristol();

        intent = getIntent();

        spinner_bristol = findViewById(R.id.spinner_bristol);
        aA_bristol = new SpinnerAdapterBristol(this, arrayList_bristol);
        //eigenes Layout erstellt für die Darstellung der Bristol-Skala + Text
        aA_bristol.setDropDownViewResource(R.layout.dropdown_item_bristol);
        spinner_bristol.setAdapter(aA_bristol);

        spinner_farbe = findViewById(R.id.spinner_farben);
        aA_farbe = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array_farbe);
        spinner_farbe.setAdapter(aA_farbe);

        spinner_schleim = findViewById(R.id.spinner_schleim);
        aA_schleim = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array_schleim);
        spinner_schleim.setAdapter(aA_schleim);

        spinner_menge = findViewById(R.id.spinner_menge);
        aA_menge = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, array_menge);
        spinner_menge.setAdapter(aA_menge);

        //Kamera
        imageButton_camera = findViewById(R.id.imageButton_kamera);
        imageView_stuhl = findViewById(R.id.imageView_stuhl);
        //wenn Button gedrückt wird, wird überprüft, ob die Camera Permission gegeben ist
        //wenn keine Permission gegeben ist, wird eine Angefragt
        //danch wird die Methode startActivityForResult aufgerufen und es kann ein Foto gemacht und gespeichert werden
        imageButton_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        Eintrag_Stuhl.this,
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE); //dies Methode ruft die onActivityResult() Methode auf
                }
                else {
                    ActivityCompat.requestPermissions(
                            Eintrag_Stuhl.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_CODE);
                }
            }
        });

        //Button Spuelen/Speichern
        button_speichern = findViewById(R.id.button_stuhlgang_speichern);
        button_speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Permission wird angefragt
                if(ContextCompat.checkSelfPermission(
                        Eintrag_Stuhl.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(
                            Eintrag_Stuhl.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED){
                        savePicture(); //diese Methode speichert das Bild in der Gallerie
                    }
                }
                else {
                    ActivityCompat.requestPermissions(
                            Eintrag_Stuhl.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            GALLERY_REQUEST_CODE);
                    ActivityCompat.requestPermissions(
                            Eintrag_Stuhl.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            GALLERY_REQUEST_CODE);
                }
                Toast.makeText(Eintrag_Stuhl.this, "Eintrag gespeichert", Toast.LENGTH_SHORT).show();

                /*
                intentStartseite = new Intent(context, Startseite_Fragment.class);
                startActivity(intentStartseite); //Startseite ist ein Fragment und keine Activity!


                //um ein Fragment aufzurufen
                //Problem: unser Fragment hat kein FrameLayout, kann die container ID nicht finden
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_statistik, new Startseite_Fragment())
                        .commit();


                //Auf Folie 13 bei "Fragments"
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.view_pager,
                        Startseite_Fragment.class,
                        null);
                ft.commit();
                */
            }
        });
    }

    //Methode wird automatisch aufgerufen von startActivityForResult()
    //Bild wird gemacht und in ImageView gespeichert und angezeigt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            startCamera();
            //get Capture Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //Set Capture Image to ImageView
            imageView_stuhl.setImageBitmap(captureImage);
            //um ImageView anzuzeigen, weil dieser davor GONE ist
            imageView_stuhl.setVisibility(View.VISIBLE);
        }
    }

    private void startCamera(){
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    //Bristol-Spinner wird mit Text und Bild befüllt
    private void initListBristol(){
        arrayList_bristol = new ArrayList<>();
        arrayList_bristol.add(new BristolItem("einzelne, fest Kügelchen, schwer auszuscheiden", R.drawable.type01));
        arrayList_bristol.add(new BristolItem("wurstartig, klumpig", R.drawable.type02));
        arrayList_bristol.add(new BristolItem("wurstartig mit rissiger Oberfläche", R.drawable.type03));
        arrayList_bristol.add(new BristolItem("wurstartig mit glatter, durchgehender Oberfläche", R.drawable.type04));
        arrayList_bristol.add(new BristolItem("einzelne weiche glattrandige Klümpchen, leicht auszuscheiden", R.drawable.type05));
        arrayList_bristol.add(new BristolItem("einzelne weiche Klümpchen mit unregelmäßigem Rand", R.drawable.type06));
        arrayList_bristol.add(new BristolItem("flüssig, ohne feste Bestandteile", R.drawable.type07));
    }

/*
    private void savePicture(){
        //BitmapDrawable mit dem ImageView des Bildes wird erstellt
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView_stuhl.getDrawable();
        //BitmapDrawable wird in Bitmap gespeichert
        Bitmap bitmap = bitmapDrawable.getBitmap();

       FileOutputStream outputStream;
        String filepath = Environment.getExternalStorageDirectory().toString();
        File dir = new File(filepath + "/EDDi");
        if(!dir.exists()){
            //Directory wird erstellt
            boolean mkdir = dir.mkdirs();
            if(!mkdir){
                System.out.println("dir.mkdirs failed");
            }
        }
        //String filename = String.format("%d.jpg", System.currentTimeMillis());
        String filename = "eddi01";
        File outFile = new File(dir, filename);
        System.out.println("File outFile");
        try {
            boolean createNewFile = outFile.createNewFile(); //No such file or directory! Kann File nicht erzeugen?!
            System.out.println("outfile.createNewFile");
            if(!createNewFile){
                System.out.println("createNewFile failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            scanFile(this, outFile, "image/jpeg");
            System.out.println("scanFile");
            outputStream = new FileOutputStream(outFile); //FileNotFoundException!!!
            System.out.println("outputStream");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            System.out.println("bitmap.compress");
            Toast.makeText(this, "Bild gespeichert", Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scanFile(Context c, File f, String mimeType){
        MediaScannerConnection
                .scanFile(c, new String[]{f.getAbsolutePath()},
                        new String[]{mimeType}, null);
    }
 */

    private void savePicture(){
        //BitmapDrawable mit dem ImageView des Bildes wird erstellt
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView_stuhl.getDrawable();
        //BitmapDrawable wird in Bitmap gespeichert
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Log.d("Dimensions", bitmap.getWidth() + " " + bitmap.getHeight());
        //OutputStream
        OutputStream outputStream;
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver contentResolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "EDDi_"+System.currentTimeMillis()+".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "EDDi");
                Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                outputStream = (FileOutputStream) contentResolver.openOutputStream(Objects.requireNonNull(imageUri));

                Toast.makeText(this, "Bild gespeichert", Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Objects.requireNonNull(outputStream);
            }else {
                savePictureLowerVersionCodeQ();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Bild konnte nicht gespeichert werden", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePictureLowerVersionCodeQ(){
        imageView_stuhl.buildDrawingCache();
        Bitmap bitmap = imageView_stuhl.getDrawingCache();

        //Bild wird um 90 Grad gedreht
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        String imageFileName = System.currentTimeMillis() + ".jpg";
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, imageFileName, String.valueOf(1));
    }

}



