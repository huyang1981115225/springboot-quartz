package com.yusys.springboot.quartz.config;

import com.yusys.springboot.quartz.job.QuartzJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * Quartz配置类
 *
 * Created by huyang on 2019/10/16.
 */
@Configuration
public class QuartzConfig {

	
	/**
	 * 1.创建Job对象
	 */
	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		// TODO 关联我们自己的Job类
		factory.setJobClass(QuartzJob.class);
		return factory;
	}
	
	/**
	 * 2.创建Trigger对象
	 * 一、简单的Trigger
	 */
	@Bean
	public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
		SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
		// TODO 关联JobDetail对象
		factory.setJobDetail(jobDetailFactoryBean.getObject());

		//该参数表示一个执行的毫秒数
		factory.setRepeatInterval(2000);

		//重复次数
		factory.setRepeatCount(5);
		return factory;
	}
	
	/**
	 * 二、Cron Trigger
	 */
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
		CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
		// TODO 关联JobDetail对象
		factory.setJobDetail(jobDetailFactoryBean.getObject());
		//设置触发时间
		factory.setCronExpression("0/10 * * * * ?");
		return factory;
	}
	
	/**
	 * 3.创建Scheduler对象
	 */
//	@Bean
//	public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean){
//		SchedulerFactoryBean factory = new SchedulerFactoryBean();
//		//关联简单Trigger
//		factory.setTriggers(simpleTriggerFactoryBean.getObject());
//		return factory;
//	}

//	@Bean
//	public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean){
//		SchedulerFactoryBean factory = new SchedulerFactoryBean();
//		//关联Cron Trigger
//		factory.setTriggers(cronTriggerFactoryBean.getObject());
//		return factory;
//	}

/**********************************************************************************************************************************************/
/*****************************TODO 加入MyAdaptableJobFactory，解决spring注入我们的对象(UserService)的问题***************************/
/**********************************************************************************************************************************************/
//	@Bean
//	public SchedulerFactoryBean schedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean,MyAdaptableJobFactory myAdaptableJobFactory){
//		SchedulerFactoryBean factory = new SchedulerFactoryBean();
//		//关联Cron Trigger
//		factory.setTriggers(simpleTriggerFactoryBean.getObject());
//		// TODO 加入MyAdaptableJobFactory
//		factory.setJobFactory(myAdaptableJobFactory);
//		return factory;
//	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory){
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		//关联简单Trigger
		factory.setTriggers(cronTriggerFactoryBean.getObject());
		// TODO 加入MyAdaptableJobFactory
		factory.setJobFactory(myAdaptableJobFactory);
		return factory;
	}
}
