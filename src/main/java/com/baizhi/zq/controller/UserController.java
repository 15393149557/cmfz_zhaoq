package com.baizhi.zq.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.zq.entity.City;
import com.baizhi.zq.entity.SexDTO;
import com.baizhi.zq.entity.User;
import com.baizhi.zq.service.UserService;
import com.baizhi.zq.util.UUIDUtil;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/queryAllUsers")
    @ResponseBody
    public Map<String,Object> queryAllUsers(Integer page,Integer rows){
        Map<String, Object> map = userService.queryAllUsers(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public void edit(User user,String oper){
        if("edit".equals(oper)) {
            userService.edit(user);
        }else if ("add".equals(oper)){
            //此处的user需要将数据补充完整   此处模拟前台发送数据
            User user1 = new User(UUIDUtil.getUUID(), "小黑", "圆圆", "123456", "1234", "1", "1.png", "男", "新疆", "我们新疆好地方", new Date(), "1");
            userService.add(user1);
            HashMap<String, Object> map = userService.queryByMonth();


            String content = JSON.toJSONString(map);

            //设置GoEasy参数:指定服务器地址  restHost在应用列表中  ,自己的AppKey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-e2325fcf05e3473098ac0693d6a7f785");

            //发布消息  参数:指定通道名称,要发送的内容
            goEasy.publish("cmfz", content);
            System.out.println("添加成功");
        }else{

        }
    }

    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request){
        List<User> users = userService.queryUsers();

        String realPath = request.getServletContext().getRealPath("/upload/photo");
        for (User user : users) {
            user.setPicImg(realPath+'/'+user.getPicImg());
        }

        System.out.println(users);

        //导出配置的参数   参数:标题名,工作薄名字
        ExportParams exportParams = new ExportParams("用户信息", "用户信息表1");

        //参数：导出的配置,导出数据对应的实体类,导出的数据
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,User.class, users);

        try {
            //将信息导入到xls文件当中
            workbook.write(new FileOutputStream(new File("D:/testPoi/user.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/queryByMonth")
    @ResponseBody
    public HashMap<String,Object> queryByMonth(){

        HashMap<String, Object> map = userService.queryByMonth();

        return map;

    }

    @RequestMapping("/queryByUserChina")
    @ResponseBody
    public ArrayList<Object> queryByUserChina(){

        ArrayList<Object> list = userService.queryByUserChina();

        return list;
    }
}
