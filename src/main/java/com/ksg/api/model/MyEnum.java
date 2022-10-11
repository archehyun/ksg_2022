package com.ksg.api.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class MyEnum {

    private String name;
    private String field;
    public String toString()
    {
        return name;
    }
    
}
