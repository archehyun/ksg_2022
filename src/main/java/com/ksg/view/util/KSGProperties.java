package com.ksg.view.util;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
public class KSGProperties extends Properties{

    private static KSGProperties ksgPropeties;
	
	String resource = "config/setting.properties";

	private KSGProperties()
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

	public static KSGProperties getInstance()
	{
		if(ksgPropeties == null)

		{
			ksgPropeties = new KSGProperties();
		}

		return ksgPropeties;
	}	
    
}
