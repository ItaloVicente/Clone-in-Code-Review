
package org.eclipse.jgit.storage.dht.spi.cache;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectIndexKey;
import org.eclipse.jgit.storage.dht.ObjectInfo;
import org.eclipse.jgit.storage.dht.StreamingCallback;
import org.eclipse.jgit.storage.dht.Sync;
import org.eclipse.jgit.storage.dht.TinyProtobuf;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.cache.CacheService.Change;

final class CacheObjectIndexTable implements ObjectIndexTable {
	private final ObjectIndexTable db;

	private final ExecutorService executor;

	private final CacheService client;

	private final Namespace ns = Namespace.OBJECT_INDEX;

	CacheObjectIndexTable(ObjectIndexTable db
		this.db = db;
		this.executor = mem.getExecutorService();
		this.client = mem.getClient();
	}

	public void get(Context options
			AsyncCallback<Map<ObjectIndexKey
		List<CacheKey> toFind = new ArrayList<CacheKey>(objects.size());
		for (ObjectIndexKey k : objects)
			toFind.add(ns.key(k));
		client.get(toFind
	}

	public void add(ObjectIndexKey objId
			throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.add(objId

		buf.modify(Change.putIfAbsent(ns.key(objId)
	}

	public void remove(ObjectIndexKey objId
			throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.remove(objId

		buf.remove(ns.key(objId));
	}

	private static byte[] encode(Collection<ObjectInfo> list) {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(128);
		for (ObjectInfo info : list) {
			TinyProtobuf.Encoder m = TinyProtobuf.encode(128);
			m.bytes(1
			m.bytes(2
			m.fixed64(3
			e.message(1
		}
		return e.asByteArray();
	}

	private static ObjectInfo decodeItem(TinyProtobuf.Decoder m) {
		ChunkKey key = null;
		TinyProtobuf.Decoder data = null;
		long time = -1;

		for (;;) {
			switch (m.next()) {
			case 0:
				return ObjectInfo.fromBytes(key
			case 1:
				key = ChunkKey.fromBytes(m);
				continue;
			case 2:
				data = m.message();
				continue;
			case 3:
				time = m.fixed64();
				continue;
			default:
				m.skip();
			}
		}
	}

	private static Collection<ObjectInfo> decode(byte[] raw) {
		List<ObjectInfo> res = new ArrayList<ObjectInfo>(1);
		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		for (;;) {
			switch (d.next()) {
			case 0:
				return res;
			case 1:
				res.add(decodeItem(d.message()));
				continue;
			default:
				d.skip();
			}
		}
	}

	private class LoaderFromCache implements
			StreamingCallback<Map<CacheKey
		private final Object lock = new Object();

		private final Context options;

		private final Set<ObjectIndexKey> remaining;

		private final AsyncCallback<Map<ObjectIndexKey

		private final StreamingCallback<Map<ObjectIndexKey

		private final Map<ObjectIndexKey

		LoaderFromCache(
				Context options
				Set<ObjectIndexKey> objects
				AsyncCallback<Map<ObjectIndexKey
			this.options = options;
			this.remaining = new HashSet<ObjectIndexKey>(objects);
			this.normalCallback = callback;

			if (callback instanceof StreamingCallback<?>) {
				streamingCallback = (StreamingCallback<Map<ObjectIndexKey
				all = null;
			} else {
				streamingCallback = null;
				all = new HashMap<ObjectIndexKey
			}
		}

		public void onPartialResult(Map<CacheKey
			Map<ObjectIndexKey
			if (streamingCallback != null)
				tmp = new HashMap<ObjectIndexKey
			else
				tmp = null;

			for (Map.Entry<CacheKey
				ObjectIndexKey objKey;
				Collection<ObjectInfo> list = decode(e.getValue());
				objKey = ObjectIndexKey.fromBytes(e.getKey().getBytes());

				if (tmp != null)
					tmp.put(objKey
				else {
					synchronized (lock) {
						all.put(objKey
						remaining.remove(objKey);
					}
				}
			}

			if (tmp != null) {
				streamingCallback.onPartialResult(tmp);
				synchronized (lock) {
					remaining.removeAll(tmp.keySet());
				}
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
			StreamingCallback<Map<ObjectIndexKey
		private final Object lock = new Object();

		private final Map<ObjectIndexKey

		private final AsyncCallback<Map<ObjectIndexKey

		private final StreamingCallback<Map<ObjectIndexKey

		LoaderFromDatabase(
				Map<ObjectIndexKey
				AsyncCallback<Map<ObjectIndexKey
				StreamingCallback<Map<ObjectIndexKey
			this.all = all;
			this.normalCallback = normalCallback;
			this.streamingCallback = streamingCallback;
		}

		public void onPartialResult(
				Map<ObjectIndexKey
			final Map<ObjectIndexKey

			if (streamingCallback != null)
				streamingCallback.onPartialResult(result);
			else {
				synchronized (lock) {
					all.putAll(result);
				}
			}

			executor.submit(new Runnable() {
				public void run() {
					List<Change> ops = new ArrayList<Change>(toPut.size());

					for (Map.Entry<ObjectIndexKey
						List<ObjectInfo> items = copy(e.getValue());
						ObjectInfo.sort(items);
						ops.add(Change.put(ns.key(e.getKey())
					}

					client.modify(ops
				}
			});
		}

		private <K
			return new HashMap<K
		}

		private <T> List<T> copy(Collection<T> result) {
			return new ArrayList<T>(result);
		}

		private <K
			return toPut.entrySet();
		}

		public void onSuccess(Map<ObjectIndexKey
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
