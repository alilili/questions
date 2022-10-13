package com.dy.questions.config.entity;

import com.alibaba.druid.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/3 01:01
 * @description : 问卷配置保存实体Bean
 */
public class SaveBean {
    private String id;
    private String year;
    private String bt;
    private String mark;
    private String startDate;
    private String endDate;
    private String questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Date getStartDate()  {
        if(StringUtils.isEmpty(startDate)){
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sf.parse(startDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        if(StringUtils.isEmpty(endDate)){
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sf.parse(endDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}