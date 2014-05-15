package io.core9.scheduler;

import java.util.Date;

import io.core9.core.executor.Executor;
import io.core9.core.plugin.Core9Plugin;

public interface SchedulerPlugin extends Core9Plugin, Executor {

	/**
	 * Register a task in the scheduler
	 * @param task
	 * @return
	 */
	SchedulerPlugin registerTask(Task task);
	
	/**
	 * Trigger a task
	 * @param taskName
	 * @param taskGroup
	 */
	SchedulerPlugin triggerTask(String name, String group);
	
	/**
	 * Trigger a task on a specific date
	 * @param taskName
	 * @param taskGroup
	 */
	SchedulerPlugin triggerTaskOnDate(String name, String group, Date startDate);
	
	/**
	 * Trigger a recurring task
	 * @param taskName
	 * @param taskGroup
	 */
	SchedulerPlugin triggerTask(String name, String group, int interval);
	
	/**
	 * Trigger a recurring task on a specific date
	 * @param taskName
	 * @param taskGroup
	 */
	SchedulerPlugin triggerTaskOnDate(String name, String group, Date startDate, int interval);
	
}
