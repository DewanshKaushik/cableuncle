package com.example.mscomputers.cableuncle.model;

import com.madept.core.model.MAdeptModel;

import java.util.ArrayList;

/**
 * Created by MS Computers on 3/25/2018.
 */
public class PaymentModel extends MAdeptModel {

    public String compId;
    public String name;

    public ArrayList<PaymentModel> modelArrayList=new ArrayList<>();
    // http://cableuncle.in/cableuncle/android/payment.php
    /*
    * {
    "status": "True",
    "response": "Success",
    "data": [
        {
            "comp_id": "10001",
            "name": "Abhishek Rai"
        },
        {
            "comp_id": "10004",
            "name": "Aditya Rai"
        },
        {
            "comp_id": "10005",
            "name": "Bajrao"
        }
    ]
}*/
// http://cableuncle.in/cableuncle/android/user_detail.php
    /*
    * {
    "status": true,
    "response": "Success",
    "data": {
        "id": "4",
        "database_id": "99903",
        "name": "Abhishek Rai",
        "company_name": "Abhi Cable Network",
        "provider": "[\"Hathway\",\"Den\",\"Siti\",\"SMR\"]",
        "address": "K-90 Sadatpur",
        "state": "Delhi",
        "city": "New",
        "aadhar_no": "0",
        "phone": "9958990953",
        "gst_no": "0",
        "email": "rai.abhishek727@gmail.com",
        "password": "7815696ecbf1c96e6894b779456d330e",
        "repassword": "asd",
        "complain_no_1": "9958990953",
        "complain_no_2": "7011977736",
        "eod": "9958990953",
        "pan": "",
        "sms_value": "1000",
        "sms_title": "[\"Payment Collection SMS\",\"Payment Revoke SMS\",\"Payment Reminder SMS\",\"New Customer Welcome SMS \",\"Complaint SMS\"]",
        "status": "1",
        "date": "2018-03-25 14:52:04"
    }
}*/

    // http://cableuncle.in/cableuncle/android/lco_details.php
    /*
    * {
    "status": true,
    "response": "Success",
    "data": {
        "id": "4",
        "database_id": "99903",
        "name": "Abhishek Rai",
        "company_name": "Abhi Cable Network",
        "provider": "[\"Hathway\",\"Den\",\"Siti\",\"SMR\"]",
        "address": "K-90 Sadatpur",
        "state": "Delhi",
        "city": "New",
        "aadhar_no": "0",
        "phone": "9958990953",
        "gst_no": "0",
        "email": "rai.abhishek727@gmail.com",
        "password": "7815696ecbf1c96e6894b779456d330e",
        "repassword": "asd",
        "complain_no_1": "9958990953",
        "complain_no_2": "7011977736",
        "eod": "9958990953",
        "pan": "",
        "sms_value": "1000",
        "sms_title": "[\"Payment Collection SMS\",\"Payment Revoke SMS\",\"Payment Reminder SMS\",\"New Customer Welcome SMS \",\"Complaint SMS\"]",
        "status": "1",
        "date": "2018-03-25 14:52:04"
    }
}*/
}
