package com.ksg.view.util;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
// import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
// import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;




import com.ksg.api.model.CommandMap;
/**
 * @author ch.park
 * @date 2022-10-12
 */
public class ExcelUtil {

    Workbook workbook;

    public void createExcelToFile(List<CommandMap> datas,String filepath, String colNames[]) throws IOException
    {
        workbook = new SXSSFWorkbook(); // 성능 개선 버전
        Sheet sheet = workbook.createSheet("데이터");
        createExcel(sheet, datas, colNames);
        FileOutputStream fos = new FileOutputStream(new File(filepath));
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
    int rowNum=0;
    //엑셀 생성
    private void createExcel(Sheet sheet, List<CommandMap> datas, String colums[]) {
    
        //데이터를 한개씩 조회해서 한개의 행으로 만든다.

        
        // Cell cell = null;
        CellStyle cs = workbook.createCellStyle();
        Row headRow = sheet.createRow(rowNum++);
        int headcellNum = 0;
        for(String name:colums)
        {
            Cell cell =headRow.createCell(headcellNum++);
            cell.setCellValue(name);
        }
        
        // setHeaderCS(cs, font, cell);
        for (CommandMap data : datas) {
            //row 생성
            Row rows = sheet.createRow(rowNum++);
            int cellNum = 0;
            
            //map에 있는 데이터를 한개씩 조회해서 열을 생성한다.
            for(String name:colums)
            {
                Cell cell = rows.createCell(cellNum++);
               	
                //cell에 데이터 삽입
                if(data.get(name)!=null)
                cell.setCellValue(String.valueOf(data.get(name)));
            }

        
        }
    }

    // private void setPrintStyle()
    // {
    //     //Font
	// 	Font fontHeader = workbook.createFont();
	// 	fontHeader.setFontName("맑은 고딕");	//글씨체
	// 	fontHeader.setFontHeight((short)(9 * 20));	//사이즈
	// 	fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);	//볼드(굵게)
	// 	Font font9 = workbook.createFont();
	// 	font9.setFontName("맑은 고딕");	//글씨체
	// 	font9.setFontHeight((short)(9 * 20));	//사이즈
	// 	// 엑셀 헤더 셋팅
		

    // }

    // private void setHeaderCS(CellStyle cs, Font font, Cell cell) {
    //     cs.setAlignment(CellStyle.ALIGN_CENTER);
    //     cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //     cs.setBorderTop(CellStyle.BORDER_THIN);
    //     cs.setBorderBottom(CellStyle.BORDER_THIN);
    //     cs.setBorderLeft(CellStyle.BORDER_THIN);
    //     cs.setBorderRight(CellStyle.BORDER_THIN);
    //     cs.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
    //     cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
    //     setHeaderFont(font, cell);
    //     cs.setFont(font);
    //     cell.setCellStyle(cs);
    //   }
       
    //   private void setHeaderFont(Font font, Cell cell) {
    //     font.setBoldweight((short) 700);
    //     font.setColor(HSSFColor.WHITE.index);
    //   }
    
}
