package com.example.abschlussprojekt_eddi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Eintrag_Essen extends AppCompatActivity {

    EditText etEssen;
    Button btSpeicher;

    public static final String EXTRA_ESSEN =
            "com.example.abschlussprojekt_eddi.EXTRA_ESSEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eintrag__essen);

        etEssen = findViewById(R.id.editText_eintrag_essen);
        btSpeicher = findViewById(R.id.button_essen_speichern);

        btSpeicher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(etEssen.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                    Toast.makeText(Eintrag_Essen.this, "Bitte Essen eintragen.", Toast.LENGTH_SHORT).show();
                } else {
                    String essen = etEssen.getText().toString();
                    replyIntent.putExtra(EXTRA_ESSEN, essen);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}