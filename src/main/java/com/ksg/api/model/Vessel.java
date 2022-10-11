package com.ksg.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 선박 정보 클래스
 */
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Vessel {
    private int id;
    private String vessel_name;
    private String vessel_abbr;
    private String vessel_type;
    private String vessel_company;
    private String vessel_use;
    private String vessel_mmsi;
    private String event_date;
    private String contents;
    
}
