package io.core9.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobWrapper implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		((Task) context.getMergedJobDataMap().get("core9task")).execute();
	}
	
}
