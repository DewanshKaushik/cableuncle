package com.example.mscomputers.cableuncle.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mmsl.DeviceUtility.DeviceBluetoothCommunication;

/**
 * Created by User on 1/20/2017.
 */
public class Util {

    public static boolean isValidString(String s) {
        return s != null && !s.equals("");
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Bitmap getBitmapOfGallaryImage(Activity act, Uri selectedImage) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 2;
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = act.getContentResolver().openAssetFileDescriptor(selectedImage, "r");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                options.inJustDecodeBounds = true;
                options.inSampleSize = 5;
                BitmapFactory.decodeFileDescriptor(
                        fileDescriptor.getFileDescriptor(), null, options);

                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFileDescriptor(
                        fileDescriptor.getFileDescriptor(), null, options);
                fileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bm;
    }

    public static String setImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64String = Base64.encodeToString(byteArray, Base64.DEFAULT);


        try {
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            byteArrayOutputStream = null;
            byteArray = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return base64String;
    }

    public static boolean isNetworkAvailable(Context act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static ArrayList<String> getSeparatedUris(String ids) {
        String[] batchesCatSubCat = ids.split(",");
        ArrayList<String> separatedUris = new ArrayList<>();

        for (int i = 0; i < batchesCatSubCat.length; i++) {
            // String myUri = Uri.parse(batchesCatSubCat[i]);
            String myUri = batchesCatSubCat[i];
            separatedUris.add(myUri);

        }
        return separatedUris;
    }


    public static Bitmap convertFilePathtoBitmap(String filePath) {
        File image = new File(filePath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 5;
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true);
        return bitmap;

    }

    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static boolean checkPermission(Context ctx) {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(ctx, Manifest.permission.RECORD_AUDIO);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED
                && FourthPermissionResult == PackageManager.PERMISSION_GRANTED
                && FifthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isLocationEnabled(LocationManager locationManager) {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


/*
    public String getDate() {

        try {
            String dateStr = "04/05/2010";

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = curFormater.parse(dateStr);
            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

            String newDateStr = postFormater.format(dateObj);
        } catch (Exception e) {

        }

    }
*/

    public static String getReceiptNO() {
        String date = null;
        //getting current date and time using Date lass
        try {
            DateFormat df = new SimpleDateFormat("dd_MM_yy_hh_mm_ss");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));

        /*getting current date time using calendar class
        * An Alternative of above*/

            Calendar calobj = Calendar.getInstance();
            System.out.println(df.format(calobj.getTime()));

            date = df.format(dateobj);
            String time = df.format(df.format(calobj.getTime()));
        } catch (Exception e) {
            System.out.println();
        }
        return date;
    }

    public static String getDateandTime() {
        String date = null;
        //getting current date and time using Date lass
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yy hh:mm:ss a");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));

        /*getting current date time using calendar class
        * An Alternative of above*/

            Calendar calobj = Calendar.getInstance();
            System.out.println(df.format(calobj.getTime()));

            date = df.format(dateobj);
            String time = df.format(df.format(calobj.getTime()));
        } catch (Exception e) {
            System.out.println();
        }
        return date;
    }

    public static double getRoundDigit(double a){
        double roundOff = Math.round(a * 100.0) / 100.0;
        return roundOff;
    }
    public static String cutStringsecond(String string) {
        String finalString = "";
        if (string.length() > 30 && string.length() < 60) {
            finalString = string.substring(0, 30) + "\n" + string.substring(30, string.length());
        } else if (string.length() > 60 && string.length() < 90) {
            finalString = string.substring(0, 30) + "\n" + string.substring(30, 60) + "\n" + string.substring(60, string.length());
        } else if (string.length() > 90 && string.length() < 120) {
            finalString = string.substring(0, 30) + "\n" + string.substring(30, 60) + "\n" + string.substring(60, 90) + "\n" + string.substring(90, string.length());
        } else {
            return string;
        }
        return finalString;
    }

    public static void printMaestroLine(DeviceBluetoothCommunication deviceBluetoothCommunication, String string){
        deviceBluetoothCommunication.SendData(string.getBytes());
        deviceBluetoothCommunication.LineFeed();
    }

}
