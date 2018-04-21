package com.example.mscomputers.cableuncle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

public class DateAndTimePickerActivityDialog
        extends Activity {
    DatePicker datePicker;
    boolean isAmpmFormat;
    Button okButton;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        setContentView(R.layout.custom_dialog_datetimepicker);
        this.datePicker = ((DatePicker) findViewById(R.id.datePicker));
        this.okButton = ((Button) findViewById(R.id.okButton));
        applyStyLingOnDate(this.datePicker);
        long l = System.currentTimeMillis() - 1000L;

        //d this.datePicker.setMinDate(l);
    }


    private void applyStyLing(TimePicker paramTimePicker) {
        Resources localResources = Resources.getSystem();
        int i = localResources.getIdentifier("hour", "id", "android");
        int j = localResources.getIdentifier("minute", "id", "android");
        NumberPicker localNumberPicker1 = (NumberPicker) paramTimePicker.findViewById(i);
        NumberPicker localNumberPicker2 = (NumberPicker) paramTimePicker.findViewById(j);
        setNumberPickerDividerColour(localNumberPicker1);
        setNumberPickerDividerColour(localNumberPicker2);
    }

    private void applyStyLingOnDate(DatePicker paramDatePicker) {
        Resources localResources = Resources.getSystem();
        int i = localResources.getIdentifier("day", "id", "android");
        int j = localResources.getIdentifier("month", "id", "android");
        localResources.getIdentifier("year", "id", "android");
        NumberPicker localNumberPicker1 = (NumberPicker) paramDatePicker.findViewById(i);
        NumberPicker localNumberPicker2 = (NumberPicker) paramDatePicker.findViewById(j);
        setNumberPickerDividerColour(localNumberPicker1);
        setNumberPickerDividerColour(localNumberPicker2);
    }

    private void setNumberPickerDividerColour(NumberPicker paramNumberPicker) {
        int i = paramNumberPicker.getChildCount();
        int j = 0;
        //   for (; ; ) {
        if (j < i) {
            try {
                Field localField = paramNumberPicker.getClass().getDeclaredField("mSelectionDivider");
                localField.setAccessible(true);
                localField.set(paramNumberPicker, new ColorDrawable(getResources().getColor(R.color.colorAccent)));
                paramNumberPicker.invalidate();
                j++;
            } catch (NoSuchFieldException localNoSuchFieldException) {
                // for (; ; ) {
                Log.w("setNumberPickerTxtClr", localNoSuchFieldException);
                //   }
            } catch (IllegalAccessException localIllegalAccessException) {
                //   for (; ; ) {
                Log.w("setNumberPickerTxtClr", localIllegalAccessException);
                //  }
            } catch (IllegalArgumentException localIllegalArgumentException) {
                //   for (; ; ) {
                Log.w("setNumberPickerTxtClr", localIllegalArgumentException);
                //  }
            }
        }
        //   }
    }


    public void performOk(View paramView) {
        int i = this.datePicker.getMonth();
        int j = this.datePicker.getDayOfMonth();
        int k = this.datePicker.getYear();
        int m = i + 1;
        String str1 = "" + m;
        String str2 = "" + j;
        if (m < 10) {
            str1 = "0" + str1;
        }
        if (j < 10) {
            str2 = "0" + str2;
        }
        String str3 = k + "-" + str1 + "-" + str2;
        Intent localIntent = getIntent();
        localIntent.putExtra("dateTimeString", str3);
        setResult(-1, localIntent);
        System.out.println();
        finish();
    }
}


/* Location:              /Users/krish/Downloads/addedrsi/debug/dex2jar-0.0.9.15/classes_dex2jar.jar!/com/avacado/DateAndTimePickerActivityDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */