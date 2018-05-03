package com.example.mscomputers.cableuncle.model;

import com.madept.core.model.MAdeptModel;

import java.io.Serializable;

/**
 * Created by MS Computers on 3/27/2018.
 */
public class PayNowModel extends MAdeptModel implements Serializable {

        /*   {
            "status": true,
                    "response": "success",
                    "data": {
                "lco_name": "Abhishek Rai Abhi Cable Network",
                        "customer_name": "Abhishek Rai",
                        "subscriber_no": "15206915157120573",
                        "no_of_tv": "1",
                        "phone": "9528556346",
                        "balance": "0",
                        "basic": "300",
                        "total": 300
            }
        }*/

    public String lcoName;
    public String lcoComplain;

    public String customerName;
    public String subscriberName;
    public String noOfTv;
    public String address;
    public String phone;
    public String invoice;
    public String date;
    public int add_charges;

    public double cgst;
    public double sgst;
    public String payment_mode;
    public String remark;
    public int cheque_no;
    public int dueBalance;

    public int paid_amount;
    public String balance;
    public String cus_addresss;
    public String basic;
    public int total;
    public String amount;

}
