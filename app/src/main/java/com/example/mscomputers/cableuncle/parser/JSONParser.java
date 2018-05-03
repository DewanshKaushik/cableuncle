package com.example.mscomputers.cableuncle.parser;


import android.util.Log;

import com.example.mscomputers.cableuncle.CableUncleApplication;
import com.example.mscomputers.cableuncle.Login;
import com.example.mscomputers.cableuncle.model.DashboardModel;
import com.example.mscomputers.cableuncle.model.LastTransactionModel;
import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.PayNowModel;
import com.example.mscomputers.cableuncle.model.PaymentModel;
import com.example.mscomputers.cableuncle.model.TotalCollectionReportModel;
import com.example.mscomputers.cableuncle.model.UserModel;
import com.example.mscomputers.cableuncle.util.Constants;
import com.madept.core.model.MAdeptModel;
import com.madept.core.parser.MAdeptJSonParser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by User on 12/27/2016.
 */


public class JSONParser extends MAdeptJSonParser {

    private static JSONParser instance;

    private JSONParser() {

    }

    public static JSONParser getInstance() {
        if (instance == null)
            instance = new JSONParser();
        return instance;
    }

    public MAdeptModel parse(String json, int requestId) {
        switch (requestId) {
            case Constants.LOGIN_REQUEST:
                return processLoginJson(json);
            case Constants.DASHBOARD_REQUEST:
                return processDashboardJson(json);
            case Constants.PAYMENT_REQUEST:
                return processPaymentJson(json);
            case Constants.LAST_TRANSACTION_REQUEST:
                return processLastTransactionJson(json);
            case Constants.USER_REQUEST:
                return processUserJson(json);
            case Constants.FORGET_PASS_REQUEST:
                return processForgetPassJson(json);
            case Constants.COMPLETE_RESOLVE_REQUEST:
                return processCompleteResolveJson(json);
            case Constants.COMPLAINT_VIEW_REQUEST:
                return processCompleteViewJson(json);
            case Constants.PAYNOW_REQUEST:
                return processPayNowJson(json);
            case Constants.UPDATE_PHONE_RQUEST:
                return processPhoneUpdateJson(json);
            case Constants.LOGOUT:
                return processLogoutJson(json);
            case Constants.SUBMIT_PAYMENT:
                return processSubmitPaymentJson(json);
            case Constants.GET_AREA:
                return processGetAreaJson(json);
            case Constants.GET_TOTAL_COLLECTION_REPORT:
                return processTotalCollectionJson(json);
        }
        return null;
    }

