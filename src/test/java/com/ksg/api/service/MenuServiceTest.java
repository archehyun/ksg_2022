package com.ksg.api.service;

import org.junit.jupiter.api.Test;

public class MenuServiceTest {

    @Test
    void test1() {

        MenuService service = new MenuService();
        System.out.println("## test1 시작 ##");
        System.out.println(service.selectAll());
    }

    
}
