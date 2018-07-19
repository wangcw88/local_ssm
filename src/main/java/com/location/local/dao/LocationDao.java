package com.location.local.dao;


import com.location.local.model.Location;
import com.location.local.model.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface LocationDao {
    String TABLE_NAME = "location";
    String INSERT_FIELDS = "username, wifi_lng, wifi_lat, gps_lng, gps_lat, lbs_lng, lbs_lat,created_time";
    String SELECT_WIFI_LOCATION = "wifi_lng, wifi_lat";
    String SELECT_GPS_LOCATION = "gps_lng, gps_lat";
    String SELECT_LBS_LOCATION = "lbs_lng, lbs_lat";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    //插入
    @Insert({"insert into ", TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{username},#{wifi_lng},#{wifi_lat},#{gps_lng},#{gps_lat},#{lbs_lng},#{lbs_lat},#{created_time})"})
    int addLocation(Location location);

    //通过时间及用户查定位数据
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where username = #{username} and created_time = #{created_time}"})
    Location selectByUsernameAndTime(String username, Date date);

    //更新定位信息
    @Update({"update", TABLE_NAME,
            "set wifi_lat=#{wifi_lat}, wifi_lng=#{wifi_lng}, " ,
                    "gps_lat=#{gps_lat}, gps_lng=#{gps_lng}, " ,
                    "lbs_lat=#{lbs_lat}, lbs_lng=#{lbs_lng} " ,
                    "where username=#{username} and created_time=#{created_time}"})
    void updateLocation(Location location);

    //删除用户
    @Delete({"delete from", TABLE_NAME," where username = #{username}"})
    void deleteByUsername(String username);


}
