package com.ksg.api.model;


import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import com.google.gson.JsonObject;


public class ShipperTableData {
    


    JSONArray dateList = new JSONArray();
    
    JSONArray vesselList = new JSONArray();

    JSONArray portList = new JSONArray();

    private final String VESSEL_LIST="vesselList";
    
    private final String PORT_LIST="portList";

    public ShipperTableData()
    {
       
    } 
    
    public String toString()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        return gson.toJson(this);
    }
    public void addDateArrays(String dateArray[][])
    {
        for(String[] date:dateArray)
        {
            addDateArray(date);
        }
    }


    public void addDateArray(String dateArray[])
    {
        JsonArray array = new JsonArray();
        for(String date:dateArray)
        {
            array.add(date);
        }
        dateList.add(array);
    }
    public void addDate(int row, String date)
    {
        JsonArray arry= (JsonArray) dateList.get(row);
        arry.add(date);
    }


    public void addVessel(String vesel_name, String voy)
    {   
        JsonObject vesselInfo1 = new JsonObject();        
        vesselInfo1.addProperty("vessel-name", vesel_name);
        vesselInfo1.addProperty("voyage", voy);
        vesselList.add(vesselInfo1);
        
    }
    public void parse(String data) throws ParseException
    {   
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(data);

        dateList = (JSONArray) jsonObject.get("dateList");

        vesselList = (JSONArray) jsonObject.get(VESSEL_LIST);

        portList  = (JSONArray) jsonObject.get(PORT_LIST);
        
    }

    public void setPorts(String[] ports) {

        portList = new JSONArray();

        for(String port:ports)
        {
            JSONObject portInfo = new JSONObject();

            portInfo.put("port-name", port);            
            portList.add(portInfo);
        }
    }
    public void setDate(int row, int col, String date)
    {
        JSONArray list = (JSONArray) dateList.get(row);
       
        String obj=(String) list.get(col);

        list.set(col, date);
    }

    public JSONArray getVesselList() {
        return vesselList;
    }

    public JSONArray getPortList() {
        return portList;
    }
    public JSONArray getDateList() {
        return dateList;
    }


}
    
