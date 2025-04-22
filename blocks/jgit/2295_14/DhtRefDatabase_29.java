
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.Config;

public class DhtReaderOptions {
	public static final int KiB = 1024;

	public static final int MiB = 1024 * KiB;

	private Timeout timeout;

	private boolean prefetchFollowEdgeHints;

	private int prefetchLimit;

	private int objectIndexConcurrentBatches;

	private int objectIndexBatchSize;

	private int deltaBaseCacheSize;

	private int deltaBaseCacheLimit;

	private int recentInfoCacheSize;

	private int recentChunkCacheSize;

	public DhtReaderOptions() {
		setTimeout(Timeout.seconds(5));
		setPrefetchFollowEdgeHints(true);
		setPrefetchLimit(5 * MiB);

		setObjectIndexConcurrentBatches(2);
		setObjectIndexBatchSize(512);

		setDeltaBaseCacheSize(1024);
		setDeltaBaseCacheLimit(10 * MiB);

		setRecentInfoCacheSize(4096);
		setRecentChunkCacheSize(4);
	}

	public Timeout getTimeout() {
		return timeout;
	}

	public DhtReaderOptions setTimeout(Timeout maxWaitTime) {
		if (maxWaitTime == null || maxWaitTime.getTime() < 0)
			throw new IllegalArgumentException();
		timeout = maxWaitTime;
		return this;
	}

	public boolean isPrefetchFollowEdgeHints() {
		return prefetchFollowEdgeHints;
	}

	public DhtReaderOptions setPrefetchFollowEdgeHints(boolean follow) {
		prefetchFollowEdgeHints = follow;
		return this;
	}

	public int getPrefetchLimit() {
		return prefetchLimit;
	}

	public DhtReaderOptions setPrefetchLimit(int maxBytes) {
		prefetchLimit = Math.max(1024
		return this;
	}

	public int getObjectIndexConcurrentBatches() {
		return objectIndexConcurrentBatches;
	}

	public DhtReaderOptions setObjectIndexConcurrentBatches(int batches) {
		objectIndexConcurrentBatches = Math.max(1
		return this;
	}

	public int getObjectIndexBatchSize() {
		return objectIndexBatchSize;
	}

	public DhtReaderOptions setObjectIndexBatchSize(int objectCnt) {
		objectIndexBatchSize = Math.max(1
		return this;
	}

	public int getDeltaBaseCacheSize() {
		return deltaBaseCacheSize;
	}

	public DhtReaderOptions setDeltaBaseCacheSize(int slotCnt) {
		deltaBaseCacheSize = Math.max(1
		return this;
	}

	public int getDeltaBaseCacheLimit() {
		return deltaBaseCacheLimit;
	}

	public DhtReaderOptions setDeltaBaseCacheLimit(int maxBytes) {
		deltaBaseCacheLimit = Math.max(0
		return this;
	}

	public int getRecentInfoCacheSize() {
		return recentInfoCacheSize;
	}

	public DhtReaderOptions setRecentInfoCacheSize(int objectCnt) {
		recentInfoCacheSize = Math.max(0
		return this;
	}

	public int getRecentChunkCacheSize() {
		return recentChunkCacheSize;
	}

	public DhtReaderOptions setRecentChunkCacheSize(int chunkCnt) {
		recentChunkCacheSize = Math.max(0
		return this;
	}

	public DhtReaderOptions fromConfig(Config rc) {
		setTimeout(Timeout.getTimeout(rc
		setPrefetchFollowEdgeHints(rc.getBoolean("core"
		setPrefetchLimit(rc.getInt("core"

		setObjectIndexConcurrentBatches(rc.getInt("core"
		setObjectIndexBatchSize(rc.getInt("core"

		setDeltaBaseCacheSize(rc.getInt("core"
		setDeltaBaseCacheLimit(rc.getInt("core"

		setRecentInfoCacheSize(rc.getInt("core"
		setRecentChunkCacheSize(rc.getInt("core"
		return this;
	}
}
