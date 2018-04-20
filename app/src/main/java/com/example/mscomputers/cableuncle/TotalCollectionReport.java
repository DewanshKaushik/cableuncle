package com.example.mscomputers.cableuncle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mscomputers.cableuncle.model.LoginModel;
import com.example.mscomputers.cableuncle.model.TotalCollectionReportModel;
import com.example.mscomputers.cableuncle.parser.JSONParser;
import com.example.mscomputers.cableuncle.util.Constants;
import com.madept.core.activity.MAdeptActivity;
import com.madept.core.net.MAdeptErrorResponse;
import com.madept.core.net.MAdeptRequest;
import com.madept.core.net.MAdeptResponse;
import com.madept.core.net.MAdeptResponseListener;
import com.madept.core.prefs.MAdeptPrefs;
import com.madept.core.util.MAdeptUtil;

/**
 * Created by MS Computers on 12/16/2017.
 */
public class TotalCollectionReport extends MAdeptActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.total_collection_report);

        getData();

    }

    public void goToNext(View v) {
        Intent i = new Intent(this,LastTransactions.class);
        startActivity(i);

    }

    public void getData(){
            MAdeptPrefs.getInstance(TotalCollectionReport.this).loadPrefs();

            MAdeptRequest req = new MAdeptRequest(Constants.GET_TOTAL_COLLECTION_REPORT, this, Constants.GET_TOTAL_COLLECTION_URL, MAdeptRequest.METHOD_POST);
            req.setJSONParser(JSONParser.getInstance());
            req.addParam("getid", MAdeptPrefs.getInstance(TotalCollectionReport.this).getPrefs(Constants.UNIQUE_ID));
            req.addParam("from_date", "2018-04-18");
            req.addParam("last_date", "2018-04-19");
            req.addParam("lco", MAdeptPrefs.getInstance(TotalCollectionReport.this).getPrefs(Constants.LCO));


            req.processForData(new MAdeptResponseListener() {
                @Override
                public void onResponse(final MAdeptResponse resp) {
                    TotalCollectionReport.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resp.isError())// Handle error code
                            {
                                if (resp instanceof MAdeptErrorResponse) {
                                    MAdeptErrorResponse error = (MAdeptErrorResponse) resp;
                                    MAdeptUtil.showToast(TotalCollectionReport.this, error.getErrorMessage());
                                } else {
                                    MAdeptUtil.showToast(TotalCollectionReport.this, resp.dataModel.getErrorMessage());
                                }

                            } else {
                                final TotalCollectionReportModel data = (TotalCollectionReportModel) resp.dataModel;
                                if (data != null) {
                                   System.out.println();
                                }
                            }
                        }
                    });

                }

            });

    }


    /*
    * http://cableuncle.in/cableuncle/android/total_collection_fieldboy.php
        Parameter
        getid : 9990501
        from_date : 2018-04-18
        last_date : 2018-04-19
        lco : 99905*/

}
