package com.ksg.view.common.table;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
public class WhiteTableUI extends AbstractTableUI{

    class HeaderRenderer extends DefaultTableCellRenderer
	{
		Color headFront;

		public HeaderRenderer(Color headBackColor)
		{
			headFront = headBackColor;
		}
		
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			if (table != null) {
				JTableHeader header = table.getTableHeader();
				if (header != null) {
					setForeground(Color.gray.darker());

					setBackground(headFront);

					Font f = header.getFont();

					Font font = new Font(f.getFontName(),Font.BOLD,12);
					setFont(font);

				}
			}
			setText((value == null) ? "" : value.toString());

			setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.lightGray));

			setHorizontalAlignment(JLabel.CENTER);

			this.setPreferredSize(new Dimension(this.getSize().width,30));

			return this;
		}
	}

    class KSGTableCellRenderer extends DefaultTableCellRenderer {

		int leftPadding = 10;

		int rightPadding = 10;

		//		Color selectedColor = new Color(51, 153, 255);
		Color selectedColor = new Color(255, 242, 230);
		Border padding = BorderFactory.createEmptyBorder(0, leftPadding, 0, rightPadding);

		boolean isOdd=false;

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			((JLabel) renderer).setOpaque(true);
			Color foreground=Color.black, background=Color.white;
			JTableHeader header = table.getTableHeader();


			if (isSelected) {
				foreground = Color.black;
				background = selectedColor;
			} 
			else {
				if(isOdd) {

					if (row % 2 == 0) {
						foreground = Color.black;
						background = Color.WHITE;
					} else {
						background = Color.WHITE;
						foreground = Color.black;
					}
				}
			}
			

			setBorder(BorderFactory.createCompoundBorder(padding, padding));
			renderer.setForeground(foreground);
			renderer.setBackground(background);

			return renderer;
		}
	}

    @Override
    public DefaultTableCellRenderer getHeaderRenderer() {
        
        return new HeaderRenderer(Color.white);
    }

    @Override
    public DefaultTableCellRenderer getCellRenderer() {
       
        return new KSGTableCellRenderer() ;
    }
    
}
