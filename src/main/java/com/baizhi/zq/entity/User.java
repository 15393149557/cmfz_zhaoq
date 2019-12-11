package com.baizhi.zq.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name = "ID")
    private String id;

    @Excel(name = "名字")
    private String name;

    @Excel(name = "法名")
    private String legalName;

    @Excel(name = "密码")
    private String password;

    @Excel(name = "盐")
    private String salt;

    @Excel(name = "状态")
    private String status;

    @Excel(name = "头像",type = 2 ,width = 40 , height = 20)
    private String picImg;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "城市")
    private String city;

    @Excel(name = "签名")
    private String sign;


    @Excel(name = "注册时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date regTime;

    @Excel(name = "上师ID")
    private String guruId;


}