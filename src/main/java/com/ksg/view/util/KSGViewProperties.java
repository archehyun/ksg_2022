package com.ksg.view.util;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
public class KSGViewProperties extends Properties{

    private static KSGViewProperties ksgPropeties;
	
	String resource = "config/setting.properties";

	private KSGViewProperties()
	{   
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            load(reader);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void reload()
	{
		try {
            Reader reader = Resources.getResourceAsReader(resource);
            load(reader);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static KSGViewProperties getInstance()
	{
		if(ksgPropeties == null)

		{
			ksgPropeties = new KSGViewProperties();
		}

		return ksgPropeties;
	}	
    
}
