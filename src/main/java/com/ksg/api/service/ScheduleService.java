package com.ksg.api.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ksg.api.mapper.PortMapper;
import com.ksg.api.mapper.ScheduleMapper;
import com.ksg.api.mapper.VesselMapper;
import com.ksg.api.model.Port;
import com.ksg.api.model.Schedule;
import com.ksg.api.model.Vessel;
import com.ksg.view.util.DateUtil;
import com.ksg.view.util.KSGProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @packageName : com.ksg.api.service
 * @fileName    : ScheduleService.java
 * @author      : ch.park
 * @date        : 2022.10.05
 * 
 */
@Slf4j
public class ScheduleService {

    private ScheduleMapper mapper;
    private VesselMapper vesselMapper;
    private PortMapper portMapper;
    KSGProperties properties = KSGProperties.getInstance();
    
    public ScheduleService()
    {
        mapper = new ScheduleMapper();
        vesselMapper = new VesselMapper();

        portMapper =new PortMapper();
    }

    public Schedule selectById(int id)
    {
        return null;
    }
    public List<Schedule> selectListByKey(String table_id)
    {
        return mapper.selectListByKey(table_id);
    }

    /**
     * @methodName       : selectByCondtion
     * @author           : ch.park
     * @date             : 2022.10.05
     * @param param
     * @return 스케줄 목록
     */
    public List<Schedule> selectByCondtion(Schedule param) {

        log.info("param:{}",param);
        
        List result = mapper.selectByCondition(param);

        return result;
    }
    /**
     * 전체 스케줄 목록 조회
     * @methodName       : selectAll
     * @author           : ch.park
     * @date             : 2022.10.05
     * @return 스케줄 목록
     */
    public List<Schedule> selectAll() {

        List<Schedule> result = mapper.selectAll();

        return mapper.selectAll();
    }
    /**
     * 스케줄 파일 생성
     * 
     * 지역 -> 출발항 -> 도착항 -> 선박
     * @param param
     * @throws IOException
     */
    public void createSchedule(Schedule param) throws IOException
    {

        FileWriter fw;
        String filePath = properties.getProperty("filepath");
        String fileName = properties.getProperty("filename");
        fw = new FileWriter(filePath+"/"+fileName);

        List<Schedule> result = mapper.selectAll();

        //선박 맵 생성
        var vesselNameList = result.stream()
        .map(Schedule::getVessel_name)
        .distinct()
        .collect(Collectors.toList());

        List vesselList=vesselMapper.selectByVesselNames(vesselNameList);
        
        Map<String, Vessel> vesselMap = (Map<String, Vessel>) vesselList.stream().collect(Collectors.toMap(Vessel::getVessel_name, Function.identity()));


        // 항구 맵 생성

        // 출발항
        var fromPortList = result.stream()
        .map(Schedule::getFrom_port)
        .distinct()
        .collect(Collectors.toList());

        // 도착항
        var toPortList = result.stream()
        .map(Schedule::getTo_port)
        .distinct()
        .collect(Collectors.toList());

        fromPortList.addAll(toPortList);

        var portList =fromPortList.stream().distinct()
                            .collect(Collectors.toList());

        Map<String, Port> portMap=portMapper.selectByPortNames(portList).stream()
                                                                        .collect(Collectors.toMap(Port::getPort_name, Function.identity()));

        




        List<Schedule> inboundScheduleList =result.stream()
                                                   // .filter(schedule -> schedule.getEvent_date().equals(param.getEvent_date()))
                                                    .filter(schedule -> "I".equals(schedule.getInout_type()))
                                                    .collect(Collectors.toList());
        // 아웃바운드 스케줄 목록 생성
        List<Schedule> outboundScheduleList =result.stream()
                                                   // .filter(schedule -> schedule.getEvent_date().equals(param.getEvent_date()))
                                                    .filter(schedule -> "O".equals(schedule.getInout_type()))
                                                    .map(schedule ->Schedule.builder()
                                                                            .from_date(schedule.getFrom_date())
                                                                            .to_date(schedule.getTo_date())
                                                                            .from_port(schedule.getFrom_port())
                                                                            .to_port(schedule.getTo_port())
                                                                            .vessel_name(schedule.getVessel_name())
                                                                            .company_name(schedule.getCompany_name())
                                                                            .vessel_type(vesselMap.get(schedule.getVessel_name()).getVessel_type())
                                                                            .build())
                                                    .collect(Collectors.toList()); 

        List<String> areaList = outboundScheduleList.stream().map(o -> o.getArea_code())

                                                            .collect(Collectors.toList());
        

        //아웃바운드 스케줄 출발항, 도착항 기준으로 그룹바이
        var groupbyToPort= outboundScheduleList.stream().collect(Collectors.groupingBy(
                                                                Schedule::getTo_port,
                                                                Collectors.groupingBy(Schedule::getFrom_port)));


                                                                
        // 지역 목록

        // id 기준 항구 정보
 
        // id 기준 선박 정보
        
        //outbound 스케줄 생성
        // 도착항 기준 순회

        makeOutboundSchedule(portMap, outboundScheduleList);

        makeInboundSchedule(inboundScheduleList);

      
    }

