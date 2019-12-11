package com.baizhi.zq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private String id;
    private String username;
    private String password;
}
