package com.dy.questions.timer.taskJob;

import com.dy.questions.publish.mapper.PublishMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

/**
 * 定时任务
 */
@Configuration
@EnableScheduling
public class ClearReplyData {

    private final PublishMapper mapper;

    public ClearReplyData(PublishMapper mapper) {
        this.mapper = mapper;
    }

    //每年年底将用户回复数据置空或删除 12-31 23:50
//    @Scheduled(cron = "0 0/1 * * * ?"  )
    @Scheduled(cron = "0 50 23 31 12 ?"  )
    private void clearReplyDataTasks(){

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        boolean flag = mapper.clearUserInfo(year);
        System.out.println("===================用户回复数据清理："+ flag+(flag?"成功":"失败"));
    }

}
