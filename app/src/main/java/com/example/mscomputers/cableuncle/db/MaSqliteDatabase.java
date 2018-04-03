package com.example.mscomputers.cableuncle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 7/25/2016.
 */
public class MaSqliteDatabase extends SQLiteOpenHelper {

    public static final String TEMPLATE_TABLE_NAME = "template";
    public static final String DAMAGE_REPORT_TABLE_NAME = "damage_report";
    public static final String SERVICE_REPORT_TABLE_NAME = "service_report";
    public static final String DAMAGE_REPORT_COORDINATE_TABLE_NAME = "damage_report_coordinate";


    public static final String KEY_TEMPLATE_ID = "tempId";
    public static final String KEY_TEMPLATE_NAME = "tempName";
    public static final String KEY_TEMPLATE_FILE = "tempFile";

    public static final String KEY_DAMAGE_REPORT_ID = "damId";
    public static final String KEY_DAMAGE_REPORT_REG_NO = "damRegNo";
    public static final String KEY_DAMAGE_REPORT_MILEAGE = "damMileage";
    public static final String KEY_DAMAGE_REPORT_PERSON_NAME = "name";
    public static final String KEY_DAMAGE_REPORT_DATE_TIME = "damDateTime";
    public static final String KEY_DAMAGE_REPORT_VIDEO_LINK = "damVideoLInk";
    public static final String VEHICLE_TYPE = "vehicleType";

    public static final String KEY_SERVICE_REPORT_ID = "serId";
    public static final String KEY_SERVICE_REPORT_FILE = "serFile";


    public static final String KEY_DAMAGE_REPORT_COORDINATE_DAMAGE_TYPE = "damageType";
    public static final String KEY_DAMAGE_REPORT_COORDINATE_X_AXIS = "xAxis";
    public static final String KEY_DAMAGE_REPORT_COORDINATE_Y_AXIS = "yAxis";


    public static final String CREATE_TEMPLATE_TABLE = "CREATE TABLE "
            + TEMPLATE_TABLE_NAME + " (" + KEY_TEMPLATE_ID
            + " TEXT NULL, " + KEY_TEMPLATE_NAME
            + " TEXT NULL, " + KEY_TEMPLATE_FILE + " TEXT NULL); ";

    public static final String CREATE_DAMAGE_REPORT = "CREATE TABLE "
            + DAMAGE_REPORT_TABLE_NAME + " (" + KEY_DAMAGE_REPORT_ID
            + " INTEGER PRIMARY KEY NOT NULL, " + KEY_DAMAGE_REPORT_REG_NO
            + " TEXT NULL, " + KEY_DAMAGE_REPORT_MILEAGE
            + " integer, " + KEY_DAMAGE_REPORT_PERSON_NAME
            + " TEXT NULL, " + KEY_DAMAGE_REPORT_DATE_TIME + " TEXT NULL, " + VEHICLE_TYPE + " TEXT NULL, " + KEY_DAMAGE_REPORT_VIDEO_LINK + " TEXT NULL); ";

    public static final String CREATE_SERVICE_REPORT = "CREATE TABLE "
            + SERVICE_REPORT_TABLE_NAME + " (" + KEY_SERVICE_REPORT_ID
            + " INTEGER PRIMARY KEY NOT NULL, " + KEY_SERVICE_REPORT_FILE
            + " TEXT NULL); ";

    public static final String CREATE_DAMAGE_REPORT_COORDINATE = "CREATE TABLE "
            + DAMAGE_REPORT_COORDINATE_TABLE_NAME + " (" + KEY_DAMAGE_REPORT_ID
            + " INTEGER, " + KEY_DAMAGE_REPORT_COORDINATE_DAMAGE_TYPE
            + " TEXT NULL, " + KEY_DAMAGE_REPORT_COORDINATE_X_AXIS
            + " integer, " + KEY_DAMAGE_REPORT_COORDINATE_Y_AXIS
            + " integer); ";

    public MaSqliteDatabase(Context context, String name) {
        super(context, name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TEMPLATE_TABLE);
        db.execSQL(CREATE_DAMAGE_REPORT);
        db.execSQL(CREATE_SERVICE_REPORT);
        db.execSQL(CREATE_DAMAGE_REPORT_COORDINATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TEMPLATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_DAMAGE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_SERVICE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_DAMAGE_REPORT_COORDINATE);
    }
}
