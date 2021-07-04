package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;

public class Eintrag_Essen extends AppCompatActivity {

    EditText etEssen;
    Button btSpeicher;
    EditText editText_currentDate;
    EditText editText_currentTime;

    public static final String EXTRA_ESSEN =
            "com.example.abschlussprojekt_eddi.EXTRA_ESSEN";
    public static final String EXTRA_DATUM =
            "com.example.abschlussprojekt_eddi.EXTRA_DATUM";
    public static final String EXTRA_UHRZEIT =
            "com.example.abschlussprojekt_eddi.EXTRA_UHRZEIT";
    public static final String EXTRA_ESSEN_ID =
            "com.example.abschlussprojekt_eddi.EXTRA_ESSEN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eintrag__essen);

        etEssen = findViewById(R.id.editText_eintrag_essen);
        btSpeicher = findViewById(R.id.button_essen_speichern);

        //Momentanes Datum anzeigen (Werte als int gespeichert)
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //für LocalDate braucht es min API 26
            currentMonth = LocalDate.now().getMonthValue();
        }else {
            currentMonth = Calendar.MONTH + 1;
        }
        int currentYear = calendar.get(Calendar.YEAR);
        editText_currentDate = findViewById(R.id.editText_currentDate);
        editText_currentDate.setText(currentDay + "." + currentMonth + "." + currentYear);

        //Momentane Zeit anzeigen (Werte als int gespeichert)
        //brauche wir sekunden? Userbility?
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        editText_currentTime = findViewById(R.id.editText_currentTime);
        editText_currentTime.setText(currentHour + ":" + currentMinute);

        Intent intent = getIntent();
        //wenn der Eintrag bereits eine ID hat, wird er aktualisiert
        //und daher wird der gespeicherte Text übergeben
        if(intent.hasExtra(EXTRA_ESSEN_ID)){
            etEssen.setText(intent.getStringExtra(EXTRA_ESSEN)); //funktioniert
            editText_currentDate.getText(); //gibt nichts aus. Warum??
            editText_currentTime.getText(); //gibt akutelles Datum aus
        }


        btSpeicher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(etEssen.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                    Toast.makeText(Eintrag_Essen.this, "Bitte Essen eintragen.", Toast.LENGTH_SHORT).show();
                } else {
                    String essen = etEssen.getText().toString();
                    String datum = editText_currentDate.getText().toString();
                    String uhrzeit = editText_currentTime.getText().toString();

                    int id = getIntent().getIntExtra(EXTRA_ESSEN_ID, -1);
                    if(id != -1){
                        replyIntent.putExtra(EXTRA_ESSEN_ID, id);
                    }
                    replyIntent.putExtra(EXTRA_ESSEN, essen);
                    replyIntent.putExtra(EXTRA_DATUM, datum);
                    replyIntent.putExtra(EXTRA_UHRZEIT, uhrzeit);

                    setResult(RESULT_OK, replyIntent);

                }
                finish();

            }
        });
    }
}