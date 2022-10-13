package com.dy.questions.demo.controller;

import com.dy.questions.demo.entity.Test;
import com.dy.questions.demo.mapper.TestMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/1 00:15
 * @description : 样例测试
 */
@RestController
@RequestMapping("/ctrl/demo")
public class DemoController {

    private final TestMapper mapper;

    public DemoController(TestMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping("/index")
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView("demo/demo");

        Test testById = mapper.getTestById("1");
        mv.addObject("name",testById.getName());

        return mv;
    }

}