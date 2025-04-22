
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AsyncOperation;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;

class QueueObjectLookup<T extends ObjectId> implements AsyncOperation {
	protected final RepositoryKey repo;

	protected final Database db;

	protected final DhtReader reader;

	private final DhtReaderOptions options;

	private final boolean reportMissing;

	private final ArrayList<ObjectInfo> tmp;

	private final int concurrentBatches;

	private int runningBatches;

	private Context context;

	private Iterator<T> toFind;

	private List<T> toRetry;

	private ObjectWithInfo<T> nextResult;

	private DhtException error;

	private boolean cacheLoadedInfo;

	QueueObjectLookup(DhtReader reader
			boolean reportMissing) {
		this.repo = reader.getRepositoryKey();
		this.db = reader.getDatabase();
		this.reader = reader;
		this.options = reader.getOptions();
		this.reportMissing = reportMissing;
		this.tmp = new ArrayList<ObjectInfo>(4);
		this.context = Context.FAST_MISSING_OK;
		this.toFind = lookInCache(objectIds).iterator();
		this.toRetry = new ArrayList<T>();

		this.concurrentBatches = options
				.getSelectObjectRepresentationConcurrentBatches();
	}

	private Iterable<T> lookInCache(Iterable<T> objects) {
		RecentInfoCache infoCache = reader.getRecentInfoCache();
		List<T> missing = null;
		for (T obj : objects) {
			List<ObjectInfo> info = infoCache.get(obj);
			if (info != null && !info.isEmpty()) {
				push(obj
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

	void setCacheLoadedInfo(boolean on) {
		cacheLoadedInfo = on;
	}

	synchronized ObjectWithInfo<T> nextObjectWithInfo()
			throws MissingObjectException
		for (;;) {
			if (error != null)
				throw error;

			while (runningBatches < concurrentBatches) {
						&& !toRetry.isEmpty()) {
					toFind = toRetry.iterator();
					toRetry = null;
					context = Context.READ_REPAIR;
				}

				if (toFind.hasNext())
					startBatch(context);
				else
					break;
			}

			ObjectWithInfo<T> c = pop();
			if (c != null) {
				if (c.info != null)
					return c;
				else
					throw missing(c.object);

			} else if (!toFind.hasNext() && runningBatches == 0)
				return null;

			try {
				wait();
			} catch (InterruptedException e) {
				throw new DhtTimeoutException(e);
			}
		}
	}

	private synchronized void startBatch(final Context ctx) {
		final int batchSize = options.getSelectObjectRepresentationBatchSize();
		final Map<ObjectIndexKey
		while (toFind.hasNext() && batch.size() < batchSize) {
			T obj = toFind.next();
			batch.put(ObjectIndexKey.create(repo
		}

		final AsyncCallback<Map<ObjectIndexKey

		cb = new AsyncCallback<Map<ObjectIndexKey
			public void onSuccess(Map<ObjectIndexKey
				processResults(ctx
			}

			public void onFailure(DhtException e) {
				processFailure(e);
			}
		};
		db.objectIndex().get(ctx
		runningBatches++;
	}

	private synchronized void processResults(Context ctx
			Map<ObjectIndexKey
			Map<ObjectIndexKey
		for (T obj : batch.values()) {
			Collection<ObjectInfo> matches = objects.get(obj);

			if (matches == null || matches.isEmpty()) {
				if (ctx == Context.FAST_MISSING_OK)
					toRetry.add(obj);
				else if (reportMissing)
					push(obj
				continue;
			}

			tmp.clear();
			tmp.addAll(matches);
			ObjectInfo.sort(tmp);
			if (cacheLoadedInfo)
				reader.getRecentInfoCache().put(obj

			push(obj
		}

		runningBatches--;
		notify();
	}

	private synchronized void processFailure(DhtException e) {
		runningBatches--;
		error = e;
		notify();
	}

	private void push(T obj
		nextResult = new ObjectWithInfo<T>(obj
	}

	private ObjectWithInfo<T> pop() {
		ObjectWithInfo<T> r = nextResult;
		if (r == null)
			return null;
		nextResult = r.next;
		return r;
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		return true;
	}

	public void release() {
	}

	private static <T extends ObjectId> MissingObjectException missing(T id) {
		return new MissingObjectException(id
	}

	static class ObjectWithInfo<T extends ObjectId> {
		final T object;

		final ObjectInfo info;

		final ObjectWithInfo<T> next;

		ObjectWithInfo(T object
			this.object = object;
			this.info = info;
			this.next = next;
		}
	}
}
