package com.example.mscomputers.cableuncle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mscomputers.cableuncle.Prefs.Prefs;
import com.example.mscomputers.cableuncle.Printing.Maestro;
import com.example.mscomputers.cableuncle.aem.PrintActivity;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.PayNowModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.example.mscomputers.cableuncle.util.Util;
import com.example.mscomputers.cableuncle.view.ConfirmDialog;
import com.example.mscomputers.cableuncle.view.CustomDialog;
import com.example.mscomputers.cableuncle.view.DialogButtonListener;
import com.example.mscomputers.cableuncle.view.EditTextDialog;
import com.example.mscomputers.cableuncle.view.EnterPaymentDialog;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

import mmsl.DeviceUtility.DeviceBluetoothCommunication;

/**
 * Created by MS Computers on 1/7/2018.
 */
public class PayNow extends MAdeptActivity {
    TextView lcoName, customerName, subscriberId, noOfTv, mobileNumber, balanceAmount, basics, total,paidAmount,customerAddress;
    String subscriberIdd;
    PayNowModel payNowModelData;
    LinearLayout bankLayout,chequeLayout;
    EditText remark,addnAmount,bankName,chequeNumber;
    RadioGroup radioGroup;
    int index;
    String paymentMode="";
    RadioButton cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_now);

        lcoName = (TextView) findViewById(R.id.lcoName);

        customerName = (TextView) findViewById(R.id.customerName);
        subscriberId = (TextView) findViewById(R.id.subscriberId);
        noOfTv = (TextView) findViewById(R.id.noOfTv);
        mobileNumber = (TextView) findViewById(R.id.mobileNumber);
        balanceAmount = (TextView) findViewById(R.id.balanceAmount);
        basics = (TextView) findViewById(R.id.basics);
        total = (TextView) findViewById(R.id.total);
        remark = (EditText) findViewById(R.id.remark);
        addnAmount= (EditText) findViewById(R.id.addnAmount);
        bankName= (EditText) findViewById(R.id.bankName);
        chequeNumber= (EditText) findViewById(R.id.chequeNumber);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);

        customerAddress = (TextView) findViewById(R.id.customerAddress);
        paidAmount= (TextView) findViewById(R.id.paidAmount);
        bankLayout= (LinearLayout) findViewById(R.id.bankLayout);
        chequeLayout= (LinearLayout) findViewById(R.id.chequeLayout);
        cash= (RadioButton) findViewById(R.id.cash);

        cash.setChecked(true);
        paymentMode="cash";

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                index = radioGroup.indexOfChild(radioButton);


                if (index == 0) {
                    paymentMode="cash";
                    bankLayout.setVisibility(View.GONE);
                    bankName.setText("");
                    chequeNumber.setText("");
                    chequeLayout.setVisibility(View.GONE);
                   // radioButton.setChecked(false);
                } else if (index == 1) {
                    paymentMode="cheque";
                    bankLayout.setVisibility(View.VISIBLE);
                    chequeLayout.setVisibility(View.VISIBLE);
                    //radioButton.setChecked(false);

                }
            }
        });


        subscriberIdd = getIntent().getStringExtra("subscriberId");

      //  getBluetoothDevice();

        launchPayNowRequest(subscriberIdd);
    }

    public void launchPayNowRequest(String subscriberId) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();
        MAdeptRequest req = new MAdeptRequest(Constants.PAYNOW_REQUEST, this, Constants.PAYNOW_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        req.addParam("dev", subscriberId);

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                PayNow.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(PayNow.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(PayNow.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            payNowModelData = (PayNowModel) resp.dataModel;
                            if (payNowModelData != null) {
                                setData(payNowModelData);
                            }
                        }
                    }
                });

            }

        });

    }

    public void setData(PayNowModel model) {
        lcoName.setText("Lco Name: " + model.lcoName);
        customerName.setText(model.customerName);
        subscriberId.setText(model.subscriberName);
        noOfTv.setText(model.noOfTv);
        mobileNumber.setText(model.phone);
        balanceAmount.setText(model.balance);
        basics.setText(model.basic + "");
        total.setText(model.total + "");
        paidAmount.setText(model.total+"");
        customerAddress.setText(model.cus_addresss+"");

        CableUncleApplication.getInstance().previousBalance=model.balance+"";
        CableUncleApplication.getInstance().previousBasics=model.basic+"";
        CableUncleApplication.getInstance().previousTotalAmount=model.total+"";

    }

    public void changePhone(View v) {
        new EditTextDialog(PayNow.this, "Enter Number", new String[]{"OK"}, new DialogButtonListener() {
            @Override
            public void onButtonClicked(String text) {
                launchPhoneNumberUpdateRequest(text);
            }
        }).show();

    }

    public void launchPhoneNumberUpdateRequest(final String text) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();

        MAdeptRequest req = new MAdeptRequest(Constants.UPDATE_PHONE_RQUEST, this, Constants.UPDATE_PHONE_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        req.addParam("dev_id", subscriberIdd);
        req.addParam("phone", text);

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                PayNow.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(PayNow.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(PayNow.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final LoginModel data = (LoginModel) resp.dataModel;
                            if (data != null) {
                                mobileNumber.setText(text);
                                payNowModelData.phone = text;
                            }
                        }
                    }
                });

            }

        });


    }

    String paidAmountString="";
    String bankNameString="";
    String chequeNumberString="";

    public void payNow(View v) {
        if(paymentMode.equalsIgnoreCase("cash")){
            paidAmountString= paidAmount.getEditableText().toString();
            if (paidAmountString.equalsIgnoreCase("")) {
                MAdeptUtil.showToast(PayNow.this,"Please Enter Paid Amount");
                return;
            }

        }else{
            paidAmountString= paidAmount.getEditableText().toString();
            bankNameString= bankName.getEditableText().toString();
            chequeNumberString=chequeNumber.getEditableText().toString();
            if (paidAmountString.equalsIgnoreCase("")) {
                MAdeptUtil.showToast(PayNow.this,"Please Enter Paid Amount");
                return;
            }else if(bankNameString.equalsIgnoreCase("")){
                MAdeptUtil.showToast(PayNow.this,"Please Enter Bank Name");
                return;
            }else if(chequeNumberString.equalsIgnoreCase("")){
                MAdeptUtil.showToast(PayNow.this,"Please Enter Cheque Number");
                return;
            }
        }
        payNowModelData.amount = paidAmountString;
        payNowModelData.payment_mode=paymentMode;

        new ConfirmDialog(PayNow.this, "Confirm Payment", "Message", new String[]{"Cancel", "Confirm"}, new DialogButtonListener() {
            @Override
            public void onButtonClicked(String text) {
                if (text.equalsIgnoreCase("Confirm")) {
                    getFields(paidAmountString);
                }
            }
        }, payNowModelData).show();

/*        new EnterPaymentDialog(PayNow.this, "Enter Payment", new String[]{"OK", "Cancel"}, new DialogButtonListener() {

            @Override
            public void onButtonClicked(String text) {

                payNowModelData.amount = text;
                new ConfirmDialog(PayNow.this, "Confirm Payment", "Message", new String[]{"ok", "Cancel"}, new DialogButtonListener() {

                    @Override
                    public void onButtonClicked(String text) {
                        if (text.equalsIgnoreCase("ok")) {
                            getFields();
                        }
                    }
                }, payNowModelData).show();

            }
        }).show();*/
    }

    public void getFields(String paidAmountString){
        String remarkString = remark.getEditableText().toString();
        if (remarkString.equalsIgnoreCase("")) {
            remarkString = "No remark";
        }
        String addnAmountString = addnAmount.getEditableText().toString();
        if (addnAmountString.equalsIgnoreCase("")) {
            addnAmountString = "null";
        }
        String bankNameString = bankName.getEditableText().toString();
        if (bankNameString.equalsIgnoreCase("")) {
            bankNameString = "Not Available";
        }
        String chequeNumberString= chequeNumber.getEditableText().toString();
        if (chequeNumberString.equalsIgnoreCase("")) {
            chequeNumberString = "null";
        }

        submitPayment(remarkString,addnAmountString,bankNameString,chequeNumberString,paidAmountString);

    }

    public void submitPayment(String remarkString,String addnAmountString,String bankNameString,String chequeNumberString,String amount) {
        MAdeptPrefs.getInstance(PayNow.this).loadPrefs();

        MAdeptRequest req = new MAdeptRequest(Constants.SUBMIT_PAYMENT, PayNow.this, Constants.SUBMIT_PAYMENT_URL, MAdeptRequest.METHOD_POST);
        req.setJSONParser(JSONParser.getInstance());
        req.addParam("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));
        Log.e("lco", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.LCO));

        req.addParam("dev_id", subscriberIdd);
        Log.e("dev_id",subscriberIdd);

        req.addParam("payment", amount);
        Log.e("payment",amount);

        req.addParam("get_id", MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.UNIQUE_ID));
        Log.e("get_id",MAdeptPrefs.getInstance(PayNow.this).getPrefs(Constants.UNIQUE_ID));

        req.addParam("total_bill", payNowModelData.total + "");
        Log.e("total_bill",payNowModelData.total + "");

        req.addParam("pay_mode", paymentMode);
        Log.e("pay_mode",paymentMode);

        req.addParam("cheque_no", chequeNumberString);
        Log.e("cheque_no",chequeNumberString);

        req.addParam("ifsc_code", bankNameString);
        Log.e("ifsc_code",bankNameString);

        req.addParam("dis_amount", "null");
        Log.e("dis_amount","null");

        req.addParam("other", addnAmountString);
        Log.e("other",addnAmountString);

        req.addParam("discount", "null");
        Log.e("discount","null");

        req.addParam("due_date", Util.getDateandTime());
        Log.e("due_date",Util.getDateandTime());

        req.addParam("remark", remarkString);
        Log.e("remark",remarkString);

        req.addParam("account_no", "null");
        Log.e("account_no","null");

        req.processForData(new MAdeptResponseListener() {
            @Override
            public void onResponse(final MAdeptResponse resp) {
                PayNow.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resp.isError())// Handle error code
                        {
                            if (resp instanceof MAdeptErrorResponse) {
                                MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                MAdeptUtil.showToast(PayNow.this, error.getErrorMessage());
                            } else {
                                MAdeptUtil.showToast(PayNow.this, resp.dataModel.getErrorMessage());
                            }

                        } else {
                            final PayNowModel data = (PayNowModel) resp.dataModel;
                            if (data != null) {
                                payNowModelData = data;
                                new CustomDialog(PayNow.this, "Alert", "Do you want to print slip?", new String[]{"OK", "Cancel"}, new DialogButtonListener() {
                                    @Override
                                    public void onButtonClicked(String text) {
                                        if (text.equalsIgnoreCase("OK")) {

                                           printData();
                                          /*  new CustomDialog(PayNow.this, "Printing", "Select Machine", new String[]{"AEM", "MAESTRO"}, new DialogButtonListener() {
                                                @Override
                                                public void onButtonClicked(String text) {
                                                    if (text.equalsIgnoreCase("AEM")) {

                                                        Intent intent = new Intent(PayNow.this, PrintActivity.class);
                                                        intent.putExtra("payNowModelData", payNowModelData);
                                                        startActivity(intent);
                                                        finish();
                                                    } else if (text.equalsIgnoreCase("MAESTRO")) {
                                                        Intent intent = new Intent(PayNow.this, Maestro.class);
                                                        intent.putExtra("payNowModelData", payNowModelData);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            }).show();*/
                                        }else{
                                            finish();
                                        }
                                    }
                                }).show();

                            }
                        }
                    }
                });

            }

        });


        /*
        * Rai.abhishek727@gmail.com
        Pwd-Lotopo@3393*/
    }


    public void printData(){
        DeviceBluetoothCommunication communication=CableUncleApplication.getInstance().bluetoothCommunication;
        BluetoothDevice device=CableUncleApplication.getInstance().device;

        if(communication==null){
            Intent intent = new Intent(PayNow.this, Maestro.class);
            intent.putExtra("from", "paynow");
            intent.putExtra("payNowModelData", payNowModelData);
            startActivity(intent);
        }else{
            Util.printBill(PayNow.this,communication,payNowModelData);
        }
        finish();

    }

