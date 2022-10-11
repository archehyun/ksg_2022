package com.ksg.view.common.template;

import com.ksg.api.model.CommandMap;

public interface View {
    public CommandMap getModel();
    public void updateView();
    public void setModel(CommandMap model);
    
}
