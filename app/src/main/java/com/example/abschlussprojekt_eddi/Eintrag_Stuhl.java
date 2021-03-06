package com.example.abschlussprojekt_eddi;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Eintrag_Stuhl extends AppCompatActivity {

    Context context = this;
    ImageButton imageButton_camera;
    ImageView imageView_stuhl;
    EditText editText_currentDate;
    EditText editText_currentTime;
    Button spuelen_Button;
    SwitchCompat switch_blut;
    SwitchCompat switch_schmerz;
    SwitchCompat switch_unverdauteNahrung;
    EditText edit_Notizen;

    public static final String EXTRA_ID =
            "com.example.abschlussprojekt_eddi.EXTRA_ID";
    public static final String EXTRA_DATUM =
            "com.example.abschlussprojekt_eddi.EXTRA_DATUM";
    public static final String EXTRA_UHRZEIT =
            "com.example.abschlussprojekt_eddi.EXTRA_UHRZEIT";
    public static final String EXTRA_BRISTOL =
            "com.example.abschlussprojekt_eddi.EXTRA_BRISTOL";
    public static final String EXTRA_BLUT =
            "com.example.abschlussprojekt_eddi.EXTRA_BLUT";
    public static final String EXTRA_SCHMERZ =
            "com.example.abschlussprojekt_eddi.EXTRA_SCHMERZ";
    public static final String EXTRA_FARBE =
            "com.example.abschlussprojekt_eddi.EXTRA_FARBE";
    public static final String EXTRA_UNVERDAUTENAHRUNG =
            "com.example.abschlussprojekt_eddi.EXTRA_UNVERDAUTENAHRUNG";
    public static final String EXTRA_SCHLEIM =
            "com.example.abschlussprojekt_eddi.EXTRA_SCHLEIM";
    public static final String EXTRA_MENGE =
            "com.example.abschlussprojekt_eddi.EXTRA_MENGE";
    public static final String EXTRA_NOTIZ =
            "com.example.abschlussprojekt_eddi.EXTRA_NOTIZ";

    public static final String EXTRA_JAHR =
            "com.example.abschlussprojekt_eddi.EXTRA_JAHR";
    public static final String EXTRA_MONAT =
            "com.example.abschlussprojekt_eddi.EXTRA_MONAT";
    public static final String EXTRA_TAG =
            "com.example.abschlussprojekt_eddi.EXTRA_TAG";
    public static final String EXTRA_STUNDE =
            "com.example.abschlussprojekt_eddi.EXTRA_STUNDE";
    public static final String EXTRA_MINUTE =
            "com.example.abschlussprojekt_eddi.EXTRA_MINUTE";

    final static int PERMISSION_CODE = 1;
    final static int GALLERY_REQUEST_CODE = 3;
    final static int REQUEST_CODE = 2;

    Spinner spinner_bristol;
    ArrayList<BristolItem> arrayList_bristol;
    SpinnerAdapterBristol aA_bristol;

    Spinner spinner_farbe;
    String [] array_farbe = new String [] {"braun", "gr??n", "grau-lehmfarben", "gelb", "rot", "schwarz"};
    ArrayAdapter <String> aA_farbe;

    Spinner spinner_schleim;
    String [] array_schleim = new String [] {"kein", "wenig", "mittel", "viel", "nur Schleim"};
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
        int currentMonth;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //f??r LocalDate braucht es min API 26
            currentMonth = LocalDate.now().getMonthValue();
        }else {
            currentMonth = Calendar.MONTH + 1;
        }
        int currentYear = calendar.get(Calendar.YEAR);
        editText_currentDate = findViewById(R.id.editText_currentDate);
        editText_currentDate.setText(currentDay + "." + currentMonth + "." + currentYear);

        //Momentane Zeit anzeigen (Werte als int gespeichert)
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        editText_currentTime = findViewById(R.id.editText_currentTime);
        editText_currentTime.setText(currentHour + ":" + currentMinute);

        //Bristol Spinner ArrayList bef??llen
        initListBristol();

        spinner_bristol = findViewById(R.id.spinner_bristol);
        aA_bristol = new SpinnerAdapterBristol(this, arrayList_bristol);
        //eigenes Layout erstellt f??r die Darstellung der Bristol-Skala + Text
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
        //wenn Button gedr??ckt wird, wird ??berpr??ft, ob die Camera Permission gegeben ist
        //wenn keine Permission gegeben ist, wird eine angefragt
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

        //switch instanzieren:
        switch_blut = findViewById(R.id.switch_blut);
        switch_schmerz = findViewById(R.id.switch_schmerzen);
        switch_unverdauteNahrung = findViewById(R.id.switch_unverdaute_nahrung);

        //editText notizen:
        edit_Notizen = findViewById(R.id.editText_notizen);

        //spuelen Button:
        spuelen_Button = findViewById(R.id.button_stuhlgang_speichern);
        spuelen_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Permission wird angefragt
               if (ContextCompat.checkSelfPermission(
                        Eintrag_Stuhl.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            Eintrag_Stuhl.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        savePicture(); //diese Methode speichert das Bild in der Gallerie
                    }
                } else {
                    ActivityCompat.requestPermissions(
                            Eintrag_Stuhl.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            GALLERY_REQUEST_CODE);
                    ActivityCompat.requestPermissions(
                            Eintrag_Stuhl.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            GALLERY_REQUEST_CODE);
                }
                //Stuhl speichern methode
                stuhlSpeichern();
            }
        });

        Intent intent = getIntent();
        //wenn der Eintrag bereits eine ID hat, wird er aktualisiert
        //und dann wird der gespeicherte Text ??bergeben
        if(intent.hasExtra(EXTRA_ID)){
            int jahr = intent.getIntExtra(EXTRA_JAHR, 2000);
            int monat = intent.getIntExtra(EXTRA_MONAT, 01);
            int tag = intent.getIntExtra(EXTRA_TAG, 01);
            editText_currentDate.setText(tag + "." + monat + "." + jahr);
            int stunde = intent.getIntExtra(EXTRA_STUNDE, 12);
            int minute = intent.getIntExtra(EXTRA_MINUTE, 20);
            editText_currentTime.setText(stunde + ":" + minute);
            spinner_bristol.setSelection(intent.getIntExtra(EXTRA_BRISTOL, 2));
            switch_blut.setChecked(intent.getBooleanExtra(EXTRA_BLUT, false));
            switch_schmerz.setChecked(intent.getBooleanExtra(EXTRA_SCHMERZ, false));
            spinner_farbe.setSelection(intent.getIntExtra(EXTRA_FARBE, 1));
            switch_unverdauteNahrung.setChecked(intent.getBooleanExtra(EXTRA_UNVERDAUTENAHRUNG,false));
            spinner_schleim.setSelection(intent.getIntExtra(EXTRA_SCHLEIM, 1));
            spinner_menge.setSelection(intent.getIntExtra(EXTRA_MENGE, 1));
            if (edit_Notizen != null){
                edit_Notizen.setText(intent.getStringExtra(EXTRA_NOTIZ)); //funktioniert
            }
        }
    }

    // Daten aus dem im Eintrag Stuhl eingegeben Fenster werden in einem Intent gespeichert
    // und danach in der onActivityResult in der MainActivity nochmal abgerufen
     public void stuhlSpeichern () {

        Intent stuhl_data = new Intent();

        String datum = editText_currentDate.getText().toString();
        String uhrzeit = editText_currentTime.getText().toString();
        int bristol = spinner_bristol.getSelectedItemPosition(); //plus eins weil erstes Symbol hat Position 0
        boolean blut = switch_blut.isChecked();
        boolean schmerz = switch_schmerz.isChecked();
        int farbe = spinner_farbe.getSelectedItemPosition();
        boolean unverdauteNahrung = switch_unverdauteNahrung.isChecked();
        int schleim = spinner_schleim.getSelectedItemPosition();
        int menge = spinner_menge.getSelectedItemPosition();
        String notizen = edit_Notizen.getText().toString();

         if (datum == null || uhrzeit == null){
             setResult(RESULT_CANCELED, stuhl_data);
             Toast.makeText(context, "Datum|Uhrzeit eintragen!", Toast.LENGTH_SHORT).show();
         } else {
             stuhl_data.putExtra(EXTRA_DATUM, datum);
             stuhl_data.putExtra(EXTRA_UHRZEIT, uhrzeit);
             stuhl_data.putExtra(EXTRA_BRISTOL, bristol);
             stuhl_data.putExtra(EXTRA_BLUT, blut);
             stuhl_data.putExtra(EXTRA_SCHMERZ, schmerz);
             stuhl_data.putExtra(EXTRA_FARBE, farbe);
             stuhl_data.putExtra(EXTRA_UNVERDAUTENAHRUNG, unverdauteNahrung);
             stuhl_data.putExtra(EXTRA_SCHLEIM, schleim);
             stuhl_data.putExtra(EXTRA_MENGE, menge);
             stuhl_data.putExtra(EXTRA_NOTIZ, notizen);

             int id = getIntent().getIntExtra(EXTRA_ID, -1);
             if(id != -1){
                 stuhl_data.putExtra(EXTRA_ID, id);
             }
             setResult(RESULT_OK, stuhl_data);
         }
         finish();
    }

    //Methode wird automatisch aufgerufen von startActivityForResult()
    //Bild wird gemacht und in ImageView gespeichert und angezeigt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
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

    //Bristol-Spinner wird mit Text und Bild bef??llt
    private void initListBristol(){
        arrayList_bristol = new ArrayList<>();
        arrayList_bristol.add(new BristolItem("einzelne, fest K??gelchen, schwer auszuscheiden", R.drawable.type01));
        arrayList_bristol.add(new BristolItem("wurstartig, klumpig", R.drawable.type02));
        arrayList_bristol.add(new BristolItem("wurstartig mit rissiger Oberfl??che", R.drawable.type03));
        arrayList_bristol.add(new BristolItem("wurstartig mit glatter, durchgehender Oberfl??che", R.drawable.type04));
        arrayList_bristol.add(new BristolItem("einzelne weiche glattrandige Kl??mpchen, leicht auszuscheiden", R.drawable.type05));
        arrayList_bristol.add(new BristolItem("einzelne weiche Kl??mpchen mit unregelm????igem Rand", R.drawable.type06));
        arrayList_bristol.add(new BristolItem("fl??ssig, ohne feste Bestandteile", R.drawable.type07));
    }

    private void savePicture(){
        //BitmapDrawable mit dem ImageView des Bildes wird erstellt
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView_stuhl.getDrawable();
        //BitmapDrawable wird in Bitmap gespeichert
        if(bitmapDrawable != null){
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



