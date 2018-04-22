package com.example.mscomputers.cableuncle.model;

import com.madept.core.model.MAdeptModel;

import java.io.Serializable;

/**
 * Created by Krishan Kumar on 20-04-2018.
 */

public class TotalCollectionReportModel  extends MAdeptModel implements Serializable {

    public String days;
    public int totalCash;
    public int totalCheque;
    public int otherTotal;
    public int grandTotal;
    public String fromDateAndTimeString;
    public String toDateAndTimeString;


    /*
    * {
    "status": true,
    "msg": "Data found",
    "total_cash": 3910,
    "total_cheque": 0,
    "other_total": 0,
    "grand_total": 3910
}*/
}
