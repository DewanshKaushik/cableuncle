package com.example.mscomputers.cableuncle.maestro.microatm;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.maestro.microatm.utils.DirectoryChooserDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import mmsl.DeviceUtility.BluetoothLibrary.CommandReceiveThread;
import mmsl.DeviceUtility.CardType;
import mmsl.DeviceUtility.DeviceBluetoothCommunication;
import mmsl.DeviceUtility.DeviceCallBacks;
import mmsl.DeviceUtility.TemplateType;
import mmsl.GetPrintableImage.GetPrintableImage;

// import metsl.microatm.utils.DirectoryChooserDialog;

public class AccordionWidgetDemoActivity extends Activity implements
        DeviceCallBacks {
    int Image_TYPE = 1;
    static int[] monahex = {0x1B, 0x23, 0x30, 0x00, 0xAB,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3C, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1C, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xF0, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xF8, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xFC, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x01,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x01,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x01,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x03,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x07,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x0F,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xF8, 0x00, 0x5F,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFC, 0x00, 0xFF,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF8, 0x0F, 0xFF,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x1F, 0xFF,
            0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xF3, 0xFF, 0xFE,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x15, 0x54, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x54, 0x00, 0x00, 0x00, 0x00, 0x15, 0x40, 0x00, 0x00, 0x00, 0x07, 0xF7, 0xFF, 0xFE,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x28, 0x00, 0x00, 0x00, 0x00, 0x02, 0xA8, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x00,
            0x00, 0x03, 0xFC, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xE0, 0x00, 0x00, 0x00, 0x0F, 0xFF, 0xFF, 0xF8,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x07, 0xFE, 0x00,
            0x00, 0x00, 0x00, 0xC0, 0x00, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x03, 0xFF, 0xFF, 0x80, 0x00, 0x00,
            0x00, 0x1F, 0xFF, 0x80, 0x00, 0x00, 0x03, 0xFF, 0xFF, 0x80, 0x00, 0x00, 0x3F, 0xFF, 0xFF, 0xF0,
            0x10, 0x00, 0x00, 0x03, 0x80, 0x00, 0x00, 0x1F, 0xFF, 0xE0, 0x00, 0x00, 0x00, 0x3F, 0xFF, 0xE0,
            0x00, 0x00, 0x00, 0xE0, 0x00, 0x00, 0x1E, 0x00, 0x00, 0x00, 0x07, 0xFF, 0xFF, 0xC0, 0x00, 0x00,
            0x00, 0x3F, 0xFF, 0xC0, 0x00, 0x00, 0x07, 0xFF, 0xFF, 0xC0, 0x00, 0x00, 0x3F, 0xFF, 0xFF, 0xF0,
            0x38, 0x00, 0x00, 0x03, 0x80, 0x00, 0x00, 0x1F, 0xFF, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xFF, 0xF0,
            0x00, 0xC0, 0x0F, 0xFF, 0x00, 0x03, 0xFF, 0xC0, 0x00, 0x00, 0x3F, 0xFF, 0xFF, 0xE0, 0x00, 0x00,
            0x01, 0xFF, 0xFF, 0xE0, 0x00, 0x00, 0x0F, 0xFF, 0xFF, 0xC0, 0x00, 0x07, 0xFF, 0xFF, 0xFE, 0x00,
            0x70, 0x00, 0xC0, 0x1F, 0xE0, 0x00, 0x00, 0xFF, 0xFF, 0xFE, 0x00, 0x00, 0x03, 0xFF, 0xFF, 0xFC,
            0x00, 0xC0, 0x0F, 0xFF, 0x80, 0x03, 0xFF, 0xE0, 0x00, 0x00, 0x3F, 0xFF, 0xFF, 0xF0, 0x00, 0x00,
            0x01, 0xFF, 0xFF, 0xF0, 0x00, 0x00, 0x1F, 0xFF, 0xFF, 0xC0, 0x00, 0x0F, 0xFF, 0xFF, 0xFC, 0x00,
            0x70, 0x00, 0xC0, 0x3F, 0xE0, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0x00, 0x00, 0x03, 0xFF, 0xFF, 0xFC,
            0x1F, 0xC0, 0x3F, 0xFF, 0xC0, 0x0F, 0xFF, 0xE0, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xF8, 0x00, 0x00,
            0x07, 0xFF, 0xFF, 0xF8, 0x00, 0x00, 0x3F, 0xFF, 0xFF, 0xC0, 0x00, 0xFF, 0xFF, 0xFF, 0x60, 0x01,
            0xF0, 0x1F, 0xF0, 0x7F, 0xF8, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0x80, 0x00, 0x0F, 0xFF, 0xFF, 0xFC,
            0xBF, 0xE0, 0xBF, 0xFF, 0xC0, 0x0F, 0xFF, 0xF8, 0x00, 0x00, 0xFF, 0xF5, 0x7F, 0xFC, 0x00, 0x00,
            0x07, 0xFE, 0xBF, 0xFE, 0x00, 0x00, 0x7F, 0xFD, 0x7F, 0xC0, 0x05, 0xFF, 0xFF, 0xFA, 0x20, 0x2B,
            0xF0, 0x3F, 0xF0, 0xFF, 0xF8, 0x00, 0x05, 0xFF, 0x7F, 0xFF, 0xE0, 0x00, 0x1F, 0xFF, 0x5F, 0xFC,
            0xFF, 0xF1, 0xFF, 0xFF, 0xC0, 0x3F, 0xFF, 0xFC, 0x00, 0x03, 0xFF, 0xE0, 0x7F, 0xF8, 0x00, 0x00,
            0x0F, 0xFC, 0x1F, 0xFE, 0x00, 0x00, 0xFF, 0xF0, 0x7F, 0xC0, 0x0F, 0xFF, 0xFF, 0xF0, 0x00, 0x3F,
            0xF0, 0x7F, 0xE1, 0xFF, 0xF8, 0x00, 0x0F, 0xFF, 0x3F, 0xFF, 0xE0, 0x00, 0x3F, 0xFE, 0x0F, 0xFC,
            0x7F, 0xF3, 0xFF, 0xFF, 0xE0, 0x7F, 0xFF, 0xF8, 0x00, 0x01, 0xFF, 0x80, 0x0F, 0xF8, 0x00, 0x00,
            0x3F, 0xF8, 0x03, 0xFF, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xC0, 0x1F, 0xFF, 0xFF, 0xF0, 0x01, 0xFF,
            0xF0, 0x1F, 0xF1, 0xFF, 0xFE, 0x00, 0x0F, 0xF8, 0x01, 0xFF, 0xE0, 0x00, 0x1F, 0xF0, 0x01, 0xFC,
            0x7F, 0xE3, 0xFF, 0xFF, 0xF0, 0x7F, 0xFF, 0xF8, 0x00, 0x03, 0xFF, 0x80, 0x07, 0xF8, 0x00, 0x00,
            0x3F, 0xF0, 0x01, 0xFF, 0x80, 0x00, 0xFF, 0x80, 0x07, 0xC0, 0x3F, 0xFF, 0xFF, 0xF0, 0x03, 0xFF,
            0xF0, 0x1F, 0xF1, 0xFF, 0xFC, 0x00, 0x0F, 0xF0, 0x01, 0xFF, 0xE0, 0x00, 0x3F, 0xE0, 0x01, 0xFC,
            0x3F, 0xC7, 0xFF, 0xFF, 0xF1, 0xFF, 0xFF, 0xFE, 0x00, 0x07, 0xFE, 0x00, 0x07, 0xFE, 0x00, 0x00,
            0x3F, 0xE0, 0x00, 0xFF, 0x80, 0x01, 0xFE, 0x00, 0x03, 0xC1, 0xFF, 0xFF, 0x1F, 0xF0, 0xFF, 0xFF,
            0xE0, 0x1F, 0xE3, 0xFF, 0xF8, 0x00, 0x1F, 0xC0, 0x00, 0x7F, 0xF8, 0x00, 0x1F, 0xE0, 0x00, 0x70,
            0x7F, 0xD7, 0xFF, 0xFF, 0xF1, 0xFF, 0xFF, 0xFE, 0x00, 0x07, 0xFE, 0x00, 0x07, 0xFE, 0x00, 0x00,
            0x7F, 0x80, 0x00, 0xFF, 0x80, 0x03, 0xFE, 0x00, 0x01, 0xC1, 0xFF, 0xFD, 0x0F, 0xFB, 0xFF, 0xFF,
            0xC0, 0x1F, 0xF7, 0xFF, 0xF8, 0x00, 0x7F, 0x80, 0x00, 0x7F, 0xF8, 0x00, 0x7F, 0xE0, 0x00, 0x78,
            0x3F, 0xFF, 0xCC, 0xFF, 0xF9, 0xFC, 0x9F, 0xFE, 0x00, 0x07, 0xFE, 0x00, 0x01, 0xFE, 0x00, 0x00,
            0xFF, 0x80, 0x00, 0xFF, 0xE0, 0x01, 0xFE, 0x00, 0x00, 0xC1, 0xFF, 0xF8, 0x1F, 0xFF, 0xFF, 0xFF,
            0x80, 0x1F, 0xFF, 0xFF, 0xF8, 0x00, 0x7F, 0xC0, 0x00, 0x3F, 0xFE, 0x00, 0x7F, 0x80, 0x00, 0x1C,
            0x7F, 0xFF, 0x80, 0x7F, 0xFF, 0xE0, 0x0F, 0xFE, 0x00, 0x07, 0xFC, 0x00, 0x03, 0xFE, 0x00, 0x00,
            0xFF, 0x80, 0x00, 0x7F, 0xC0, 0x07, 0xFC, 0x00, 0x00, 0xC3, 0xFF, 0x00, 0x0F, 0xFF, 0xFF, 0xFC,
            0x00, 0x1F, 0xFF, 0xED, 0xC0, 0x00, 0x7F, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x7F, 0x80, 0x00, 0x38,
            0x3F, 0xFF, 0x80, 0x7F, 0xFF, 0xC0, 0x07, 0xFE, 0x00, 0x07, 0xF8, 0x00, 0x01, 0xFE, 0x00, 0x00,
            0xFF, 0x80, 0x00, 0x3F, 0xE0, 0x07, 0xF8, 0x00, 0x00, 0xC7, 0xFF, 0x00, 0x0F, 0xFF, 0xFF, 0xF8,
            0x00, 0x1F, 0xFF, 0xCC, 0x80, 0x00, 0x7F, 0x00, 0x00, 0x0F, 0xFE, 0x00, 0x7F, 0xC0, 0x00, 0x30,
            0x1F, 0xFC, 0x00, 0x1F, 0xFF, 0x80, 0x03, 0xFE, 0x00, 0x1F, 0xF8, 0x00, 0x03, 0xFE, 0x00, 0x00,
            0xFE, 0x00, 0x00, 0x3F, 0xC0, 0x03, 0xFE, 0x00, 0x00, 0xC7, 0xF0, 0x00, 0xFF, 0xFF, 0xFF, 0xC0,
            0x00, 0x1F, 0xFE, 0x00, 0x00, 0x01, 0xFF, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x7F, 0x80, 0x00, 0x0C,
            0x1F, 0xF8, 0x00, 0x1F, 0xFF, 0x80, 0x01, 0xFE, 0x00, 0x1F, 0xFC, 0x00, 0x01, 0xFE, 0x00, 0x00,
            0xFE, 0x00, 0x00, 0x3F, 0xE0, 0x03, 0xFE, 0x00, 0x00, 0x43, 0xF0, 0x08, 0xFF, 0xFF, 0xFF, 0xC0,
            0x00, 0x1F, 0xFE, 0x00, 0x00, 0x01, 0xFE, 0x00, 0x00, 0x07, 0xFE, 0x00, 0x7F, 0xC0, 0x00, 0x0C,
            0x7F, 0xF8, 0x00, 0x1F, 0xFF, 0x80, 0x03, 0xFF, 0x80, 0x07, 0xF8, 0x00, 0x03, 0xFE, 0x00, 0x01,
            0xFE, 0x00, 0x00, 0x3F, 0xC0, 0x07, 0xFE, 0x00, 0x00, 0x07, 0x80, 0x1F, 0xFF, 0xFF, 0xF8, 0x00,
            0x00, 0x1F, 0xFC, 0x00, 0x00, 0x01, 0xFC, 0x00, 0x00, 0x03, 0xFF, 0x00, 0x7F, 0x80, 0x00, 0x00,
            0x1F, 0xFC, 0x00, 0x0F, 0xFF, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xF8, 0x00, 0x01, 0xFE, 0x00, 0x03,
            0xFE, 0x00, 0x00, 0x3F, 0xF0, 0x03, 0xFC, 0x00, 0x00, 0x03, 0x80, 0xBF, 0xFF, 0xFD, 0x50, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x01, 0xFC, 0x00, 0x00, 0x03, 0xFF, 0x00, 0x7F, 0xC0, 0x00, 0x00,
            0x1F, 0xF8, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xF0, 0x00, 0x03, 0xFE, 0x00, 0x01,
            0xFE, 0x00, 0x00, 0x3F, 0xF0, 0x01, 0xF8, 0x00, 0x00, 0x07, 0x80, 0xFF, 0xFF, 0xFC, 0x00, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x01, 0xFC, 0x00, 0x00, 0x03, 0xFF, 0x00, 0x7F, 0x80, 0x00, 0x00,
            0x3F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xF0, 0x00, 0x01, 0xFE, 0x00, 0x07,
            0xFE, 0x00, 0x00, 0x3F, 0xF0, 0x07, 0xFE, 0x00, 0x00, 0x07, 0x07, 0xFF, 0xFF, 0xF8, 0x00, 0x00,
            0x00, 0x1F, 0xFC, 0x00, 0x00, 0x03, 0xFC, 0x00, 0x00, 0x03, 0xFF, 0x00, 0x7F, 0xC0, 0x00, 0x00,
            0x3F, 0xE0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xE0, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFE, 0x00, 0x00, 0x3F, 0xF0, 0x07, 0xFE, 0x00, 0x00, 0x06, 0x0F, 0xFF, 0xFF, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x00, 0x03, 0xFF, 0x00, 0x7F, 0x80, 0x00, 0x00,
            0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x01, 0xFE, 0x00, 0x07,
            0xF8, 0x00, 0x00, 0x3F, 0xF0, 0x01, 0xFE, 0x00, 0x00, 0x00, 0x1F, 0xFF, 0xCF, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x00, 0x01, 0xFF, 0x00, 0x7F, 0x80, 0x00, 0x00,
            0x3F, 0xE0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFD, 0x40, 0x0A, 0xBF, 0xF0, 0x07, 0xFE, 0x00, 0x00, 0x00, 0x3F, 0xFE, 0x8F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xFC, 0x00, 0x00, 0x03, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0x80, 0x7F, 0xC0, 0x00, 0x00,
            0x7F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x01, 0xFE, 0x00, 0x07,
            0xFF, 0xC0, 0x1F, 0xFF, 0xF0, 0x07, 0xFF, 0x80, 0x00, 0x00, 0x1F, 0xFE, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x7F, 0xE0, 0x00, 0x00,
            0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFF, 0xFF, 0xFF, 0xFF, 0xE0, 0x03, 0xFF, 0x80, 0x00, 0x00, 0x7F, 0xC0, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x00, 0x01, 0xFF, 0xC0, 0x7F, 0xF0, 0x00, 0x00,
            0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x01, 0xFE, 0x00, 0x07,
            0xFF, 0xFF, 0xFF, 0xFF, 0xC0, 0x01, 0xFF, 0x80, 0x00, 0x00, 0x7F, 0x80, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x7F, 0xF8, 0x00, 0x00,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x03, 0xFE, 0x00, 0x1F,
            0xFF, 0xFF, 0xFF, 0xFF, 0xE0, 0x03, 0xFF, 0xC0, 0x00, 0x00, 0x7F, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x01, 0xFF, 0xC0, 0x7F, 0xF8, 0x00, 0x00,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x01, 0xFE, 0x00, 0x0F,
            0xFF, 0xFF, 0xFF, 0xFF, 0xC0, 0x01, 0xFF, 0xE0, 0x00, 0x00, 0x7E, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x7F, 0xFC, 0x00, 0x00,
            0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFF, 0xFF, 0xFF, 0xE6, 0x00, 0x03, 0xFF, 0xF0, 0x00, 0x00, 0x70, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x1F, 0xFC, 0x00, 0x00,
            0x3F, 0xE0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x03, 0xFE, 0x00, 0x0F,
            0xFE, 0xAA, 0xAA, 0xA0, 0x00, 0x00, 0xFF, 0xF0, 0x00, 0x00, 0x60, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x01, 0xFF, 0xC0, 0x3F, 0xFF, 0x00, 0x00,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x07, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xF0, 0x00, 0x00, 0xE0, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x1F, 0xFF, 0x00, 0x00,
            0x1F, 0xE0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x1F, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFE, 0x00, 0x00, 0x60, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x3F, 0xFF, 0xC0, 0x00,
            0x1F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x1F, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x40, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x1F, 0xFF, 0xC0, 0x00,
            0x3F, 0xF0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0x1F, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x0F, 0xFF, 0xF0, 0x00,
            0x3F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0xBF, 0xFE, 0x00, 0x1F,
            0xFC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F, 0xFF, 0x40, 0x00, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF8, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x07, 0xFF, 0xF0, 0x00,
            0x1F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x00, 0xFF, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xFF, 0xE0, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xFC, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0x3F, 0xC0, 0x03, 0xFF, 0xFC, 0x00,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x03, 0xFF, 0xFE, 0x00, 0x1F,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xFF, 0xF0, 0x00, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x01, 0xFF, 0xFC, 0x00,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x03, 0xFF, 0xFE, 0x00, 0x1F,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xFF, 0xF8, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x3F, 0xC0, 0x00, 0xFF, 0xFF, 0x00,
            0x1F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x0F, 0xFB, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xFC, 0x00, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x7F, 0xFF, 0x00,
            0x1F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x2F, 0xD1, 0xFE, 0x00, 0x1F,
            0xFC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xFE, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xE0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x3F, 0xFF, 0xC0,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x7F, 0xC3, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xFC, 0x00, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x1F, 0xFF, 0xC0,
            0x1F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x00, 0xFF, 0x01, 0xFE, 0x00, 0x1F,
            0xFC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x00, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x3F, 0xC0, 0x00, 0x0F, 0xFF, 0xF0,
            0x1F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x01, 0xFF, 0x03, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, 0xFF, 0x00, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x07, 0xFF, 0xF0,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x03, 0xF8, 0x01, 0xFE, 0x00, 0x1F,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xFF, 0xC0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x03, 0xFF, 0xF0,
            0x3F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x07, 0xF0, 0x03, 0xFE, 0x00, 0x1F,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xFF, 0xC0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x01, 0xFF, 0xF0,
            0x1F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x3F, 0xE0, 0x01, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xFF, 0xE0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x3F, 0xC0, 0x00, 0x00, 0xFF, 0xFC,
            0x3F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0x7F, 0x80, 0x03, 0xFE, 0x00, 0x1F,
            0xFC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xF0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xE0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x00, 0x00, 0x7F, 0xFC,
            0x7F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0xFF, 0x80, 0x01, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xF0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x00, 0x00, 0x3F, 0xFC,
            0x1F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x00, 0xFE, 0x00, 0x03, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF8, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x00, 0x1F, 0xFF,
            0x1F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x00, 0xFE, 0x00, 0x01, 0xFE, 0x00, 0x1F,
            0xFC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF8, 0x00, 0x00, 0x3F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0xC0, 0x00, 0x00, 0x0F, 0xFF,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x01, 0xFC, 0x00, 0x03, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, 0xFC, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x00, 0x00, 0x07, 0xFF,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x03, 0xF8, 0x00, 0x01, 0xFE, 0x00, 0x0F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xE0, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0xC0, 0x00, 0x00, 0x03, 0xFF,
            0x1F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xF8, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFC, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF0, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x7F, 0x00, 0x00, 0x00, 0x07, 0xFF,
            0x3F, 0xE0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xFC, 0x00, 0x01, 0xFE, 0x00, 0x0F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xE0, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x00, 0x00, 0x7F, 0x00, 0x00, 0x00, 0x07, 0xFF,
            0x7F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xF8, 0x00, 0x03, 0xFE, 0x00, 0x1F,
            0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x07, 0xFF,
            0x1F, 0xF0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF0, 0x00, 0x01, 0xFE, 0x00, 0x0F,
            0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF8, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x00, 0x01, 0xFF, 0x00, 0x00, 0x00, 0x01, 0xFF,
            0x3F, 0xE0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF0, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xF8, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x01, 0xFF,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xE0, 0x00, 0x01, 0xFE, 0x00, 0x07,
            0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x01, 0xFF,
            0x3F, 0xE0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF8, 0x00, 0x03, 0xFE, 0x00, 0x07,
            0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x0F, 0xE0, 0x00, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0x01, 0xFF, 0x00, 0x00, 0x00, 0x01, 0xFF,
            0x1F, 0xF0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xFC, 0x00, 0x01, 0xFE, 0x00, 0x07,
            0xFE, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF0, 0x00, 0x00, 0x0F, 0xFC, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x01, 0xFF,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x07,
            0xFF, 0x00, 0x00, 0x00, 0x40, 0x18, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x07, 0xFE, 0x00, 0x00, 0x00, 0xFE, 0x06, 0x00, 0x00, 0x01, 0xFF,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xE0, 0x00, 0x07, 0xFE, 0x00, 0x07,
            0xFF, 0x80, 0x00, 0x00, 0x60, 0x18, 0x00, 0x00, 0x07, 0xFC, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x07, 0xFF, 0x00, 0x00, 0x00, 0xFC, 0x06, 0x00, 0x00, 0x01, 0xFF,
            0x1F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x03,
            0xFF, 0xC0, 0x00, 0x00, 0x60, 0x18, 0x00, 0x00, 0x1F, 0xF8, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF8, 0x00, 0x00, 0x03, 0xFF, 0x00, 0x00, 0x03, 0xFE, 0x01, 0x80, 0x00, 0x01, 0xFF,
            0x1F, 0xE0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x01,
            0xFF, 0xE0, 0x00, 0x00, 0xE0, 0x18, 0x00, 0x00, 0x0F, 0xF8, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x07, 0xF8, 0x00, 0x00, 0x07, 0xFE, 0x00, 0x00, 0x03, 0xFC, 0x01, 0x80, 0x00, 0x01, 0xFF,
            0x7F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xFC, 0x00, 0x07, 0xFE, 0x00, 0x03,
            0xFF, 0xC0, 0x00, 0x01, 0xC0, 0x18, 0x00, 0x00, 0x07, 0xF0, 0x00, 0x00, 0x0F, 0xF0, 0x00, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x07, 0xFF, 0x00, 0x00, 0x03, 0xF8, 0x07, 0x80, 0x00, 0x01, 0xFF,
            0x1F, 0xE0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF8, 0x00, 0x0F, 0xFE, 0x00, 0x01,
            0xFF, 0xF0, 0x00, 0x03, 0xE0, 0x1C, 0x00, 0x00, 0x0F, 0xE0, 0x00, 0x00, 0x1F, 0xFC, 0x01, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x01, 0xFF, 0x80, 0x00, 0x03, 0xF8, 0x01, 0x80, 0x00, 0x01, 0xFE,
            0x1F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xF8, 0x00, 0x1F, 0xFE, 0x00, 0x03,
            0xFF, 0xF0, 0x00, 0x07, 0xC0, 0x1E, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x0F, 0xFC, 0x03, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x01, 0xFF, 0x80, 0x00, 0x03, 0xF8, 0x01, 0x80, 0x00, 0x01, 0xFC,
            0x3F, 0xC0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xFE, 0x00, 0x3F, 0xFE, 0x00, 0x00,
            0xFF, 0xFC, 0x00, 0x3F, 0xE0, 0x1F, 0x00, 0x00, 0x1F, 0xF0, 0x00, 0x00, 0x0F, 0xFC, 0x03, 0x80,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x01, 0xFF, 0x80, 0x00, 0x07, 0xF8, 0x01, 0xC0, 0x00, 0x03, 0xFC,
            0x3F, 0xC0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xFE, 0x00, 0x7F, 0xFE, 0x00, 0x00,
            0xFF, 0xFC, 0x00, 0x7F, 0xC0, 0x1F, 0x00, 0x00, 0x3F, 0xE0, 0x00, 0x00, 0x1F, 0xFC, 0x03, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x01, 0xFF, 0xC0, 0x00, 0x0F, 0xF0, 0x03, 0xE0, 0x00, 0x07, 0xFC,
            0x1F, 0xF0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x1F, 0xFF, 0x90, 0xFB, 0xFE, 0x00, 0x00,
            0xFF, 0xFF, 0xE1, 0xFF, 0x80, 0x1F, 0x00, 0x00, 0x3F, 0xC0, 0x00, 0x00, 0x0F, 0xFF, 0x87, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x00, 0x7F, 0xE0, 0x00, 0x0F, 0xE0, 0x03, 0xF0, 0x00, 0x03, 0xFC,
            0x3F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x0F, 0xFF, 0xBB, 0xF1, 0xFE, 0x00, 0x00,
            0x7F, 0xFF, 0xF7, 0xFF, 0x00, 0x1F, 0x80, 0x00, 0xFF, 0x80, 0x00, 0x00, 0x0F, 0xFF, 0xDF, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x00, 0x7F, 0xF0, 0x00, 0x1F, 0xC0, 0x07, 0xF0, 0x00, 0x0F, 0xF8,
            0x7F, 0xF0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xFF, 0xFF, 0xF9, 0xFE, 0x00, 0x00,
            0x3F, 0xFF, 0xFF, 0xFE, 0x00, 0x1F, 0xC0, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x07, 0xFF, 0xFF, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x00, 0x7F, 0xF8, 0x00, 0x1F, 0xC0, 0x07, 0xF0, 0x00, 0x0F, 0xF0,
            0x1F, 0xC0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xFF, 0xFF, 0xFB, 0xFF, 0x00, 0x00,
            0x1F, 0xFF, 0xFF, 0xFE, 0x00, 0x1F, 0xF8, 0x03, 0xFF, 0x00, 0x00, 0x00, 0x03, 0xFF, 0xFF, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x3F, 0xFF, 0x01, 0xFF, 0x80, 0x03, 0xFE, 0x00, 0xFF, 0xE0,
            0x1F, 0xC0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x07, 0xFF, 0xFF, 0xF9, 0xFF, 0x80, 0x00,
            0x0F, 0xFF, 0xFF, 0xFE, 0x00, 0x1F, 0xFC, 0x03, 0xFF, 0x00, 0x00, 0x00, 0x07, 0xFF, 0xFF, 0x00,
            0x00, 0x1F, 0xE0, 0x00, 0x00, 0x00, 0x1F, 0xFF, 0x81, 0xFF, 0x80, 0x07, 0xFF, 0x00, 0xFF, 0xC0,
            0x7F, 0xE0, 0x00, 0x07, 0xF8, 0x00, 0x00, 0xFF, 0x80, 0x03, 0xFF, 0xFF, 0xE0, 0xFF, 0x80, 0x00,
            0x1F, 0xFF, 0xFF, 0xF8, 0x00, 0x1F, 0xFF, 0xFF, 0xFE, 0x00, 0x00, 0x00, 0x07, 0xFF, 0xFE, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x0F, 0xFF, 0xFF, 0xFF, 0x00, 0x07, 0xFF, 0xFF, 0xFF, 0x80,
            0x3F, 0xF0, 0x00, 0x07, 0xFC, 0x00, 0x00, 0xFF, 0x80, 0x01, 0xFF, 0xFF, 0xE0, 0xFF, 0x80, 0x00,
            0x07, 0xFF, 0xFF, 0xF8, 0x00, 0x0F, 0xFF, 0xFF, 0xFC, 0x00, 0x00, 0x00, 0x03, 0xFF, 0xFC, 0x00,
            0x00, 0x1F, 0xF0, 0x00, 0x00, 0x00, 0x07, 0xFF, 0xFF, 0xFE, 0x00, 0x03, 0xFF, 0xFF, 0xFF, 0x80,
            0x7F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0x80, 0x03, 0xFF, 0xFF, 0x83, 0xFF, 0x80, 0x00,
            0x07, 0xFF, 0xFF, 0xE0, 0x00, 0x07, 0xFF, 0xFF, 0xF8, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xFE, 0x00,
            0x00, 0x1F, 0xFC, 0x00, 0x00, 0x00, 0x03, 0xFF, 0xFF, 0xFC, 0x00, 0x01, 0xFF, 0xFF, 0xFF, 0x80,
            0x3F, 0xE0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0xC0, 0x00, 0x3F, 0xFF, 0x00, 0xFF, 0xC0, 0x00,
            0x01, 0xFF, 0xFF, 0xE0, 0x00, 0x03, 0xFF, 0xFF, 0xF0, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xF8, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xFF, 0xE0, 0x00, 0x00, 0xFF, 0xFF, 0xFC, 0x00,
            0x7F, 0xF0, 0x00, 0x07, 0xFE, 0x00, 0x00, 0xFF, 0xC0, 0x00, 0x3F, 0xFF, 0x00, 0xFF, 0xE0, 0x00,
            0x01, 0xFF, 0xFF, 0xC0, 0x00, 0x01, 0xFF, 0xFF, 0xE0, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xF8, 0x00,
            0x00, 0x1F, 0xF8, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xC0, 0x00, 0x00, 0x7F, 0xFF, 0xF8, 0x00,
            0xFF, 0xF8, 0x00, 0x1F, 0xFE, 0x00, 0x00, 0xFF, 0xE0, 0x00, 0x1F, 0xFC, 0x00, 0xF0, 0x00, 0x00,
            0x00, 0x3F, 0xFF, 0x00, 0x00, 0x00, 0x3F, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7F, 0xE0, 0x00,
            0x00, 0x3F, 0xFE, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xFF, 0x80, 0x00, 0x00, 0x0F, 0xFF, 0xC0, 0x00,
            0xFF, 0xD8, 0x00, 0x1F, 0xFC, 0x00, 0x00, 0xEE, 0xC0, 0x00, 0x0F, 0xD8, 0x00, 0xE0, 0x00, 0x00,
            0x00, 0x1F, 0xFB, 0x00, 0x00, 0x00, 0x3B, 0xF6, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7E, 0xC0, 0x00,
            0x00, 0x7F, 0xEE, 0x00, 0x00, 0x00, 0x00, 0x1D, 0xFB, 0x00, 0x00, 0x00, 0x07, 0xFD, 0x80, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1E, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3C, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x7E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xF0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x3F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7E, 0x00, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x3C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xE0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7C, 0x00, 0x1F, 0x03, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xC0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x01, 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1E, 0x00, 0x1F, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x60, 0x00, 0x00, 0x00, 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xE0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x7B, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1C, 0x00, 0x0F, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xF0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x79, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7E, 0x00, 0x0C, 0x07, 0xC0, 0x00, 0x00, 0x00, 0x00, 0x00,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xC0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3C, 0x00, 0x0C, 0x07, 0xC0, 0x00, 0x00, 0x00, 0x00, 0x00,
            0xE0, 0x00, 0x00, 0x00, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xC0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1E, 0x00, 0x0F, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0xF0, 0x00, 0x00, 0x00, 0x00, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xC0, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x80, 0x00, 0xA0, 0x01, 0x40, 0x1C, 0x40, 0x1E, 0x03, 0x00, 0x02, 0x00, 0x04, 0x00, 0x00,
            0xE0, 0x00, 0x40, 0x00, 0x50, 0x1C, 0x20, 0x00, 0x20, 0x00, 0x50, 0x01, 0xC0, 0x04, 0x00, 0x01,
            0x00, 0x10, 0x00, 0x00, 0x70, 0x00, 0x40, 0x00, 0x20, 0x00, 0x80, 0x00, 0x02, 0x00, 0x20, 0x50,
            0x01, 0xC0, 0x00, 0xE0, 0x07, 0xC0, 0x7C, 0xC0, 0x3C, 0x03, 0x00, 0x0F, 0x00, 0x0E, 0x00, 0x00,
            0xF0, 0x00, 0xE0, 0x00, 0x70, 0x1C, 0xF0, 0x00, 0x60, 0x00, 0x70, 0x03, 0xC0, 0x0F, 0x00, 0x07,
            0x80, 0x18, 0x00, 0x01, 0xF8, 0x01, 0xC0, 0x00, 0x30, 0x00, 0xC0, 0x00, 0x0F, 0x00, 0x30, 0x70,
            0x0F, 0xE0, 0x7F, 0xE0, 0x0F, 0xF0, 0x7F, 0xE0, 0x0E, 0x07, 0xC0, 0xFF, 0x80, 0x3F, 0xF0, 0x01,
            0xFE, 0x03, 0xF8, 0x03, 0xFC, 0x1F, 0xF8, 0x07, 0xF0, 0x01, 0xFE, 0x01, 0xC0, 0x1F, 0xE0, 0x0F,
            0xF0, 0x38, 0x78, 0x00, 0xFC, 0x03, 0xFC, 0x07, 0xF8, 0x01, 0xE1, 0xE0, 0x7F, 0x80, 0x38, 0x70,
            0x1F, 0xF0, 0x7F, 0xE0, 0x1F, 0xF0, 0x3F, 0xF0, 0x0F, 0x07, 0xC1, 0xFF, 0x80, 0x3F, 0xF0, 0x01,
            0xFE, 0x03, 0xF8, 0x07, 0xFC, 0x1F, 0xF8, 0x0F, 0xF8, 0x01, 0xFF, 0x03, 0xC0, 0x3F, 0xE0, 0x1F,
            0xF0, 0x78, 0xF8, 0x01, 0xFC, 0x07, 0xFC, 0x07, 0xF8, 0x00, 0xE3, 0xF0, 0x7F, 0xC0, 0x38, 0xF0,
            0x1F, 0xF0, 0x3F, 0xF8, 0x3F, 0xF0, 0x7F, 0xF8, 0x0C, 0x07, 0x01, 0xFF, 0xC0, 0x3F, 0xE0, 0x03,
            0xFC, 0x0F, 0xFC, 0x03, 0xFC, 0x1F, 0xFC, 0x0F, 0xFE, 0x03, 0xFF, 0x01, 0xC0, 0x7F, 0xE0, 0x3F,
            0xFC, 0x78, 0xFC, 0x00, 0xFE, 0x0F, 0xFC, 0x07, 0xFC, 0x00, 0xE1, 0xF0, 0x7F, 0xE0, 0xF1, 0xF0,
            0x3E, 0xF0, 0x1D, 0xF8, 0x3F, 0xF0, 0x7F, 0xFC, 0x1C, 0x07, 0x81, 0xFF, 0x80, 0x77, 0xC0, 0x01,
            0xF8, 0x0F, 0x5E, 0x0F, 0xBC, 0x1F, 0xF8, 0x0F, 0xFC, 0x07, 0xAF, 0x03, 0xC0, 0x7D, 0xE0, 0x3F,
            0xFC, 0x38, 0x70, 0x00, 0xFC, 0x1F, 0xBC, 0x07, 0xF8, 0x00, 0xE3, 0xC0, 0xFF, 0xE0, 0xF0, 0xF0,
            0x7C, 0xFC, 0x1C, 0xF8, 0x3E, 0x70, 0x3F, 0xF8, 0x1F, 0x07, 0xC1, 0xF3, 0x80, 0xF1, 0xE0, 0x00,
            0xFC, 0x0F, 0x1C, 0x0F, 0x9C, 0x1F, 0x3C, 0x0F, 0xFE, 0x07, 0x8F, 0xC1, 0xC0, 0x70, 0xF8, 0x39,
            0xF0, 0x1C, 0x30, 0x00, 0x38, 0x0E, 0x1F, 0x07, 0xFC, 0x00, 0xE1, 0xC1, 0xF3, 0xE0, 0x3C, 0x70,
            0x3E, 0xF8, 0x7C, 0x78, 0x3C, 0x70, 0x7E, 0x7C, 0x1C, 0x07, 0x81, 0xF3, 0xE0, 0xF9, 0xC0, 0x00,
            0xF0, 0x1F, 0x5E, 0x1E, 0x0C, 0x1E, 0x38, 0x0F, 0x3C, 0x03, 0x8F, 0x83, 0xC1, 0xF1, 0xF8, 0x38,
            0xF0, 0x3E, 0x60, 0x00, 0xE0, 0x0E, 0x3F, 0x07, 0x90, 0x01, 0xF3, 0xC1, 0xE1, 0xE0, 0x31, 0xF0,
            0x7F, 0xFC, 0x7C, 0x38, 0x38, 0x70, 0x7C, 0x38, 0x0C, 0x07, 0x01, 0xF3, 0xE0, 0xF9, 0xE0, 0x00,
            0xF0, 0x3F, 0xFC, 0x0E, 0x0C, 0x1C, 0x38, 0x0F, 0x1E, 0x07, 0x8F, 0xC1, 0xC1, 0xF0, 0xF8, 0x3C,
            0x70, 0x7C, 0xF0, 0x00, 0xE0, 0x0E, 0x1F, 0x07, 0x00, 0x00, 0xF9, 0xC1, 0xC0, 0xE0, 0x31, 0xF0,
            0x3F, 0xF8, 0x3C, 0x78, 0x01, 0xF0, 0x3E, 0x38, 0x0F, 0x07, 0xC1, 0xC3, 0x80, 0xF1, 0xC0, 0x00,
            0xE0, 0x1F, 0xFE, 0x0E, 0x00, 0x1E, 0x38, 0x0E, 0x1C, 0x0F, 0x83, 0x83, 0xC0, 0xF0, 0x78, 0x38,
            0x70, 0x1C, 0xE0, 0x00, 0x70, 0x3E, 0x0F, 0x07, 0x00, 0x00, 0x79, 0xC1, 0xE0, 0xF8, 0xF8, 0xF0,
            0x7F, 0xF8, 0x1C, 0x38, 0x01, 0xF0, 0x7C, 0x3C, 0x0E, 0x07, 0xC1, 0xE3, 0xC0, 0xF1, 0xE0, 0x00,
            0xF0, 0x3F, 0x7C, 0x1E, 0x00, 0x1C, 0x3C, 0x0F, 0x1E, 0x1F, 0x03, 0x81, 0xC0, 0x70, 0x38, 0x3D,
            0x70, 0x1C, 0x70, 0x00, 0x78, 0x3E, 0x0E, 0x07, 0x00, 0x00, 0x3B, 0xC1, 0xC1, 0xF8, 0xF8, 0x70,
            0xF8, 0x00, 0x3C, 0x78, 0x03, 0xF0, 0x7E, 0x3E, 0x3C, 0x07, 0x01, 0xC3, 0xE0, 0xFF, 0xC0, 0x00,
            0xE0, 0x1F, 0x00, 0x3E, 0x00, 0x1E, 0x38, 0x0F, 0x1C, 0x0E, 0x0F, 0x83, 0xC1, 0xF0, 0x78, 0x3F,
            0xF0, 0x1C, 0xE0, 0x01, 0xE0, 0x38, 0x3F, 0x07, 0x00, 0x00, 0x7F, 0xC7, 0xE0, 0xE0, 0xE1, 0xF0,
            0x78, 0x00, 0x1C, 0x78, 0x0F, 0xF0, 0x1C, 0x3E, 0x0E, 0x07, 0x81, 0xE3, 0xC0, 0x7F, 0xC0, 0x00,
            0xF0, 0x3E, 0x00, 0x1E, 0x00, 0x1C, 0x38, 0x0E, 0x1E, 0x0F, 0x07, 0xC1, 0xC1, 0xF0, 0x38, 0x1F,
            0xE0, 0x1E, 0xF0, 0x00, 0x70, 0x3E, 0x0F, 0x07, 0x00, 0x00, 0x3B, 0x81, 0xC0, 0xF0, 0xF8, 0xF0,
            0x38, 0x00, 0x3C, 0x38, 0x0F, 0xF0, 0x1E, 0x3E, 0x0F, 0x07, 0xC1, 0xC3, 0x80, 0x3F, 0x80, 0x00,
            0xE0, 0x1C, 0x00, 0x0E, 0x00, 0x1E, 0x38, 0x0F, 0x1C, 0x1F, 0x83, 0x83, 0xC1, 0xF0, 0x78, 0x0F,
            0xE0, 0x1F, 0xF0, 0x00, 0x78, 0x3E, 0x0E, 0x07, 0x00, 0x00, 0x71, 0x01, 0xE1, 0xF8, 0xF8, 0x70,
            0xF8, 0x00, 0x7C, 0x78, 0x3F, 0xF0, 0x3C, 0x3C, 0x0C, 0x07, 0x81, 0xE3, 0xE0, 0x3F, 0x00, 0x00,
            0xF0, 0x3E, 0x00, 0x3E, 0x00, 0x1C, 0x3C, 0x0F, 0x1E, 0x0E, 0x07, 0x81, 0xC1, 0xF0, 0x38, 0x0F,
            0x80, 0x07, 0xC0, 0x00, 0xE0, 0x3C, 0x1F, 0x07, 0x00, 0x00, 0x7F, 0x03, 0xC0, 0xF0, 0xF1, 0xF0,
            0xF8, 0x00, 0x7C, 0x78, 0x3F, 0xF0, 0x7E, 0x38, 0x0C, 0x07, 0x01, 0xC3, 0xE0, 0x3E, 0x00, 0x00,
            0xE0, 0x1F, 0x00, 0x3E, 0x00, 0x1E, 0x38, 0x0E, 0x1C, 0x0E, 0x0F, 0xC3, 0xC1, 0xF0, 0x78, 0x1F,
            0x00, 0x07, 0xC0, 0x00, 0xE0, 0x38, 0x3F, 0x07, 0x00, 0x00, 0x3F, 0x07, 0xE1, 0xE0, 0xF1, 0xF0,
            0x3C, 0x08, 0x3C, 0x38, 0x3C, 0x70, 0x3C, 0x38, 0x3F, 0x07, 0xC1, 0xE3, 0x80, 0xF8, 0x00, 0x00,
            0xF0, 0x3F, 0x00, 0x1E, 0x00, 0x1F, 0x38, 0x0F, 0x1E, 0x0F, 0x83, 0x81, 0xC1, 0xF0, 0x38, 0x3E,
            0x00, 0x1F, 0xC0, 0x00, 0x78, 0x0E, 0x0E, 0x07, 0x00, 0x00, 0x1F, 0x01, 0xC0, 0xE0, 0x3C, 0x70,
            0x7E, 0x3C, 0x1C, 0x78, 0x38, 0xF0, 0x7E, 0x38, 0x1E, 0x07, 0xC1, 0xC3, 0xC0, 0xFC, 0x00, 0x00,
            0xF0, 0x1F, 0x04, 0x0E, 0x10, 0x1E, 0x3C, 0x0F, 0x1C, 0x1F, 0x83, 0x83, 0xC1, 0xF0, 0xF8, 0x3E,
            0x00, 0x1F, 0xC0, 0x00, 0x78, 0x3E, 0x0F, 0x07, 0x00, 0x00, 0x1F, 0x01, 0xE0, 0xE0, 0x78, 0xF0,
            0x3C, 0xF8, 0x3C, 0x78, 0x39, 0xF0, 0x7C, 0x38, 0x0C, 0x07, 0xC1, 0xE3, 0xE0, 0xFE, 0x00, 0x00,
            0xFC, 0x3F, 0x9C, 0x0F, 0x9C, 0x1E, 0x38, 0x0E, 0x1E, 0x0F, 0x8F, 0xC1, 0xC1, 0xF0, 0xF8, 0x3F,
            0x80, 0x07, 0xC0, 0x01, 0xF8, 0x3E, 0x1F, 0x07, 0x00, 0x00, 0x7F, 0x01, 0xF0, 0xE0, 0xF9, 0xF0,
            0x7F, 0xF0, 0x1C, 0x38, 0x3F, 0xF0, 0x1F, 0xFC, 0x0E, 0x07, 0x01, 0xC3, 0xC0, 0xFF, 0xC0, 0x00,
            0xFC, 0x0F, 0xFE, 0x1F, 0xFC, 0x1C, 0x38, 0x0F, 0x1C, 0x07, 0xFF, 0x03, 0xE0, 0xFF, 0xE0, 0x3F,
            0xF0, 0x07, 0x80, 0x00, 0x78, 0x1F, 0xFC, 0x07, 0xC0, 0x00, 0x3E, 0x01, 0xFF, 0xE0, 0x7F, 0xF0,
            0x3F, 0xF0, 0x3C, 0x78, 0x3F, 0xF0, 0x1F, 0xF8, 0x0F, 0x07, 0x01, 0xE3, 0x80, 0xFF, 0xE0, 0x00,
            0xFE, 0x0F, 0xFC, 0x0F, 0xFC, 0x1E, 0x3C, 0x0F, 0x1E, 0x07, 0xFF, 0x01, 0xF0, 0x7F, 0xE0, 0x3F,
            0xF0, 0x07, 0x00, 0x00, 0x78, 0x0F, 0xFC, 0x07, 0xC0, 0x00, 0x1E, 0x01, 0xFF, 0xE0, 0x3F, 0xF0,
            0x1F, 0xF0, 0x7C, 0xF8, 0x3F, 0xF0, 0x3F, 0xF0, 0x3F, 0x07, 0xC1, 0xC3, 0xE0, 0xFF, 0xF0, 0x00,
            0xFE, 0x0F, 0xF8, 0x07, 0xF0, 0x1C, 0x3E, 0x0E, 0x1C, 0x07, 0xFC, 0x03, 0xC0, 0x7F, 0xE0, 0x1F,
            0xFC, 0x07, 0x00, 0x00, 0xF8, 0x0F, 0xF0, 0x07, 0x00, 0x00, 0x1C, 0x00, 0xFF, 0xC0, 0x3F, 0xF0,
            0x1F, 0xE0, 0x7C, 0xF8, 0x3F, 0xF0, 0x7F, 0xE0, 0x1E, 0x07, 0xC1, 0xE1, 0xE0, 0xFF, 0xF0, 0x00,
            0x7E, 0x07, 0xF0, 0x03, 0xF0, 0x1E, 0x1E, 0x07, 0x1E, 0x03, 0xFC, 0x01, 0xC0, 0x3F, 0xE0, 0x0F,
            0xFC, 0x07, 0x00, 0x00, 0xF8, 0x0F, 0xF0, 0x03, 0x00, 0x00, 0x1E, 0x00, 0x3F, 0x80, 0x1F, 0xF0,
            0x07, 0xC0, 0x0C, 0x60, 0x0F, 0xF0, 0x67, 0xC0, 0x0C, 0x03, 0x00, 0x61, 0x80, 0xE7, 0xF0, 0x00,
            0x38, 0x03, 0xE0, 0x01, 0xF0, 0x06, 0x18, 0x03, 0x06, 0x01, 0xFC, 0x00, 0xC0, 0x3F, 0x80, 0x39,
            0xFC, 0x07, 0x00, 0x00, 0x60, 0x01, 0xF0, 0x03, 0x00, 0x00, 0x1C, 0x00, 0x3F, 0x00, 0x1F, 0xC0,
            0x02, 0x80, 0x00, 0x00, 0x0A, 0xA0, 0x02, 0x80, 0x00, 0x01, 0x00, 0x40, 0x81, 0xF1, 0xF0, 0x00,
            0x08, 0x01, 0x40, 0x00, 0x50, 0x04, 0x08, 0x02, 0x04, 0x00, 0xA8, 0x00, 0x00, 0x0A, 0x80, 0x38,
            0xBC, 0x07, 0x00, 0x00, 0x00, 0x00, 0x50, 0x01, 0x00, 0x00, 0x1C, 0x00, 0x0A, 0x00, 0x05, 0x40,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xF1, 0xF0, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38,
            0x3C, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xC0, 0xF0, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x70,
            0x1C, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xC0, 0x70, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xE0,
            0x3C, 0x0C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x78, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xC0, 0x70, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF0,
            0x1C, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xD0, 0xF0, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF4,
            0x3C, 0x3C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xF9, 0xF0, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFE,
            0x7E, 0x3E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xFF, 0xE0, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF,
            0xFF, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F, 0xE0, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0xFF, 0xC0, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF,
            0xFF, 0xF8, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F, 0xE0, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0x80, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F,
            0xCF, 0xE0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F, 0xC0, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F,
            0xC7, 0xE0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFE, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F,
            0x83, 0xE0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00};


    static String hexwsq = "02E20102E1087108000004040001010101638C1B0302E109710800000104000000000050971B0302E10A710800000104000800000093121B0302C10B2160120000FFFFFFFF6EFC00464D520020323000000000FC00000100019000C500C5010000002C25406A0006F03C40AE00276C5D405E0056F85D80A000676C5D40730069785D803B0079905D80D10081E85D807D0091F05D40A700B0DC5D80C800C75C5D80A000C9DC5D805E00CEF057807F00CEE05D808D00D55C57801B1400DA2443807F00E1D45040E200E1DC5D405C00EFAC50402A00FBAC5D80A200FBD45D805C0104B450407F0106C85780650109B04A403B010B2C5D805C0112344380B50119545D4099011ED05D807D0123B85D4021012E24434088012EC45D80AC0148545D40B00159645D40E2015968438078016E105080CF016E704340BA017A7843806501830C4300003D581B12000A90010001F401F4019C1500000800945A0000401B120000FFA0FFA80006313A3231FFA4003A0907000932D32637000AE0F31B1B0C010A41EFF1CC010B8E276546000BE179A46F00092EFF55F0010AF933D16C010BF2871FC1000A2677DA1AFFA5018505ABE0022342022A4F022342022A4F022342022A4F022342022A4F022835023040022A540232CB022627022DC80229C402321F022A310232A102239A022AB9022310022A1B1402287A023093022349022A580221AD02286A021FFF0226650222060228D402241D022B560222F50229F30225A4022D2B022D4802365602329D023CBC0226F1022EBB022B83023436023117023AE902313C023B14022D62023676023193023B7E02291702314F022B0402339E02299C0231EF0229FB023261022F790238F70231FC023BFC0228990230B8022E4F0237920225E8022D7D022501022C68022F1402387E022CCB0235C10227BF022FB20228F1023121022B240233C5022AF2023389022F1B1202387B022E4802378A022FBB023946022E45023786022DA50236C602291F023158022E3E02377D022C65023546022F760238F4022AF502338C02439602511A0229B502320C024553025330022A0B02327302298C0231DB023B97024782023DC4024A1F000000000000000000000000000000000000000000000000FFA600BC000002010301030707060C030900000000B5B30102B2B603B704B1B8050607B00809B9AF0ABABB0BADAEAC0C0DAAABC40EA7BEC1C2C3A5A8BFC50FA2A6BC101218191A9EA4BDC00100020002020C04071B14120F1B1200000000B3B50102030405B2B606070B0869090D0E0C0A0F106AB1B7121B12161B1B17141A1F20192E1B141E272B2F38B0152A313D18242C2D3A1D222329303235393B40451C253C3FB8283336373E41434648494B50535521344244474A4D4E4F51595A5D5E60B9BAFFA2001B1200FF0190010002481904385301DD95FFA3000300FF00EFEBF0FCFEFF001F87EBFA7F5FB7B5DFB7E1FB7F5F9ED7F13F4FC3F5E57FC7F1F3E5FB7F3E7F2FE3F2FB4FDBFD7CFEFF00BFF9FB7DFF000EF42B1B0302810C5F3F9FFE7EFF0097DBF5F5FBFE5FE3FC7E5F6FD3F9F8FF003FF7FBFDBE7FA7F37F6FCBF8F9F1FCFEDB7CBF7FBFCAFF004FC3F397C1EDF9FF005FAFAF2DBF3FD48178A4B818C084B638B2FD8E08B3CB662D72F2776794688EDAD936DADD81CD9A2BD71AE55EC53C26503C73086E3C61A074C58DEE1B142203445A08B9598C522BBE7556898A5107A157022D0D31D11B147184CD34B18D869A7A8940999CC9697A2FB90CEE50CB99E87339B43A4F0FBB4668A06904A05BBD332D14D5949CC8D5EDBB42D35CB9B41B1B9B7563089021034D0807822055C22A8CD8A11745006EAB4601CCD0B5B14E88461B1463416C4A78C298BAB81D0217A7A8300D67718FD08BE98D1B14377991A3DC463D5D298BCC8EEE9FF869F0C0A231EA7734C69843B9D1D1446989A0A7BB490D5E960F77749B747450C77210D21A37209DD34A3D1B12E87448B5643AA4587718D14740C84C87B83F565FB8D5BD0A614439949BE771A23034AD0C63D6F20C68CD3440698E89746884BAC610A65C32ADDCCA6DE548BAC18DDEB2F71670F1172863574C78B8915DFB55AC5A4993B6B1869A37332274E1A36D8358DE2F3CC8C78BEF91855BCD8468ABF4488BE1CCBF72DFFA3421EE9E8C0D30F06E1B12EA3BB0DDD2EE74376171D147404E8B0DC3451D3028772116668EE866885118294C23B8D1425108E8838AC28A288938C62C4CA77BD043D134BB9020FA2B0DCE6428A23D4D27A100F7771EE9148785DDEAE98E07874F81398F33F11EE51D5DD1349DC4851A7987FB9A634E8A61A0EE40631DC846DBA3AB80AC4A7AB7B64C8C7AE7921B1BBCC73292E3B91B1B74D3BB584597D12186B3BD80C7AB443F14F46031F0C0A0E844A03DD2103774E828D11484423D586EB40469A0979160F34C6CE38DB9B4E6CCB3771AE2F96D5CBCF25B1AEC72DABD785D585F27944D9A6331CED919C65E80276E51CE1CE77CB8E1A0F959076EDE5E7C1A2F3CF20F1EBC59B9B3C6CF06C6EB1B1BE06D874692315FA01D0E61FF0023F885103A93618CBE8429FECD10E67F6479BCC68EA7337348EEC74F44631E78D3978B44B82E92B1E69ABDB2157E52F9343E5B436DBB6D9E7C9A33CB7F2C9EDF2E575B76CF385F6F5795C2B2C3CBB1991EB7B5C132D8EEC4599C47BE46074214AA43990D5D5F7058FA3FD8E9F0B908C3A8342DF3074C01E77C2164666EF2841B12D6512F4D1A7725BB6EA6974BA3C1444CBE8BA3469224184799A79A741A344214F3749BAC688B0D14B0DDDD74268D2F73EA44F0BF5158CCEEB87D19C463DDAB5F7CB317A8D0B8BDD81B12C3A0342F561080CB85220C5DB69B3A31289DBB5DD18F143672E5B4639B4291EDDA6DB4BF3ED18C50E2728B91A36261DBB445C45A338F380D00564BF3D2F0EE8B7CF3715E6DEEC2266EF561B51B126140CE59A4234166DA6E6CDDED91BBE6B6CBBCDA1B1B9414357D58878605243D1F9C011B0302810DA9748F7DAF10B8F478E54E0743972F2F32C139DF9C163186FCB6F3F9F14217A7E267C7CF680CC686FD7CFE36E2009483EBE445D3B97EDE5C2941BB2F758BA21C711B12DC8D3C44688EE51A58C7469340D3C7860EEEEC7AB4479A5247E8F46AD77374231E8931C66DD12F6E2F69877B396DB0776E76F37C2373B3E89E50D83ADEDC7ABF7DBD8EE5F9797C7C39B9579E67FAE5E7F3F8771B12F8E4FAFB6DB74E563EA7CEF32657AEDF18F69FFBEB2EFCEEB8F87CB8F2E5CB96D135B797ADB7ED9768DC0BF97C7AFCBE574DA510F6DBCB3D078F3586EEECE50D0191D3C6ED374468DC98D3446118EE4408B0034C0742AD14B1E7853456372FB71CD8DF19DAF92EE667219961D083483DD970FA14FD6D87868F43C1D08421DC8D1B12ABEA681428CA611217757C70C28BBDBDB8ED2DF2C8BC5ED3E53D997E53881CA587CA795671ED1B1B767276BE531671B234A728460E88CE359028A2F6BF3AC44D32F93DA27108CC472EE9ED06104DAF8ABE2B88396FAE5EEF6E09B37C3CC59B1B14211EEAC28EA53E8C5D3EF94FD50FC4FC4D3D0DCD1E1D0F561B12E6FF00B1BBE2CDDF448EED141A4E36D08BB3AE395E1B1BED46438DB8C8C733B0E99C55CB0EC3A55698719C691C7579174639B9C72E1DC584369CB8D8D1381AF2B2E1A28A7475428D1D06B062F5691B14D321F44ECFD073972CF0EB339479B45D36781732EDE8C271330E850EB3D763A146D2FB79F0433622DCDB6CBDB7E38ACCBEDDBB4E5A3682DFCAF6CBBCA788D0671B142309B5DCC97440D2EC10831DC129F768E869E6689C68D253A775A61DDA5D361CDDC29BDC204B73039DF1C401F18408BD40DDFA0BEE4209E840A3A10A683D08FA1A29F0BCDF0C69A7B9122F3182A559A4772C83D06B6F333821745219E64BA28AE4F3744B82C1CB69619434E68EA683748745E874B74428D30E870423999E2F15A7A0D1A7A1BE2FBAC4DFFFA3000301BCF25BB8410E45587764C4DCA1A306AE8785A9C0F83C1C8A92DF6B8361D1EAE25A5EC342E851A3520E82F6596165C8C034493161D8D5F0989FA5FF00738BE53BC26E4B0703BC9E0791E03890F05A392C26F6F120EA3260D8B081F5942A75B255A058430B91224AC02D409B948E9124B6D1858B7A6124BA3A610E9E85BA8CDD6C0368D560858B5B50BD0E8E8612C1A243A02C10E443009A906445A06CF94E0D818153BEF88F41896103528978D592EB0E210D2DA2C924944AA45CC8C5C64C92DAD048282E8722583460687891DC93C6F5BC173677C6B93C0930725C1B1BCB187538106C717F37D0E74A76171AB176058F55BF1B12DE3B5E46CF23F170307FC8ED775B0286806A5A43560B2D206190BD065D01931B1290FC09706C20DC357D0EC9ABE4737A3AD1D0A17ADEC9B06C3B1DE3E420DC61E0D8FCAF88F03E14D86864210A82DE165D2B245B4BA596845B04AC8D96B5B94284B6D9C21B0302810E2656D1818782D0A178C060324BA32EA9062C30FE07D2FF0056F6F6528E4C0CA58D84A32DE54824A88E02430C5D6104370CB462D84B412A5845D88490BA2EA4A43B1E37D041C4D060975757A98B8D8EB2A54BA082A30D0B883016D821B6C492A352AC24A4B0E8352F28C90393561AA49630936A6035356AECEEFC63BBAB790795E21B1B32F001C416DC1B1B184BC2524B1826EAB2D020552E82C327470285E48EA978B5BA070B59B48B7125BB021B12DCA1F19A9E37EB7B0E0773A1A2D0EF9612951B1B1C0DAD38B073014B722F286E4A780FFA38BD6E2C8723B4A2FCAAEEF539305E121564BDBAE928D082166D60701696DBD2058D07A6006A56DB62EBC828F4DCB82C968C9810DD2B630BC965C48487BE713C050ED3371B12C8A1465A1428C1BB060D596F28C1268D0A3610D2D81F7963894343E621E0B243924123801B126C36C385DD3D2430E0DD1FA2D94C2DE82DB574BADE83D57190C747A88196FE9E88BA020830E9DAEE931B124931B47065D9A3DC685082C7894781912ED6EA2700487459377128745B9143A3A2DB7226EE88FD09790C74FABA3A6E707D47FF0074F47405E85D77AA0B42C247D42AD1FD1B126DD1FF00ADA0953A2E2E22EBAF21BBA16E486F7A1B1B6A41604942F5BB7240BCA92D87B52F7264D08496AD41B00A5B7417917308E8C3E0361809313BE4B47264D594A3632C0CB812F26F307B9EC7FECC9EFB0F6BE150DD84365D1AB0E44BC9C96A1C0377221B1B1977243156A50E25EFC4F85EE71770943464F0DA6C438B030E2DB6E212062ADB6991175CB75D835042C486E584B5C1E9E9B1A0411745B0DEB084098168ACB0BD8F71DE7C4C32D0DC6C68E0D080BC22EAAE416189C48375D87565872160483220316A497B5060C467A2E322414AB62B2D4AB6DAC3444AA389750F592D0A147457B9C55E2C25596A54686454D0E4F51B1B3C85C48791AB431061D482C6ACB25082848365D2C1020D10861680FE29FADA9C1F299351D820ED7C24191460D5F9490D9212C6ACDCEAAEC9C01961C86C24A94486A410309A3A3FD9B196C6852E30286CD5ED772ABDF6A706A641437BAF6F791270081A379F3BF8963E87CC49B8E84BB173262F1B1491C487B9650E0C38B81A945D0DCB092C08367C0F69C8E4FD2DE71B1493822C189D47B5D5D5751E4EE41B1B3C41D9F29FA5F03F1B14D66A7FABC1EB6587E0D86A7518B0EA41F810717CC723FCC835760DCF2188AF15DC1C4E24DD6B914780FAC68C92A6CA501C07CAE078C494EB7DE704D93B4E49F306AE850838A37905456F6CB6E82C192556C02C21BC18B4A160CDA18B572612AFCEE8F78E258E860516D30609B9B4C821285E9D69C9EFA7538B5764E0DE62D0C9193760DC36791B1B3C4FE2F99EA65F601A8C3F2906A50C58316A41A242E44971A15720EF30F0183365771F0A6041B3EE3F61F6B9C23FC9FBDF19FB8C0C0F611B1B2B1B0302A10F07F07EE60E0E762E78E7D0F61FC0F19A9F619C219C1A7D6FF37E3357EA7ADFA09377EF3EB20703F51E91D0F4151C520F3BA1050FD862CBE27B0A38147B1EA2593249194F3923058923EC20C0C093C8D875BA108F07A99314AB8A3F5258C99343A8F22383C4F598B8305E77353C854BD86486AD0D887AD96C6ADED83D46CCB2C92C90DEF7C8716C64A3E028492E8E8151C8D9356C186855DDF790E851C5E4BDE196ACB8991FDCD42C25B1D5EC6586C7D2EA7BDEE7CC62F598B279DD983D8751FD8F98DCDDA3F68FFC1D6678A71B14B0E0713E0E6CC78CF89F79ABFDE58D87DC75342F7521D4F49258F5357C443E92ACB63EB326ACBB3610F69D8D0F33D89A3F3B0790D1FA9C1F39043F534684127B9E2FB1FDEFA5ED7E0E27E46E675CE7087FCFFFA3000301EE21C0ABCC04DCCF14E60204E7D673086B73FE3DC672666CE1F43F21FD1FFC082A4BB9D6147BE703E87B5FD6C3B9E16C312AFC4B7876BE225BCA3ED60F59F4B8361ED6197F91ED357DA16163B1F30761E62A107C8B07D8FEE789F6989E97E0703433803F37DCFF001B1436B28671A7DEF31F763984399FE0E6D8FF00B39D739B599B5BCC0445E604D879CCDF0E6042EF31D06337164CDED928627B8F61561B1B03D0649F7BF6BC0CDFDCDCD87D2C3FCCF9CFEAE0FC6C3C5FD4E6F4FF00896147EB28FDC7B9C8F8CCE29F33A9C0F39217B43B1F292F03EE7FD1F110778FA57E46F0F90FCCCE00CDF980FC9CF01A3CC09ECCF7DE603FE7301FF5FC9CE08CFD0F399E69CC040DFDAE702E6DAFEF73FD7819DA3BBCC05E4FB1A9FB4973FD3FA39F5189F63CC0AD4C0E60482E6F2F301B1B33981B1B439BCBCC0FCDCDF0E60DEE732E1FFFA1E39E1B03";
    // private static final String TAG = "AccordionWidgetDemoActivity";
    DeviceBluetoothCommunication bluetoothCommunication;
    int count = 0;
    int slipnumber;
    String transactionName = "TRANSFER";
    String date = "Date : 04/07/2014";
    String time = "Time : 12:18:40";

    String bcName = "BC Name : MaestrosLTD.";
    String bcLoc = "BC Location : Navi Mumbai";
    String agentID = "Agent ID :Sachin";
    String tID = "Terminal ID :TID00001";
    String aadharNo = "REM AADHAR : XXXX XX99 99";
    String uidaiAuthCode = "UID Auth.Code: 123456781234567";
    String custName = "Customer Name : AAKASH JAGTAP";
    String status = "Txn Status :SUCCESS";
    String totalAmountString = " Transfered Amount Rs:100";
    boolean isLongSlip = false;
    TextView recievedTextView, filepath, autooff;
    ImageView Finger_Image;
    Button connectButton, filechooser, filepicker;
    Button disconnectButton;
    Button resetButton;
    Spinner swipetimeoutSpinner;
    Button swipestartButton;
    Button swipestopButton;
    Button mPrintSlipButton;
    Spinner fpsTimeoutSpinner;
    Spinner fpsScanperfingerSpinner;
    Spinner fpsTemplateformatSpinner;
    Button fpsScanButton, btgetSerialno, btgetVersionno, btMatchWSQ;
    Button fpsVerifyButton;
    Button fpsStopButton;
    Spinner mprintslipSpinner;
    Spinner slotNumberSpinner;
    Spinner smartcardTypeSpinner;
    NumberPicker mAutoOffPicker;
    EditText userIDEditText;
    // EditText userNameEditText;
    Button smartcardReadButton; // smartcardapdu;
    Button smartcardWriteButton;
    Button smartcardStopButton;

    EditText mNameEditText;
    EditText mAmountEditText;
    Button mPrintButton;

    Button mPrinttestSlipButton, mDisableAutoOffButton, mBatteryStat;
    Button mPrintBarcodeButton;
    Button mPrinterStatusButton;
    Button mHexLogoButton;
    Button mLeftAlign;
    Button mMonalisaButton;

    byte FontStyleVal;
    boolean fingerimageenable;
    RadioButton m9x24CheckBox;
    RadioButton m12x24CheckBox;
    RadioButton m16x24CheckBox;
    CheckBox menableWSQ;
    CheckBox mDedupScan;
    CheckBox mBoldCheckBox;
    CheckBox mUnderlineCheckBox;
    CheckBox mDoubleHeightCheckBox;
    CheckBox mDoubleWidthCheckBox;
    CheckBox disableFingerPrint;
    Uri imageUri;
    int[] prnRasterImg;
    int image_width;
    int image_height;
    String _ChhosenPath = null;
    Context ctx;
    CardType cardType;
    static byte templatecode;
    private boolean SC_Write_Flag = false;
    private boolean SC_Read_Flag = false;
    int slotnumber;

    byte[] userid, username;
    int fileid = 6;
    int nL, nH;
    List<Byte> templatecodeList;

    List<byte[]> fingerprintList, myfptemplatelist;

    enum SMSTATUS {
        READ_USERID, READ_USERNAME, WRITE_USERID, WRITE_USERNAME, WRITE_MEMORY, READ_MEMORY, APDU

    }

    ;

    enum SmartCardCommand {
        CreateMF, SelectMF, CreateEF, SelectEF, Write_CPU, Read_CPU
    }

    ;

    SmartCardCommand smartcardcommand;
    SMSTATUS smstatus;
    TemplateType templateType;
    int numberofscan;
    Bitmap bm;
    BroadcastReceiver _wsqreceiver;
    int dedupcounter;
    boolean enablededupscan = false;
    boolean dedupnewfinger = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myfptemplatelist = new ArrayList<byte[]>();

		/*File wspth= new File(Environment
                 .getExternalStorageDirectory()
	 			+ File.separator
	  			+ "AppPathWSQ" +File.separator
	  			+"aakash.wsq");
		String cnvtpath = Environment
        	 			.getExternalStorageDirectory()
        	 			+ File.separator
        	  			+ "AppPathWSQ" +File.separator
        	  			+"aakash.png";
		File png = Jnbis.wsq()
                .decode(wspth)
                .toPng()
                .asFile(cnvtpath);
		png.isFile();*/
        recievedTextView = (TextView) (findViewById(R.id.readertextview));

        Finger_Image = (ImageView) findViewById(R.id.Finger_Image);
        Finger_Image.setImageBitmap(bm);
        connectButton = (Button) findViewById(R.id.connect);
        disconnectButton = (Button) findViewById(R.id.disconnect);
        resetButton = (Button) findViewById(R.id.reset);
        swipetimeoutSpinner = (Spinner) findViewById(R.id.swipespinner);
        swipestartButton = (Button) findViewById(R.id.swipestart);
        swipestopButton = (Button) findViewById(R.id.swipestop);
        mBatteryStat = (Button) findViewById(R.id.btBatterystatus);
        //mLeftAlign=(Button)findViewById(R.id.leftalignButton);
        mprintslipSpinner = (Spinner) findViewById(R.id.printslipnumberspinner);
        fpsTimeoutSpinner = (Spinner) findViewById(R.id.fpstimeoutspinner);
        fpsScanperfingerSpinner = (Spinner) findViewById(R.id.fpsscancountspinner);
        disableFingerPrint = (CheckBox) findViewById(R.id.disableFingerPrint);
        fpsTemplateformatSpinner = (Spinner) findViewById(R.id.fpstemplatespinner);
        fpsScanButton = (Button) findViewById(R.id.fpsscan);
        btgetSerialno = (Button) findViewById(R.id.getSerialno);
        btgetVersionno = (Button) findViewById(R.id.getVersionno);
        btMatchWSQ = (Button) findViewById(R.id.matchwsq);
        fpsVerifyButton = (Button) findViewById(R.id.fpsverify);

        fpsStopButton = (Button) findViewById(R.id.fpsstop);
        // smartcardapdu = (Button) findViewById(R.id.smapdu);
        mPrintSlipButton = (Button) findViewById(R.id.printSlip);
        slotNumberSpinner = (Spinner) findViewById(R.id.slotnumberspinner);
        smartcardTypeSpinner = (Spinner) findViewById(R.id.cardtypespinner);
        userIDEditText = (EditText) findViewById(R.id.useridedittext);
        // userNameEditText = (EditText) findViewById(R.id.usernameedittext);
        smartcardReadButton = (Button) findViewById(R.id.smread);
        smartcardWriteButton = (Button) findViewById(R.id.smwrite);
        smartcardStopButton = (Button) findViewById(R.id.smstop);

        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mAmountEditText = (EditText) findViewById(R.id.amountEditText);
        mPrintButton = (Button) findViewById(R.id.printButton);

        mPrinttestSlipButton = (Button) findViewById(R.id.printtestButton);
        mDisableAutoOffButton = (Button) findViewById(R.id.printimageButton);

        m9x24CheckBox = (RadioButton) findViewById(R.id.RadioButton9x24);
        menableWSQ = (CheckBox) findViewById(R.id.RadioButtonWSQ);
        mDedupScan = (CheckBox) findViewById(R.id.RadioButtonDedup);
        m12x24CheckBox = (RadioButton) findViewById(R.id.RadioButton12x24);
        m16x24CheckBox = (RadioButton) findViewById(R.id.RadioButton16x24);
        mBoldCheckBox = (CheckBox) findViewById(R.id.boldCheckBox);
        mUnderlineCheckBox = (CheckBox) findViewById(R.id.underlineCheckBox01);
        mDoubleHeightCheckBox = (CheckBox) findViewById(R.id.doubleheightCheckBox01);
        mDoubleWidthCheckBox = (CheckBox) findViewById(R.id.doublewidthCheckBox01);
        mPrintBarcodeButton = (Button) findViewById(R.id.barcodeButton);
        mPrinterStatusButton = (Button) findViewById(R.id.captButton);
        mHexLogoButton = (Button) findViewById(R.id.HexLogoButton);
        mMonalisaButton = (Button) findViewById(R.id.monalisaButton);
        mprintslipSpinner.setEnabled(false);
        fpsTimeoutSpinner.setEnabled(false);
        fpsScanperfingerSpinner.setEnabled(false);
        fpsTemplateformatSpinner.setEnabled(false);
        fpsScanButton.setEnabled(false);
        fpsVerifyButton.setEnabled(false);
        fpsStopButton.setEnabled(false);
        swipestartButton.setEnabled(false);
        swipetimeoutSpinner.setEnabled(false);
        mPrintSlipButton.setEnabled(false);
        slotNumberSpinner.setEnabled(false);
        smartcardTypeSpinner.setEnabled(false);
        userIDEditText.setEnabled(false);
        // userNameEditText.setEnabled(false);
        smartcardReadButton.setEnabled(false);
        smartcardWriteButton.setEnabled(false);
        smartcardStopButton.setEnabled(false);
        // smartcardapdu.setEnabled(false);
        mBatteryStat.setEnabled(false);
        mDisableAutoOffButton.setEnabled(false);
        mHexLogoButton.setEnabled(false);
        btMatchWSQ.setEnabled(false);
        btgetSerialno.setEnabled(false);
        resetButton.setEnabled(true);
        mPrintButton.setEnabled(false);

        mPrinttestSlipButton.setEnabled(false);
        mPrintBarcodeButton.setEnabled(false);
        mPrinterStatusButton.setEnabled(false);

        mMonalisaButton.setEnabled(false);

        templateType = TemplateType.ANSI_378;
        numberofscan = 1;
        cardType = CardType.CPU;
        ctx = this;
        filechooser = (Button) findViewById(R.id.filechooser);
        filepicker = (Button) findViewById(R.id.FilepickerButton);
        filepath = (TextView) findViewById(R.id.tempfilepath);

        SharedPreferences preferences = this.getSharedPreferences(
                "templatepath", 0);

        _ChhosenPath = preferences.getString("path", "No path Selected");

        if (_ChhosenPath != null) {
            filepath.setText("Change Path");
            filepath.setText(_ChhosenPath);

        } else {

            filechooser.setText("Select Path");

        }

		/*mLeftAlign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nL=80;
				nH=50;
			//	bluetoothCommunication.SetLeftAlign(nL, nH);
				String name = "Name : " + mNameEditText.getText().toString();
				bluetoothCommunication.SendData(name.getBytes());
			}
		});
		*/
        filechooser.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                showFileChooser();
            }
        });
        filepicker.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showFilePicker();

            }
        });
        mBoldCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {

                    FontStyleVal |= 0x08;
                } else {
                    FontStyleVal &= 0xF7;
                }
            }
        });

        mUnderlineCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            FontStyleVal |= 0x80;
                        } else {
                            FontStyleVal &= 0x7F;
                        }
                    }
                });

        mDoubleHeightCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            FontStyleVal |= 0x10;
                        } else {
                            FontStyleVal &= 0xEF;
                        }
                    }
                });

        mDoubleWidthCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            FontStyleVal |= 0x20;
                        } else {
                            FontStyleVal &= 0xDF;
                        }
                    }
                });

        m9x24CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    FontStyleVal &= 0xFC;
                    FontStyleVal |= 0x02;
                }
            }
        });

        m12x24CheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            FontStyleVal &= 0xFC;
                            FontStyleVal |= 0x00;
                        }
                    }
                });

        m16x24CheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            FontStyleVal &= 0xFC;
                            FontStyleVal |= 0x01;
                        }
                    }
                });
        menableWSQ.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (bluetoothCommunication != null) {
                        bluetoothCommunication.GetWSQData();
                    }
                } else {
                    if (bluetoothCommunication != null) {
                        bluetoothCommunication.GetNormalScan();
                    }

                }

            }
        });
        mDedupScan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enablededupscan = true;
                    dedupnewfinger = true;


                } else {
                    enablededupscan = false;
                    dedupcounter = 0;

                }

            }
        });
        smartcardTypeSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                        switch (position) {
                            case 0:
                                cardType = CardType.CPU;
                                break;
                            case 1:
                                cardType = CardType.MEMORY;
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

        mPrinttestSlipButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                bluetoothCommunication.PrintTestSlip();


            }
        });
        mDisableAutoOffButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                bluetoothCommunication.DisableAutoOff();
            }
        });
        mBatteryStat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                byte[] batstat = {0x1B, 0x42};
                bluetoothCommunication.SendData(batstat);

            }
        });
        mPrinterStatusButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bluetoothCommunication.GetPrinterStatus();
            }
        });

        mPrintButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                bluetoothCommunication.setPrinterFont(FontStyleVal);
                String name = "Name : " + mNameEditText.getText().toString();
                // byte[] imageDataBytes = Base64.decode(name, 0);
                // bluetoothCommunication.SendData(imageDataBytes);
                bluetoothCommunication.SendData(name.getBytes());
                bluetoothCommunication.LineFeed();
                String amount = "Amount : "
                        + mAmountEditText.getText().toString();
                bluetoothCommunication.SendData(amount.getBytes());
                bluetoothCommunication.LineFeed();
                bluetoothCommunication.LineFeed();
                bluetoothCommunication.LineFeed();

            }
        });

        mPrintBarcodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
				/*String name = "Name : " + mNameEditText.getText().toString();

				try {
					Bitmap barbmp = createBarcodeBitmap(name, 485, 150);
					FileOutputStream out = null;
					try {
					    out = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/ajbar.png");
					    barbmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance

					    // PNG is a lossless format, the compression factor (100) is ignored
					} catch (Exception e) {
					    e.printStackTrace();
					} finally {
					    try {
					        if (out != null) {
					            out.close();
					        }
					    } catch (IOException e) {
					        e.printStackTrace();
					    }
					}
					try {


						GetPrintableImage getPrintableImage = new GetPrintableImage();
						prnRasterImg = getPrintableImage.GetPrintableArray(
								AccordionWidgetDemoActivity.this, barbmp.getWidth(),
								barbmp);
						image_width = getPrintableImage.getPrintWidth();
						image_height = getPrintableImage.getPrintHeight();
						getPrintableImage = null;

					} catch (Exception e) {
						e.printStackTrace();
					}
					try {

						// sleep(1000);

						byte[] CommandImagePrint = new byte[prnRasterImg.length + 5];

						CommandImagePrint[0] = 0x1B; // Command to for bit image
														// mode
														// please refer the previous
						// document
						CommandImagePrint[1] = 0x23; // Exc #
						CommandImagePrint[2] = (byte) image_width; // 8 Vertical
																	// Dots(Heights)
																	// &
																	// Single Width
																	// Mode

						// selected
						CommandImagePrint[3] = (byte) (image_height / 256);// f8 //
																			// Decimal
																			// 248
																			// since
																			// the
																			// Image
																			// width
																			// is

						// 248 Pixels as mentioned above
						CommandImagePrint[4] = (byte) (image_height % 256);

						for (int i = 0; i < prnRasterImg.length; i++) {
							CommandImagePrint[i + 5] = (byte) (prnRasterImg[i] & 0xFF);
						}

						bluetoothCommunication.SendData(CommandImagePrint); // CommandImagePrint.length
						count = 14;
						isLongSlip = true;
						// bluetoothCommunication.LineFeed();
						bluetoothCommunication.GetPrinterStatus();

					} catch (Exception ioe) {
						System.out
								.println("Problems reading from or writing to serial port."
										+ ioe.getMessage());

					}

					Cocos2dxBitmap cocos2dxBitmap = new Cocos2dxBitmap(
							getApplicationContext());
					Bitmap ecgBmp1 = cocos2dxBitmap.createTextBitmap(mNameEditText
							.getText().toString(), familyName, typeface,
							(int) fontSize, alignment, 380, 2000);
					printtextasimg(ecgBmp1);
				} catch (WriterException e) {
					e.printStackTrace();
				}*/


                byte[] val = {0x1B, 0x39, 0x01, 0x30, 0x31, 0x32, 0x33, 0x34,
                        0x35, 0x36, 0x37, 0x38, 0x39, 0x30, 0x31, 0x0A, 0x0A,
                        0x0A, 0x0A};
			/*	DisableAll();
				isLongSlip = false;
				try {
					InputStream inputStream = getAssets().open("client200.jpg");
					Bitmap bit = BitmapFactory.decodeStream(inputStream);
					GetPrintableImage getPrintableImage = new GetPrintableImage();
					prnRasterImg = getPrintableImage.GetPrintableArray(
							AccordionWidgetDemoActivity.this, bit.getWidth(),
							bit);
					image_width = getPrintableImage.getPrintWidth();
					image_height = getPrintableImage.getPrintHeight();
					getPrintableImage = null;

				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					// sleep(1000);

					byte[] CommandImagePrint = new byte[prnRasterImg.length + 5];

					CommandImagePrint[0] = 0x1B; // Command to for bit image
													// mode
													// please refer the previous
					// document
					CommandImagePrint[1] = 0x23; // Exc #
					CommandImagePrint[2] = (byte) image_width; // 8 Vertical
																// Dots(Heights)
																// &
																// Single Width
																// Mode

					// selected
					CommandImagePrint[3] = (byte) (image_height / 256);// f8 //
																		// Decimal
																		// 248
																		// since
																		// the
																		// Image
																		// width
																		// is

					// 248 Pixels as mentioned above
					CommandImagePrint[4] = (byte) (image_height % 256);

					for (int i = 0; i < prnRasterImg.length; i++) {
						CommandImagePrint[i + 5] = (byte) (prnRasterImg[i] & 0xFF);
					}

					bluetoothCommunication.SendData(CommandImagePrint); // CommandImagePrint.length
					count = 14;
					isLongSlip = true;
					// bluetoothCommunication.LineFeed();
					bluetoothCommunication.GetPrinterStatus();

				} catch (Exception ioe) {
					System.out
							.println("Problems reading from or writing to serial port."
									+ ioe.getMessage());

				}
				bluetoothCommunication.LineFeed();
*/


                bluetoothCommunication.SendData(val);

            }
        });

        mHexLogoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                printImagedata();

            }
        });
        mPrintSlipButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DisableAll();
                count = 0;
                slipnumber = mprintslipSpinner.getSelectedItemPosition() + 1;
                slipcounter = 1;
                isLongSlip = true;
                bluetoothCommunication.GetPrinterStatus();

            }
        });
      /*  mMonalisaButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DisableAll();
                isLongSlip = false;
                try {
                    InputStream inputStream = getResources().openRawResource(R.drawable.monalisa);
                    Bitmap bit = BitmapFactory.decodeStream(inputStream);
                    GetPrintableImage getPrintableImage = new GetPrintableImage();
                    prnRasterImg = getPrintableImage.GetPrintableArray(
                            AccordionWidgetDemoActivity.this, bit.getWidth(),
                            bit);
                    image_width = getPrintableImage.getPrintWidth();
                    image_height = getPrintableImage.getPrintHeight();
                    getPrintableImage = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    // sleep(1000);

                    byte[] CommandImagePrint = new byte[prnRasterImg.length + 5];

                    CommandImagePrint[0] = 0x1B; // Command to for bit image
                    // mode
                    // please refer the previous
                    // document
                    CommandImagePrint[1] = 0x23; // Exc #
                    CommandImagePrint[2] = (byte) image_width; // 8 Vertical
                    // Dots(Heights)
                    // &
                    // Single Width
                    // Mode

                    // selected
                    CommandImagePrint[3] = (byte) (image_height / 256);// f8 //
                    // Decimal
                    // 248
                    // since
                    // the
                    // Image
                    // width
                    // is

                    // 248 Pixels as mentioned above
                    CommandImagePrint[4] = (byte) (image_height % 256);

                    for (int i = 0; i < prnRasterImg.length; i++) {
                        CommandImagePrint[i + 5] = (byte) (prnRasterImg[i] & 0xFF);
                    }

                    bluetoothCommunication.SendData(CommandImagePrint); // CommandImagePrint.length
                    count = 14;
                    isLongSlip = true;
                    // bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();

                } catch (Exception ioe) {
                    System.out.println("Problems reading from or writing to serial port." + ioe.getMessage());

                }

                // bluetoothCommunication.GetPrinterStatus();
            }
        });*/

        smartcardReadButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                swipestartButton.setEnabled(false);
                swipetimeoutSpinner.setEnabled(false);
                swipestopButton.setEnabled(false);
                disconnectButton.setEnabled(false);
                fpsTimeoutSpinner.setEnabled(false);
                fpsScanperfingerSpinner.setEnabled(false);
                fpsTemplateformatSpinner.setEnabled(false);
                fpsScanButton.setEnabled(false);
                fpsVerifyButton.setEnabled(false);
                fpsStopButton.setEnabled(false);

                mPrintButton.setEnabled(false);

                mPrinttestSlipButton.setEnabled(false);
                mPrintBarcodeButton.setEnabled(false);
                mPrinterStatusButton.setEnabled(false);

                mMonalisaButton.setEnabled(false);

                slotNumberSpinner.setEnabled(false);
                smartcardTypeSpinner.setEnabled(false);
                userIDEditText.setEnabled(false);
                // userNameEditText.setEnabled(false);
                smartcardReadButton.setEnabled(false);
                smartcardWriteButton.setEnabled(false);
                smartcardStopButton.setEnabled(true);
                mprintslipSpinner.setEnabled(false);
                mPrintSlipButton.setEnabled(false);

                slotnumber = slotNumberSpinner.getSelectedItemPosition() + 1;
                if (cardType == CardType.CPU)
                    smstatus = SMSTATUS.READ_USERID;
                else
                    smstatus = SMSTATUS.READ_MEMORY;
                bluetoothCommunication.CheckSmartCardStatus(slotnumber);
                SC_Write_Flag = false;
                SC_Read_Flag = true;
            }
        });

        smartcardWriteButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                swipestartButton.setEnabled(false);
                swipetimeoutSpinner.setEnabled(false);
                swipestopButton.setEnabled(false);
                disconnectButton.setEnabled(false);
                fpsTimeoutSpinner.setEnabled(false);
                fpsScanperfingerSpinner.setEnabled(false);
                fpsTemplateformatSpinner.setEnabled(false);
                fpsScanButton.setEnabled(false);
                fpsVerifyButton.setEnabled(false);
                fpsStopButton.setEnabled(false);
                mPrintButton.setEnabled(false);

                mPrinttestSlipButton.setEnabled(false);
                mPrintBarcodeButton.setEnabled(false);
                mPrinterStatusButton.setEnabled(false);

                mMonalisaButton.setEnabled(false);

                slotNumberSpinner.setEnabled(false);
                smartcardTypeSpinner.setEnabled(false);
                userIDEditText.setEnabled(false);
                // userNameEditText.setEnabled(false);
                smartcardReadButton.setEnabled(false);
                smartcardWriteButton.setEnabled(false);
                smartcardStopButton.setEnabled(true);

                userid = userIDEditText.getText().toString().getBytes();
                // username = userNameEditText.getText().toString().getBytes();

                slotnumber = slotNumberSpinner.getSelectedItemPosition() + 1;
                if (cardType == CardType.CPU)
                    smstatus = SMSTATUS.WRITE_USERID;
                else
                    smstatus = SMSTATUS.WRITE_MEMORY;
                bluetoothCommunication.CheckSmartCardStatus(slotnumber);
                SC_Write_Flag = true;
                SC_Read_Flag = false;

            }
        });

		/*
		 * smartcardapdu.setOnClickListener(new OnClickListener(){
		 *
		 * @Override public void onClick(View v) { stub smstatus =
		 * SMSTATUS.APDU;
		 * bluetoothCommunication.CheckSmartCardStatus(slotNumberSpinner
		 * .getSelectedItemPosition() + 1);
		 *
		 * }});
		 */

        smartcardStopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                bluetoothCommunication.CancelScan();
            }
        });

        fpsScanperfingerSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {

                        switch (position) {
                            case 0:
                                numberofscan = 1;
                                break;
                            case 1:
                                numberofscan = 3;
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
        disableFingerPrint
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            fingerimageenable = false;
                        } else {
                            fingerimageenable = true;
                            Finger_Image.setImageDrawable(null);
                        }
                    }
                });

        fpsScanButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (fingerimageenable == true) {
                    swipestartButton.setEnabled(false);
                    swipetimeoutSpinner.setEnabled(false);
                    swipestopButton.setEnabled(false);
                    disconnectButton.setEnabled(false);
                    fpsTimeoutSpinner.setEnabled(false);
                    fpsScanperfingerSpinner.setEnabled(false);
                    fpsTemplateformatSpinner.setEnabled(false);
                    fpsScanButton.setEnabled(false);
                    fpsVerifyButton.setEnabled(false);
                    fpsStopButton.setEnabled(true);

                    slotNumberSpinner.setEnabled(false);
                    smartcardTypeSpinner.setEnabled(false);
                    userIDEditText.setEnabled(false);
                    // userNameEditText.setEnabled(false);
                    smartcardReadButton.setEnabled(false);
                    smartcardWriteButton.setEnabled(false);
                    smartcardStopButton.setEnabled(false);

                    mPrintButton.setEnabled(false);

                    mPrinttestSlipButton.setEnabled(false);
                    mPrintBarcodeButton.setEnabled(false);
                    mPrinterStatusButton.setEnabled(false);

                    mMonalisaButton.setEnabled(false);
                    bluetoothCommunication.GetTemplate(templateType,
                            (int) fpsTimeoutSpinner.getSelectedItemPosition(),
                            numberofscan, 0);
                    if (enablededupscan) {
                        dedupcounter++;
                    }
                    Finger_Image.setImageBitmap(null);

                } else {
                    swipestartButton.setEnabled(false);
                    swipetimeoutSpinner.setEnabled(false);
                    swipestopButton.setEnabled(false);
                    disconnectButton.setEnabled(false);
                    fpsTimeoutSpinner.setEnabled(false);
                    fpsScanperfingerSpinner.setEnabled(false);
                    fpsTemplateformatSpinner.setEnabled(false);
                    fpsScanButton.setEnabled(false);
                    fpsVerifyButton.setEnabled(false);
                    fpsStopButton.setEnabled(true);

                    slotNumberSpinner.setEnabled(false);
                    smartcardTypeSpinner.setEnabled(false);
                    userIDEditText.setEnabled(false);
                    // userNameEditText.setEnabled(false);
                    smartcardReadButton.setEnabled(false);
                    smartcardWriteButton.setEnabled(false);
                    smartcardStopButton.setEnabled(false);

                    mPrintButton.setEnabled(false);

                    mPrinttestSlipButton.setEnabled(false);
                    mPrintBarcodeButton.setEnabled(false);
                    mPrinterStatusButton.setEnabled(false);

                    mMonalisaButton.setEnabled(false);

                    bluetoothCommunication.GetTemplate(templateType,
                            (int) fpsTimeoutSpinner.getSelectedItemPosition(),
                            numberofscan, 1);
                    if (enablededupscan) {
                        dedupcounter++;
                    }
                }
            }
        });
        btgetSerialno.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
