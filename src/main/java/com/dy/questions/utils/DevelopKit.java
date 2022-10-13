package com.dy.questions.utils;

import java.util.UUID;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/1 23:29
 * @description : 开发工具包
 */
public class DevelopKit {

    /**
     * 生成主键
     * @author : wanghl
     * @date :  2022/10/1 23:32
     * @return String
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("\\-", "");
    }
}