package com.ksg.view.workbench.shippertable;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;

import com.ksg.api.controll.ScheduleController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.ShipperTableData;
import com.ksg.view.common.button.KSGButton;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.common.template.MgtPn;
import com.ksg.view.workbench.shippertable.dialog.ShipperTablePortUpdatePopup;
import com.ksg.view.workbench.shippertable.dialog.ShipperTableUpdatePopup;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
/**
 * 광고정보관리 화면
 * 광고정보 입력
 * 
 */
public class DataGrid extends KSGPanel
{
    private int id;

    TableBuilder builder;

    private String table_id;

    private JTabbedPane pnTab = new JTabbedPane();

    private JTextArea txfData = new JTextArea();

    private JTable table = new JTable();

    public JButton butClose = new KSGButton("닫기");

    public JButton butInsert = new KSGButton("추가");

    public JButton butSave = new KSGButton("저장");

    public JButton butShipper = new KSGButton("광고관리");

    public JButton butPort = new KSGButton("항구관리");

    private ShipperTableData tabledata = new ShipperTableData();

    private KSGPanel pnDataControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));    

    public CommandMap param;

    private JLabel lblInbound = new JLabel();

    private JLabel lblOutbound = new JLabel();

    public SchedulePn schedulePn = new SchedulePn("schedule");

    private MyTableModel model = new MyTableModel();

    public DataGrid()
    {  
        initComponents(); 
    }

    private void initComponents()
    {
        butShipper.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                ShipperTableUpdatePopup  updatePopup = new ShipperTableUpdatePopup (param);

                updatePopup.createAndUpdateUI();
                
            }});
        butPort.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
    
                    ShipperTablePortUpdatePopup  updatePopup = new ShipperTablePortUpdatePopup (id,tabledata);
    
                    updatePopup.createAndUpdateUI();

                    if(updatePopup.STATUS == CommonPopup.OK)
                    {
                        ShipperTableData data =(ShipperTableData) updatePopup.result;
                        try {
                            loadData(data.toString());
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    
                }});    
        butInsert.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               model.addRow();
                
            }});
        pnDataControl.add(butInsert);

        pnDataControl.add(butShipper);

        pnDataControl.add(butPort);

        pnDataControl.add(butSave);

        pnDataControl.add(butClose);

        pnTab.add("table", buildGridTable());

        pnTab.add("text", new JScrollPane(txfData));

        pnTab.add("schedule", buildSchedule());

        add(pnTab);

        add(pnDataControl,BorderLayout.SOUTH);
    }

    private KSGPanel buildSchedule() {

        return schedulePn;
    }

    public KSGPanel buildGridTable()
    {
        table.setGridColor(Color.lightGray);

        table.setRowHeight(35);

        KSGPanel pnInfo = new KSGPanel(new FlowLayout(FlowLayout.LEFT));

        pnInfo.add(new JLabel("Inbound : "));

        pnInfo.add(lblInbound);

        pnInfo.add(new JLabel("Outbound : "));

        pnInfo.add(lblOutbound);        

        KSGPanel pnMain = new KSGPanel();

        pnMain.add(pnInfo,BorderLayout.NORTH);

        pnMain.add(new JScrollPane(table));

        return pnMain;

    }
    public String getData()
    {
        return model.getData();
    }
    public void setParam(CommandMap param)
    {
        this.param      = param;

        this.id         = (int) param.get("id");

        this.table_id   =(String) param.get("table_id");

        this.lblInbound.setText((String)param.get("inbound_index"));

        this.lblOutbound.setText((String)param.get("xoutbound_index"));

        this.schedulePn.setTable_id(table_id);
    }

    public CommandMap getParam()
    {
        return this.param;
    }
    /**
     * 
     * @param data 광고데이터
     * @throws ParseException
     */
    public void loadData(ShipperTableData data) throws ParseException
    {
        loadData(data.toString());
    }
    /**
     * 광고정보 로드
     * @param data 광고데이터
     * @throws ParseException
     */
    public void loadData(String data) throws ParseException
    {   
        tabledata.parse(data);

        model.setData(tabledata);
        
        table.setModel(model);
        // table.setValueAt(model, ERROR, ABORT);

        TableColumnModel columnModel=  table.getColumnModel();

        for(int i=2;i<columnModel.getColumnCount();i++)
        {
            columnModel.getColumn(i).setCellEditor(new DateCellEditor());
            columnModel.getColumn(i).setCellRenderer(new DateCellEditor());
        }
        
        txfData.setText(data);
    }
    //TODO 선박명 자동완성
