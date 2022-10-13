package com.dy.questions.census.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.census.entity.TotalCensus;
import com.dy.questions.census.mapper.TotalCensusMapper;
import com.dy.questions.census.service.CensusService;
import com.dy.questions.census.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class CensusServiceImpl implements CensusService {
    private final TotalCensusMapper totalCensusMapper;

    public CensusServiceImpl(TotalCensusMapper totalCensusMapper){
        this.totalCensusMapper = totalCensusMapper;
    }

    @Override
    public JSONObject totalData(String year) {
        JSONArray array = new JSONArray();
        List<TotalCensus> list = totalCensusMapper.getTotalCensusData(year);
        if(list.size()>0) {
            array = JSONArray.parseArray(JSON.toJSONString(list));
        }
        JSONObject object = new JSONObject();
        object.put("code","0");
        object.put("data",array);
        object.put("count",array.size());
        return object;
    }

    @Override
    public JSONObject totalTopData(String year) {
        JSONObject object = new JSONObject();
        TotalCensus totalCensus = totalCensusMapper.getTotalCensusTopData(year);
        String total = "0";
        String cstore = "0";
        if(totalCensus!=null){
            total = totalCensus.getQuesCount();
            cstore = totalCensus.getcStore();
        }
        object.put("total",total);
        object.put("cstore",cstore);
        return object;
    }

    @Override
    public JSONObject getTotalBarData(String questionId) {
        JSONObject object = new JSONObject();
        TotalCensus totalCensus = totalCensusMapper.getTotalBarData(questionId);
        object.put("data",totalCensus);
        return object;
    }

    @Override
    public void exportCensusData(HttpServletResponse response,String year, String quesCount, String cStore) {
        List<TotalCensus> list = totalCensusMapper.getTotalCensusExcelData(year);
        HSSFWorkbook excel = ExcelUtils.createExcel(list, quesCount,cStore);
        ExcelUtils.doDownload(excel, "工作计划", response);
    }
}
