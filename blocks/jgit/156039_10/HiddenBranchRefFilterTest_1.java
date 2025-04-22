package org.eclipse.jgit.niofs.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jgit.niofs.internal.util.DescriptiveThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorServiceProducer {

	private Logger logger = LoggerFactory.getLogger(ExecutorServiceProducer.class);

	private final ExecutorService executorService;
	private final ExecutorService unmanagedExecutorService;
	private final ExecutorService indexingExecutorService;

	protected static final String MANAGED_LIMIT_PROPERTY = "concurrent.managed.thread.limit";
	protected static final String UNMANAGED_LIMIT_PROPERTY = "concurrent.unmanaged.thread.limit";
	protected static final String INDEXING_LIMIT_PROPERTY = "concurrent.indexing.thread.limit";

	public ExecutorServiceProducer() {
		this.executorService = this.buildFixedThreadPoolExecutorService(MANAGED_LIMIT_PROPERTY);
		this.unmanagedExecutorService = this.buildFixedThreadPoolExecutorService(UNMANAGED_LIMIT_PROPERTY);
		this.indexingExecutorService = this.buildFixedThreadPoolExecutorService(INDEXING_LIMIT_PROPERTY);
	}

	protected ExecutorService buildFixedThreadPoolExecutorService(String key) {
		String stringProperty = System.getProperty(key);
		int threadLimit = stringProperty == null ? 0 : toInteger(stringProperty);
		if (threadLimit > 0) {
			return Executors.newFixedThreadPool(threadLimit
		} else {
			return Executors.newCachedThreadPool(new DescriptiveThreadFactory());
		}
	}

	private Integer toInteger(String stringProperty) {
		try {
			return Integer.valueOf(stringProperty);
		} catch (NumberFormatException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Property {} is invalid
			}
			return 0;
		}
	}

	public ExecutorService produceExecutorService() {
		return this.getManagedExecutorService();
	}

	public ExecutorService produceUnmanagedExecutorService() {
		return this.getUnmanagedExecutorService();
	}

	public ExecutorService produceIndexingExecutorService() {
		return this.getIndexingExecutorService();
	}

	protected ExecutorService getManagedExecutorService() {
		return this.executorService;
	}

	protected ExecutorService getUnmanagedExecutorService() {
		return this.unmanagedExecutorService;
	}

	protected ExecutorService getIndexingExecutorService() {
		return this.indexingExecutorService;
	}
}
