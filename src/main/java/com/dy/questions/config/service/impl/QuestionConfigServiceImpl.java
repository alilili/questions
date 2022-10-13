package com.dy.questions.config.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Question;
import com.dy.questions.config.entity.QuestionSub;
import com.dy.questions.config.entity.SaveBean;
import com.dy.questions.config.mapper.QuestionMapper;
import com.dy.questions.config.mapper.QuestionSubMapper;
import com.dy.questions.config.service.QuestionConfigService;
import com.dy.questions.utils.DevelopKit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/2 19:52
 * @description : 问卷配置服务实现类
 */
@Service
public class QuestionConfigServiceImpl implements QuestionConfigService {

    private final QuestionMapper questionMapper;
    private final QuestionSubMapper subMapper;

    public QuestionConfigServiceImpl(QuestionMapper questionMapper, QuestionSubMapper subMapper) {
        this.questionMapper = questionMapper;
        this.subMapper = subMapper;
    }

    @Override
    public JSONObject data() {

        JSONArray array;
        List<Question> questions = questionMapper.loadAllData();

        if(questions.size()>0){
            array = JSONArray.parseArray(JSON.toJSONString(questions));
        }else{
            array = new JSONArray();
        }

        JSONObject obj = new JSONObject();

        obj.put("code","0");
        obj.put("data",array);
        obj.put("count",questions.size());

        return obj;
    }

    @Override
    public JSONObject check(String year) {

        String id = questionMapper.checkConfig(year);

        JSONObject result = new JSONObject();

        result.put("result",id);

        return result;
    }

    @Override
    @Transactional
    public JSONObject publish(String id) {

        JSONObject result = new JSONObject();
        result.put("result",true);

        Question question = questionMapper.checkPublish();

        if(question!=null&&id.equals(question.getId())){
            //当前问卷调查已经发布
            return result;
        }

        //取消发布非本数据的问卷
        boolean flag = questionMapper.unPublishOther(id);

        if(!flag){
            result.put("result",false);
            return result;
        }

        //发布本数据
        flag = questionMapper.publish(id);

        if(!flag){
            result.put("result",false);
            return result;
        }

        return result;
    }

    @Override
    public JSONObject unPublish(String id) {
        JSONObject result = new JSONObject();
        result.put("result",questionMapper.unPublishById(id));
        return result;
    }

    @Override
    public String getPublishQuestionId() {
        Question question = questionMapper.checkPublish();
        if(question==null){
            return "";
        }
        return question.getId();
    }

    @Override
    @Transactional
    public JSONObject save(SaveBean bean) {

        JSONObject obj = new JSONObject();
        obj.put("result",false);

        boolean flag;

        if(StringUtils.isEmpty(bean.getId())){

            String configId = DevelopKit.generateUUID();

            //新增
            Question question = new Question();
            question.setId(configId);
            question.setYear(bean.getYear());
            question.setCreateDate(new Date());
            question.setRowstate(1);
            question.setBt(bean.getBt());
            question.setMark(bean.getMark());
            question.setStartDate(bean.getStartDate());
            question.setEndDate(bean.getEndDate());

            flag = questionMapper.saveQuestion(question);

            JSONArray array = JSONArray.parseArray(bean.getQuestions());

            for (int i = 0; i < array.size(); i++) {
                JSONObject quesObj = array.getJSONObject(i);

                QuestionSub sub = new QuestionSub();

                sub.setId(DevelopKit.generateUUID());
                sub.setFid(configId);
                sub.setOrgId(quesObj.getString("org"));
                sub.setShowOrder(quesObj.getIntValue("showOrder"));
                sub.setQuestion(quesObj.getString("question"));

                flag = subMapper.saveQuestionSub(sub);
            }

        }else{
            //更新
            Question question = new Question();
            question.setId(bean.getId());
            question.setYear(bean.getYear());
            question.setBt(bean.getBt());
            question.setMark(bean.getMark());
            question.setStartDate(bean.getStartDate());
            question.setEndDate(bean.getEndDate());

            flag = questionMapper.updateConfig(question);

            JSONArray array = JSONArray.parseArray(bean.getQuestions());

            for (int i = 0; i < array.size(); i++) {
                JSONObject quesObj = array.getJSONObject(i);

                String subId = quesObj.getString("subId");

                QuestionSub sub = new QuestionSub();

                if(StringUtils.isEmpty(subId)){
                    //子表执行新增操作
                    sub.setId(DevelopKit.generateUUID());
                    sub.setFid(bean.getId());
                    sub.setOrgId(quesObj.getString("org"));
                    sub.setShowOrder(quesObj.getIntValue("showOrder"));
                    sub.setQuestion(quesObj.getString("question"));

                    flag = subMapper.saveQuestionSub(sub);

                }else{
                    //子表执行更新操作
                    sub.setId(subId);
                    sub.setOrgId(quesObj.getString("org"));
                    sub.setShowOrder(quesObj.getIntValue("showOrder"));
                    sub.setQuestion(quesObj.getString("question"));

                    flag = subMapper.updateSub(sub);
                }
            }

        }

        obj.put("result",flag);

        return obj;
    }

    /**
     * 删除数据
     *
     * @param id 要删除的数据
     * @return JSONObject
     * @author : wanghl
     * @date :  2022/10/2 23:01
     */
    @Override
    public JSONObject delRecord(String id) {

        JSONObject obj = new JSONObject();

        obj.put("result",questionMapper.delRecord(id));

        return obj;
    }

    @Override
    public Question loadQuestion(String id) {
        return questionMapper.getById(id);
    }

    @Override
    public List<QuestionSub> loadQuestionSub(String id,String mark) {
        List<QuestionSub> questionSubs = subMapper.queryQuestionSub(id);
        if(StringUtils.isEmpty(mark)){
            return questionSubs;
        }
        for(QuestionSub sub : questionSubs){
            String orgName =  sub.getOrganize().getOrgName();
            String question = sub.getQuestion();
            sub.setQuestion(mark.replaceAll("\\[dept\\]",orgName).replaceAll("\\[question\\]",question));
        }

        return questionSubs;
    }

    @Override
    public JSONObject deleteSub(String subId) {
        JSONObject obj = new JSONObject();
        obj.put("result",subMapper.deleteSub(subId));
        return obj;
    }
}