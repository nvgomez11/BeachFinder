package com.example.nelson.beachfinder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by estadm on 12/6/2018.
 */

public class beachSelected {
    private String idRealSelectedBeach;

    private ArrayList<String> chosen_beach_info = new ArrayList<String>();
    private Boolean data=false;

    private static beachSelected instanceBeach;

    public static beachSelected getInstance(){
        if(instanceBeach == null){
            instanceBeach = new beachSelected();
        }
        return instanceBeach;
    }

    public String getComments(){

        return chosen_beach_info.get(18);
    }


    public void setIdRealForBeach(String pidRealSelectedBeach)
    {
        idRealSelectedBeach=pidRealSelectedBeach;

    }






    public String getIdRealForBeach() {
        return idRealSelectedBeach;
    }



    public ArrayList<String> getChosen_beach_info() {
        return chosen_beach_info;
    }

    public void setChosen_beach_info(ArrayList<String> pChosen_beach_info) {

        for(int i=0;i< pChosen_beach_info.size();i++)
        {
            chosen_beach_info.add(pChosen_beach_info.get(i));

        }
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean pData) {
        data = pData;
    }
}