//TODO 날짜 포맷 변경
//TODO 복사 붙여넣기
class DateCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 1L;

    private JTextField renderer;

    private JTextField editor;

    private JTable table;

    private int row, col;
    
    SimpleDateFormat formatYYYYMMDD = new SimpleDateFormat("YYYYmmdd");

    SimpleDateFormat formatMMDD = new SimpleDateFormat("mm/dd");

    Object cellValue;
    public DateCellEditor() {

        renderer = new JTextField();
        renderer.setBorder(null);
        editor = new JTextField();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


        try{
        
            if(value instanceof DateType)
            {
                DateType item = (DateType) value;

                // item.formatedData = formatMMDD.format(formatYYYYMMDD.parse(item.data));
                
                renderer.setText(item.data);
            }
            else{
                renderer.setText(String.valueOf(value));
            }
        //else{}
    }
       catch(Exception e){
        //System.err.println("error:"+value);
       renderer.setText((String)value);
    }
        
        return renderer;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        this.row = row;

        this.col = column;

        this.table = table;
        try{
            if(value instanceof DateType)
            {
                DateType item = (DateType) value;

                this.cellValue=item;

                // item.formatedData = formatMMDD.format(formatYYYYMMDD.parse(item.data));

                editor.setText(item.data);
            }
            else{
                editor.setText(String.valueOf(value+" e"));
            }
        }

        catch(Exception e)
        {

            editor.setText((String)value);
    }

        return editor;
    }

    @Override
    public boolean stopCellEditing() {

        /*
            연도 계산 방법(안)
         * 1. 기존 날짜에서 연도를 가져옴
         * 2. 시스템 날짜 기준으로 연도를 계산
         */
        
        
        String editValue = editor.getText();

        Calendar cal = Calendar.getInstance();

        // 기존 데이터에서 year 정보 가져오기
        DateType  type = (DateType) this.cellValue;

      // cal.setTime(formatYYYYMMDD.parse(type.data));

        int year=cal.get(Calendar.YEAR);

        // 신규 년도 및 날짜 지정
       // cal.setTime(formatMMDD.parse(editValue));

        //cal.set(Calendar.YEAR, year);
       
       //String rowvalue=formatYYYYMMDD.format(cal.getTime());
       
       //System.out.println("date cell edit("+row+", "+col+","+editValue+", "+rowvalue);

       type.data = editValue;
       type.formatedData = editValue;

       this.table.getModel().setValueAt(type, row, col-2);

        return super.stopCellEditing();
    }

    @Override
    public Object getCellEditorValue() {

        return editor.getText();
    }
}

   
    class DateType
    {
        public static final int DATE=1;
        public static final int STRING=2;
        public int type;
        public String data;
        public String formatedData;
        public String toString()
        {
            return formatedData;
        }
    }

    /**
     * DataGrid 테이블 모델
     */
    class MyTableModel extends AbstractTableModel 
    {
        DefaultTableModel model = new DefaultTableModel();

        private JSONArray vesselList;

        private JSONArray portList;
    
        private JSONArray dateList;
    
        private String dateArray[][];
    
        private String dataArrays[][];
        
        private Object[] column;

        private Object[][] data;

        private ShipperTableData sdata;

        public String getData()
        {
            return sdata.toString();
        }

        public void addRow() {
        }

        public void setData( ShipperTableData data)
        {
            this.sdata =data;

            vesselList = data.getVesselList();

            portList = data.getPortList();
    
            dateList = data.getDateList();

            String[] header = getHeader();
        
            String dataArray[][]=getDataArray();  

            this.setData(dataArray, header);

        }

        public void setData(Object data[][], Object column[])
        {
            this.column =  column;

            this.data = new DateType[data.length][];

            for(int i=0;i<data.length;i++)
            {
                this.data[i] = new DateType[data[i].length];
                for(int j=0;j<data[i].length;j++)
                {   
                    this.data[i][j] = new DateType();

                    ( (DateType)this.data[i][j]).data = String.valueOf(data[i][j]);
                    ( (DateType)this.data[i][j]).formatedData = String.valueOf(data[i][j]);
                }
            }
        }

        public String getColumnName(int col) {
            return (String) column[col];
        }

        @Override
        public int getRowCount() {
            
            return data.length;
        }

        @Override
        public int getColumnCount() {
            
            return column.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            
            return data[rowIndex][columnIndex];
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            if(aValue instanceof DateType)
            {
                DateType type = (DateType) aValue;
                this.sdata.setDate(rowIndex, columnIndex, type.data);
            }
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
          }

          public String[] getHeader() {

            ArrayList<String> head = new ArrayList<String>();
            
            JSONArray portList = sdata.getPortList();
    
            head.add("Vessel");

            head.add("Voyage");

            for(int i=0;i<portList.size();i++)
            {
                JSONObject e = (JSONObject) portList.get(i);

                head.add((String)e.get("port-name"));
            }
    
            String[] headArray = head.toArray(new String[head.size()]);
            
            
            return headArray;
        }
    
        public String[][] getVessel()
        {  
            String vessels[][] = new String[vesselList.size()][2];
    
            for(int i=0;i<vesselList.size();i++)
            {
                JSONObject e = (JSONObject) vesselList.get(i);
                
                vessels[i][0] = (String) e.get("vessel-name");
                vessels[i][1] = (String) e.get("voyage");
            }
            return  vessels;
        }
    
        public String[] getPorts() {
    
            String ports[] = new String[portList.size()];
    
            for(int i=0;i<portList.size();i++)
            {
                JSONObject e = (JSONObject) portList.get(i);
                
                ports[i] = (String) e.get("port-name");
            }
            return  ports;
        }
    
        public String[][] getDateArray() {
    
            int vesselCount = vesselList.size();

            int dateArrayCount=dateList.size();

            int rowCount = vesselCount;

            dateArray = new String[vesselCount][];
    
            for(int i=0;i<rowCount;i++)
            {
                ArrayList<String> rowArray = new ArrayList<String>();
    
                JSONObject e = (JSONObject) vesselList.get(i);

                if(i<dateArrayCount)
                {
                    JSONArray ob = (JSONArray) dateList.get(i);
                
                    for(int j=0;j<ob.size();j++)
                    {
                        rowArray.add(ob.get(j).toString());
    
                    }
                }

                dateArray[i] = rowArray.toArray(new String[rowArray.size()]);
            }
    
    
            return dateArray;
        }
    
        public String[][] getDataArray() {
    
            int vesselCount = vesselList.size();
    
            int dateArrayCount=dateList.size();
    
            int rowCount = vesselCount;

            dataArrays = new String[vesselCount][];
    
            for(int i=0;i<rowCount;i++)
            {
                ArrayList<String> rowArray = new ArrayList<String>();
    
                JSONObject e = (JSONObject) vesselList.get(i);
                rowArray.add((String)e.get("vessel-name"));
                rowArray.add((String)e.get("voyage"));
    
                if(i<dateArrayCount)
                {
                    JSONArray ob = (JSONArray) dateList.get(i);
                
                    for(int j=0;j<ob.size();j++)
                    {
                        rowArray.add(ob.get(j).toString());
                        // sub[j] = ob.get(j).toString();
                    }
                }

                dataArrays[i] = rowArray.toArray(new String[rowArray.size()]);;
            }
    
    
            return dataArrays;
        }
        
    }


}





