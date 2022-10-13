package com.dy.questions.publish.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Question;
import com.dy.questions.config.entity.QuestionSub;
import com.dy.questions.config.service.QuestionConfigService;
import com.dy.questions.publish.entity.Reply;
import com.dy.questions.publish.service.PublishService;
import com.dy.questions.utils.IpLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 问卷发布，填写提交控制器
 */
@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private PublishService publishService;

    @Autowired
    private QuestionConfigService configService;

    @RequestMapping("/form")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response ){

        String questionId = configService.getPublishQuestionId();

        if(StringUtils.isEmpty(questionId)){
            return new ModelAndView("publish/noQuestion");
        }

        ModelAndView mv = new ModelAndView("publish/form");

        Question question = configService.loadQuestion(questionId);
        if(question!=null && !StringUtils.isEmpty(question.getId())){

            List<QuestionSub> list = configService.loadQuestionSub(questionId,question.getMark());
            mv.addObject("questionSub",list);

            mv.addObject("question",question);
            mv.addObject("questionsNum",list.size());

            return mv;
        }
        System.out.println("获取问卷信息异常，客户端ip："+IpLocalUtil.getIPAddress(request)+"，问卷id："+ questionId);

        //500页面
        try {
            String text = "获取问卷失败";
            response.setContentType("text/html;charset=UTF-8");
            response.setContentLength(text.getBytes("UTF-8").length);
            response.getWriter().write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        throw new RuntimeException("获取问卷信息异常，客户端ip："+IpLocalUtil.getIPAddress(request)+"，问卷id："+ questionId);
        return null;
    }


    /**
     * 保存数据
     * @return
     */
    @RequestMapping("/save")
    public JSONObject save(HttpServletRequest request, Reply reply){

        return publishService.saveReply(request,reply);
    }

}