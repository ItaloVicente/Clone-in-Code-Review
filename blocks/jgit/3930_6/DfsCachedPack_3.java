
package org.eclipse.jgit.storage.dfs;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.lib.Config;

public class DfsBlockCacheConfig {
	public static final int KB = 1024;

	public static final int MB = 1024 * KB;

	private long blockLimit;

	private int blockSize;

	private int readAheadLimit;

	private ThreadPoolExecutor readAheadService;

	public DfsBlockCacheConfig() {
		setBlockLimit(32 * MB);
		setBlockSize(64 * KB);
	}

	public long getBlockLimit() {
		return blockLimit;
	}

	public DfsBlockCacheConfig setBlockLimit(final long newLimit) {
		blockLimit = newLimit;
		return this;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public DfsBlockCacheConfig setBlockSize(final int newSize) {
		blockSize = Math.max(512
		return this;
	}

	public int getReadAheadLimit() {
		return readAheadLimit;
	}

	public DfsBlockCacheConfig setReadAheadLimit(final int newSize) {
		readAheadLimit = Math.max(0
		return this;
	}

	public ThreadPoolExecutor getReadAheadService() {
		return readAheadService;
	}

	public DfsBlockCacheConfig setReadAheadService(ThreadPoolExecutor svc) {
		if (svc != null)
			svc.setRejectedExecutionHandler(ReadAheadRejectedExecutionHandler.INSTANCE);
		readAheadService = svc;
		return this;
	}

	public DfsBlockCacheConfig fromConfig(final Config rc) {
		setBlockLimit(rc.getLong("core"
		setBlockSize(rc.getInt("core"
		setReadAheadLimit(rc.getInt("core"

		int readAheadThreads = rc.getInt("core"
		if (0 < getReadAheadLimit() && 0 < readAheadThreads) {
			setReadAheadService(new ThreadPoolExecutor(
					1
					readAheadThreads
					60
					new ArrayBlockingQueue<Runnable>(1)
					new ThreadFactory() {
						private final String name = "JGit-DFS-ReadAhead";
						private final AtomicInteger cnt = new AtomicInteger();
						private final ThreadGroup group = new ThreadGroup(name);

						public Thread newThread(Runnable body) {
							int id = cnt.incrementAndGet();
							Thread thread = new Thread(group
							thread.setDaemon(true);
							thread.setContextClassLoader(getClass().getClassLoader());
							return thread;
						}
					}
		}
		return this;
	}
}
