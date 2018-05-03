package com.example.mscomputers.cableuncle.model;

import com.madept.core.model.MAdeptModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MS Computers on 3/20/2018.
 */
public class LastTransactionModel extends MAdeptModel implements Serializable{

    public String company_name;
    public String lco_address;
    public String complain_no_1;
    public String complain_no_2;
    public String customer_name;
    public String comp_id;
    public String adress;
    public String phone;
    public String no_of_connection;
    public String package_price;
    public String other_charges;
    public String igst;
    public String cgst;
    public String total_amount;
    public String panding_amount;
    public String date;
    public String pay_mode;
    public String invoice;
    public String remark;


    public ArrayList<LastTransactionModel> lastTransactionModelArrayList=new ArrayList<>();

/*    public String msg;
    public String id;
    public String get_id;
    public String dev_id;
    public String lco_id;
    public String payment;
    public String pay_mode;
    public String account_no;
    public String cheque_no;
    public String ifsc;
    public String other_charges;
    public String discount;
    public String dis_amount;
    public String total_amount;
    public String panding_amount;
    public String due_date;
    public String remark;
    public String status;
    public String invoice;
    public String link;
    public String date;
    public String coming_blance;
 //   public String msg;



    * 1{"status":true,
    * "msg":"Data found",
    * "data":[{"id":"485",
    * "get_id":"rai.abhishek727@gmail.com",
    * "dev_id":"15206075577719825",
    * "lco_id":"99903",
    * "payment":"350",
    * "pay_mode":"cash",
    * "account_no":"0",
    * "cheque_no":"0",
    * "ifsc":"0",
    * "other_charges":"0",
    * "discount":"no",
    * "dis_amount":"0",
    * "total_amount":"350",
    * "panding_amount":"0",
    * "due_date":"18-03-18",
    * "remark":"retyuio",
    * "status":"1",
    * "invoice":"180318485",
    * "link":"Web",
    * "date":"2018-03-17 20:45:24",
    * "coming_blance":"300"}]
    * ,"0":350}*/
}
