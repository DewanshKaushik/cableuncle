package com.example.mscomputers.cableuncle.model;

import com.madept.core.model.MAdeptModel;

import java.util.ArrayList;

/**
 * Created by MS Computers on 12/23/2017.
 */
public class PaymentHistoryModel extends MAdeptModel {

   // public boolean status;
    public String response;
    public String id;
    public String unique_conn;
    public String dev_id;
    public String lco_id;
    public String stb_no;
    public String vc_no;
    public String package_name;
    public String package_validity;
    public String package_price;
    public String bill_start_date;
    public String tax;
    public String pandig_amount;
    public String total_amount;
    public String date;
    public String join_date;
    public String company_name;
    public String customer_name;
    public String gst;
    public String aadhar;
    public String comp_id;
    public String phone;
    public String landline;
    public String email;
    public String adress;
    public String state;
    public String city;
    public String area_code;
    public String pincode;
    public String lane;
    public String society;
    public String builing;
    public String statusInt;
    public String remark;
    public String no_of_connection;

    public ArrayList<PaymentHistoryModel> paymentModels=new ArrayList<PaymentHistoryModel>();


    /*
    * {
    "status": true,
    "response": "Success",
    "data": [
        {
            "id": "197",
            "unique_conn": "conn0196",
            "dev_id": "15206071855465644",
            "lco_id": "99903",
            "stb_no": "",
            "vc_no": "5465465465",
            "package_name": "661781520691065",
            "package_validity": "12",
            "package_price": "500",
            "bill_start_date": "2018-03-10",
            "tax": "5471421520617180selected",
            "pandig_amount": "0",
            "total_amount": "500",
            "date": "2018-03-18 11:44:12",
            "join_date": "2018-03-09",
            "company_name": "Siti",
            "customer_name": "Abhishek Rai",
            "gst": "3543",
            "aadhar": "543546",
            "comp_id": "10001",
            "phone": "9958990953",
            "landline": "9863247125",
            "email": "abc@gmail.com",
            "adress": "sdetv",
            "state": "Delhi",
            "city": "New",
            "area_code": "2176211520690032",
            "pincode": "110001",
            "lane": "0",
            "society": "0",
            "builing": "0",
            "status": "2",
            "remark": "Abc",
            "no_of_connection": "1"
        }
    ]
}*/
}
