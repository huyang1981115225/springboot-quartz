package com.yusys.springboot.quartz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * Created by huyang on 2019/10/16.
 *
 * createJobInstance（TriggerFiredBundle bundle）{
 *     return bundle.getJobDetail().getJobClass().newInstance();
 * }
 *
 * 实例化我们的类使用了反射，并没有交给Spring管理，IOC容器中没有我们的对象，所以不能@Autowired
 * 所以UserService不能@Autowired注入
 * 所以才需要写这个类将我们的对象(UserService)添加到SpringIOC容器中，并且完成该对象注入
 */
@Component("myAdaptableJobFactory")
public class MyAdaptableJobFactory extends AdaptableJobFactory {

	/**
	 * TODO ☆☆☆☆☆AutowireCapableBeanFactory 可以将一个对象添加到SpringIOC容器中，并且完成该对象注入
	 */
	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	/**
	 * TODO ☆☆☆☆☆该方法需要将实例化的任务对象手动的添加到springIOC容器中并且完成对象的注入
	 */
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object obj = super.createJobInstance(bundle);
		// TODO ☆☆☆☆☆将obj对象添加Spring IOC容器中，并完成注入
		this.autowireCapableBeanFactory.autowireBean(obj);
		return obj;
	}

}
