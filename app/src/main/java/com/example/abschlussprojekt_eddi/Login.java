package com.example.abschlussprojekt_eddi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;
import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    Context context = this;
    ImageButton button_fingerprint;
    Button button_login;
    Button button_registrieren;
    Intent intent_main;
    Intent intent_registrieren;

    EditText etNutzername;
    EditText etPin;
    BenutzerdatenSpeicher bdSp;
    Benutzer ben;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNutzername = findViewById(R.id.editText_nutzername);
        etPin = findViewById(R.id.editText_pw);
        bdSp = new BenutzerdatenSpeicher(this);

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

                if (verifyLogin(etNutzername, etPin)){
                    intent_main = new Intent(Login.this, MainActivity.class);
                    startActivity(intent_main);
                    Toast t = Toast.makeText(this, "Login erfolgreich", Toast.LENGTH_SHORT);
                    t.show();
                    break;
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Zugangsdaten vergessen?")
                            .setMessage("Wenn sie ein neues Profil anlegen werden die bisherigen" +
                                    " Einträge gelöscht")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteStuhlDatabase();
                                    deleteEssenDatabase();
                                    intent_registrieren = new Intent(Login.this, Anmeldung.class);
                                    startActivity(intent_registrieren);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    break;
                }

            case R.id.button_registrieren:
                intent_registrieren = new Intent(Login.this, Anmeldung.class);
                startActivity(intent_registrieren);
                break;
        }
    }

    // holt sich Pin aus shared preferences und vergleicht die Eingabe damit,
    // wenn Pin und/oder Nutzername falsch eingegeben wird, kann der Nutzer neues Profil anlegen -
    // Datenbankreinträge werden aber aus Sicherheitsgründen gelöscht
    public boolean verifyLogin (EditText name, EditText pin) {
        boolean login = false;
        ben = bdSp.getLoggedInUser();
        String benName = ben.getNutzername();
        String benPin = ben.getPin();
            if( (name.getText().toString().equals(benName)) && (pin.getText().toString().equals(benPin)) ){
                login = true;
            } else {
                etPin = null;
                etNutzername = null;
                Toast toast = Toast.makeText(this, "Falsche Zugangsdaten!",  Toast.LENGTH_SHORT);
                toast.show();
            }
        return login;
    }

    public void deleteStuhlDatabase (){
        ViewModel_Stuhl viewModel_stuhl = new ViewModelProvider((ViewModelStoreOwner) context).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(this, new Observer<List<Entity_Stuhl>>() {
            @Override
            public void onChanged(List<Entity_Stuhl> entity_stuhls) {
                for (Entity_Stuhl entity_stuhl : entity_stuhls){
                    viewModel_stuhl.delete(entity_stuhl);
                }
            }
        });
    }

    public void deleteEssenDatabase () {
        ViewModel_Essen viewModel_essen = new ViewModelProvider((ViewModelStoreOwner) context).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(this, new Observer<List<Entity_Essen>>() {
            @Override
            public void onChanged(List<Entity_Essen> entity_essens) {
                for (Entity_Essen entity_essen  : entity_essens){
                    viewModel_essen.deleteEssen(entity_essen);
                }
            }
        });
    }
}