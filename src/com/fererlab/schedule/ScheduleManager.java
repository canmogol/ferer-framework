package com.fererlab.schedule;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


public class ScheduleManager {

	private static ScheduleManager scheduleManager;
	
	private Scheduler scheduler;
	
	private ScheduleManager(String quartzConf){
		
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzConf);
			
			scheduler = schedulerFactory.getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO : log exception
			throw new RuntimeException(e);
		}
		
	}
	
	public static synchronized ScheduleManager createInstance(String quartzConf){
		
		if(scheduleManager == null){
			scheduleManager = new ScheduleManager(quartzConf);
		}
		
		return scheduleManager;
		
	}
	
	public static void scheduleJob(JobDetail job, Trigger trigger){
		
		synchronized (job.getJobClass()) {
			try {
				scheduleManager.getScheduler().scheduleJob(job, trigger);
			} catch (SchedulerException e) {
				// TODO : log exception
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void shutDownScheduler(){
		
		try {
			scheduleManager.getScheduler().shutdown();
		} catch (SchedulerException e) {
			// TODO : log exception
			throw new RuntimeException(e);
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
	
}
