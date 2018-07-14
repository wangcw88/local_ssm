package com.location.local.dao;

import com.location.local.model.Location;
import com.location.local.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "username, password, lng, lat, gps, wifi, lbs";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    //插入
    @Insert({"insert into ", TABLE_NAME, "(", "username, password",
            ") values (#{username}, #{password})"})
    int addUser(User user);

    //通过用户查询位置
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where username = #{username}"})
    User selectByUsername(String username);

    //更新定位信息
    @Update({"update", TABLE_NAME, "set lat=#{lat}, lng=#{lng} where username=#{username}"})
    void updateLocation(User user);

    //更新定位状态
    @Update({"update", TABLE_NAME, "set gps=#{gps},wifi=#{wifi},lbs=#{lbs} where username=#{username}"})
    void updateState(User user);

    //删除用户
    @Delete({"delete from", TABLE_NAME," where username = #{username}"})
    void deleteByUsername(String username);

}
