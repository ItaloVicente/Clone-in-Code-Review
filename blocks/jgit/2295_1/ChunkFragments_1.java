
package org.eclipse.jgit.storage.dht;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.lib.AnyObjectId;

public class ChunkCache {
	private static final ChunkCache cache = new ChunkCache();

	static ChunkCache get() {
		return cache;
	}


	private final ReentrantLock lock;

	private final LinkedHashMap<ChunkKey

	private ChunkCache() {
		final int capacity = 32;
		chunks = new LinkedHashMap<ChunkKey
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry<ChunkKey
				return capacity < size();
			}
		};
	}

	PackChunk get(ChunkKey key) {
		lock.lock();
		try {
			return chunks.get(key);
		} finally {
			lock.unlock();
		}
	}

	DhtReader.ChunkAndOffset find(RepositoryKey repo
		lock.lock();
		try {
			for (PackChunk c : chunks.values()) {
				int pos = c.findOffset(repo
				if (0 <= pos) {
					chunks.get(c.getChunkKey());
					return new DhtReader.ChunkAndOffset(c
				}
			}
			return null;
		} finally {
			lock.unlock();
		}
	}

	PackChunk put(PackChunk chunk) {
		lock.lock();
		try {
			if (chunks.containsKey(chunk.getChunkKey()))
				return chunks.get(chunk.getChunkKey());

			chunks.put(chunk.getChunkKey()
			return chunk;
		} finally {
			lock.unlock();
		}
	}
}
