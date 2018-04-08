package com.example.mscomputers.cableuncle.aem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aem.api.AEMPrinter;
import com.aem.api.AEMScrybeDevice;
import com.aem.api.CardReader;
import com.aem.api.IAemCardScanner;
import com.aem.api.IAemScrybe;
import com.example.mscomputers.cableuncle.R;
import com.example.mscomputers.cableuncle.model.PayNowModel;
import com.example.mscomputers.cableuncle.util.Util;
import com.madept.core.activity.MAdeptActivity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by MS Computers on 12/27/2017.
 */
public class PrintActivity extends MAdeptActivity implements IAemCardScanner,
        IAemScrybe {
    int effectivePrintWidth = 48;
    AEMScrybeDevice m_AemScrybeDevice;
    CardReader m_cardReader = null;
    AEMPrinter m_AemPrinter = null;
    Button btnPrint, btnSetPrnType;
    ArrayList<String> printerList;
    String creditData;
    ProgressDialog m_WaitDialogue;
    CardReader.CARD_TRACK cardTrackType;
    int glbPrinterWidth;
    // MyClient mcl = null;
    //private Context context;
    EditText editText, rfText;
    Spinner spinner;
    String imgDecodableString;
    PayNowModel payNowModelData;
    Button bill;
    private PrintWriter printOut;
    private Socket socketConnection;
    private String txtIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        editText = (EditText) findViewById(R.id.edittext);
        bill = (Button) findViewById(R.id.bill);
        bill.setEnabled(false);

        m_AemScrybeDevice = new AEMScrybeDevice(this);
        Button discoverButton = (Button) findViewById(R.id.pairing);
        registerForContextMenu(discoverButton);
        payNowModelData = (PayNowModel) getIntent().getSerializableExtra("payNowModelData");
        System.out.println();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Printer to connect");

        for (int i = 0; i < printerList.size(); i++) {
            menu.add(0, v.getId(), 0, printerList.get(i));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        String printerName = item.getTitle().toString();
        try {
            m_AemScrybeDevice.connectToPrinter(printerName);
            m_cardReader = m_AemScrybeDevice.getCardReader(this);
            m_AemPrinter = m_AemScrybeDevice.getAemPrinter();
            Toast.makeText(PrintActivity.this, "Connected with " + printerName, Toast.LENGTH_SHORT).show();
            bill.setEnabled(true);

            m_cardReader.readMSR();
        } catch (IOException e) {
            if (e.getMessage().contains("Service discovery failed")) {
                Toast.makeText(PrintActivity.this, "Not Connected\n" + printerName + " is unreachable or off otherwise it is connected with other device", Toast.LENGTH_SHORT).show();
            } else if (e.getMessage().contains("Device or resource busy")) {
                Toast.makeText(PrintActivity.this, "the device is already connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PrintActivity.this, "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (m_AemScrybeDevice != null) {
            try {
                m_AemScrybeDevice.disConnectPrinter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    public void onDisconnectDevice(View v) {
        if (m_AemScrybeDevice != null) {
            try {
                m_AemScrybeDevice.disConnectPrinter();
                Toast.makeText(PrintActivity.this, "disconnected", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPrintBill(View v) {
        Toast.makeText(PrintActivity.this, "Printing " + 2 + " Character/Line Bill", Toast.LENGTH_SHORT).show();
        printBill();
    }

    public void printBill() {
        if (payNowModelData == null) {
            Toast.makeText(PrintActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        if (m_AemPrinter == null) {
            Toast.makeText(PrintActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        String data = "Invoice Cum Receipt";
        String d = "_____________________________";
        try {
            m_AemPrinter.setFontType(AEMPrinter.DOUBLE_HEIGHT);
            m_AemPrinter.setFontType(AEMPrinter.TEXT_ALIGNMENT_CENTER);

            m_AemPrinter.print(data);
            m_AemPrinter.print(d);

            m_AemPrinter.setFontType(AEMPrinter.FONT_NORMAL);

            String lcoName = "Lco Name: " + payNowModelData.lcoName;
            String finalLcoName = cutStringsecond(lcoName);
            m_AemPrinter.print(finalLcoName);
            Log.e("finalLcoName", finalLcoName);

            String lcoComplain = "Complain No: " + payNowModelData.lcoComplain;
            String finalComplain = cutStringsecond(lcoComplain);
            m_AemPrinter.print(finalComplain);
            Log.e("Complain No", finalComplain);

            m_AemPrinter.print(d);

            String customerName = "Customer Name: " + payNowModelData.customerName;
            String finalCustomerName = cutStringsecond(customerName);
            m_AemPrinter.print(finalCustomerName);
            Log.e("Customer Name ", finalCustomerName);

            String subscriberName = "Customer Id: " + payNowModelData.subscriberName;
            String finalSubscriberName = cutStringsecond(subscriberName);
            m_AemPrinter.print(finalSubscriberName);
            Log.e("Customer Id:", finalSubscriberName);

            String address = "Address: " + payNowModelData.address;
            String finalAddress = cutStringsecond(address);
            m_AemPrinter.print(finalAddress);
            Log.e("Address:", finalAddress);

            String mobileNumber = "Mobile Number: " + payNowModelData.phone;
            String finalMobileNumber = cutStringsecond(mobileNumber);
            m_AemPrinter.print(finalMobileNumber);
            Log.e("Mobile Number", finalMobileNumber);

            String noOfTv = "No of Tv's: " + payNowModelData.noOfTv;
            String finalNoOfTv = cutStringsecond(noOfTv);
            m_AemPrinter.print(finalNoOfTv);
            Log.e("No of Tvs:", finalNoOfTv);

            m_AemPrinter.print(d);

            String basics = "Basic Amt: " + payNowModelData.basic;
            String finalBasics = cutStringsecond(basics);
            m_AemPrinter.print(finalBasics);
            Log.e("Basic Amt: ", finalBasics);

            String addCharges = "Addn Charges: " + payNowModelData.add_charges;
            String finalAddCharges = cutStringsecond(addCharges);
            m_AemPrinter.print(finalAddCharges);
            Log.e("Addn Charges: ", finalAddCharges);

            String sgst = "SGST: " + Util.getRoundDigit(payNowModelData.sgst);
            String finalSgst = cutStringsecond(sgst);
            m_AemPrinter.print(finalSgst);
            Log.e("SGST: ", finalSgst);

            String cgst = "CGST: " + Util.getRoundDigit(payNowModelData.cgst);
            String finalCgst = cutStringsecond(cgst);
            m_AemPrinter.print(finalCgst);
            Log.e("CGST: ", finalSgst);

            String total = "Grand Total: " + payNowModelData.total;
            String finalTotal = cutStringsecond(total);
            m_AemPrinter.print(finalTotal);
            Log.e("Grand Total:", finalTotal);

            String amount = "Paid Amount: " + payNowModelData.total;
            String finalAmount = cutStringsecond(amount);
            m_AemPrinter.print(finalAmount);
            Log.e("Paid Amount: ", finalAmount);

            String dueBalance = "Due Balance: " + payNowModelData.dueBalance;
            String finalDueBalance = cutStringsecond(dueBalance);
            m_AemPrinter.print(finalDueBalance);
            Log.e("Due Balance:", finalDueBalance);

            m_AemPrinter.print(d);

            String receiptno = "Receipt No: " + payNowModelData.invoice;
            String finalReceiptno = cutStringsecond(receiptno);
            m_AemPrinter.print(finalReceiptno);
            Log.e("Receipt No: ", finalReceiptno);

            String date = "Date: " + payNowModelData.date;
            String finalDate = cutStringsecond(date);
            m_AemPrinter.print(finalDate);
            Log.e("Date: ", finalDate);

            String remark = "Remark: " + payNowModelData.remark;
            String finalRemark = cutStringsecond(remark);
            m_AemPrinter.print(finalRemark);
            Log.e("Remark: ", finalRemark);

            m_AemPrinter.print(d);

            String pMode = "Payment Mode: " + payNowModelData.payment_mode;
            String finalpMode = cutStringsecond(pMode);
            m_AemPrinter.print(finalpMode);
            Log.e("Payment Mode: ", finalpMode);

            String chequeNo = "Cheque No: " + payNowModelData.cheque_no;
            String finalChequeNo = cutStringsecond(chequeNo);
            m_AemPrinter.print(finalChequeNo);
            Log.e("Cheque No: ", finalChequeNo);

            m_AemPrinter.print(d);

/*
            String invoice = "Invoice No: " + payNowModelData.invoice;
            String finalInvoice= cutStringsecond(invoice);
            m_AemPrinter.print(finalInvoice);
            Log.e("Invoice No:", finalInvoice);
*/

            /*  String balance = "Balance: " + payNowModelData.balance;
            String finalBalance = cutStringsecond(balance);
            m_AemPrinter.print(finalBalance);
            Log.e("Balance:", finalBalance);*/

            m_AemPrinter.print(d);

            String pow = "Powered By CABLEUNCLE";
            m_AemPrinter.print(pow);

            String web = "www.cableuncle.in";
            m_AemPrinter.print(web);

            String version = "";
            try {
                PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String ver = "Version :" + version;
            m_AemPrinter.print(ver);

            //footer
            m_AemPrinter.setCarriageReturn();
            m_AemPrinter.setCarriageReturn();
            m_AemPrinter.setCarriageReturn();
            m_AemPrinter.setCarriageReturn();
        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(PrintActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();

        }
    }

    public void onPrintBillBluetooth() {

        if (m_AemPrinter == null) {
            Toast.makeText(PrintActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        String data = "TWO INCH PRINTER: TEST PRINT ";
        String d = "_________________________________";
        try {
            m_AemPrinter.setFontType(AEMPrinter.DOUBLE_HEIGHT);
            m_AemPrinter.setFontType(AEMPrinter.TEXT_ALIGNMENT_CENTER);

            m_AemPrinter.print(data);
            m_AemPrinter.print(d);
            data = "CODE|DESC|RATE(Rs)|QTY |AMT(Rs)";
            m_AemPrinter.print(data);
            m_AemPrinter.print(d);
            data = "13|Dewansh |35.00|02|70.00\n" +
                    "29|Pears Soap |25.00|01|25.00\n" +
                    "88|Lux Shower |46.00|01|46.00\n" +
                    "15|Dabur Honey|65.00|01|65.00\n" +
                    "52|Dairy Milk |20.00|10|200.00\n" +
                    "128|Maggie TS |36.00|04|144.00\n" +
                    "_______________________________\n";

            m_AemPrinter.setFontType(AEMPrinter.FONT_NORMAL);
            m_AemPrinter.print(data);
            m_AemPrinter.setFontType(AEMPrinter.DOUBLE_HEIGHT);
            m_AemPrinter.setFontType(AEMPrinter.TEXT_ALIGNMENT_CENTER);
            data = "   TOTAL AMOUNT (Rs.)   550.00\n";
            m_AemPrinter.print(data);
            m_AemPrinter.setFontType(AEMPrinter.FONT_002);
            m_AemPrinter.print(d);
            data = "   Thank you! \n";

            m_AemPrinter.setFontType(AEMPrinter.DOUBLE_WIDTH);
            m_AemPrinter.print(data);

            m_AemPrinter.setCarriageReturn();
            m_AemPrinter.setCarriageReturn();
            m_AemPrinter.setCarriageReturn();
            m_AemPrinter.setCarriageReturn();

        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(PrintActivity.this, "Printer not connected", Toast.LENGTH_SHORT).show();

        }

    }


    public void onScanDLCard(final String buffer) {
        CardReader.DLCardData dlCardData = m_cardReader.decodeDLData(buffer);

        String name = "NAME:" + dlCardData.NAME + "\n";
        String SWD = "SWD Of: " + dlCardData.SWD_OF + "\n";
        String dob = "DOB: " + dlCardData.DOB + "\n";
        String dlNum = "DLNUM: " + dlCardData.DL_NUM + "\n";
        String issAuth = "ISS AUTH: " + dlCardData.ISS_AUTH + "\n";
        String doi = "DOI: " + dlCardData.DOI + "\n";
        String tp = "VALID TP: " + dlCardData.VALID_TP + "\n";
        String ntp = "VALID NTP: " + dlCardData.VALID_NTP + "\n";

        final String data = name + SWD + dob + dlNum + issAuth + doi + tp + ntp;

        runOnUiThread(new Runnable() {
            public void run() {
                editText.setText(data);
            }
        });
    }

    public void onDiscoveryComplete(ArrayList<String> aemPrinterList) {
        printerList = aemPrinterList;
        for (int i = 0; i < aemPrinterList.size(); i++) {
            String Device_Name = aemPrinterList.get(i);
            String status = m_AemScrybeDevice.pairPrinter(Device_Name);
            Log.e("STATUS", status);
        }

    }

    @Override
    public void onScanRFD(final String buffer) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(buffer);
        String temp = "";
        try {
            temp = stringBuffer.deleteCharAt(8).toString();
        } catch (Exception e) {
            // TODO: handle exception
        }
        final String data = temp;

        PrintActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                //rfText.setText("RF ID:   " + data);
                editText.setText("ID " + data);
                try {
                    m_AemPrinter.print(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onShowPairedPrinters(View v) {

        String p = m_AemScrybeDevice.pairPrinter("BTprinter0314");
     //d   showAlert(p);
        printerList = m_AemScrybeDevice.getPairedPrinters();

        if (printerList.size() > 0)
            openContextMenu(v);
        else
            showAlert("No Paired Printers found");
    }

    public void showAlert(String alertMsg) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(
                PrintActivity.this);

        alertBox.setMessage(alertMsg).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        return;
                    }
                });

        AlertDialog alert = alertBox.create();
        alert.show();
    }

    public void onScanRCCard(final String buffer) {
        CardReader.RCCardData rcCardData = m_cardReader.decodeRCData(buffer);

        String regNum = "REG NUM: " + rcCardData.REG_NUM + "\n";
        String regName = "REG NAME: " + rcCardData.REG_NAME + "\n";
        String regUpto = "REG UPTO: " + rcCardData.REG_UPTO + "\n";

        final String data = regNum + regName + regUpto;

        runOnUiThread(new Runnable() {
            public void run() {
                editText.setText(data);
            }
        });
    }

    @Override
    public void onScanPacket(String buffer) {
        // TODO Auto-generated method stub

    }

    public void onScanMSR(final String buffer, CardReader.CARD_TRACK cardTrack) {
        cardTrackType = cardTrack;

        creditData = buffer;
        PrintActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                editText.setText(buffer.toString());
            }
        });
    }


    public String cutStringsecond(String string) {
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


}
