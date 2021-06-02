package com.example.abschlussprojekt_eddi;

import android.content.Context;
import android.content.SharedPreferences;

public class StuhlLogbuchWriter {

    public static final String SP_Name = "Stuhllogbuch";
    public static final String STIMMUNG = "Stimmung";
    SharedPreferences sp;

    public StuhlLogbuchWriter (Context context){
        sp = context.getSharedPreferences(SP_Name, Context.MODE_PRIVATE);
    }

    public void writeInLogbuch (Logbuch_Stuhl logStuhl) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(STIMMUNG, logStuhl.getStimmung());
    }

    public Logbuch_Stuhl getLogbuchEntry(){
        String stimmung = sp.getString("stimmung", "");

        return new Logbuch_Stuhl(stimmung);
    }
}
