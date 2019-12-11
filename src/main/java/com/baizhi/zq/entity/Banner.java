package com.baizhi.zq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name="banner")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {
    @Id
    private String id;
    private String src_img;
    private String  description;
    private String status;
    private Date upload_time;
}
