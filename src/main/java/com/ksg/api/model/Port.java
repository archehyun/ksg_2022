package com.ksg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Port {
    private int id;
    private String port_name;
    private String port_area;
    private String port_nationality;
    private String area_code;
    private String event_date;
    private String contents;

    
}
