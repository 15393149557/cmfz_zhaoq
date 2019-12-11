package com.baizhi.zq.service;

import com.baizhi.zq.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String,Object> queryAllUsers(Integer page,Integer rows);
    public void edit(User user);
    public List<User> queryUsers();
    public void add(User user);
    public HashMap<String,Object> queryByMonth();
    public ArrayList<Object> queryByUserChina();



}
