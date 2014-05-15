package io.core9.scheduler;

public interface TaskProvider {
	
	/**
	 * Return a task specification
	 * @return
	 */
	Task getTaskSpec();
}
