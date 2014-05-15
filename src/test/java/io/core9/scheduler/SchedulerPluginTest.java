package io.core9.scheduler;

import io.core9.core.PluginRegistry;
import io.core9.core.PluginRegistryImpl;
import io.core9.core.boot.BootstrapFramework;

public class SchedulerPluginTest {
	
	public static void main(String[] args) {
		BootstrapFramework.run();
		PluginRegistry registry = PluginRegistryImpl.getInstance();
		SchedulerPlugin plugin = (SchedulerPlugin) registry.getPlugin(SchedulerPluginImpl.class);
		
		Task task = new Task() {

			@Override
			public void execute() {
				System.out.println("Test running");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Test done!");
			}

			@Override
			public String getName() {
				return "test";
			}

			@Override
			public String getGroup() {
				return "group";
			}
		};
		plugin.registerTask(task);
		plugin.triggerTask(task.getName(), task.getGroup());
		plugin.triggerTask(task.getName(), task.getGroup(), 10);
	}

}
