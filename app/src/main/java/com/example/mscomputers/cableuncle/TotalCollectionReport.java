package com.example.mscomputers.cableuncle;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mscomputers.cableuncle.Printing.Maestro;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.TotalCollectionReportModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

import mmsl.DeviceUtility.DeviceBluetoothCommunication;

/**
 * Created by MS Computers on 12/16/2017.
 */
public class TotalCollectionReport extends MAdeptActivity {

    TextView fromDate,toDate,totalDays,cashAmount,chequeAmount,otherAmount,totalAmount;
    TotalCollectionReportModel totalCollectionReportModel;
    String fromDateAndTimeString = "";
    String toDateAndTimeString = "";
    Button printButton;
    public static int FROM_DATE_AND_TIME_STRING = 0;
    public static int TO_DATE_AND_TIME_STRING = 1;
    public String days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.total_collection_report);
        fromDate=(TextView) findViewById(R.id.fromDate);
        toDate=(TextView) findViewById(R.id.toDate);
        totalDays=(TextView) findViewById(R.id.totalDays);
        cashAmount=(TextView) findViewById(R.id.cashAmount);
        chequeAmount=(TextView) findViewById(R.id.chequeAmount);
        otherAmount=(TextView) findViewById(R.id.otherAmount);
        totalAmount=(TextView) findViewById(R.id.totalAmount);
        printButton=(Button) findViewById(R.id.printButton);
        printButton.setEnabled(false);


        fromDate.setText(Util.getTodayDate());
        fromDateAndTimeString=Util.getTodayDate();


    }

    public void goToNext(View v) {
        Intent i = new Intent(this,LastTransactions.class);
        startActivity(i);

    }

    public void getData(){
            MAdeptPrefs.getInstance(TotalCollectionReport.this).loadPrefs();

            MAdeptRequest req = new MAdeptRequest(Constants.GET_TOTAL_COLLECTION_REPORT, this, Constants.GET_TOTAL_COLLECTION_URL, MAdeptRequest.METHOD_POST);
            req.setJSONParser(JSONParser.getInstance());
            req.addParam("getid", MAdeptPrefs.getInstance(TotalCollectionReport.this).getPrefs(Constants.UNIQUE_ID));
            req.addParam("from_date", fromDateAndTimeString);
            req.addParam("last_date", toDateAndTimeString);
            req.addParam("lco", MAdeptPrefs.getInstance(TotalCollectionReport.this).getPrefs(Constants.LCO));


            req.processForData(new MAdeptResponseListener() {
                @Override
                public void onResponse(final MAdeptResponse resp) {
                    TotalCollectionReport.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resp.isError())// Handle error code
                            {
                                if (resp instanceof MAdeptErrorResponse) {
                                    MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                    MAdeptUtil.showToast(TotalCollectionReport.this, error.getErrorMessage());
                                } else {
                                    MAdeptUtil.showToast(TotalCollectionReport.this, resp.dataModel.getErrorMessage());
                                }

                            } else {
                                final TotalCollectionReportModel data = (TotalCollectionReportModel) resp.dataModel;
                                if (data != null) {
                                    totalCollectionReportModel=data;
                                    totalCollectionReportModel.days=days;
                                    setData(data);
                                    printButton.setEnabled(true);
                                }
                            }
                        }
                    });

                }

            });

    }

    public void setData(TotalCollectionReportModel data){
        cashAmount.setText("INR "+data.totalCash);
        chequeAmount.setText("INR "+data.totalCheque);
        otherAmount.setText("INR "+data.otherTotal);
        totalAmount.setText("INR "+data.grandTotal);
    }

    public void printTotalCollectionReport(View v){
        printData();
    }

    public void printData(){

        DeviceBluetoothCommunication communication=CableUncleApplication.getInstance().bluetoothCommunication;

        if(communication==null){
            Intent intent = new Intent(TotalCollectionReport.this, Maestro.class);
            intent.putExtra("from", "totalcollectionreport");
            intent.putExtra("totalCollectionReportModel", totalCollectionReportModel);
            startActivity(intent);
        }else{
            Util.printTotalCollectionData(TotalCollectionReport.this,communication,totalCollectionReportModel);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        try {
            super.onActivityResult(paramInt1, paramInt2, paramIntent);
          /*  if ((paramInt1 == 101) && (paramInt2 == -1)) {
                getDataList();
            }*/
            if ((paramInt1 == FROM_DATE_AND_TIME_STRING) && (paramInt2 == -1)) {
                this.fromDateAndTimeString = paramIntent.getStringExtra("dateTimeString");
                this.fromDate.setText(this.fromDateAndTimeString);
            }
            if ((paramInt1 == TO_DATE_AND_TIME_STRING) && (paramInt2 == -1)) {
                this.toDateAndTimeString = paramIntent.getStringExtra("dateTimeString");
                this.toDate.setText(this.toDateAndTimeString);

                if(fromDateAndTimeString.equalsIgnoreCase("")){
                    MAdeptUtil.showToast(TotalCollectionReport.this,"Please select From Date");
                    return;
                }else if(toDateAndTimeString.equalsIgnoreCase("")){
                    MAdeptUtil.showToast(TotalCollectionReport.this,"Please select To Date");
                    return;

                }
                totalDays.setText(Util.getCountOfDays(fromDateAndTimeString,toDateAndTimeString));
                days=Util.getCountOfDays(fromDateAndTimeString,toDateAndTimeString);
                getData();

            }
            return;


        } catch (Exception localException) {
            Toast.makeText(this, localException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void fromSelectDate(View paramView) {
        startActivityForResult(new Intent(this, DateAndTimePickerActivityDialog.class), FROM_DATE_AND_TIME_STRING);
    }

    public void toSelectDate(View paramView) {
        startActivityForResult(new Intent(this, DateAndTimePickerActivityDialog.class), TO_DATE_AND_TIME_STRING);
    }

}
