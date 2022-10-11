package com.ksg.api.model;

import org.json.simple.parser.ParseException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class ShipperTable {

    private ShipperTableData shipperdata = new ShipperTableData();
    private int id;
    private String table_id;
    private String title;
    // private String data;
    private String agent;
    private String company;
    private String inbound_from_index;
    private String inbound_to_index;
    private String outbound_from_index;
    private String outbound_to_index;
    private String event_date;
    private String table_type;
    private int page;

    public void setData(String data) throws ParseException
    { 
        if(shipperdata==null)
            shipperdata =new ShipperTableData();
            
        shipperdata.parse(data);
        
    }
    public String getData()
    {
        if(shipperdata==null)
        shipperdata =new ShipperTableData();
        return shipperdata.toString();
    }
    
}
