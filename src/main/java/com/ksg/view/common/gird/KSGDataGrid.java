package com.ksg.view.common.gird;

import com.ksg.api.model.CommandMap;
import com.ksg.view.common.panel.KSGPanel;

import com.ksg.view.common.table.KSGTable;
import com.ksg.view.common.table.WhiteTableUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.extern.slf4j.Slf4j;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;



import java.awt.event.*;
/**
 * 데이터 그리드
 */
@Slf4j
public class KSGDataGrid extends KSGPanel implements ActionListener{
    
    /* 테이블 정보 */
    private KSGTable table;
    
    /* 풋터 */
    private GirdFooter footer;

    /* 총 수량 */
    private int totalCount;

    /* 현재 페이지 */
    private int currentPage=1;

    /* 페이지 당수량 */
    private int countPerPage;

    /* 페이지 수 */
    private int pageCount;


    private int level[];

    List<CommandMap> resultData;

    public KSGDataGrid(int level[])
    {
        super(); 

        table = new KSGTable();

        table.setTableUI(new WhiteTableUI());

        table.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        this.level = level;

        footer = new GirdFooter(level);

        JScrollPane comp = new JScrollPane( table);

        table.getParent().getParent()
        .setBackground(Color.white);

        table.getTableHeader().setBackground(Color.white);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));

        comp.setVerticalScrollBar(new ScrollBarCustom());

        JPanel pn = new JPanel();

        pn.setBackground(new Color(30,30,30));
        
        comp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, pn);

        comp.getViewport().setBackground(Color.white);

        comp.setBorder(BorderFactory.createLineBorder(Color.white,2));

        this.add(comp); 

        this.add(footer,BorderLayout.SOUTH);

        footer.butFirst.setActionCommand("first");
        footer.butBackword.setActionCommand("backword");
        footer.butForword.setActionCommand("forword");
        footer.butEnd.setActionCommand("end");

        footer.butFirst.addActionListener(this);
        footer.butBackword.addActionListener(this);        
        footer.butForword.addActionListener(this);
        footer.butEnd.addActionListener(this);

        footer.cbxLevel.addItemListener(new ItemChangeListener());

        // table.getParent().setBackground(Color.white);

        setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        setCountPerPage(0);
    }
    public void setCountPerPage(int i)
    {
        countPerPage = level[i];
    }
    
    public void loadData(List<CommandMap> resultData)
    {
        this.resultData = resultData;

        AtomicInteger indexHolder = new AtomicInteger(1);

		resultData.stream().forEach(o -> o.put("rowNum", indexHolder.getAndIncrement()));	
	
        this.totalCount = resultData.size();

        this.pageCount = totalCount/countPerPage+((totalCount%countPerPage)>0?1:0);        

        currentPage=1;

        footer.setTotal(resultData.size());       

        footer.butForword.setEnabled(totalCount!=0);

        footer.butBackword.setEnabled(totalCount!=0);
        
        footer.butFirst.setEnabled(false);

        footer.butEnd.setEnabled(totalCount!=0);        

        updateData();
    }
    /**
     * 
     * @return 테이블
     */
    public KSGTable getTable()
    {
        return table;
    }
    /**
     * 
     * @return 푸터
     */
    public GirdFooter getTableFooter()
    {
        return footer;
    }
    /**
     * 테이블 데이터 업테이트
     */
    private void updateData()
    {
        //rowNum 설정
        List<CommandMap> pagedList = resultData.stream().filter(o-> ((int)o.get("rowNum"))<=(currentPage*countPerPage)&&(int)o.get("rowNum")>((currentPage-1)*countPerPage))
                                                        
        .collect(Collectors.toList());

        table.setResultData(pagedList);

        //푸터 페이지 정보  설정
        footer.setPageInfo(totalCount==0?0:currentPage, totalCount==0?0:pageCount);        
    }
    /** 페이징 액션 정의 */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if("forword".equals(command))
        {
            if(currentPage<pageCount)
            {
                currentPage++;
            }
            
        }
        else if("backword".equals(command))
        {
            if( currentPage>1)currentPage--;
        }
        else if("first".equals(command))
        {
            currentPage =1;
        }
        else if("end".equals(command))
        {
            currentPage = pageCount;
        }

        if(currentPage== pageCount)
        {
            footer.butEnd.setEnabled(false);
            footer.butFirst.setEnabled(true);
        }
        else{
            footer.butEnd.setEnabled(true);
            footer.butFirst.setEnabled(false);
       }
       updateData();
        
    }

    class ItemChangeListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
           if (event.getStateChange() == ItemEvent.SELECTED) {
              int item = (int) event.getItem();

              countPerPage = item;

              pageCount = totalCount/countPerPage+((totalCount%countPerPage)>0?1:0);        

              currentPage=1;

              updateData();

              // do something with object
           }
        }       
    }
    class ScrollBarCustom extends JScrollBar
    {
        public ScrollBarCustom()
        {
            setPreferredSize(new Dimension(8,8));
            setForeground(new Color(48, 144,216));
            setBackground(new Color(30,30,30));
        }
    }

    
}
