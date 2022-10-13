package com.dy.questions.config.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Organizes;

public interface DeptService {

    JSONArray getOrganizesList();

    JSONArray getOrganizesList(String type);

    Organizes getOrganizesData(String pk);

    JSONObject save(Organizes org);

    JSONObject deleteData(String id);
}
