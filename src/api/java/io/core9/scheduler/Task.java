package io.core9.scheduler;

public interface Task {

	/**
	 * Executes the task
	 */
	void execute();
	
	/**
	 * Identifier for the task
	 * @return
	 */
	String getName();
	
	/**
	 * Group identifier for the task
	 * @return
	 */
	String getGroup();
}
