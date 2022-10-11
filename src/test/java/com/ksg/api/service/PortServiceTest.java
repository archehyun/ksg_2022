package com.ksg.api.service;

import org.junit.jupiter.api.Test;

public class PortServiceTest {

    @Test
    void test1() {

        PortService service = new PortService();
        System.out.println("## test1 시작 ##");
        System.out.println(service.selectAll());
    }

    
}
