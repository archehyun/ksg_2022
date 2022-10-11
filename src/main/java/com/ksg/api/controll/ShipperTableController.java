package com.ksg.api.controll;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Schedule;
import com.ksg.api.model.ShipperTable;
import com.ksg.api.model.ShipperTableData;
import com.ksg.api.service.ScheduleService;
import com.ksg.api.service.ShipperTableService;
import com.ksg.view.common.template.View;
import com.ksg.view.util.DateUtil;
import com.ksg.view.workbench.shippertable.TableBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShipperTableController extends AbstractController{

    ShipperTableService service;

    ScheduleService scheduleService;

    public ShipperTableController() {
        super();
        service = new ShipperTableService();
        scheduleService = new ScheduleService();
    }

    @ControlMethod(serviceId = "selectShipperTable")
    public CommandMap selectByCondtion(CommandMap param) throws Exception
    {
        log.info("param:{}", param);

        // String table_id =(String) param.get("table_id");
        String table_type = (String) param.get("table_type");

        ShipperTable shipperTable = ShipperTable.builder()
                                                // .table_id(table_id)
                                                .table_type(table_type)
                                                .build();

        List<ShipperTable> result = service.selectByCondtion(shipperTable);
 

        
        result.stream().forEach(o -> {
          
                o.setEvent_date( DateUtil.convertType(o.getEvent_date()));
           
        });

        CommandMap model = new CommandMap();

        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());

        model.put("success", true);
        model.put("data", resultArry);
        
        return model;

    }


    @ControlMethod(serviceId = "insertShipperTable")
    public CommandMap insertShipperTable(CommandMap param) throws Exception{

        String table_id = (String) param.get("table_id");
        String title = (String) param.get("title");
        String company = (String) param.get("company");
        String agent = (String) param.get("agent");
        String data = (String) param.get("data");
        String inbound_from_index = (String) param.get("inbound_from_index");
        String inbound_to_index = (String) param.get("inbound_to_index");
        String outbound_from_index = (String) param.get("outbound_from_index");
        String outbound_to_index = (String) param.get("outbound_to_index");
        String table_type = (String) param.get("table_type");
        int page = (int) param.get("page");
        

        ShipperTable shippterTable = ShipperTable.builder()
                                .table_id(table_id)
                                .title(title)
                                .agent(agent)
                                .agent(data)
                                .company(company)
                                .table_type(table_type)
                                .inbound_from_index(inbound_from_index)
                                .inbound_to_index(inbound_to_index)
                                .outbound_from_index(outbound_from_index)
                                .outbound_to_index(outbound_to_index)
                                .page(page)
                                .build();

        int result = service.insert(shippterTable);                                
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }
    @ControlMethod(serviceId = "updateShipperTable")
    public CommandMap updateShipperTable(CommandMap param) throws Exception{
        int id = (int) param.get("id");
        String table_id = (String) param.get("table_id");
        String title = (String) param.get("title");
        String agent = (String) param.get("agent");
        String company = (String) param.get("company");
        String table_type = (String) param.get("table_type");
        String inbound_from_index= (String) param.get("inbound_from_index");
        String inbound_to_index= (String) param.get("inbound_to_index");
        String outbound_from_index= (String) param.get("outbound_from_index");
        String outbound_to_index= (String) param.get("outbound_to_index");
        int page = (int) param.get("page");
       

        ShipperTable shipperTable = ShipperTable.builder()
                                .id(id)
                                .table_id(table_id)
                                .title(title)
                                .agent(agent)
                                .company(company)
                                .table_type(table_type)
                                .inbound_from_index(inbound_from_index)
                                .inbound_to_index(inbound_to_index)
                                .outbound_from_index(outbound_from_index)
                                .outbound_to_index(outbound_to_index)
                                .page(page)
                                .build();        
       

        var result = service.update(shipperTable);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
        
    }


    private void makeSchedule(CommandMap param) throws Exception
    {
        log.info("make Schhedule start");

        String table_id         = (String) param.get("table_id");

        String inbound_from_index    = (String) param.get("inbound_from_index");
        String inbound_to_index    = (String) param.get("inbound_to_index");
        String outbound_from_index    = (String) param.get("outbound_from_index");
        String outbound_to_index    = (String) param.get("outbound_to_index");
        
        ShipperTableData data   = new ShipperTableData();
       
        data.parse((String) param.get("data"));
       
        ArrayList<Integer> inFromIndex  = exportArray(inbound_from_index, "#");
        ArrayList<Integer> inToIndex    = exportArray(inbound_to_index, "#");
        ArrayList<Integer> outFromIndex = exportArray(outbound_from_index, "#");
        ArrayList<Integer> outToIndex   = exportArray(outbound_to_index, "#");
        

        TableBuilder builder = new TableBuilder(data);

        String dateArray[][]= builder.getDateArray();

        String vesselArray[][] = builder.getVessel();

        String portArray[] = builder.getPorts();

        ArrayList<Schedule> scheduleList = new ArrayList<>();

        for(int i =0;i<dateArray.length;i++)
        {            
            String vessel_name =vesselArray[i][0]; 
            String vessel_voyage =vesselArray[i][1];

            
            for(Integer fromIndex:inFromIndex)
            {
                for(Integer toIndex:inToIndex)
                {
                    try{
                        String fromDate=dateArray[i][fromIndex-1];
                        String toDate=dateArray[i][toIndex-1];
                        String fromPort = portArray[fromIndex];
                        String toPort = portArray[toIndex];
                        
                        var schedule= Schedule.builder().table_id(table_id)
                                            .vessel_name(vessel_name)
                                            .vessel_voyage(vessel_voyage)
                                            .inout_type("I")
                                            .from_port(fromPort)
                                            .from_date(fromDate)
                                            .to_port(toPort)
                                            .to_date(toDate)
                                            .build();
                        scheduleList.add(schedule);
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.err.println(e.getMessage());
                    }
                    
                }
            }
            
            for(Integer fromIndex:outFromIndex)
            {
                for(Integer toIndex:outToIndex)
                {
                    try{
                        String fromDate = dateArray[i][fromIndex-1];
                        String toDate   = dateArray[i][toIndex-1];
                        String fromPort = portArray[fromIndex];
                        String toPort   = portArray[toIndex];
                        
                        var schedule= Schedule.builder().table_id(table_id)
                                            .inout_type("O")
                                            .vessel_name(vessel_name)
                                            .from_port(fromPort)
                                            .from_date(fromDate)
                                            .to_port(toPort)
                                            .to_date(toDate)
                                            .build();
                        scheduleList.add(schedule);
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }

        scheduleService.insertScheduleBulk(table_id,scheduleList);

        log.info("make Schhedule end");
    }

    private ArrayList exportArray(String data, String delim)
    {
        StringTokenizer inFromtoken = new StringTokenizer(data, delim);
        ArrayList result = new ArrayList<>();
        while(inFromtoken.hasMoreTokens())
        {
            result.add(Integer.parseInt(inFromtoken.nextToken()));
        }
        return result;
    }
    @ControlMethod(serviceId = "updateShipperTableData")
    public CommandMap updateShipperTableData(CommandMap param) throws Exception{
        int id = (int) param.get("id");
        String data = (String) param.get("data");
       
        makeSchedule(param);

        ShipperTable shipperTable = ShipperTable.builder()
                                .id(id)
                                .build();
       

        shipperTable.setData(data);

        ShipperTableData shipperData=shipperTable.getShipperdata();


        var result = service.updateData(shipperTable);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
        
    }

    @ControlMethod(serviceId = "deleteShipperTable")
    public CommandMap deleteCompany(CommandMap param) throws Exception {
        var id = (int) param.get("id");
        var result=service.delete(id);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);

        return returnMap;
    }
    
}

