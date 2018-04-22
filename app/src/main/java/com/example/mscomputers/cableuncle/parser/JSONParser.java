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
        Log.e("processPaymentJson", json);
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

/*
    public PaymentHistoryModel processPaymentJson(String json) {
        Log.e("processPaymentJson",json);

        try {
            JSONObject obj = new JSONObject(json);
            PaymentHistoryModel lm = new PaymentHistoryModel();
            boolean status = obj.getBoolean("status");

            if (status) {

                lm.response = obj.getString("response");

                JSONArray array = obj.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj1 = array.getJSONObject(i);
                    PaymentHistoryModel arrayObject = new PaymentHistoryModel();

                    arrayObject.id = obj1.getString("id");
                    arrayObject.unique_conn = obj1.getString("unique_conn");
                    arrayObject.dev_id = obj1.getString("dev_id");
                    arrayObject.lco_id = obj1.getString("lco_id");
                    arrayObject.stb_no = obj1.getString("stb_no");
                    arrayObject.vc_no = obj1.getString("vc_no");
                    arrayObject.package_name = obj1.getString("package_name");
                    arrayObject.package_validity = obj1.getString("package_validity");
                    arrayObject.package_price = obj1.getString("package_price");
                    arrayObject.bill_start_date = obj1.getString("bill_start_date");
                    arrayObject.tax = obj1.getString("tax");
                    arrayObject.pandig_amount = obj1.getString("pandig_amount");
                    arrayObject.total_amount = obj1.getString("total_amount");
                    arrayObject.date = obj1.getString("date");
                    arrayObject.join_date = obj1.getString("join_date");
                    arrayObject.company_name = obj1.getString("company_name");
                    arrayObject.customer_name = obj1.getString("customer_name");
                    arrayObject.gst = obj1.getString("gst");
                    arrayObject.aadhar = obj1.getString("aadhar");
                    arrayObject.comp_id = obj1.getString("comp_id");
                    arrayObject.phone = obj1.getString("phone");
                    arrayObject.landline = obj1.getString("landline");
                    arrayObject.email = obj1.getString("email");
                    arrayObject.adress = obj1.getString("adress");
                    arrayObject.state = obj1.getString("state");
                    arrayObject.city = obj1.getString("city");
                    arrayObject.area_code = obj1.getString("area_code");
                    arrayObject.pincode = obj1.getString("pincode");
                    arrayObject.lane = obj1.getString("lane");
                    arrayObject.society = obj1.getString("society");
                    arrayObject.builing = obj1.getString("builing");
                    arrayObject.statusInt = obj1.getString("status");
                    arrayObject.remark = obj1.getString("remark");
                    arrayObject.no_of_connection = obj1.getString("no_of_connection");

                    lm.paymentModels.add(arrayObject);
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
*/


    public LastTransactionModel processLastTransactionJson(String json) {
        Log.e("processLastTransactionJson", json);

        try {
            JSONObject obj = new JSONObject(json);
            LastTransactionModel lm = new LastTransactionModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                lm.id = obj.getString("id");
                lm.get_id = obj.getString("get_id");
                lm.dev_id = obj.getString("dev_id");
                lm.lco_id = obj.getString("lco_id");
                lm.payment = obj.getString("payment");
                lm.pay_mode = obj.getString("pay_mode");
                lm.account_no = obj.getString("account_no");
                lm.cheque_no = obj.getString("cheque_no");
                lm.ifsc = obj.getString("ifsc");
                lm.other_charges = obj.getString("other_charges");
                lm.discount = obj.getString("discount");
                lm.total_amount = obj.getString("total_amount");
                lm.panding_amount = obj.getString("panding_amount");
                lm.due_date = obj.getString("due_date");
                lm.remark = obj.getString("remark");
                lm.invoice = obj.getString("invoice");
                lm.link = obj.getString("link");
                lm.date = obj.getString("date");
                lm.coming_blance = obj.getString("coming_blance");


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

/*

    public CategorySubCategoryModel processCategoryJson(String json) {

        try {
            JSONObject obj = new JSONObject(json);
            CategorySubCategoryModel lm = new CategorySubCategoryModel();
            boolean status = obj.getBoolean("status");

            if (status) {
                JSONArray array = obj.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj1 = array.getJSONObject(i);
                    CategoryListModel cates = new CategoryListModel();
                    cates.categoryName = obj1.getString("title");
                    cates.categoryId = obj1.getString("id");
                    JSONArray subArray = obj1.getJSONArray("sub_category");
                    for (int j = 0; j < subArray.length(); j++) {
                        JSONObject subObj = subArray.getJSONObject(j);
                        CategoryListModel subCates = new CategoryListModel();
                        subCates.categoryName = subObj.getString("title");
                        subCates.categoryId = subObj.getString("id");
                        cates.subCategories.add(subCates);
                    }
                    lm.categories.add(cates);
                }
            } else {
                lm.setError(true);
                lm.setErrorMessage(obj.getString("message"));
            }

            return lm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public SectionModel processQuestionJson(String json) {

        try {
            JSONObject obj = new JSONObject(json);
            SectionModel model = new SectionModel();
            boolean status = obj.getBoolean("status");

            if (status) {

                JSONArray batchesArray = obj.getJSONArray("data");
                    for (int i = 0; i < batchesArray.length(); i++) {
                        JSONObject queObj = batchesArray.getJSONObject(i);
                        QuestionModel question = new QuestionModel();
                        question.quesID = queObj.getInt("id");
                        question.catId = queObj.getInt("category_id");
                        question.subCatId = queObj.getInt("sub_cat_id");
                        question.question = queObj.getString("question");
                        question.optionA = queObj.getString("option_a");
                        question.optionB = queObj.getString("option_b");
                        question.optionC = queObj.getString("option_c");
                        question.optionD = queObj.getString("option_d");
                        question.correctAnswer = queObj.getString("correct");
                        question.catName = queObj.optString("category");
                        question.subCatName = queObj.optString("subcategory");
                        question.sectionName = queObj.optString("section");
                        model.questionModels.add(question);
                    }
            } else {
                model.setError(true);
                model.setErrorMessage(obj.getString("message"));
            }
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public SectionModel processQuestionJsonSecond(String json) {

        try {
            JSONObject obj = new JSONObject(json);
            SectionModel model = new SectionModel();
            boolean status = obj.getBoolean("status");

            if (status) {

                JSONArray questionsArray = obj.getJSONArray("questions");
                for (int i = 0; i < questionsArray.length(); i++) {
                    JSONObject obj1 = questionsArray.getJSONObject(i);
                    SectionModel sectionModel=new SectionModel();
                  //d  JSONObject questionMark = obj1.getJSONObject("question_mark");
                    Iterator keys = obj1.keys();

                    while(keys.hasNext()) {
                        // loop to get the dynamic key
                        String currentDynamicKey = (String)keys.next();
                        JSONArray questions= obj1.getJSONArray(currentDynamicKey);
                        for(int j=0;j<questions.length();j++) {
                            JSONObject obj2 = questions.getJSONObject(j);
                            QuestionModel question = new QuestionModel();
                            question.quesID = obj2.getInt("id");
                            question.catId = obj2.getInt("category_id");
                            question.subCatId = obj2.getInt("sub_cat_id");
                            question.question = obj2.getString("question");
                            question.optionA = obj2.getString("option_a");
                            question.optionB = obj2.getString("option_b");
                            question.optionC = obj2.getString("option_c");
                            question.optionD = obj2.getString("option_d");
                            question.correctAnswer = obj2.getString("correct");
                            question.sectionName = obj2.optString("section");

                            model.questionModels.add(question);

                     //d       sectionModel.questionModels.add(question);
                        }

                        // get the value of the dynamic key
                       //d JSONObject currentDynamicValue = questionMark.getJSONObject(currentDynamicKey);
                        System.out.println();
                    }

                    //d model.questionsList.add(sectionModel);
   lm.questions.add(question);

                }
//d                model.questionsList.add(lm);
            } else {
                model.setError(true);
                model.setErrorMessage(obj.getString("message"));
            }

            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public LoginModel processBatchListJson(String json) {

        try {
            JSONObject obj = new JSONObject(json);
            LoginModel blm = new LoginModel();
            boolean status = obj.getBoolean("status");
            if (status) {
                JSONArray array = obj.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj1 = array.getJSONObject(i);
                    BatchModel bm = new BatchModel();
                    bm.id = obj1.getInt("id");
                    bm.name = obj1.getString("name");
                    bm.batchCode = obj1.getString("batch_code");
                    bm.categoryId = obj1.getInt("category_id");
                    bm.subCategoryId = obj1.getInt("sub_cat_id");
                    bm.subCatName = obj1.getString("subcategory");
                    bm.catName = obj1.getString("category");
                    bm.year = obj1.getInt("year");
                    bm.courseDuration = obj1.getString("course_duration");
                    bm.startDate = obj1.getString("start_date");
                    bm.endDate = obj1.getString("end_date");
                    bm.examDuration = obj1.getInt("exam_duration");

                    blm.batchModelArrayList.add(bm);
                }
            } else {
                blm.setError(true);
                blm.setErrorMessage(obj.getString("message"));
            }
            return blm;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public StatusModel processStatusModel(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            StatusModel vsm = new StatusModel();
            boolean status = obj.getBoolean("status");
            String message = obj.getString("message");
            vsm.status = status;
            vsm.message = message;
            return vsm;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentModel processStudentModel(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            StudentModel vsm = new StudentModel();
            boolean status = obj.getBoolean("status");
            vsm.status = status;
            JSONArray array = obj.getJSONArray("data");

            if (status) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj1 = array.getJSONObject(i);
                    StudentModel bm = new StudentModel();
                    bm.id = obj1.getInt("id");
                    bm.studentCode = obj1.getString("student_code");
                    bm.batchId = obj1.getInt("batch_id");
                    bm.batchCode = obj1.getString("batch_code");
                    bm.studenName = obj1.getString("name");
                    bm.email = obj1.getString("email");
                    bm.phone = obj1.getString("phone");
                    bm.dob = obj1.getString("dob");
                    bm.image = obj1.getString("image");
                    bm.address = obj1.getString("address");

                    vsm.studentModelArrayList.add(bm);
                }
            }
            return vsm;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

*/

}
