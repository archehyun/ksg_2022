/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ksg.view.common.panel;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.ksg.view.util.KSGViewProperties;
import com.ksg.view.util.ViewUtil;

import java.awt.BorderLayout;
// import com.ksg.common.model.KSGModelManager;
// import com.ksg.common.util.KSGPropertis;


/**
 * @author 박창현
 *
 */
public class KSGPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KSGViewProperties viewPropeties = KSGViewProperties.getInstance();

    private Color background=Color.white;

	public KSGPanel()
	{
		super();
		this.setLayout(new BorderLayout());
		this.setDoubleBuffered(true);

		try {
			background=ViewUtil.getColorRGB(viewPropeties.getProperty("panel.background.color"));
		}catch(Exception e)

		{
			background = Color.white;
		}
		this.setBackground(background);
	}
	public KSGPanel(LayoutManager layout) {
		this();

		this.setLayout(layout);		
	}
}
