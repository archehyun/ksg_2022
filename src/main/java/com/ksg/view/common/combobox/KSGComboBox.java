package com.ksg.view.common.combobox;

import java.util.List;

import javax.swing.JComboBox;
import java.awt.*;




import com.ksg.api.model.MyEnum;

public class KSGComboBox<T> extends JComboBox<MyEnum>{

    public KSGComboBox()
    {
     
    
    }


    public void loadData(List<MyEnum> data)
    {
        this.removeAllItems();
        super.addItem(null);
        for(MyEnum obj:data)
        {
            this.addItem(obj);
        }
       
    }
    public void setSelectedValue( Object value)
    {
        MyEnum item;
        for (int i = 0; i < this.getItemCount(); i++)
        {
            item = (MyEnum)getItemAt(i);
            if (item!=null&&item.getField().equalsIgnoreCase((String) value))
            {
                setSelectedIndex(i);
                break;
            }
        }
        
    }
}
