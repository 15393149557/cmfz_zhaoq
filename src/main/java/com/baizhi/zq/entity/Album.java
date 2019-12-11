package com.baizhi.zq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="album")
public class Album implements Serializable {
    private String id;

    private String title;
    @Column(name="cover_img")
    private String cover_img;

    private Integer score;

    private String author;

    private String broadcast;

    private Integer count;

    private String countent;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="pub_date")
    private Date pub_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCoverImg() {
        return cover_img;
    }

    public void setCoverImg(String coverImg) {
        this.cover_img = coverImg == null ? null : coverImg.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast == null ? null : broadcast.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCountent() {
        return countent;
    }

    public void setCountent(String countent) {
        this.countent = countent == null ? null : countent.trim();
    }

    public Date getPubDate() {
        return pub_date;
    }

    public void setPubDate(Date pubDate) {
        this.pub_date = pubDate;
    }
}