package com.example.mscomputers.cableuncle.util;

/**
 * Created by User on 12/27/2016.
 */
public class Constants {

    public static final String PREFS_EMAIL = "email";
    public static final String PREFS_IS_LOGIN = "isLogin";
    public static final String LCO = "lco";
    public static final String UNIQUE_ID = "uniqueId";
    public static final String EMAIL = "email";

    public static final int LOGIN_REQUEST = 0;
    public static final int DASHBOARD_REQUEST = 1;
    public static final int PAYMENT_REQUEST = 2;
    public static final int LAST_TRANSACTION_REQUEST = 3;
    public static final int FORGET_PASS_REQUEST = 4;
    public static final int COMPLETE_RESOLVE_REQUEST = 5;
    public static final int COMPLAINT_VIEW_REQUEST = 6;
    public static final int USER_REQUEST = 7;
    public static final int PAYNOW_REQUEST = 8;
    public static final int PAYMENT_RQUEST = 9;
    public static final int UPDATE_PHONE_RQUEST = 10;
    public static final int LOGOUT = 11;
    public static final int SUBMIT_PAYMENT = 12;
    public static final int GET_AREA = 13;
    public static final int GET_TOTAL_COLLECTION_REPORT = 14;
    public static final int GET_LAST_TRANSACTION = 15;


    public static final String TEST_IP_ADDRESS = "192.168.1.36:8000";
    public static final String SERVER_IP_ADDRESS = "www.madept.com/samiksha";
    public static final String BASE_URL = "http://" + SERVER_IP_ADDRESS + "/api/";
    //  public static final String LOGIN_URL = BASE_URL + "accessor_login";
    public static final String DASHBOARD_URL = "http://www.cableuncle.in/cableuncle/android/dashboard.php";
    public static final String TOTAL_COLLECTION_URL = "http://www.cableuncle.in/cableuncle/android/total_collection.php";
    public static final String PAYMENT_URL = "http://www.cableuncle.in/cableuncle/android/payment.php";


    // public static final String PAYMENT_HISTORY_URL = "http://cableuncle.in/cableuncle/android/payment_history.php";
    public static final String FORGET_PASS_URL = "http://www.cableuncle.in/cableuncle/android/forgetpass.php";
    public static final String COMPLETE_RESOLVE_URL = "http://www.cableuncle.in/cableuncle/android/complain_resolve.php";
    public static final String COMPLAINT_VIEW_URL = "http://www.cableuncle.in/cableuncle/android/complaint_view.php";
    public static final String USER_DETAIL_URL = "http://www.cableuncle.in/cableuncle/android/user_detail.php";


    public static final String PAYNOW_URL = "http://www.cableuncle.in/cableuncle/android/show_payment.php";
    public static final String UPDATE_PHONE_URL = "http://www.cableuncle.in/cableuncle/android/phone_editing.php";
    public static final String LOGINURL = "http://www.cableuncle.in/cableuncle/android/login2.php";
    public static final String LOGOUT_URL = "http://www.cableuncle.in/cableuncle/android/logout.php";
    public static final String SUBMIT_PAYMENT_URL = "http://www.cableuncle.in/cableuncle/android/payment_submit.php";
    public static final String GET_AREA_URL = "http://www.cableuncle.in/cableuncle/android/area_name.php";
    public static final String GET_TOTAL_COLLECTION_URL = "http://cableuncle.in/cableuncle/android/total_collection_fieldboy.php";
    public static final String GET_LAST_TRANSACTION_URL = "http://cableuncle.in/cableuncle/android/last_6_transation.php";


/*

  More
3 of 581

api
Inbox
x

cable uncle		AttachmentsMar 24 (1 day ago)


cable uncle		AttachmentsMar 24 (1 day ago)
check kr lijiye

cable uncle
AttachmentsMar 24 (1 day ago)
to me

Attachments area

cable uncle
Attachments11:34 PM (19 hours ago)
to me


area_name Api:
http://cableuncle.in/cableuncle/android/area_name.php

lco=99903
area= 6070511520617750


http://cableuncle.in/cableuncle/android/lco_details.php

lco=99903


1.Login Api:

http://cableuncle.in/cableuncle/android/login2.php

Param:email:arun@gmail.com
password=123456789


2.Dashboard Api.

http://cableuncle.in/cableuncle/android/dashboard.php
Param:lco=99903
area:D BLOCK


3.Total Collection

http://cableuncle.in/cableuncle/android/total_collection.php
 param: lco=99903
from_date='2018-03-01'
last_date='2018-03-15'

4.Payment History
http://cableuncle.in/cableuncle/android/payment_history.php


param: lco=99903
dev_id=10001 or other


5.User_details
http://cableuncle.in/cableuncle/android/user_detail.php

param:
lco=99903 and dev_id=10001,10002,10003 koi bhi

6.http://cableuncle.in/cableuncle/android/forgetpass.php
param email:shweta.gupta249@gmail.com


7.complaint_view

http://cableuncle.in/cableuncle/android/complain_resolve.php

param: lco=99903
area=D BLOCK

8.complaint resolve

lco=99903
area=D BLOCK

http://cableuncle.in/cableuncle/android/complaint_view.php

9.Payement
http://cableuncle.in/cableuncle/android/payment.php

lco_id=99903
area=A Blok


10.http://cableuncle.in/cableuncle/android/phone_editing.php
lco_id=99903
dev_id=10001
phone=?


11.http://cableuncle.in/cableuncle/android/payment_submit.php

get_id
lco
dev_id
total_bill
pay_mode
payment
cheque_no
ifsc_code
dis_amount
other
discount
due_date
remark
$account

12. http://cableuncle.in/cableuncle/android/payment_bill.php
lco_id=99903
dev_id=10001,10002,10003,10004

13.area_name Api:
http://cableuncle.in/cableuncle/android/area_name.php

lco=99903
area= 6070511520617750

14.http://cableuncle.in/cableuncle/android/lco_details.php

lco=99903
*/
}
