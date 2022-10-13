package com.dy.questions.publish.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Question;
import com.dy.questions.config.service.QuestionConfigService;
import com.dy.questions.publish.entity.Reply;
import com.dy.questions.publish.entity.ReplySub;
import com.dy.questions.publish.mapper.PublishMapper;
import com.dy.questions.publish.service.PublishService;
import com.dy.questions.utils.DevelopKit;
import com.dy.questions.utils.IpLocalUtil;
import com.dy.questions.utils.ResultJsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


@Service
public class PublishServiceImpl implements PublishService {

    private final PublishMapper mapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public PublishServiceImpl(PublishMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private QuestionConfigService configService;


    public static Integer SUBMIT_BETWEEN_SECOND = 10*1000;//两次提交间隔时间 秒


    @Override
    @Transactional
    public JSONObject saveReply(HttpServletRequest request, Reply reply) {

        HttpSession session = request.getSession();
        String ip = IpLocalUtil.getIPAddress(request);
        reply.setIp(ip);

        String configId = reply.getConfigId();

        if(StringUtils.isEmpty(configId)){
            logger.error("【问卷】提交失败，未获取到问卷设置表单ip:[{}]，configId:[{}]",ip,configId);
            return ResultJsonKit.error("提交失败，未找到要提交的问卷信息。");
        }

        //提交次数限制
        Date lastSaveTime = (Date) session.getAttribute("lastSaveTime");
        if(lastSaveTime !=null && (System.currentTimeMillis()-lastSaveTime.getTime())< SUBMIT_BETWEEN_SECOND){
            logger.error("【问卷】操作过于频繁，提交ip:[{}]",ip);
            return ResultJsonKit.error("操作过于频繁。");
        }
        session.setAttribute("lastSaveTime",new Date());

        //是否存在，是否发布和超过时间范围
        Question question = configService.loadQuestion(configId);
        if(question==null){
            logger.error("【问卷】提交失败，未找到要提交的问卷信息ip:[{}]，configId:[{}]",ip,configId);
            return ResultJsonKit.error("提交失败，未找到要提交的问卷信息。");
        }

        if(question.getPublish()!=1 || question.getStartDateTime()==null || question.getEndDateTime()==null
                || new Date().getTime()<question.getStartDateTime().getTime()
                || new Date().getTime()>question.getEndDateTime().getTime()){
            logger.error("【问卷】提交失败，调查问卷尚未开始ip:[{}]，configId:[{}]，开始时间startdate:[{}]，结束时间enddate:[{}]",ip,configId,question.getStartDate(),question.getEndDate());
            return ResultJsonKit.error("提交失败，问卷调查尚未开始。");
        }

        //保存主表
        String replyId = DevelopKit.generateUUID();
        reply.setId(replyId);
        reply.setCreateTime(new Date());

        boolean flag = false;

        try {
            flag = mapper.saveReply(reply);
        } catch (Exception e) {
            logger.error("【问卷】主表数据提交失败，formData:[\n{}\n]", JSON.toJSONString(reply));
            e.printStackTrace();
        }

        if(!flag){
            return ResultJsonKit.error("数据提交失败，请检查填写的信息。");
        }

        //保存子表
        JSONArray array = JSONArray.parseArray(reply.getQuestions());

        for (int i = 0; i < array.size(); i++) {
            JSONObject qObj = array.getJSONObject(i);

            ReplySub sub = new ReplySub();

            sub.setId(DevelopKit.generateUUID());
            sub.setFid(replyId);
            sub.setQuestionId(qObj.getString("questionId"));

            sub.setAnswer(qObj.getString("answer"));
            sub.setAnswerText(qObj.getString("answerText"));
            sub.setConfigId(reply.getConfigId());
            sub.setQuestion(qObj.getString("question"));
            sub.setShowOrder(qObj.getString("showOrder"));

            try {
                flag = mapper.saveReplySub(sub);
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            }

            if(!flag){
                logger.error("【问卷】子表数据提交失败，formData:[\n{}\n]", JSON.toJSONString(reply));
                return ResultJsonKit.error("数据提交失败。");
            }
        }

        //提交成功重设key值
        session.setAttribute("key","0");

        return ResultJsonKit.success();
    }
}
