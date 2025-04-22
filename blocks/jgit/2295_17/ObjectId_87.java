
package org.eclipse.jgit.storage.dht.spi.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorTools {
	public static ExecutorService getDefaultExecutorService() {
		return DefaultExecutors.service;
	}

	private static class DefaultExecutors {
		static final ExecutorService service;
		static {
			int ncpu = Runtime.getRuntime().availableProcessors();
			ThreadFactory threadFactory = new ThreadFactory() {
				private final AtomicInteger cnt = new AtomicInteger();

				private final ThreadGroup group = new ThreadGroup("JGit-DHT");

				public Thread newThread(Runnable body) {
					int id = cnt.incrementAndGet();
					String name = "JGit-DHT-Worker-" + id;
					ClassLoader myCL = getClass().getClassLoader();

					Thread thread = new Thread(group
					thread.setDaemon(true);
					thread.setContextClassLoader(myCL);
					return thread;
				}
			};
			service = Executors.newFixedThreadPool(2 * ncpu
		}
	}

	private ExecutorTools() {
	}
}
