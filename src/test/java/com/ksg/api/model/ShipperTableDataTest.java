package com.ksg.api.model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import com.google.gson.JsonElement;
// import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;
public class ShipperTableDataTest {
    @Test
    void test1() {
       
        ShipperTableData data = new ShipperTableData();
        
        // vessel
        data.addVessel("testHo1", "testHo1");
        data.addVessel("testHo2", "testHo2");

        // port
        String ports[] = {"port01", "port02"};
        data.setPorts(ports);

        // port
     
       // data.getPorts();
        String[] dateArray1 ={"20220808", "20220808"};
        String[] dateArray2 ={"20220908", "20220908"};

        String[][] dateArray3 ={{"20220808", "20220808"},
        {"20220908", "20220908"}};
        // data.addDateArray(dateArray1);
        // data.addDateArray(dateArray2);
        data.addDateArrays(dateArray3);

         System.out.println(data);
    }

    @Test
    void parse() throws ParseException {


        ShipperTableData data = new ShipperTableData();
        System.out.println(data);
        System.out.println("init:=================");

       String strdata = "{\"dateList\": [     [\"20220808\",          \"20220808\"        ],        [          \"20220908\",          \"20220908\"        ]      ],"
       + "\"vesselList\": [ {\"vessel-name\": \"testHo1\",\"voyage\": \"testHo1\"}],  \"portList\": [    {      \"port-name\": \"port01\"    },    {      \"port-name\": \"port02\"    }  ]}";
        
       

        data.parse(strdata);

        System.out.println(data);
       // System.out.println(data.getPorts()[0]);

        data.setDate(0, 0, "20220707");
        System.out.println("=================");
        System.out.println(data);
    }
    @Test
    public void test()
    {
        int total =11;
        int pageCount =10;
        System.out.println(total/pageCount);
        System.out.println(total/pageCount+(total%pageCount>0?1:0));
    }

    
}
