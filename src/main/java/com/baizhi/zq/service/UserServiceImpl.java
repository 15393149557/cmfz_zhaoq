package com.baizhi.zq.service;

import com.baizhi.zq.dao.UserMapper;
import com.baizhi.zq.entity.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Map<String, Object> queryAllUsers(Integer page,Integer rows) {
        //返回数据的类型
        Map<String, Object> map = new HashMap<>();

        //1.设置总条数records
        UserExample example = new UserExample();
        int records = userMapper.selectCountByExample(example);
        map.put("records",records);
        //2.设置总页数total
        int total=0;
        if(records%rows==0){
            total = records/rows;
        }else{
            total = records/rows+1;
        }
        map.put("total",total);
        //3.设置当前页page
        map.put("page",page);
        //4.设置查询到的数据rows
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows",users);
        return map;
    }

    @Override
    public void edit(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user,userExample);
    }

    @Override
    public List<User> queryUsers() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public HashMap<String, Object> queryByMonth() {
        HashMap<String, Object> map = new HashMap<>();

        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        map.put("month",months );

        //在数据库中查询出的数据
        List<MonthDTO> boys = userMapper.queryMonth("男");
        List<MonthDTO> girls = userMapper.queryMonth("女");

        //将数据库中查询出的数据需要新封装到新的list当中
        ArrayList<Integer> boysCounts = new ArrayList<>();
        ArrayList<Integer> girlsCounts = new ArrayList<>();

        for (String month : months) {
            boysCounts.add(exitsMonth(month,boys));
            girlsCounts.add(exitsMonth(month,girls));
        }

        map.put("boys",boysCounts);
        map.put("girls",girlsCounts);



        return map;
    }

    @Override
    public ArrayList<Object> queryByUserChina() {
        ArrayList<Object> lists = new ArrayList<>();

        List<City> citiesBoy = userMapper.queryCity("男");

        List<City> citiesGirl = userMapper.queryCity("女");

        SexDTO sexDTO1 = new SexDTO("小男孩",citiesBoy);
        SexDTO sexDTO2 = new SexDTO("小女孩",citiesGirl);

        lists.add(sexDTO1);
        lists.add(sexDTO2);
        System.out.println(lists);
        return lists;
    }



    //根据月份查询出对应list当中的数量
    public Integer exitsMonth(String month,List<MonthDTO> list){
        Integer count = 0;

        for (MonthDTO monthDTO : list) {
            if(monthDTO.getMonth().equals(month)){
                count = monthDTO.getCount();
            }
        }

        return count;
    }

}
