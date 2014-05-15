package io.core9.scheduler;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

@PluginImplementation
public class SchedulerPluginImpl implements SchedulerPlugin {
	
	private Scheduler scheduler;

	public SchedulerPluginImpl() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
    
		// Shutdown handler for scheduler
        Runtime.getRuntime().addShutdownHook(new Thread() {
        	public void run() {
        		try {
					scheduler.shutdown();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
            }
        });
	}
	
	private void schedule(Trigger trigger) {
		try {
			scheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SchedulerPlugin registerTask(Task task) {
		JobDataMap jobData = new JobDataMap();
		jobData.put("core9task", task);
		JobDetail job = JobBuilder
				.newJob(JobWrapper.class)
				.storeDurably()
				.withIdentity(task.getName(), task.getGroup())
				.setJobData(jobData)
				.build();
		try {
			scheduler.addJob(job, true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public SchedulerPlugin triggerTask(String name, String group) {
		schedule(newTrigger().startNow().forJob(name, group).build());
		return this;
	}
	
	@Override
	public SchedulerPlugin triggerTaskOnDate(String name, String group,	Date startDate) {
		schedule(newTrigger().startAt(startDate).forJob(name, group).build());
		return this;
	}
	
	@Override
	public SchedulerPlugin triggerTask(String name, String group, int interval) {
		schedule(newTrigger().startNow().forJob(name, group)
				.withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever())
		        .build());
		return this;
	}

	@Override
	public SchedulerPlugin triggerTaskOnDate(String name, String group, Date startDate, int interval) {
		schedule(newTrigger().startAt(startDate).forJob(name, group)
				.withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever())
		        .build());
		return this;
	}

	@Override
	public SchedulerPlugin removeTask(Task task) {
		return removeTask(task.getName(), task.getGroup());
	}

	@Override
	public SchedulerPlugin removeTask(String name, String group) {
		try {
			scheduler.deleteJob(new JobKey(name, group));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return this;
	}

	
}
