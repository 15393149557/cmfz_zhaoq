package com.baizhi.zq.service;

import com.baizhi.zq.dao.AdminMapper;
import com.baizhi.zq.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Resource
    private AdminMapper adminMapper;


    @Override
    public Admin queryAdminByName(Admin admin) {
        List<Admin> admins = adminMapper.select(admin);
        if(admins.size()!=0)
        return admins.get(0);
        else {
            return null;
        }
    }

    @Override
    public HashMap<String, Object> login(Admin admin, String enCode, HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        String codeValue = (String)session.getAttribute("codeValue");

        List<Admin> admins = adminMapper.select(admin);

        if(codeValue.equals(enCode)){
            if(admins.size()!=0&&admins.get(0).getPassword().equals(admin.getPassword())){
                map.put("success",200);
                map.put("message","登录成功");
                session.setAttribute("flag",admin.getUsername());
            }else{
                map.put("success",400);
                map.put("message","密码不正确");
            }
        }else{
            map.put("success",400);
            map.put("message","验证码不正确");
        }
        return map;
    }


}
