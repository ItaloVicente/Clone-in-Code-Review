
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.Config;

public class ChunkCacheConfig {
	public static final int KiB = 1024;

	public static final int MiB = 1024 * KiB;

	private long chunkCacheLimit;

	public ChunkCacheConfig() {
		setChunkCacheLimit(10 * MiB);
	}

	public long getChunkCacheLimit() {
		return chunkCacheLimit;
	}

	public ChunkCacheConfig setChunkCacheLimit(final long newLimit) {
		chunkCacheLimit = Math.max(0
		return this;
	}

	public ChunkCacheConfig fromConfig(final Config rc) {
		setChunkCacheLimit(rc.getLong("core"
		return this;
	}
}
