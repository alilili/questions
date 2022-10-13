package com.dy.questions.census.controller;

import com.alibaba.fastjson.JSONObject;
import com.dy.questions.census.entity.TotalCensus;
import com.dy.questions.census.service.CensusService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/census")
public class CensusController {
    @Autowired
    private CensusService censusService;

    @RequestMapping("/total")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("census/totalCensus");

        return mv;
    }

    @RequestMapping("/totalData")
    public JSONObject totalData(HttpServletRequest request) {
        String year = request.getParameter("year");
        if(StringUtil.isBlank(year)){
            Calendar calendar = Calendar.getInstance();
            year = String.valueOf(calendar.get(Calendar.YEAR));
        }
        return censusService.totalData(year);
    }

    @RequestMapping("/getTotalBarData")
    public JSONObject getTotalBarData(HttpServletRequest request) {
        String questionId = request.getParameter("questionId");
        return censusService.getTotalBarData(questionId);
    }

    @RequestMapping("/totalTopData")
    public JSONObject totalTopData(HttpServletRequest request) {
        String year = request.getParameter("year");
        if(StringUtil.isBlank(year)){
            Calendar calendar = Calendar.getInstance();
            year = String.valueOf(calendar.get(Calendar.YEAR));
        }
        return censusService.totalTopData(year);
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request , HttpServletResponse response){
        String year = request.getParameter("year");
        String quesCount = request.getParameter("quesCount");
        String cStore = request.getParameter("cStore");
        censusService.exportCensusData(response,year,quesCount,cStore);
    }
}
