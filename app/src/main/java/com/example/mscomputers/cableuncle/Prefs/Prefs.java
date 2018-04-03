package com.example.mscomputers.cableuncle.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by s on 5/12/2016.
 */
public class Prefs {

    final String PREF_NAME = "myPrefs";
    final String PREF_DAMAGE_HEADER = "damageHeader";
    final String PREF_SERVICE_HEADER = "serviceHeader";
    final String PREF_GOOGLE_DRIVE= "isGoogleDrive";
    final String PREF_DRIVE_EMAIL = "isDriveEmail";
    final String PREF_DRIVE_SMS= "isDriveSms";
    final String PREF_EMAIL= "email";
    final String PREF_IS_VIDEO_DAMAGE = "isVideoCompulsory";
   // final String PREFS_TEMP_SYNC = "isTempSync";


    public String damageHeader = "";
    public boolean isVideoCompulsory = false;
    public String serviceHeader = "";
    public boolean isGoogleDrive=false;
    public boolean isDriveEmail=false;
    public boolean isDriveSms=false;
    public String email = "";
    //public boolean isTempSync = false;

    static Prefs _instance = new Prefs();

    public static Prefs getInstance() {
        return _instance;
    }

    public void loadPrefs(Context ctx) {

        SharedPreferences p = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        damageHeader = p.getString(PREF_DAMAGE_HEADER, damageHeader);
        isVideoCompulsory = p.getBoolean(PREF_IS_VIDEO_DAMAGE, false);
        serviceHeader = p.getString(PREF_SERVICE_HEADER, serviceHeader);
        isGoogleDrive = p.getBoolean(PREF_GOOGLE_DRIVE, false);
        isDriveEmail = p.getBoolean(PREF_DRIVE_EMAIL, false);
        isDriveSms = p.getBoolean(PREF_DRIVE_SMS, false);
        email = p.getString(PREF_EMAIL, email);
      //  isTempSync = p.getBoolean(PREFS_TEMP_SYNC , isTempSync);
    }

    public boolean savePrefs(Context ctx) {

        SharedPreferences.Editor e = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();

        e.putString(PREF_DAMAGE_HEADER, damageHeader);
        e.putString(PREF_EMAIL, email);
        e.putBoolean(PREF_IS_VIDEO_DAMAGE, isVideoCompulsory);
        e.putBoolean(PREF_GOOGLE_DRIVE, isGoogleDrive);
        e.putBoolean(PREF_DRIVE_EMAIL, isDriveEmail);
        e.putBoolean(PREF_DRIVE_SMS, isDriveSms);

        e.putString(PREF_SERVICE_HEADER, serviceHeader);
      //  e.putBoolean(PREFS_TEMP_SYNC , isTempSync);
        return e.commit();
    }
}
