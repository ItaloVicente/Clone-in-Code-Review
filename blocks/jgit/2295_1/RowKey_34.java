
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.ThreadSafeProgressMonitor;
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
	}

	void select(Iterable<DhtObjectToPack> objects) throws IOException {
		final int batchSize = reader.getOptions()
				.getSelectObjectRepresentationBatchSize();

		Map<ObjectId
		Iterator<DhtObjectToPack> otpItr = objects.iterator();
		while (otpItr.hasNext()) {
			DhtObjectToPack otp = otpItr.next();

			batch.put(otp

			if (batch.size() < batchSize && otpItr.hasNext())
				continue;

			if (error.get() != null)
				break;

			try {
				batches.acquire();
			} catch (InterruptedException err) {
				error.compareAndSet(null
				break;
			}

			startFindQuery(batch);
			batch = new HashMap<ObjectId
		}

		try {
			batches.acquire(concurrentBatches);
		} catch (InterruptedException err) {
			error.compareAndSet(null
		}

		if (error.get() != null)
			throw error.get();

		selectLock.lock();
		selectLock.unlock();
	}

	private void startFindQuery(final Map<ObjectId
		HashSet<ObjectIndexKey> keys = new HashSet<ObjectIndexKey>();
		for (ObjectId o : batch.keySet())
			keys.add(ObjectIndexKey.create(repo

		final AsyncCallback<Map<ObjectIndexKey
			public void onSuccess(Map<ObjectIndexKey
				selectLock.lock();
				try {
					processFindResults(batch
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
		db.objectIndex().get(keys
	}

	private void processFindResults(Map<ObjectId
			Map<ObjectIndexKey
		DhtObjectRepresentation rep = new DhtObjectRepresentation();
		ArrayList<ChunkLink> tmp = new ArrayList<ChunkLink>();

		for (Map.Entry<ObjectIndexKey
			tmp.clear();
			tmp.addAll(entry.getValue());
			ChunkLink.sort(tmp);


			DhtObjectToPack obj = batch.get(entry.getKey());
			for (ChunkLink key : tmp) {
				rep.set(key);
				packer.select(obj
			}
			progress.update(1);
		}
	}

	private static Set<Entry<ObjectIndexKey
			Map<ObjectIndexKey
		return chunks.entrySet();
	}
}