    public TotalCollectionReportModel processTotalCollectionJson(String json){
        Log.e("processTotalCollectionJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            TotalCollectionReportModel lm = new TotalCollectionReportModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                lm.totalCash=obj.getInt("total_cash");
                lm.totalCheque=obj.getInt("total_cheque");
                lm.otherTotal=obj.getInt("other_total");
                lm.grandTotal=obj.getInt("grand_total");

            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginModel processGetAreaJson(String json){
           /*
        {
        "status": "True",
        "response": "Success",
        "msg": "Your Phone number is successfully updated"

        }*/
        Log.e("processGetAreaJson", json);
        try {
            JSONObject obj = new JSONObject(json);
            LoginModel lm = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                JSONArray array = obj.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj3 = array.getJSONObject(i);

                    lm.modelarrlist.add(obj3.getString("area_name"));

                }

            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PayNowModel processSubmitPaymentJson(String json) {
/*
* {
    "status": true,
    "response": "Success",
    "msg": "Your payment is successfully completed",
    "data": {
        "lco_name": "Abhishek Rai",
        "lco_complain": "9958990953,7011977736",
        "customer_name": "Abhishek Rai",
        "subscriber_no": "1",
        "address": "K-46/D",
        "no_of_tv": "1",
        "phone": "9958990953",
        "invoice": "18040101",
        "date": "2018-04-01",
        "basic": "300",
        "add_charges": 0,
        "sgst": 0,
        "cgst": 0,
        "due_balance": 50,
        "Grand_total": 250,
        "payment_mode": "cash",
        "cheque_no": 0,
        "paid_amount": 63.14,
        "remark": "testing"
    }
}*/
        Log.e("processSubmitPaymentJson", json);
        try {
            JSONObject obj = new JSONObject(json);
            PayNowModel lm = new PayNowModel();
            boolean status = obj.getBoolean("status");
            if (status) {
                JSONObject obj1 = obj.getJSONObject("data");
                lm.lcoName = obj1.getString("lco_name");
                lm.lcoComplain = obj1.getString("lco_complain");

                lm.customerName = obj1.getString("customer_name");
                lm.subscriberName = obj1.getString("subscriber_no");
                lm.noOfTv = obj1.getString("no_of_tv");
                lm.address = obj1.getString("address");
                lm.phone = obj1.getString("phone");
                lm.invoice = obj1.getString("invoice");
                lm.date = obj1.getString("date");
                lm.basic = obj1.getString("basic");
                lm.add_charges = obj1.getInt("add_charges");
                lm.sgst = obj1.getDouble("sgst");
                lm.cgst = obj1.getDouble("cgst");
                lm.dueBalance = obj1.getInt("due_balance");
                lm.total = obj1.getInt("Grand_total");

                lm.paid_amount=obj1.getInt("paid_amount");
                lm.payment_mode= obj1.getString("payment_mode");
                lm.cheque_no= obj1.getInt("cheque_no");
                lm.remark= obj1.getString("remark");
            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public LoginModel processLogoutJson(String json) {
        /*
        {
        "status": "True",
        "response": "Success",
        "msg": "Your Phone number is successfully updated"

        }*/
        Log.e("processLogoutJson", json);
        try {
            JSONObject obj = new JSONObject(json);
            LoginModel lm = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {

                lm.message = obj.getString("msg");

            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public LoginModel processPhoneUpdateJson(String json) {
        /*
        {
        "status": "True",
        "response": "Success",
        "msg": "Your Phone number is successfully updated"

        }*/
        Log.e("processPhoneUpdateJson", json);
        try {
            JSONObject obj = new JSONObject(json);
            LoginModel lm = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {

                lm.message = obj.getString("msg");

            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public PayNowModel processPayNowJson(String json) {
            /*
        * {
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
        Log.e("processPayNowJson", json);
        try {
            JSONObject obj = new JSONObject(json);
            PayNowModel lm = new PayNowModel();
            boolean status = obj.getBoolean("status");

            if (status) {

                JSONObject obj1 = obj.getJSONObject("data");
                // JSONObject obj2 =obj1.getJSONObject("lco_name");
                lm.lcoName = obj1.getString("lco_name");
                lm.customerName = obj1.getString("customer_name");
                lm.subscriberName = obj1.getString("subscriber_no");
                lm.noOfTv = obj1.getString("no_of_tv");
                lm.phone = obj1.getString("phone");
                lm.balance = obj1.getString("balance");
                lm.basic = obj1.getString("basic");
                lm.total = obj1.getInt("total");
                lm.cus_addresss = obj1.getString("cus_addresss");


            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginModel processLoginJson(String json) {
        /*
        * {
    "status": true,
    "response": "Success",
    "data": [
        {
            "lco": "99903",
            "unique_id": "95285563461234",
            "email": "shweta.gupta249@gmail.com",
            "area": [
                {
                    "area_name": "D BLOCK"
                },
                {
                    "area_name": "A Blok"
                }
            ]
        }
    ]
}*/
        Log.e("parseLoginDatajson", json);
        try {
            JSONObject obj = new JSONObject(json);
            LoginModel lm = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                JSONArray array = obj.getJSONArray("data");
                JSONObject obj2 = array.getJSONObject(0);

                lm.lco = obj2.getString("lco");
                lm.uniqueId = obj2.getString("unique_id");
                lm.email = obj2.getString("email");


                JSONArray array2 = obj2.getJSONArray("area");
                String[] arr = new String[array2.length()];
                for (int i = 0; i < array2.length(); i++) {
                    JSONObject obj3 = array2.getJSONObject(i);

                    lm.modelarrlist.add(obj3.getString("area_name"));

                }

            } else {
                lm.setError(true);
                lm.setErrorMessage("error");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public DashboardModel processDashboardJson(String json) {
        Log.e("processDashboardJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            DashboardModel lm = new DashboardModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                lm.totalCustomer = obj.getInt("total_customer");
                lm.pandingCustomer = obj.getInt("panding_customer");
                lm.paidCustomer = obj.getInt("paid_customer");

            } else {
                lm.setError(true);
                lm.setErrorMessage("");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PaymentModel processPaymentJson(String json) {
        Log.e("processPaymentJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            PaymentModel lm = new PaymentModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                JSONArray array = obj.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    PaymentModel model = new PaymentModel();
                    JSONObject obj2 = array.getJSONObject(i);

                    model.compId = obj2.getString("comp_id");
                    model.name = obj2.getString("name");

                    lm.modelArrayList.add(model);
                }

            } else {
                lm.setError(true);
                lm.setErrorMessage("");
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public LastTransactionModel processLastTransactionJson(String json) {
        Log.e("processLastTransactionJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            LastTransactionModel model = new LastTransactionModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                JSONArray object = obj.getJSONArray("data");

                for (int i = 0; i < object.length(); i++) {
                    LastTransactionModel lm = new LastTransactionModel();
                    JSONObject obj3 = object.getJSONObject(i);

                    lm.company_name = obj3.getString("company_name");
                    lm.lco_address = obj3.getString("lco_address");
                    lm.complain_no_1 = obj3.getString("complain_no_1");
                    lm.complain_no_2 = obj3.getString("complain_no_2");
                    lm.customer_name = obj3.getString("customer_name");
                    lm.comp_id = obj3.getString("comp_id");
                    lm.adress = obj3.getString("adress");
                    lm.phone = obj3.getString("phone");
                    lm.no_of_connection = obj3.getString("no_of_connection");
                    lm.package_price = obj3.getString("package_price");
                    lm.other_charges = obj3.getString("other_charges");
                    lm.igst = obj3.getString("igst");
                    lm.cgst = obj3.getString("cgst");
                    lm.total_amount = obj3.getString("total_amount");
                    lm.panding_amount = obj3.getString("panding_amount");
                    lm.date = obj3.getString("date");
                    lm.pay_mode = obj3.getString("pay_mode");
                    lm.invoice = obj3.getString("invoice");
                    lm.remark = obj3.getString("remark");

                    model.lastTransactionModelArrayList.add(lm);
                }

            } else {
                model.setError(true);
                model.setErrorMessage("");
            }

            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public UserModel processUserJson(String json) {
        Log.e("processUserJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            UserModel model = new UserModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                JSONArray object = obj.getJSONArray("data");

                for (int i = 0; i < object.length(); i++) {
                    UserModel lm = new UserModel();

                    lm.id = obj.getString("id");
                    lm.join_date = obj.getString("join_date");
                    lm.dev_id = obj.getString("dev_id");
                    lm.company_name = obj.getString("company_name");
                    lm.customer_name = obj.getString("customer_name");
                    lm.gst = obj.getString("gst");
                    lm.aadhar = obj.getString("aadhar");
                    lm.comp_id = obj.getString("comp_id");
                    lm.lco_id = obj.getString("lco_id");
                    lm.phone = obj.getString("id");
                    lm.landline = obj.getString("landline");
                    lm.email = obj.getString("email");
                    lm.tax = obj.getString("tax");
                    lm.adress = obj.getString("adress");
                    lm.state = obj.getString("state");
                    lm.city = obj.getString("city");
                    lm.area_code = obj.getString("area_code");
                    lm.pincode = obj.getString("pincode");
                    lm.lane = obj.getString("lane");
                    lm.society = obj.getString("society");
                    lm.builing = obj.getString("builing");
                    lm.status = obj.getString("status");
                    lm.remark = obj.getString("remark");
                    lm.no_of_connection = obj.getString("no_of_connection");
                    lm.date = obj.getString("date");
                    lm.unique_conn = obj.getString("unique_conn");
                    lm.stb_no = obj.getString("stb_no");
                    lm.vc_no = obj.getString("vc_no");
                    lm.package_name = obj.getString("package_name");
                    lm.package_validity = obj.getString("package_validity");
                    lm.package_price = obj.getString("package_price");
                    lm.bill_start_date = obj.getString("bill_start_date");
                    lm.pandig_amount = obj.getString("pandig_amount");
                    lm.total_amount = obj.getString("total_amount");

                    model.modelArrayList.add(lm);
                }

            } else {
                model.setError(true);
                model.setErrorMessage("");
            }

            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public LoginModel processForgetPassJson(String json) {
        Log.e("processForgetPassJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            LoginModel model = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                model.message = obj.getString("msg");

            } else {
                model.setError(true);
                model.setErrorMessage("");
            }

            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginModel processCompleteResolveJson(String json) {
        Log.e("processCompleteResolveJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            LoginModel model = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                model.message = obj.getString("msg");

            } else {
                model.setError(true);
                model.setErrorMessage("");
            }

            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public LoginModel processCompleteViewJson(String json) {
        Log.e("processCompleteViewJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            LoginModel model = new LoginModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                model.message = obj.getString("msg");

            } else {
                model.setError(true);
                model.setErrorMessage("");
            }

            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
