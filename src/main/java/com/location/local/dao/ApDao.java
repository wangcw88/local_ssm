package com.location.local.dao;

import com.location.local.model.Ap;
import com.location.local.model.Location;
import com.location.local.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface ApDao {
    String TABLE_NAME = "ap_location";
    String INSERT_FIELDS = "username, Alng, Alat, Blng, Blat, Clng, Clat, ALbslng, ALbslat, BLbslng, BLbslat, CLbslng, CLbslat, created_time";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    //插入
    @Insert({"insert into ", TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{username},#{Alng},#{Alat},#{Blng},#{Blat},#{Clng},#{Clat},#{ALbslng},#{ALbslat},#{BLbslng},#{BLbslat},#{CLbslng},#{CLbslat},#{created_time})"})
    int addApLocation(Ap ap);

    //通过时间及用户查定位数据
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where username = #{username}"})
    Ap selectByUsername(String username);

    //更新定位信息
    @Update({"update", TABLE_NAME,
            "set Alat=#{Alat}, Alng=#{Alng}, Blat=#{Blat}, Blng=#{Blng}, Clat=#{Clat}, Clng=#{Clng}, ALbslng=#{ALbslng}, ALbslat=#{ALbslat}, BLbslng=#{BLbslng}, BLbslat=#{BLbslat}, CLbslng=#{CLbslng}, CLbslat=#{CLbslat}, created_time=#{created_time} where username=#{username}"})
    void updateLocation(Ap ap);
}
