package com.example.abschlussprojekt_eddi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    Context context = this;
    ImageButton button_fingerprint;
    Button button_login;
    Button button_registrieren;
    Intent intent_main;
    Intent intent_registrieren;
    BenutzerdatenSpeicher bdSp;
    EditText nutzername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BiometricManager biometricManager = BiometricManager.from(context);
        switch(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) { //BIOMETRIC_STRONG; BIOMETRIC_WEAK; DEVICE_CREDENTIAL achtung auf API
            case BiometricManager.BIOMETRIC_SUCCESS: //Biometrischer Sensor kann verwendet werden
                Log.i("BiometricManager","Fingerprint kann verwendet werden");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE: //Gerät hat keinen Fingerprint-Sensor
                Log.e("BiometricManager", "Error, no hardware / Kein Sensor vorhanden");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("BiometricManager", "Error, hw unavailable / Sensor gerade nicht verfügbar");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e("BiometricManager","Error, none enrolled / Keine Finerprints am Gerät hinterlegt");
                break;
        }

        //Executor
        Executor executor = ContextCompat.getMainExecutor(context);

        //Biometric Prompt Callback liefert das Ergebnis der Authentifizierung zurück, ob man sich einloggen kann oder nicht
        BiometricPrompt biometricPrompt = new BiometricPrompt(Login.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override //wenn die Authentifizierung erfolgreich ist, wird die Startseite geöffnet
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(context, "Login erfolgreich", Toast.LENGTH_SHORT).show();
                intent_main = new Intent(Login.this, MainActivity.class);
                startActivity(intent_main);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        //Biometric Dialog
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Finger auf den Sensor legen")
                .setNegativeButtonText("Abbrechen")
                .build();

        //Fingerprint Authentifizierung beim Klicken auf den Button
        //soll später automatisch aufscheinen
        button_fingerprint = findViewById(R.id.button_login_fingerprint);
        button_fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

        button_login = findViewById(R.id.button_login_anmelden);
        button_registrieren = findViewById(R.id.button_registrieren);

        button_login.setOnClickListener(this::onClick);
        button_registrieren.setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_login_anmelden:
                intent_main = new Intent(Login.this, MainActivity.class);
                startActivity(intent_main);
                break;
            case R.id.button_registrieren:
                intent_registrieren = new Intent(Login.this, Anmeldung.class);
                startActivity(intent_registrieren);
                break;
        }
    }

}