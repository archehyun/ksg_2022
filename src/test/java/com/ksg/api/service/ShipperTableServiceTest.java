package com.ksg.api.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.ksg.api.model.ShipperTable;

public class ShipperTableServiceTest {

    ShipperTableService service; 
    @Test
    public void selectAll()
    {
        service = new ShipperTableService();
        List<ShipperTable> li=service.selectAll();
        //Assert.isTrue(li.size()==1, "message");
        System.out.println("li:"+li.size());
        
    }
}
