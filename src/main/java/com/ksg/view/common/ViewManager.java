package com.ksg.view.common;

import java.util.ArrayList;
import java.util.Iterator;

import com.ksg.view.common.template.KSGStyle;



public class ViewManager {

    private static ViewManager instance;
	
	ArrayList<KSGStyle> viewList;
	
	KSGViewUtil propeties = KSGViewUtil.getInstance();
	
	private ViewManager()
	{
		viewList = new ArrayList<KSGStyle>();
	}

	
	public static ViewManager getInstance()
	{
		if(instance == null)
			instance = new ViewManager();
		return instance;
	}
	
	public void addView(KSGStyle view)
	{
		viewList.add(view);
	}
	public void update()
	{
		propeties.reload();
		Iterator<KSGStyle> iter = viewList.iterator();
		while(iter.hasNext())
		{
			iter.next().updateStyle();
		}
	}
    
}
