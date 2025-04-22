
package org.eclipse.jgit.storage.dht;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.ThreadSafeProgressMonitor;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;

abstract class BatchObjectLookup<T extends ObjectId> {
	private final RepositoryKey repo;

	private final Database db;

	private final DhtReader reader;

	private final ThreadSafeProgressMonitor progress;

	private final Semaphore batches;

	private final ReentrantLock resultLock;

	private final AtomicReference<DhtException> error;

	private final int concurrentBatches;

	private final List<T> retry;

	private final ArrayList<ObjectInfo> tmp;

	private boolean retryMissingObjects;

	private boolean cacheLoadedInfo;

	BatchObjectLookup(DhtReader reader) {
		this(reader
	}

	BatchObjectLookup(DhtReader reader
		this.repo = reader.getRepositoryKey();
		this.db = reader.getDatabase();
		this.reader = reader;

		if (monitor != null && monitor != NullProgressMonitor.INSTANCE)
			this.progress = new ThreadSafeProgressMonitor(monitor);
		else
			this.progress = null;

		this.concurrentBatches = reader.getOptions()
				.getObjectIndexConcurrentBatches();

		this.batches = new Semaphore(concurrentBatches);
		this.resultLock = new ReentrantLock();
		this.error = new AtomicReference<DhtException>();
		this.retry = new ArrayList<T>();
		this.tmp = new ArrayList<ObjectInfo>(4);
	}

	void setRetryMissingObjects(boolean on) {
		retryMissingObjects = on;
	}

	void setCacheLoadedInfo(boolean on) {
		cacheLoadedInfo = on;
	}

	void select(Iterable<T> objects) throws IOException {
		selectInBatches(Context.FAST_MISSING_OK

		if (retryMissingObjects && !retry.isEmpty()) {
			batches.release(concurrentBatches);
			selectInBatches(Context.READ_REPAIR
		}

		if (progress != null)
			progress.pollForUpdates();
	}

	private Iterable<T> lookInCache(Iterable<T> objects) {
		RecentInfoCache infoCache = reader.getRecentInfoCache();
		List<T> missing = null;
		for (T obj : objects) {
			List<ObjectInfo> info = infoCache.get(obj);
			if (info != null) {
				onResult(obj
				if (progress != null)
					progress.update(1);
			} else {
				if (missing == null) {
					if (objects instanceof List<?>)
						missing = new ArrayList<T>(((List<?>) objects).size());
					else
						missing = new ArrayList<T>();
				}
				missing.add(obj);
			}
		}
		if (missing != null)
			return missing;
		return Collections.emptyList();
	}

	private void selectInBatches(Context options
			throws DhtException {
		final int batchSize = reader.getOptions()
				.getObjectIndexBatchSize();

		Map<ObjectIndexKey
		Iterator<T> otpItr = objects.iterator();
		while (otpItr.hasNext()) {
			T otp = otpItr.next();

			batch.put(ObjectIndexKey.create(repo

			if (batch.size() < batchSize && otpItr.hasNext())
				continue;

			if (error.get() != null)
				break;

			try {
				if (progress != null) {
					while (!batches.tryAcquire(500
						progress.pollForUpdates();
					progress.pollForUpdates();
				} else {
					batches.acquire();
				}
			} catch (InterruptedException err) {
				error.compareAndSet(null
				break;
			}

			startQuery(options
			batch = new HashMap<ObjectIndexKey
		}

		try {
			if (progress != null) {
				while (!batches.tryAcquire(concurrentBatches
					progress.pollForUpdates();
				progress.pollForUpdates();
			} else {
				batches.acquire(concurrentBatches);
			}
		} catch (InterruptedException err) {
			error.compareAndSet(null
		}

		if (error.get() != null)
			throw error.get();

		resultLock.lock();
		resultLock.unlock();
	}

	private void startQuery(final Context context
			final Map<ObjectIndexKey
		final AsyncCallback<Map<ObjectIndexKey

		cb = new AsyncCallback<Map<ObjectIndexKey
			public void onSuccess(Map<ObjectIndexKey
				resultLock.lock();
				try {
					processResults(context
				} finally {
					resultLock.unlock();
					batches.release();
				}
			}

			public void onFailure(DhtException e) {
				error.compareAndSet(null
				batches.release();
			}
		};
		db.objectIndex().get(context
	}

	private void processResults(Context context
			Map<ObjectIndexKey
		for (T obj : batch.values()) {
			Collection<ObjectInfo> matches = objects.get(obj);

			if (matches == null || matches.isEmpty()) {
				if (retryMissingObjects && context == Context.FAST_MISSING_OK)
					retry.add(obj);
				continue;
			}

			tmp.clear();
			tmp.addAll(matches);
			ObjectInfo.sort(tmp);
			if (cacheLoadedInfo)
				reader.getRecentInfoCache().put(obj

			onResult(obj
		}

		if (progress != null)
			progress.update(objects.size());
	}

	protected abstract void onResult(T obj
}
