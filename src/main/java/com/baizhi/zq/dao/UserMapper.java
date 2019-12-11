package com.baizhi.zq.dao;

import com.baizhi.zq.entity.City;
import com.baizhi.zq.entity.MonthDTO;
import com.baizhi.zq.entity.User;
import com.baizhi.zq.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    public List<MonthDTO> queryMonth(String sex);
    public List<City> queryCity(String sex);
}