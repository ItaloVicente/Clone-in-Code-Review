
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectListChunk;
import org.eclipse.jgit.storage.dht.ObjectListChunkKey;
import org.eclipse.jgit.storage.dht.StreamingCallback;
import org.eclipse.jgit.storage.dht.Sync;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.ObjectListTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.cache.CacheService.Change;

final class CacheObjectListTable implements ObjectListTable {
	private final ObjectListTable db;

	private final CacheService client;

	private final Namespace ns = Namespace.OBJECT_LIST;

	CacheObjectListTable(ObjectListTable db
		this.db = db;
		this.client = mem.getClient();
	}

	public void get(Context options
			AsyncCallback<Collection<ObjectListChunk>> callback) {
		List<CacheKey> toFind = new ArrayList<CacheKey>(keys.size());
		for (ObjectListChunkKey k : keys)
			toFind.add(ns.key(k));
		client.get(toFind
	}

	public void put(ObjectListChunk chunk
			throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.put(chunk
		buf.modify(Change.put(ns.key(chunk.getRowKey())
	}

	private class LoaderFromCache implements
			StreamingCallback<Map<CacheKey
		private final Object lock = new Object();

		private final Context options;

		private final Set<ObjectListChunkKey> remaining;

		private final AsyncCallback<Collection<ObjectListChunk>> normalCallback;

		private final StreamingCallback<Collection<ObjectListChunk>> streamingCallback;

		private final Collection<ObjectListChunk> all;

		LoaderFromCache(Context options
				AsyncCallback<Collection<ObjectListChunk>> callback) {
			this.options = options;
			this.remaining = new HashSet<ObjectListChunkKey>(objects);
			this.normalCallback = callback;

			if (callback instanceof StreamingCallback<?>) {
				streamingCallback = (StreamingCallback<Collection<ObjectListChunk>>) callback;
				all = null;
			} else {
				streamingCallback = null;
				all = new ArrayList<ObjectListChunk>(objects.size());
			}
		}

		public void onPartialResult(Map<CacheKey
			Collection<ObjectListChunk> tmp;
			if (streamingCallback != null)
				tmp = new ArrayList<ObjectListChunk>(result.size());
			else
				tmp = null;

			synchronized (lock) {
				for (Map.Entry<CacheKey
					ObjectListChunkKey key;
					ObjectListChunk listChunk;

					key = ObjectListChunkKey.fromBytes(e.getKey().getBytes());
					listChunk = ObjectListChunk.fromBytes(key

					if (tmp != null)
						tmp.add(listChunk);
					else
						all.add(listChunk);
					remaining.remove(key);
				}

				if (tmp != null)
					streamingCallback.onPartialResult(tmp);
			}
		}

		public void onSuccess(Map<CacheKey
			if (result != null && !result.isEmpty())
				onPartialResult(result);

			synchronized (lock) {
				if (remaining.isEmpty() || options == Context.FAST_MISSING_OK) {
					normalCallback.onSuccess(all);
				} else {
					db.get(options
							normalCallback
				}
			}
		}

		public void onFailure(DhtException error) {
			normalCallback.onFailure(error);
		}
	}

	private class LoaderFromDatabase implements
			StreamingCallback<Collection<ObjectListChunk>> {
		private final Object lock = new Object();

		private final Collection<ObjectListChunk> all;

		private final AsyncCallback<Collection<ObjectListChunk>> normalCallback;

		private final StreamingCallback<Collection<ObjectListChunk>> streamingCallback;

		LoaderFromDatabase(Collection<ObjectListChunk> all
				AsyncCallback<Collection<ObjectListChunk>> normalCallback
				StreamingCallback<Collection<ObjectListChunk>> streamingCallback) {
			this.all = all;
			this.normalCallback = normalCallback;
			this.streamingCallback = streamingCallback;
		}

		public void onPartialResult(Collection<ObjectListChunk> result) {
			if (streamingCallback != null)
				streamingCallback.onPartialResult(result);
			else {
				synchronized (lock) {
					all.addAll(result);
				}
			}

			List<Change> ops = new ArrayList<Change>(result.size());
			for (ObjectListChunk lc : result)
				ops.add(Change.put(ns.key(lc.getRowKey())
			client.modify(ops
		}

		public void onSuccess(Collection<ObjectListChunk> result) {
			if (result != null && !result.isEmpty())
				onPartialResult(result);

			synchronized (lock) {
				normalCallback.onSuccess(all);
			}
		}

		public void onFailure(DhtException error) {
			normalCallback.onFailure(error);
		}
	}
}
