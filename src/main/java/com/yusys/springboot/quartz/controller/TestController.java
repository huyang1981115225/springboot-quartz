package com.yusys.springboot.quartz.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huyang on 2019/10/16.
 */
@RestController
public class TestController {

    @Autowired
    private Scheduler scheduler;

    @RequestMapping("/start")
    public String start() throws Exception {
        JobKey jobKey = new JobKey("job1");
        System.out.println(scheduler.getJobDetail(jobKey));
        scheduler.start();
        return "start";
    }

    @RequestMapping("/updateTime")
    @ResponseBody
    public String trigger() throws Exception {
        // 获取任务
        JobKey jobKey = new JobKey("job1");
        // 获取 jobDetail
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        // 生成 新的trigger，设置执行时间为2s
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();
        // 删除任务，不删除会报错。报任务已存在
        scheduler.deleteJob(jobKey);
        // 启动任务
        scheduler.scheduleJob(jobDetail, trigger);
        return "updateTime";
    }

    @RequestMapping("/pause")
    @ResponseBody
    public String pause() throws Exception {

        JobKey key = new JobKey("job1");
        scheduler.pauseJob(key);
        return "pause job1";
    }
}
