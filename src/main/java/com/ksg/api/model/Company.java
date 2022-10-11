package com.ksg.api.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Company {
    private int id;
    private String company_name;
    private String company_abbr;
    private String agent_name;
    private String agent_abbr;
    private String event_date;
    private String contents;		

    
}
