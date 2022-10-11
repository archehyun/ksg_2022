package com.ksg.api.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ksg.api.model.Schedule;

public class ScheduleMapper extends AbstractMapper{

    public ScheduleMapper()
    {
        this.namespace="scheduleMapper";
    }
    public List<Schedule> selectAll()
    {
        return selectList("selectAll", null);
    }
    public Schedule selectById(int id)
    {
        return (Schedule) selectOne("selectById", id);
    }
    public List<Schedule> selectListByKey(String table_id) {
        return  selectList("selectListByKey", table_id);
    }

    public List<Schedule> selectByCondition(Schedule param)
    {
        return selectList("selectByCondition", param);
    }
    public int insertSchedule(Schedule param) throws Exception
    {
        return insert("insertSchedule", param);
    }

    public int insertScheduleBulk(ArrayList<Schedule> scheduleList) throws Exception
    {
        return insert("insertScheduleBulk", scheduleList);
    }

    public int updateSchedule(Schedule param)
    {
        return update("updateSchedule", param);
    }
    public int deleteSchedule(int id)
    {
        return update("deleteSchedule", id);
    }

    public int deleteScheduleByKey(String table_id)
    {
        return update("deleteScheduleByKey", table_id);
    }

    
}
