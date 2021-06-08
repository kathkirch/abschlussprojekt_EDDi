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
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.io.StringBufferInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Eintrag_Stuhl extends AppCompatActivity {

    Intent intent;
    ImageButton imageButton_camera;
    ImageView imageView_stuhl;
    EditText editText_currentDate;
    EditText editText_currentTime;

    final static int CAMERA_PERMISSION_CODE = 1;
    final static int CAMERA_REQUEST_CODE = 2;

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

        //Kamera
        imageButton_camera = findViewById(R.id.imageButton_kamera);
        imageView_stuhl = findViewById(R.id.imageView_stuhl);

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

        //wenn Button gedrückt wird, wird überprüft, ob die Camera Permission gegeben ist
        //wenn keine Permission gegeben ist, wird eine Angefragt
        //danch wird die Methode startActivityForResult aufgerufen und es kann ein Foto gemacht und gespeichert werde
        imageButton_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        Eintrag_Stuhl.this,
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE); //dies Methode ruft die onActivityResult() Methode auf
                }
                else {
                    ActivityCompat.requestPermissions(
                            Eintrag_Stuhl.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_CODE);
                }
            }
        });
    }

    //Methode wird automatisch aufgerufen von startActivityForResult()
    //Bild wird gemacht und in ImageView gespeichert und angezeigt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE){
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

}



