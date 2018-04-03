package com.example.mscomputers.cableuncle.model;

import com.madept.core.model.MAdeptModel;

import java.util.ArrayList;

/**
 * Created by User on 1/20/2017.
 */
public class LoginModel extends MAdeptModel {

    /* {
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

    public String lco;
    public String uniqueId;
    public String email;
    public String message;

    public String areaName;

    public ArrayList<String> modelarrlist=new ArrayList<>();


}