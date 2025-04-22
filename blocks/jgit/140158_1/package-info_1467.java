
package org.eclipse.jgit.lib.internal;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class WorkQueue {
	private static final ScheduledThreadPoolExecutor executor;

	static final Object executorKiller;

	static {
		int threads = 1;
		executor = new ScheduledThreadPoolExecutor(threads
				new ThreadFactory() {
					private final ThreadFactory baseFactory = Executors
							.defaultThreadFactory();

					@Override
					public Thread newThread(Runnable taskBody) {
						Thread thr = baseFactory.newThread(taskBody);
						thr.setContextClassLoader(null);
						thr.setDaemon(true);
						return thr;
					}
				});
		executor.setRemoveOnCancelPolicy(true);
		executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		executor.prestartAllCoreThreads();

		executor.setThreadFactory(Executors.defaultThreadFactory());

		executorKiller = new Object() {
			@Override
			protected void finalize() {
				executor.shutdownNow();
			}
		};
	}

	public static ScheduledThreadPoolExecutor getExecutor() {
		return executor;
	}
}
