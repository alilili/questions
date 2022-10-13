package com.dy.questions.census.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;

public interface CensusService {
    JSONObject totalData(String year);

    JSONObject totalTopData(String year);

    JSONObject getTotalBarData(String questionId);

    void exportCensusData(HttpServletResponse response,String year, String quesCount, String cStore);
}
