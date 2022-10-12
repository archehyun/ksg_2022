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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.json.simple.parser.ParseException;

import java.awt.event.ComponentEvent;
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
/**
 * 광고정보관리 화면
 * 광고정보 입력
 */
public class DataGrid extends KSGPanel
{
    private int id;

    private String table_id;

    private JTabbedPane pnTab = new JTabbedPane();

    private JTextArea txfData = new JTextArea();

    private JTable table = new JTable();

    public JButton butClose = new KSGButton("닫기");

    public JButton butSave = new KSGButton("저장");

    public JButton butShipper = new KSGButton("광고관리");

    public JButton butPort = new KSGButton("항구관리");

    private ShipperTableData tabledata = new ShipperTableData();

    private KSGPanel pnDataControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));    

    public CommandMap param;

    private JLabel lblInbound = new JLabel();

    private JLabel lblOutbound = new JLabel();

    public SchedulePn schedulePn = new SchedulePn("schedule");

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
    public String getText()
    {
        return txfData.getText();
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

        TableBuilder builder = new TableBuilder(tabledata);

        String[] header = builder.getHeader();
        

        //TODO 삭제
        String dataArray[][]={{"1"},{"2"}};

        dataArray = builder.getDataArray();

        DefaultTableModel mode = new DefaultTableModel(dataArray, header);

        table.setModel(mode);

        TableColumnModel columnModel=  table.getColumnModel();

        for(int i=0;i<columnModel.getColumnCount();i++)
        {
            columnModel.getColumn(i).setCellEditor(new MyCellEditor());
            columnModel.getColumn(i).setCellRenderer(new MyCellEditor());
        }
        
        txfData.setText(data);
    }
}



class MyCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 1L;
    private JTextField renderer;
    private JTextField editor;
    

    public MyCellEditor() {
       
        renderer = new JTextField();
        renderer.setBorder(null);
        editor = new JTextField();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       // System.out.println("r_:"+value);
        renderer.setText((String) value);
        return renderer;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

      //  System.out.println("e_:"+value);
        editor.setText((String) value); 
        return editor;
    }

    @Override
    public boolean stopCellEditing() {
        // try {
        //     editor.comit;
        // } catch (ParseException e) {
        //     return false;
        // }
        return super.stopCellEditing();
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getText();
    }
}


