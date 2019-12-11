package com.baizhi.zq.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/editor")
public class EditorController {


    /*
    *
    * //成功时
        {
                "error" : 0,
                "url" : "http://www.example.com/path/to/file.ext"
        }
        //失败时
        {
                "error" : 1,
                "message" : "错误信息"
        }
    *
    * */

    @RequestMapping("/uploadPhoto")
    public HashMap<String, Object> uploadPhoto(MultipartFile img, HttpServletRequest request){

        System.out.println("==文件上传==");

        HashMap<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");

        File file = new File(realPath);

        if(!file.exists()){
            file.mkdirs();
        }


        String filename = img.getOriginalFilename();

        String newName=new Date().getTime()+"-"+filename;

        try {
            img.transferTo(new File(realPath,newName));


            //获取http
            String scheme = request.getScheme();

            //获取ip地址  localhost
            String serverName = request.getServerName();

            //获取端口号   9999
            int serverPort = request.getServerPort();
            System.out.println(serverPort);
            //获取项目名   cmfz
            String contextPath = request.getContextPath();

            //"http://localhost:9999/cmfz/upload/editor/"+newName
            String url=scheme+"://"+serverName+":"+serverPort+"/"+contextPath+"/upload/editor/"+newName;

            map.put("error",0);
            map.put("url",url);

        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("message","上传失败");
        }

        return map;
    }

    @RequestMapping("/photos")
    public HashMap<String, Object> photos(HttpServletRequest request){

        HashMap<String, Object> maps = new HashMap<>();

        ArrayList<Object> lists = new ArrayList<>();

        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");
        System.out.println(realPath);
        //获取文件
        File file = new File(realPath);

        //返回目录中所有的文件名
        String[] names = file.list();

        for (int i = 0; i < names.length; i++) {

            //获取文件名
            String name = names[i];

            /*
            *
              "is_dir": false,
              "has_file": false,
              "filesize": 208736,
              "dir_path": "",
              "is_photo": true,
              "filetype": "jpg",
              "filename": "1241601537255682809.jpg",
              "datetime": "2018-06-06 00:36:39"
            * */

            HashMap<String, Object> map = new HashMap<>();
            map.put("is_dir",false); //是否是文件夹
            map.put("has_file",false); //是否有文件
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length()); //文件大小
            map.put("is_photo",true); //是否是图片
            //获取扩展名
            String extension = FilenameUtils.getExtension(name);
            map.put("filetype",extension); //图片类型
            map.put("filename",name); //图片名

            // 1575512825242-4.jpg

            String[] strs = name.split("-");

            String time=strs[0];
            long times = Long.parseLong(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timess = dateFormat.format(times);

            map.put("datetime",timess); //时间

            //将封装好的图片信息数据放入集合中
            lists.add(map);
        }

        /*
        *
        * "moveup_dir_path": "",
          "current_dir_path": "",
          "current_url": "/ke4/php/../attached/",
          "total_count": 5,
          "file_list": [
        * */
        maps.put("current_url","http://localhost:9999/cmfz/upload/editor/");
        maps.put("total_count",lists.size());
        maps.put("file_list",lists);

        return maps;
    }
}
