
package org.eclipse.jgit.storage.dht.spi.tools;

import java.util.concurrent.ExecutorService;
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

				public Thread newThread(Runnable taskBody) {
					int id = cnt.incrementAndGet();
					ClassLoader myCL = getClass().getClassLoader();

					Thread t = new Thread(taskBody);
					t.setDaemon(true);
					t.setName("JGit-DHT-Worker-" + id);
					t.setContextClassLoader(myCL);
					return t;
				}
			};
			service = java.util.concurrent.Executors.newFixedThreadPool(
					2 * ncpu
		}
	}

	private ExecutorTools() {
	}
}
