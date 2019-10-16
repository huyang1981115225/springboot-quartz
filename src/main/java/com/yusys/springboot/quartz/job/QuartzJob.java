package com.yusys.springboot.quartz.job;

import com.yusys.springboot.quartz.service.UsersService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Job类
 *
 * Created by huyang on 2019/10/16.
 */
public class QuartzJob implements Job {
	
	@Autowired
	private UsersService usersService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Execute...."+new Date());
		this.usersService.addUsers();
	}
}
