package com.baizhi.zq.service;

import com.baizhi.zq.dao.AdminMapper;
import com.baizhi.zq.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


public interface AdminService {

    public Admin queryAdminByName(Admin admin);

    public HashMap<String, Object> login(Admin admin, String enCode, HttpSession session);
}
