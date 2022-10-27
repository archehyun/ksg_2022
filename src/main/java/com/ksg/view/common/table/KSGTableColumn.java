package com.ksg.view.common.table;

import javax.swing.JLabel;
import javax.swing.table.TableColumn;

/**
 * 테이블 컬럼
 */
public class KSGTableColumn extends TableColumn{
	
	
	
	public int ALIGNMENT=JLabel.CENTER;
	
	public int size = 0;
	
	public int minSize = 0;
	
	public int maxSize = 0;

	public String columnName;

	public String columnField;

	public int cloumIndex;
	
	public KSGTableColumn() {

	}
	public KSGTableColumn(String columnField, String columnName) {
		this.columnField = columnField;
		this.columnName = columnName;
	}

	public KSGTableColumn(String columnField, String columnName, int size) {
		this(columnField, columnName);
		
		this.size = size;
	}
	
	public KSGTableColumn(String columnField, String columnName, int size, int alignment) {
		this(columnField, columnName, size);
		
		this.ALIGNMENT = alignment;
	}

	public KSGTableColumn(String columnField, String columnName, int size, int maxSize, int alignment) {
		this(columnField, columnName, size, alignment);
		
		this.maxSize = maxSize;
	}
	
	public Object getValue(Object obj)
	{
		return obj;
	}


	@Override
	public String toString() {
		return columnName;
	}

}
