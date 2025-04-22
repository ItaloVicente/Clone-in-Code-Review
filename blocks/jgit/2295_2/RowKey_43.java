
package org.eclipse.jgit.storage.dht;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.ThreadSafeProgressMonitor;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.pack.PackWriter;

final class RepresentationSelector {
	private final PackWriter packer;

	private final RepositoryKey repo;

	private final Database db;

	private final DhtReader reader;

	private final ThreadSafeProgressMonitor progress;

	private final Semaphore batches;

	private final ReentrantLock selectLock;

	private final AtomicReference<DhtException> error;

	private final int concurrentBatches;

	private final List<DhtObjectToPack> retry;

	RepresentationSelector(PackWriter packer
			ProgressMonitor monitor
		this.packer = packer;
		this.repo = repo;
		this.db = db;
		this.reader = reader;
		this.progress = new ThreadSafeProgressMonitor(monitor);

		this.concurrentBatches = reader.getOptions()
				.getSelectObjectRepresentationConcurrentBatches();

		this.batches = new Semaphore(concurrentBatches);
		this.error = new AtomicReference<DhtException>();
		this.retry = new ArrayList<DhtObjectToPack>();
	}

	void select(Iterable<DhtObjectToPack> objects) throws IOException {
		selectInBatches(Context.FAST_MISSING_OK

		if (!retry.isEmpty()) {
			batches.release(concurrentBatches);
			selectInBatches(Context.READ_REPAIR
		}

		progress.pollForUpdates();
	}

	private void selectInBatches(Context options
			Iterable<DhtObjectToPack> objects) throws DhtException {
		final int batchSize = reader.getOptions()
				.getSelectObjectRepresentationBatchSize();

		Map<ObjectIndexKey
		Iterator<DhtObjectToPack> otpItr = objects.iterator();
		while (otpItr.hasNext()) {
			DhtObjectToPack otp = otpItr.next();

			batch.put(ObjectIndexKey.create(repo

			if (batch.size() < batchSize && otpItr.hasNext())
				continue;

			if (error.get() != null)
				break;

			try {
				while (!batches.tryAcquire(500
					progress.pollForUpdates();
				progress.pollForUpdates();
			} catch (InterruptedException err) {
				error.compareAndSet(null
				break;
			}

			startFindQuery(options
			batch = new HashMap<ObjectIndexKey
		}
		try {
			while (!batches.tryAcquire(concurrentBatches
				progress.pollForUpdates();
			progress.pollForUpdates();
		} catch (InterruptedException err) {
			error.compareAndSet(null
		}

		if (error.get() != null)
			throw error.get();

		selectLock.lock();
		selectLock.unlock();
	}

	private void startFindQuery(final Context options
			final Map<ObjectIndexKey
		final AsyncCallback<Map<ObjectIndexKey
			public void onSuccess(Map<ObjectIndexKey
				selectLock.lock();
				try {
					processFindResults(options
				} finally {
					selectLock.unlock();
					batches.release();
				}
			}

			public void onFailure(DhtException e) {
				error.compareAndSet(null
				batches.release();
			}
		};
		db.objectIndex().get(options
	}

	private void processFindResults(Context options
			Map<ObjectIndexKey
			Map<ObjectIndexKey
		DhtObjectRepresentation rep = new DhtObjectRepresentation();
		ArrayList<ObjectInfo> tmp = new ArrayList<ObjectInfo>();

		for (Map.Entry<ObjectIndexKey
			tmp.clear();
			tmp.addAll(entry.getValue());
			if (!tmp.isEmpty()) {
				DhtObjectToPack obj = batch.remove(entry.getKey());

				ObjectInfo.sort(tmp);
				rep.set(tmp.get(0));
				packer.select(obj
			}
			progress.update(1);
		}

		if (options == Context.FAST_MISSING_OK)
			retry.addAll(batch.values());
	}

	private static Set<Map.Entry<ObjectIndexKey
			Map<ObjectIndexKey
		return chunks.entrySet();
	}
}
