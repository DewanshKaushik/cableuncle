package com.example.mscomputers.cableuncle.Printing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aem.api.AEMPrinter;
import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.aem.PrintActivity;
import com.example.mscomputers.cableuncle.maestro.microatm.DeviceListActivity;
import com.example.mscomputers.cableuncle.model.PayNowModel;
import com.example.mscomputers.cableuncle.util.Util;
import com.madept.core.activity.MAdeptActivity;

import java.io.IOException;
import java.util.Arrays;

import mmsl.DeviceUtility.DeviceBluetoothCommunication;
import mmsl.DeviceUtility.DeviceCallBacks;

/**
 * Created by MS Computers on 3/24/2018.
 */
public class Maestro extends MAdeptActivity implements DeviceCallBacks {
    Button connectButton;
    Button disconnectButton, printBill;
    DeviceBluetoothCommunication bluetoothCommunication;
    byte FontStyleVal;
    int duplicatetempnumber;
    BroadcastReceiver _wsqreceiver;
    PayNowModel payNowModelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maestro);

        connectButton = (Button) findViewById(R.id.connectButton);
        disconnectButton = (Button) findViewById(R.id.disconnectButton);
        printBill = (Button) findViewById(R.id.printBill);
        printBill.setEnabled(false);

        payNowModelData = (PayNowModel) getIntent().getSerializableExtra("payNowModelData");

    }

    @Override
    public void onConnectComplete() {
        Util.showToast(Maestro.this, "Connection Successful");
        Log.e("onConnectComplete", "onConnectComplete");
        printBill.setEnabled(true);

    }

    @Override
    public void onMoveFingerUP() {

    }

    @Override
    public void onMoveFingerRight() {

    }

    @Override
    public void onMoveFingerDown() {

    }

    @Override
    public void onFingerScanStarted(int i) {

    }

    @Override
    public void onConnectionFailed() {
        Util.showToast(Maestro.this, "Connection not established please connect");
        Log.e("onConnectionFailed", "onConnectionFailed");
    }

    @Override
    public void onFingeracquisitioncompeted(String s) {

    }

    @Override
    public void onImproveSwipe() {

    }

    @Override
    public void onCancelledCommand() {

    }

    @Override
    public void onCPUSmartCardCommandDataRecieved(byte[] bytes) {

    }

    @Override
    public void onMSRDataRecieved(String s) {

    }

    @Override
    public void onNoData() {

    }

    @Override
    public void onCommandRecievedWhileAnotherRunning() {

    }

    @Override
    public void onFingerTooMoist() {

    }

    @Override
    public void onLatentFingerHard(String s) {

    }

    @Override
    public void onInvalidCommand() {

    }

    @Override
    public void onWSQCOMLETE(int i) {

    }

    @Override
    public void onCommandRecievedWhileProcessing() {

    }

    @Override
    public void onMoveFingerLeft() {

    }

    public void onConnect(View v) {

        Intent i = new Intent(getApplicationContext(),
                DeviceListActivity.class);
        startActivityForResult(i, 12);

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

    public void onDisConnect(View v) {
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        printBill.setEnabled(false);

        if (bluetoothCommunication != null) {
            bluetoothCommunication.StopConnection();
        }
    }

    public void printMaestroBill(View v) {
        Toast.makeText(Maestro.this, "Printing " + 2 + " Character/Line Bill", Toast.LENGTH_SHORT).show();
        printBill();
    }


    public void printBill() {
        if (payNowModelData == null) {
            Toast.makeText(Maestro.this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bluetoothCommunication == null) {
            Toast.makeText(Maestro.this, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        String data = "Invoice Cum Receipt";
        String d = "____________________________";
        try {
            FontStyleVal &= 0xFC;
            bluetoothCommunication.setPrinterFont(FontStyleVal);

            Util.printMaestroLine(bluetoothCommunication,data);

            Util.printMaestroLine(bluetoothCommunication,d);


            String lcoName = "Lco Name: " + payNowModelData.lcoName;
            String finalLcoName = Util.cutStringsecond(lcoName);
            Util.printMaestroLine(bluetoothCommunication,finalLcoName);
            Log.e("finalLcoName", finalLcoName);

            String lcoComplain = "Complain No: " + payNowModelData.lcoComplain;
            String finalComplain = Util.cutStringsecond(lcoComplain);
            Util.printMaestroLine(bluetoothCommunication,finalComplain);
            Log.e("Complain No", finalComplain);

            Util.printMaestroLine(bluetoothCommunication,d);

            String customerName = "Customer Name: " + payNowModelData.customerName;
            String finalCustomerName = Util.cutStringsecond(customerName);
            Util.printMaestroLine(bluetoothCommunication,finalCustomerName);
            Log.e("Customer Name ", finalCustomerName);

            String subscriberName = "Customer Id: " + payNowModelData.subscriberName;
            String finalSubscriberName = Util.cutStringsecond(subscriberName);
            Util.printMaestroLine(bluetoothCommunication,finalSubscriberName);
            Log.e("Customer Id:", finalSubscriberName);

            String address = "Address: " + payNowModelData.address;
            String finalAddress = Util.cutStringsecond(address);
            Util.printMaestroLine(bluetoothCommunication,finalAddress);
            Log.e("Address:", finalAddress);

            String mobileNumber = "Mobile Number: " + payNowModelData.phone;
            String finalMobileNumber = Util.cutStringsecond(mobileNumber);
            Util.printMaestroLine(bluetoothCommunication,finalMobileNumber);
            Log.e("Mobile Number", finalMobileNumber);

            String noOfTv = "No of Tv's: " + payNowModelData.noOfTv;
            String finalNoOfTv = Util.cutStringsecond(noOfTv);
            Util.printMaestroLine(bluetoothCommunication,finalNoOfTv);
            Log.e("No of Tvs:", finalNoOfTv);

            Util.printMaestroLine(bluetoothCommunication,d);

            String basics = "Basic Amt: " + payNowModelData.basic;
            String finalBasics = Util.cutStringsecond(basics);
            Util.printMaestroLine(bluetoothCommunication,finalBasics);
            Log.e("Basic Amt: ", finalBasics);

            String addCharges = "Addn Charges: " + payNowModelData.add_charges;
            String finalAddCharges = Util.cutStringsecond(addCharges);
            Util.printMaestroLine(bluetoothCommunication,finalAddCharges);
            Log.e("Addn Charges: ", finalAddCharges);

            String sgst = "SGST: " + Util.getRoundDigit(payNowModelData.sgst);
            String finalSgst = Util.cutStringsecond(sgst);
            Util.printMaestroLine(bluetoothCommunication,finalSgst);
            Log.e("SGST: ", finalSgst);

            String cgst = "CGST: " + Util.getRoundDigit(payNowModelData.cgst);
            String finalCgst = Util.cutStringsecond(cgst);
            Util.printMaestroLine(bluetoothCommunication,finalCgst);
            Log.e("CGST: ", finalCgst);

            String total = "Grand Total: " + payNowModelData.total;
            String finalTotal = Util.cutStringsecond(total);
            Util.printMaestroLine(bluetoothCommunication,finalTotal);
            Log.e("Grand Total:", finalTotal);

            String amount = "Paid Amount: " + payNowModelData.total;
            String finalAmount = Util.cutStringsecond(amount);
            Util.printMaestroLine(bluetoothCommunication,finalAmount);
            Log.e("Paid Amount: ", finalAmount);

            String dueBalance = "Due Balance: " + payNowModelData.dueBalance;
            String finalDueBalance = Util.cutStringsecond(dueBalance);
            Util.printMaestroLine(bluetoothCommunication,finalDueBalance);
            Log.e("Due Balance:", finalDueBalance);

            Util.printMaestroLine(bluetoothCommunication,d);

            String receiptno = "Receipt No: " + payNowModelData.invoice;
            String finalReceiptno = Util.cutStringsecond(receiptno);
            Util.printMaestroLine(bluetoothCommunication,finalReceiptno);
            Log.e("Receipt No: ", finalReceiptno);

            String date = "Date: " + payNowModelData.date;
            String finalDate = Util.cutStringsecond(date);
            Util.printMaestroLine(bluetoothCommunication,finalDate);
            Log.e("Date: ", finalDate);

            String remark = "Remark: " + payNowModelData.remark;
            String finalRemark = Util.cutStringsecond(remark);
            Util.printMaestroLine(bluetoothCommunication,finalRemark);
            Log.e("Remark: ", finalRemark);

            Util.printMaestroLine(bluetoothCommunication,d);

            String pMode = "Payment Mode: " + payNowModelData.payment_mode;
            String finalpMode = Util.cutStringsecond(pMode);
            Util.printMaestroLine(bluetoothCommunication,finalpMode);
            Log.e("Payment Mode: ", finalpMode);

            String chequeNo = "Cheque No: " + payNowModelData.cheque_no;
            String finalChequeNo = Util.cutStringsecond(chequeNo);
            Util.printMaestroLine(bluetoothCommunication,finalChequeNo);
            Log.e("Cheque No: ", finalChequeNo);

            Util.printMaestroLine(bluetoothCommunication,d);

            Util.printMaestroLine(bluetoothCommunication,d);

            String pow = "Powered By CABLEUNCLE";
            Util.printMaestroLine(bluetoothCommunication,pow);

            String web = "www.cableuncle.in";
            Util.printMaestroLine(bluetoothCommunication,web);

            String version = "";
            try {
                PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String ver = "Version :" + version;
            Util.printMaestroLine(bluetoothCommunication,ver);


            //footer
            bluetoothCommunication.LineFeed();
            bluetoothCommunication.LineFeed();
            bluetoothCommunication.LineFeed();

        } catch (Exception e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(Maestro.this, "Printer not connected", Toast.LENGTH_SHORT).show();

        }
    }



/*
    public void printMaestroBill(View v) {
        FontStyleVal &= 0xFC;
        bluetoothCommunication.setPrinterFont(FontStyleVal);
        String name = "Name : Dewansh Kaushik";
        // byte[] imageDataBytes = Base64.decode(name, 0);
        // bluetoothCommunication.SendData(imageDataBytes);
        bluetoothCommunication.SendData(name.getBytes());
        bluetoothCommunication.LineFeed();
        String amount = "Amount : 20 INR";
        bluetoothCommunication.SendData(amount.getBytes());
        bluetoothCommunication.LineFeed();
        bluetoothCommunication.LineFeed();
    }
*/

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

		/*String mywsq=converthex(wsqfingerhex);
        byte[] finascistr =myhexToASCII(mywsq.getBytes());
		writeToFile(finascistr);
*/
    }

    @Override
    public void onCryptographicError() {


    }

    @Override
    public void onErrorOccured() {


    }

    @Override
    public void onErrorOccuredWhileProccess() {


    }

    @Override
    public void onErrorReadingSmartCard() {


    }

    @Override
    public void onErrorWritingSmartCard() {


    }

    @Override
    public void onFalseFingerDetected() {


    }

    @Override
    public void onFingerImageRecieved(byte[] data) {

    }

    @Override
    public void onFingerPrintTimeout() {

        disconnectButton.setEnabled(true);

    }

    @Override
    public void onNoSmartCardFound() {

        disconnectButton.setEnabled(true);

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

    @Override
    public void onOperationNotSupported() {


    }

    @Override
    public void onParameterOutofRange() {


    }

    @Override
    public void onPlaceFinger() {


    }

    @Override
    public void onPressFingerHard() {

    }

    @Override
    public void onRemoveFinger() {


    }

    @Override
    public void onSameFinger() {

    }

    @Override
    public void onSmartCardDataRecieved(byte[] data) {


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


        disconnectButton.setEnabled(true);


    }

    @Override
    public void onSmartCardPresent() {


    }

    @Override
    public void onTemplateRecieved(byte[] data) {
/*        if (enablededupscan) {
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
            //recievedTextView.setText("Template Received"*//*bstemplate*//*);
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
        }*/
    }

    @Override
    public void onVerificationSuccessful(int templatenumber) {
      /*  if (enablededupscan) {
            duplicatetempnumber = templatenumber;
            recievedTextView.setText("Finger Already Exist.");
            myfptemplatelist.remove(templatenumber);
            //	templatecodeList.remove(templatenumber);
		*//*File file = new File(_ChhosenPath+ File.separator + "Template"+templatenumber+".iso");
		boolean deleted = file.delete();
		dedupnewfinger=false;*//*
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

        mMonalisaButton.setEnabled(true);*/
    }

    @Override
    public void onVerificationfailed() {
       /* if (enablededupscan) {
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

        mMonalisaButton.setEnabled(true);*/
    }

    @Override
    public void onWriteToSmartCardSuccessful() {

        //D   recievedTextView.setText("Data written on SmartCard Successfully.");

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
     /*   swipestartButton.setEnabled(true);
        swipetimeoutSpinner.setEnabled(true);
        swipestopButton.setEnabled(false);*/
        disconnectButton.setEnabled(true);

    /*    fpsTimeoutSpinner.setEnabled(true);
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

        mMonalisaButton.setEnabled(true);*/
    }

    @Override
    public void onOutofPaper() {

     /*   recievedTextView.setText("Printer Out of Paper.");
        EnableAll();*/
    }

    @Override
    public void onPlatenOpen() {

        //D recievedTextView.setText("Platen is Open.");
    }

    @Override
    public void onHighHeadTemperature() {

        //D   recievedTextView.setText("High Head Temperature.");
    }

    @Override
    public void onLowHeadTemperature() {

        //D  recievedTextView.setText("Head Temperature is Low.");

    }

    @Override
    public void onImproperVoltage() {

        //D   recievedTextView.setText("Improper Voltage.");

    }

    @Override
    public void onSuccessfulPrintIndication() {

        //D   recievedTextView.setText("Indication for successful print.");
        // mPrintSlipButton.setEnabled(false);
/*        if (isLongSlip) {
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
        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothCommunication != null)
            bluetoothCommunication.StopConnection();
    }

/*
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
                    Toast.makeText(Maestro.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter bintentFilter = new IntentFilter(
                CommandReceiveThread.ACTION_EVENT_WSQDATA_RECIEVED);
        getBaseContext().registerReceiver(_wsqreceiver, bintentFilter);

    }
*/

}
