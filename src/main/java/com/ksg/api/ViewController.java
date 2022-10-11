package com.ksg.api;

import org.springframework.stereotype.Controller;

import com.ksg.view.workbench.admin.Mainframe;

@Controller
public class ViewController {
    

    Mainframe frame;
    
    public ViewController()
    {
        frame = new Mainframe();
    }
}