    private void makeOutboundSchedule(Map<String, Port> portMap,List<Schedule> outboundScheduleList) throws IOException
    {
        FileWriter fw;
        String filePath = properties.getProperty("filepath");
        String fileName = properties.getProperty("filename");
        fw = new FileWriter(filePath+"/"+fileName);

          //아웃바운드 스케줄 출발항, 도착항 기준으로 그룹바이
          var groupbyToPort= outboundScheduleList.stream().collect(Collectors.groupingBy(
            Schedule::getTo_port,
            Collectors.groupingBy(Schedule::getFrom_port)));
            
            // 지역 목록

            // id 기준 항구 정보

            // id 기준 선박 정보

            //outbound 스케줄 생성
            // 도착항 기준 순회
            for(String toPort:groupbyToPort.keySet())
            {   
                Port port = portMap.get(toPort);           

                var groupbyport =  groupbyToPort.get(toPort);

            // 출발항 기준 순회
                for(String fromPort:groupbyport.keySet())
                {

                    fw.write("\t- "+fromPort+" -\n");

                    List<Schedule> list = groupbyport.get(fromPort);

                    //선박명 기준 그룹 생성
                    Map<String, List<Schedule>> vesselGroup=list.stream().collect(Collectors.groupingBy(
                    Schedule::getVessel_name, Collectors.toList()));

                    // 공동 배선
                    for(String vesselName:vesselGroup.keySet())
                    {
                    List<Schedule> scheduleList = commonshipping(vesselGroup.get(vesselName));

                    for(Schedule item:scheduleList)
                    {
                    fw.write("\t\t\t"+new FormattedSchedule(item)+"\n");
                    }
                    }
                }
                }
            // 파일 출력
            fw.close();
    }

    /**
     * 
     * @param inboundScheduleList 인바운드 스케줄 목록
     */
    private void makeInboundSchedule(List<Schedule> inboundScheduleList)
    {
        var groupbyToPort= inboundScheduleList.stream().collect(Collectors.groupingBy(
            Schedule::getFrom_port,
            Collectors.groupingBy(Schedule::getVessel_name)));

        // 외국 출항항 순회
        for(String fromPort:groupbyToPort.keySet())
        {   
            System.out.println("fromPort:"+fromPort);
                var groupbyport =  groupbyToPort.get(fromPort);
                StringBuffer buffer = new StringBuffer();
                for(String vesselName:groupbyport.keySet())
                {
                    List<Schedule> scheduleList=groupbyport.get(vesselName);
                    for(Schedule item:scheduleList)
                    {
                        buffer.append(item.getTo_port()+"["+item.getTo_date()+"]");
                    }

                    System.out.println(vesselName+" "+buffer.toString());
                    
                }

        }


    }

    private List<Schedule> commonshipping(List<Schedule> vesselList)
    {
        return vesselList;
    }
    class FormattedSchedule
    {
        Schedule schedule;

        String dateFormat = "MM/dd";

        public FormattedSchedule(Schedule schedule)
        {
            this.schedule = schedule;
        }
        public String toString()
        {
            return String.format("%s  %s  [%s]   %S  %s", 
                                    DateUtil.convertType(schedule.getTo_date(),dateFormat), 
                                    schedule.getVessel_name(),
                                    schedule.getVessel_type(),
                                    schedule.getCompany_name(),
                                    DateUtil.convertType(schedule.getTo_date(),dateFormat));
        }
    }
    public void insertScheduleBulk(String table_id,ArrayList<Schedule> scheduleList) throws Exception {

        this.mapper.deleteScheduleByKey(table_id);
        this.mapper.insertScheduleBulk(scheduleList);
        
    }
    
}