/*
    public void getBluetoothDevice(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);
    }
*/

  //  String foundedDeviceAddress;
/*
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                String previousDeviceAddress=CableUncleApplication.getInstance().device.getAddress();
                foundedDeviceAddress=device.getAddress();

                // ... //Device found
            }
            else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                String previousDeviceAddress=CableUncleApplication.getInstance().device.getAddress();
                foundedDeviceAddress=device.getAddress();

             */
/*   if(previousDeviceAddress.equalsIgnoreCase(foundedDeviceAddress)){
                    MAdeptUtil.showToast(PayNow.this,"Device Connected ");
                }else{
                    MAdeptUtil.showToast(PayNow.this,"Device Not Connected ");
                }*//*

                // ... //Device is now connected
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                String previousDeviceAddress=CableUncleApplication.getInstance().device.getAddress();
                foundedDeviceAddress=device.getAddress();

                // ... //Done searching
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                String previousDeviceAddress=CableUncleApplication.getInstance().device.getAddress();
                foundedDeviceAddress=device.getAddress();
                // ... //Device is about to disconnect
            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                String previousDeviceAddress=CableUncleApplication.getInstance().device.getAddress();
                foundedDeviceAddress=device.getAddress();
                // ... //Device has disconnected
            }
        }
    };
*/


    public void sendSMS(String phoneNo, String msg) {
        try {
            String subject = msg;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, subject, null, null);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void eMail(String mailCustId, String text) {
        String msg = text;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mailCustId, Prefs.getInstance().email});
        //d i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject));
        i.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PayNow.this, Payment.class);
        startActivity(i);
        finish();
    }
}
