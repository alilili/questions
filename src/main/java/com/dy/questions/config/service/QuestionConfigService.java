package com.dy.questions.config.service;

import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Question;
import com.dy.questions.config.entity.QuestionSub;
import com.dy.questions.config.entity.SaveBean;

import java.util.List;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/2 19:52
 * @description : 问卷配置服务接口
 */
public interface QuestionConfigService {

    /**
     * 加载全部调查配置数据
     * @author : wanghl
     * @date :  2022/10/2 21:47
     * @return JSONObject
     */
    JSONObject data();

    JSONObject check(String year);

    JSONObject publish(String id);

    JSONObject unPublish(String id);

    String getPublishQuestionId();

    /**
     * 保存配置数据
     * @author : wanghl
     * @date :  2022/10/2 22:00
     * @return JSONObject
     */
    JSONObject save(SaveBean bean);

    /**
     * 删除数据
     * @author : wanghl
     * @date :  2022/10/2 23:01
     * @param id 要删除的数据
     * @return JSONObject
     */
    JSONObject delRecord(String id);

    Question loadQuestion(String id);

    List<QuestionSub> loadQuestionSub(String id,String mark);

    JSONObject deleteSub(String subId);

}