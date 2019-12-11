package com.baizhi.zq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teachter {
    private String id;
    private String name;
    private List<Student> students;
}
