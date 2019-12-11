package com.baizhi.zq.test;

import com.baizhi.zq.CmfzZhaoqApplication;
import com.baizhi.zq.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest(classes = CmfzZhaoqApplication.class)
@RunWith(SpringRunner.class)
public class TestPoi {

    @Test
    public void testPoiExport(){
        //创建一个xls文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建表
        HSSFSheet sheet = workbook.createSheet("学生表");

        //创建一行，没有列的概念 下标是从0开始
        HSSFRow row = sheet.createRow(0);

        //创建一个单元格 下标是从0开始
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("这是一个单元格");
        try {
            workbook.write(new FileOutputStream(new File("D:/testPoi/testPoi.xls")));

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPoiExport1(){
        //创建一个xls文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建表
        HSSFSheet sheet = workbook.createSheet("学生表");

        //设置单元格宽度   参数：列索引，列宽
        sheet.setColumnWidth(3, 15*256);

        //创建单元格需要的样式1
        HSSFFont font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontHeight((short) 400);  //字体高度(单位与Excel 1:20)
        font.setFontName("楷体");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);     //将字体样式引入
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //文字居中


        //创建样式对象2
        CellStyle cellStyle2 = workbook.createCellStyle();
        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        cellStyle2.setDataFormat(dataFormat.getFormat("yyy年MM月dd日"));


        //创建标题行
        HSSFRow row = sheet.createRow(0);

        //设置行高   参数：short类型的值
        row.setHeight((short) 500);

        //标题栏的列名
        String[] title={"ID","名字","年龄","生日"};

        //处理单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);    //单元格下标
            cell.setCellValue(title[i]);   //单元格内容

            cell.setCellStyle(cellStyle);  //给每一个单元格设置定义好的cellstyle样式
        }

        //创建数据
        Student s1 = new Student("1", "小柴", 23, new Date());
        Student s2 = new Student("2", "小柴", 23, new Date());
        Student s3 = new Student("3", "小柴", 23, new Date());
        ArrayList<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        for (int i = 0; i < students.size(); i++) {
            //重新创建一行
            HSSFRow row1 = sheet.createRow(i + 1);

            //创建一个单元格 下标是从0开始  并且给每一个单元格设置值
            HSSFCell cell2 = row1.createCell(0);
            cell2.setCellValue(students.get(i).getId());
            row1.createCell(1).setCellValue(students.get(i).getName());
            row1.createCell(2).setCellValue(students.get(i).getAge());
            HSSFCell cell3 = row1.createCell(3);
            cell3.setCellValue(students.get(i).getBirth());
            cell3.setCellStyle(cellStyle2);
        }

        //创建合并行的测试
        HSSFRow row1 = sheet.createRow(4);
        HSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("测试合并的行");
        //要合并的行     参数：行开始，行结束，列开时，列结束
        CellRangeAddress region=new CellRangeAddress(4, 4, 0, 3);
        sheet.addMergedRegion(region);


        try {
            //将数据写入到这个文件当中
            workbook.write(new FileOutputStream(new File("D:/testPoi/testPoi.xls")));

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPoiImport(){
        try {
            //获取要导入的文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:/testPoi/testPoi.xls")));

            //获取工作薄
            HSSFSheet sheet = workbook.getSheet("学生表");

            for (int i = 1; i < sheet.getLastRowNum(); i++) {

                Student student = new Student();

                //获取行
                HSSFRow row = sheet.getRow(i);

                //获取Id
                student.setId(row.getCell(0).getStringCellValue());
                //获取name
                student.setName(row.getCell(1).getStringCellValue());
                //获取age
                double ages = row.getCell(2).getNumericCellValue();
                student.setAge((int) ages);
                //获取生日
                student.setBirth(row.getCell(3).getDateCellValue());

                //调用一个插入数据库的方法
                System.out.println(student);
            }

            //关闭资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
