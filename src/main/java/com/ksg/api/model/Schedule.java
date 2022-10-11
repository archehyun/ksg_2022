package com.ksg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Schedule {

    private int id;
    private String table_id;
    private String company_name;
    private String vessel_name;
    private String vessel_voyage;
    private String vessel_type;
    private String schedule_type;
    private String inout_type;
    private String event_date;
    private String area_code;
    private String area_name;
    private String from_port;
    private String to_port;
    private String from_date;
    private String to_date;



    
    
    
}
