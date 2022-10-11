package com.ksg.view.workbench.admin;

import com.ksg.view.common.template.LeftTreeUI;

public class SystemUI extends LeftTreeUI{

    public SystemUI(String name)
    {
        super(name);
        
        this.addComp(new CodeMgtPn("공통코드관리"));        
        
        this.createUIandUpdate();
    }
}
