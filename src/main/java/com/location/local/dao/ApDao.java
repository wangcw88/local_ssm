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
    String INSERT_FIELDS = "username, Alng, Alat, Blng, Blat, Clng, Clat, Lbslng, Lbslat, created_time";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    //插入
    @Insert({"insert into ", TABLE_NAME,"(",INSERT_FIELDS,
            ") values (#{username},#{Alng},#{Alat},#{Blng},#{Blat},#{Clng},#{Clat},#{Lbslng},#{Lbslat},#{created_time})"})
    int addApLocation(Ap ap);

    //通过时间及用户查定位数据
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where username = #{username}"})
    Ap selectByUsername(String username);

    //更新定位信息
    @Update({"update", TABLE_NAME, "set Alat=#{Alat}, Alng=#{Alng},Blat=#{Blat}, Blng=#{Blng},Clat=#{Clat}, Clng=#{Clng}, Lbslng=#{Lbslng}, Lbslat=#{Lbslat}  where username=#{username}"})
    void updateLocation(Ap ap);
}
