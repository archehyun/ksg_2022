package com.ksg.view.workbench.shippertable;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ksg.api.model.ShipperTableData;

public class TableBuilder {
    ShipperTableData data;
    private JSONArray vesselList;
    private JSONArray portList;
    private JSONArray dateList;
    public TableBuilder(ShipperTableData data)
    {
        this.data=data;

        vesselList = data.getVesselList();
        portList = data.getPortList();
        dateList = data.getDateList();
    }

    public String[] getHeader() {
        ArrayList<String> head = new ArrayList<String>();
        
        JSONArray portList = data.getPortList();
        

        head.add("Vessel");
        head.add("Voyage");
        for(int i=0;i<portList.size();i++)
        {
            JSONObject e = (JSONObject) portList.get(i);
            head.add((String)e.get("port-name"));
        }

        String[] headArray = head.toArray(new String[head.size()]);
        
        
        return headArray;
    }

    public String[][] getVessel()
    {
       
        String vessels[][] = new String[vesselList.size()][2];

        for(int i=0;i<vesselList.size();i++)
        {
            JSONObject e = (JSONObject) vesselList.get(i);
            
            vessels[i][0] = (String) e.get("vessel-name");
            vessels[i][1] = (String) e.get("voyage");
        }
        return  vessels;
    }

    public String[] getPorts() {

        String ports[] = new String[portList.size()];

        for(int i=0;i<portList.size();i++)
        {
            JSONObject e = (JSONObject) portList.get(i);
            
            ports[i] = (String) e.get("port-name");
        }
        return  ports;
    }

    public String[][] getDateArray() {

        int vesselCount = vesselList.size();
        int dateArrayCount=dateList.size();
        int rowCount = vesselCount;
        //vesselCount>dateArrayCount?vesselCount:dateArrayCount;
        String date[][] = new String[vesselCount][];

        for(int i=0;i<rowCount;i++)
        {
            ArrayList<String> rowArray = new ArrayList<String>();

            JSONObject e = (JSONObject) vesselList.get(i);
           
            if(i<dateArrayCount)
            {
                JSONArray ob = (JSONArray) dateList.get(i);
            
                for(int j=0;j<ob.size();j++)
                {
                    rowArray.add(ob.get(j).toString());

                }
            }
           
            date[i] = rowArray.toArray(new String[rowArray.size()]);;

        }


        return date;
    }

    public String[][] getDataArray() {

        int vesselCount = vesselList.size();
        int dateArrayCount=dateList.size();
        int rowCount = vesselCount;
        //vesselCount>dateArrayCount?vesselCount:dateArrayCount;
        String date[][] = new String[vesselCount][];

        for(int i=0;i<rowCount;i++)
        {
            ArrayList<String> rowArray = new ArrayList<String>();

            JSONObject e = (JSONObject) vesselList.get(i);
            rowArray.add((String)e.get("vessel-name"));
            rowArray.add((String)e.get("voyage"));

            if(i<dateArrayCount)
            {
                JSONArray ob = (JSONArray) dateList.get(i);
            
                for(int j=0;j<ob.size();j++)
                {
                    rowArray.add(ob.get(j).toString());
                    // sub[j] = ob.get(j).toString();
                }
            }
           
            date[i] = rowArray.toArray(new String[rowArray.size()]);;

        }


        return date;
    }
    
}
