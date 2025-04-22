
package org.eclipse.jgit.storage.dht.spi.cache;

import static java.util.Collections.singleton;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.ChunkMeta;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.PackChunk;
import org.eclipse.jgit.storage.dht.StreamingCallback;
import org.eclipse.jgit.storage.dht.Sync;
import org.eclipse.jgit.storage.dht.TinyProtobuf;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.cache.CacheService.Change;

public class CacheChunkTable implements ChunkTable {
	private final ChunkTable db;

	private final ExecutorService executor;

	private final CacheService client;

	private final Sync<Void> none;

	private final Namespace ns = Namespace.CHUNK;

	public CacheChunkTable(ChunkTable dbTable
		this.db = dbTable;
		this.executor = cacheDatabase.getExecutorService();
		this.client = cacheDatabase.getClient();
		this.none = Sync.none();
	}

	public void get(Context options
			AsyncCallback<Collection<PackChunk.Members>> callback) {
		List<CacheKey> toFind = new ArrayList<CacheKey>(keys.size());
		for (ChunkKey k : keys)
			toFind.add(ns.key(k));
		client.get(toFind
	}

	public void put(PackChunk.Members chunk
			throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.put(chunk

		if (chunk.hasChunkData())
			buf.put(ns.key(chunk.getChunkKey())
		else
			buf.removeAfterFlush(ns.key(chunk.getChunkKey()));
	}

	public void remove(ChunkKey key
		CacheBuffer buf = (CacheBuffer) buffer;
		db.remove(key
		buf.remove(ns.key(key));
	}

	private static byte[] encode(PackChunk.Members members) {
		final byte[] meta;
		if (members.hasMeta())
			meta = members.getMeta().asBytes();
		else
			meta = null;

		ByteBuffer chunkData = members.getChunkDataAsByteBuffer();
		ByteBuffer chunkIndex = members.getChunkIndexAsByteBuffer();

		TinyProtobuf.Encoder sizer = TinyProtobuf.size();
		TinyProtobuf.Encoder e = sizer;
		do {
			e.bytes(1
			e.bytes(2
			e.bytes(3
			if (e == sizer)
				e = TinyProtobuf.encode(e.size());
			else
				return e.asByteArray();
		} while (true);
	}

	private static PackChunk.Members decode(ChunkKey key
		PackChunk.Members members = new PackChunk.Members();
		members.setChunkKey(key);

		TinyProtobuf.Decoder d = TinyProtobuf.decode(raw);
		for (;;) {
			switch (d.next()) {
			case 0:
				return members;
			case 1: {
				int cnt = d.bytesLength();
				int ptr = d.bytesOffset();
				byte[] buf = d.bytesArray();
				members.setChunkData(buf
				continue;
			}
			case 2: {
				int cnt = d.bytesLength();
				int ptr = d.bytesOffset();
				byte[] buf = d.bytesArray();
				members.setChunkIndex(buf
				continue;
			}
			case 3:
				members.setMeta(ChunkMeta.fromBytes(key
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

		private final Set<ChunkKey> remaining;

		private final AsyncCallback<Collection<PackChunk.Members>> normalCallback;

		private final StreamingCallback<Collection<PackChunk.Members>> streamingCallback;

		private final List<PackChunk.Members> all;

		LoaderFromCache(Context options
				AsyncCallback<Collection<PackChunk.Members>> callback) {
			this.options = options;
			this.remaining = new HashSet<ChunkKey>(keys);
			this.normalCallback = callback;

			if (callback instanceof StreamingCallback<?>) {
				streamingCallback = (StreamingCallback<Collection<PackChunk.Members>>) callback;
				all = null;
			} else {
				streamingCallback = null;
				all = new ArrayList<PackChunk.Members>(keys.size());
			}
		}

		public void onPartialResult(Map<CacheKey
			for (Map.Entry<CacheKey
				ChunkKey key = ChunkKey.fromBytes(ent.getKey().getBytes());
				PackChunk.Members members = decode(key

				if (streamingCallback != null) {
					streamingCallback.onPartialResult(singleton(members));

					synchronized (lock) {
						remaining.remove(key);
					}
				} else {
					synchronized (lock) {
						all.add(members);
						remaining.remove(key);
					}
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
			StreamingCallback<Collection<PackChunk.Members>> {
		private final Object lock = new Object();

		private final List<PackChunk.Members> all;

		private final AsyncCallback<Collection<PackChunk.Members>> normalCallback;

		private final StreamingCallback<Collection<PackChunk.Members>> streamingCallback;

		LoaderFromDatabase(
				List<PackChunk.Members> all
				AsyncCallback<Collection<PackChunk.Members>> normalCallback
				StreamingCallback<Collection<PackChunk.Members>> streamingCallback) {
			this.all = all;
			this.normalCallback = normalCallback;
			this.streamingCallback = streamingCallback;
		}

		public void onPartialResult(Collection<PackChunk.Members> result) {
			final List<PackChunk.Members> toPutIntoCache = copy(result);

			if (streamingCallback != null)
				streamingCallback.onPartialResult(result);
			else {
				synchronized (lock) {
					all.addAll(result);
				}
			}

			executor.submit(new Runnable() {
				public void run() {
					for (PackChunk.Members members : toPutIntoCache) {
						ChunkKey key = members.getChunkKey();
						Change op = Change.put(ns.key(key)
						client.modify(singleton(op)
					}
				}
			});
		}

		private <T> List<T> copy(Collection<T> result) {
			return new ArrayList<T>(result);
		}

		public void onSuccess(Collection<PackChunk.Members> result) {
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
