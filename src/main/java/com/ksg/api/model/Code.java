package com.ksg.api.model;


import org.springframework.core.codec.CodecException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Code {
    
    int code_id;
    String code_class_id;    
    String code_name;
    String code_class_name;
    String code_field1;
    String code_field2;
    String code_field3;
    String event_date;    
}
