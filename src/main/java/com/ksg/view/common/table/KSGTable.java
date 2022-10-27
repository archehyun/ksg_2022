package com.ksg.view.common.table;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import com.ksg.api.model.CommandMap;
import com.ksg.view.common.template.KSGStyle;
import com.ksg.view.util.KSGViewProperties;
import com.ksg.view.util.ViewUtil;


/**
 * @author	: ch.park
 * @date 	: 2022-10-05
 */
public class KSGTable extends JTable implements KSGStyle{

	/*헤더 높이 */
    protected int HEADER_HEIGHT;

	/* 폰트 사이즈 */
    protected int FONT_SIZE;

	/* 그리드 컬러 */
    protected static Color GRID_COLOR;

	protected static Color COLUMN_HEAD_COLOR;

    protected static Color CELL_ODD_COLOR;

    private TableModel model;

    protected int ROW_HEIGHT=30;	

    KSGViewProperties propeties = KSGViewProperties.getInstance();

    public KSGTable() {

		model = new TableModel();

		getTableHeader().setPreferredSize(new Dimension(0,35));
		// 정렬 기능 추가
		setRowSorter(new TableRowSorter<TableModel>(model));

		setStyle(null);

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	}
	public void setTableUI(AbstractTableUI ui)
	{
		getTableHeader().setDefaultRenderer(ui.getHeaderRenderer());

		setDefaultRenderer(Object.class,ui.getCellRenderer());
	}

    /**
	 * 컬럼 추가
	 * @param column
	 */
	public void addColumn(KSGTableColumn column) {

		model.addColumn(column);

	}

	/**
	 * 포트 사이즈 지정
	 * @param fontSize
	 */
    private void setFontSize(int fontSize)
	{
		Font currentFont=this.getFont();
		
		this.setFont(new Font(currentFont.getName(),currentFont.getStyle(), fontSize));

	}

    public void setColumnName(KSGTableColumn columnNames[]) {
		this.model.setColumns(columnNames);
	}

    /**
	 * 데이터 초기화
	 */
	public void clearReslult() {
		model.clearResult();
	}


	/**
	 * 스타일 지정
	 * @param param
	 */
    public void setStyle(HashMap<String, Object> param)
	{
		COLUMN_HEAD_COLOR 	= ViewUtil.getColorRGB(propeties.getProperty("table.header.background.color"));
		
		HEADER_HEIGHT		= Integer.parseInt(propeties.getProperty("table.header.height"));

		ROW_HEIGHT			= Integer.parseInt(propeties.getProperty("table.row.height"));

		GRID_COLOR 			= ViewUtil.getColorRGB(propeties.getProperty("table.gird.color"));
		
		CELL_ODD_COLOR 		= ViewUtil.getColorRGB(propeties.getProperty("table.cell.odd.background.color"));

		FONT_SIZE 			= Integer.parseInt(propeties.getProperty("table.font.size"));
		
		boolean showline 	= Boolean.valueOf(propeties.getProperty("table.grid.verticallines"));

		this.setShowVerticalLines(true);
		

		// this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setShowVerticalLines(showline);

		this.setGridColor(GRID_COLOR);		

		this.setRowHeight(ROW_HEIGHT);

		this.setFontSize(FONT_SIZE);
		
		this.setBorder(BorderFactory.createEmptyBorder());
		
		repaint();
	}

    public void initComp() {

		super.setModel(model);

		TableColumnModel colmodel = getColumnModel();


		for (int i = 0; i < colmodel.getColumnCount(); i++) {


			KSGTableColumn col = model.getColumn(i);

			DefaultTableCellRenderer cellRenderer = (DefaultTableCellRenderer) this.getDefaultRenderer(getClass());

			TableColumn namecol = colmodel.getColumn(i);

			// namecol.setCellRenderer(cellRenderer);

			

			cellRenderer.setHorizontalAlignment(col.ALIGNMENT);


			if(col.maxSize!=0)
			{
				
				namecol.setMaxWidth(col.maxSize);
				

			}

			if(col.minSize!=0)
			{
				namecol.setMinWidth(col.minSize);
			}

			if(col.size!=0)
			{
				namecol.setPreferredWidth(col.size);				
			}
		}

		this.setRowHeight(ROW_HEIGHT);	

	}

	public Object getValueAt(int rowIndex, String name)
	{
		return model.getValueAt(rowIndex,name);
	}
	public Object getValueAt(int rowIndex)
	{
		return model.getValueAt(rowIndex);
	}

			/**
	 * 결과 저장
	 * @param resultData
	 */
	
	public void setResultData(List<CommandMap> resultData) {


		
		this.model.setData(resultData);

		model.fireTableDataChanged();
		// 결과 저장후 화면 갱신
		this.updateUI();
	}
    

	@Override
	public void updateStyle() {
		setStyle(null);
		
	}

    
}