/*		  byte[] serbuffer = { 0x1B, 0x53, 0x72 };
		  bluetoothCommunication.SendData(serbuffer);*/
                byte[] buffer = {0x1B, 0x73, 0x6E, 0x00, 0x00, 0x01, 0x00};
                Log.i("WSQTAG", "WSQ Command Sent");
                if (bluetoothCommunication != null) {
                    bluetoothCommunication.SendData(buffer);
                }
                if (enablededupscan) {
                    dedupcounter++;
                }
            }
        });
        btgetVersionno.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
		 /* byte[] verbuffer = { 0x1B, 0x53, 0x76 };
		  bluetoothCommunication.SendData(verbuffer);*/
                String newTextFile = Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "AppPathWSQ"
                        + File.separator + "hexfulldata.txt";
                File mfolderpath = new File(Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "AppPathWSQ");

                if (!mfolderpath.exists())
                    mfolderpath.mkdir();

                //Read text from file
                String finaldata = read_file(getApplicationContext(), newTextFile);
                int startpos = finaldata.toLowerCase(Locale.ENGLISH).indexOf("ffffffff6e");

                String length_1stletter = Character.toString(finaldata.charAt(startpos + 10));
                String length_2ndletter = Character.toString(finaldata.charAt(startpos + 11));
                String length_3rdletter = Character.toString(finaldata.charAt(startpos + 12));
                String length_4thletter = Character.toString(finaldata.charAt(startpos + 13));
                String Templatelength = length_3rdletter + length_4thletter + length_1stletter + length_2ndletter;
                int tmplength = Integer.parseInt(Templatelength.trim(), 16);
                int finaltmplength = tmplength * 2;
                String templatestr = finaldata.substring(startpos + 14, startpos + 14 + finaltmplength);
                String finalwsqhexdata = finaldata.substring(finaltmplength, finaldata.length());

                byte[] matchtemp = hexStringToByteArray(templatestr);
                String templatepath = Environment.getExternalStorageDirectory() + File.separator + "AppPathWSQ"
                        + File.separator + "matchwsq.iso";
                File folderpath = new File(Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "AppPathWSQ" + File.separator);

                if (!folderpath.exists())
                    folderpath.mkdir();
                {
                    writeToFileName(matchtemp, templatepath);
                }
                String hexwsqdata = converthex(finalwsqhexdata);
                byte[] finalwsq = myhexToASCII(hexwsqdata.getBytes());
                writeToFile(finalwsq, folderpath.getAbsolutePath());
		/*bluetoothCommunication.MatchTemplate(
				fpsTimeoutSpinner.getSelectedItemPosition(), 1,
				templateType, matchtemp);*/

            }
        });
        btMatchWSQ.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "AppPathWSQ"
                            + File.separator;

                    File matchwsqfile = new File(path + "matchwsq.iso");

                    if (matchwsqfile.exists()) {
                        FileInputStream bos = new FileInputStream(matchwsqfile);
                        byte[] templatewsqdata = new byte[bos.available()];
                        bos.read(templatewsqdata);
                        bos.close();
                        bluetoothCommunication.MatchTemplate(
                                fpsTimeoutSpinner.getSelectedItemPosition(), 1,
                                templateType, templatewsqdata);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Read text from file
	/*String finaldata = read_file(getApplicationContext(), newTextFile);
int startpos = finaldata.indexOf("ffffffff6e");

String length_1stletter = Character.toString(finaldata.charAt(startpos+10));
String length_2ndletter = Character.toString(finaldata.charAt(startpos+11));
String length_3rdletter = Character.toString(finaldata.charAt(startpos+12));
String length_4thletter = Character.toString(finaldata.charAt(startpos+13));
String Templatelength   =   length_3rdletter+ length_4thletter+length_1stletter+length_2ndletter;
int tmplength = Integer.parseInt(Templatelength.trim(), 16);
int finaltmplength=tmplength*2;
String templatestr = finaldata.substring(startpos+14,startpos+14+finaltmplength);
byte[] matchtemp = hexStringToByteArray(templatestr);
String path = Environment
.getExternalStorageDirectory()
+ File.separator
+ "AakashWSQ"
+ File.separator + "matchwsq.iso";
writeToFileName(matchtemp, path);*/
	/*	String hexwsqdata = converthex(finaldata);
		byte[]finalwsq= myhexToASCII(hexwsqdata.getBytes());
		writeToFile(finalwsq);
	*/


            }
        });
        fpsVerifyButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {

                    File f = new File(_ChhosenPath + File.separator
                            + "Template" + ".iso");
                    if (f.exists()) {
                        FileInputStream bos = new FileInputStream(_ChhosenPath
                                + File.separator + "Template" + ".iso");
                        byte[] templatedata = new byte[bos.available()];
                        bos.read(templatedata);
                        bos.close();

                        swipestartButton.setEnabled(false);
                        swipetimeoutSpinner.setEnabled(false);
                        swipestopButton.setEnabled(false);
                        disconnectButton.setEnabled(false);
                        fpsTimeoutSpinner.setEnabled(false);
                        fpsScanperfingerSpinner.setEnabled(false);
                        fpsTemplateformatSpinner.setEnabled(false);
                        fpsScanButton.setEnabled(false);
                        fpsVerifyButton.setEnabled(false);
                        fpsStopButton.setEnabled(true);
                        slotNumberSpinner.setEnabled(false);
                        smartcardTypeSpinner.setEnabled(false);
                        userIDEditText.setEnabled(false);
                        // userNameEditText.setEnabled(false);
                        smartcardReadButton.setEnabled(false);
                        smartcardWriteButton.setEnabled(false);
                        smartcardStopButton.setEnabled(false);

                        mPrintButton.setEnabled(false);

                        mPrinttestSlipButton.setEnabled(false);
                        mPrintBarcodeButton.setEnabled(false);
                        mPrinterStatusButton.setEnabled(false);

                        mMonalisaButton.setEnabled(false);

                        bluetoothCommunication.MatchTemplate(
                                fpsTimeoutSpinner.getSelectedItemPosition(), 1,
                                templateType, templatedata);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please Scan First", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),
                            "Please Scan First", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fpsStopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                bluetoothCommunication.CancelScan();
            }
        });

        fpsTemplateformatSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {

                        switch (position) {
						/*
						 * case 0: templateType = TemplateType.ANSI_378; break;
						 * case 1: templateType =
						 * TemplateType.DIN_V66400_UNORDERED; break; case 2:
						 * templateType = TemplateType.DIN_V66400_ORDERED;
						 * break; case 3: templateType = TemplateType.ISO_FMR;
						 * break; case 4: templateType =
						 * TemplateType.ISO_FMC_CS; break; case 5: templateType
						 * = TemplateType.ISO_FMC_CS_AA; break; case 6:
						 * templateType = TemplateType.ISO_FMC_NS; break; case
						 * 7: templateType = TemplateType.MINEX_A; break; case
						 * 8: templateType = TemplateType.PK_COMP; break; case
						 * 9: templateType = TemplateType.PK_COMP_NORM; break;
						 * case 10: templateType = TemplateType.PK_MAT; break;
						 * case 11: templateType = TemplateType.PK_MAT_NORM;
						 * break; default: break;
						 */

                            case 0:
                                templateType = TemplateType.ISO_FMR;
                                templatecode = 0x6E;
                                break;
                            case 1:
                                templateType = TemplateType.DIN_V66400_UNORDERED;
                                templatecode = 0x7D;
                                break;
                            case 2:
                                templateType = TemplateType.DIN_V66400_ORDERED;
                                templatecode = 0x7E;
                                break;
                            case 3:
                                templateType = TemplateType.ANSI_378;
                                templatecode = 0x41;
                                break;
                            case 4:
                                templateType = TemplateType.ISO_FMC_CS;
                                templatecode = 0x6D;
                                break;
                            case 5:
                                templateType = TemplateType.ISO_FMC_CS_AA;
                                templatecode = 0x7F;
                                break;
                            case 6:
                                templateType = TemplateType.ISO_FMC_NS;
                                templatecode = 0x6C;
                                break;
                            case 7:
                                templateType = TemplateType.MINEX_A;
                                templatecode = 0x6F;
                                break;
                            case 8:
                                templateType = TemplateType.PK_COMP;
                                templatecode = 0x02;
                                break;
                            case 9:
                                templateType = TemplateType.PK_COMP_NORM;
                                templatecode = 0x37;
                                break;
                            case 10:
                                templateType = TemplateType.PK_MAT;
                                templatecode = 0x03;
                                break;
                            case 11:
                                templateType = TemplateType.PK_MAT_NORM;
                                templatecode = 0x35;
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });

        swipestartButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                swipestartButton.setEnabled(false);
                swipetimeoutSpinner.setEnabled(false);
                swipestopButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                fpsTimeoutSpinner.setEnabled(false);
                fpsScanperfingerSpinner.setEnabled(false);
                fpsTemplateformatSpinner.setEnabled(false);
                fpsScanButton.setEnabled(false);
                fpsVerifyButton.setEnabled(false);
                fpsStopButton.setEnabled(false);
                slotNumberSpinner.setEnabled(false);
                smartcardTypeSpinner.setEnabled(false);
                userIDEditText.setEnabled(false);
                // userNameEditText.setEnabled(false);
                smartcardReadButton.setEnabled(false);
                smartcardWriteButton.setEnabled(false);
                smartcardStopButton.setEnabled(false);

                mPrintButton.setEnabled(false);

                mPrinttestSlipButton.setEnabled(false);
                mPrintBarcodeButton.setEnabled(false);
                mPrinterStatusButton.setEnabled(false);

                mMonalisaButton.setEnabled(false);

                bluetoothCommunication.GetMSRData(swipetimeoutSpinner
                        .getSelectedItemPosition());
            }
        });


        swipestopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                bluetoothCommunication.CancelScan();

            }
        });

        connectButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(),
                        DeviceListActivity.class);
                startActivityForResult(i, 12);
            }
        });

        disconnectButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                bluetoothCommunication.StopConnection();
                disconnectButton.setEnabled(false);
                mprintslipSpinner.setEnabled(false);
                mPrintSlipButton.setEnabled(false);
                resetButton.setEnabled(false);
                connectButton.setEnabled(true);
                swipestartButton.setEnabled(false);
                swipetimeoutSpinner.setEnabled(false);
                fpsTimeoutSpinner.setEnabled(false);
                fpsScanperfingerSpinner.setEnabled(false);
                fpsTemplateformatSpinner.setEnabled(false);
                fpsScanButton.setEnabled(false);
                fpsVerifyButton.setEnabled(false);
                fpsStopButton.setEnabled(false);
                slotNumberSpinner.setEnabled(false);
                smartcardTypeSpinner.setEnabled(false);
                userIDEditText.setEnabled(false);
                // userNameEditText.setEnabled(false);
                smartcardReadButton.setEnabled(false);
                smartcardWriteButton.setEnabled(false);
                smartcardStopButton.setEnabled(false);
                // smartcardapdu.setEnabled(false);
                mPrintButton.setEnabled(false);

                mPrinttestSlipButton.setEnabled(false);
                mPrintBarcodeButton.setEnabled(false);
                mPrinterStatusButton.setEnabled(false);

                mMonalisaButton.setEnabled(false);

            }
        });
        resetButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
			/*	Intent i = new Intent(getApplicationContext(),
						NovoloanMaestrosPrintActivity.class);
				startActivity(i);
			*/
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
                fpsScanButton.setEnabled(true);
                fpsVerifyButton.setEnabled(false);
                fpsStopButton.setEnabled(false);
                recievedTextView.setText("");
                EnableAll();
                Finger_Image.setImageBitmap(null);

            }
        });
    }

    public void GenerateImage(byte[] data) {
        int[] Imagedata = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            Imagedata[i] = (int) (data[i] & 0xFF);
        }
        int rows = ((int) (Imagedata[3] << 8) + Imagedata[2]);// ByteBuffer.wrap(rawHeader).order(mOrder).getInt(1);
        int columns = ((int) Imagedata[5] << 8) + Imagedata[4];// ByteBuffer.wrap(rawHeader).order(mOrder).getInt(1);
        // int vres = ((int) Imagedata[7] << 8) + Imagedata[6];//
        // ByteBuffer.wrap(rawHeader).order(mOrder).getInt(1);
        // int hres = ((int) Imagedata[9] << 8) + Imagedata[8];//
        // ByteBuffer.wrap(rawHeader).order(mOrder).getInt(1);
        // fingerMessageTextView.setText("Rows : " + rows + " Columns : "
        // + columns + " Vres : " + vres + " Hres : " + hres);
        Bitmap bitmap = Bitmap.createBitmap(rows, columns, Config.RGB_565);
        bitmap.setHasAlpha(true);
        int counter = 12;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                bitmap.setPixel(i, j, Color.argb(255, Imagedata[counter],
                        Imagedata[counter], Imagedata[counter]));
                counter++;
            }
        }
        // Bitmap bmp=BitmapFactory.decodeByteArray(data,0,data.length);
        Finger_Image.setImageBitmap(bitmap);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            connectButton.setEnabled(false);
            Toast.makeText(getApplicationContext(),
                    "Device selected : " + data.getStringExtra("Device"),
                    Toast.LENGTH_SHORT).show();

            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                    .getDefaultAdapter();
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(data
                    .getStringExtra("Device"));
            bluetoothCommunication = new DeviceBluetoothCommunication();
            bluetoothCommunication.StartConnection(device, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothCommunication != null)
            bluetoothCommunication.StopConnection();
    }

    protected void showFileChooser() {

        // Create DirectoryChooserDialog and register a callback
        DirectoryChooserDialog directoryChooserDialog = new DirectoryChooserDialog(
                ctx, new DirectoryChooserDialog.ChosenDirectoryListener() {
            @Override
            public void onChosenDir(String chosenDir) {
                SharedPreferences preferences = ctx
                        .getSharedPreferences("templatepath", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("path", chosenDir);
                editor.commit();
                _ChhosenPath = preferences.getString("path", null);
                Toast.makeText(ctx,
                        "Chosen directory: " + _ChhosenPath,
                        Toast.LENGTH_LONG).show();

                System.out.println("---->> Chosen directory: "
                        + _ChhosenPath);
                filepath.setText(_ChhosenPath);
                filechooser.setText("ChangePath");

            }
        });
        // Load directory chooser dialog for initial 'm_chosenDir' directory and
        // select a required directory.
        // The registered callback will be called upon final directory selection
        directoryChooserDialog.chooseDirectory("");
        // MainActivity.GetPrintPATHfromConfigFile(filename, context)

    }

    protected void showFilePicker() {
        File mPath = new File(Environment.getExternalStorageDirectory() + "");
        FileDialog fileDialog = new FileDialog(this, mPath, ".jpg");
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
            public void fileSelected(File file) {
                try {
                    InputStream inputStream = new FileInputStream(file);
                    Bitmap bit = BitmapFactory.decodeStream(inputStream);
                    GetPrintableImage getPrintableImage = new GetPrintableImage();
                    prnRasterImg = getPrintableImage.GetPrintableArray(
                            AccordionWidgetDemoActivity.this, bit.getWidth(),
                            bit);
                    image_width = getPrintableImage.getPrintWidth();
                    image_height = getPrintableImage.getPrintHeight();
                    getPrintableImage = null;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    // sleep(1000);

                    byte[] CommandImagePrint = new byte[prnRasterImg.length + 5];

                    CommandImagePrint[0] = 0x1B; // Command to for bit image
                    // mode
                    // please refer the previous
                    // document
                    CommandImagePrint[1] = 0x23; // Exc #
                    CommandImagePrint[2] = (byte) image_width; // 8 Vertical
                    // Dots(Heights)
                    // &
                    // Single Width
                    // Mode

                    // selected
                    CommandImagePrint[3] = (byte) (image_height / 256);// f8 //
                    // Decimal
                    // 248
                    // since
                    // the
                    // Image
                    // width
                    // is

                    // 248 Pixels as mentioned above
                    CommandImagePrint[4] = (byte) (image_height % 256);

                    for (int i = 0; i < prnRasterImg.length; i++) {
                        CommandImagePrint[i + 5] = (byte) (prnRasterImg[i] & 0xFF);
                    }

                    bluetoothCommunication.SendData(CommandImagePrint); // CommandImagePrint.length
                    count = 14;
                    isLongSlip = true;
                    // bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();

                } catch (Exception ioe) {
                    System.out
                            .println("Problems reading from or writing to serial port."
                                    + ioe.getMessage());

                }


                Log.d(getClass().getName(), "selected file " + file.toString());
            }
        });
        //fileDialog.addDirectoryListener(new FileDialog.DirectorySelectedListener() {
        //  public void directorySelected(File directory) {
        //      Log.d(getClass().getName(), "selected dir " + directory.toString());
        //  }
        //});
        //fileDialog.setSelectDirectoryOption(false);
        fileDialog.showDialog();
        // Create DirectoryChooserDialog and register a callback

        // MainActivity.GetPrintPATHfromConfigFile(filename, context)

    }

    private void CreateMF(int slotnumber) {

        byte[] create_mf_command = {0x00, (byte) 0xE0, 0x00, 0x00, 0x10, 0x62,
                0x0E, (byte) 0x82, 0x01, 0x38, (byte) 0x83, 0x02, 0x3F, 0x00,
                (byte) 0x84, 0x05, 0x4D, 0x45, 0x54, 0x53, 0x4C};
        // mConnection.sendData(create_mf_command, 0);
        bluetoothCommunication.CPUSmartCardCommand(create_mf_command,
                slotnumber);

    }

    private void SelectMF(int slotnumber) {

        byte[] Select_MF_Command = {0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3F,
                0x00};
        // mConnection.sendData(Select_MF_Command, 0);
        bluetoothCommunication.CPUSmartCardCommand(Select_MF_Command,
                slotnumber);
        smartcardcommand = SmartCardCommand.SelectMF;

    }

    private void createEF(int slotnumber, int maxbytesinfile, int fileid) {

        byte[] Create_EF_User_ID_Command = {0x00, (byte) 0xE0, 0x00, 0x00,
                0x0E, 0x62, 0x0C, (byte) 0x80, 0x02, 0x00,
                (byte) maxbytesinfile, (byte) 0x82, 0x02, 0x01, 0x01,
                (byte) 0x83, 0x02, 0x30, (byte) fileid};
        // mConnection.sendData(Create_EF_User_ID_Command, 0);
        // smartcardcommand = SmartCardCommand.CreateEF ;
        bluetoothCommunication.CPUSmartCardCommand(Create_EF_User_ID_Command,
                slotnumber);

    }

    private void SelectEF(int slotnumber, int fileid) {

        byte[] Select_EF_User_ID_Command = {0x00, (byte) 0xA4, 0x00, 0x00,
                0x02, 0x30, (byte) fileid};
        // mConnection.sendData(Select_EF_User_ID_Command, 0);
        bluetoothCommunication.CPUSmartCardCommand(Select_EF_User_ID_Command,
                slotnumber);
        // smartcardcommand = SmartCardCommand.SelectEF ;

    }

    private void write_To_CPU(int slotnumber, byte[] data) {

        byte[] User_ID_Write_Command = new byte[(6 + data.length)];
        int Length = data.length;
        // User_ID_Write_Command[0] = 0x1B;
        // User_ID_Write_Command[1] = 0x2E;
        // User_ID_Write_Command[2] = (byte) slotnumber;
        User_ID_Write_Command[0] = 0x00;
        User_ID_Write_Command[1] = (byte) 0xD6;
        User_ID_Write_Command[2] = 0x00;
        User_ID_Write_Command[3] = 0x00;
        User_ID_Write_Command[4] = (byte) (Length + 1);
        User_ID_Write_Command[5] = (byte) Length;
        // Length += 6;
        // User_ID_Write_Command[3] = (byte) Length;
        // User_ID_Write_Command[4] = (byte) (Length >> 8);

        System.arraycopy(data, 0, User_ID_Write_Command, 6, Length);

        // mConnection.sendData(User_ID_Write_Command, 0);
        // smartcardcommand = SmartCardCommand.Write_CPU;

        bluetoothCommunication.CPUSmartCardCommand(User_ID_Write_Command,
                slotnumber);

    }

    private void Read_From_CPU() {

        byte[] User_ID_Read_Command = new byte[5];

        // User_ID_Read_Command[0] = 0x1B;
        // User_ID_Read_Command[1] = 0x2E;
        // User_ID_Read_Command[2] = (byte) SlotNumber;
        // User_ID_Read_Command[3] = 0x05;
        // User_ID_Read_Command[4] = 0x00;
        User_ID_Read_Command[0] = 0x00;
        User_ID_Read_Command[1] = (byte) 0xB0;
        User_ID_Read_Command[2] = 0x00;
        User_ID_Read_Command[3] = 0x00;
        User_ID_Read_Command[4] = (byte) 210;

        // mConnection.sendData(User_ID_Read_Command, 0);
        // smartcardcommand = SmartCardCommand.Read_CPU;
        bluetoothCommunication.CPUSmartCardCommand(User_ID_Read_Command,
                slotnumber);

    }

    @Override
    public void onCancelledCommand() {

        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);

    }

    @Override
    public void onCommandRecievedWhileAnotherRunning() {

        recievedTextView.setText("Command Recieved  While Another Running.");
    }

    @Override
    public void onCommandRecievedWhileProcessing() {

        recievedTextView.setText("Command Recieved While Processing.");
    }

    File newTextFile = new File(Environment
            .getExternalStorageDirectory()
            + File.separator
            + "AppPathWSQ"
            + File.separator + "hexfulldata.txt");
    File dynamicFolder = new File(Environment
            .getExternalStorageDirectory()
            + File.separator
            + "AppPathWSQ");

    @Override
    public void onConnectComplete() {
		/*new Handler().postDelayed(new Runnable() {
		    public void run () {
		    	bluetoothCommunication.GetWSQData();

		    }
		}, 2000L);
		new Handler().postDelayed(new Runnable() {
		    public void run () {
		    	byte[] buffer = { 0x1B, 0x73,0x6E,0x00,0x00,0x01,0x00};
		        Log.i("WSQTAG","WSQ Command Sent");
		        bluetoothCommunication.SendData(buffer);

		    }
		}, 2000L);*/

        if (!dynamicFolder.exists())
            dynamicFolder.mkdir();

        DeviceBluetoothCommunication.path = newTextFile.getAbsolutePath();
        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
        resetButton.setEnabled(true);
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);
        mPrintSlipButton.setEnabled(true);
        mprintslipSpinner.setEnabled(true);
        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);
        mBatteryStat.setEnabled(true);
        mDisableAutoOffButton.setEnabled(true);
        mHexLogoButton.setEnabled(true);
        btMatchWSQ.setEnabled(true);
        // smartcardapdu.setEnabled(true);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);
        btgetSerialno.setEnabled(true);
        mMonalisaButton.setEnabled(true);

        count = 0;

		/*isLongSlip = true;
		 String textData = "AMRAVATI MUNICIPAL CORPORATION\n";
	        bluetoothCommunication.SendData(textData.getBytes());
	        bluetoothCommunication.LineFeed();
	        bluetoothCommunication.GetPrinterStatus();
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}*/

    }

    @Override
    public void onConnectionFailed() {

        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        swipestartButton.setEnabled(false);
        swipetimeoutSpinner.setEnabled(false);
        fpsTimeoutSpinner.setEnabled(false);
        fpsScanperfingerSpinner.setEnabled(false);
        fpsTemplateformatSpinner.setEnabled(false);
        fpsScanButton.setEnabled(false);
        fpsVerifyButton.setEnabled(false);
        fpsStopButton.setEnabled(false);
        slotNumberSpinner.setEnabled(false);
        smartcardTypeSpinner.setEnabled(false);
        userIDEditText.setEnabled(false);
        mPrintSlipButton.setEnabled(false);
        mprintslipSpinner.setEnabled(false);
        // userNameEditText.setEnabled(false);
        smartcardReadButton.setEnabled(false);
        smartcardWriteButton.setEnabled(false);
        smartcardStopButton.setEnabled(false);
        // smartcardapdu.setEnabled(false);

        mPrintButton.setEnabled(false);

        mPrinttestSlipButton.setEnabled(false);
        mPrintBarcodeButton.setEnabled(false);
        mPrinterStatusButton.setEnabled(false);

        mMonalisaButton.setEnabled(false);
    }

    @Override
    public void onCryptographicError() {

        recievedTextView.setText("Cryptographic error.");
    }

    @Override
    public void onErrorOccured() {

        recievedTextView
                .setText("Error occurs during the execution of the function.");
    }

    @Override
    public void onErrorOccuredWhileProccess() {

        recievedTextView.setText("Error occurs during process.");
    }

    @Override
    public void onErrorReadingSmartCard() {

        recievedTextView.setText("Error occurs while reading from card.");
    }

    @Override
    public void onErrorWritingSmartCard() {

        recievedTextView.setText("Error occurs while writing to card.");
    }

    @Override
    public void onFalseFingerDetected() {

        recievedTextView.setText("False Finger Detected.");
    }

    @Override
    public void onFingerImageRecieved(byte[] data) {
        if (fingerimageenable == false) {
            recievedTextView.setText("Finger Image recieved.");
            String fingerhex = bytesToHex(data);
            System.out.print("Fingerhex " + fingerhex);
            GenerateImage(data);
        }
    }

    @Override
    public void onFingerPrintTimeout() {

        recievedTextView.setText("Scan Timeout.");
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);

    }

    String aakashwsq, str1, str2, split_final;

    private String readFromFile(String fname) {

        String ret = "";

        try {
            InputStream inputStream = openFileInput(fname);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("gyug", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public String converthex(String hex) {
	/*	String myreplace,myreplace2,myreplace3,myreplace4;
		int index = StringUtils.ordinalIndexOf(hex, "1B03", 3) ;
		int pos = -1;
		for (int i = 0; i < 3; i++) {
		    pos = hexwsq.indexOf("1B3D", pos + 1);
		}
        str1 = hex.substring(0,index+4);
		str2 = hex.substring(index+4);
		myreplace = str2.replaceAll("1B12", "11");
		myreplace2=myreplace.replaceAll("1B14", "13");
		myreplace3=myreplace2.replaceAll("1B1B", "1B");
		myreplace4=myreplace3.replaceAll("1B1B", "1B");

	*/
		/* StringBuilder sb = new StringBuilder();
		    sb.append(myreplace4);
		    String[] myArray = sb.toString().split("1B03");
		    ArrayList<String> as = new ArrayList<String>();

		    String[] mArray2 = new String[myArray.length];
		    StringBuilder newsb = new StringBuilder();

		    for (int i =0;i<myArray.length;i++){
		    	//mArray2[i]=Arrays.copyOfRange(Byte.parseByte(myArray[i]), 1, 3);
		    	if(i==0)
		    	{
		    	String ss1 = myArray[i].substring(0,myArray[i].length()-4 );
		    newsb.append(ss1);
		    	}
		    	else
		    	{
			    	String ss1 = myArray[i].substring(6,myArray[i].length()-4 );

			        newsb.append(ss1);
		    	}





		*/
        aakashwsq = hex;
        int pos = aakashwsq.indexOf("3d");
        int check9c = pos + 26;
        String a_letter = Character.toString(aakashwsq.charAt(check9c));
        String b_letter = Character.toString(aakashwsq.charAt(check9c + 1));
        String c_letter = a_letter + b_letter;
        if (c_letter.contains("9c")) {
            int splitindex = check9c + 28;
            split_final = aakashwsq.substring(splitindex);

            return split_final;


        }
        return split_final;

    }

    @Override
    public void onFingerScanStarted(int scan) {

        recievedTextView.setText("Finger scan " + scan + " started");
    }

    @Override
    public void onFingerTooMoist() {

        recievedTextView.setText("Finger too moist.");
    }

    @Override
    public void onFingeracquisitioncompeted(String msg) {

        recievedTextView.setText(msg);
        fpsStopButton.setEnabled(false);
    }

    @Override
    public void onImproveSwipe() {

        recievedTextView.setText("Improper Swipe");
        EnableAll();
    }

    @Override
    public void onInvalidCommand() {

        recievedTextView.setText("Invalid Command");
        EnableAll();

    }

    @Override
    public void onLatentFingerHard(String msg) {

        recievedTextView.setText(msg);
    }

    @Override
    public void onMSRDataRecieved(String msg) {

        recievedTextView.setText(msg.trim());
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);

    }

    @Override
    public void onMoveFingerDown() {

        recievedTextView.setText("Move Finger Down.");
    }

    @Override
    public void onMoveFingerLeft() {

        recievedTextView.setText("Move Finger Left.");
    }

    @Override
    public void onMoveFingerRight() {

        recievedTextView.setText("Move Finger Right.");
    }

    @Override
    public void onMoveFingerUP() {

        recievedTextView.setText("Move Finger UP.");
    }

    @Override
    public void onNoData() {

        recievedTextView.setText("No Data.");
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);
    }

    @Override
    public void onNoSmartCardFound() {

        recievedTextView.setText("No smart card found");
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);
    }

    @Override
    public void onOperationNotSupported() {

        recievedTextView.setText("operation not supported");
    }

    @Override
    public void onParameterOutofRange() {

        recievedTextView.setText("Parameter out of range.");
    }

    @Override
    public void onPlaceFinger() {

        recievedTextView.setText("Place Finger.");
    }

    @Override
    public void onPressFingerHard() {

        recievedTextView.setText("Press Finger Hard.");
    }

    @Override
    public void onRemoveFinger() {

        recievedTextView.setText("Remove Finger.");
    }

    @Override
    public void onSameFinger() {

        recievedTextView.setText("Same Finger Used.");
    }

    @Override
    public void onSmartCardDataRecieved(byte[] data) {

        recievedTextView.setText("Data on Smart Card :"
                + (new String(data)).trim());

		/*
		 * if (cardType == CardType.CPU) { switch (smstatus) { case READ_USERID:
		 * recievedTextView.setText("User ID :" + (new String(data)).trim());
		 *
		 * bluetoothCommunication.ReadfromSmartCard(
		 * slotNumberSpinner.getSelectedItemPosition() + 1, 50, 200, cardType,
		 * 7, 210); smstatus = SMSTATUS.READ_USERNAME; fileid = 7;
		 * SelectMF(slotnumber);
		 *
		 * break; case READ_USERNAME:
		 * recievedTextView.setText(recievedTextView.getText().toString() + "\n"
		 * + "User Name :" + new String(data)); fileid = 6;
		 * swipestartButton.setEnabled(true);
		 * swipetimeoutSpinner.setEnabled(true);
		 * swipestopButton.setEnabled(false); disconnectButton.setEnabled(true);
		 *
		 * fpsTimeoutSpinner.setEnabled(true);
		 * fpsScanperfingerSpinner.setEnabled(true);
		 * fpsTemplateformatSpinner.setEnabled(true);
		 * fpsScanButton.setEnabled(true); fpsVerifyButton.setEnabled(true);
		 * fpsStopButton.setEnabled(false);
		 *
		 * slotNumberSpinner.setEnabled(true);
		 * smartcardTypeSpinner.setEnabled(true);
		 * userIDEditText.setEnabled(true); userNameEditText.setEnabled(true);
		 * smartcardReadButton.setEnabled(true);
		 * smartcardWriteButton.setEnabled(true);
		 * smartcardStopButton.setEnabled(false);
		 *
		 * mPrintButton.setEnabled(true);
		 * mDisableAutoOffButton.setEnabled(true);
		 * mPrinttestSlipButton.setEnabled(true);
		 * mPrintBarcodeButton.setEnabled(true);
		 * mPrinterStatusButton.setEnabled(true);
		 *
		 * mMonalisaButton.setEnabled(true); break; default: break; } } else {
		 * String value = new String(data); String[] val = value.split(",");
		 * recievedTextView.setText("User Id : " + val[0] + "\n" +
		 * "User Name : " + val[1]); EnableAll(); }
		 */

        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);

    }

    @Override
    public void onSmartCardPresent() {

        if (cardType == CardType.CPU) {

            SelectMF(slotnumber);

        } else {
            switch (smstatus) {
                case READ_MEMORY:
                    bluetoothCommunication.ReadfromSmartCard(
                            slotNumberSpinner.getSelectedItemPosition() + 1, 50,
                            200, cardType, 6, 210);

                    break;
                case WRITE_MEMORY:
                    String data = userIDEditText.getText().toString() + ","
                            + userIDEditText.getText().toString();

                    byte[] useridarray = data.getBytes();
                    bluetoothCommunication.WritetoSmartCard(
                            slotNumberSpinner.getSelectedItemPosition() + 1, 50,
                            useridarray.length, useridarray, cardType, 6, 210);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onTemplateRecieved(byte[] data) {
        if (enablededupscan) {
            myfptemplatelist.add(data);
            if (myfptemplatelist.size() == 2) {

                templatecodeList = new ArrayList<Byte>();
                fingerprintList = new ArrayList<byte[]>();
                for (int k = 1; k < dedupcounter + 1; k++) {
                    templatecodeList.add((byte) 108);

                }

                bluetoothCommunication.DeDuplicationMatchTemplate(myfptemplatelist.size(),
                        templatecodeList, myfptemplatelist);

            }
            if (myfptemplatelist.size() >= 3) {
                int i1 = myfptemplatelist.size() - 1;
                byte[] mysearchedtemp = myfptemplatelist.get(i1);
                myfptemplatelist.remove(i1);
                myfptemplatelist.add(0, mysearchedtemp);
                int i2 = myfptemplatelist.size();
                for (int k = 1; k < dedupcounter + 1; k++) {
                    templatecodeList.add((byte) 108);

                }

                bluetoothCommunication.DeDuplicationMatchTemplate(i2,
                        templatecodeList, myfptemplatelist);
            }
            try {
                File f = new File(_ChhosenPath + File.separator + "Template" + ".iso");
                if (f.exists()) {
                    f.delete();
                }
                FileOutputStream bos = new FileOutputStream(_ChhosenPath
                        + File.separator + "Template" + ".iso", true);
                bos.write(data);
                bos.flush();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            swipestartButton.setEnabled(true);
            swipetimeoutSpinner.setEnabled(true);
            swipestopButton.setEnabled(false);
            disconnectButton.setEnabled(true);

            fpsTimeoutSpinner.setEnabled(true);
            fpsScanperfingerSpinner.setEnabled(true);
            fpsTemplateformatSpinner.setEnabled(true);
            fpsScanButton.setEnabled(true);
            fpsVerifyButton.setEnabled(true);
            fpsStopButton.setEnabled(false);

            slotNumberSpinner.setEnabled(true);
            smartcardTypeSpinner.setEnabled(true);
            userIDEditText.setEnabled(true);
            // userNameEditText.setEnabled(true);
            smartcardReadButton.setEnabled(true);
            smartcardWriteButton.setEnabled(true);
            smartcardStopButton.setEnabled(false);

            mPrintButton.setEnabled(true);

            mPrinttestSlipButton.setEnabled(true);
            mPrintBarcodeButton.setEnabled(true);
            mPrinterStatusButton.setEnabled(true);

            mMonalisaButton.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Template Received", Toast.LENGTH_LONG).show();


        } else {
            try {
                File f = new File(_ChhosenPath + File.separator + "Template.iso");
                if (f.exists()) {
                    f.delete();
                }
                FileOutputStream bos = new FileOutputStream(_ChhosenPath
                        + File.separator + "Template.iso", true);
                bos.write(data);
                bos.flush();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String bstemplate = Base64.encodeToString(data, Base64.DEFAULT);
            //recievedTextView.setText("Template Received"/*bstemplate*/);
            Toast.makeText(getApplicationContext(), "Template Received", Toast.LENGTH_LONG).show();
            swipestartButton.setEnabled(true);
            swipetimeoutSpinner.setEnabled(true);
            swipestopButton.setEnabled(false);
            disconnectButton.setEnabled(true);

            fpsTimeoutSpinner.setEnabled(true);
            fpsScanperfingerSpinner.setEnabled(true);
            fpsTemplateformatSpinner.setEnabled(true);
            fpsScanButton.setEnabled(true);
            fpsVerifyButton.setEnabled(true);
            fpsStopButton.setEnabled(false);

            slotNumberSpinner.setEnabled(true);
            smartcardTypeSpinner.setEnabled(true);
            userIDEditText.setEnabled(true);
            // userNameEditText.setEnabled(true);
            smartcardReadButton.setEnabled(true);
            smartcardWriteButton.setEnabled(true);
            smartcardStopButton.setEnabled(false);

            mPrintButton.setEnabled(true);

            mPrinttestSlipButton.setEnabled(true);
            mPrintBarcodeButton.setEnabled(true);
            mPrinterStatusButton.setEnabled(true);

            mMonalisaButton.setEnabled(true);
        }
    }

    int duplicatetempnumber;

    @Override
    public void onVerificationSuccessful(int templatenumber) {
        if (enablededupscan) {
            duplicatetempnumber = templatenumber;
            recievedTextView.setText("Finger Already Exist.");
            myfptemplatelist.remove(templatenumber);
            //	templatecodeList.remove(templatenumber);
		/*File file = new File(_ChhosenPath+ File.separator + "Template"+templatenumber+".iso");
		boolean deleted = file.delete();
		dedupnewfinger=false;*/
        } else {
            recievedTextView.setText("Verification Successful.");
        }
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);
    }

    @Override
    public void onVerificationfailed() {
        if (enablededupscan) {
            recievedTextView.setText("New Finger Saved.");
            dedupnewfinger = true;

        } else {
            recievedTextView.setText("Verification Failed.");
        }


        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);
    }

    @Override
    public void onWriteToSmartCardSuccessful() {

        recievedTextView.setText("Data written on SmartCard Successfully.");

		/*
		 * if (cardType == CardType.CPU) { switch (smstatus) { case
		 * WRITE_USERID:
		 * recievedTextView.setText("User ID writtern Successfully."); byte[]
		 * usernamearray = userNameEditText.getText().toString() .getBytes();
		 * fileid = 7; SelectMF(slotnumber); smstatus = SMSTATUS.WRITE_USERNAME;
		 *
		 * break; case WRITE_USERNAME: fileid = 6;
		 * recievedTextView.setText(recievedTextView.getText().toString() + "\n"
		 * + "User Name writtern Successfully.");
		 * swipestartButton.setEnabled(true);
		 * swipetimeoutSpinner.setEnabled(true);
		 * swipestopButton.setEnabled(false); disconnectButton.setEnabled(true);
		 *
		 * fpsTimeoutSpinner.setEnabled(true);
		 * fpsScanperfingerSpinner.setEnabled(true);
		 * fpsTemplateformatSpinner.setEnabled(true);
		 * fpsScanButton.setEnabled(true); fpsVerifyButton.setEnabled(true);
		 * fpsStopButton.setEnabled(false);
		 *
		 * slotNumberSpinner.setEnabled(true);
		 * smartcardTypeSpinner.setEnabled(true);
		 * userIDEditText.setEnabled(true); userNameEditText.setEnabled(true);
		 * smartcardReadButton.setEnabled(true);
		 * smartcardWriteButton.setEnabled(true);
		 * smartcardStopButton.setEnabled(false);
		 *
		 * mPrintButton.setEnabled(true);
		 * mDisableAutoOffButton.setEnabled(true);
		 * mPrinttestSlipButton.setEnabled(true);
		 * mPrintBarcodeButton.setEnabled(true);
		 * mPrinterStatusButton.setEnabled(true);
		 *
		 * mMonalisaButton.setEnabled(true); break;
		 *
		 * default: break; } } else {
		 * recievedTextView.setText("Data writtern Successfully."); EnableAll();
		 * }
		 */

        // recievedTextView.setText(recievedTextView.getText().toString()
        // + "\n" + "User Name writtern Successfully.");
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);

        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);

        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);

        mPrintButton.setEnabled(true);

        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);

        mMonalisaButton.setEnabled(true);
    }

    @Override
    public void onOutofPaper() {

        recievedTextView.setText("Printer Out of Paper.");
        EnableAll();
    }

    @Override
    public void onPlatenOpen() {

        recievedTextView.setText("Platen is Open.");
    }

    @Override
    public void onHighHeadTemperature() {

        recievedTextView.setText("High Head Temperature.");
    }

    @Override
    public void onLowHeadTemperature() {

        recievedTextView.setText("Head Temperature is Low.");

    }

    @Override
    public void onImproperVoltage() {

        recievedTextView.setText("Improper Voltage.");

    }

    @Override
    public void onSuccessfulPrintIndication() {

        recievedTextView.setText("Indication for successful print.");
        // mPrintSlipButton.setEnabled(false);
        if (isLongSlip) {
            switch (count) {

                case 0:
                    printImagedata();
                    //printAxisLogoFromImage();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 1:
                    bluetoothCommunication.SendData(transactionName.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 2:
                    bluetoothCommunication.SendData(date.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 3:
                    bluetoothCommunication.SendData(time.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 4:
                    bluetoothCommunication.SendData(bcName.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 5:
                    bluetoothCommunication.SendData(bcLoc.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 6:
                    bluetoothCommunication.SendData(agentID.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 7:
                    bluetoothCommunication.SendData(tID.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 8:
                    bluetoothCommunication.SendData(aadharNo.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 9:
                    bluetoothCommunication.SendData(custName.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;

                case 10:
                    bluetoothCommunication.SendData(uidaiAuthCode.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 11:
                    bluetoothCommunication.SendData(status.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;
                case 12:
                    bluetoothCommunication.SendData(totalAmountString.getBytes());
                    bluetoothCommunication.LineFeed();
                    bluetoothCommunication.GetPrinterStatus();
                    count++;
                    break;

                case 13:
                    printImagedata();
                    //printAxisLogoFromImage();
                    bluetoothCommunication.GetPrinterStatus();
                    if (slipcounter == slipnumber) {
                        mPrintSlipButton.setEnabled(true);
                        count++;
                        // bluetoothCommunication.LineFeed();
                        // bluetoothCommunication.GetPrinterStatus();
                    } else {
                        slipcounter++;
                        count = 0;
                    }

                    break;

                default:
                    bluetoothCommunication.LineFeed();

                    EnableAll();
                    isLongSlip = false;
                    break;
            }
        }

    }

    int slipcounter = 1;

    class BTPrnImage extends Thread {
        // private String receipt;

        private int width, height;
        private int[] img;

		/*
		 * public void setReceipt(String r) { receipt = r; }
		 */

        public BTPrnImage(int l_width, int l_height, int[]

                img_raster) {

            img = img_raster;
            width = l_width;
            height = l_height;
        }

        public void run() {

            try {
                sleep(1000);

                byte[] CommandImagePrint = new byte[254];
                // int ImageYPointer = 0, ImageXPointer = 0;
                int ImageHeight = height, ImageWidth = width;
                // int ImageCounter = 0;

                // // Command to set printer head

                // position to 150th Dot

                CommandImagePrint[0] = 0x1B; // Command to for bit image mode
                // please refer the previous

                // document
                CommandImagePrint[1] = 0x23; // Exc #
                CommandImagePrint[2] = (byte) ImageWidth; // 8 Vertical
                // Dots(Heights) &
                // Single Width Mode

                // selected
                CommandImagePrint[3] = (byte) (ImageHeight / 256);// f8 //
                // Decimal
                // 248 since
                // the Image
                // width is

                // 248 Pixels as mentioned above
                CommandImagePrint[4] = (byte) (ImageHeight % 256);

                byte[] CommandImageData = new byte[5];
                for (int i = 0; i < CommandImageData.length; i++) {
                    CommandImageData[i] = (CommandImagePrint[i]);
                }
                // Send Command to print image
                bluetoothCommunication.SendData(CommandImageData);

                CommandImagePrint = new byte[img.length];
                for (int i = 0; i < CommandImagePrint.length; i++) {
                    CommandImagePrint[i] = (byte) (img[i] & 0xFF);
                }

                bluetoothCommunication.SendData(CommandImagePrint); // CommandImagePrint.length

                byte[] CommandImagePrint2 = {0x1B, 0x32}; // Command to set
                // Default Line
                // Spacing

                // Send Command to set Default Line Spacing
                bluetoothCommunication.SendData(CommandImagePrint2);
                CommandImagePrint2[0] = 0x0A;
                CommandImagePrint2[1] = 0x0A;
                bluetoothCommunication.SendData(CommandImagePrint2);
                sleep(2000);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        EnableAll();
                    }
                });

            } catch (Exception ioe) {
                System.out
                        .println("Problems reading from or writing to serial port."
                                + ioe.getMessage());

            }

        }
    }

    private void EnableAll() {
        swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(true);
        mprintslipSpinner.setEnabled(true);
        fpsTimeoutSpinner.setEnabled(true);
        fpsScanperfingerSpinner.setEnabled(true);
        fpsTemplateformatSpinner.setEnabled(true);
        fpsScanButton.setEnabled(true);
        fpsVerifyButton.setEnabled(true);
        fpsStopButton.setEnabled(false);
        slotNumberSpinner.setEnabled(true);
        smartcardTypeSpinner.setEnabled(true);
        userIDEditText.setEnabled(true);
        // userNameEditText.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);
        smartcardReadButton.setEnabled(true);
        smartcardWriteButton.setEnabled(true);
        smartcardStopButton.setEnabled(false);
        mPrintSlipButton.setEnabled(true);
        mPrintButton.setEnabled(true);
        mBatteryStat.setEnabled(true);
        mDisableAutoOffButton.setEnabled(true);
        mHexLogoButton.setEnabled(true);
        btMatchWSQ.setEnabled(true);
        mPrinttestSlipButton.setEnabled(true);
        mPrintBarcodeButton.setEnabled(true);
        mPrinterStatusButton.setEnabled(true);
        mMonalisaButton.setEnabled(true);
    }

    @Override
    public void onCPUSmartCardCommandDataRecieved(byte[] data) {

        int Data_Length = 0;
        Data_Length = data[0] & 0xFF;
        Data_Length <<= 8;
        Data_Length |= data[1] & 0xFF;

        switch (smartcardcommand) {

            case Read_CPU:

                if ((Data_Length > 0)) {

                    // Create a byte array buffer to hold the incoming
                    // data
                    byte[] Tempbuffer1 = new byte[(data[0] & 0xFF)];

                    for (int k = 1; k <= Tempbuffer1.length; k++)
                        Tempbuffer1[k - 1] = data[k];

                    onSmartCardDataRecieved(Tempbuffer1);

                } else {

                    onNoData();

                }
                break;
            case Write_CPU:

                if (Data_Length == 0x9000) {
                    onWriteToSmartCardSuccessful();
                } else {
                    // Smart Card Error
                    onErrorWritingSmartCard();

                }
                break;
            case CreateEF:

                if (Data_Length == 0x9000) {

                    smartcardcommand = SmartCardCommand.SelectEF;
                    SelectEF(slotnumber, 10);

                } else {
                    // Smart Card Error

                    onInvalidCommand();

                }
                break;
            case SelectEF:

                if (Data_Length == 0x9000) {
                    if (SC_Write_Flag == true) {
                        smartcardcommand = SmartCardCommand.Write_CPU;
                        write_To_CPU(slotnumber, userid);

                    } else if (SC_Read_Flag == true) {
                        // REad from Smart Card
                        smartcardcommand = SmartCardCommand.Read_CPU;
                        Read_From_CPU();

                    }
                } else {
                    // Smart Card Error
                    if (SC_Write_Flag == true) {
                        smartcardcommand = SmartCardCommand.CreateEF;
                        createEF(slotnumber, 210, 10);
                        // mCallBacks.selectEF();

                    } else if (SC_Read_Flag == true) {
                        // No data found

                        onNoData();

                    }
                }
                break;
            case CreateMF:

                if (Data_Length == 0x9000) {
                    smartcardcommand = SmartCardCommand.SelectMF;
                    SelectMF(slotnumber);
                    // mCallBacks.createMF();

                } else {
                    // Smart Card Error

                    onInvalidCommand();

                }
                break;
            case SelectMF:

                if (Data_Length == 0x9000) {
                    smartcardcommand = SmartCardCommand.SelectEF;
                    SelectEF(slotnumber, 10);
                    // mCallBacks.selectMF();

                } else {
                    if (SC_Write_Flag == true) {
                        smartcardcommand = SmartCardCommand.CreateMF;
                        CreateMF(slotnumber);
                        // mCallBacks.selectMF();

                    } else if (SC_Read_Flag == true) {
                        // Smart Card Error

                        onInvalidCommand();

                    }
                }
                break;

        }

    }

    public void printImagedata() {
        {

            byte[] prnRasterImg = Readcsv(getBaseContext());
            bluetoothCommunication.SendData(prnRasterImg);

        }
    }

    /*
	private static byte[] Readcsv(Context context) {
		try {

			InputStream in = context.getAssets().open("logo.txt");// openFileInput();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			byte[] mona = new byte[14926];
			int cnt = 0;
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] RowData = line.split(",");
					for (int i = 0; i < RowData.length; i++) {
						mona[cnt] = (byte) Integer.parseInt(
								RowData[i].replace("0x", "").replace(" ", ""),
								16);
						cnt++;
					}

					// do something with "data" and "value"
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				// handle exception
			} finally {
				try {
					in.close();
				} catch (Exception e) {
					// handle exception
				}
			}

			return mona;
		} catch (Exception e) {
			return null;
		}
	}

	*/
    private static byte[] Readcsv(Context context) {
        try {

		/*	InputStream in = context.getAssets().open("logo1.txt");// openFileInput();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
		*/
            byte[] mona = new byte[monahex.length];

            int cnt = 0;
            try {
                String line;
				/*while ((line = reader.readLine()) != null) {
					String[] RowData = line.split(",");*/
                for (int i = 0; i < monahex.length; i++) {
                    mona[cnt] = (byte) monahex[i];
                    cnt++;
                }

                // Do something with "data" and "value"
                //}
            } catch (Exception ex) {
                ex.printStackTrace();
                // handle exception
            } finally {
                try {
                    //	in.close();
                } catch (Exception e) {
                    // handle exception
                }
            }

            return mona;
        } catch (Exception e) {
            return null;
        }
    }

    private void DisableAll() {
        slotNumberSpinner.setEnabled(false);
        smartcardTypeSpinner.setEnabled(false);
        smartcardReadButton.setEnabled(false);
        smartcardWriteButton.setEnabled(false);
        fpsScanButton.setEnabled(false);
        fpsVerifyButton.setEnabled(false);
        swipestartButton.setEnabled(false);
        swipetimeoutSpinner.setEnabled(false);
        swipestopButton.setEnabled(false);
        disconnectButton.setEnabled(false);
        fpsTimeoutSpinner.setEnabled(false);
        fpsScanperfingerSpinner.setEnabled(false);
        fpsTemplateformatSpinner.setEnabled(false);
        // userNameEditText.setEnabled(true);
        mprintslipSpinner.setEnabled(false);
        mPrinterStatusButton.setEnabled(false);
        mPrintButton.setEnabled(false);

        mPrinttestSlipButton.setEnabled(false);
        mPrintBarcodeButton.setEnabled(false);
        mPrintSlipButton.setEnabled(false);
        mMonalisaButton.setEnabled(false);
        mPrintBarcodeButton.setEnabled(false);
        mBatteryStat.setEnabled(false);
        mDisableAutoOffButton.setEnabled(false);
        mHexLogoButton.setEnabled(false);
        btMatchWSQ.setEnabled(false);

    }

    @Override
    public void onBackPressed() {
        if (bluetoothCommunication != null)
            bluetoothCommunication.StopConnection();
        finish();
    }

    @Override
    public void onCorruptDataRecieved() {

    }

    @Override
    public void onCorruptDataSent() {

    }

    @Override
    public void onInternalFPModuleCommunicationerror() {

    }

    @Override
    public void onNoResponseFromCard() {

    }

    @Override
    public void onCardNotSupported() {

    }

    @Override
    public void onCommandNotSupported() {

    }


    @Override
    public void onNFIQ(int nfiq) {
        System.out.println("NFIQ : " + nfiq);
        recievedTextView.setText("NFIQ Score :" + String.valueOf(nfiq));
        //	Toast.makeText(AccordionWidgetDemoActivity.this, "NFIQ Value : "+String.valueOf(nfiq), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSerialNumber(byte[] serno) {
        //	byte[] sno={ 16, 17, 8, 86, 66, 53, 55, 45, 49, 53, 48, 53, 49, 56, 54, 48, 48, 48, 52};
        String ser = new String(serno);
        System.out.println("Text Decryted : " + ser);
        //	Toast.makeText(getApplicationContext(), ser.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), ser, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onVersionNumberReceived(byte[] verno) {
        String ver = new String(verno);
        Toast.makeText(getApplicationContext(), "Version : " + ver, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onWSQFingerReceived(byte[] wsqData) {
        String wsqfingerhex = bytesToHex(wsqData);
		/*String mywsq=converthex(wsqfingerhex);
		byte[] finascistr =myhexToASCII(mywsq.getBytes());
		writeToFile(finascistr);
*/
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        getBaseContext().unregisterReceiver(_wsqreceiver);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        _wsqreceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (action
                        .equals(CommandReceiveThread.ACTION_EVENT_WSQDATA_RECIEVED)) {
                    String message = intent
                            .getStringExtra("wsqdata");
                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter bintentFilter = new IntentFilter(
                CommandReceiveThread.ACTION_EVENT_WSQDATA_RECIEVED);
        getBaseContext().registerReceiver(_wsqreceiver, bintentFilter);

    }

    public static long toAscii(String s) {
        StringBuilder sb = new StringBuilder();
        String ascString = null;
        long asciiInt;
        for (int i = 0; i < s.length(); i++) {
            sb.append((int) s.charAt(i));
            char c = s.charAt(i);
        }
        ascString = sb.toString();
        asciiInt = Long.parseLong(ascString);
        return asciiInt;
    }

    private void writeStringToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("/storage/emulated/0/AakashWSQ/stringaakash.wsq", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /*public static String readFromAssets(Context context, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(filename.to);

        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }*/
    public String convertHexToString(String hex) {


        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String stringToHex(String base) {
        StringBuffer buffer = new StringBuffer();
        int intValue;
        for (int x = 0; x < base.length(); x++) {
            int cursor = 0;
            intValue = base.charAt(x);
            String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
            for (int i = 0; i < binaryChar.length(); i++) {
                if (binaryChar.charAt(i) == '1') {
                    cursor += 1;
                }
            }
            if ((cursor % 2) > 0) {
                intValue += 128;
            }
            buffer.append(Integer.toHexString(intValue) + " ");
        }
        return buffer.toString();
    }

    public static String hexToASCII(String hex) {
        if (hex.length() % 2 != 0) {
            System.err.println("requires EVEN number of chars");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        //Convert Hex 0232343536AB into two characters stream.
        for (int i = 0; i < hex.length(); i += 2) {
             /*
              * Grab the hex in pairs
              */
            String output = hex.substring(i, (i + 2));
            /*
             * Convert Hex to Decimal
             */
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);

        }
        return sb.toString();
    }

    private static byte[] myhexToASCII(byte[] hexValue) {
        byte[] myarray = new byte[hexValue.length / 2];
        for (int i = 0, j = 0; i < hexValue.length - 1; i += 2, j++) {
            //int str = hexValue.length;
            myarray[j] = (byte) hex_to_ascii(hexValue[i], hexValue[i + 1]);


        }
		/*StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexValue.length; i += 2)
		{
					//int str = hexValue.length;
			char mychar = hex_to_ascii   (hexValue[i],hexValue[i+1]);
			output.append(mychar);

		}*/
        return myarray;
    }

    static int hex_to_int(byte c) {
        if (c >= 97)
            c -= 32;
        int first = c / 16 - 3;
        int second = c % 16;
        int result = first * 10 + second;
        if (result > 9) result--;
        return result;
    }

    static char hex_to_ascii(byte c, byte d) {
        int high = hex_to_int(c) * 16;
        int low = hex_to_int(d);
        return (char) (high + low);
    }


    public void writeToFile(byte[] array, String path) {
        try {

            FileOutputStream stream = new FileOutputStream(path + "/aakash.wsq");
            try {
                stream.write(array, 0, array.length);
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    Bitmap createBarcodeBitmap(String data, int width, int height) throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        String finalData = Uri.encode(data);

        // Use 1 as the height of the matrix as this is a 1D Barcode.
        BitMatrix bm = writer.encode(finalData, BarcodeFormat.CODE_128, width, 1);
        int bmWidth = bm.getWidth();

        Bitmap imageBitmap = Bitmap.createBitmap(bmWidth, height, Config.ARGB_8888);

        for (int i = 0; i < bmWidth; i++) {
            // Paint columns of width 1
            int[] column = new int[height];
            Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.WHITE);
            imageBitmap.setPixels(column, 0, 1, i, 0, 1, height);
        }

        return imageBitmap;
    }

    public void writeToFileName(byte[] array, String Filename) {
        try {

            FileOutputStream stream = new FileOutputStream(Filename);
            try {
                stream.write(array, 0, array.length);
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    public void printAxisLogoFromImage() {
        try {
            InputStream inputStream = getAssets().open("photo.jpg");
            Bitmap bit = BitmapFactory.decodeStream(inputStream);
            GetPrintableImage getPrintableImage = new GetPrintableImage();
            prnRasterImg = getPrintableImage.GetPrintableArray(
                    AccordionWidgetDemoActivity.this, bit.getWidth(),
                    bit);
            image_width = getPrintableImage.getPrintWidth();
            image_height = getPrintableImage.getPrintHeight();
            getPrintableImage = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            byte[] CommandImagePrint = new byte[prnRasterImg.length + 5];

            CommandImagePrint[0] = 0x1B; // Command to for bit image
            // mode
            // please refer the previous
            // document
            CommandImagePrint[1] = 0x23; // Exc #
            CommandImagePrint[2] = (byte) image_width; // 8 Vertical
            // Dots(Heights)
            // &
            // Single Width
            // Mode

            // selected
            CommandImagePrint[3] = (byte) (image_height / 256);// f8 //
            // Decimal
            // 248
            // since
            // the
            // Image
            // width
            // is

            // 248 Pixels as mentioned above
            CommandImagePrint[4] = (byte) (image_height % 256);

            for (int i = 0; i < prnRasterImg.length; i++) {
                CommandImagePrint[i + 5] = (byte) (prnRasterImg[i] & 0xFF);
            }

            bluetoothCommunication.SendData(CommandImagePrint); // CommandImagePrint.length
            //	count = 14;
            //	isLongSlip = true;
            // bluetoothCommunication.LineFeed();
            //	bluetoothCommunication.GetPrinterStatus();

        } catch (Exception ioe) {
            System.out
                    .println("Problems reading from or writing to serial port."
                            + ioe.getMessage());

        }

    }

    public String read_file(Context context, String filename) {
        try {
            FileInputStream fis = new FileInputStream(new File(filename));
            // FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void onWSQCOMLETE(int i) {
        // TODO Auto-generated method stub
        if (i == 1) {
            DeviceBluetoothCommunication.taskFlag = 0;
            Toast.makeText(getApplicationContext(), "WSQ Data Received", Toast.LENGTH_SHORT).show();
            String newTextFile = Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "AppPathWSQ"
                    + File.separator + "hexfulldata.txt";
            File mfolderpath = new File(Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "AppPathWSQ");

            if (!mfolderpath.exists())
                mfolderpath.mkdir();

            //Read text from file
            String finaldata = read_file(getApplicationContext(), newTextFile);
            int startpos = finaldata.toLowerCase(Locale.ENGLISH).indexOf("ffffffff6e");

            String length_1stletter = Character.toString(finaldata.charAt(startpos + 10));
            String length_2ndletter = Character.toString(finaldata.charAt(startpos + 11));
            String length_3rdletter = Character.toString(finaldata.charAt(startpos + 12));
            String length_4thletter = Character.toString(finaldata.charAt(startpos + 13));
            String Templatelength = length_3rdletter + length_4thletter + length_1stletter + length_2ndletter;
            int tmplength = Integer.parseInt(Templatelength.trim(), 16);
            int finaltmplength = tmplength * 2;
            String templatestr = finaldata.substring(startpos + 14, startpos + 14 + finaltmplength);
            String finalwsqhexdata = finaldata.substring(finaltmplength, finaldata.length());

            byte[] matchtemp = hexStringToByteArray(templatestr);
            String templatepath = Environment.getExternalStorageDirectory() + File.separator + "AppPathWSQ"
                    + File.separator + "matchwsq.iso";
            File folderpath = new File(Environment
                    .getExternalStorageDirectory()
                    + File.separator
                    + "AppPathWSQ" + File.separator);

            if (!folderpath.exists())
                folderpath.mkdir();
            {
                writeToFileName(matchtemp, templatepath);
                FileInputStream bos;
                try {
                    bos = new FileInputStream(templatepath);
                    byte[] templatewsqdata = new byte[bos.available()];
                    bos.read(templatewsqdata);
                    bos.close();
                    myfptemplatelist.add(templatewsqdata);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (myfptemplatelist.size() == 2) {

                    templatecodeList = new ArrayList<Byte>();
                    fingerprintList = new ArrayList<byte[]>();
                    int tpsize = myfptemplatelist.size();

                    for (int k = 1; k < tpsize + 1; k++) {
                        templatecodeList.add((byte) 110);

                    }

                    bluetoothCommunication.DeDuplicationMatchTemplate(myfptemplatelist.size(),
                            templatecodeList, myfptemplatelist);
                    templatecodeList = null;
                }
                if (myfptemplatelist.size() >= 3) {
                    templatecodeList = null;

                    templatecodeList = new ArrayList<Byte>();

                    int i1 = myfptemplatelist.size() - 1;
                    byte[] mysearchedtemp = myfptemplatelist.get(i1);
                    myfptemplatelist.remove(i1);
                    myfptemplatelist.add(0, mysearchedtemp);
                    int i2 = myfptemplatelist.size();
                    for (int k = 1; k < i2 + 1; k++) {
                        templatecodeList.add((byte) 110);

                    }


                    bluetoothCommunication.DeDuplicationMatchTemplate(i2,
                            templatecodeList, myfptemplatelist);
                }
            }
            String hexwsqdata = converthex(finalwsqhexdata);
            byte[] finalwsq = myhexToASCII(hexwsqdata.getBytes());
            writeToFile(finalwsq, folderpath.getAbsolutePath());

        }
    }


    @Override
    public void onBatteryStatus(byte[] batterystatus) {
        String batstat = new String(batterystatus);

        if (batstat.equals("g")) {
            Toast.makeText(getApplicationContext(), "Battery is Charging ", Toast.LENGTH_LONG).show();
            batstat = null;
        } else {

            Toast.makeText(getApplicationContext(), "Battery  " + Arrays.toString(batterystatus).replace("[", "").replace("]", "") + "%", Toast.LENGTH_LONG).show();
        }
    }
}