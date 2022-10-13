package com.dy.questions.config.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Organizes;
import com.dy.questions.config.mapper.DeptMapper;
import com.dy.questions.config.service.DeptService;
import com.dy.questions.utils.DevelopKit;
import com.dy.questions.utils.ResultJsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class DeptServiceImpl implements DeptService {

    private final DeptMapper mapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DeptServiceImpl(DeptMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public JSONArray getOrganizesList() {
        return getOrganizesList(null);
    }

    @Override
    public JSONArray getOrganizesList(String type) {
        JSONArray array = new JSONArray();
        List<Organizes> list = null;
        if("all".equals(type)){
            list = mapper.getListData();
        }else{
            list = mapper.getOrganizes();
        }

        if(list.size()>0){
            array = JSONArray.parseArray(JSON.toJSONString(list));
        }
        return array;
    }

    @Override
    public Organizes getOrganizesData(String pk) {

        Organizes org = mapper.getOrgById(pk);

        return org;
    }

    @Override
    public JSONObject save(Organizes org) {

        boolean isNew = false;
        if(StringUtils.isEmpty(org.getId())){
            isNew = true;
        }

        if(!isNew){
            Organizes temp = mapper.getOrgById(org.getId());
            if(temp == null){
                isNew = true;
            }
        }

        boolean flag = false;
        try {
            if(isNew){
                org.setId(DevelopKit.generateUUID());
                org.setCreateTime(new Date());

                flag = mapper.insertData(org);
            }else{
                org.setLastUpdateTime(new Date());
                flag = mapper.updateOrg(org);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultJsonKit.result(flag);
    }

    @Override
    public JSONObject deleteData(String id) {
        boolean flag = mapper.deleteData(id);
        return ResultJsonKit.result(flag);
    }
}
