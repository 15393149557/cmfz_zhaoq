package com.baizhi.zq.controller;

import com.baizhi.zq.entity.Admin;
import com.baizhi.zq.service.AdminService;
import com.baizhi.zq.service.BannerService;
import com.baizhi.zq.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public HashMap<String, Object> login(Admin admin, String enCode, HttpSession session){
        HashMap<String, Object> map = adminService.login(admin, enCode, session);
        return map;
    }

    @RequestMapping("/getImageCode")
    public String getImageCode(HttpSession session, HttpServletResponse response){

        String securityCode = ImageCodeUtil.getSecurityCode();
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        session.setAttribute("codeValue", securityCode);

        //获取向客户端输出内容的输出流

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            //将生成的验证码图片以png(1.png)的格式  输出到页面
            ImageIO.write(image, "png", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.removeAttribute("flag");
        session.invalidate();
        return "/login/login";
    }
}
